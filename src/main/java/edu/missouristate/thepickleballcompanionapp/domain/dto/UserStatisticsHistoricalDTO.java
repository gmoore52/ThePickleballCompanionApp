package edu.missouristate.thepickleballcompanionapp.domain.dto;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.AbstractMap;
import java.util.List;

public class UserStatisticsHistoricalDTO {
    private String name;

    private List<List<Number>> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<Number>> getData() {
        return data;
    }

    public void setData(List<List<Number>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserStatisticsHistoricalDTO{" +
                "name='" + name + '\'' +
                ", data=" + data +
                '}';
    }

    public UserStatisticsHistoricalDTO() {}

    public UserStatisticsHistoricalDTO(String name, List<List<Number>> series) {
        this.name = name;
        this.data = series;
    }
}
