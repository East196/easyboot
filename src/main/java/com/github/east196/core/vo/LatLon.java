package com.github.east196.core.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class LatLon {
    @JsonAlias({"alarmId","faultId","riskId"})
    private String eventId;
    private Double lat;
    private Double lon;

    public LatLon() {
    }

    public LatLon(String eventId) {
        this.eventId = eventId;
    }

    public LatLon(String eventId, Double lat, Double lon) {
        this.eventId = eventId;
        this.lat = lat;
        this.lon = lon;
    }
}
