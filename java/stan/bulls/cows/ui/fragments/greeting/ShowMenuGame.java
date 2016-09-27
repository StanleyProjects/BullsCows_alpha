package stan.bulls.cows.ui.fragments.greeting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import stan.bulls.cows.R;

public class ShowMenuGame
        extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.show_menu_game, container, false);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {

    }
    private void init()
    {

    }
}