package fr.ravenfeld.livewallpaper.library.objects.animation;

import android.util.Log;

import fr.ravenfeld.livewallpaper.library.objects.simple.Image;
import rajawali.animation.Animation3D;
import rajawali.materials.textures.Texture;

public class SwitchTextureAnimation extends Animation3D {

    protected Texture[] mTextures;

    public SwitchTextureAnimation() {
        super();
    }

    public void play() {
        super.play();
    }

    @Override
    protected void applyTransformation() {
        if(mTextures!= null && mTextures.length==2){
            mTextures[0].setInfluence(1.0f - (float) mInterpolatedTime);
            mTextures[1].setInfluence((float) mInterpolatedTime);
        }
    }

    public void setTextures(Texture[] textures) {
        mTextures = textures;
    }

    protected void eventEnd() {
        super.eventEnd();
        reset();
    }
    public void stop(){
        super.pause();
        mTextures[0].setInfluence(1);
        mTextures[1].setInfluence(0);
    }
    @Override
    public void reset() {
        super.reset();
    }
}

