package com.belajarspring.tokoonline.qrcodegenerator.Repository;

import com.belajarspring.tokoonline.qrcodegenerator.Entity.Qris;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrisRepository extends JpaRepository<Qris, String> {
}
