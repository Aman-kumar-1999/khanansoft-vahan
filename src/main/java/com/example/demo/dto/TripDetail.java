package com.example.demo.dto;

public class TripDetail {
    private String id;
    private String dmo;
    private String consignerName;
    private String consigneeName;
    private String mineralName;
    private String challanNo;
    private String destination;
    private String transportedDate;
    private String quantityMT;

    public TripDetail() {
    }

    public TripDetail(String id, String dmo, String consignerName, String consigneeName,
            String mineralName, String challanNo, String destination, String transportedDate,
            String quantityMT) {
        this.id = id;
        this.dmo = dmo;
        this.consignerName = consignerName;
        this.consigneeName = consigneeName;
        this.mineralName = mineralName;
        this.challanNo = challanNo;
        this.destination = destination;
        this.transportedDate = transportedDate;
        this.quantityMT = quantityMT;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDmo() {
        return dmo;
    }

    public void setDmo(String dmo) {
        this.dmo = dmo;
    }

    public String getConsignerName() {
        return consignerName;
    }

    public void setConsignerName(String consignerName) {
        this.consignerName = consignerName;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getMineralName() {
        return mineralName;
    }

    public void setMineralName(String mineralName) {
        this.mineralName = mineralName;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
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

    public String getQuantityMT() {
        return quantityMT;
    }

    public void setQuantityMT(String quantityMT) {
        this.quantityMT = quantityMT;
    }
}
