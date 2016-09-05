package stan.bulls.cows.core.offers;

public class NumberOfferElement
        implements OfferElement
{
    private int value;

    public NumberOfferElement(int v)
    {
        this.value = v;
    }

    public int getValue()
    {
        return this.value;
    }
    public void setValue(int v)
    {
        this.value = v;
    }

    @Override
    public boolean isEquals(OfferElement offerElement)
    {
        if(offerElement instanceof NumberOfferElement)
        {
            return this.value == ((NumberOfferElement)offerElement).getValue();
        }
        return false;
    }
}