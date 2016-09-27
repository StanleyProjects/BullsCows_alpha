package stan.bulls.cows.ui.fragments.greeting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import stan.bulls.cows.R;
import stan.bulls.cows.core.Difficults;

public class ShowAddOffer
        extends Fragment
{
    //___________________VIEWS
    private TextView offer_value;
    private View add_offer;
    private View number_element_0;
    //__123
    private View number_element_1;
    private View number_element_2;
    private View number_element_3;

    //___________________FIELDS
    private int count;
    private String offerValue = "";
    private View.OnClickListener clickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.number_element_0:
                    addElement(0);
                    break;
                //__123
                case R.id.number_element_1:
                    addElement(1);
                    break;
                case R.id.number_element_2:
                    addElement(2);
                    break;
                case R.id.number_element_3:
                    addElement(3);
                    break;
                case R.id.refresh:
                    changeOfferValue("");
                    checkReady();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.show_add_offer, container, false);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        add_offer = v.findViewById(R.id.add_offer);
        v.findViewById(R.id.refresh).setOnClickListener(clickListener);
        offer_value = (TextView) v.findViewById(R.id.offer_value);
        number_element_0 = v.findViewById(R.id.number_element_0);
        v.findViewById(R.id.number_element_container_456).setVisibility(View.GONE);
        v.findViewById(R.id.number_element_container_789).setVisibility(View.GONE);
        v.findViewById(R.id.number_element_container_123).setVisibility(View.VISIBLE);
        number_element_1 = v.findViewById(R.id.number_element_1);
        number_element_2 = v.findViewById(R.id.number_element_2);
        number_element_3 = v.findViewById(R.id.number_element_3);
    }
    private void init()
    {
        count = Difficults.MAX_COUNT_SILVER;
        number_element_0.setOnClickListener(clickListener);
        number_element_1.setOnClickListener(clickListener);
        number_element_2.setOnClickListener(clickListener);
        number_element_3.setOnClickListener(clickListener);
        add_offer.setOnClickListener(clickListener);
        changeOfferValue("");
        checkReady();
    }
    private void addElement(int e)
    {
        if(offerValue.length() < count)
        {
            changeOfferValue(offerValue + e);
        }
        checkReady();
    }
    private void changeOfferValue(String newOfferValue)
    {
        offerValue = newOfferValue;
        offer_value.setText(offerValue);
    }
    private void checkReady()
    {
        if(offerValue.length() < count)
        {
            add_offer.setVisibility(View.INVISIBLE);
        }
        else
        {
            add_offer.setVisibility(View.VISIBLE);
        }
    }
}