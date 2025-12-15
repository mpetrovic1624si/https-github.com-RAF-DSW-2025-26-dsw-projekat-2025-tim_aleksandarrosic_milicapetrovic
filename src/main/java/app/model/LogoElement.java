package app.model;

public class LogoElement extends SlideElement{
    public LogoElement(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public SlideElement copy() {
        LogoElement copy = new LogoElement(x, y, width, height);
        copy.rotation = this.rotation;
        return copy;
    }


}
