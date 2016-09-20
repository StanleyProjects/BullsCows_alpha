package stan.bulls.cows.helpers.ui;

import stan.bulls.cows.R;
import stan.bulls.cows.core.Levels;

public class LevelsNamesHelper
{
    static public int getLevelName(int lvl)
    {
        switch(lvl)
        {
            case Levels.zero:
                return R.string.level_name_zero;
            case Levels.bronze:
                return R.string.level_name_bronze;
            case Levels.silver:
                return R.string.level_name_silver;
            case Levels.diamond:
                return R.string.level_name_diamond;
            case Levels.master:
                return R.string.level_name_master;
            case Levels.godlike:
                return R.string.level_name_godlike;
        }
        return -1;
    }
}