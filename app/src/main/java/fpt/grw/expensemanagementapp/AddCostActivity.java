package fpt.grw.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddCostActivity extends AppCompatActivity {
    private static final String[] costTypes = {
            "Costs incurred",
            "Travel costs",
            "Food cost",
            "Accommodation costs"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost);

        Intent intent = getIntent();
        int tripId = intent.getIntExtra("tripId", 0);

        EditText amount = findViewById(R.id.inputDetailAmountCost);
        EditText description = findViewById(R.id.inputDetailDescriptionCost);
        EditText date = findViewById(R.id.inputDetailDateCost);

        Button btnAddCost = findViewById(R.id.btnUpdateDetailCost);
        Button btnCancel = findViewById(R.id.btnDeleteDetailCost);
        //Selected Cost
        Spinner spinner = findViewById(R.id.spinnerDetailTypeCost);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCostActivity.this,
                android.R.layout.simple_spinner_item, costTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //Cancel Add Cost
        btnCancel.setOnClickListener(view ->{
            Intent cancel = new Intent(AddCostActivity.this, ViewCostsActivity.class);
            cancel.putExtra("tripId", tripId);
            startActivity(cancel);
        });

        //Add cost
        btnAddCost.setOnClickListener(view -> {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            CostModalClass costModalClass = new CostModalClass(
                    description.getText().toString(),
                    Integer.parseInt(String.valueOf(spinner.getSelectedItemId())),
                    Integer.parseInt(amount.getText().toString()),
                    date.getText().toString(),
                    tripId
            );
            dbHelper.insertCost(costModalClass);
            amount.setText("");
            description.setText("");
            date.setText("");
            spinner.setSelection(0);
            Toast.makeText(AddCostActivity.this,"Add cost for trip by id: " + tripId, Toast.LENGTH_SHORT).show();

        });
    }
}