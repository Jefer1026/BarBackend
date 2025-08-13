package com.jogdev.barbackend.bar.persistence.repository;

import com.jogdev.barbackend.bar.dto.ProductSalesReportDto;
import com.jogdev.barbackend.bar.persistence.entity.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {


    @Query(value = "CALL get_product_sales_report(:name, :start, :end);", nativeQuery = true)
    List<ProductSalesReportDto> callSalesReportProcedure(
            @Param("name") String name,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}