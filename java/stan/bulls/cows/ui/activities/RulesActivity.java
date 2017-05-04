package stan.bulls.cows.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import stan.bulls.cows.R;

public class RulesActivity
    extends Activity
{
    private TextView text;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules_activity);
        initViews();
        init();
    }
    private void initViews()
    {
        text = (TextView)findViewById(R.id.text);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });
    }
    private void init()
    {
        text.setText(Html.fromHtml(getResources().getString(R.string.rules_text)));
    }
}