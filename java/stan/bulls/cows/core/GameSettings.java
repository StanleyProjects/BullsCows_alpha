package stan.bulls.cows.core;

public class GameSettings
{
    public int count;
    public int difficult;

    public int getDifficultLevel()
    {
        return (count + difficult - 4)/2;
    }
    public int getTimeGame()
    {
        return 3 * getDifficultLevel() * 15
                / ((7 - count + 1)/2)
                / ((6 - getDifficultLevel() + 1)/2)
                * 1000;
    }
}