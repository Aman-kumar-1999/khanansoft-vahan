package com.example.demo.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.entity.KhananData;
import com.example.demo.entity.VehicleTripSummary;
import com.example.demo.repository.KhananDataRepo;
import com.example.demo.repository.VehicleTripSummaryRepo;
import com.example.demo.dto.VehicleSummary;
import com.example.demo.dto.TripDetail;
import com.example.demo.dto.PaginatedResponse;

@Service
public class KhananDataService {

    private static final Logger logger = LoggerFactory.getLogger(KhananDataService.class);

    @Autowired
    private KhananDataRepo khananDataRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private VehicleTripSummaryRepo vehicleTripSummaryRepo;

    @PostConstruct
    public void createIndexes() {
        logger.info("Creating MongoDB indexes for optimization...");
        mongoTemplate.indexOps("khanan_data").createIndex(new Index().on("vehicleRegNo", org.springframework.data.domain.Sort.Direction.ASC));
        logger.info("Created index on vehicleRegNo");
        mongoTemplate.indexOps("khanan_data").createIndex(new Index().on("mineralName", org.springframework.data.domain.Sort.Direction.ASC));
        logger.info("Created index on mineralName");
        mongoTemplate.indexOps("khanan_data").createIndex(new Index().on("vehicleRegNo", org.springframework.data.domain.Sort.Direction.ASC).on("mineralName", org.springframework.data.domain.Sort.Direction.ASC));
        logger.info("Created compound index on vehicleRegNo and mineralName");
        // refreshAllVehicleTripSummariesAndReturnDataToApi();
        // logger.info("All indexes created successfully for MongoDB optimization");
        
    }
    

    // public List<KhananData> saveAllKhananData(List<KhananData> khananDataList) {
    //     return khananDataRepo.saveAll(khananDataList);
    // }

    // @Cacheable("vehicleSummaries")
    // public List<VehicleSummary> getVehicleSummaries() {
    //     return getVehicleSummaries(Pageable.unpaged()).getContent();
    // }

    public List<KhananData> saveAllKhananData(List<KhananData> khananDataList) {
        List<KhananData> saved = khananDataRepo.saveAll(khananDataList);

        // Update summary collection for affected vehicles
        List<String> vehicles = saved.stream()
            .map(KhananData::getVehicleRegNo)
            .distinct()
            .toList();
        if (!vehicles.isEmpty()) {
            refreshVehicleTripSummariesForVehicles(vehicles);
        }

        return saved;
    }

