package com.belajarspring.tokoonline.qrcodegenerator.Service;

import com.google.zxing.WriterException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class QuickResponseStringGenerator {

    public void qrStringNew(String qrCodeText, String amount, String uuid) throws IOException, WriterException {

        GenerateQuickResponseImage generateQuickresponseImage = new GenerateQuickResponseImage();

        ConvertCRC16 convertCRC16 = new ConvertCRC16();
        String QRcode = qrCodeText.trim();
        String trimmed = amount.trim();

        QRcode = QRcode.substring(0, QRcode.length() - 4);
        String step1 = QRcode.replace("010211", "010212");
        String[] step2 = step1.split("5802ID");

        String uang = "54" + String.format("%02d", trimmed.length()) + trimmed;
        uang += "5802ID";

        String fix = step2[0].trim() + uang + step2[1];
        fix += convertCRC16.convertCRC16(fix);
        generateQuickresponseImage.generateImage(fix,  uuid);

    }



}
