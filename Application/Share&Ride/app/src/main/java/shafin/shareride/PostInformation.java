package shafin.shareride;

/**
 * Created by shafins on 6/12/17.
 */

public class PostInformation {
    public String from,to,time,vehicle;

    public PostInformation() {
    }

    public PostInformation(String from, String to, String time, String vehicle) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.vehicle = vehicle;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getTime() {
        return time;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}
