package stan.bulls.cows.core.results;

import stan.bulls.cows.core.GameResult;
import stan.bulls.cows.core.GameSettings;

public class CountOfferGameResult
        implements GameResult
{
    private int count;
    @Override
    public boolean isReward(GameSettings settings)
    {
        if(settings.getDifficultLevel()<4)
        {
            return false;
        }
        int r = settings.getCountOffers() - settings.count+2;
        return count <= r;
    }
    @Override
    public boolean isMulct(GameSettings settings)
    {
        if(settings.getDifficultLevel()<4 || isReward(settings) || isEndGame(settings))
        {
            return false;
        }
        int m = settings.getCountOffers() - (settings.count-2)/2;
        return count >= m;
    }
    @Override
    public boolean isEndGame(GameSettings settings)
    {
        if(settings.getDifficultLevel()<5)
        {
            return false;
        }
        return getResult() >= settings.getCountOffers();
    }
    @Override
    public int getReward(GameSettings settings)
    {
        return settings.getDifficultLevel()*(settings.count-1);
    }
    @Override
    public int getMulct(GameSettings settings)
    {
        return settings.getDifficultLevel()*(settings.count-1)/2
                + (settings.count-1)*(settings.difficult/3)/2
                + settings.getCountOffers()*(settings.difficult/3);
    }

    public int getResult()
    {
        return count;
    }
    public void updateResult(int r)
    {
        count = r;
    }
}