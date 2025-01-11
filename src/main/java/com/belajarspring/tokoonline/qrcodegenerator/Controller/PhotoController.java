package com.belajarspring.tokoonline.qrcodegenerator.Controller;

import com.belajarspring.tokoonline.qrcodegenerator.Entity.Image;
import com.belajarspring.tokoonline.qrcodegenerator.Service.QrisService;
import com.belajarspring.tokoonline.qrcodegenerator.Service.QuickResponseStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
public class PhotoController {

    @Autowired
    private QrisService qrisService;

    @GetMapping("/photo")
    public ResponseEntity<String> getPhoto(@RequestParam("qr") String qr, @RequestParam("amount") String amount){
        UUID randomUUID = UUID.randomUUID();
        String uuid = randomUUID.toString();
        QuickResponseStringGenerator quickResponseStringGenerator = new QuickResponseStringGenerator();
        if (qr.isEmpty() || amount.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            quickResponseStringGenerator.qrStringNew(qr, amount, uuid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        Path path = Path.of("src/main/resources/static/" + uuid + ".png");
        try {

            File file = new File(path.toString());
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }
            Image savedImage = qrisService.saveImage(file);

            // Prepare Delete File
            File fileToDelete = new File(path.toString());
            fileToDelete.delete();
            return ResponseEntity.status(HttpStatus.CREATED).body(savedImage.getId());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        Image image = qrisService.getImageById(id);
        try {
            if (image == null) {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image.getImages());
    }



}
