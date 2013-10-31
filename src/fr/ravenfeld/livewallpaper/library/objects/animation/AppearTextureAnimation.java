package fr.ravenfeld.livewallpaper.library.objects.animation;

import android.util.Log;

import fr.ravenfeld.livewallpaper.library.objects.simple.AElement;
import rajawali.animation.Animation3D;

public class AppearTextureAnimation extends Animation3D {

    protected AElement mImage;

    public AppearTextureAnimation(AElement image) {
        super();
        setImage(image);
    }

    public void play() {
        super.play();

    }

    @Override
    protected void applyTransformation() {
        mImage.getTexture().setInfluence((float) mInterpolatedTime);
    }

    public void setImage(AElement image) {
        super.setTransformable3D(image.getObject3D());
        mImage = image;
        mImage.getTexture().setInfluence(0.0f);
    }

    protected void eventEnd() {
        super.eventEnd();
        reset();
    }

    @Override
    public void reset() {
        super.reset();
    }
}

