package fr.ravenfeld.livewallpaper.library.objects.interaction;

public interface IOffsetsChanged {

	public void offsetsChanged(float xOffset);

	public SwipeDirection getOffsetDirection();

	public void setOffsetDirection(SwipeDirection direction);
}