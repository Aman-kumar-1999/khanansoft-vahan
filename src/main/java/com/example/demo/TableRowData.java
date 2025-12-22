package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class TableRowData {
    private String districtName;
    private List<String> hrefs = new ArrayList<>();

    public TableRowData() {}

    public TableRowData(String districtName, List<String> hrefs) {
        this.districtName = districtName;
        this.hrefs = hrefs;
    }

    public String getDistrictName() { return districtName; }
    public void setDistrictName(String districtName) { this.districtName = districtName; }

    public List<String> getHrefs() { return hrefs; }
    public void setHrefs(List<String> hrefs) { this.hrefs = hrefs; }
}

