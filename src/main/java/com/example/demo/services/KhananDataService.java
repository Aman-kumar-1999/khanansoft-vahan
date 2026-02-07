package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.FilterCriteria;
import com.example.demo.dto.PaginatedResponse;
import com.example.demo.entity.KhananData;
import com.example.demo.repository.KhananDataRepo;

@Service
public class KhananDataService {

    @Autowired
    private KhananDataRepo khananDataRepo;

    public KhananData saveKhananData(KhananData khananData) {
        // Add any processing logic here if needed

        return khananDataRepo.save(khananData);
    }

    public List<KhananData> saveAllKhananData(List<KhananData> khananDataList) {
        return khananDataRepo.saveAll(khananDataList);
    }

    public List<KhananData> getAllKhananData() {
        return khananDataRepo.findAll();
    }

    public void deleteKhananDataById(String id) {
        khananDataRepo.deleteById(id);
    }

    public boolean khananDataExistsByVehicleRegNo(String vehicleRegNo) {
        return khananDataRepo.existsByVehicleRegNo(vehicleRegNo);
    }

    // Single field filter methods
    public List<KhananData> filterByDistrict(String district) {
        return khananDataRepo.findByDistrict(district);
    }

    public List<KhananData> filterByConsignerName(String consignerName) {
        return khananDataRepo.findByConsignerName(consignerName);
    }

    public List<KhananData> filterByMineralName(String mineralName) {
        return khananDataRepo.findByMineralName(mineralName);
    }

    public List<KhananData> filterByMineralCategory(String mineralCategory) {
        return khananDataRepo.findByMineralCategory(mineralCategory);
    }

    public List<KhananData> filterByCheckStatus(String checkStatus) {
        return khananDataRepo.findByCheckStatus(checkStatus);
    }

    public List<KhananData> filterBySourceType(String sourceType) {
        return khananDataRepo.findBySourceType(sourceType);
    }

    public List<KhananData> filterByConsigneeName(String consigneeName) {
        return khananDataRepo.findByConsigneeName(consigneeName);
    }

    public List<KhananData> filterByChallanNo(String challanNo) {
        return khananDataRepo.findByChallanNo(challanNo);
    }

    public List<KhananData> filterByDestination(String destination) {
        return khananDataRepo.findByDestination(destination);
    }

    public List<KhananData> filterByQuantity(String quantity) {
        return khananDataRepo.findByQuantity(quantity);
    }

    public List<KhananData> filterByUnit(String unit) {
        return khananDataRepo.findByUnit(unit);
    }

    public List<KhananData> filterByDate(String date) {
        return khananDataRepo.findByDate(date);
    }

    public List<KhananData> filterByTransportedDate(String transportedDate) {
        return khananDataRepo.findByTransportedDate(transportedDate);
    }

    public List<KhananData> filterByVehicleRegNo(String vehicleRegNo) {
        KhananData result = khananDataRepo.findByVehicleRegNo(vehicleRegNo);
        if (result != null) {
            return java.util.Arrays.asList(result);
        }
        return java.util.Collections.emptyList();
    }

    // Multiple filter method - Dynamic filtering
    public List<KhananData> filterByMultipleCriteria(FilterCriteria criteria) {
        List<KhananData> result = khananDataRepo.findAll();

        // Apply filters dynamically based on non-null values
        if (criteria.getDistrict() != null && !criteria.getDistrict().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getDistrict().equalsIgnoreCase(criteria.getDistrict()))
                    .collect(Collectors.toList());
        }

