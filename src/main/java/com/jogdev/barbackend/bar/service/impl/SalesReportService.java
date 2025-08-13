package com.jogdev.barbackend.bar.service.impl;

import com.jogdev.barbackend.bar.dto.ProductSalesReportDto;
import com.jogdev.barbackend.bar.persistence.repository.InvoiceDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesReportService {

    private final InvoiceDetailRepository invoiceDetailRepository;

    public ProductSalesReportDto getSalesReport(
            String productName,
            LocalDate start,
            LocalDate end) {


        List<ProductSalesReportDto> results =
                invoiceDetailRepository.callSalesReportProcedure(productName.toUpperCase(), start, end);

        if (results.isEmpty()) {
            throw new EntityNotFoundException("SalesReport not found");
        }

        return results.get(0);


    }
}
