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
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.AnimatedGIFTexture;
import rajawali.primitives.Plane;

public class BackgroundGIFFixed extends AImageGIF {

	public BackgroundGIFFixed(String nameTexture, int resourceId)
			throws TextureException {
		this(nameTexture, resourceId, 512);
	}

	public BackgroundGIFFixed(String nameTexture, int resourceId,
			int textureSize) throws TextureException {
		mTexture = new AnimatedGIFTexture(nameTexture, resourceId, textureSize);
		mPlane = new Plane(1f, 1f, 1, 1);
		mMaterial = new Material();
        mMaterial.setColorInfluence(0);
		mMaterial.addTexture(mTexture);
		mTexture.rewind();
		mPlane.setMaterial(mMaterial);
		mPlane.setPosition(0, 0, 0);
		mPlane.setRotY(180);
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

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible){
            animate();
        }else{
            stopAnimation();
        }
    }

	@Override
	public void surfaceChanged(int width, int height) {
		float ratioDisplay = (float) height / (float) width;
		float ratioSize = 1f / getHeight();
		float size = getWidth() * ratioSize * ratioDisplay;
		mPlane.setScaleX(size);
		mPlane.setScaleY(1);
	}
}
