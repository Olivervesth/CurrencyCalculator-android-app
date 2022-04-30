package dk.valuta.myapplication.Currency;

import java.util.List;

import dk.valuta.myapplication.Rate;

public interface CurrencyDAO {
    public List<Rate> getRates(String base);

}

