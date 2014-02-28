/**
 * Copyright 2013 Alexis Lecanu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package fr.ravenfeld.livewallpaper.library.objects.simple;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import fr.ravenfeld.livewallpaper.library.objects.Utils;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;

public class Text extends AElement {
    protected Texture mTexture;
    protected String mText;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private final Context mContext;
    private int mWidth;
    private int mHeight;
    private int mLayoutGravity;
    private int mWidthMax;
    private int mHeightMax;
    private float mTextSize;
    private Paint mPaint;
    private int mColorText;
    private int mColorBackgroundText;
    private int mColorBackgroundLayout;

    public Text(Context context, String textureName, String text) {
        this(context, textureName, text, 14);
    }

    public Text(Context context, String textureName, String text, int textSize) {
        this(context, textureName, text, textSize, Gravity.CENTER);
    }


    public Text(Context context, String textureName, String text, float textSize, int layoutGravity) {
        this(context, textureName, text, textSize, getDisplayWidth(context), getDisplayHeight(context), layoutGravity);
    }

    public Text(Context context, String textureName, String text, float textSize, int width, int height, int layoutGravity) {
        mContext = context;
        mText = text;
        mTextSize = textSize;
        mPlane = new Plane(1f, 1f, 1, 1);
        mPlane.setColor(Color.TRANSPARENT);
        mPlane.setTransparent(true);
        mPlane.setRotY(180);

        mWidth = Utils.nextPowerOfTwo(width);
        mHeight = Utils.nextPowerOfTwo(height);
        mWidthMax = width;
        mHeightMax = height;

        mPaint = new Paint();

        mPaint.setStrokeWidth(0);
        mPaint.setAntiAlias(true);

        mColorText = Color.BLACK;
        mColorBackgroundText = Color.TRANSPARENT;
        mColorBackgroundLayout = Color.TRANSPARENT;

        mMaterial = new Material();
        mMaterial.setColorInfluence(0);
        mLayoutGravity = layoutGravity;

        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        draw();

        mTexture = new Texture(textureName, mBitmap);

        mMaterial = new Material();
        try {
            mMaterial.addTexture(mTexture);
        } catch (TextureException e) {
            e.printStackTrace();
        }
        mPlane.setMaterial(mMaterial);
    }


    private static int getDisplayWidth(Context context) {
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    private static int getDisplayHeight(Context context) {
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }


    public void setGravity(int gravity) {
        mLayoutGravity = gravity;
        draw();
    }

    public void setBackgroundColorLayout(int color) {
        mColorBackgroundLayout = color;
        draw();
    }

    public void setBackgroundColor(int color) {
        mColorBackgroundText = color;
        draw();
    }

    public void setTextColor(int color) {
        mColorText = color;
        draw();
    }

    public void setText(String text) {
        mText = text;
        draw();
    }


        public float getTextSize() {
            return mTextSize;
        }

   public void setTextSize(float textSize) {
       mTextSize=textSize;
        draw();
    }

    public String getText() {
        return mText.toString();
    }

    public void setFont(String font) {
        Typeface type = Typeface.createFromAsset(mContext.getAssets(),
                font);
        mPaint.setTypeface(type);
        draw();
    }


    public Plane getObject3D() {
        return mPlane;
    }

    @Override
    public int getWidth() {
        return mWidth;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    public void getHeight(int height) {
        mHeight = height;
    }

    public void getWidth(int width) {
        mWidth = width;
    }

    public void surfaceChanged(int width, int height) {
        mPlane.setScaleX((float) mWidth / (float) width);
        mPlane.setScaleY((float) mHeight / (float) height);
    }

    public void setPosition(double x, double y) {
        mPlane.setPosition(x, y, mPlane.getZ());
    }

    public void setPosition(double x, double y, double z) {
        mPlane.setPosition(x, y, z);
    }

    public void setScale(double scaleX, double scaleY, double scaleZ) {
        mPlane.setScale(scaleX, scaleY, scaleZ);
    }

    private int nbLines() {
        String text = mText.toString();
        String[] split = text.split("\n");
        return split.length;
    }

    private int getLineWidth() {

        String text = mText.toString();

        String[] split = text.split("\n");
        int widthMax = 0;

        for (int i = 0; i < split.length; i++) {
            int width = Math.round(mPaint.measureText(split[i].trim()));
            if (width > widthMax) {
                widthMax = width;
            }
        }

        return widthMax;
    }

    private void splitLines() {
        int lineWidth = getLineWidth();
        while (lineWidth > mWidthMax) {
            String text = mText.toString();
            String[] split = text.split("\n");

            text = "";
            for (int i = 0; i < split.length; i++) {
                String string = split[i];
                int nextPos = mPaint.breakText(string, true, mWidthMax, null);


                if (nextPos < string.length()) {
                    while (string.charAt(nextPos) != ' ' && nextPos > 0) {
                        nextPos--;
                    }
                    String stringLeft = string.substring(0, nextPos);
                    String stringRight = string.substring(nextPos, string.length());
                    string = stringLeft + "\n" + stringRight.trim();
                }
                if (text.equalsIgnoreCase("")) {
                    text = string;
                } else {
                    text = text + "\n" + string;
                }
            }
            mText = text;
            lineWidth = getLineWidth();
        }
    }

    private void draw() {
        splitLines();

        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mPaint.setTextSize(mTextSize);

        int lineHeight = Math.round(mTextSize * (nbLines()-1));

        String[] lines = mText.split("\n");

        float left = mWidth / 2 - mWidthMax / 2;
        float top = mHeight / 2 - mHeightMax / 2;
        float right = left + mWidthMax;
        float bottom = top + mHeightMax;
        mPaint.setColor(mColorBackgroundLayout);
        mCanvas.drawRect(left, top, right, bottom, mPaint);


        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            float lineWidth = mPaint.measureText(line.trim());


            float posX = left;
            float posY = top + mTextSize * (i + 1) - (int) mPaint.descent();
            switch (mLayoutGravity) {
                case Gravity.LEFT:
                    posX = left;
                    break;
                case Gravity.RIGHT:
                    posX = (int) (right - lineWidth);
                    break;
                case Gravity.CENTER_HORIZONTAL:
                    posX = (int) (mWidth / 2f - lineWidth / 2f);
                    break;
                case Gravity.LEFT | Gravity.TOP:
                    posX = left;
                    posY = top + mTextSize * (i + 1);
                    break;
                case Gravity.RIGHT | Gravity.TOP:
                    posX = (int) (right - lineWidth);
                    posY = top + mTextSize * (i + 1) - (int) mPaint.descent();
                    break;
                case Gravity.CENTER_HORIZONTAL | Gravity.TOP:
                    posX = (int) (mWidth / 2f - lineWidth / 2f);
                    posY = top + mTextSize * (i + 1) - (int) mPaint.descent();
                    break;
                case Gravity.LEFT | Gravity.BOTTOM:
                    posX = left;
                    posY = bottom - lineHeight + mTextSize * i - (int) mPaint.descent();
                    break;
                case Gravity.RIGHT | Gravity.BOTTOM:
                    posX = (int) (right - lineWidth);
                    posY = bottom - lineHeight + mTextSize * i - (int) mPaint.descent();
                    break;
                case Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM:
                    posX = (int) (mWidth / 2f - lineWidth / 2f);
                    posY = bottom - lineHeight + mTextSize * i - (int) mPaint.descent();
                    break;
                case Gravity.LEFT | Gravity.CENTER_VERTICAL:
                    posX = left;
                    posY = (int) (mHeight / 2f - lineHeight / 2f) + mTextSize * i;
                    break;
                case Gravity.RIGHT | Gravity.CENTER_VERTICAL:
                    posX = (int) (right - lineWidth);
                    posY = (int) (mHeight / 2f - lineHeight / 2f) + mTextSize * i;
                    break;
                case Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL:
                    posX = (int) (mWidth / 2f - lineWidth / 2f);
                    posY = (int) (mHeight / 2f - lineHeight / 2f) + mTextSize * i;
                    break;
                case Gravity.TOP:
                    posY = top + mTextSize * (i + 1) - (int) mPaint.descent();
                    break;
                case Gravity.BOTTOM:
                    posY = bottom - lineHeight + mTextSize * i - (int) mPaint.descent();
                    break;
                case Gravity.CENTER_VERTICAL:
                    posY = (int) (mHeight / 2f - lineHeight / 2f) + mTextSize * i;
                    break;
            }

            if (posY + (int) mPaint.ascent() >= top && posY + (int) mPaint.descent() <= bottom) {
                mPaint.setColor(mColorBackgroundText);
                mCanvas.drawRect(posX, posY + (int) mPaint.ascent(), posX + lineWidth, posY + (int) mPaint.descent(), mPaint);

                mPaint.setColor(mColorText);

                mCanvas.drawText(line, posX, posY, mPaint);
            }
        }
    }

    public Texture getTexture() {
        return mTexture;
    }

    public Material getMaterial() {
        return mMaterial;
    }

    public void surfaceDestroyed() throws TextureException {
        mMaterial.removeTexture(mTexture);
        mTexture.reset();
    }
}
