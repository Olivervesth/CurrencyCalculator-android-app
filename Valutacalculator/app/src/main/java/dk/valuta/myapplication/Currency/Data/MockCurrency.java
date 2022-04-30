package dk.valuta.myapplication.Currency.Data;

import java.util.ArrayList;
import java.util.List;

import dk.valuta.myapplication.Currency.CurrencyDAO;
import dk.valuta.myapplication.Rate;

public class MockCurrency implements CurrencyDAO {

    List<Rate> ratelist;
    public MockCurrency() {
        ratelist = new ArrayList<>();
        Rate DOP = new Rate("DOP",0.0);
        ratelist.add(DOP);
        Rate EGP = new Rate("EGP",0.0);
        ratelist.add(EGP);
        Rate SVC = new Rate("SVC",0.0);
        ratelist.add(SVC);
        Rate EUR = new Rate("EUR",0.0);
        ratelist.add(EUR);
        Rate USD = new Rate("USD",0.0);
        ratelist.add(USD);
    }

    @Override
    public List<Rate> getRates(String base) {
        return ratelist;
    }
}
