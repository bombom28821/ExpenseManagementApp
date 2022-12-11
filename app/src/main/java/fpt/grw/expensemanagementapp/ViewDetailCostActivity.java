package fpt.grw.expensemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class ViewDetailCostActivity extends AppCompatActivity {
    private static final String[] costTypes = {
            "Costs incurred",
            "Travel costs",
            "Food cost",
            "Accommodation costs"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_cost);

        DatabaseHelper db = new DatabaseHelper(this);
        Intent intent = getIntent();
        int costId = intent.getIntExtra("costId", 0);
        int tripId = intent.getIntExtra("tripId", 0);

        EditText inputDetailAmountCost = findViewById(R.id.inputDetailAmountCost);
        EditText inputDetailDateCost = findViewById(R.id.inputDetailDateCost);
        EditText inputDetailDescriptionCost = findViewById(R.id.inputDetailDescriptionCost);
        Spinner spinnerDetailTypeCost = findViewById(R.id.spinnerDetailTypeCost);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, costTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDetailTypeCost.setAdapter(adapter);

        Button btnDeleteDetailCost = findViewById(R.id.btnDeleteDetailCost);
        Button btnUpdateDetailCost = findViewById(R.id.btnUpdateDetailCost);
        ImageView imageViewBackDetailCost = findViewById(R.id.imageViewBackDetailCost);

        //Show Detail Cost
        CostModalClass costModalClass =  db.getCostDetails(costId, tripId);

        inputDetailAmountCost.setText(costModalClass.getAmount().toString());
        inputDetailDateCost.setText(costModalClass.getDate());
        inputDetailDateCost.setEnabled(false);
        inputDetailDescriptionCost.setText(costModalClass.getDescription());
        spinnerDetailTypeCost.setSelection(costModalClass.getCost_type());

        //Back Detail Cost
        imageViewBackDetailCost.setOnClickListener(view ->{
            Intent cancel = new Intent(ViewDetailCostActivity.this, ViewCostsActivity.class);
            cancel.putExtra("tripId", tripId);
            startActivity(cancel);
        });

        //Update Detail Cost
        btnUpdateDetailCost.setOnClickListener(view -> {
            if(inputDetailAmountCost.getText().toString().matches("")){
                Toast.makeText(this, "Field amount is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            CostModalClass cost = new CostModalClass(
                    costId,
                    inputDetailDescriptionCost.getText().toString(),
                    Integer.parseInt(String.valueOf(spinnerDetailTypeCost.getSelectedItemId())),
                    Integer.parseInt(inputDetailAmountCost.getText().toString()),
                    inputDetailDateCost.getText().toString(),
                    tripId
            );
            db.updateCost(cost);
            Toast.makeText(ViewDetailCostActivity.this,"Update cost successfully by id: " + tripId, Toast.LENGTH_SHORT).show();
        });

        //Delete Detail Cost
        btnDeleteDetailCost.setOnClickListener(view -> {
            db.deleteCost(tripId, costId);
            Toast.makeText(ViewDetailCostActivity.this,"Delete cost successfully by id: " + tripId, Toast.LENGTH_SHORT).show();
            Intent cost = new Intent(ViewDetailCostActivity.this, ViewCostsActivity.class);
            cost.putExtra("tripId", tripId);
            startActivity(cost);
        });
    }
}