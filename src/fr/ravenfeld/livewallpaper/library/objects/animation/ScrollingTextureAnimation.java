package fr.ravenfeld.livewallpaper.library.objects.animation;

import fr.ravenfeld.livewallpaper.library.objects.simple.AElement;
import rajawali.animation.Animation3D;

public class ScrollingTextureAnimation extends Animation3D {

    protected AElement mImage;

    public ScrollingTextureAnimation(AElement image) {
        super();
        setImage(image);
    }

    public void play() {
        super.play();
    }

    @Override
    protected void applyTransformation() {
        mImage.getTexture().setOffsetU((float) mInterpolatedTime);
    }

    public void setImage(AElement image) {
        super.setTransformable3D(image.getObject3D());
        mImage = image;
        mImage.getTexture().enableOffset(true);
        mImage.getTexture().setOffsetU(0f);
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

