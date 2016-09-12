package stan.bulls.cows.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import stan.bulls.cows.R;
import stan.bulls.cows.core.Difficults;
import stan.bulls.cows.core.GameSettings;
import stan.bulls.cows.helpers.PreferenceHelper;
import stan.bulls.cows.ui.activities.GameActivity;
import stan.bulls.cows.ui.views.selectors.DifficultSelector;

public class SandBoxFragment
        extends Fragment
{
    //___________________VIEWS
    private TextView gold;
    private TextView game_count_value;
    private SeekBar game_count_seek;
    private DifficultSelector difficult;
    private TextView game_max_amount_text;
    private View easy_lable;
    private View check_quality;
    private View note_the_time;
    private View note_the_time_offer;
    private View with_mulct;
    private View check_count_offers;
    private View can_lose;

    //___________________FIELDS
    private int countMax = 6;
    private int countMin = 3;
    private GameSettings gameSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.sandbox_fragment, container, false);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        gold = (TextView)v.findViewById(R.id.gold);
        difficult = (DifficultSelector)v.findViewById(R.id.difficult);
        game_max_amount_text = (TextView)v.findViewById(R.id.game_max_amount_text);
        game_count_value = (TextView) v.findViewById(R.id.game_count_value);
        game_count_seek = (SeekBar) v.findViewById(R.id.game_count_seek);
        v.findViewById(R.id.go).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startGame();
            }
        });
        easy_lable = v.findViewById(R.id.easy_lable);
        check_quality = v.findViewById(R.id.check_quality);
        note_the_time = v.findViewById(R.id.note_the_time);
        note_the_time_offer = v.findViewById(R.id.note_the_time_offer);
        with_mulct = v.findViewById(R.id.with_mulct);
        check_count_offers = v.findViewById(R.id.check_count_offers);
        can_lose = v.findViewById(R.id.can_lose);
    }
    private void init()
    {
        difficult.setListener(new DifficultSelector.DifficultListener()
        {
            @Override
            public void setDifficult(int dif)
            {
                gameSettings.difficult = dif;
                updateFromGameSettings();
            }
        });
        game_count_seek.setMax(countMax-countMin);
        game_count_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                setCount(i+countMin);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
        gameSettings = new GameSettings(countMin, Difficults.DIFFICULT_EASY);
        refreshGold();
        updateFromGameSettings();
    }

    private void setCount(int c)
    {
        if(c >= countMin && c <= countMax)
        {
            gameSettings.count = c;
        }
        updateFromGameSettings();
    }

    private void updateFromGameSettings()
    {
        game_max_amount_text.setText(gameSettings.difficult+"");
        game_count_value.setText(""+gameSettings.count);
        int d = gameSettings.getDifficultLevel();
        Log.e(GameFragment.class.getCanonicalName(), "dif = " + d);
        easy_lable.setVisibility(View.GONE);
        check_quality.setVisibility(View.GONE);
        note_the_time.setVisibility(View.GONE);
        note_the_time_offer.setVisibility(View.GONE);
        with_mulct.setVisibility(View.GONE);
        check_count_offers.setVisibility(View.GONE);
        can_lose.setVisibility(View.GONE);
        switch (d)
        {
            case 5:
                can_lose.setVisibility(View.VISIBLE);
            case 4:
                check_count_offers.setVisibility(View.VISIBLE);
            case 3:
                with_mulct.setVisibility(View.VISIBLE);
                note_the_time_offer.setVisibility(View.VISIBLE);
            case 2:
                note_the_time.setVisibility(View.VISIBLE);
            case 1:
                check_quality.setVisibility(View.VISIBLE);
                break;
            case 0:
                easy_lable.setVisibility(View.VISIBLE);
        }
    }

    public void refreshGold()
    {
        gold.setText(PreferenceHelper.getGold(getActivity()) + "");
    }

    private void startGame()
    {
        GameActivity.startGame(getActivity(), gameSettings.count, gameSettings.difficult);
    }
}