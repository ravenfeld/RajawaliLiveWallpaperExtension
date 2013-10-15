package fr.ravenfeld.livewallpaper.library.objects.animation;

import fr.ravenfeld.livewallpaper.library.objects.simple.Image;
import rajawali.animation.Animation3D;

public class DisappearAnimation extends Animation3D {

    protected Image mImage;

    public DisappearAnimation() {
        super();
    }

    public void play() {
        super.play();
        mImage.getTexture().setInfluence(1.0f);
    }

    @Override
    protected void applyTransformation() {
        mImage.getTexture().setInfluence(1.0f - (float) mInterpolatedTime);
    }

    public void setImage(Image image) {
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

