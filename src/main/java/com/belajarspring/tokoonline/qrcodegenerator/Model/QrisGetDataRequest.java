package com.belajarspring.tokoonline.qrcodegenerator.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrisGetDataRequest {

    private String uuid;
}
