package stan.bulls.cows.ui.fragments.greeting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import stan.bulls.cows.R;
import stan.bulls.cows.core.Difficults;
import stan.bulls.cows.core.GameSettings;
import stan.bulls.cows.ui.views.selectors.DifficultPrimarySelector;

public class ShowGameDifficults
        extends Fragment
{
    //___________________VIEWS
    private TextView game_count_value;
    private SeekBar game_count_seek;
    private DifficultPrimarySelector difficult;
    private TextView game_max_amount_text;

    //___________________FIELDS
    private GameSettings gameSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.show_game_difficults, container, false);
        initViews(v);
        init();
        return v;
    }

    private void initViews(View v)
    {
        difficult = (DifficultPrimarySelector)v.findViewById(R.id.difficult);
        game_max_amount_text = (TextView)v.findViewById(R.id.game_max_amount_text);
        game_count_value = (TextView)v.findViewById(R.id.game_count_value);
        game_count_seek = (SeekBar)v.findViewById(R.id.game_count_seek);
    }

    private void init()
    {
        difficult.setListener(new DifficultPrimarySelector.DifficultListener()
        {
            @Override
            public void setDifficult(int dif)
            {
                gameSettings.difficult = dif;
                updateFromGameSettings();
            }
        });
        game_count_seek.setMax(Difficults.MAX_COUNT_MASTER - Difficults.MIN_COUNT);
        game_count_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                if(i + Difficults.MIN_COUNT >= Difficults.MIN_COUNT)
                {
                    gameSettings.count = i + Difficults.MIN_COUNT;
                    updateFromGameSettings();
                }
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
        updateFromGameSettings();
    }

    private void updateFromGameSettings()
    {
        String amount_text = this.getResources()
                                 .getString(R.string.combination_element_can_be, gameSettings.difficult);
        game_max_amount_text.setText(Html.fromHtml(amount_text.replace("\n", "<br>")));
        String count_value = this.getResources()
                                 .getString(R.string.count_elements, gameSettings.count);
        game_count_value.setText(Html.fromHtml(count_value));
    }
}