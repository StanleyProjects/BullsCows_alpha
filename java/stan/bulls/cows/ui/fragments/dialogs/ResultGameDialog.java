package stan.bulls.cows.ui.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import stan.bulls.cows.R;
import stan.bulls.cows.core.ResultGame;

public class ResultGameDialog
        extends DialogFragment
{
    static public ResultGameDialog newInstance(ResultGameDialogListener l, ResultGame resultGame)
    {
        ResultGameDialog fragment = new ResultGameDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.listener = l;
        fragment.resultGame = resultGame;
        fragment.setCancelable(false);
        return fragment;
    }

    //___________________VIEWS
    TextView result_game_label;
    TextView amount_offers;
    TextView time_spend;
    TextView gold_earned;
    View gold_earned_ll;

    View quality_reward;
    TextView quality_reward_count;
    View quality_mulct;
    TextView quality_mulct_count;
    View quality_end_game;

    View time_game_reward;
    TextView time_game_reward_count;
    View time_game_mulct;
    TextView time_game_mulct_count;
    View time_game_end_game;

    //___________________FIELDS
    private ResultGameDialogListener listener;
    private ResultGame resultGame;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return new Dialog(getActivity(), R.style.Dialog);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.result_game_dialog, null);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        v.findViewById(R.id.result_ok).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                listener.ok();
            }
        });
        result_game_label = (TextView) v.findViewById(R.id.result_game_label);
        amount_offers = (TextView) v.findViewById(R.id.amount_offers);
        time_spend = (TextView) v.findViewById(R.id.time_spend);
        gold_earned = (TextView) v.findViewById(R.id.gold_earned);
        gold_earned_ll = v.findViewById(R.id.gold_earned_ll);

        quality_reward = v.findViewById(R.id.quality_reward);
        quality_reward_count = (TextView) v.findViewById(R.id.quality_reward_count);
        quality_mulct = v.findViewById(R.id.quality_mulct);
        quality_mulct_count = (TextView) v.findViewById(R.id.quality_mulct_count);
        quality_end_game = v.findViewById(R.id.quality_end_game);

        time_game_reward = v.findViewById(R.id.time_game_reward);
        time_game_reward_count = (TextView) v.findViewById(R.id.time_game_reward_count);
        time_game_mulct = v.findViewById(R.id.time_game_mulct);
        time_game_mulct_count = (TextView) v.findViewById(R.id.time_game_mulct_count);
        time_game_end_game = v.findViewById(R.id.time_game_end_game);
    }
    private void init()
    {
        quality_reward.setVisibility(View.GONE);
        quality_mulct.setVisibility(View.GONE);
        quality_end_game.setVisibility(View.GONE);

        time_game_reward.setVisibility(View.GONE);
        time_game_mulct.setVisibility(View.GONE);
        time_game_end_game.setVisibility(View.GONE);

        amount_offers.setText(resultGame.amount_offers + "");
        time_spend.setText((resultGame.time_spend / 1000) + "");
        if(resultGame.win)
        {
            initWin();
        }
        else
        {
            initLose();
        }
    }
    private void initWin()
    {
        result_game_label.setText(R.string.congratulations);
        gold_earned.setText(resultGame.gold_earned + "");
        if(resultGame.gameSettings.quality.isReward())
        {
            quality_reward.setVisibility(View.VISIBLE);
            quality_reward_count.setText(resultGame.gameSettings.quality.getReward() + "");
        }
        else if(resultGame.gameSettings.quality.isMulct())
        {
            quality_mulct.setVisibility(View.VISIBLE);
            quality_mulct_count.setText(resultGame.gameSettings.quality.getMulct() + "");
        }
        if(resultGame.gameSettings.time_game.isReward())
        {
            time_game_reward.setVisibility(View.VISIBLE);
            time_game_reward_count.setText(resultGame.gameSettings.time_game.getReward() + "");
        }
        else if(resultGame.gameSettings.time_game.isMulct())
        {
            time_game_mulct.setVisibility(View.VISIBLE);
            time_game_mulct_count.setText(resultGame.gameSettings.time_game.getMulct() + "");
        }
    }
    private void initLose()
    {
        result_game_label.setText(R.string.you_lose);
        gold_earned_ll.setVisibility(View.GONE);
        if(resultGame.gameSettings.quality.isEndGame())
        {
            quality_end_game.setVisibility(View.VISIBLE);
        }
        else if(resultGame.gameSettings.time_game.isEndGame())
        {
            time_game_end_game.setVisibility(View.VISIBLE);
        }
    }

    public interface ResultGameDialogListener
    {
        void ok();
    }
}