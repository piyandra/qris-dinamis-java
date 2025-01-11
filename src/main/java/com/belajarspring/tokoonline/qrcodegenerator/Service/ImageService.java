package com.belajarspring.tokoonline.qrcodegenerator.Service;

import com.belajarspring.tokoonline.qrcodegenerator.Entity.Image;
import com.belajarspring.tokoonline.qrcodegenerator.Repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;

    public ImageService(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    public Image saveImage(File file) throws IOException {
        byte[] fileBytes = getFileBytes(file);
        Image image = new Image();
        image.setImages(fileBytes);
        return imageRepo.save(image);
    }

    public byte[] getFileBytes(File file) throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            return fileInputStream.readAllBytes();
        }
    }

    public Image getImageById(String id) {
        return imageRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Image not found"));
    }
}


