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

public class BackgroundFixed {
	protected Texture mTexture;
	protected Material mMaterial;
	protected Plane mPlane;

	public BackgroundFixed(String nameTexture, int resourceID)
			throws TextureException {
		mTexture = new Texture(nameTexture, resourceID);
		mPlane = new Plane(1f, 1f, 1, 1);
		mMaterial = new Material();
		mMaterial.addTexture(mTexture);
		mPlane.setMaterial(mMaterial);
		mPlane.setPosition(0, 0, 0);
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
		mPlane = other.getObject3D();
	}

	public Texture getTexture() {
		return mTexture;
	}

	public Material getMaterial() {
		return mMaterial;
	}

	public Plane getObject3D() {
		return mPlane;
	}

	public int getWidth() {
		return mTexture.getWidth();
	}

	public int getHeight() {
		return mTexture.getHeight();
	}

	public void surfaceChanged(int width, int height) {
		float ratioDisplay = (float) height / (float) width;
		float ratioSize = 1f / getHeight();
		float taille = getWidth() * ratioSize * ratioDisplay;
		mPlane.setScaleX(taille);
		mPlane.setScaleY(1);
	}
}
