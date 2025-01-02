package com.example.calculator;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout contentLayout;
    private ScrollView arithmeticLayout;
    private ScrollView trigonometricLayout;
    private Switch unitSwitch;
    private TextView unitLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize layouts
        contentLayout = findViewById(R.id.content_layout);
        arithmeticLayout = findViewById(R.id.arithmetic_layout);
        trigonometricLayout = findViewById(R.id.trigonometric_layout);

        // Initially hide arithmetic and trigonometric layouts
        arithmeticLayout.setVisibility(View.GONE);
        trigonometricLayout.setVisibility(View.GONE);

        // Spinner setup
        Spinner operationSpinner = findViewById(R.id.operation_spinner);
        String[] operations = {"Select an operation", "Arithmetic Operations", "Trigonometric Operations"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, operations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operationSpinner.setAdapter(adapter);

        operationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOperation = (String) parentView.getItemAtPosition(position);
                if (selectedOperation.equals("Arithmetic Operations")) {
                    showArithmeticOperations();
                } else if (selectedOperation.equals("Trigonometric Operations")) {
                    showTrigonometricOperations();
                } else {
                    Toast.makeText(MainActivity.this, "Please select a valid operation.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void showArithmeticOperations() {
        arithmeticLayout.setVisibility(View.VISIBLE);
        trigonometricLayout.setVisibility(View.GONE);
    }

    private void showTrigonometricOperations() {
        arithmeticLayout.setVisibility(View.GONE);
        trigonometricLayout.setVisibility(View.VISIBLE);

         unitSwitch = findViewById(R.id.unit_switch);
         unitLabel = findViewById(R.id.unit_label);

        unitSwitch.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#1595A2")));
        unitSwitch.setTrackTintList(ColorStateList.valueOf(Color.parseColor("#292929")));


        unitLabel.setText("Degrees");

        unitSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                unitSwitch.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                unitSwitch.setTrackTintList(ColorStateList.valueOf(Color.parseColor("#8BC34A")));


                unitLabel.setText("Radians");

                unitSwitch.setText("Toggle to Degrees");
            } else {

                unitSwitch.setThumbTintList(ColorStateList.valueOf(Color.parseColor("#1595A2")));  // Default thumb color
                unitSwitch.setTrackTintList(ColorStateList.valueOf(Color.parseColor("#292929")));  // Default track color

                unitLabel.setText("Degrees");
                unitSwitch.setText("Toggle to Radians");
            }


    });

        EditText input = findViewById(R.id.input);
        findViewById(R.id.sin_btn).setOnClickListener(v -> performTrigonometricOperation(input, "sin"));
        findViewById(R.id.cos_btn).setOnClickListener(v -> performTrigonometricOperation(input, "cos"));
        findViewById(R.id.tan_btn).setOnClickListener(v -> performTrigonometricOperation(input, "tan"));
        findViewById(R.id.sec_btn).setOnClickListener(v -> performTrigonometricOperation(input, "sec"));
        findViewById(R.id.csc_btn).setOnClickListener(v -> performTrigonometricOperation(input, "csc"));
        findViewById(R.id.cot_btn).setOnClickListener(v -> performTrigonometricOperation(input, "cot"));
        findViewById(R.id.inv_sin_btn).setOnClickListener(v -> performTrigonometricOperation(input, "asin"));
        findViewById(R.id.inv_cos_btn).setOnClickListener(v -> performTrigonometricOperation(input, "acos"));
        findViewById(R.id.inv_tan_btn).setOnClickListener(v -> performTrigonometricOperation(input, "atan"));
        findViewById(R.id.asec_btn).setOnClickListener(v -> performTrigonometricOperation(input, "asec"));
        findViewById(R.id.acsc_btn).setOnClickListener(v -> performTrigonometricOperation(input, "acsc"));
        findViewById(R.id.acot_btn).setOnClickListener(v -> performTrigonometricOperation(input, "acot"));

    }

    private void performTrigonometricOperation(EditText input, String operation) {
        try {
            double angle = Double.parseDouble(input.getText().toString());
            double result = 0;

            boolean isInRadians = unitLabel.getText().toString().equals("Radians");
            if (!isInRadians) {
                angle = Math.toRadians(angle);
            }

            switch (operation) {
                case "sin": result = Math.sin(angle); break;
                case "cos": result = Math.cos(angle); break;
                case "tan": result = Math.tan(angle); break;
                case "sec": result = 1 / Math.cos(angle); break;
                case "csc": result = 1 / Math.sin(angle); break;
                case "cot": result = 1 / Math.tan(angle); break;
                case "asin": result = Math.toDegrees(Math.asin(angle)); break;
                case "acos": result = Math.toDegrees(Math.acos(angle)); break;
                case "atan": result = Math.toDegrees(Math.atan(angle)); break;
                case "asec": result = Math.toDegrees(Math.acos(1 / angle)); break;
                case "acsc": result = Math.toDegrees(Math.asin(1 / angle)); break;
                case "acot":
                    if (angle == 0) {
                        Toast.makeText(MainActivity.this, "Invalid input for acot (cannot divide by zero)", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    result = Math.toDegrees(Math.atan(1 / angle));
                    break;
            }

            Toast.makeText(MainActivity.this, "Result: " + result, Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }
}
