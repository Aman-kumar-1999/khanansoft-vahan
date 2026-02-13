package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FilterCriteria;
import com.example.demo.dto.PaginatedResponse;
import com.example.demo.entity.KhananData;
import com.example.demo.services.KhananDataService;

@RestController
@RequestMapping("/api/khanan-data")
public class KhananDataController {

    @Autowired
    private KhananDataService khananDataService;

    // ==================== LOAD KHANANSOFT DATA BY FROM CURRENT DATE TO LAST DATE
    // WISE ENDPOINTS ====================

    // @GetMapping("/by-date-range")
    // public ResponseEntity<List<KhananData>> getKhananDataByDateRange(
    //         @RequestParam(defaultValue = "https://khanansoft.bihar.gov.in/portal/CitizenRpt/epassreportAllDist.aspx") String url,
    //         @RequestParam(defaultValue = "%23ctl00_MainContent_btnshow") String selector,
    //         @RequestParam(defaultValue = "%23ctl00_MainContent_txtDate1") String inputCssSelector,
    //         // @RequestParam(defaultValue = "") String inputCssSelectorData,
    //         @RequestParam String fromDate,
    //         @RequestParam String toDate) {
    //     List<KhananData> data = khananDataService.getKhananDataByDateRange(url, selector, inputCssSelector, fromDate, toDate);
    //     return new ResponseEntity<>(data, HttpStatus.OK);
    // }

    // ==================== CREATE ENDPOINTS ====================

