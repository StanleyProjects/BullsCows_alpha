package stan.bulls.cows.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import stan.bulls.cows.R;
import stan.bulls.cows.helpers.PreferenceHelper;
import stan.bulls.cows.ui.fragments.SandBoxFragment;

public class MainActivity
        extends AppCompatActivity
{
    private final SandBoxFragment sandBoxFragment = new SandBoxFragment();

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
                    sandBoxFragment.refreshGold();
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
        init();
    }

    private void init()
    {
        if(PreferenceHelper.getGold(this) == -1)
        {
            PreferenceHelper.addGold(this, 1);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_frame, sandBoxFragment)
                .commit();
    }
}