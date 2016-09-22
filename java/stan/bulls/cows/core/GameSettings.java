package stan.bulls.cows.core;

import android.util.Log;

import stan.bulls.cows.core.results.CountOfferGameResult;
import stan.bulls.cows.core.results.QualityGameResult;
import stan.bulls.cows.core.results.TimeGameResult;
import stan.bulls.cows.core.results.TimeOfferGameResult;

public class GameSettings
{
    static public final int MAX_GOLD_COINS = 999_999_999;

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
    }

    public int getDifficultLevel()
    {
        return count - 2 + difficult / 3 - 2;
    }
    public int getTimeGame()
    {
        int t = getDifficultLevel();
        t *= getDifficultLevel();
        t += 3*getDifficultLevel();
//        t += 3*(5-getDifficultLevel());
        t += getDifficultLevel()*getDifficultLevel();
        t *= 1000;
        return t;
    }
    public int getTimeOffer()
    {
        return getTimeGame() / getCountOffers() + (getDifficultLevel() - 2) * 4000;
    }
    public int getCountOffers()
    {
        return count + difficult - 2;
    }
}