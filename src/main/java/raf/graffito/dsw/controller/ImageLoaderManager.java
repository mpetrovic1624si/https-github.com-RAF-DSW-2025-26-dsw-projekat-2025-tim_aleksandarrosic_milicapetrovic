package raf.graffito.dsw.controller;

import java.io.File;

public class ImageLoaderManager {
    private static ImageLoaderManager instance;
    private File selectedImage;
    
    private ImageLoaderManager() {}
    
    public static ImageLoaderManager getInstance() {
        if (instance == null) {
            instance = new ImageLoaderManager();
        }
        return instance;
    }
    
    public void setSelectedImage(File imageFile) {
        this.selectedImage = imageFile;
    }
    
    public File getSelectedImage() {
        return selectedImage;
    }
    
    public String getSelectedImagePath() {
        return selectedImage != null ? selectedImage.getAbsolutePath() : null;
    }
}

