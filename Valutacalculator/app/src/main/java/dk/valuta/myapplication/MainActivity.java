package dk.valuta.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import dk.valuta.myapplication.Currency.CurrencyCalculator;
import dk.valuta.myapplication.Currency.CurrencyDAO;
import dk.valuta.myapplication.Currency.Data.FixerCurrency;
import dk.valuta.myapplication.Currency.Data.MockCurrency;

//https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
public class MainActivity extends AppCompatActivity {
    //CurrencyDAO mc = new MockCurrency();
    CurrencyDAO data;
    CurrencyCalculator calculator = new CurrencyCalculator();
    Spinner spinner;
    List<Rate> ratesList;
    FixerCurrency fixer;
    String basecurrency = "USD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();

    }
    //Starts the MACHINA
    public void start(){

        fixer = new FixerCurrency();
        fixer.requestRates(this,MainActivity.this);
        data = fixer;
    }
    //Starts the calculation of the currency
    public void calcBtn(View view){
        EditText valueField = (EditText)findViewById(R.id.valuefield);
        if (valueField.getText().toString().trim().length() <= 0){//Makes sure we are not proceeding without value to calculate
            return;
        }

        double valuefieldasdouble = Double.parseDouble(String.valueOf(valueField.getText()));
        double currencyRate = 0;
        List<String> currencyList = new ArrayList<>();
        for (Rate rate: ratesList){
            if (rate.name == spinner.getSelectedItem().toString()){
                currencyRate = rate.spotRate;
                double result = calculator.Calculate(currencyRate,valuefieldasdouble);
                currencyList.add(rate.name+" : "+result);
            }
        }
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,currencyList);
        ListView list = (ListView)findViewById(R.id.currencylist);
        list.setAdapter(itemsAdapter);

    }
    // Update the spinner ui
    public void upateSpinnerUi() {
        try {
            // mc = new MockCurrency();
            ratesList = data.getRates("DKK");
            //ratesList = mc.getRates("DKK");

            List<String> currencycode = new ArrayList<String>();
            for (Rate rate : ratesList) {//Extracting the names of the currencies and adds them to a currency code list
                currencycode.add(rate.name);
            }

            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currencycode);
            itemsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner = (Spinner) findViewById(R.id.currencycode);
            spinner.setAdapter(itemsAdapter);

        }catch (Error e){
            Log.d("Error", "OnStart: "+e);
            start();
        }
    }
}
