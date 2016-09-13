package stan.bulls.cows.core;

public interface GameResult
{
    boolean isReward(GameSettings settings);
    boolean isMulct(GameSettings settings);
    boolean isEndGame(GameSettings settings);
    int getReward(GameSettings settings);
    int getMulct(GameSettings settings);
}