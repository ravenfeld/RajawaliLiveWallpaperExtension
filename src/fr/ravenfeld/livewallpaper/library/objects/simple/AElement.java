package fr.ravenfeld.livewallpaper.library.objects.simple;

import rajawali.materials.Material;
import rajawali.materials.textures.ASingleTexture;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.Texture;
import rajawali.math.vector.Vector3;
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

    public void setPosition(double x, double y) {
        mPlane.setPosition(x, y, mPlane.getZ());
    }

    public void setPosition(double x, double y, double z) {
        mPlane.setPosition(x, y, z);
    }

    public Vector3 getPosition() {
        return mPlane.getPosition();
    }

    public void setX(double x) {
        mPlane.setX(x);
    }

    public double getX() {
        return mPlane.getX();
    }

    public void setY(double y) {
        mPlane.setY(y);
    }

    public double getY() {
        return mPlane.getY();
    }

    public void setZ(double z) {
        mPlane.setZ(z);
    }

    public double getZ() {
        return mPlane.getZ();
    }

    public abstract void surfaceChanged(int width, int height);

    public abstract void surfaceDestroyed() throws ATexture.TextureException;

    public abstract ASingleTexture getTexture();
}
