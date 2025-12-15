package app.model;

public class TextElement extends SlideElement {
    private String text;

    public TextElement(int x, int y, int w, int h, String text) {
        super(x, y, w, h);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public SlideElement copy() {
        TextElement copy = new TextElement(x, y, width, height, text);
        copy.rotation = this.rotation;
        return copy;
    }


}
