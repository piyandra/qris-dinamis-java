package com.belajarspring.tokoonline.qrcodegenerator.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrisResponse {

    private String qr;
    private String amount;
    private byte[] images;
}
