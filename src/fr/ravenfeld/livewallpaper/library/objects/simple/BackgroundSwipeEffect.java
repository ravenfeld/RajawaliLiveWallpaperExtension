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

import fr.ravenfeld.livewallpaper.library.objects.interaction.IOffsetsChanged;
import fr.ravenfeld.livewallpaper.library.objects.interaction.SwipeDirection;
import rajawali.materials.textures.ATexture.TextureException;

public class BackgroundSwipeEffect extends BackgroundFixedEffect implements IOffsetsChanged {
    protected float mWidthSwipe;
    protected SwipeDirection mSwipeDirection;

    public BackgroundSwipeEffect(String nameTexture1, int resourceId1,
                                 String nameTexture2, int resourceId2)
            throws TextureException {
        this(nameTexture1, resourceId1, nameTexture2, resourceId2, SwipeDirection.NORMAL);
    }

    public BackgroundSwipeEffect(String nameTexture1, int resourceId1,
                                 String nameTexture2, int resourceId2, SwipeDirection direction)
            throws TextureException {
        super(nameTexture1, resourceId1, nameTexture2, resourceId2);
        setSwipeDirection(direction);
    }

    public BackgroundSwipeEffect(String nameTexture1, Bitmap bitmap1,
                                 String nameTexture2, Bitmap bitmap2)
            throws TextureException {
        this(nameTexture1, bitmap1, nameTexture2, bitmap2, SwipeDirection.NORMAL);
    }

    public BackgroundSwipeEffect(String nameTexture1, Bitmap bitmap1,
                                 String nameTexture2, Bitmap bitmap2, SwipeDirection direction)
            throws TextureException {
        super(nameTexture1, bitmap1, nameTexture2, bitmap2);
        setSwipeDirection(direction);
    }

    public BackgroundSwipeEffect(BackgroundSwipeEffect other) {
        super(other);
        setFrom(other);
    }

    @Override
    public BackgroundSwipeEffect clone() {
        return new BackgroundSwipeEffect(this);
    }

    public void setFrom(BackgroundSwipeEffect other) {
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
