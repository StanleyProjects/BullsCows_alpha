package stan.bulls.cows.core.offers;

public abstract class Offer<OFFER_TYPE extends OfferElement>
{
    private OFFER_TYPE[] offerElements;
    public int bulls;
    public int cows;

    public Offer(OFFER_TYPE[] oe)
    {
        offerElements = oe;
    }

    public int getLenght()
    {
        return this.offerElements.length;
    }
    public OFFER_TYPE getOfferElement(int i)
    {
        return this.offerElements[i];
    }
    public void setOfferElement(int i, OFFER_TYPE offerElement)
    {
        this.offerElements[i] = offerElement;
    }
    public OFFER_TYPE[] getOfferElements()
    {
        return offerElements;
    }

    public abstract String getStringValues();
}