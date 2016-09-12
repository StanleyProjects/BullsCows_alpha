package stan.bulls.cows.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import stan.bulls.cows.R;
import stan.bulls.cows.models.OfferListItem;

public class BullsCowsAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    static public final int FOOTER_TYPE = 0;
    static public final int OFFER_TYPE = 1;
    static public final int OFFER_FIRST_TYPE = 2;

    private Context context;
    private ArrayList<OfferListItem> data;
    private boolean checkingQuality;
    private boolean checkingTime;

    public BullsCowsAdapter(Context c)
    {
        context = c;
        data = new ArrayList<>();
        checkingQuality = false;
        checkingTime = false;
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
        holder.offer_value.setText(data.get(p).offer.getStringValues());
        holder.offer_bulls.setText(data.get(p).offer.bulls + "");
        holder.offer_cows.setText(data.get(p).offer.cows + "");
    }
    private void initOffer(BullsCowsHolder holder, int p)
    {
        holder.offer_value.setText(data.get(p).offer.getStringValues());
        holder.offer_bulls.setText(data.get(p).offer.bulls + "");
        holder.offer_cows.setText(data.get(p).offer.cows + "");
        if(!checkingQuality || data.get(p).quality)
        {
           holder.quality.setVisibility(View.GONE);
        }
        else
        {
            holder.quality.setVisibility(View.VISIBLE);
        }
        if(checkingTime)
        {
            holder.time.setVisibility(View.VISIBLE);
            switch(data.get(p).time)
            {
                case OfferListItem.TIME_OFFER_GOOD:
                    holder.time.setColorFilter(context.getResources().getColor(R.color.green));
                    break;
                case OfferListItem.TIME_OFFER_NEUTRAL:
                    holder.time.setColorFilter(context.getResources().getColor(R.color.gold));
                    break;
                case OfferListItem.TIME_OFFER_BAD:
                    holder.time.setColorFilter(context.getResources().getColor(R.color.red));
                    break;
            }
        }
        else
        {
            holder.time.setVisibility(View.GONE);
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

    public void swapData(ArrayList<OfferListItem> d)
    {
        data = d;
        notifyDataSetChanged();
    }
    public ArrayList<OfferListItem> getData()
    {
        return data;
    }

    public void setCheckingQuality(boolean q)
    {
        checkingQuality = q;
    }
    public void setCheckingTime(boolean t)
    {
        checkingTime = t;
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
        public ImageView time;
        public BullsCowsHolder(View v)
        {
            super(v);
            offer_value = (TextView)v.findViewById(R.id.offer_value);
            offer_bulls = (TextView)v.findViewById(R.id.offer_bulls);
            offer_cows = (TextView)v.findViewById(R.id.offer_cows);
            quality = v.findViewById(R.id.quality);
            time = (ImageView)v.findViewById(R.id.time);
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