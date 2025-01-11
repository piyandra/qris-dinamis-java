package com.belajarspring.tokoonline.qrcodegenerator.Service;

import com.belajarspring.tokoonline.qrcodegenerator.Entity.Image;
import com.belajarspring.tokoonline.qrcodegenerator.Model.QrisRequest;
import com.belajarspring.tokoonline.qrcodegenerator.Model.QrisResponse;
import com.belajarspring.tokoonline.qrcodegenerator.Repository.QrisRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

@Service
public class QrisService {

    @Autowired
    private QrisRepository qrisRepository;

    public QrisService(QrisRepository qrisRepository) {
        this.qrisRepository = qrisRepository;
    }

    public Image saveImage(File file) throws IOException {
        byte[] fileBytes = getFileBytes(file);
        Image image = new Image();
        image.setImages(fileBytes);
        return qrisRepository.save(image);
    }

    public byte[] getFileBytes(File file) throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            return fileInputStream.readAllBytes();
        }
    }

    public Image getImageById(String id) {
        return qrisRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Image not found"));
    }

    private QrisRequest qrisRequest(Image image) {
        return QrisRequest.builder()
                .qr(image.getQr())
                .amount(image.getAmount())
                .build();
    }

    private QrisResponse qrisResponse(Image image) {
        return QrisResponse.builder()
                .qr(image.getQr())
                .amount(image.getAmount())
                .build();
    }
}


