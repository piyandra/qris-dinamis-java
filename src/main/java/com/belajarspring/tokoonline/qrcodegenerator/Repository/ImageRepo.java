package com.belajarspring.tokoonline.qrcodegenerator.Repository;

import com.belajarspring.tokoonline.qrcodegenerator.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, String> {
}
