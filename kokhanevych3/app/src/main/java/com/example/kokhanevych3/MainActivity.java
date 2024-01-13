package com.example.kokhanevych3;


import static com.example.kokhanevych3.utils.Constants.SIGN_DIVISION;
import static com.example.kokhanevych3.utils.Constants.SIGN_MINUS;
import static com.example.kokhanevych3.utils.Constants.SIGN_MULTIPLYING;
import static com.example.kokhanevych3.utils.Constants.SIGN_PLUS;
import static com.example.kokhanevych3.utils.Constants.SIGN_POINT;
import static com.example.kokhanevych3.utils.Constants.STATE_KEY;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView field;

    private Button plus;

    private Button multiplying;

    private Button minus;

    private Button division;

    private Button equal;

    private Button clean;

    private Button allClean;

    private Button point;

    private ArrayList<Button> digits;

    private static final Calculation calculation = new Calculation();

    private final StringBuilder textField = new StringBuilder();

    private final Map<Integer, Consumer<StringBuilder>> actionsForAddingSigns = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializationButtons();
        initializationActionsForMap();

        plus.setOnClickListener(this);
        multiplying.setOnClickListener(this);
        minus.setOnClickListener(this);
        division.setOnClickListener(this);
        equal.setOnClickListener(this);
        clean.setOnClickListener(this);
        allClean.setOnClickListener(this);
        point.setOnClickListener(this);
        for (Button digit : digits) {
            digit.setOnClickListener(this);
        }
    }

    private void initializationButtons() {
        field = findViewById(R.id.textView);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.cons);
        division = findViewById(R.id.division);
        equal = findViewById(R.id.equal);
        clean = findViewById(R.id.clear);
        allClean = findViewById(R.id.all_clear);
        multiplying = findViewById(R.id.multiply);
        point = findViewById(R.id.point);
        digits = new ArrayList<>();
        digits.add(findViewById(R.id.number_zero));
        digits.add(findViewById(R.id.number_one));
        digits.add(findViewById(R.id.number_two));
        digits.add(findViewById(R.id.number_three));
        digits.add(findViewById(R.id.number_four));
        digits.add(findViewById(R.id.number_five));
        digits.add(findViewById(R.id.number_six));
        digits.add(findViewById(R.id.number_seven));
        digits.add(findViewById(R.id.number_eight));
        digits.add(findViewById(R.id.number_nine));
    }

    private void initializationActionsForMap() {
        actionsForAddingSigns.put(plus.getId(), (textField) -> textField.append(SIGN_PLUS));
        actionsForAddingSigns.put(multiplying.getId(), (textField) -> textField.append(SIGN_MULTIPLYING));
        actionsForAddingSigns.put(minus.getId(), (textField) -> textField.append(SIGN_MINUS));
        actionsForAddingSigns.put(division.getId(), (textField) -> textField.append(SIGN_DIVISION));
        actionsForAddingSigns.put(clean.getId(), (textField) -> {
            if (textField.length() != 0) {
                textField.delete(textField.length() - 1, textField.length());
            }
        });
        actionsForAddingSigns.put(allClean.getId(), (textField) -> textField.delete(0, textField.length()));
        actionsForAddingSigns.put(point.getId(), (textField) -> textField.append(SIGN_POINT));
        actionsForAddingSigns.put(equal.getId(), (textField) -> {
            if (textField.length() != 0) {
                field.setText(calculation.calculateExpression(String.valueOf(textField)));
                textField.delete(0, textField.length());
                textField.append(field.getText());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (actionsForAddingSigns.containsKey(view.getId())) {
            Objects.requireNonNull(actionsForAddingSigns.get(view.getId())).accept(textField);
        } else {
            onClickDigitButtons(view);
        }

        field.setText(textField);
    }

    private void onClickDigitButtons(View view) {
        for (int i = 0; i < digits.size(); i++) {
            if (view.getId() == digits.get(i).getId()) {
                textField.append(digits.get(i).getText());
                break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_KEY, field.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        textField.append(savedInstanceState.get(STATE_KEY));
        field.setText(savedInstanceState.get(STATE_KEY).toString());
    }
}