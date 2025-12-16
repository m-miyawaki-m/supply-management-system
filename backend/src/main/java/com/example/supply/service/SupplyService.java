package com.example.supply.service;

import com.example.supply.dto.SupplyRequest;
import com.example.supply.entity.Supply;
import com.example.supply.mapper.SupplyMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class SupplyService {

    private final SupplyMapper supplyMapper;

    public SupplyService(SupplyMapper supplyMapper) {
        this.supplyMapper = supplyMapper;
    }

    public List<Supply> getAllSupplies() {
        return supplyMapper.findAll();
    }

    public Supply getSupplyById(Long id) {
        return supplyMapper.findById(id);
    }

    public Supply createSupply(SupplyRequest request) {
        Supply supply = new Supply();
        supply.setName(request.getName());
        supply.setQuantity(request.getQuantity());
        supply.setUnitPrice(request.getUnitPrice());
        supply.setCategory(request.getCategory());

        supplyMapper.insert(supply);
        return supply;
    }

    public Supply updateSupply(Long id, SupplyRequest request) {
        Supply supply = supplyMapper.findById(id);
        if (supply == null) {
            throw new RuntimeException("Supply not found with id: " + id);
        }

        supply.setName(request.getName());
        supply.setQuantity(request.getQuantity());
        supply.setUnitPrice(request.getUnitPrice());
        supply.setCategory(request.getCategory());

        supplyMapper.update(supply);
        return supply;
    }

    public void deleteSupply(Long id) {
        Supply supply = supplyMapper.findById(id);
        if (supply == null) {
            throw new RuntimeException("Supply not found with id: " + id);
        }
        supplyMapper.delete(id);
    }

    public List<Supply> getSuppliesByCategory(String category) {
        return supplyMapper.findByCategory(category);
    }

    // TODO: Implement CSV import functionality
    public void importFromCsv(MultipartFile file) {
        throw new UnsupportedOperationException("CSV import not implemented yet");
    }

    public byte[] exportToExcel() {
        List<Supply> supplies = supplyMapper.findAll();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("補給品一覧");

            // ヘッダー行を作成
            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {"ID", "補給品名", "数量", "単価", "カテゴリ", "登録日時", "更新日時"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // データ行を作成
            int rowNum = 1;
            for (Supply supply : supplies) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(supply.getId());
                row.createCell(1).setCellValue(supply.getName());
                row.createCell(2).setCellValue(supply.getQuantity());
                row.createCell(3).setCellValue(supply.getUnitPrice().doubleValue());
                row.createCell(4).setCellValue(supply.getCategory());
                row.createCell(5).setCellValue(supply.getCreatedAt().toString());
                row.createCell(6).setCellValue(supply.getUpdatedAt().toString());
            }

            // 列幅を自動調整
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Failed to export Excel file", e);
        }
    }
}
