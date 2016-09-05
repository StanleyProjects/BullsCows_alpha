package stan.bulls.cows.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper
{
    static private final String PREFS_KEY = PreferenceHelper.class.getCanonicalName();

    static private final String GOLD_KEY = PREFS_KEY + "." + "gold_key";

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
}