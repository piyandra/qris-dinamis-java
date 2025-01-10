package com.belajarspring.tokoonline.qrcodegenerator.Controller;

import com.belajarspring.tokoonline.qrcodegenerator.Service.QuickResponseStringGenerator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class PhotoController {

    @GetMapping("/photo")
    public ResponseEntity<byte[]> getPhoto(@RequestParam("qr") String qr, @RequestParam("amount") String amount){
        String times = String.valueOf(System.currentTimeMillis());
        QuickResponseStringGenerator quickResponseStringGenerator = new QuickResponseStringGenerator();
        if (qr.isEmpty() || amount.isEmpty()) {
            return ResponseEntity.badRequest().body("Kode QR Atau Nominal Harus Diisi".getBytes());
        }
        try {
            quickResponseStringGenerator.qrStringNew(qr, amount, times);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Path path = Path.of("src/main/resources/static/" + times + ".png");
            InputStream in = Files.newInputStream(path);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(in.readAllBytes());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
