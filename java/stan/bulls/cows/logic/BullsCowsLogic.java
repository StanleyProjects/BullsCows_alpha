package stan.bulls.cows.logic;

import java.util.ArrayList;

import stan.bulls.cows.core.offers.Offer;

public class BullsCowsLogic
{
    static public <OFFER_TYPE extends Offer> OFFER_TYPE checkCountBullsAndCows(OFFER_TYPE offer, Offer secret)
    {
        boolean[] bulls = new boolean[offer.getLenght()];
        boolean[] cows = new boolean[offer.getLenght()];
        for(int i = 0; i < offer.getLenght(); i++)
        {
            if(offer.getOfferElement(i).isEquals(secret.getOfferElement(i)))
            {
                offer.bulls++;
                bulls[i] = true;
            }
        }
        for(int i = 0; i < offer.getLenght(); i++)
        {
            if(bulls[i])
            {
                continue;
            }
            for(int j = 0; j < offer.getLenght(); j++)
            {
                if(bulls[j])
                {
                    continue;
                }
                if(cows[j])
                {
                    continue;
                }
                if(offer.getOfferElement(i).isEquals(secret.getOfferElement(j)))
                {
                    offer.cows++;
                    cows[j] = true;
                    break;
                }
            }
        }
        return offer;
    }

    static public boolean checkQualityOffer(Offer offer, ArrayList<Offer> data)
    {
        for(int i = 0; i<data.size(); i++)
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
}