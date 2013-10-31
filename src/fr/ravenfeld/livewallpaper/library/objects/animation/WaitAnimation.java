package fr.ravenfeld.livewallpaper.library.objects.animation;

import fr.ravenfeld.livewallpaper.library.objects.simple.AElement;
import rajawali.animation.Animation3D;

public class WaitAnimation extends Animation3D {


    public WaitAnimation(long duration) {
        super();
        setDuration(duration);
    }

    public void play() {
try{
    super.play();
}catch (Exception e)
{

}
    }

    @Override
    protected void applyTransformation() {

    }
}

