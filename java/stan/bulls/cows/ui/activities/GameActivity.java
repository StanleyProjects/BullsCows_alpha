package stan.bulls.cows.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import stan.bulls.cows.R;
import stan.bulls.cows.core.ResultGame;
import stan.bulls.cows.helpers.PreferenceHelper;
import stan.bulls.cows.marks.GameMark;
import stan.bulls.cows.ui.fragments.GameFragment;
import stan.bulls.cows.ui.fragments.dialogs.ResultGameDialog;

public class GameActivity
        extends AppCompatActivity
    implements GameMark
{
    static public final int REQUEST_CODE = 344;
    static public final int RESULT_WIN = 3441;
    static public final int RESULT_LOSE = 3442;
    static public final int RESULT_CANCEL = 3443;
    static public final int RESULT_ERROR = 3440;
    static public void startGame(Activity activity, int count, int dif)
    {
        Intent i = new Intent(activity, GameActivity.class);
        i.putExtra(COUNT_KEY, count);
        i.putExtra(DIFFICULT_KEY, dif);
        activity.startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        init();
    }

    private void init()
    {
        int count = getIntent().getIntExtra(COUNT_KEY, -1);
        int dif = getIntent().getIntExtra(DIFFICULT_KEY, -1);
        if(count == -1 || dif == -1)
        {
            setResult(RESULT_ERROR);
            finish();
            return;
        }
        setResult(RESULT_CANCEL);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_frame, GameFragment.newInstance(count, dif, new GameFragment.IGameFragmentListener()
                {
                    @Override
                    public void result(ResultGame result)
                    {
                        if(result.win)
                        {
                            setResult(RESULT_WIN);
                            PreferenceHelper.addGold(GameActivity.this, result.gold_earned);
                        }
                        else
                        {
                            setResult(RESULT_LOSE);
                        }
                        ResultGameDialog.newInstance(new ResultGameDialog.ResultGameDialogListener()
                        {
                            @Override
                            public void ok()
                            {
                                finish();
                            }
                        }, result).show(getSupportFragmentManager(), ResultGameDialog.class.getCanonicalName());
                    }
                }))
                .commit();
    }
}