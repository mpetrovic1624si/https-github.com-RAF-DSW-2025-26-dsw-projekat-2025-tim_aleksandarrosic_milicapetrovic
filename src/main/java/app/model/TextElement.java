package model;

import app.model.SlideElement;

public class TextElement extends SlideElement {

    private String text;
    private String fontName;
    private int fontSize;

    public TextElement(int x, int y, int width, int height, String text) {
        super(x, y, width, height);
        this.text = text;
        this.fontName = "Arial";
        this.fontSize = 14;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getFontName() { return fontName; }
    public void setFontName(String fontName) { this.fontName = fontName; }

    public int getFontSize() { return fontSize; }
    public void setFontSize(int fontSize) { this.fontSize = fontSize; }

    @Override
    public SlideElement cloneElement() {
        TextElement copy = new TextElement(x, y, width, height, text);
        copy.setFontName(fontName);
        copy.setFontSize(fontSize);
        return copy;
    }
}
