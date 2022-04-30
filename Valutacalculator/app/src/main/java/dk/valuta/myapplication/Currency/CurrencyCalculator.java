package dk.valuta.myapplication.Currency;

import android.util.Log;

public class CurrencyCalculator {

    public CurrencyCalculator(){

    }

    public double Calculate(double rate,double value){//Need to use the base valuta USD convert to usd and then convert to chosen valuta
        double result = value/rate;//Right now its checking usd against the other valutas
        return result;
    }
}
