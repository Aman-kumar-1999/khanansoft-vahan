package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PaginatedResponse;
import com.example.demo.dto.VehicleSummary;
import com.example.demo.entity.VehicleTripSummary;
import com.example.demo.services.KhananDataService;
import com.example.demo.services.VehicleTripSummaryService;

@RestController
@RequestMapping("/api/vehicle-trips-summary")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VehicleTripSummaryController {

    // private final KhananDataService khananDataService;

    @Autowired
    private VehicleTripSummaryService vehicleTripSummaryService;

    // public VehicleTripSummaryController(KhananDataService khananDataService) {
    //     // this.khananDataService = khananDataService;
    //     this.vehicleTripSummaryService = new VehicleTripSummaryService();
    // }

    // @GetMapping
    // public PaginatedResponse<VehicleSummary> getVehicleTripSummaries(
    //         @RequestParam(defaultValue = "0") int page,
    //         @RequestParam(defaultValue = "20") int size) {
    //     Pageable pageable = PageRequest.of(page, size);
    //     Page<VehicleSummary> summaryPage = khananDataService.getVehicleSummaries(pageable);
    //     return new PaginatedResponse<>(
    //             summaryPage.getContent(),
    //             summaryPage.getNumber(),
    //             summaryPage.getSize(),
    //             summaryPage.getTotalElements(),
    //             summaryPage.getTotalPages(),
    //             summaryPage.isFirst(),
    //             summaryPage.isLast(),
    //             summaryPage.hasNext(),
    //             summaryPage.hasPrevious()
    //     );
    // }
    
    @GetMapping
    public PaginatedResponse<VehicleTripSummary> getVehicleTripSummaries(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "20") int size
        
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VehicleTripSummary> vehiclePage = vehicleTripSummaryService.getAllVehicleTripSummaries(pageable);
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
    // @GetMapping("/load-vehicle-trip-reference-data")
    // public VehicleTripSummary referaceData() {
        
    //     List<VehicleTripSummary> summaryPage = khananDataService.refreshAllVehicleTripSummariesAndReturnDataToApi();
    //     // return new PaginatedResponse<>(
    //     //         summaryPage.getContent(),
    //     //         summaryPage.getNumber(),
    //     //         summaryPage.getSize(),
    //     //         summaryPage.getTotalElements(),
    //     //         summaryPage.getTotalPages(),
    //     //         summaryPage.isFirst(),
    //     //         summaryPage.isLast(),
    //     //         summaryPage.hasNext(),
    //     //         summaryPage.hasPrevious()
    //     // );
    //     return summaryPage.isEmpty() ? null : summaryPage.get(0);
    // }
}
