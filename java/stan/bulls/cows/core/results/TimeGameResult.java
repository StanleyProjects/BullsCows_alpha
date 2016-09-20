package stan.bulls.cows.core.results;

import stan.bulls.cows.core.GameResult;
import stan.bulls.cows.core.GameSettings;

public class TimeGameResult
        implements GameResult
{
    private int time;
    @Override
    public boolean isReward(GameSettings settings)
    {
        if(settings.getDifficultLevel()<2)
        {
            return false;
        }
        int r = settings.getTimeGame() * settings.getDifficultLevel();
        r -= settings.getTimeGame() * ((settings.getDifficultLevel()-1)/2);
        r /= settings.getDifficultLevel();
        return time <= r;
    }

    @Override
    public boolean isMulct(GameSettings settings)
    {
        if(settings.getDifficultLevel()<3 || isReward(settings) || isEndGame(settings))
        {
            return false;
        }
        int m = settings.getTimeGame();
        m -= settings.getTimeGame() / ((8-settings.getDifficultLevel())*2);
        return time >= m;
    }

    @Override
    public boolean isEndGame(GameSettings settings)
    {
        if(settings.getDifficultLevel()<5)
        {
            return false;
        }
        return getResult() >= settings.getTimeGame();
    }

    @Override
    public int getReward(GameSettings settings)
    {
        return settings.getDifficultLevel()*settings.getDifficultLevel();
    }

    @Override
    public int getMulct(GameSettings settings)
    {
        return settings.getDifficultLevel()*(settings.count-1)/2 + (settings.count-1)*(settings.difficult/3)/2 + settings.getDifficultLevel();
    }

    public int getResult()
    {
        return time;
    }
    public void updateResult(int r)
    {
        time = r;
    }
}