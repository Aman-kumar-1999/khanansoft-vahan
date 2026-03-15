package com.example.demo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.KhananData;
import com.example.demo.dto.VehicleSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface KhananDataRepo extends MongoRepository<KhananData, ObjectId> {

    // Removed @Aggregation, will use MongoTemplate in service for optimization

    List<KhananData> findByVehicleRegNo(String vehicleRegNo);

    Page<KhananData> findByVehicleRegNo(String vehicleRegNo, Pageable pageable);

}