    // @Cacheable("vehicleSummaries")
    public Page<VehicleSummary> getVehicleSummariesFromKhanaDataTable(Pageable pageable) {
        Aggregation aggregation = Aggregation.newAggregation(
            context -> Document.parse("{ $group: { _id: '$vehicleRegNo', totalTrips: { $sum: 1 }, totalMTWeight: { $sum: { $toDouble: '$quantity' } }, sandTrips: { $sum: { $cond: { if: { $regexMatch: { input: '$mineralName', regex: 'sand', options: 'i' } }, then: 1, else: 0 } } }, sandMTWeight: { $sum: { $cond: { if: { $regexMatch: { input: '$mineralName', regex: 'sand', options: 'i' } }, then: { $toDouble: '$quantity' }, else: 0 } } }, stoneTrips: { $sum: { $cond: { if: { $regexMatch: { input: '$mineralName', regex: 'stone', options: 'i' } }, then: 1, else: 0 } } }, stoneMTWeight: { $sum: { $cond: { if: { $regexMatch: { input: '$mineralName', regex: 'stone', options: 'i' } }, then: { $toDouble: '$quantity' }, else: 0 } } } } }"),
            context -> Document.parse("{ $project: { vehicleRegNo: '$_id', totalTrips: 1, totalMTWeight: 1, sandTrips: 1, sandMTWeight: 1, stoneTrips: 1, stoneMTWeight: 1, _id: 0 } }")
        ).withOptions(AggregationOptions.builder().allowDiskUse(true).build());

        AggregationResults<VehicleSummary> results = mongoTemplate.aggregate(aggregation, "khanan_data", VehicleSummary.class);
        List<VehicleSummary> allSummaries = results.getMappedResults();

        // Manual pagination
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), allSummaries.size());
        List<VehicleSummary> pageContent = allSummaries.subList(start, end);
        return new PageImpl<>(pageContent, pageable, allSummaries.size());
    }

    

    @Async
    public CompletableFuture<Page<VehicleSummary>> getVehicleSummariesAsync(Pageable pageable) {
        return CompletableFuture.completedFuture(getVehicleSummaries(pageable));
    }
    public List<VehicleSummary> getVehicleSummaries() {
        return getVehicleSummaries(Pageable.unpaged()).getContent();
    }

    public Page<VehicleSummary> getVehicleSummaries(Pageable pageable) {
        Page<VehicleTripSummary> storePage = vehicleTripSummaryRepo.findAll(pageable);
        List<VehicleSummary> converted = storePage.getContent().stream()
            .map(this::toDto)
            .toList();
        return new PageImpl<>(converted, pageable, storePage.getTotalElements());
    }

    private VehicleSummary toDto(VehicleTripSummary summary) {
        VehicleSummary dto = new VehicleSummary();
        dto.setVehicleRegNo(summary.getVehicleRegNo());
        dto.setTotalTrips(summary.getTotalTrips());
        dto.setTotalMTWeight(summary.getTotalMTWeight());
        dto.setSandTrips(summary.getSandTrips());
        dto.setSandMTWeight(summary.getSandMTWeight());
        dto.setStoneTrips(summary.getStoneTrips());
        dto.setStoneMTWeight(summary.getStoneMTWeight());
        return dto;
    }

    // @Async
    // public CompletableFuture<Page<VehicleSummary>> getVehicleSummariesAsync(Pageable pageable) {
    //     return CompletableFuture.completedFuture(getVehicleSummaries(pageable));
    // }

    public void refreshVehicleTripSummariesForVehicles(List<String> vehicleRegNos) {
        if (vehicleRegNos == null || vehicleRegNos.isEmpty()) {
            return;
        }

        // Ensure we don't run aggregation for duplicates
        Set<String> uniqueVehicles = new HashSet<>(vehicleRegNos);
        List<VehicleTripSummary> summaries = aggregateVehicleSummaries(uniqueVehicles);
        if (!summaries.isEmpty()) {
            vehicleTripSummaryRepo.saveAll(summaries);
            logger.info("Updated {} vehicle trip summaries", summaries.size());
        }
    }

    public void refreshAllVehicleTripSummaries() {
        List<VehicleTripSummary> summaries = aggregateVehicleSummaries(null);
        if (!summaries.isEmpty()) {
            vehicleTripSummaryRepo.deleteAll();
            vehicleTripSummaryRepo.saveAll(summaries);
            logger.info("Refreshed all vehicle trip summaries (count={})", summaries.size());
        }
    }
    public List<VehicleTripSummary> refreshAllVehicleTripSummariesAndReturnDataToApi() {
        List<VehicleTripSummary> summaries = aggregateVehicleSummaries(null);
        if (!summaries.isEmpty()) {
            vehicleTripSummaryRepo.deleteAll();
            vehicleTripSummaryRepo.saveAll(summaries);
            logger.info("Refreshed all vehicle trip summaries (count={})", summaries.size());
        }
        return summaries;
    }

    private List<VehicleTripSummary> aggregateVehicleSummaries(Set<String> vehicleRegNos) {
        // List<org.springframework.data.mongodb.core.aggregation.AggregationOperation> pipeline = new ArrayList<>();

        // if (vehicleRegNos != null && !vehicleRegNos.isEmpty()) {
        //     pipeline.add(context -> new Document("$match",
        //             new Document("vehicleRegNo", new Document("$in", vehicleRegNos))));
        // }

        // pipeline.add(context -> Document.parse("{ $group: { _id: '$vehicleRegNo', totalTrips: { $sum: 1 }, totalMTWeight: { $sum: { $toDouble: '$quantity' } }, sandTrips: { $sum: { $cond: { if: { $regexMatch: { input: '$mineralName', regex: 'sand', options: 'i' } }, then: 1, else: 0 } } }, sandMTWeight: { $sum: { $cond: { if: { $regexMatch: { input: '$mineralName', regex: 'sand', options: 'i' } }, then: { $toDouble: '$quantity' }, else: 0 } } }, stoneTrips: { $sum: { $cond: { if: { $regexMatch: { input: '$mineralName', regex: 'stone', options: 'i' } }, then: 1, else: 0 } } }, stoneMTWeight: { $sum: { $cond: { if: { $regexMatch: { input: '$mineralName', regex: 'stone', options: 'i' } }, then: { $toDouble: '$quantity' }, else: 0 } } } } }"));
        // pipeline.add(context -> Document.parse("{ $project: { vehicleRegNo: '$_id', totalTrips: 1, totalMTWeight: 1, sandTrips: 1, sandMTWeight: 1, stoneTrips: 1, stoneMTWeight: 1, _id: 0 } }"));

        // Aggregation aggregation = Aggregation.newAggregation(pipeline).withOptions(AggregationOptions.builder().allowDiskUse(true).build());
        
        Aggregation aggregation = Aggregation.newAggregation(

            context -> Document.parse(
                "{ $group: { " +
                "_id: '$vehicleRegNo', " +
                "totalTrips: { $sum: 1 }, " +
                "totalMTWeight: { $sum: { $toDouble: '$quantity' } }, " +

                "sandTrips: { $sum: { $cond: [ " +
                "{ $regexMatch: { input: '$mineralName', regex: 'sand', options: 'i' } }, 1, 0 ] } }, " +

                "sandMTWeight: { $sum: { $cond: [ " +
                "{ $regexMatch: { input: '$mineralName', regex: 'sand', options: 'i' } }, { $toDouble: '$quantity' }, 0 ] } }, " +

                "stoneTrips: { $sum: { $cond: [ " +
                "{ $regexMatch: { input: '$mineralName', regex: 'stone', options: 'i' } }, 1, 0 ] } }, " +

                "stoneMTWeight: { $sum: { $cond: [ " +
                "{ $regexMatch: { input: '$mineralName', regex: 'stone', options: 'i' } }, { $toDouble: '$quantity' }, 0 ] } } " +

                "} }"
            ),

            context -> Document.parse(
                "{ $project: { " +
                "vehicleRegNo: '$_id', " +
                "totalTrips: 1, " +
                "totalMTWeight: 1, " +
                "sandTrips: 1, " +
                "sandMTWeight: 1, " +
                "stoneTrips: 1, " +
                "stoneMTWeight: 1, " +
                "_id: 0 } }"
            ),

            context -> Document.parse(
                "{ $merge: { " +
                "into: 'vehicle_trip_summary', " +
                "on: 'vehicleRegNo', " +
                "whenMatched: 'replace', " +
                "whenNotMatched: 'insert' " +
                "} }"
            )

        ).withOptions(AggregationOptions.builder().allowDiskUse(true).build());

        
        
        AggregationResults<VehicleTripSummary> results = mongoTemplate.aggregate(aggregation, "khanan_data", VehicleTripSummary.class);
        return results.getMappedResults();
    }

    @Scheduled(fixedDelayString = "PT30M")
    public void scheduledRefreshSummaries() {
        logger.info("Scheduled refresh of vehicle trip summaries started");
        refreshAllVehicleTripSummaries();
    }

    public PaginatedResponse<TripDetail> getTripDetailsByVehicle(String vehicleRegNo, Pageable pageable) {
        Page<KhananData> tripsPage = khananDataRepo.findByVehicleRegNo(vehicleRegNo, pageable);
        List<TripDetail> tripDetails = tripsPage.getContent().stream()
            .map(this::convertToTripDetail)
            .collect(Collectors.toList());
        return new PaginatedResponse<>(
            tripDetails,
            tripsPage.getNumber(),
            tripsPage.getSize(),
            tripsPage.getTotalElements(),
            tripsPage.getTotalPages(),
            tripsPage.isFirst(),
            tripsPage.isLast(),
            tripsPage.hasNext(),
            tripsPage.hasPrevious()
        );
    }

    private TripDetail convertToTripDetail(KhananData data) {
        return new TripDetail(
            data.getId(),
            data.getDistrict(), // Assuming district is DMO
            data.getConsignerName(),
            data.getConsigneeName(),
            data.getMineralName(),
            data.getChallanNo(),
            data.getDestination(),
            data.getTransportedDate(),
            data.getQuantity()
        );
    }

}
