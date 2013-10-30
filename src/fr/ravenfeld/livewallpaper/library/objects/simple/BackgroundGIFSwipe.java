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

import rajawali.materials.textures.ATexture.TextureException;
import fr.ravenfeld.livewallpaper.library.objects.interaction.IOffsetsChanged;
import fr.ravenfeld.livewallpaper.library.objects.interaction.SwipeDirection;

public class BackgroundGIFSwipe extends BackgroundGIFFixed implements
        IOffsetsChanged {
    private float mWidthSwipe;
    protected SwipeDirection mSwipeDirection;

    public BackgroundGIFSwipe(String nameTexture, int resourceId)
            throws TextureException {
        this(nameTexture, resourceId, SwipeDirection.NORMAL);
    }

    public BackgroundGIFSwipe(String nameTexture, int resourceId,
                              int textureSize) throws TextureException {
        this(nameTexture, resourceId, textureSize, SwipeDirection.NORMAL);
    }

    public BackgroundGIFSwipe(String nameTexture, int resourceId,
                              SwipeDirection direction) throws TextureException {
        super(nameTexture, resourceId);
        setSwipeDirection(direction);
    }

    public BackgroundGIFSwipe(String nameTexture, int resourceId,
                              int textureSize,
                              SwipeDirection direction) throws TextureException {
        super(nameTexture, resourceId, textureSize);
        setSwipeDirection(direction);
    }

    public BackgroundGIFSwipe(BackgroundGIFSwipe other) {
        super(other);
        setFrom(other);
    }

    @Override
    public BackgroundGIFSwipe clone() {
        return new BackgroundGIFSwipe(this);
    }


    public void setFrom(BackgroundGIFSwipe other) {
        super.setFrom(other);
        mWidthSwipe = getWidthSwipe();
        mSwipeDirection = getSwipeDirection();
    }

    @Override
    public void surfaceChanged(int width, int height) {
        float ratioDisplay = (float) height / (float) width;
        float ratioSize = 1f / getHeight();
        float scaleX = getWidth() * ratioSize * ratioDisplay;
        mPlane.setScaleX(scaleX);
        mPlane.setScaleY(1);

        if (ratioDisplay >= 1) {
            mWidthSwipe = scaleX;
        } else {
            mWidthSwipe = 1;
        }
    }

    public void offsetsChanged(float xOffset) {
        if (mSwipeDirection == SwipeDirection.INVERSE) {
            xOffset = -1.0f * xOffset + 1.0f;
        }
        setX((1 - mWidthSwipe) * (xOffset - 0.5f));
    }

    @Override
    public SwipeDirection getSwipeDirection() {
        return mSwipeDirection;
    }

    @Override
    public void setSwipeDirection(SwipeDirection direction) {
        mSwipeDirection = direction;
    }

    @Override
    public float getWidthSwipe() {
        return mWidthSwipe;
    }
}
