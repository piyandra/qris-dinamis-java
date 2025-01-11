package com.belajarspring.tokoonline.qrcodegenerator.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "images")
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String qr;

    private String amount;

    @Lob
    private byte[] images;
}
