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

import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class Text {
	protected Texture mTexture;
	protected Material mMaterial;
	protected Plane mPlane;
	protected TextView mText;
	private final Bitmap mBitmap;
	private final Canvas mCanvas;
	private final LinearLayout mWidgetGroup;
	private final Context mContext;

	public Text(Context context, String text) {
		this(context, text, 14);
	}

	public Text(Context context, String text, int textSize) {
		mContext = context;
		mWidgetGroup = new LinearLayout(mContext);
		mWidgetGroup.setBackgroundColor(Color.TRANSPARENT);
		mWidgetGroup.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		mText = new TextView(mContext);
		mPlane = new Plane(1f, 1f, 1, 1);
		mPlane.setRotY(180);
		mPlane.setTransparent(true);

		mMaterial = new Material();
		mText.setText(text);
		mText.setTextSize(textSize);
		mWidgetGroup.addView(mText);

		int sizeWidth = Math
				.round((nbMaxCharacterLine() * mText.getTextSize() / 1.80f));
				

		int sizeHeight = Math.round(mText.getTextSize() * 1.25f * nbLignes());

		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);


		mWidgetGroup.layout(0, 0, size.x, size.y);
		mText.layout(0, 0, sizeWidth, sizeHeight);


		mBitmap = Bitmap.createBitmap(mWidgetGroup.getWidth(),
				mWidgetGroup.getHeight(), Bitmap.Config.ARGB_8888);

		mCanvas = new Canvas(mBitmap);
		mWidgetGroup.draw(mCanvas);

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

	private int nbLignes() {
		String text = mText.getText().toString();
		String[] split = text.split("\n");
		return split.length;
	}

	private int nbMaxCharacterLine() {
		String text = mText.getText().toString();
		String[] split = text.split("\n");
		int nbMax = 0;
		if (split.length == 0) {
			nbMax = text.length() + 1;
		} else {
			for (int i = 0; i < split.length; i++) {
				int lenght = split[i].length();
				if (lenght > nbMax) {
					nbMax = lenght + 1;
				}
			}
		}

		return nbMax;
	}
	private void draw() {
		mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		mWidgetGroup.draw(mCanvas);
	}
}
