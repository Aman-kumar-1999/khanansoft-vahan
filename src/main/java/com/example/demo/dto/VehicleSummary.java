package com.example.demo.dto;

public class VehicleSummary {
    private String id;
    private String vehicleRegNo;
    private Long totalTrips;
    private Double totalMTWeight;
    private String ownerName;
    private String mobileNo;
    private String make;
    private String model;
    private Double gvwKgs;
    private Double unladenWeightKgs;
    private Long sandTrips;
    private Double sandMTWeight;
    private Long stoneTrips;
    private Double stoneMTWeight;

    public VehicleSummary() {
    }

    public VehicleSummary(String vehicleRegNo, Long totalTrips, Double totalMTWeight, String ownerName,
            String mobileNo, String make, String model, Double gvwKgs, Double unladenWeightKgs,
            Long sandTrips, Double sandMTWeight, Long stoneTrips, Double stoneMTWeight) {
        this.vehicleRegNo = vehicleRegNo;
        this.totalTrips = totalTrips;
        this.totalMTWeight = totalMTWeight;
        this.ownerName = ownerName;
        this.mobileNo = mobileNo;
        this.make = make;
        this.model = model;
        this.gvwKgs = gvwKgs;
        this.unladenWeightKgs = unladenWeightKgs;
        this.sandTrips = sandTrips;
        this.sandMTWeight = sandMTWeight;
        this.stoneTrips = stoneTrips;
        this.stoneMTWeight = stoneMTWeight;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