    @PostMapping("/save")
    public ResponseEntity<KhananData> saveKhananData(@RequestBody KhananData khananData) {
        KhananData saved = khananDataService.saveKhananData(khananData);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/save-all")
    public ResponseEntity<List<KhananData>> saveAllKhananData(@RequestBody List<KhananData> khananDataList) {
        List<KhananData> saved = khananDataService.saveAllKhananData(khananDataList);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ==================== GET/READ ENDPOINTS ====================

    @GetMapping("/all")
    public ResponseEntity<List<KhananData>> getAllKhananData() {
        List<KhananData> data = khananDataService.getAllKhananData();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // ==================== SINGLE FIELD FILTER ENDPOINTS ====================

    @GetMapping("/filter/district")
    public ResponseEntity<List<KhananData>> filterByDistrict(@RequestParam String district) {
        List<KhananData> data = khananDataService.filterByDistrict(district);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/consigner-name")
    public ResponseEntity<List<KhananData>> filterByConsignerName(@RequestParam String consignerName) {
        List<KhananData> data = khananDataService.filterByConsignerName(consignerName);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/mineral-name")
    public ResponseEntity<List<KhananData>> filterByMineralName(@RequestParam String mineralName) {
        List<KhananData> data = khananDataService.filterByMineralName(mineralName);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/mineral-category")
    public ResponseEntity<List<KhananData>> filterByMineralCategory(@RequestParam String mineralCategory) {
        List<KhananData> data = khananDataService.filterByMineralCategory(mineralCategory);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/check-status")
    public ResponseEntity<List<KhananData>> filterByCheckStatus(@RequestParam String checkStatus) {
        List<KhananData> data = khananDataService.filterByCheckStatus(checkStatus);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/source-type")
    public ResponseEntity<List<KhananData>> filterBySourceType(@RequestParam String sourceType) {
        List<KhananData> data = khananDataService.filterBySourceType(sourceType);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/consignee-name")
    public ResponseEntity<List<KhananData>> filterByConsigneeName(@RequestParam String consigneeName) {
        List<KhananData> data = khananDataService.filterByConsigneeName(consigneeName);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/challan-no")
    public ResponseEntity<List<KhananData>> filterByChallanNo(@RequestParam String challanNo) {
        List<KhananData> data = khananDataService.filterByChallanNo(challanNo);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/destination")
    public ResponseEntity<List<KhananData>> filterByDestination(@RequestParam String destination) {
        List<KhananData> data = khananDataService.filterByDestination(destination);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/quantity")
    public ResponseEntity<List<KhananData>> filterByQuantity(@RequestParam String quantity) {
        List<KhananData> data = khananDataService.filterByQuantity(quantity);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/unit")
    public ResponseEntity<List<KhananData>> filterByUnit(@RequestParam String unit) {
        List<KhananData> data = khananDataService.filterByUnit(unit);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/date")
    public ResponseEntity<List<KhananData>> filterByDate(@RequestParam String date) {
        List<KhananData> data = khananDataService.filterByDate(date);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/transported-date")
    public ResponseEntity<List<KhananData>> filterByTransportedDate(@RequestParam String transportedDate) {
        List<KhananData> data = khananDataService.filterByTransportedDate(transportedDate);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/vehicle-reg-no")
    public ResponseEntity<List<KhananData>> filterByVehicleRegNo(@RequestParam String vehicleRegNo) {
        List<KhananData> data = khananDataService.filterByVehicleRegNo(vehicleRegNo);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // ==================== MULTIPLE FIELD FILTER ENDPOINTS ====================

    @PostMapping("/filter/multiple")
    public ResponseEntity<List<KhananData>> filterByMultipleCriteria(@RequestBody FilterCriteria criteria) {
        List<KhananData> data = khananDataService.filterByMultipleCriteria(criteria);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/district-consigner")
    public ResponseEntity<List<KhananData>> filterByDistrictAndConsigner(
            @RequestParam String district,
            @RequestParam String consignerName) {
        List<KhananData> data = khananDataService.filterByDistrictAndConsignerName(district, consignerName);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/district-mineral")
    public ResponseEntity<List<KhananData>> filterByDistrictAndMineral(
            @RequestParam String district,
            @RequestParam String mineralName) {
        List<KhananData> data = khananDataService.filterByDistrictAndMineralName(district, mineralName);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/district-status")
    public ResponseEntity<List<KhananData>> filterByDistrictAndStatus(
            @RequestParam String district,
            @RequestParam String checkStatus) {
        List<KhananData> data = khananDataService.filterByDistrictAndCheckStatus(district, checkStatus);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/mineral-status")
    public ResponseEntity<List<KhananData>> filterByMineralAndStatus(
            @RequestParam String mineralName,
            @RequestParam String checkStatus) {
        List<KhananData> data = khananDataService.filterByMineralNameAndCheckStatus(mineralName, checkStatus);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/consigner-mineral")
    public ResponseEntity<List<KhananData>> filterByConsignerAndMineral(
            @RequestParam String consignerName,
            @RequestParam String mineralName) {
        List<KhananData> data = khananDataService.filterByConsignerNameAndMineralName(consignerName, mineralName);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/vehicle-status")
    public ResponseEntity<List<KhananData>> filterByVehicleAndStatus(
            @RequestParam String vehicleRegNo,
            @RequestParam String checkStatus) {
        List<KhananData> data = khananDataService.filterByVehicleRegNoAndCheckStatus(vehicleRegNo, checkStatus);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/vehicle-destination")
    public ResponseEntity<List<KhananData>> filterByVehicleAndDestination(
            @RequestParam String vehicleRegNo,
            @RequestParam String destination) {
        List<KhananData> data = khananDataService.filterByVehicleRegNoAndDestination(vehicleRegNo, destination);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // ==================== THREE FIELD FILTER ENDPOINTS ====================

    @GetMapping("/filter/district-mineral-status")
    public ResponseEntity<List<KhananData>> filterByDistrictMineralAndStatus(
            @RequestParam String district,
            @RequestParam String mineralName,
            @RequestParam String checkStatus) {
        List<KhananData> data = khananDataService.filterByDistrictAndMineralNameAndCheckStatus(district, mineralName,
                checkStatus);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/district-consigner-mineral")
    public ResponseEntity<List<KhananData>> filterByDistrictConsignerAndMineral(
            @RequestParam String district,
            @RequestParam String consignerName,
            @RequestParam String mineralName) {
        List<KhananData> data = khananDataService.filterByDistrictAndConsignerNameAndMineralName(district,
                consignerName, mineralName);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/consigner-mineral-status")
    public ResponseEntity<List<KhananData>> filterByConsignerMineralAndStatus(
            @RequestParam String consignerName,
            @RequestParam String mineralName,
            @RequestParam String checkStatus) {
        List<KhananData> data = khananDataService.filterByConsignerNameAndMineralNameAndCheckStatus(consignerName,
                mineralName, checkStatus);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/filter/vehicle-destination-status")
    public ResponseEntity<List<KhananData>> filterByVehicleDestinationAndStatus(
            @RequestParam String vehicleRegNo,
            @RequestParam String destination,
            @RequestParam String checkStatus) {
        List<KhananData> data = khananDataService.filterByVehicleRegNoAndDestinationAndCheckStatus(vehicleRegNo,
                destination, checkStatus);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // ==================== UPDATE ENDPOINTS (RETURNS FILTERED DATA)
    // ====================

    @PutMapping("/update/{id}")
    public ResponseEntity<KhananData> updateKhananData(
            @PathVariable String id,
            @RequestBody KhananData khananData) {
        khananData.setId(id);
        KhananData updated = khananDataService.saveKhananData(khananData);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/update/{id}/with-filter")
    public ResponseEntity<KhananData> updateAndReturnRecord(
            @PathVariable String id,
            @RequestBody KhananData khananData) {
        khananData.setId(id);
        KhananData updated = khananDataService.saveKhananData(khananData);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/filter-update-multiple")
    public ResponseEntity<List<KhananData>> updateAndFilterByMultipleCriteria(
            @RequestBody FilterCriteria criteria) {
        List<KhananData> filteredData = khananDataService.filterByMultipleCriteria(criteria);
        return new ResponseEntity<>(filteredData, HttpStatus.OK);
    }

    // ==================== DELETE ENDPOINTS ====================

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteKhananData(@PathVariable String id) {
        khananDataService.deleteKhananDataById(id);
        return new ResponseEntity<>("Record deleted successfully", HttpStatus.OK);
    }

    // ==================== CHECK ENDPOINTS ====================

    @GetMapping("/check/vehicle-exists/{vehicleRegNo}")
    public ResponseEntity<Boolean> checkVehicleExists(@PathVariable String vehicleRegNo) {
        boolean exists = khananDataService.khananDataExistsByVehicleRegNo(vehicleRegNo);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // ==================== PAGINATION ENDPOINTS ====================

    @GetMapping("/paginated/all")
    public ResponseEntity<PaginatedResponse<KhananData>> getAllWithPagination(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.getAllKhananDataWithPagination(pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // ==================== SINGLE FIELD FILTER PAGINATION ENDPOINTS
    // ====================

    @GetMapping("/paginated/filter/district")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByDistrictPaginated(
            @RequestParam String district,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByDistrictWithPagination(district, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/consigner-name")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByConsignerNamePaginated(
            @RequestParam String consignerName,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByConsignerNameWithPagination(consignerName,
                pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/mineral-name")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByMineralNamePaginated(
            @RequestParam String mineralName,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByMineralNameWithPagination(mineralName, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/mineral-category")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByMineralCategoryPaginated(
            @RequestParam String mineralCategory,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByMineralCategoryWithPagination(mineralCategory,
                pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/check-status")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByCheckStatusPaginated(
            @RequestParam String checkStatus,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByCheckStatusWithPagination(checkStatus, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/source-type")
    public ResponseEntity<PaginatedResponse<KhananData>> filterBySourceTypePaginated(
            @RequestParam String sourceType,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterBySourceTypeWithPagination(sourceType, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/consignee-name")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByConsigneeNamePaginated(
            @RequestParam String consigneeName,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByConsigneeNameWithPagination(consigneeName,
                pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/challan-no")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByChallanNoPaginated(
            @RequestParam String challanNo,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByChallanNoWithPagination(challanNo, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/destination")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByDestinationPaginated(
            @RequestParam String destination,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByDestinationWithPagination(destination, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/quantity")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByQuantityPaginated(
            @RequestParam String quantity,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByQuantityWithPagination(quantity, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/unit")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByUnitPaginated(
            @RequestParam String unit,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByUnitWithPagination(unit, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/date")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByDatePaginated(
            @RequestParam String date,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByDateWithPagination(date, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/transported-date")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByTransportedDatePaginated(
            @RequestParam String transportedDate,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByTransportedDateWithPagination(transportedDate,
                pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/vehicle-reg-no")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByVehicleRegNoPaginated(
            @RequestParam String vehicleRegNo,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByVehicleRegNoWithPagination(vehicleRegNo,
                pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // ==================== MULTIPLE FIELD FILTER PAGINATION ENDPOINTS (2 FIELDS)
    // ====================

    @GetMapping("/paginated/filter/district-consigner")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByDistrictAndConsignerPaginated(
            @RequestParam String district,
            @RequestParam String consignerName,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByDistrictAndConsignerNameWithPagination(district,
                consignerName, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/district-mineral")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByDistrictAndMineralPaginated(
            @RequestParam String district,
            @RequestParam String mineralName,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByDistrictAndMineralNameWithPagination(district,
                mineralName, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/district-status")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByDistrictAndStatusPaginated(
            @RequestParam String district,
            @RequestParam String checkStatus,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByDistrictAndCheckStatusWithPagination(district,
                checkStatus, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/mineral-status")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByMineralAndStatusPaginated(
            @RequestParam String mineralName,
            @RequestParam String checkStatus,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService
                .filterByMineralNameAndCheckStatusWithPagination(mineralName, checkStatus, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/consigner-mineral")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByConsignerAndMineralPaginated(
            @RequestParam String consignerName,
            @RequestParam String mineralName,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService
                .filterByConsignerNameAndMineralNameWithPagination(consignerName, mineralName, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/vehicle-status")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByVehicleAndStatusPaginated(
            @RequestParam String vehicleRegNo,
            @RequestParam String checkStatus,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService
                .filterByVehicleRegNoAndCheckStatusWithPagination(vehicleRegNo, checkStatus, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/vehicle-destination")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByVehicleAndDestinationPaginated(
            @RequestParam String vehicleRegNo,
            @RequestParam String destination,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService
                .filterByVehicleRegNoAndDestinationWithPagination(vehicleRegNo, destination, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // ==================== MULTIPLE FIELD FILTER PAGINATION ENDPOINTS (3 FIELDS)
    // ====================

    @GetMapping("/paginated/filter/district-mineral-status")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByDistrictMineralAndStatusPaginated(
            @RequestParam String district,
            @RequestParam String mineralName,
            @RequestParam String checkStatus,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService
                .filterByDistrictAndMineralNameAndCheckStatusWithPagination(district, mineralName, checkStatus,
                        pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/district-consigner-mineral")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByDistrictConsignerAndMineralPaginated(
            @RequestParam String district,
            @RequestParam String consignerName,
            @RequestParam String mineralName,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService
                .filterByDistrictAndConsignerNameAndMineralNameWithPagination(district, consignerName, mineralName,
                        pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/consigner-mineral-status")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByConsignerMineralAndStatusPaginated(
            @RequestParam String consignerName,
            @RequestParam String mineralName,
            @RequestParam String checkStatus,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService
                .filterByConsignerNameAndMineralNameAndCheckStatusWithPagination(consignerName, mineralName,
                        checkStatus, pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/paginated/filter/vehicle-destination-status")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByVehicleDestinationAndStatusPaginated(
            @RequestParam String vehicleRegNo,
            @RequestParam String destination,
            @RequestParam String checkStatus,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService
                .filterByVehicleRegNoAndDestinationAndCheckStatusWithPagination(vehicleRegNo, destination, checkStatus,
                        pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // ==================== ADVANCED PAGINATION FILTER ENDPOINTS
    // ====================

    @PostMapping("/paginated/filter/multiple")
    public ResponseEntity<PaginatedResponse<KhananData>> filterByMultipleCriteriaPaginated(
            @RequestBody FilterCriteria criteria,
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        PaginatedResponse<KhananData> data = khananDataService.filterByMultipleCriteriaWithPagination(criteria,
                pageable);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
