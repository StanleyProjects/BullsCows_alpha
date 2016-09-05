package stan.bulls.cows.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import stan.bulls.cows.R;
import stan.bulls.cows.core.Difficults;
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

    //___________________FIELDS
    private int countMax = 6;
    private int countMin = 3;
    private int count;
    private int amountDifficult;

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
    }
    private void init()
    {
        difficult.setListener(new DifficultSelector.DifficultListener()
        {
            @Override
            public void setDifficult(int dif)
            {
                amountDifficult = dif;
                game_max_amount_text.setText(amountDifficult+"");
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
        amountDifficult = Difficults.DIFFICULT_EASY;
        game_max_amount_text.setText(amountDifficult+"");
        count = countMin;
        game_count_value.setText(""+count);
        refreshGold();
    }

    private void setCount(int c)
    {
        if(c >= countMin && c <= countMax)
        {
            count = c;
            game_count_value.setText(""+count);
        }
    }

    public void refreshGold()
    {
        gold.setText(PreferenceHelper.getGold(getActivity()) + "");
    }

    private void startGame()
    {
        GameActivity.startGame(getActivity(), count, amountDifficult);
    }
}