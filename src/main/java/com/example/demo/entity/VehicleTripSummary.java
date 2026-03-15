package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicle_trip_summary")
public class VehicleTripSummary {

    @Id
    private String id; // MongoDB document ID
    private String vehicleRegNo;

    private Long totalTrips;
    private Double totalMTWeight;
    private Long sandTrips;
    private Double sandMTWeight;
    private Long stoneTrips;
    private Double stoneMTWeight;


    private String ownerName;
    private String mobileNo;
    private String make;
    private String model;
    private Double gvwKgs;
    private Double unladenWeightKgs;


    public VehicleTripSummary() {
    }

    public VehicleTripSummary(String vehicleRegNo, Long totalTrips, Double totalMTWeight, Long sandTrips,
                              Double sandMTWeight, Long stoneTrips, Double stoneMTWeight, String ownerName,
                              String mobileNo, String make, String model, Double gvwKgs, Double unladenWeightKgs) {
        this.vehicleRegNo = vehicleRegNo;
        this.totalTrips = totalTrips;
        this.totalMTWeight = totalMTWeight;
        this.sandTrips = sandTrips;
        this.sandMTWeight = sandMTWeight;
        this.stoneTrips = stoneTrips;
        this.stoneMTWeight = stoneMTWeight;
        this.ownerName = ownerName;
        this.mobileNo = mobileNo;
        this.make = make;
        this.model = model;
        this.gvwKgs = gvwKgs;
        this.unladenWeightKgs = unladenWeightKgs;

    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public Long getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(Long totalTrips) {
        this.totalTrips = totalTrips;
    }

    public Double getTotalMTWeight() {
        return totalMTWeight;
    }

    public void setTotalMTWeight(Double totalMTWeight) {
        this.totalMTWeight = totalMTWeight;
    }

    public Long getSandTrips() {
        return sandTrips;
    }

    public void setSandTrips(Long sandTrips) {
        this.sandTrips = sandTrips;
    }

    public Double getSandMTWeight() {
        return sandMTWeight;
    }

    public void setSandMTWeight(Double sandMTWeight) {
        this.sandMTWeight = sandMTWeight;
    }

    public Long getStoneTrips() {
        return stoneTrips;
    }

    public void setStoneTrips(Long stoneTrips) {
        this.stoneTrips = stoneTrips;
    }

    public Double getStoneMTWeight() {
        return stoneMTWeight;
    }

    public void setStoneMTWeight(Double stoneMTWeight) {
        this.stoneMTWeight = stoneMTWeight;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getGvwKgs() {
        return gvwKgs;
    }

    public void setGvwKgs(Double gvwKgs) {
        this.gvwKgs = gvwKgs;
    }

    public Double getUnladenWeightKgs() {
        return unladenWeightKgs;
    }

    public void setUnladenWeightKgs(Double unladenWeightKgs) {
        this.unladenWeightKgs = unladenWeightKgs;
    }
}
