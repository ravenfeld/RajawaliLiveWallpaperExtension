package fr.ravenfeld.livewallpaper.library.objects.simple;

import android.graphics.Bitmap;

import rajawali.materials.Material;
import rajawali.materials.textures.ASingleTexture;
import rajawali.materials.textures.ATexture;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Plane;

public abstract class AImage extends AElement{
    protected Texture mTexture;

    public  Texture getTexture(){
        return mTexture;
    }

    public void setTexture(int resourceId) {
        mTexture.setResourceId(resourceId);
    }

    public void setTexture(Bitmap bitmap) {
        mTexture.setBitmap(bitmap);
    }

    @Override
    public int getWidth() {
        return mTexture.getWidth();
    }

    @Override
    public int getHeight() {
        return mTexture.getHeight();
    }

    @Override
    public void surfaceDestroyed() throws ATexture.TextureException {
        mMaterial.removeTexture(mTexture);
        mTexture.reset();
    }
}
