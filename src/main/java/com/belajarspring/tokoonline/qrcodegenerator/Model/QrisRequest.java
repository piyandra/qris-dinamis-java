package com.belajarspring.tokoonline.qrcodegenerator.Model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrisRequest {

    private String qr;
    private String amount;
}
