package stan.bulls.cows.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import stan.bulls.cows.R;
import stan.bulls.cows.core.offers.Offer;
import stan.bulls.cows.logic.BullsCowsLogic;

public class BullsCowsAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    static public final int FOOTER_TYPE = 0;
    static public final int OFFER_TYPE = 1;
    static public final int OFFER_FIRST_TYPE = 2;

    private Context context;
    private ArrayList<Offer> data;

    public BullsCowsAdapter(Context c)
    {
        context = c;
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == FOOTER_TYPE)
        {
            View v = inflater.inflate(R.layout.bulls_cows_footer, parent, false);
            return new BullsCowsFooterHolder(v);
        }
        else if (viewType == OFFER_FIRST_TYPE)
        {
            View v = inflater.inflate(R.layout.bulls_cows_offer_first, parent, false);
            return new BullsCowsFirstHolder(v);
        }
        View v = inflater.inflate(R.layout.bulls_cows_offer, parent, false);
        return new BullsCowsHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(position < data.size())
        {
            if(position == 0)
            {
                initFirstOffer((BullsCowsFirstHolder)holder, position);
                return;
            }
            initOffer((BullsCowsHolder)holder, position);
        }
    }
    private void initFirstOffer(BullsCowsFirstHolder holder, int p)
    {
        holder.offer_value.setText(data.get(p).getStringValues());
        holder.offer_bulls.setText(data.get(p).bulls + "");
        holder.offer_cows.setText(data.get(p).cows + "");
    }
    private void initOffer(BullsCowsHolder holder, int p)
    {
        holder.offer_value.setText(data.get(p).getStringValues());
        holder.offer_bulls.setText(data.get(p).bulls + "");
        holder.offer_cows.setText(data.get(p).cows + "");
        if(checkQualityOffer(data.get(p), p))
        {
           holder.quality.setVisibility(View.GONE);
        }
        else
        {
            holder.quality.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount()
    {
        if(data == null)
        {
            return 0;
        }
        else if(data.size() == 1)
        {
            return 1;
        }
        else
        {
            return data.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position < data.size())
        {
            if(position == 0)
            {
                return OFFER_FIRST_TYPE;
            }
            return OFFER_TYPE;
        }
        return FOOTER_TYPE;
    }

    private boolean checkQualityOffer(Offer offer, int p)
    {
        for(int i = 0; i<p; i++)
        {
            Offer o = BullsCowsLogic.checkCountBullsAndCows(new Offer(data.get(i).getOfferElements())
            {
                @Override
                public String getStringValues()
                {
                    return null;
                }
            }, offer);
            if(data.get(i).bulls != o.bulls || data.get(i).cows != o.cows)
            {
                return false;
            }
        }
        return true;
    }
    public void addOffer(Offer offer)
    {
        data.add(offer);
        notifyDataSetChanged();
    }

    protected class BullsCowsFirstHolder
            extends RecyclerView.ViewHolder
    {
        public TextView offer_value;
        public TextView offer_bulls;
        public TextView offer_cows;
        public BullsCowsFirstHolder(View v)
        {
            super(v);
            offer_value = (TextView)v.findViewById(R.id.offer_value);
            offer_bulls = (TextView)v.findViewById(R.id.offer_bulls);
            offer_cows = (TextView)v.findViewById(R.id.offer_cows);
        }
    }
    protected class BullsCowsHolder
            extends RecyclerView.ViewHolder
    {
        public TextView offer_value;
        public TextView offer_bulls;
        public TextView offer_cows;
        public View quality;
        public BullsCowsHolder(View v)
        {
            super(v);
            offer_value = (TextView)v.findViewById(R.id.offer_value);
            offer_bulls = (TextView)v.findViewById(R.id.offer_bulls);
            offer_cows = (TextView)v.findViewById(R.id.offer_cows);
            quality = v.findViewById(R.id.quality);
        }
    }
    protected class BullsCowsFooterHolder
            extends RecyclerView.ViewHolder
    {
        public BullsCowsFooterHolder(View v)
        {
            super(v);
        }
    }
}