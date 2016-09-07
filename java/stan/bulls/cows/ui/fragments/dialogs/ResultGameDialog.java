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
    }
    private void init()
    {
        quality_reward.setVisibility(View.GONE);
        quality_mulct.setVisibility(View.GONE);
        amount_offers.setText(resultGame.amount_offers + "");
        time_spend.setText((resultGame.time_spend / 1000) + "");
        if(resultGame.win)
        {
            initWin();
        }
        else
        {
            result_game_label.setText(R.string.you_lose);
            gold_earned_ll.setVisibility(View.GONE);
        }
    }

    private void initWin()
    {
        result_game_label.setText(R.string.congratulations);
        gold_earned.setText(resultGame.gold_earned + "");
        if(resultGame.gameSettings.isQualityReward())
        {
            quality_reward.setVisibility(View.VISIBLE);
            quality_reward_count.setText(resultGame.gameSettings.getQualityReward() + "");
        }
        else if(resultGame.gameSettings.isQualityMulct())
        {
            quality_mulct.setVisibility(View.VISIBLE);
            quality_mulct_count.setText(resultGame.gameSettings.getQualityMulct() + "");
        }
    }

    public interface ResultGameDialogListener
    {
        void ok();
    }
}