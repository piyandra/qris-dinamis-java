package com.belajarspring.tokoonline.qrcodegenerator.Service;

import org.springframework.stereotype.Component;

@Component
public class ConvertCRC16 {

    public String convertCRC16(String str) {
        int crc = 0xFFFF;
        int strlen = str.length();

        for (int c = 0; c < strlen; c++) {
            crc ^= str.charAt(c) << 8;
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ 0x1021;
                } else {
                    crc = crc << 1;
                }
            }
        }

        int hex = crc & 0xFFFF;
        String hexString = Integer.toHexString(hex).toUpperCase();

        if (hexString.length() == 3) {
            hexString = "0" + hexString;
        }

        return hexString;
    }
}
