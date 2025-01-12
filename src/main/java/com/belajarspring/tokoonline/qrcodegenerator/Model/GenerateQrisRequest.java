package com.belajarspring.tokoonline.qrcodegenerator.Model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateQrisRequest {

    private String qr;
    private String amount;
}
