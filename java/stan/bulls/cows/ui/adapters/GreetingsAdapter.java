package stan.bulls.cows.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class GreetingsAdapter
        extends FragmentPagerAdapter
{
    private ArrayList<Fragment> fragments;

    public GreetingsAdapter(FragmentManager fm, ArrayList<Fragment> fs)
    {
        super(fm);
        this.fragments = fs;
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount()
    {
        if(fragments == null)
        {
            return 0;
        }
        return fragments.size();
    }
}