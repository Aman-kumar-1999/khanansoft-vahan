package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.VehicleTripSummary;



@Repository
public interface VehicleTripSummaryRepo extends MongoRepository<VehicleTripSummary, String> {
    // get all summaries by pagination For API
    //  Page<VehicleTripSummary> findByPage(Pageable pageable);
   
}
