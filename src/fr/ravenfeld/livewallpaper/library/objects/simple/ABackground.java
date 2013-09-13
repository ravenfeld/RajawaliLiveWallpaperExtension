package fr.ravenfeld.livewallpaper.library.objects.simple;

import rajawali.materials.Material;
import rajawali.primitives.PointSprite;

public abstract class ABackground {
	protected Material mMaterial;
	protected PointSprite mPointSprite;

	public Material getMaterial() {
		return mMaterial;
	}

	public PointSprite getObject3D() {
		return mPointSprite;
	}

	public abstract int getWidth();

	public abstract int getHeight();

	public void setTransparent(boolean transparent) {
		mPointSprite.setTransparent(transparent);
	}

	public abstract void surfaceChanged(int width, int height);
}
