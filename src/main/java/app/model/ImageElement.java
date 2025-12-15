package app.model;

public class ImageElement extends SlideElement {
    public ImageElement(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public byte[] getImagePath() {
    }

    public class ImageElement extends SlideElement {

        private String imagePath;

        public ImageElement(int x, int y, int w, int h, String imagePath) {
            super(x, y, w, h);
            this.imagePath = imagePath;
        }

        public String getImagePath() {
            return imagePath;
        }

        @Override
        public SlideElement copy() {
            ImageElement copy = new ImageElement(x, y, width, height, imagePath);
            copy.rotation = this.rotation;
            return copy;
        }
    }
}

