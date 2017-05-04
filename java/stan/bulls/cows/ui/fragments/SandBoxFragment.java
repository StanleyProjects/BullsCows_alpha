package stan.bulls.cows.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import stan.bulls.cows.R;
import stan.bulls.cows.core.Difficults;
import stan.bulls.cows.core.GameSettings;
import stan.bulls.cows.core.Levels;
import stan.bulls.cows.helpers.PreferenceHelper;
import stan.bulls.cows.ui.activities.GameActivity;
import stan.bulls.cows.ui.views.selectors.DifficultPrimarySelector;

public class SandBoxFragment
        extends Fragment
{
    //___________________VIEWS
    private AdView ad_view;
    private TextView game_count_value;
    private View game_count_frame;
    private SeekBar game_count_seek;
//    private DifficultSelector difficult;
    private DifficultPrimarySelector difficult;
    private TextView game_max_amount_text;
    private View easy_lable;
    private View check_quality;
    private View note_the_time;
    private View note_the_time_offer;
    private View with_mulct;
    private View check_count_offers;
    private View can_lose;

    //___________________FIELDS
    private GameSettings gameSettings;
    private String banner_ad_unit_id;

    @Override
    public void onPause()
    {
        if(ad_view != null)
        {
            ad_view.pause();
        }
        super.onPause();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if(ad_view != null)
        {
            ad_view.resume();
        }
    }
    @Override
    public void onDestroy()
    {
        if(ad_view != null)
        {
            ad_view.destroy();
        }
        super.onDestroy();
    }
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
        ad_view = (AdView)v.findViewById(R.id.ad_view);
//        difficult = (DifficultSelector)v.findViewById(R.id.difficult);
        difficult = (DifficultPrimarySelector)v.findViewById(R.id.difficult);
        game_max_amount_text = (TextView)v.findViewById(R.id.game_max_amount_text);
        game_count_value = (TextView) v.findViewById(R.id.game_count_value);
        game_count_frame = v.findViewById(R.id.game_count_frame);
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
        banner_ad_unit_id = getActivity().getResources().getString(R.string.banner_ad_unit_id);
//        difficult.setListener(new DifficultSelector.DifficultListener()
        difficult.setListener(new DifficultPrimarySelector.DifficultListener()
        {
            @Override
            public void setDifficult(int dif)
            {
                gameSettings.difficult = dif;
                updateFromGameSettings();
            }
        });
//        game_count_seek.setMax(countMax-countMin);
        game_count_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                setCount(i+Difficults.MIN_COUNT);
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
        gameSettings = new GameSettings(Difficults.MIN_COUNT, Difficults.DIFFICULT_EASY);
        updateFromLevel();
        updateFromGameSettings();
//        ad_view.setVisibility(View.GONE);
    }

    private void setCount(int c)
    {
        if(c >= Difficults.MIN_COUNT)
        {
            gameSettings.count = c;
        }
        updateFromGameSettings();
    }

    private void updateFromGameSettings()
    {
//        game_max_amount_text.setText(gameSettings.difficult+"");
        String amount_text = this.getResources().getString(R.string.combination_element_can_be, gameSettings.difficult);
        game_max_amount_text.setText(Html.fromHtml(amount_text.replace("\n", "<br>")));
//        game_count_value.setText(""+gameSettings.count);
        String count_value = this.getResources().getString(R.string.count_elements, gameSettings.count);
        game_count_value.setText(Html.fromHtml(count_value));
        int d = gameSettings.getDifficultLevel();
        Log.e(GameFragment.class.getCanonicalName(), "dif = " + d);
        easy_lable.setVisibility(View.GONE);
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
        switch (d)
        {
            case 0:
                check_quality.setVisibility(View.GONE);
            case 1:
                note_the_time.setVisibility(View.GONE);
            case 2:
                note_the_time_offer.setVisibility(View.GONE);
                with_mulct.setVisibility(View.GONE);
            case 3:
                check_count_offers.setVisibility(View.GONE);
            case 4:
                can_lose.setVisibility(View.GONE);
        }
    }
    public void updateFromLevel()
    {
        int lvl = PreferenceHelper.getLevel(getActivity());
        difficult.showHard(false);
        game_count_frame.setVisibility(View.VISIBLE);
        gameSettings.difficult = Difficults.DIFFICULT_MEDIUM;
        switch(lvl)
        {
            case Levels.master:
                game_count_seek.setMax(Difficults.MAX_COUNT_MASTER - Difficults.MIN_COUNT);
                break;
            case Levels.diamond:
            case Levels.silver:
                game_count_seek.setMax(Difficults.MAX_COUNT_SILVER - Difficults.MIN_COUNT);
                break;
            case Levels.bronze:
                game_count_seek.setMax(Difficults.MAX_COUNT_BRONZE - Difficults.MIN_COUNT);
                break;
            case Levels.zero:
                game_count_seek.setMax(Difficults.MIN_COUNT);
                game_count_frame.setVisibility(View.GONE);
        }
        switch(lvl)
        {
            case Levels.godlike:
            case Levels.master:
            case Levels.diamond:
                difficult.showHard(true);
        }
        gameSettings.count = Difficults.MIN_COUNT;
        gameSettings.difficult = Difficults.DIFFICULT_EASY;
        game_count_seek.setProgress(0);
        difficult.reset();
        updateAd();
    }
    private void updateAd()
    {
        ad_view.post(new Runnable()
        {
            @Override
            public void run()
            {
                if(PreferenceHelper.getLevel(getActivity()) == Levels.godlike)
                {
                    ad_view.setVisibility(View.GONE);
                    return;
                }
                MobileAds.initialize(getActivity(), banner_ad_unit_id);
                ad_view.loadAd(new AdRequest.Builder().build());
                ad_view.setVisibility(View.VISIBLE);
            }
        });
    }

    private void startGame()
    {
        GameActivity.startGame(getActivity(), gameSettings.count, gameSettings.difficult);
    }
}