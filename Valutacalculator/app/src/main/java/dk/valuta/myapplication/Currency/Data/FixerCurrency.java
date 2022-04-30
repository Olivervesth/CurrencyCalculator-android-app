package dk.valuta.myapplication.Currency.Data;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dk.valuta.myapplication.Currency.CurrencyDAO;
import dk.valuta.myapplication.MainActivity;
import dk.valuta.myapplication.Rate;
//https://google.github.io/volley/requestqueue.html
//https://fixer.io/documentation
public class FixerCurrency implements CurrencyDAO {
    List<Rate> ratelist;
    public FixerCurrency() {

    }
    public void requestRates(Context context, MainActivity mc){
        ratelist = new ArrayList<Rate>();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://v1.nocodeapi.com/johnnybravo/cx/sDGHcWXqNXLbFXmE/rates";
        // Request a string response from the provided URL.
        Log.d("Request", "start: ");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {// When we get a response back from the api we are calling
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObj = jsonObject.getJSONObject("rates");
                                //We have a static amount of valuta's we wanna provide for the user
                                if (jsonObj.has("DOP")){
                                    Rate DOP = new Rate("DOP",jsonObj.getDouble("DOP"));//Creates new rate
                                    ratelist.add(DOP);// Adds the new rate to our list
                                }
                                if (jsonObj.has("EUR")){
                                    Rate DOP = new Rate("EUR",jsonObj.getDouble("EUR"));
                                    ratelist.add(DOP);
                                }
                                if (jsonObj.has("EGP")){
                                    Rate DOP = new Rate("EGP",jsonObj.getDouble("EGP"));
                                    ratelist.add(DOP);
                                }
                                if (jsonObj.has("SVC")){
                                    Rate DOP = new Rate("SVC",jsonObj.getDouble("SVC"));
                                    ratelist.add(DOP);
                                }
                                if (jsonObj.has("USD")){
                                    Rate DOP = new Rate("USD",jsonObj.getDouble("USD"));
                                    ratelist.add(DOP);
                                }
                                mc.upateSpinnerUi();//...I know this is illegal but its 3 on a friday night ZzZzZ
                        }catch (JSONException err){
                            Log.d("Error", "onResponse: "+err.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {// If the call fails we end up here and logs the fail
                Log.d("fail", "onErrorResponse: "+error);
            }
        });
        queue.add(stringRequest);// Here we add the api http request to the queue so it will be called
    }

    @Override
    public List<Rate> getRates(String base) {// Here we return the ratelist for the one asking
        return ratelist;
    }
}
