package stan.bulls.cows.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import stan.bulls.cows.core.LevelController;
import stan.bulls.cows.core.Levels;

public class PreferenceHelper
{
    static private final String PREFS_KEY = PreferenceHelper.class.getCanonicalName();

    static private final String GOLD_KEY = PREFS_KEY + "." + "gold_key";
    static private final String LEVEL_KEY = PREFS_KEY + "." + "level_key";
    static private final String FIRST_LAUNCH_KEY = PREFS_KEY + "." + "first_launch_key";

    static public void addGold(Context context, int g)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int gold = getGold(context);
        editor.putInt(GOLD_KEY, gold + g);
        editor.commit();
    }
    static public int getGold(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        return preferences.getInt(GOLD_KEY, -1);
    }
    static public void spendGold(Context context, int g)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int gold = getGold(context);
        editor.putInt(GOLD_KEY, gold - g);
        editor.commit();
    }

    static public void levelUp(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int lvl = getLevel(context);
        editor.putInt(LEVEL_KEY, LevelController.getNextLevel(lvl));
        editor.commit();
    }
    static public void resetLevels(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(LEVEL_KEY, Levels.zero);
        editor.commit();
    }
    static public int getLevel(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        return preferences.getInt(LEVEL_KEY, -1);
    }

    static public boolean isFirstLaunch(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        return preferences.getBoolean(FIRST_LAUNCH_KEY, true);
    }
    static public void firstLaunch(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FIRST_LAUNCH_KEY, false);
        editor.commit();
    }
}