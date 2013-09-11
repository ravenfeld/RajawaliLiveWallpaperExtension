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
import rajawali.primitives.PointSprite;
import android.graphics.Color;

public class BackgroundFixed {
	protected Texture mTexture;
	protected Material mMaterial;
	protected PointSprite mPointSprite;

	public BackgroundFixed(String nameTexture, int resourceId)
			throws TextureException {
		mTexture = new Texture(nameTexture, resourceId);
		mPointSprite = new PointSprite(1f, 1f);
		mMaterial = new Material();
		mMaterial.addTexture(mTexture);
		mPointSprite.setMaterial(mMaterial);
		mPointSprite.setPosition(0, 0, 0);
	}

	public BackgroundFixed(BackgroundFixed other) {
		setFrom(other);
	}

	@Override
	public BackgroundFixed clone() {
		return new BackgroundFixed(this);
	}

	public void setFrom(BackgroundFixed other) {
		mTexture = other.getTexture();
		mMaterial = other.getMaterial();
		mPointSprite = other.getObject3D();
	}

	public Texture getTexture() {
		return mTexture;
	}

	public Material getMaterial() {
		return mMaterial;
	}

	public PointSprite getObject3D() {
		return mPointSprite;
	}

	public int getWidth() {
		return mTexture.getWidth();
	}

	public int getHeight() {
		return mTexture.getHeight();
	}

	public void setTransparent(boolean transparent) {
		mPointSprite.setTransparent(transparent);
	}

	public void surfaceChanged(int width, int height) {
		float ratioDisplay = (float) height / (float) width;
		float ratioSize = 1f / getHeight();
		float taille = getWidth() * ratioSize * ratioDisplay;
		mPointSprite.setScaleX(taille);
		mPointSprite.setScaleY(1);
	}
}
