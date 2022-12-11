package fpt.grw.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String[] tripTypes = {
            "Travel",
            "Work",
            "Visit relatives"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name = findViewById(R.id.txtNameTrip);
        EditText description = findViewById(R.id.txtDescriptionTrip);
        EditText date_from = findViewById(R.id.txtDateFromTrip);
        EditText date_to = findViewById(R.id.txtDateToTrip);
        EditText destination = findViewById(R.id.txtDestinationTrip);
        Switch is_risk = findViewById(R.id.switchRisk);
        Spinner trip_type = findViewById(R.id.spinnerTypeTrip);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tripTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trip_type.setAdapter(adapter);

        Button btn_add_trip = findViewById(R.id.btnAddTrip);
        Button btn_view_trip = findViewById(R.id.btnViewAllTrip);

        //Add Trip
        btn_add_trip.setOnClickListener(view -> {
           if(name.getText().toString().matches("")){
               Toast.makeText(this, "Field name is required!", Toast.LENGTH_SHORT).show();
               return;
           }
            if(date_from.getText().toString().matches("")){
                Toast.makeText(this, "Field date from is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(date_to.getText().toString().matches("")){
                Toast.makeText(this, "Field date to is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(destination.getText().toString().matches("")){
                Toast.makeText(this, "Field destination is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            TripModalClass trip = new TripModalClass(
                    description.getText().toString(),
                    name.getText().toString(),
                    date_from.getText().toString(),
                    date_to.getText().toString(),
                    destination.getText().toString(),
                    is_risk.isChecked(),
                    Integer.parseInt(String.valueOf(trip_type.getSelectedItemId()))
            );
           dbHelper.insertTrip(trip);
           description.setText("");
           name.setText("");
           date_from.setText("");
           date_to.setText("");
           destination.setText("");
           is_risk.setChecked(false);
           trip_type.setSelection(0);
           Toast.makeText(MainActivity.this, "Enter trip successfully!",Toast.LENGTH_SHORT).show();
        });
        btn_view_trip.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ViewAllTripActivity.class);
            startActivity(intent);
        });

    }

    private void openDetailsTrip(TripModalClass selectedTrip) {
        Intent intent = new Intent(MainActivity.this,ViewDetailTripActivity.class);
        intent.putExtra("tripId", selectedTrip.getId());
        startActivity(intent);
    }
}