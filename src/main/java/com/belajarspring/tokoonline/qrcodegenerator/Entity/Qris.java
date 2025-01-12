package com.belajarspring.tokoonline.qrcodegenerator.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "images")
@Builder
public class Qris {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String qr;

    private String amount;

    @Lob
    private byte[] images;
}
