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
import android.graphics.Bitmap;

public class BackgroundFixedEffect extends ABackground {
	protected Texture mTexture1;
	protected Texture mTexture2;
	private boolean mSens = true;
	public BackgroundFixedEffect(String nameTexture1, int resourceId1,
			String nameTexture2, int resourceId2)
			throws TextureException {
		mTexture1 = new Texture(nameTexture1, resourceId1);
		mTexture2 = new Texture(nameTexture2, resourceId2);
		init();
	}

	public BackgroundFixedEffect(String nameTexture1, Bitmap bitmap1,
			String nameTexture2, Bitmap bitmap2)
			throws TextureException {
		mTexture1 = new Texture(nameTexture1, bitmap1);
		mTexture2 = new Texture(nameTexture2, bitmap2);
		init();
	}

	public BackgroundFixedEffect(BackgroundFixedEffect other) {
		setFrom(other);
	}

	@Override
	public BackgroundFixedEffect clone() {
		return new BackgroundFixedEffect(this);
	}

	private void init() throws TextureException {
		mPlane = new Plane(1f, 1f, 1, 1);
		mMaterial = new Material();
		mMaterial.addTexture(mTexture1);
		mMaterial.addTexture(mTexture2);
		mTexture1.setInfluence(0.0f);
		mTexture2.setInfluence(1.0f);
		mPlane.setMaterial(mMaterial);
		mPlane.setPosition(0, 0, 0);
		mPlane.setRotY(180);
	}

	public void setFrom(BackgroundFixedEffect other) {
		mTexture1 = other.getTextures()[0];
		mTexture2 = other.getTextures()[1];
		mMaterial = other.getMaterial();
		mPlane = other.getObject3D();
	}

	public void setTexture(String nameTexture, int resourceId) {
		if (mTexture1.getTextureName().equalsIgnoreCase(nameTexture)) {
			mTexture1.setResourceId(resourceId);
		} else if (mTexture2.getTextureName().equalsIgnoreCase(nameTexture)) {
			mTexture2.setResourceId(resourceId);
		}

	}

	public void setTexture(String nameTexture, Bitmap bitmap) {
		if (mTexture1.getTextureName().equalsIgnoreCase(nameTexture)) {
			mTexture1.setBitmap(bitmap);
		} else if (mTexture2.getTextureName().equalsIgnoreCase(nameTexture)) {
			mTexture2.setBitmap(bitmap);
		}

	}

	public Texture[] getTextures() {
		return new Texture[] { mTexture1, mTexture2 };
	}

	@Override
	public int getWidth() {
		return Math.max(mTexture1.getWidth(), mTexture2.getWidth());
	}

	@Override
	public int getHeight() {
		return Math.max(mTexture1.getHeight(), mTexture2.getHeight());
	}

	@Override
	public void surfaceChanged(int width, int height) {
		float ratioDisplay = (float) height / (float) width;
		float ratioSize = 1f / getHeight();
		float scale = getWidth() * ratioSize * ratioDisplay;
		mPlane.setScaleX(scale);
		mPlane.setScaleY(1);
	}

	public void update(float pas) {
		if (mTexture1.getInfluence() < 0.0f) {
			mSens = true;
		} else if (mTexture1.getInfluence() > 1.0f) {
			mSens = false;
		}
		if (mSens) {
			mTexture1.setInfluence(mTexture1.getInfluence() + pas);
			mTexture2.setInfluence(mTexture2.getInfluence() - pas);
		} else {
			mTexture1.setInfluence(mTexture1.getInfluence() - pas);
			mTexture2.setInfluence(mTexture2.getInfluence() + pas);
		}
	}
}
