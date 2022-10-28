package fpt.grw.expensemanagementapp;

import android.util.Log;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CostModalClass {
    Integer id;
    String description;
    Integer cost_type;
    Integer amount;
    String date;
    Integer trip_id;

    public CostModalClass(
            String description,
            Integer cost_type,
            Integer amount,
            String date,
            Integer trip_id
    ) {
        this.description = description;
        this.cost_type = cost_type;
        this.amount = amount;
        this.date = date;
        this.trip_id = trip_id;
    }

    public CostModalClass(
            Integer id,
            String description,
            Integer cost_type,
            Integer amount,
            String date,
            Integer trip_id
    ) {
        this.id = id;
        this.description = description;
        this.cost_type = cost_type;
        this.amount = amount;
        this.date = date;
        this.trip_id = trip_id;
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

    public Integer getCost_type() {
        return cost_type;
    }

    public void setCost_type(Integer cost_type) {
        this.cost_type = cost_type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(Integer trip_id) {
        this.trip_id = trip_id;
    }
    @NonNull
    @Override
    public  String toString(){
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(amount);
        return formattedNumber + " $ - " + description;
    }
}
