package stan.bulls.cows.core;

import android.util.Log;

import stan.bulls.cows.core.offers.Offer;
import stan.bulls.cows.logic.BullsCowsLogic;

public class GameController
{
    private GameSettings gameSettings;
    private Offer secret;
    private int offersCount;
    private long date;
    private long dateOffer;
    private GameControllerListener listener;

    public GameController(GameSettings settings, GameControllerListener l)
    {
        gameSettings = settings;
        listener = l;
    }

    private Offer setSecretFromFirstOffer(Offer firstOffer)
    {
        secret = listener.createSecret(gameSettings);
        Offer offer = BullsCowsLogic.checkCountBullsAndCows(firstOffer, secret);
        if(checkWin(offer))
        {
            return setSecretFromFirstOffer(firstOffer);
        }
        return offer;
    }

    public void addOffer(Offer newOffer)
    {
        if(offersCount == 0)
        {
            setSecretFromFirstOffer(newOffer);
            date = System.currentTimeMillis();
            listener.initFromFirstOffer(newOffer);
        }
        else
        {
            BullsCowsLogic.checkCountBullsAndCows(newOffer, secret);
            long timeOffer = System.currentTimeMillis() - dateOffer;
            listener.initFromOffer(gameSettings, newOffer, timeOffer);
        }
        dateOffer = System.currentTimeMillis();
        offersCount++;
        listener.refreshUIFromOffersCount(gameSettings, offersCount);
        if(checkWin(newOffer))
        {
            endWinGame(true);
        }
        else if(checkLose())
        {
            endWinGame(false);
        }
    }

    private boolean checkWin(Offer offer)
    {
        return offer.bulls == secret.getLenght();
    }
    private boolean checkLose()
    {
        if(gameSettings.quality.isEndGame(gameSettings))
        {
            return true;
        }
        if(gameSettings.time_game.isEndGame(gameSettings))
        {
            return true;
        }
        if(gameSettings.time_offer.isEndGame(gameSettings))
        {
            return true;
        }
        return false;
    }
    private void endWinGame(boolean win)
    {
        ResultGame resultGame = new ResultGame();
        resultGame.time_spend = System.currentTimeMillis() - date;
        resultGame.gameSettings = gameSettings;
        if(win)
        {
            resultGame.win = true;
            resultGame.amount_offers = offersCount;
            resultGame.gold_earned = calculateGoldEarned();
        }
        else
        {
            resultGame.win = false;
            resultGame.amount_offers = offersCount;
            resultGame.gold_earned = 0;
        }
        listener.endWinGame(resultGame);
    }
    private int calculateGoldEarned()
    {
        int gold = 0;
        gold += gameSettings.getDifficultLevel() * (gameSettings.count-2);
        Log.e(GameController.class.getCanonicalName(), "calculateGoldEarned gold " + gold);
        gold += calculateGoldEarnedFromResult(gameSettings.quality, gameSettings);
        Log.e(GameController.class.getCanonicalName(), "calculateGoldEarned quality " + gold);
        gold += calculateGoldEarnedFromResult(gameSettings.time_game, gameSettings);
        Log.e(GameController.class.getCanonicalName(), "calculateGoldEarned time_game " + gold);
        gold += calculateGoldEarnedFromResult(gameSettings.time_offer, gameSettings);
        Log.e(GameController.class.getCanonicalName(), "calculateGoldEarned time_offer " + gold);
        if(gold < 0)
        {
            gold = 0;
        }
        return gold;
    }
    private int calculateGoldEarnedFromResult(GameResult result, GameSettings settings)
    {
        int gold = 0;
        if(result.isReward(settings))
        {
            gold += result.getReward(settings);
        }
        else if(result.isMulct(settings))
        {
            gold -= result.getMulct(settings);
        }
        return gold;
    }

    public GameSettings getSettings()
    {
        return gameSettings;
    }
    public long getSpendTimeGame()
    {
        return System.currentTimeMillis() - date;
    }
    public long getSpendTimeOffer()
    {
        return System.currentTimeMillis() - dateOffer;
    }
    public void updateTimeGame()
    {
        gameSettings.time_game.updateResult((int)(System.currentTimeMillis() - date));
    }

    public void checkGameLose()
    {
        if(checkLose())
        {
            endWinGame(false);
        }
    }

    public interface GameControllerListener
    {
        Offer createSecret(GameSettings settings);
        void initFromFirstOffer(Offer newOffer);
        void initFromOffer(GameSettings settings, Offer newOffer, long timeOffer);
        void refreshUIFromOffersCount(GameSettings settings, int count);
        void endWinGame(ResultGame resultGame);
    }
}