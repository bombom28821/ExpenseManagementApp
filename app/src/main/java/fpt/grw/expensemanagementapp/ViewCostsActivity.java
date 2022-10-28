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

import java.util.ArrayList;
import java.util.List;

public class ViewCostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_costs);

        Intent viewCosts = getIntent();
        int tripId = viewCosts.getIntExtra("tripId", 0);

        //View costs of this trip
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ArrayList<CostModalClass> costs = dbHelper.getCostsByIdTrip("", tripId);
        ArrayAdapter<CostModalClass> arrayAdapter
                = new ArrayAdapter<CostModalClass>(this, android.R.layout.simple_list_item_1, costs);
        ListView listView = findViewById(R.id.listViewCosts);
        listView.setAdapter(arrayAdapter);

        //View detail cost by id
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            CostModalClass selectedTrip = costs.get(i);
            Intent detailCost = new Intent(ViewCostsActivity.this, ViewDetailCostActivity.class);
            detailCost.putExtra("costId", selectedTrip.getId());
            detailCost.putExtra("tripId", selectedTrip.getTrip_id());
            startActivity(detailCost);
        });

        //Search costs of this trip by type cost
        EditText searchCosts = findViewById(R.id.txtSearchCosts);
        searchCosts.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable searchQuery) {
                ArrayList<CostModalClass> costs = dbHelper.getCostsByIdTrip(searchQuery.toString().trim(), tripId);
                ArrayAdapter<CostModalClass> arrayAdapter
                        = new ArrayAdapter<CostModalClass>(ViewCostsActivity.this,
                        android.R.layout.simple_list_item_1,
                        costs
                );
                ListView listView = findViewById(R.id.listViewCosts);
                listView.setAdapter(arrayAdapter);
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

        //Add cost
        ImageView imgViewAddCost = findViewById(R.id.imgViewAddCost);
        imgViewAddCost.setOnClickListener(view -> {
            Intent addCost = new Intent(ViewCostsActivity.this, AddCostActivity.class);
            addCost.putExtra("tripId", tripId);
            startActivity(addCost);
        });

        //Back costs
        ImageView imgViewBackCost = findViewById(R.id.imgViewBackCost);
        imgViewBackCost.setOnClickListener(view -> {
            Intent backCost = new Intent(ViewCostsActivity.this, ViewDetailTripActivity.class);
            backCost.putExtra("tripId", tripId);
            startActivity(backCost);
        });

    }
}