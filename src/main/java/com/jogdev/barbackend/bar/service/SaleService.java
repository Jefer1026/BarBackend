package com.jogdev.barbackend.bar.service;

import com.jogdev.barbackend.bar.dto.SaleItemRequest;
import com.jogdev.barbackend.bar.persistence.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaleService {

    Page<Invoice> getAllInvoices(Pageable pageable);

    Invoice generateInvoice(List<SaleItemRequest> saleItems);


}
