package stan.bulls.cows.models;

import stan.bulls.cows.core.offers.Offer;

public class OfferListItem
{
    static public final int TIME_OFFER_GOOD = 1;
    static public final int TIME_OFFER_NEUTRAL = 2;
    static public final int TIME_OFFER_BAD = 3;

    public Offer offer;
    public boolean quality;
    public int time;
}