package stan.bulls.cows.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import stan.bulls.cows.R;
import stan.bulls.cows.ui.adapters.GreetingsAdapter;
import stan.bulls.cows.ui.fragments.greeting.ShowAddOffer;
import stan.bulls.cows.ui.fragments.greeting.ShowGameDifficults;
import stan.bulls.cows.ui.fragments.greeting.ShowGameSettings;
import stan.bulls.cows.ui.fragments.greeting.ShowGreeting;
import stan.bulls.cows.ui.fragments.greeting.ShowInGame;
import stan.bulls.cows.ui.fragments.greeting.ShowMenuGame;

public class GreetingActivity
        extends AppCompatActivity
{
    //___________________VIEWS
    private ViewPager pager;
    private View arrow_left;
    private View arrow_right;
    private View done;

    //_______________FIELDS
    private GreetingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greeting_activity);
        initViews();
        init();
    }
    private void initViews()
    {
        pager = (ViewPager)findViewById(R.id.pager);
        arrow_left = findViewById(R.id.arrow_left);
        arrow_right = findViewById(R.id.arrow_right);
        done = findViewById(R.id.done);
    }
    private void init()
    {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ShowGreeting());
        fragments.add(new ShowGameDifficults());
        fragments.add(new ShowGameSettings());
        fragments.add(new ShowMenuGame());
        fragments.add(new ShowAddOffer());
        fragments.add(new ShowInGame());
        adapter = new GreetingsAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }
            @Override
            public void onPageSelected(int position)
            {
                arrow_left.setVisibility(View.VISIBLE);
                arrow_right.setVisibility(View.VISIBLE);
                done.setVisibility(View.GONE);
                if(position == 0)
                {
                    arrow_left.setVisibility(View.GONE);
                }
                if(position == adapter.getCount()-1)
                {
                    arrow_right.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        arrow_left.setVisibility(View.GONE);
        arrow_right.setVisibility(View.VISIBLE);
        done.setVisibility(View.GONE);
        arrow_left.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pager.setCurrentItem(pager.getCurrentItem()-1);
            }
        });
        arrow_right.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pager.setCurrentItem(pager.getCurrentItem()+1);
            }
        });
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

}