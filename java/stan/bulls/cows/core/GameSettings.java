package stan.bulls.cows.core;

public class GameSettings
{
    public int count;
    public int difficult;
    public int quality;

    public GameSettings()
    {
        quality = 0;
    }

    public int getDifficultLevel()
    {
        return count-2 + difficult/3 - 2;
    }
    public int getTimeGame()
    {
        return (count-2)*(count-2)*(difficult/3)*10*1000;
    }

    public boolean isQualityReward()
    {
        if(getDifficultLevel()<1)
        {
            return false;
        }
        int r = (getDifficultLevel()+1)/2;
        return quality <= r/4;
    }
    public boolean isQualityMulct()
    {
        if(getDifficultLevel()<3 || isQualityReward() || isQualityEndGame())
        {
            return false;
        }
        int m = (getDifficultLevel()+1)/2;
        return quality >= (m/2)*2;
    }
    public boolean isQualityEndGame()
    {
        if(getDifficultLevel()<5)
        {
            return false;
        }
        int e = (getDifficultLevel()+1)/2;
        return quality >= e;
    }
    public int getQualityReward()
    {
        return getDifficultLevel()*(count-2);
    }
    public int getQualityMulct()
    {
        return getDifficultLevel()*(count-2)/2 + (count-2)*(difficult/3)/2;
    }
}