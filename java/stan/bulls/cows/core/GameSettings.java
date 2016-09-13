package stan.bulls.cows.core;

import android.util.Log;

import stan.bulls.cows.core.results.QualityGameResult;
import stan.bulls.cows.core.results.TimeGameResult;
import stan.bulls.cows.core.results.TimeOfferGameResult;

public class GameSettings
{
    public QualityGameResult quality = new QualityGameResult();
    public TimeGameResult time_game = new TimeGameResult();
    public TimeOfferGameResult time_offer = new TimeOfferGameResult();
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
    }

    public int getDifficultLevel()
    {
        return count-2 + difficult/3 - 2;
    }
    public int getTimeGame()
    {
        return (count-2)*(count-2)*(difficult/3)*10*1000;
    }
    public int getTimeOffer()
    {
        return (getTimeGame()/getCountOffers());
    }
    public int getCountOffers()
    {
//        return 3 * (difficult/3) + ((count-2)+2)/2 + ((difficult/3)+2)/2;
        return count + difficult;
    }
}