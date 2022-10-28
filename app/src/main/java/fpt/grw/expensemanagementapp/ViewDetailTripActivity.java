package fpt.grw.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class ViewDetailTripActivity extends AppCompatActivity {
    private static final String[] tripTypes = {
            "Travel",
            "Work",
            "Visit relatives"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_trip);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        int tripId = intent.getIntExtra("tripId", 0);
        TripModalClass tripDetail = dbHelper.getTripDetails(tripId);

        EditText name = findViewById(R.id.txtNameDetailTrip);
        EditText description = findViewById(R.id.txtDescriptionDetailTrip);
        EditText date_from = findViewById(R.id.txtDateFromDetailTrip);
        EditText date_to = findViewById(R.id.txtDateToDetailTrip);
        EditText destination = findViewById(R.id.txtDestinationDetailTrip);
        Switch is_risk = findViewById(R.id.switchRiskDetailTrip);

        Spinner trip_type = findViewById(R.id.spinnerDetailTypeTrip);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tripTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trip_type.setAdapter(adapter);

        name.setText(tripDetail.getName());
        description.setText(tripDetail.getDescription());
        date_from.setText(tripDetail.getDate_from());
        date_to.setText(tripDetail.getDate_to());
        destination.setText(tripDetail.getDestination());
        trip_type.setSelection(tripDetail.getTrip_type());
        is_risk.setChecked(tripDetail.getIs_risk());

        ImageView imgBackViewAllTrip = findViewById(R.id.imgBackViewAllTrip);
        Button btnUpdateDetailTrip = findViewById(R.id.btnUpdateDetailTrip);
        Button btnDeleteTrip = findViewById(R.id.btnDeleteTrip);
        Button btnViewCosts = findViewById(R.id.btnViewCosts);

        imgBackViewAllTrip.setOnClickListener(view -> {
            Intent viewAll = new Intent(ViewDetailTripActivity.this, ViewAllTripActivity.class);
            startActivity(viewAll);
        });

        btnUpdateDetailTrip.setOnClickListener(view -> {
            TripModalClass tripUpdate = new TripModalClass(
                    tripId,
                    description.getText().toString(),
                    name.getText().toString(),
                    date_from.getText().toString(),
                    date_to.getText().toString(),
                    destination.getText().toString(),
                    is_risk.isChecked(),
                    Integer.parseInt(String.valueOf(trip_type.getSelectedItemId()))
            );
            dbHelper.updateTrip(tripUpdate);
            Toast.makeText(ViewDetailTripActivity.this, "Update trip successfully!",Toast.LENGTH_SHORT).show();
        });

        btnDeleteTrip.setOnClickListener(view -> {
            dbHelper.deleteTrip(tripId);
            Toast.makeText(ViewDetailTripActivity.this, "Delete trip successfully!",Toast.LENGTH_SHORT).show();
            Intent deleteIntent = new Intent(ViewDetailTripActivity.this, ViewAllTripActivity.class);
            startActivity(deleteIntent);
        });

        btnViewCosts.setOnClickListener(view -> {
            Intent viewCosts = new Intent(ViewDetailTripActivity.this, ViewCostsActivity.class);
            viewCosts.putExtra("tripId", tripId);
            startActivity(viewCosts);
        });

    }
}