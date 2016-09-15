package stan.bulls.cows.core;

import android.util.Log;

import stan.bulls.cows.core.results.CountOfferGameResult;
import stan.bulls.cows.core.results.QualityGameResult;
import stan.bulls.cows.core.results.TimeGameResult;
import stan.bulls.cows.core.results.TimeOfferGameResult;

public class GameSettings
{
    public QualityGameResult quality = new QualityGameResult();
    public TimeGameResult time_game = new TimeGameResult();
    public TimeOfferGameResult time_offer = new TimeOfferGameResult();
    public CountOfferGameResult count_offers = new CountOfferGameResult();
    public int count;
    public int difficult;

    public GameSettings(int c, int a)
    {
        count = c;
        difficult = a;
        test();
    }

    private void test()
    {
        Log.e("GameSettings", "DifficultLevel " + getDifficultLevel());
        Log.e("GameSettings", "TimeGame " + getTimeGame());
        Log.e("GameSettings", "TimeOffer " + getTimeOffer());
        Log.e("GameSettings", "CountOffers " + getCountOffers());
        Log.e("quality", "isReward " + (getDifficultLevel()+1)/2/4);
        Log.e("quality", "isMulct " + ((getDifficultLevel()+1)/2/2)*2);
        int e = (getDifficultLevel()+1)/2;
        Log.e("quality", "isEndGame " + e);
        Log.e("quality", "getMulct " + quality.getMulct(this));
        Log.e("quality", "getReward " + quality.getReward(this));
//        int r = getTimeGame() * getDifficultLevel();
//        r -= getTimeGame() * ((getDifficultLevel()-1)/2);
//        r /= getDifficultLevel();
//        Log.e("time_game", "isReward " + r);
        int m = getTimeGame();
        m -= getTimeGame() / ((8-getDifficultLevel())*2);
        Log.e("time_game", "isMulct " + m);
        Log.e("time_game", "getMulct " + time_game.getMulct(this));
        Log.e("time_game", "getReward " + time_game.getReward(this));
        Log.e("count_offers", "isReward " + count_offers.isReward(this));
        Log.e("count_offers", "isMulct " + count_offers.isMulct(this));
        Log.e("count_offers", "getMulct " + count_offers.getMulct(this));
        Log.e("count_offers", "getReward " + count_offers.getReward(this));
    }

    public int getDifficultLevel()
    {
        return count-2 + difficult/3 - 2;
    }
    public int getTimeGame()
    {
        int t = (count-2)*(count-2);
        t *= (difficult/3);
//        t *= 18 - count*3;
        t *= 1000;
//        t += (7-getDifficultLevel()) * 1000;
        if(difficult > count)
        {
            t += difficult*1000;
            t -= (count-3)*6*1000;
        }
        return t;
    }
    public int getTimeOffer()
    {
        return getTimeGame()/getCountOffers()
            + (getDifficultLevel()-2)*5000;
    }
    public int getCountOffers()
    {
//        return 3 * (difficult/3) + ((count-2)+2)/2 + ((difficult/3)+2)/2;
        return count + difficult - 2;
    }
}