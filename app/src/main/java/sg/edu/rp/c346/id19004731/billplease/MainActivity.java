package sg.edu.rp.c346.id19004731.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText amountInput, numOfPaxInput, discountInput;
    ToggleButton svsTgBtn, gstTgBtn;
    TextView totalBillDisplay, splitBillDisplay;
    Button splitBtn, resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountInput = findViewById(R.id.amountEditText);
        numOfPaxInput = findViewById(R.id.numOfPaxEditText);
        discountInput = findViewById(R.id.discountEditText);
        svsTgBtn = findViewById(R.id.serviceToggleButton);
        gstTgBtn = findViewById(R.id.gstToggleButton);
        totalBillDisplay = findViewById(R.id.totalBillTextView);
        splitBillDisplay = findViewById(R.id.eachPaysTextView);
        splitBtn = findViewById(R.id.splitButton);
        resetBtn = findViewById(R.id.resetButton);

        splitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountStr = amountInput.getText().toString();
                String numOfPaxStr = numOfPaxInput.getText().toString();
                String discountStr = discountInput.getText().toString();

                if (isValid(amountStr, numOfPaxStr, discountStr)) {
                    boolean withSvs = svsTgBtn.isChecked();
                    boolean withGst = gstTgBtn.isChecked();

                    double amount = Double.parseDouble(amountStr);
                    int numOfPax = Integer.parseInt(numOfPaxStr);
                    double discount = Double.parseDouble(discountStr);

                    double totalBill = calcTotalBill(amount, discount, withGst, withSvs);
                    totalBillDisplay.setText(String.format("%s $%.2f", getString(R.string.totalBill), totalBill));
                    double splitBill = splitTotalBill(totalBill, numOfPax);
                    splitBillDisplay.setText(String.format("%s $%.2f", getString(R.string.eachPays), splitBill));
                } else {
                    Toast.makeText(MainActivity.this,"Please fill in all fields",Toast.LENGTH_LONG).show();
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountInput.getText().clear();
                numOfPaxInput.getText().clear();
                discountInput.getText().clear();
                svsTgBtn.setChecked(false);
                gstTgBtn.setChecked(false);
                totalBillDisplay.setText(R.string.totalBill);
                splitBillDisplay.setText(R.string.eachPays);

            }
        });
    }

    public double calcTotalBill(double amount, double discount, boolean withGst, boolean withSvs) {
        double svs = 0;
        double gst = 0;
        if (withGst) gst = 7;
        if (withSvs) svs = 10;
        double totalBill = (amount * ((gst+svs+100)/100) ) * ((100-discount)/100);
        return totalBill;
    }

    public double splitTotalBill(double totalBill, int num) {
        double splitBill = totalBill / (double)num;
        return splitBill;
    }

    public boolean isValid(String amountStr, String noOfPaxStr, String discountStr) {
        String[] textFields = {amountStr, noOfPaxStr, discountStr};

        int count = 0;
        for (int i = 0; i < textFields.length; i++) {
            if (textFields[i].trim().length() > 0) count++;
        }
        return count==3;
    }

} //end of MainActivity class
