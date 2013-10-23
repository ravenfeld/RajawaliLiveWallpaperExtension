package fr.ravenfeld.livewallpaper.library.objects.animation;

import android.util.Log;

import fr.ravenfeld.livewallpaper.library.objects.simple.AElement;
import rajawali.animation.Animation3D;

public class AppearAnimation extends Animation3D {

    protected AElement mImage;

    public AppearAnimation() {
        super();
    }

    public void play() {
        super.play();
        mImage.getTexture().setInfluence(0.0f);
    }

    @Override
    protected void applyTransformation() {
        mImage.getTexture().setInfluence((float) mInterpolatedTime);
    }

    public void setImage(AElement image) {
        super.setTransformable3D(image.getObject3D());
        mImage = image;
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

