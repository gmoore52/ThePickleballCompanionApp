package edu.missouristate.thepickleballcompanionapp.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class UserStatisticsHistoricalCompositeKey implements Serializable {
    private String username;
    private Timestamp maxTimestamp;

    public UserStatisticsHistoricalCompositeKey(String username, Timestamp maxTimestamp) {
        this.username = username;
        this.maxTimestamp = maxTimestamp;
    }

    public UserStatisticsHistoricalCompositeKey() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getMaxTimestamp() {
        return maxTimestamp;
    }

    public void setMaxTimestamp(Timestamp maxTimestamp) {
        this.maxTimestamp = maxTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatisticsHistoricalCompositeKey that = (UserStatisticsHistoricalCompositeKey) o;
        return Objects.equals(username, that.username) && Objects.equals(maxTimestamp, that.maxTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, maxTimestamp);
    }
}
