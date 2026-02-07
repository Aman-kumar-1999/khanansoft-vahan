package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "khanan_data")
public class KhananData {

    @Id
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




    public KhananData(String district, String consignerName, String date, String sourceType,
        String consigneeName, String challanNo, String mineralName, String mineralCategory, String vehicleRegNo,
        String destination, String transportedDate, String quantity, String unit, String checkStatus
    ) {
        this.district = district;
        this.consignerName = consignerName;
        this.date = date;
        this.sourceType = sourceType;
        this.consigneeName = consigneeName;
        this.challanNo = challanNo;
        this.mineralName = mineralName;
        this.mineralCategory = mineralCategory;
        this.vehicleRegNo = vehicleRegNo;
        this.destination = destination;
        this.transportedDate = transportedDate;
        this.quantity = quantity;
        this.unit = unit;
        this.checkStatus = checkStatus;

    }

    
}
