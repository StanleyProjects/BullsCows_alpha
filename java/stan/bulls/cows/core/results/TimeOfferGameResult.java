package stan.bulls.cows.core.results;

import stan.bulls.cows.core.GameResult;
import stan.bulls.cows.core.GameSettings;

public class TimeOfferGameResult
        implements GameResult
{
    int goodOffer;
    int neutralOffer;
    int badOffer;

    int reward;

    public TimeOfferGameResult()
    {
        goodOffer = 0;
        badOffer = 0;
        reward = 0;
    }

    @Override
    public boolean isReward(GameSettings settings)
    {
        return reward > 0;
    }

    @Override
    public boolean isMulct(GameSettings settings)
    {
        return reward < 0;
    }

    @Override
    public boolean isEndGame(GameSettings settings)
    {
        if(settings.getDifficultLevel()<5)
        {
            return false;
        }
        return badOffer > 3;
    }

    @Override
    public int getReward(GameSettings settings)
    {
        if(reward < 0)
        {
            return 0;
        }
        if(badOffer == 0 && neutralOffer == 0)
        {
            return reward*2;
        }
        if(neutralOffer == 0)
        {
            return reward + settings.count;
        }
        if(badOffer == 0)
        {
            return reward + settings.count + settings.difficult;
        }
        return reward;
    }

    @Override
    public int getMulct(GameSettings settings)
    {
        if(reward > 0)
        {
            return 0;
        }
        return reward * -1;
    }

    public void addGoodOffer(int i, GameSettings settings)
    {
        goodOffer++;
        int r = settings.getDifficultLevel()*(settings.count-2);
        r /= settings.getCountOffers() - i;
//        r /= settings.difficult/3;
        reward += r;
    }
    public void addNeutralOffer(int i, GameSettings settings)
    {
        neutralOffer++;
    }
    public void addBadOffer(int i, GameSettings settings)
    {
        badOffer++;
        reward -= settings.getDifficultLevel()*(settings.count-2)*(settings.difficult/3) / (settings.getCountOffers() - (settings.count-2) - (i/2));
    }
}