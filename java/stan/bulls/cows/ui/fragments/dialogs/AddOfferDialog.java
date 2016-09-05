package stan.bulls.cows.ui.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import stan.bulls.cows.R;
import stan.bulls.cows.core.Difficults;
import stan.bulls.cows.core.offers.NumberOffer;
import stan.bulls.cows.core.offers.NumberOfferElement;
import stan.bulls.cows.marks.GameMark;

public class AddOfferDialog
        extends DialogFragment
    implements GameMark, Difficults
{
    static public AddOfferDialog newInstance(AddOfferDialogListener l, int count, int dif)
    {
        AddOfferDialog fragment = new AddOfferDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(COUNT_KEY, count);
        bundle.putInt(DIFFICULT_KEY, dif);
        fragment.setArguments(bundle);
        fragment.listener = l;
        return fragment;
    }

    //___________________VIEWS
    private TextView offer_value;
    private View add_offer;
    private View number_element_0;
    //__123
    private View number_element_1;
    private View number_element_2;
    private View number_element_3;
    //__456
    private View number_element_4;
    private View number_element_5;
    private View number_element_6;
    //__789
    private View number_element_7;
    private View number_element_8;
    private View number_element_9;

    //___________________FIELDS
    private int count;
    private int difficult;
    private String offerValue = "";
    private AddOfferDialogListener listener;
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
                //__456
                case R.id.number_element_4:
                    addElement(4);
                    break;
                case R.id.number_element_5:
                    addElement(5);
                    break;
                case R.id.number_element_6:
                    addElement(6);
                    break;
                //__789
                case R.id.number_element_7:
                    addElement(7);
                    break;
                case R.id.number_element_8:
                    addElement(8);
                    break;
                case R.id.number_element_9:
                    addElement(9);
                    break;
                case R.id.add_offer:
                    addOffer();
                    break;
                case R.id.refresh:
                    changeOfferValue("");
                    checkReady();
                    break;
            }
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return new Dialog(getActivity(), R.style.Dialog);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.add_offer_dialog, null);
        count = getArguments().getInt(COUNT_KEY);
        difficult = getArguments().getInt(DIFFICULT_KEY);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        add_offer = v.findViewById(R.id.add_offer);
        offer_value = (TextView) v.findViewById(R.id.offer_value);
        number_element_0 = v.findViewById(R.id.number_element_0);
        v.findViewById(R.id.number_element_container_456).setVisibility(View.GONE);
        v.findViewById(R.id.number_element_container_789).setVisibility(View.GONE);
        if(difficult == DIFFICULT_EASY || difficult == DIFFICULT_MEDIUM || difficult == DIFFICULT_HARD)
        {
            v.findViewById(R.id.number_element_container_123).setVisibility(View.VISIBLE);
            number_element_1 = v.findViewById(R.id.number_element_1);
            number_element_2 = v.findViewById(R.id.number_element_2);
            number_element_3 = v.findViewById(R.id.number_element_3);
        }
        v.findViewById(R.id.refresh).setOnClickListener(clickListener);
        if(difficult == DIFFICULT_MEDIUM || difficult == DIFFICULT_HARD)
        {
            v.findViewById(R.id.number_element_container_456).setVisibility(View.VISIBLE);
            number_element_4 = v.findViewById(R.id.number_element_4);
            number_element_5 = v.findViewById(R.id.number_element_5);
            number_element_6 = v.findViewById(R.id.number_element_6);
        }
        if(difficult == DIFFICULT_HARD)
        {
            v.findViewById(R.id.number_element_container_789).setVisibility(View.VISIBLE);
            number_element_7 = v.findViewById(R.id.number_element_7);
            number_element_8 = v.findViewById(R.id.number_element_8);
            number_element_9 = v.findViewById(R.id.number_element_9);
        }
    }
    private void init()
    {
        number_element_0.setOnClickListener(clickListener);
        if (difficult == DIFFICULT_EASY || difficult == DIFFICULT_MEDIUM || difficult == DIFFICULT_HARD)
        {
            number_element_1.setOnClickListener(clickListener);
            number_element_2.setOnClickListener(clickListener);
            number_element_3.setOnClickListener(clickListener);
        }
        if(difficult == DIFFICULT_MEDIUM || difficult == DIFFICULT_HARD)
        {
            number_element_4.setOnClickListener(clickListener);
            number_element_5.setOnClickListener(clickListener);
            number_element_6.setOnClickListener(clickListener);
        }
        if(difficult == DIFFICULT_HARD)
        {
            number_element_7.setOnClickListener(clickListener);
            number_element_8.setOnClickListener(clickListener);
            number_element_9.setOnClickListener(clickListener);
        }
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
    private void addOffer()
    {
        if(offerValue.length() != count)
        {
            return;
        }
        NumberOfferElement[] elements = new NumberOfferElement[count];
        char[] valueCharArray = offerValue.toCharArray();
        for(int i=0; i<count; i++)
        {
            elements[i] = new NumberOfferElement(valueCharArray[i] - 48);
        }
        NumberOffer offer = new NumberOffer(elements);
        listener.addOffer(offer);
        dismiss();
    }

    public interface AddOfferDialogListener
    {
        void addOffer(NumberOffer offer);
    }
}