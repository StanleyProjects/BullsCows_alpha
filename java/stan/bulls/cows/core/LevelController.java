package stan.bulls.cows.core;

public class LevelController
{
    static public int getLevelPrice(int lvl)
    {
        switch(lvl)
        {
            case Levels.bronze:
                return 16;
            case Levels.silver:
                return 80;
            case Levels.diamond:
                return 210;
            case Levels.master:
                return 768;
            case Levels.godlike:
                return 2048;
        }
        return -1;
    }
    static public int getNextLevel(int lvl)
    {
        switch(lvl)
        {
            case Levels.zero:
                return Levels.bronze;
            case Levels.bronze:
                return Levels.silver;
            case Levels.silver:
                return Levels.diamond;
            case Levels.diamond:
                return Levels.master;
            case Levels.master:
                return Levels.godlike;
        }
        return Levels.zero;
    }
}