        if (criteria.getConsignerName() != null && !criteria.getConsignerName().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getConsignerName().equalsIgnoreCase(criteria.getConsignerName()))
                    .collect(Collectors.toList());
        }

        if (criteria.getMineralName() != null && !criteria.getMineralName().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getMineralName().equalsIgnoreCase(criteria.getMineralName()))
                    .collect(Collectors.toList());
        }

        if (criteria.getMineralCategory() != null && !criteria.getMineralCategory().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getMineralCategory().equalsIgnoreCase(criteria.getMineralCategory()))
                    .collect(Collectors.toList());
        }

        if (criteria.getCheckStatus() != null && !criteria.getCheckStatus().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getCheckStatus().equalsIgnoreCase(criteria.getCheckStatus()))
                    .collect(Collectors.toList());
        }

        if (criteria.getVehicleRegNo() != null && !criteria.getVehicleRegNo().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getVehicleRegNo().equalsIgnoreCase(criteria.getVehicleRegNo()))
                    .collect(Collectors.toList());
        }

        if (criteria.getSourceType() != null && !criteria.getSourceType().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getSourceType().equalsIgnoreCase(criteria.getSourceType()))
                    .collect(Collectors.toList());
        }

        if (criteria.getConsigneeName() != null && !criteria.getConsigneeName().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getConsigneeName().equalsIgnoreCase(criteria.getConsigneeName()))
                    .collect(Collectors.toList());
        }

        if (criteria.getChallanNo() != null && !criteria.getChallanNo().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getChallanNo().equalsIgnoreCase(criteria.getChallanNo()))
                    .collect(Collectors.toList());
        }

        if (criteria.getDestination() != null && !criteria.getDestination().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getDestination().equalsIgnoreCase(criteria.getDestination()))
                    .collect(Collectors.toList());
        }

        if (criteria.getQuantity() != null && !criteria.getQuantity().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getQuantity().equalsIgnoreCase(criteria.getQuantity()))
                    .collect(Collectors.toList());
        }

        if (criteria.getUnit() != null && !criteria.getUnit().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getUnit().equalsIgnoreCase(criteria.getUnit()))
                    .collect(Collectors.toList());
        }

        if (criteria.getDate() != null && !criteria.getDate().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getDate().equalsIgnoreCase(criteria.getDate()))
                    .collect(Collectors.toList());
        }

        if (criteria.getTransportedDate() != null && !criteria.getTransportedDate().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getTransportedDate().equalsIgnoreCase(criteria.getTransportedDate()))
                    .collect(Collectors.toList());
        }

        return result;
    }

    // Specific two field filters
    public List<KhananData> filterByDistrictAndConsignerName(String district, String consignerName) {
        return khananDataRepo.findByDistrictAndConsignerName(district, consignerName);
    }

    public List<KhananData> filterByDistrictAndMineralName(String district, String mineralName) {
        return khananDataRepo.findByDistrictAndMineralName(district, mineralName);
    }

    public List<KhananData> filterByDistrictAndCheckStatus(String district, String checkStatus) {
        return khananDataRepo.findByDistrictAndCheckStatus(district, checkStatus);
    }

    public List<KhananData> filterByMineralNameAndCheckStatus(String mineralName, String checkStatus) {
        return khananDataRepo.findByMineralNameAndCheckStatus(mineralName, checkStatus);
    }

    public List<KhananData> filterByConsignerNameAndMineralName(String consignerName, String mineralName) {
        return khananDataRepo.findByConsignerNameAndMineralName(consignerName, mineralName);
    }

    public List<KhananData> filterByVehicleRegNoAndCheckStatus(String vehicleRegNo, String checkStatus) {
        return khananDataRepo.findByVehicleRegNoAndCheckStatus(vehicleRegNo, checkStatus);
    }

    public List<KhananData> filterByVehicleRegNoAndDestination(String vehicleRegNo, String destination) {
        return khananDataRepo.findByVehicleRegNoAndDestination(vehicleRegNo, destination);
    }

    // Specific three field filters
    public List<KhananData> filterByDistrictAndMineralNameAndCheckStatus(String district, String mineralName, String checkStatus) {
        return khananDataRepo.findByDistrictAndMineralNameAndCheckStatus(district, mineralName, checkStatus);
    }

    public List<KhananData> filterByDistrictAndConsignerNameAndMineralName(String district, String consignerName, String mineralName) {
        return khananDataRepo.findByDistrictAndConsignerNameAndMineralName(district, consignerName, mineralName);
    }

    public List<KhananData> filterByConsignerNameAndMineralNameAndCheckStatus(String consignerName, String mineralName, String checkStatus) {
        return khananDataRepo.findByConsignerNameAndMineralNameAndCheckStatus(consignerName, mineralName, checkStatus);
    }

    public List<KhananData> filterByVehicleRegNoAndDestinationAndCheckStatus(String vehicleRegNo, String destination, String checkStatus) {
        return khananDataRepo.findByVehicleRegNoAndDestinationAndCheckStatus(vehicleRegNo, destination, checkStatus);
    }

    // ==================== PAGINATION METHODS ====================

    // Helper method to convert Page to PaginatedResponse
    private <T> PaginatedResponse<T> convertPageToPaginatedResponse(Page<T> page) {
        return new PaginatedResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast(),
            page.hasNext(),
            page.hasPrevious()
        );
    }

    // Get all with pagination
    public PaginatedResponse<KhananData> getAllKhananDataWithPagination(Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findAll(pageable);
        return convertPageToPaginatedResponse(page);
    }

    // Single field pagination methods
    public PaginatedResponse<KhananData> filterByDistrictWithPagination(String district, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByDistrict(district, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByConsignerNameWithPagination(String consignerName, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByConsignerName(consignerName, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByMineralNameWithPagination(String mineralName, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByMineralName(mineralName, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByMineralCategoryWithPagination(String mineralCategory, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByMineralCategory(mineralCategory, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByCheckStatusWithPagination(String checkStatus, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByCheckStatus(checkStatus, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterBySourceTypeWithPagination(String sourceType, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findBySourceType(sourceType, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByConsigneeNameWithPagination(String consigneeName, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByConsigneeName(consigneeName, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByChallanNoWithPagination(String challanNo, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByChallanNo(challanNo, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByDestinationWithPagination(String destination, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByDestination(destination, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByQuantityWithPagination(String quantity, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByQuantity(quantity, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByUnitWithPagination(String unit, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByUnit(unit, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByDateWithPagination(String date, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByDate(date, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByTransportedDateWithPagination(String transportedDate, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByTransportedDate(transportedDate, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByVehicleRegNoWithPagination(String vehicleRegNo, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByVehicleRegNo(vehicleRegNo, pageable);
        return convertPageToPaginatedResponse(page);
    }

    // Multiple field pagination methods (2 fields)
    public PaginatedResponse<KhananData> filterByDistrictAndConsignerNameWithPagination(String district, String consignerName, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByDistrictAndConsignerName(district, consignerName, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByDistrictAndMineralNameWithPagination(String district, String mineralName, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByDistrictAndMineralName(district, mineralName, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByDistrictAndCheckStatusWithPagination(String district, String checkStatus, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByDistrictAndCheckStatus(district, checkStatus, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByMineralNameAndCheckStatusWithPagination(String mineralName, String checkStatus, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByMineralNameAndCheckStatus(mineralName, checkStatus, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByConsignerNameAndMineralNameWithPagination(String consignerName, String mineralName, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByConsignerNameAndMineralName(consignerName, mineralName, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByVehicleRegNoAndCheckStatusWithPagination(String vehicleRegNo, String checkStatus, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByVehicleRegNoAndCheckStatus(vehicleRegNo, checkStatus, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByVehicleRegNoAndDestinationWithPagination(String vehicleRegNo, String destination, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByVehicleRegNoAndDestination(vehicleRegNo, destination, pageable);
        return convertPageToPaginatedResponse(page);
    }

    // Multiple field pagination methods (3 fields)
    public PaginatedResponse<KhananData> filterByDistrictAndMineralNameAndCheckStatusWithPagination(String district, String mineralName, String checkStatus, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByDistrictAndMineralNameAndCheckStatus(district, mineralName, checkStatus, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByDistrictAndConsignerNameAndMineralNameWithPagination(String district, String consignerName, String mineralName, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByDistrictAndConsignerNameAndMineralName(district, consignerName, mineralName, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByConsignerNameAndMineralNameAndCheckStatusWithPagination(String consignerName, String mineralName, String checkStatus, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByConsignerNameAndMineralNameAndCheckStatus(consignerName, mineralName, checkStatus, pageable);
        return convertPageToPaginatedResponse(page);
    }

    public PaginatedResponse<KhananData> filterByVehicleRegNoAndDestinationAndCheckStatusWithPagination(String vehicleRegNo, String destination, String checkStatus, Pageable pageable) {
        Page<KhananData> page = khananDataRepo.findByVehicleRegNoAndDestinationAndCheckStatus(vehicleRegNo, destination, checkStatus, pageable);
        return convertPageToPaginatedResponse(page);
    }

    // Dynamic multi-criteria pagination
    public PaginatedResponse<KhananData> filterByMultipleCriteriaWithPagination(FilterCriteria criteria, Pageable pageable) {
        List<KhananData> result = khananDataRepo.findAll();

        // Apply filters dynamically based on non-null values
        if (criteria.getDistrict() != null && !criteria.getDistrict().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getDistrict().equalsIgnoreCase(criteria.getDistrict()))
                    .collect(Collectors.toList());
        }

        if (criteria.getConsignerName() != null && !criteria.getConsignerName().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getConsignerName().equalsIgnoreCase(criteria.getConsignerName()))
                    .collect(Collectors.toList());
        }

        if (criteria.getMineralName() != null && !criteria.getMineralName().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getMineralName().equalsIgnoreCase(criteria.getMineralName()))
                    .collect(Collectors.toList());
        }

        if (criteria.getMineralCategory() != null && !criteria.getMineralCategory().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getMineralCategory().equalsIgnoreCase(criteria.getMineralCategory()))
                    .collect(Collectors.toList());
        }

        if (criteria.getCheckStatus() != null && !criteria.getCheckStatus().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getCheckStatus().equalsIgnoreCase(criteria.getCheckStatus()))
                    .collect(Collectors.toList());
        }

        if (criteria.getVehicleRegNo() != null && !criteria.getVehicleRegNo().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getVehicleRegNo().equalsIgnoreCase(criteria.getVehicleRegNo()))
                    .collect(Collectors.toList());
        }

        if (criteria.getSourceType() != null && !criteria.getSourceType().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getSourceType().equalsIgnoreCase(criteria.getSourceType()))
                    .collect(Collectors.toList());
        }

        if (criteria.getConsigneeName() != null && !criteria.getConsigneeName().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getConsigneeName().equalsIgnoreCase(criteria.getConsigneeName()))
                    .collect(Collectors.toList());
        }

        if (criteria.getChallanNo() != null && !criteria.getChallanNo().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getChallanNo().equalsIgnoreCase(criteria.getChallanNo()))
                    .collect(Collectors.toList());
        }

        if (criteria.getDestination() != null && !criteria.getDestination().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getDestination().equalsIgnoreCase(criteria.getDestination()))
                    .collect(Collectors.toList());
        }

        if (criteria.getQuantity() != null && !criteria.getQuantity().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getQuantity().equalsIgnoreCase(criteria.getQuantity()))
                    .collect(Collectors.toList());
        }

        if (criteria.getUnit() != null && !criteria.getUnit().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getUnit().equalsIgnoreCase(criteria.getUnit()))
                    .collect(Collectors.toList());
        }

        if (criteria.getDate() != null && !criteria.getDate().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getDate().equalsIgnoreCase(criteria.getDate()))
                    .collect(Collectors.toList());
        }

        if (criteria.getTransportedDate() != null && !criteria.getTransportedDate().isEmpty()) {
            result = result.stream()
                    .filter(data -> data.getTransportedDate().equalsIgnoreCase(criteria.getTransportedDate()))
                    .collect(Collectors.toList());
        }

        // Apply pagination manually
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), result.size());
        
        List<KhananData> pageContent = result.subList(start, end);
        Page<KhananData> page = new PageImpl<>(pageContent, pageable, result.size());
        
        return convertPageToPaginatedResponse(page);
    }

}
