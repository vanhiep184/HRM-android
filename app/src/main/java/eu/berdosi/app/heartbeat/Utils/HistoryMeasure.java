package eu.berdosi.app.heartbeat.Utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HistoryMeasure {
    private String userId;
    private LocalDateTime time;
    private int measureResult;

    public HistoryMeasure(String userId, LocalDateTime time, int measureResult) {
        this.userId = userId;
        this.time = time;
        this.measureResult = measureResult;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getMeasureResult() {
        return measureResult;
    }

    public void setMeasureResult(int measureResult) {
        this.measureResult = measureResult;
    }
}
