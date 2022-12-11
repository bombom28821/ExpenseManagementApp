package fpt.grw.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ViewAllTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_trip);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<TripModalClass> trips = dbHelper.getTrips("");
        ArrayAdapter<TripModalClass> arrayAdapter
                = new ArrayAdapter<TripModalClass>(this, android.R.layout.simple_list_item_1, trips);
        ListView listView = findViewById(R.id.listViewTrip);
        listView.setAdapter(arrayAdapter);

        //click o Listview Item
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            TripModalClass selectedTrip = trips.get(i);
            openDetailsTrip(selectedTrip);
        });
        //Back Add Trip
        ImageView imageViewBackAddTrip = findViewById(R.id.imageViewBackAddTrip);
        imageViewBackAddTrip.setOnClickListener(view -> {
            Intent viewAll = new Intent(ViewAllTripActivity.this, MainActivity.class);
            startActivity(viewAll);
        });
        //Search
        EditText searchTrip = findViewById(R.id.txtSearchTrip);
        TextView txtNotFound = findViewById(R.id.txtNotFound);
        searchTrip.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable searchQuery) {
                List<TripModalClass> trips = dbHelper.getTrips(searchQuery.toString().trim());
                if(trips.size() == 0){
                    txtNotFound.setText("Not Found any trip!");
                }else{
                    txtNotFound.setText("");
                }
                ArrayAdapter<TripModalClass> arrayAdapter
                        = new ArrayAdapter<TripModalClass>(
                                ViewAllTripActivity.this,
                                android.R.layout.simple_list_item_1,
                                trips
                );
                ListView listView = findViewById(R.id.listViewTrip);
                listView.setAdapter(arrayAdapter);

            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

        });
    }

    private void openDetailsTrip(TripModalClass selectedTrip) {
        Intent intent = new Intent(ViewAllTripActivity.this,ViewDetailTripActivity.class);
        intent.putExtra("tripId", selectedTrip.getId());
        startActivity(intent);
    }
}