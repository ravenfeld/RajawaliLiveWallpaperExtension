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
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;

public class Text {
    protected Texture mTexture;
    protected Material mMaterial;
    protected Plane mPlane;
    protected TextView mText;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private final FrameLayout mWidgetGroup;
    private final Context mContext;

    public Text(Context context, String text) {
        this(context, text, 14);
    }

    public Text(Context context, String text, int textSize) {
        mContext = context;
        mWidgetGroup = new FrameLayout(mContext);
        mWidgetGroup.setBackgroundColor(Color.TRANSPARENT);
        FrameLayout.LayoutParams params =new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                params.gravity=Gravity.CENTER;

        mWidgetGroup.setLayoutParams(params);
        mText = new TextView(mContext);
        mPlane = new Plane(1f, 1f, 1, 1);
        mPlane.setColor(Color.TRANSPARENT);
        mPlane.setTransparent(true);
        mPlane.setRotY(180);

        mMaterial = new Material();
        mMaterial.setColorInfluence(0);
        mText.setText(text);
        mText.setTextSize(textSize);
        mText.setGravity(Gravity.CENTER);
        mWidgetGroup.addView(mText);
        mWidgetGroup.setBackgroundColor(Color.RED);

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

    public void surfaceChanged(int width, int height) {
        // TODO rotation pour le text
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

    private int nbMaxCharacterLine() {
        String text = mText.getText().toString();
        String[] split = text.split("\n");
        int nbMax = 0;
        if (split.length == 0) {
            nbMax = text.length()+1 ;
        } else {
            for (int i = 0; i < split.length; i++) {
                int length = split[i].length();
                if (length > nbMax) {
                    nbMax = length+1 ;
                }
            }
        }

        return nbMax;
    }

    private void draw() {
        Log.e("TEST","DRAW");


        Display display = ((WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int sizeWidth = Math
                .round((nbMaxCharacterLine() * mText.getTextSize() / 1.8f));
int count=0;
        while(sizeWidth > size.x && count<5){

            String text = mText.getText().toString();
            String[] split = text.split("\n");
            int nbCharMax =(int)( size.x/(mText.getTextSize() / 1.8f))-1;
            if (split.length == 0) {

                String sub=text.substring(0,nbCharMax);
                String sub1=text.substring(nbCharMax);
                mText.setText(sub+"\n"+sub1);
            } else {
                text="";
                for (int i = 0; i < split.length; i++) {
                    String string = split[i];
                    if(string.length()>nbCharMax){
                        String sub=string.substring(0,nbCharMax);
                        String sub1=string.substring(nbCharMax);
                        split[i]=sub+"\n"+sub1;
                    }
                    if(text.equalsIgnoreCase("")){
                     text=split[i];
                    }else{
                    text=text+"\n"+split[i];
                    }
                }
                mText.setText(text);
            }
            Log.e("TEST","TEST phrase "+mText.getText());


             sizeWidth = Math
                    .round((nbMaxCharacterLine() * mText.getTextSize() / 1.8f));
            Log.e("TEST","nbLine "+nbLines()+ " count "+count+ " sizeWidth "+sizeWidth+ " x "+size.x);
            count++;
        }




        int sizeHeight = Math.round(mText.getTextSize() * 1.25f * nbLines());


        mWidgetGroup.layout(0, 0, size.x, size.y);
        int posX =(int)(size.x/2f-sizeWidth/2f);
        int posY = (int)(size.y/2f-sizeHeight/2f);
        mText.layout(posX, posY, posX+sizeWidth, posY+sizeHeight);
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(mWidgetGroup.getWidth(),
                    mWidgetGroup.getHeight(), Bitmap.Config.ARGB_8888);
        }
        if (mCanvas == null) {
            mCanvas = new Canvas(mBitmap);
        }
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mWidgetGroup.draw(mCanvas);
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
