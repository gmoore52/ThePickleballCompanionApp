package edu.missouristate.thepickleballcompanionapp.domain.dto;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.AbstractMap;
import java.util.List;

public class UserStatisticsHistoricalDTO {
    private String name;

    private List<AbstractMap.SimpleEntry<Timestamp, Number>> series;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AbstractMap.SimpleEntry<Timestamp, Number>> getSeries() {
        return series;
    }

    public void setSeries(List<AbstractMap.SimpleEntry<Timestamp, Number>> series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "UserStatisticsHistoricalDTO{" +
                "name='" + name + '\'' +
                ", series=" + series +
                '}';
    }

    public UserStatisticsHistoricalDTO() {}

    public UserStatisticsHistoricalDTO(String name, List<AbstractMap.SimpleEntry<Timestamp, Number>> series) {
        this.name = name;
        this.series = series;
    }
}
