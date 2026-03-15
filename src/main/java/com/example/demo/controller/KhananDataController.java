package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.KhananDataService;
import com.example.demo.dto.VehicleSummary;
import com.example.demo.dto.TripDetail;
import com.example.demo.dto.PaginatedResponse;

import java.util.List;

@RestController
@RequestMapping("/api/khanan-data")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class KhananDataController {

    private final KhananDataService khananDataService;

    public KhananDataController(KhananDataService khananDataService) {
        this.khananDataService = khananDataService;
    }

    @GetMapping("/vehicles")
    public PaginatedResponse<VehicleSummary> getVehicleSummaries(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VehicleSummary> vehiclePage = khananDataService.getVehicleSummariesFromKhanaDataTable(pageable);
        return new PaginatedResponse<>(
            vehiclePage.getContent(),
            vehiclePage.getNumber(),
            vehiclePage.getSize(),
            vehiclePage.getTotalElements(),
            vehiclePage.getTotalPages(),
            vehiclePage.isFirst(),
            vehiclePage.isLast(),
            vehiclePage.hasNext(),
            vehiclePage.hasPrevious()
        );
    }

    @GetMapping("/vehicles/{vehicleRegNo}/trips")
    public PaginatedResponse<TripDetail> getTripDetails(@PathVariable String vehicleRegNo,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return khananDataService.getTripDetailsByVehicle(vehicleRegNo, pageable);
    }

}
