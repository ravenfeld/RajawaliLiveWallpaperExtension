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

public class BackgroundSpriteSheetSwipe extends BackgroundSpriteSheetFixed
        implements IOffsetsChanged {
    private float mWidthSwipe;
    protected SwipeDirection mSwipeDirection;

    public BackgroundSpriteSheetSwipe(String nameTexture, int resourceId,
                                      int numTilesX, int numTilesY)
            throws TextureException {
        this(nameTexture, resourceId, numTilesX, numTilesY,
                SwipeDirection.NORMAL);
    }

    public BackgroundSpriteSheetSwipe(String nameTexture, int resourceId,
                                      int numTilesX, int numTilesY, SwipeDirection direction)
            throws TextureException {
        super(nameTexture, resourceId, numTilesX, numTilesY);
        setSwipeDirection(direction);
    }

    public BackgroundSpriteSheetSwipe(String nameTexture, int resourceId,
                                      int numTilesX, int numTilesY, int numFrames)
            throws TextureException {
        this(nameTexture, resourceId, numTilesX, numTilesY, numFrames,
                SwipeDirection.NORMAL);
    }

    public BackgroundSpriteSheetSwipe(String nameTexture, int resourceId,
                                      int numTilesX, int numTilesY, int numFrames,
                                      SwipeDirection direction) throws TextureException {
        super(nameTexture, resourceId, numTilesX, numTilesY, numFrames);
        setSwipeDirection(direction);
    }

    public BackgroundSpriteSheetSwipe(String nameTexture, int resourceId,
                                      int numTilesX, int numTilesY, int fps, int numFrames)
            throws TextureException {
        this(nameTexture, resourceId, numTilesX, numTilesY, fps, numFrames,
                SwipeDirection.NORMAL);
    }

    public BackgroundSpriteSheetSwipe(String nameTexture, int resourceId,
                                      int numTilesX, int numTilesY, int fps, int numFrames,
                                      SwipeDirection direction) throws TextureException {
        super(nameTexture, resourceId, numTilesX, numTilesY, fps, numFrames);
        setSwipeDirection(direction);
    }

    public BackgroundSpriteSheetSwipe(String nameTexture, int resourceId,
                                      int numTilesX, int numTilesY, long[] frameDurations)
            throws TextureException {
        this(nameTexture, resourceId, numTilesX, numTilesY, frameDurations,
                SwipeDirection.NORMAL);
    }

    public BackgroundSpriteSheetSwipe(String nameTexture, int resourceId,
                                      int numTilesX, int numTilesY, long[] frameDurations,
                                      SwipeDirection direction) throws TextureException {
        super(nameTexture, resourceId, numTilesX, numTilesY, frameDurations);
        setSwipeDirection(direction);
    }

    public BackgroundSpriteSheetSwipe(BackgroundSpriteSheetSwipe other) {
        super(other);
        setFrom(other);
    }

    @Override
    public BackgroundSpriteSheetSwipe clone() {
        return new BackgroundSpriteSheetSwipe(this);
    }

    public void setFrom(BackgroundSpriteSheetSwipe other) {
        super.setFrom(other);
        mWidthSwipe = getWidthSwipe();
        mSwipeDirection = getSwipeDirection();
    }

    @Override
    public void surfaceChanged(int width, int height) {
        float ratioDisplay = (float) height / (float) width;
        float ratioSize = 1f / getHeightTile();
        float scaleX = getWidthTile() * ratioSize * ratioDisplay;
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
