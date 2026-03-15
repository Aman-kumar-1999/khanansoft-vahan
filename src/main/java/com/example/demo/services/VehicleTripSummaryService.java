package com.example.demo.services;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.entity.VehicleTripSummary;
import com.example.demo.repository.VehicleTripSummaryRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class VehicleTripSummaryService {

    @Autowired
    private VehicleTripSummaryRepo vehicleTripSummaryRepo;

    public void saveVehicleTripSummary(VehicleTripSummary summary) {
        vehicleTripSummaryRepo.save(summary);
    }

    public VehicleTripSummary getVehicleTripSummary(String vehicleRegNo) {
        return vehicleTripSummaryRepo.findById(vehicleRegNo).orElse(null);
    }

    // find all summaries by Pagination For API
    public Page<VehicleTripSummary> getAllVehicleTripSummaries(Pageable pageable) {
        return vehicleTripSummaryRepo.findAll(pageable);
    }


    
}
