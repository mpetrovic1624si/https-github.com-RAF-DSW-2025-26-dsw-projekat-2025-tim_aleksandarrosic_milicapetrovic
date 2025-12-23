package model;

import app.model.SlideElement;

/**
 * Apstraktni grafički logo element.
 * Može biti bilo koji oblik (pravougaonik, krug, apstraktni oblik)
 */
public class LogoElement extends SlideElement {

    private String logoType; // npr. "circle", "rectangle", "custom"

    public LogoElement(int x, int y, int width, int height, String logoType) {
        super(x, y, width, height);
        this.logoType = logoType;
    }

    public String getLogoType() { return logoType; }
    public void setLogoType(String logoType) { this.logoType = logoType; }

    @Override
    public SlideElement cloneElement() {
        return new LogoElement(x, y, width, height, logoType);
    }
}
