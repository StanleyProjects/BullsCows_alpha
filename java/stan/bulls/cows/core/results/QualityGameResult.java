package stan.bulls.cows.core.results;

import stan.bulls.cows.core.GameResult;
import stan.bulls.cows.core.GameSettings;

public class QualityGameResult
    implements GameResult
{
    private int result;

    public int getResult()
    {
        return result;
    }
    public void updateResult(int r)
    {
        result = r;
    }

    @Override
    public boolean isReward(GameSettings settings)
    {
        if(settings.getDifficultLevel()<1)
        {
            return false;
        }
        int r = (settings.getDifficultLevel()+1)/2;
        return getResult() <= r/4;
    }

    @Override
    public boolean isMulct(GameSettings settings)
    {
        if(settings.getDifficultLevel()<3 || isReward(settings) || isEndGame(settings))
        {
            return false;
        }
        int m = (settings.getDifficultLevel()+1)/2;
        return getResult() >= (m/2)*2;
    }

    @Override
    public boolean isEndGame(GameSettings settings)
    {
        if(settings.getDifficultLevel()<5)
        {
            return false;
        }
        int e = (settings.getDifficultLevel()+1)/2;
        return getResult() >= e;
    }

    @Override
    public int getReward(GameSettings settings)
    {
        return settings.getDifficultLevel()*settings.getDifficultLevel();
    }

    @Override
    public int getMulct(GameSettings settings)
    {
        return settings.getDifficultLevel()*(settings.count-2)/2
                + (settings.count-2)*(settings.difficult/3)/2
                + settings.getCountOffers()/(settings.difficult/3)
                + settings.getDifficultLevel();
    }
}