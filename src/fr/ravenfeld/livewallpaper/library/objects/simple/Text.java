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
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;

public class Text extends AElement {
    protected Texture mTexture;
    protected Material mMaterial;
    protected Plane mPlane;
    protected TextView mText;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private final FrameLayout mWidgetGroup;
    private final Context mContext;
    private int mWidth;
    private int mHeight;

    public Text(Context context, String text) {
        this(context, text, 14);
    }

    public Text(Context context, String text, int textSize) {
        this(context, text, textSize, getDisplayWidth(context), getDisplayHeight(context));
    }

    public Text(Context context, String text, int textSize, int width, int height) {
        mContext = context;
        mWidgetGroup = new FrameLayout(mContext);
        mWidgetGroup.setBackgroundColor(Color.TRANSPARENT);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;

        mWidgetGroup.setLayoutParams(params);
        mText = new TextView(mContext);
        mPlane = new Plane(1f, 1f, 1, 1);
        mPlane.setColor(Color.TRANSPARENT);
        mPlane.setTransparent(true);
        mPlane.setRotY(180);

        mWidth = width;
        mHeight = height;

        mMaterial = new Material();
        mMaterial.setColorInfluence(0);

        mText.setText(text);
        mText.setTextSize(textSize);
        mText.setGravity(Gravity.CENTER);

        mWidgetGroup.addView(mText);
        mWidgetGroup.setBackgroundColor(Color.TRANSPARENT);

        draw();

        mTexture = new Texture("text", mBitmap);

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

    public void setBackgroundColor(int color) {
        mText.setBackgroundColor(color);
        draw();
    }

    public void setBackground(Drawable drawable) {
        mText.setBackground(drawable);
        draw();
    }

    public void setBackgroundResource(int resId) {
        mText.setBackgroundResource(resId);
        draw();
    }

    public void setTextColor(int color) {
        mText.setTextColor(color);
        draw();
    }

    public void setTextColor(ColorStateList colors) {
        mText.setTextColor(colors);
        draw();
    }

    public void setText(String text) {
        mText.setText(text);
        draw();
    }

    public float getTextSize() {
        return mText.getTextSize();
    }

    public void setTextSize(float textSize) {
        mText.setTextSize(textSize);
        draw();
    }

    public String getText() {
        return mText.getText().toString();
    }

    public void setFont(String font) {
        Typeface type = Typeface.createFromAsset(mContext.getAssets(),
                font);
        mText.setTypeface(type);
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

    public void setPosition(double x, double y, double z) {
        mPlane.setPosition(x, y, z);
    }

    public void setScale(double scaleX, double scaleY, double scaleZ) {
        mPlane.setScale(scaleX, scaleY, scaleZ);
    }

    private int nbLines() {
        String text = mText.getText().toString();
        String[] split = text.split("\n");
        return split.length;
    }

    private int getLineWidth() {
        Paint textPaint = mText.getPaint();
        String text = mText.getText().toString();
        String[] split = text.split("\n");
        int widthMax = 0;

        for (int i = 0; i < split.length; i++) {

            int width =Math.round(textPaint.measureText(split[i])+0.5f);
            if (width > widthMax) {
                widthMax = width;
            }
        }

        return widthMax;
    }

    private void draw() {
        int lineWidth = getLineWidth();
        while (lineWidth > mWidth) {

            String text = mText.getText().toString();
            String[] split = text.split("\n");
            Paint p = mText.getPaint();
            text = "";
            for (int i = 0; i < split.length; i++) {
                String string = split[i];
                int nextPos = p.breakText(string, false, mWidth, null);
                if (nextPos < string.length()) {
                    string = string.substring(0, nextPos) + "\n" + string.substring(nextPos, string.length());
                }
                if (text.equalsIgnoreCase("")) {
                    text = string;
                } else {
                    text = text + "\n" + string;
                }
            }
            mText.setText(text);
            lineWidth = getLineWidth();
        }
        int lineHeight = Math.round(mText.getLineHeight() * nbLines());
        if (mText.getLineCount() > 1) {
            lineWidth = mWidth;
        }
        mWidgetGroup.measure(mWidth, mHeight);
        mWidgetGroup.layout(0, 0, mWidth, mHeight);
        int posX = (int) (mWidth / 2f - lineWidth / 2f);
        int posY = (int) (mHeight / 2f - lineHeight / 2f);
        mText.layout(posX, posY, posX + lineWidth, posY + lineHeight);
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(mWidgetGroup.getWidth(),
                    mWidgetGroup.getHeight(), Bitmap.Config.ARGB_8888);
        }
        if (mCanvas == null) {
            mCanvas = new Canvas(mBitmap);
        }
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mWidgetGroup.draw(mCanvas);
        surfaceChanged(getDisplayWidth(mContext), getDisplayHeight(mContext));
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
