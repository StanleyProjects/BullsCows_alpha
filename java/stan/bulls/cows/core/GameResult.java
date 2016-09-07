package stan.bulls.cows.core;

public interface GameResult
{
    boolean isReward();
    boolean isMulct();
    boolean isEndGame();
    int getReward();
    int getMulct();
    int getResult();
    void updateResult(int r);
}