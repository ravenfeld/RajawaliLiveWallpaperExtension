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

public class BackgroundSwipe extends BackgroundFixed {
	private float mWidthSwipe;

	public BackgroundSwipe(String nameTexture, int resourceId)
			throws TextureException {
		super(nameTexture, resourceId);
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
		mPlane.setX((1 - mWidthSwipe) * (xOffset - 0.5f));
	}

}
