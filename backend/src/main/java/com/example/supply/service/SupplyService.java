package com.example.supply.service;

import com.example.supply.dto.SupplyRequest;
import com.example.supply.entity.Supply;
import com.example.supply.mapper.SupplyMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 補給品管理のビジネスロジックを提供するサービスクラス
 *
 * <p>このサービスクラスは補給品のCRUD操作、ファイルのインポート/エクスポート処理など、
 * 補給品に関連する業務ロジックを実装します。
 * すべてのパブリックメソッドはトランザクション管理されています。</p>
 *
 * <p>主な機能:
 * <ul>
 *   <li>補給品の検索、登録、更新、削除</li>
 *   <li>カテゴリ別の補給品検索</li>
 *   <li>CSVファイルからの一括インポート</li>
 *   <li>Excel形式でのエクスポート</li>
 * </ul>
 * </p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 * @see Supply
 * @see SupplyMapper
 * @see SupplyRequest
 */
@Service
@Transactional
@Slf4j
public class SupplyService {

    /** 補給品データアクセスマッパー */
    private final SupplyMapper supplyMapper;

    /**
     * コンストラクタ
     *
     * @param supplyMapper 補給品マッパー（自動インジェクション）
     */
    public SupplyService(SupplyMapper supplyMapper) {
        this.supplyMapper = supplyMapper;
    }

    /**
     * 全ての補給品を取得します。
     *
     * @return 補給品のリスト（登録データがない場合は空のリスト）
     */
    public List<Supply> getAllSupplies() {
        log.debug("Fetching all supplies");
        List<Supply> supplies = supplyMapper.findAll();
        log.debug("Found {} supplies", supplies.size());
        return supplies;
    }

    /**
     * 指定されたIDの補給品を取得します。
     *
     * @param id 補給品ID
     * @return 補給品情報、該当するデータが存在しない場合はnull
     */
    public Supply getSupplyById(Long id) {
        log.debug("Fetching supply by id: {}", id);
        Supply supply = supplyMapper.findById(id);
        if (supply == null) {
            log.debug("Supply not found: id={}", id);
        } else {
            log.debug("Found supply: id={}, name={}", supply.getId(), supply.getName());
        }
        return supply;
    }

    /**
     * 新しい補給品を登録します。
     *
     * <p>リクエストオブジェクトから補給品エンティティを生成し、データベースに登録します。
     * 登録後、自動生成されたIDが設定された補給品情報を返します。</p>
     *
     * @param request 補給品登録リクエスト（名称、数量、単価、カテゴリを含む）
     * @return 登録された補給品情報（IDを含む）
     */
    public Supply createSupply(SupplyRequest request) {
        log.info("Creating supply: name={}, category={}, quantity={}, unitPrice={}",
                request.getName(), request.getCategory(), request.getQuantity(), request.getUnitPrice());

        Supply supply = new Supply();
        supply.setName(request.getName());
        supply.setQuantity(request.getQuantity());
        supply.setUnitPrice(request.getUnitPrice());
        supply.setCategory(request.getCategory());

        supplyMapper.insert(supply);
        log.info("Supply created successfully: id={}, name={}", supply.getId(), supply.getName());
        return supply;
    }

    /**
     * 既存の補給品情報を更新します。
     *
     * <p>指定されたIDの補給品が存在する場合、リクエスト情報で更新します。</p>
     *
     * @param id 更新対象の補給品ID
     * @param request 更新する補給品情報
     * @return 更新後の補給品情報
     * @throws RuntimeException 指定されたIDの補給品が存在しない場合
     */
    public Supply updateSupply(Long id, SupplyRequest request) {
        log.info("Updating supply: id={}, name={}, category={}", id, request.getName(), request.getCategory());

        Supply supply = supplyMapper.findById(id);
        if (supply == null) {
            log.warn("Update failed - Supply not found: id={}", id);
            throw new RuntimeException("Supply not found with id: " + id);
        }

        String oldName = supply.getName();
        supply.setName(request.getName());
        supply.setQuantity(request.getQuantity());
        supply.setUnitPrice(request.getUnitPrice());
        supply.setCategory(request.getCategory());

        supplyMapper.update(supply);
        log.info("Supply updated successfully: id={}, oldName={}, newName={}", id, oldName, supply.getName());
        return supply;
    }

    /**
     * 指定されたIDの補給品を削除します。
     *
     * @param id 削除対象の補給品ID
     * @throws RuntimeException 指定されたIDの補給品が存在しない場合
     */
    public void deleteSupply(Long id) {
        log.info("Deleting supply: id={}", id);

        Supply supply = supplyMapper.findById(id);
        if (supply == null) {
            log.warn("Delete failed - Supply not found: id={}", id);
            throw new RuntimeException("Supply not found with id: " + id);
        }

        String name = supply.getName();
        supplyMapper.delete(id);
        log.info("Supply deleted successfully: id={}, name={}", id, name);
    }

    /**
     * 指定されたカテゴリの補給品を取得します。
     *
     * @param category カテゴリ名
     * @return カテゴリに一致する補給品のリスト（該当データがない場合は空のリスト）
     */
    public List<Supply> getSuppliesByCategory(String category) {
        return supplyMapper.findByCategory(category);
    }

    /**
     * CSVファイルから補給品情報を一括インポートします。
     *
     * <p>現在未実装のため、このメソッドを呼び出すとUnsupportedOperationExceptionがスローされます。
     * 将来的には、CSVファイルを解析して複数の補給品を一括登録する機能を実装予定です。</p>
     *
     * @param file インポート対象のCSVファイル
     * @throws UnsupportedOperationException このメソッドは未実装です
     */
    // TODO: Implement CSV import functionality
    public void importFromCsv(MultipartFile file) {
        throw new UnsupportedOperationException("CSV import not implemented yet");
    }

    /**
     * 全ての補給品情報をExcel形式でエクスポートします。
     *
     * <p>Apache POIライブラリを使用して、システムに登録されている全補給品情報を
     * Excel形式（.xlsx）のバイナリデータとして生成します。
     * エクスポートされるデータには、ID、補給品名、数量、単価、カテゴリ、登録日時、更新日時が含まれます。</p>
     *
     * @return Excelファイルのバイナリデータ（byte配列）
     * @throws RuntimeException Excelファイルの生成中にIOエラーが発生した場合
     */
    public byte[] exportToExcel() {
        log.info("Starting Excel export");
        List<Supply> supplies = supplyMapper.findAll();
        log.debug("Exporting {} supplies to Excel", supplies.size());

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
            byte[] result = out.toByteArray();
            log.info("Excel export completed successfully: {} bytes", result.length);
            return result;

        } catch (IOException e) {
            log.error("Excel export failed", e);
            throw new RuntimeException("Failed to export Excel file", e);
        }
    }
}
