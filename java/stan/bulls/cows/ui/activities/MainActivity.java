package stan.bulls.cows.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import stan.bulls.cows.BuildConfig;
import stan.bulls.cows.R;
import stan.bulls.cows.core.GameSettings;
import stan.bulls.cows.core.LevelController;
import stan.bulls.cows.core.Levels;
import stan.bulls.cows.helpers.PreferenceHelper;
import stan.bulls.cows.helpers.ui.LevelsNamesHelper;
import stan.bulls.cows.ui.fragments.SandBoxFragment;
import stan.bulls.cows.ui.fragments.dialogs.LevelUpDialog;

public class MainActivity
        extends AppCompatActivity
{
    //___________________VIEWS
    private DrawerLayout main_drawer;
    private TextView gold;
    private TextView for_coins;
    private TextView level_name;
    private View buy_next_level_container;
    private View buy_next_level;

    //___________________FRAGMENTS
    private final SandBoxFragment sandBoxFragment = new SandBoxFragment();

    //___________________FIELDS
    private View.OnClickListener clickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.test:
                    test();
                    break;
                case R.id.menu:
                    menu();
                    break;
                case R.id.rate_app:
                    rateApp();
                    break;
                case R.id.share_progress:
                    shareProgress();
                    break;
                case R.id.get_help:
                    getHelp();
                    break;
                case R.id.kill_progress:
                    killProgress();
                    break;
            }
        }
    };

    private String help_url;

    @Override
    protected void onActivityResult(int request, int result, Intent intent)
    {
        if(request == GameActivity.REQUEST_CODE)
        {
            switch(result)
            {
                case GameActivity.RESULT_ERROR:
                    break;
                case GameActivity.RESULT_WIN:
                    refreshGold();
                    break;
                case GameActivity.RESULT_LOSE:
                    break;
            }
        }
        super.onActivityResult(request, result, intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initViews();
        init();
    }
    private void initViews()
    {
        main_drawer = (DrawerLayout)findViewById(R.id.main_drawer);
        gold = (TextView)findViewById(R.id.gold);
        for_coins = (TextView)findViewById(R.id.for_coins);
        level_name = (TextView)findViewById(R.id.level_name);
        buy_next_level_container = findViewById(R.id.buy_next_level_container);
        buy_next_level = findViewById(R.id.buy_next_level);
        buy_next_level.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                buyNextLevel();
            }
        });
        findViewById(R.id.test).setOnClickListener(clickListener);
        findViewById(R.id.menu).setOnClickListener(clickListener);
        findViewById(R.id.rate_app).setOnClickListener(clickListener);
        findViewById(R.id.share_progress).setOnClickListener(clickListener);
        findViewById(R.id.get_help).setOnClickListener(clickListener);
        findViewById(R.id.kill_progress).setOnClickListener(clickListener);
    }
    private void init()
    {
        help_url = getResources().getString(R.string.help_url);
        if(PreferenceHelper.getGold(this) == -1)
        {
            PreferenceHelper.addGold(this, 1);
        }
        if(PreferenceHelper.getLevel(this) == -1)
        {
            PreferenceHelper.levelUp(this);
        }
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.main_frame, sandBoxFragment)
                                   .commit();
        refreshGold();
        if(PreferenceHelper.isFirstLaunch(this))
        {
            PreferenceHelper.firstLaunch(this);
            startActivity(new Intent(this, GreetingActivity.class));
        }
    }

    public void refreshGold()
    {
        int g = PreferenceHelper.getGold(this);
        if(g < GameSettings.MAX_GOLD_COINS)
        {
            gold.setText(g + "");
        }
        else
        {
            gold.setText(R.string.much);
        }
        int lvl = PreferenceHelper.getLevel(this);
        level_name.setText(LevelsNamesHelper.getLevelName(lvl));
        buy_next_level_container.setVisibility(View.VISIBLE);
        switch(lvl)
        {
            case Levels.godlike:
                buy_next_level_container.setVisibility(View.INVISIBLE);
                return;
        }
        int nextlvl = LevelController.getNextLevel(lvl);
        String text = this.getResources()
                          .getString(R.string.for_coins, LevelController.getLevelPrice(nextlvl));
        for_coins.setText(Html.fromHtml(text));
    }

    private void buyNextLevel()
    {
        int g = PreferenceHelper.getGold(this);
        int lvl = PreferenceHelper.getLevel(this);
        int nextlvl = LevelController.getNextLevel(lvl);
        int p = LevelController.getLevelPrice(nextlvl);
        if(p > g)
        {
            Toast.makeText(this, R.string.need_more_gold, Toast.LENGTH_SHORT)
                 .show();
            return;
        }
        PreferenceHelper.spendGold(this, p);
        PreferenceHelper.levelUp(this);
        refreshGold();
        LevelUpDialog.newInstance()
                     .show(this.getSupportFragmentManager(), LevelUpDialog.class.getCanonicalName());
        sandBoxFragment.updateFromLevel();
    }

    private void test()
    {
        PreferenceHelper.addGold(MainActivity.this, 50);
        refreshGold();
    }

    private void menu()
    {
        main_drawer.openDrawer(GravityCompat.START);
    }

    private void rateApp()
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
    }

    private void shareProgress()
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void getHelp()
    {
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(help_url)));
        startActivity(new Intent(this, GreetingActivity.class));
    }

    private void killProgress()
    {
        PreferenceHelper.spendGold(MainActivity.this, PreferenceHelper.getGold(MainActivity.this));
        PreferenceHelper.resetLevels(MainActivity.this);
        refreshGold();
        sandBoxFragment.updateFromLevel();
    }
}