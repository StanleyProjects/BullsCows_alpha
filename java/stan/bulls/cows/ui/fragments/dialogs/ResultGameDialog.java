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

    View time_offer_reward;
    TextView time_offer_reward_count;
    View time_offer_mulct;
    TextView time_offer_mulct_count;
    View time_offer_end_game;

    View count_reward;
    TextView count_reward_count;
    View count_mulct;
    TextView count_mulct_count;
    View count_end_game;

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

        time_offer_reward = v.findViewById(R.id.time_offer_reward);
        time_offer_reward_count = (TextView) v.findViewById(R.id.time_offer_reward_count);
        time_offer_mulct = v.findViewById(R.id.time_offer_mulct);
        time_offer_mulct_count = (TextView) v.findViewById(R.id.time_offer_mulct_count);
        time_offer_end_game = v.findViewById(R.id.time_offer_end_game);

        count_reward = v.findViewById(R.id.count_reward);
        count_reward_count = (TextView) v.findViewById(R.id.count_reward_count);
        count_mulct = v.findViewById(R.id.count_mulct);
        count_mulct_count = (TextView) v.findViewById(R.id.count_mulct_count);
        count_end_game = v.findViewById(R.id.count_end_game);
    }
    private void init()
    {
        quality_reward.setVisibility(View.GONE);
        quality_mulct.setVisibility(View.GONE);
        quality_end_game.setVisibility(View.GONE);

        time_game_reward.setVisibility(View.GONE);
        time_game_mulct.setVisibility(View.GONE);
        time_game_end_game.setVisibility(View.GONE);

        time_offer_reward.setVisibility(View.GONE);
        time_offer_mulct.setVisibility(View.GONE);
        time_offer_end_game.setVisibility(View.GONE);

        count_reward.setVisibility(View.GONE);
        count_mulct.setVisibility(View.GONE);
        count_end_game.setVisibility(View.GONE);

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
        if(resultGame.gameSettings.getDifficultLevel() == 0)
        {
            gold_earned_ll.setVisibility(View.GONE);
            return;
        }
        gold_earned.setText(resultGame.gold_earned + "");
        if(resultGame.gameSettings.quality.isReward(resultGame.gameSettings))
        {
            quality_reward.setVisibility(View.VISIBLE);
            quality_reward_count.setText(resultGame.gameSettings.quality.getReward(resultGame.gameSettings) + "");
        }
        else if(resultGame.gameSettings.quality.isMulct(resultGame.gameSettings))
        {
            quality_mulct.setVisibility(View.VISIBLE);
            quality_mulct_count.setText(resultGame.gameSettings.quality.getMulct(resultGame.gameSettings) + "");
        }
        if(resultGame.gameSettings.time_game.isReward(resultGame.gameSettings))
        {
            time_game_reward.setVisibility(View.VISIBLE);
            time_game_reward_count.setText(resultGame.gameSettings.time_game.getReward(resultGame.gameSettings) + "");
        }
        else if(resultGame.gameSettings.time_game.isMulct(resultGame.gameSettings))
        {
            time_game_mulct.setVisibility(View.VISIBLE);
            time_game_mulct_count.setText(resultGame.gameSettings.time_game.getMulct(resultGame.gameSettings) + "");
        }
        if(resultGame.gameSettings.time_offer.isReward(resultGame.gameSettings))
        {
            time_offer_reward.setVisibility(View.VISIBLE);
            time_offer_reward_count.setText(resultGame.gameSettings.time_offer.getReward(resultGame.gameSettings) + "");
        }
        else if(resultGame.gameSettings.time_offer.isMulct(resultGame.gameSettings))
        {
            time_offer_mulct.setVisibility(View.VISIBLE);
            time_offer_mulct_count.setText(resultGame.gameSettings.time_offer.getMulct(resultGame.gameSettings) + "");
        }
        if(resultGame.gameSettings.count_offers.isReward(resultGame.gameSettings))
        {
            count_reward.setVisibility(View.VISIBLE);
            count_reward_count.setText(resultGame.gameSettings.count_offers.getReward(resultGame.gameSettings) + "");
        }
        else if(resultGame.gameSettings.count_offers.isMulct(resultGame.gameSettings))
        {
            count_mulct.setVisibility(View.VISIBLE);
            count_mulct_count.setText(resultGame.gameSettings.count_offers.getMulct(resultGame.gameSettings) + "");
        }
    }
    private void initLose()
    {
        result_game_label.setText(R.string.you_lose);
        gold_earned_ll.setVisibility(View.GONE);
        if(resultGame.gameSettings.quality.isEndGame(resultGame.gameSettings))
        {
            quality_end_game.setVisibility(View.VISIBLE);
        }
        else if(resultGame.gameSettings.time_game.isEndGame(resultGame.gameSettings))
        {
            time_game_end_game.setVisibility(View.VISIBLE);
        }
        else if(resultGame.gameSettings.time_offer.isEndGame(resultGame.gameSettings))
        {
            time_offer_end_game.setVisibility(View.VISIBLE);
        }
        else if(resultGame.gameSettings.count_offers.isEndGame(resultGame.gameSettings))
        {
            count_end_game.setVisibility(View.VISIBLE);
        }
    }

    public interface ResultGameDialogListener
    {
        void ok();
    }
}