package stan.bulls.cows.core.offers;

public class NumberOffer
    extends Offer<NumberOfferElement>
{
    public NumberOffer(NumberOfferElement[] oe)
    {
        super(oe);
    }

    @Override
    public String getStringValues()
    {
        String values = "";
        for(NumberOfferElement oe : getOfferElements())
        {
            values += oe.getValue() + "";
        }
        return values;
    }
}