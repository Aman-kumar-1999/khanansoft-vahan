package com.example.demo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.KhananData;
import java.util.List;

@Repository
public interface KhananDataRepo extends MongoRepository<KhananData, ObjectId> {

    public KhananData findById(String id);

    public void deleteById(String id);

    public boolean existsById(String id);

    public KhananData findByVehicleRegNo(String vehicleRegNo);

    public boolean existsByVehicleRegNo(String vehicleRegNo);

    // Single field filters
    public List<KhananData> findByDistrict(String district);

    public List<KhananData> findByConsignerName(String consignerName);

    public List<KhananData> findByMineralName(String mineralName);

    public List<KhananData> findByMineralCategory(String mineralCategory);

    public List<KhananData> findByCheckStatus(String checkStatus);

    public List<KhananData> findBySourceType(String sourceType);

    public List<KhananData> findByConsigneeName(String consigneeName);

    public List<KhananData> findByChallanNo(String challanNo);

    public List<KhananData> findByDestination(String destination);

    public List<KhananData> findByQuantity(String quantity);

    public List<KhananData> findByUnit(String unit);

    public List<KhananData> findByDate(String date);

    public List<KhananData> findByTransportedDate(String transportedDate);

    // Multiple filter combinations
    public List<KhananData> findByDistrictAndConsignerName(String district, String consignerName);

    public List<KhananData> findByDistrictAndMineralName(String district, String mineralName);

    public List<KhananData> findByDistrictAndCheckStatus(String district, String checkStatus);

    public List<KhananData> findByMineralNameAndCheckStatus(String mineralName, String checkStatus);

    public List<KhananData> findByConsignerNameAndMineralName(String consignerName, String mineralName);

    public List<KhananData> findByVehicleRegNoAndCheckStatus(String vehicleRegNo, String checkStatus);

    public List<KhananData> findByVehicleRegNoAndDestination(String vehicleRegNo, String destination);

    // Three field filters
    public List<KhananData> findByDistrictAndMineralNameAndCheckStatus(String district, String mineralName, String checkStatus);

    public List<KhananData> findByDistrictAndConsignerNameAndMineralName(String district, String consignerName, String mineralName);

    public List<KhananData> findByConsignerNameAndMineralNameAndCheckStatus(String consignerName, String mineralName, String checkStatus);

    public List<KhananData> findByVehicleRegNoAndDestinationAndCheckStatus(String vehicleRegNo, String destination, String checkStatus);

    // ==================== PAGINATION SUPPORT ====================

    // Single field pagination
    public Page<KhananData> findByDistrict(String district, Pageable pageable);

    public Page<KhananData> findByConsignerName(String consignerName, Pageable pageable);

    public Page<KhananData> findByMineralName(String mineralName, Pageable pageable);

    public Page<KhananData> findByMineralCategory(String mineralCategory, Pageable pageable);

    public Page<KhananData> findByCheckStatus(String checkStatus, Pageable pageable);

    public Page<KhananData> findBySourceType(String sourceType, Pageable pageable);

    public Page<KhananData> findByConsigneeName(String consigneeName, Pageable pageable);

    public Page<KhananData> findByChallanNo(String challanNo, Pageable pageable);

    public Page<KhananData> findByDestination(String destination, Pageable pageable);

    public Page<KhananData> findByQuantity(String quantity, Pageable pageable);

    public Page<KhananData> findByUnit(String unit, Pageable pageable);

    public Page<KhananData> findByDate(String date, Pageable pageable);

    public Page<KhananData> findByTransportedDate(String transportedDate, Pageable pageable);

    public Page<KhananData> findByVehicleRegNo(String vehicleRegNo, Pageable pageable);

    // Multiple field pagination (2 fields)
    public Page<KhananData> findByDistrictAndConsignerName(String district, String consignerName, Pageable pageable);

    public Page<KhananData> findByDistrictAndMineralName(String district, String mineralName, Pageable pageable);

    public Page<KhananData> findByDistrictAndCheckStatus(String district, String checkStatus, Pageable pageable);

    public Page<KhananData> findByMineralNameAndCheckStatus(String mineralName, String checkStatus, Pageable pageable);

    public Page<KhananData> findByConsignerNameAndMineralName(String consignerName, String mineralName, Pageable pageable);

    public Page<KhananData> findByVehicleRegNoAndCheckStatus(String vehicleRegNo, String checkStatus, Pageable pageable);

    public Page<KhananData> findByVehicleRegNoAndDestination(String vehicleRegNo, String destination, Pageable pageable);

    // Multiple field pagination (3 fields)
    public Page<KhananData> findByDistrictAndMineralNameAndCheckStatus(String district, String mineralName, String checkStatus, Pageable pageable);

    public Page<KhananData> findByDistrictAndConsignerNameAndMineralName(String district, String consignerName, String mineralName, Pageable pageable);

    public Page<KhananData> findByConsignerNameAndMineralNameAndCheckStatus(String consignerName, String mineralName, String checkStatus, Pageable pageable);

    public Page<KhananData> findByVehicleRegNoAndDestinationAndCheckStatus(String vehicleRegNo, String destination, String checkStatus, Pageable pageable);

}
