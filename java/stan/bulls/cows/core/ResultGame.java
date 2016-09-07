package stan.bulls.cows.core;

public class ResultGame
{
    public boolean win;
    public int gold_earned;
    public long time_spend;
    public int amount_offers;
    public GameSettings gameSettings;

    public ResultGame()
    {
        this.win = false;
    }
}