package com.belajarspring.tokoonline.qrcodegenerator.Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Paths;

public class GenerateQuickResponseImage {


    public void generateImage(String qr, String milis) throws WriterException, IOException {
        String path = "src/main/resources/static/" + milis + ".png";
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qr, BarcodeFormat.QR_CODE, 720,720);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get(path));
    }
}
