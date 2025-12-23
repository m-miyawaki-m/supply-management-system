package com.example.supply.controller;

import com.example.supply.dto.SupplyRequest;
import com.example.supply.entity.Supply;
import com.example.supply.service.SupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 補給品管理のREST APIコントローラー
 *
 * <p>このコントローラーは補給品のCRUD操作、ファイルインポート/エクスポート機能を提供します。
 * すべてのエンドポイントは{@code /api/supplies}配下に配置されています。</p>
 *
 * <p>主な機能:
 * <ul>
 *   <li>補給品の一覧取得、詳細取得</li>
 *   <li>補給品の登録、更新、削除</li>
 *   <li>CSVファイルからの一括インポート</li>
 *   <li>Excelファイルへのエクスポート</li>
 * </ul>
 * </p>
 *
 * @author Supply Management Team
 * @version 1.0.0
 * @since 2024-12-17
 * @see Supply
 * @see SupplyService
 * @see SupplyRequest
 */
@RestController
@RequestMapping("/api/supplies")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Supply Management", description = "補給品管理API")
public class SupplyController {

    /** 補給品サービス */
    private final SupplyService supplyService;

    /**
     * コンストラクタ
     *
     * @param supplyService 補給品サービス（自動インジェクション）
     */
    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    /**
     * 補給品一覧を取得します。
     *
     * <p>システムに登録されている全ての補給品情報を取得します。
     * 補給品が存在しない場合は空のリストを返します。</p>
     *
     * @return 補給品のリストを含むレスポンスエンティティ（HTTP 200 OK）
     */
    @GetMapping
    @Operation(summary = "補給品一覧取得", description = "全ての補給品を取得します")
    public ResponseEntity<List<Supply>> getAllSupplies() {
        List<Supply> supplies = supplyService.getAllSupplies();
        return ResponseEntity.ok(supplies);
    }

    /**
     * 指定されたIDの補給品を取得します。
     *
     * <p>指定されたIDに一致する補給品の詳細情報を取得します。
     * 該当する補給品が存在しない場合はHTTP 404 Not Foundを返します。</p>
     *
     * @param id 補給品ID
     * @return 補給品情報を含むレスポンスエンティティ（HTTP 200 OK）、
     *         または補給品が見つからない場合（HTTP 404 Not Found）
     */
    @GetMapping("/{id}")
    @Operation(summary = "補給品詳細取得", description = "指定されたIDの補給品を取得します")
    public ResponseEntity<Supply> getSupplyById(@PathVariable Long id) {
        Supply supply = supplyService.getSupplyById(id);
        if (supply == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(supply);
    }

    /**
     * 新しい補給品を登録します。
     *
     * <p>リクエストボディに含まれる補給品情報を基に、新しい補給品をシステムに登録します。
     * 登録に成功した場合、生成されたIDを含む補給品情報を返します。</p>
     *
     * @param request 補給品登録リクエスト（名称、数量、単価などの情報を含む）
     * @return 登録された補給品情報を含むレスポンスエンティティ（HTTP 201 Created）
     */
    @PostMapping
    @Operation(summary = "補給品登録", description = "新しい補給品を登録します")
    public ResponseEntity<Supply> createSupply(@RequestBody SupplyRequest request) {
        Supply supply = supplyService.createSupply(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(supply);
    }

    /**
     * 指定されたIDの補給品を更新します。
     *
     * <p>既存の補給品情報を、リクエストボディに含まれる新しい情報で更新します。
     * 該当する補給品が存在しない場合はHTTP 404 Not Foundを返します。</p>
     *
     * @param id 更新対象の補給品ID
     * @param request 更新する補給品情報を含むリクエスト
     * @return 更新された補給品情報を含むレスポンスエンティティ（HTTP 200 OK）、
     *         または補給品が見つからない場合（HTTP 404 Not Found）
     */
    @PutMapping("/{id}")
    @Operation(summary = "補給品更新", description = "指定されたIDの補給品を更新します")
    public ResponseEntity<Supply> updateSupply(@PathVariable Long id, @RequestBody SupplyRequest request) {
        try {
            Supply supply = supplyService.updateSupply(id, request);
            return ResponseEntity.ok(supply);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 指定されたIDの補給品を削除します。
     *
     * <p>指定されたIDに一致する補給品をシステムから削除します。
     * 削除に成功した場合はHTTP 204 No Contentを返します。
     * 該当する補給品が存在しない場合はHTTP 404 Not Foundを返します。</p>
     *
     * @param id 削除対象の補給品ID
     * @return 削除成功時は空のレスポンスエンティティ（HTTP 204 No Content）、
     *         または補給品が見つからない場合（HTTP 404 Not Found）
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "補給品削除", description = "指定されたIDの補給品を削除します")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        try {
            supplyService.deleteSupply(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * CSVファイルから補給品を一括インポートします。
     *
     * <p>アップロードされたCSVファイルを解析し、複数の補給品情報を一括でシステムに登録します。
     * インポート処理中にエラーが発生した場合は、HTTP 500 Internal Server Errorと
     * エラーメッセージを返します。</p>
     *
     * @param file アップロードされたCSVファイル（補給品情報を含む）
     * @return インポート成功時は成功メッセージ（HTTP 200 OK）、
     *         またはインポート失敗時はエラーメッセージ（HTTP 500 Internal Server Error）
     */
    @PostMapping("/import")
    @Operation(summary = "CSVインポート", description = "CSVファイルから補給品をインポートします")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        try {
            supplyService.importFromCsv(file);
            return ResponseEntity.ok("Import successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Import failed: " + e.getMessage());
        }
    }

    /**
     * 補給品一覧をExcelファイルとしてエクスポートします。
     *
     * <p>システムに登録されている全ての補給品情報をExcel形式（.xlsx）で出力します。
     * ファイルはHTTPレスポンスボディとして返され、クライアント側でダウンロード可能です。
     * エクスポート処理中にエラーが発生した場合は、HTTP 500 Internal Server Errorを返します。</p>
     *
     * @return Excelファイルのバイナリデータを含むレスポンスエンティティ（HTTP 200 OK）、
     *         またはエクスポート失敗時（HTTP 500 Internal Server Error）
     */
    @GetMapping("/export")
    @Operation(summary = "Excelエクスポート", description = "補給品一覧をExcelファイルとしてエクスポートします")
    public ResponseEntity<byte[]> exportExcel() {
        try {
            byte[] excelData = supplyService.exportToExcel();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=supplies.xlsx")
                    .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .body(excelData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
