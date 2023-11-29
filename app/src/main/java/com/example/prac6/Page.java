package com.example.prac6;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Page extends AppCompatActivity {


    private static final String TAG = "MyApp";
    private ArrayList<String> al = new ArrayList<>();
    private final Map<String, Double> itemCostMap = new HashMap<>();
    final String[] items = {"Extra Cheese", "Corn", "Capsicum","Pepper","Chilly Flakes"};
    double totalCost = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);


        ImageView pizza1ImageView = findViewById(R.id.pizza1);
        ImageView pizza2ImageView = findViewById(R.id.pizza2);


        registerForContextMenu(pizza1ImageView);
        registerForContextMenu(pizza2ImageView);

        ImageView cart1=findViewById(R.id.cart1);
        cart1.setOnClickListener(v -> {
            Intent i=new Intent(Page.this, CartActivity.class);
            i.putStringArrayListExtra("items",al);
            i.putExtra("Cost",totalCost);
            startActivity(i);
        });
        pizza1ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pizza 1 is clicked
                String pizzaName = "Margherita Pizza";
                String pizzaCost = "250";
                al.add(pizzaName + "   " +"Cost:"+ pizzaCost);
                Toast.makeText(Page.this, "Item added to the cart.", Toast.LENGTH_SHORT).show();
                // Verify whether item is added to the ArrayList
                verifyItemsAddedToArrayList();

            }
        });

        pizza2ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pizza 2 is clicked
                String pizzaName = "Cheese Pizza";
                String pizzaCost = "200";
                al.add(pizzaName + "   " +"Cost:"+ pizzaCost);
                Toast.makeText(Page.this, "Item added to the cart.", Toast.LENGTH_SHORT).show();
                // Verify whether item is added to the ArrayList
                verifyItemsAddedToArrayList();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        itemCostMap.put("Extra Cheese",15.0);
        itemCostMap.put("Corn",25.0);
        itemCostMap.put("Capsicum",15.0);
        itemCostMap.put("Pepper",10.0);
        itemCostMap.put("Chilly Flakes",10.0);

        // Create a boolean array to track the checked state of each item
        final boolean[] checkedItems = new boolean[items.length];
        for(int i=0;i<items.length;i++){
            checkedItems[i]=false;
        }
        // Build an AlertDialog with a multi-choice list
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose extra needed")
                .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // Update the checked state of the item
                        checkedItems[which] = isChecked;
                    }
                })
                .setPositiveButton("Add to Cart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Process the selected items when the "OK" button is clicked
                        processSelectedItems(checkedItems);
                        calculateTotalCost();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void processSelectedItems(boolean[] checked){
        for(int i=0;i< checked.length;i++){
            if(checked[i]){
                al.add(items[i]+" "+"Cost:"+itemCostMap.get(items[i]));
            }
        }
        for(int i=0;i<al.size();i++){
            Log.d("myapp",al.get(i));
        }
    }
    private void calculateTotalCost() {
        for (String item : al) {
            // Extract the cost from the string and add it to the total
            String[] parts = item.split("Cost:");
            if (parts.length == 2) {
                double cost = Double.parseDouble(parts[1].trim());
                Log.d("app", String.valueOf(cost));
                totalCost += cost;
            }
        }

        // Print or use the total cost
        Log.d(TAG, "Total Cost: " + totalCost);
    }
    private void verifyItemsAddedToArrayList() {
        for (String str : al) {
            Log.d(TAG, str);
        }
    }

}
