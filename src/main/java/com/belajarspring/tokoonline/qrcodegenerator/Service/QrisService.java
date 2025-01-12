package com.belajarspring.tokoonline.qrcodegenerator.Service;

import com.belajarspring.tokoonline.qrcodegenerator.Entity.Qris;
import com.belajarspring.tokoonline.qrcodegenerator.Model.GenerateQrisRequest;
import com.belajarspring.tokoonline.qrcodegenerator.Model.QrisGetDataRequest;
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

    public Qris saveImage(File file, GenerateQrisRequest generateQrisRequest) throws IOException {
        byte[] fileBytes = getFileBytes(file);
        Qris qris = new Qris();
        qris.setImages(fileBytes);
        qris.setAmount(generateQrisRequest.getAmount());
        qris.setQr(generateQrisRequest.getQr());
        return qrisRepository.save(qris);
    }

    public byte[] getFileBytes(File file) throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            return fileInputStream.readAllBytes();
        }
    }

    public Qris getImageById(QrisGetDataRequest qrisGetDataRequest) {
        return qrisRepository.findById(qrisGetDataRequest.getUuid()).orElse(null);
    }

    private GenerateQrisRequest qrisRequest(Qris qris) {
        return GenerateQrisRequest.builder()
                .qr(qris.getQr())
                .amount(qris.getAmount())
                .build();
    }


}


