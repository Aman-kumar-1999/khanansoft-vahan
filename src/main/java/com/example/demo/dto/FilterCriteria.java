package com.example.demo.dto;

public class FilterCriteria {

    private String id;
    private String district;
    private String consignerName;
    private String date;
    private String sourceType;
    private String consigneeName;
    private String challanNo;
    private String mineralName;
    private String mineralCategory;
    private String vehicleRegNo;
    private String destination;
    private String transportedDate;
    private String quantity;
    private String unit;
    private String checkStatus;

    // Constructors
    public FilterCriteria() {
    }

    public FilterCriteria(String district, String consignerName, String mineralName,
                         String vehicleRegNo, String checkStatus) {
        this.district = district;
        this.consignerName = consignerName;
        this.mineralName = mineralName;
        this.vehicleRegNo = vehicleRegNo;
        this.checkStatus = checkStatus;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getConsignerName() {
        return consignerName;
    }

    public void setConsignerName(String consignerName) {
        this.consignerName = consignerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public String getMineralName() {
        return mineralName;
    }

    public void setMineralName(String mineralName) {
        this.mineralName = mineralName;
    }

    public String getMineralCategory() {
        return mineralCategory;
    }

    public void setMineralCategory(String mineralCategory) {
        this.mineralCategory = mineralCategory;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTransportedDate() {
        return transportedDate;
    }

    public void setTransportedDate(String transportedDate) {
        this.transportedDate = transportedDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    @Override
    public String toString() {
        return "FilterCriteria{" +
                "district='" + district + '\'' +
                ", consignerName='" + consignerName + '\'' +
                ", mineralName='" + mineralName + '\'' +
                ", mineralCategory='" + mineralCategory + '\'' +
                ", checkStatus='" + checkStatus + '\'' +
                ", vehicleRegNo='" + vehicleRegNo + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", consigneeName='" + consigneeName + '\'' +
                ", challanNo='" + challanNo + '\'' +
                ", destination='" + destination + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit='" + unit + '\'' +
                ", date='" + date + '\'' +
                ", transportedDate='" + transportedDate + '\'' +
                '}';
    }
}
