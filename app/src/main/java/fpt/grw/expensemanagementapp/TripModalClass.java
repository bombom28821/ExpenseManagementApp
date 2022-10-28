package fpt.grw.expensemanagementapp;

import androidx.annotation.NonNull;

public class TripModalClass {
    Integer id;
    String description;
    String name;
    String date_from;
    String date_to;
    String destination;
    Boolean is_risk;
    Integer trip_type;

    public TripModalClass(
            Integer id,
            String description,
            String name,
            String date_from,
            String date_to,
            String destination,
            Boolean is_risk,
            Integer trip_type
    ) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.date_from = date_from;
        this.date_to = date_to;
        this.destination = destination;
        this.is_risk = is_risk;
        this.trip_type = trip_type;
    }
    public TripModalClass(
            String description,
            String name,
            String date_from,
            String date_to,
            String destination,
            Boolean is_risk,
            Integer trip_type
    ) {
        this.description = description;
        this.name = name;
        this.date_from = date_from;
        this.date_to = date_to;
        this.destination = destination;
        this.is_risk = is_risk;
        this.trip_type = trip_type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Boolean getIs_risk() {
        return is_risk;
    }

    public void setIs_risk(Boolean is_risk) {
        this.is_risk = is_risk;
    }

    public Integer getTrip_type() {
        return trip_type;
    }

    public void setTrip_type(Integer trip_type) {
        this.trip_type = trip_type;
    }
    @NonNull
    @Override
    public  String toString(){

        return name + " - " + date_from;
    }
}
