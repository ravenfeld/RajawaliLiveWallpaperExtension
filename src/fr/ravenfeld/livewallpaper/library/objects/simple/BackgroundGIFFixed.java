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
import rajawali.materials.textures.AnimatedGIFTexture;
import rajawali.primitives.Plane;
import android.util.Log;

public class BackgroundGIFFixed {
	protected AnimatedGIFTexture mTexture;
	protected Material mMaterial;
	protected Plane mPlane;

	public BackgroundGIFFixed(String nameTexture, int resourceId)
			throws TextureException {
		this(nameTexture, resourceId, 512);
	}

	public BackgroundGIFFixed(String nameTexture, int resourceID,
			int textureSize) throws TextureException {
		mTexture = new AnimatedGIFTexture(nameTexture, resourceID, textureSize);
		mPlane = new Plane(1f, 1f, 1, 1);
		mMaterial = new Material();
		mMaterial.addTexture(mTexture);
		mTexture.rewind();
		mPlane.setMaterial(mMaterial);
		mPlane.setPosition(0, 0, 0);
	}

	public BackgroundGIFFixed(BackgroundGIFFixed other) {
		setFrom(other);
	}

	@Override
	public BackgroundGIFFixed clone() {
		return new BackgroundGIFFixed(this);
	}

	public void setFrom(BackgroundGIFFixed other) {
		mTexture = other.getTexture();
		mMaterial = other.getMaterial();
		mPlane = other.getObject3D();
	}

	public AnimatedGIFTexture getTexture() {
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

	public void setLoop(boolean loop) {
		mTexture.setLoop(loop);
	}

	public void rewind() {
		mTexture.rewind();
	}

	public void animate() {
		mTexture.animate();
	}

	public void stopAnimation() {
		mTexture.stopAnimation();
	}



	public void update() throws TextureException {
		mTexture.update();
	}

	public void surfaceChanged(int width, int height) {
		Log.e("TEST", "size " + width + " " + height);
		Log.e("TEST", "size image " + getWidth() + " " + getHeight());

		float ratioDisplay = (float) height / (float) width;

		float ratioSize = 1f / getHeight();
		float taille = getWidth() * ratioSize * ratioDisplay;
		Log.e("TEST", "ratio " + ratioSize + " " + ratioDisplay + " " + taille);
		mPlane.setScaleX(taille);
		mPlane.setScaleY(1);
		// mPlane.setMaterial(mMaterial);
		// if (ratioDisplay >= 1) {
		// rendererPortrait(ratioDisplay);
		// } else {
		// rendererLandscape(ratioDisplay);
		// }
	}

	// private void rendererLandscape(float ratioDisplay) {
	//
	// }
	//
	// private void rendererPortrait(float ratioDisplay) {
	//
	// }
}