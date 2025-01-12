package com.belajarspring.tokoonline.qrcodegenerator.Controller;
import com.belajarspring.tokoonline.qrcodegenerator.Entity.Qris;
import com.belajarspring.tokoonline.qrcodegenerator.Model.ErrorResponse;
import com.belajarspring.tokoonline.qrcodegenerator.Model.GenerateQrisRequest;
import com.belajarspring.tokoonline.qrcodegenerator.Model.QrisGetDataRequest;
import com.belajarspring.tokoonline.qrcodegenerator.Model.WebResponse;
import com.belajarspring.tokoonline.qrcodegenerator.Service.QrisService;
import com.belajarspring.tokoonline.qrcodegenerator.Service.QuickResponseStringGenerator;
import com.google.zxing.WriterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
public class DynamicQris {

    private final QrisService qrisService;

    public DynamicQris(QrisService qrisService) {
        this.qrisService = qrisService;
    }


    @Deprecated
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
            return ResponseEntity.badRequest()
                    .build();
        }
        Path path = Path.of("src/main/resources/static/" + uuid + ".png");
        try {

            File file = new File(path.toString());
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }
            Qris savedQris = qrisService.saveImage(file, GenerateQrisRequest
                    .builder()
                    .build());

            // Prepare Delete File
            File fileToDelete = new File(path.toString());
            fileToDelete.delete();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedQris.getId());
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/photo",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<?> postQrisData(@RequestBody GenerateQrisRequest generateQrisRequest) {
        String uuid = UUID.randomUUID().toString();
        Path path = Path.of("src/main/resources/static/" + uuid + ".png");
        QuickResponseStringGenerator quickResponseStringGenerator = new QuickResponseStringGenerator();
        try {
            quickResponseStringGenerator.qrStringNew(generateQrisRequest.getQr(),
                    generateQrisRequest.getAmount(), uuid);
        } catch (WriterException | IOException writerException){
            return new WebResponse<>("Error Generating QR", null);
        }
        try {
            if (generateQrisRequest.getQr().isEmpty() || generateQrisRequest.getAmount().isEmpty()) {
                return new WebResponse<>("", "QR or Amount is Empty");
            }
        } catch (Exception e) {
            return new WebResponse<>("QR or Amount is Empty", null);
        }
        try {
            File file = new File(path.toString());
            Qris savedQris = qrisService.saveImage(file, generateQrisRequest);
            // Prepare Delete File
            File fileToDelete = new File(path.toString());
            fileToDelete.delete();
            return new WebResponse<>(GenerateQrisRequest.builder()
                    .qr(savedQris.getId())
                    .amount(savedQris.getAmount())
                    .build(), null);
        } catch (IOException e) {
            return new WebResponse<>("Error Saving Image", null);
        }

    }

    @GetMapping("/photo/{id}")
    private ResponseEntity<?> getImage(@PathVariable QrisGetDataRequest id) {
        Qris qris = qrisService.getImageById(id);

        try {
            ErrorResponse errorResponse = new ErrorResponse("Not Found", 404);
            if (qris == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(errorResponse);
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qris.getImages());
    }



}
