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

import android.graphics.Bitmap;

import rajawali.animation.Animation;
import rajawali.animation.Animation3D;
import rajawali.animation.SwitchAnimationTexture;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;

public class ImageEffect extends AImage {
    protected float mSize;
    protected Texture mTexture1;
    protected Texture mTexture2;
    private SwitchAnimationTexture mSwitchAnimationTexture;

	public ImageEffect(String nameTexture1, int resourceId1,
                       String nameTexture2, int resourceId2, float size)
			throws TextureException {
        mTexture1 = new Texture(nameTexture1, resourceId1);
        mTexture2 = new Texture(nameTexture2, resourceId2);
		init(size);
	}

	public ImageEffect(String nameTexture1, Bitmap bitmap1,
                       String nameTexture2, Bitmap bitmap2, float size)
			throws TextureException {
        mTexture1 = new Texture(nameTexture1, bitmap1);
        mTexture2 = new Texture(nameTexture2, bitmap2);
		init(size);
	}

	public ImageEffect(ImageEffect other) {
		setFrom(other);
	}

	@Override
	public ImageEffect clone() {
		return new ImageEffect(this);
	}

	private void init(float size) throws TextureException {
        mSize=size;
		mPlane = new Plane(1f, 1f, 1, 1);
		mMaterial = new Material();
        mMaterial.setColorInfluence(0);
        mMaterial.addTexture(mTexture1);
        mMaterial.addTexture(mTexture2);
		mPlane.setMaterial(mMaterial);
		mPlane.setPosition(0, 0, 0);
		mPlane.setRotY(180);
        mSwitchAnimationTexture = new SwitchAnimationTexture();
        mSwitchAnimationTexture.setRepeatMode(Animation.RepeatMode.REVERSE_INFINITE);
        mSwitchAnimationTexture.setTextures(getTextures());
        mSwitchAnimationTexture.play();
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

    public void setFrom(ImageEffect other) {
        mTexture = other.getTexture();
        mMaterial = other.getMaterial();
        mPlane = other.getObject3D();
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


    public float getSize() {
        return mSize;
    }

	@Override
	public void surfaceChanged(int width, int height) {
		float ratioDisplay = (float) height / (float) width;
		float ratioSize = mSize / getHeight();
		float scale = getWidth() * ratioSize * ratioDisplay;
		mPlane.setScaleX(scale);
		mPlane.setScaleY(mSize);
	}

    @Override
    public void surfaceDestroyed() throws TextureException {
        mMaterial.removeTexture(mTexture1);
        mMaterial.removeTexture(mTexture2);
        mTexture1.reset();
        mTexture2.reset();
    }

    public SwitchAnimationTexture getSwitchTextureAnimation(){
        return mSwitchAnimationTexture;
    }

    public void setDuration(long duration){
        mSwitchAnimationTexture.setDuration(duration);
    }
}
