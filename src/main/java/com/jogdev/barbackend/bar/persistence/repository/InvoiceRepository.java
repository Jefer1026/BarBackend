package com.jogdev.barbackend.bar.persistence.repository;

import com.jogdev.barbackend.bar.persistence.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
