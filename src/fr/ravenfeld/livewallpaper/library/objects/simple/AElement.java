package fr.ravenfeld.livewallpaper.library.objects.simple;

import rajawali.materials.Material;
import rajawali.materials.textures.ASingleTexture;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;

public abstract class AElement {
	protected Material mMaterial;
	protected Plane mPlane;
	public Material getMaterial() {
		return mMaterial;
	}

	public Plane getObject3D() {
		return mPlane;
	}

	public abstract int getWidth();

	public abstract int getHeight();

	public void setTransparent(boolean transparent) {
		mPlane.setTransparent(transparent);
	}

	public void setVisible(boolean visible) {
		mPlane.setVisible(visible);
	}

	public boolean isVisible() {
		return mPlane.isVisible();
	}
	public abstract void surfaceChanged(int width, int height);

    public abstract void surfaceDestroyed() throws ATexture.TextureException;

    public abstract ASingleTexture getTexture();
}
