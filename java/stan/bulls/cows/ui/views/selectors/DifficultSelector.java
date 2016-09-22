package stan.bulls.cows.ui.views.selectors;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import stan.bulls.cows.R;
import stan.bulls.cows.core.Difficults;

public class DifficultSelector
        extends LinearLayout
        implements Difficults
{
    private View easy;
    private View medium;
    private View hard;
    private TextView easy_text;
    private TextView medium_text;
    private TextView hard_text;

    private final View.OnClickListener clickListener = new OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if (listener == null)
            {
                return;
            }
            switch (view.getId())
            {
                case R.id.easy:
                    if (DIFFICULT_EASY != difficult)
                    {
                        difficult = DIFFICULT_EASY;
                        clearAll();
                        easy.setBackgroundColor(getResources().getColor(R.color.green));
                        easy_text.setTextColor(getResources().getColor(R.color.white));
                        listener.setDifficult(DIFFICULT_EASY);
                    }
                    break;
                case R.id.medium:
                    if (DIFFICULT_MEDIUM != difficult)
                    {
                        difficult = DIFFICULT_MEDIUM;
                        clearAll();
                        medium.setBackgroundColor(getResources().getColor(R.color.gold));
                        medium_text.setTextColor(getResources().getColor(R.color.white));
                        listener.setDifficult(DIFFICULT_MEDIUM);
                    }
                    break;
                case R.id.hard:
                    if (DIFFICULT_HARD != difficult)
                    {
                        difficult = DIFFICULT_HARD;
                        clearAll();
                        hard.setBackgroundColor(getResources().getColor(R.color.red));
                        hard_text.setTextColor(getResources().getColor(R.color.white));
                        listener.setDifficult(DIFFICULT_HARD);
                    }
                    break;
            }
        }
    };
    private DifficultListener listener;
    private int difficult;

    public DifficultSelector(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.difficult_selector, this);
        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);
        easy_text = (TextView) findViewById(R.id.easy_text);
        medium_text = (TextView) findViewById(R.id.medium_text);
        hard_text = (TextView) findViewById(R.id.hard_text);
        easy.setOnClickListener(clickListener);
        medium.setOnClickListener(clickListener);
        hard.setOnClickListener(clickListener);
        difficult = DIFFICULT_EASY;
        clearAll();
        easy.setBackgroundColor(getResources().getColor(R.color.green));
        easy_text.setTextColor(getResources().getColor(R.color.white));
    }

    public void showHard(boolean show)
    {
        hard.setVisibility(show ? VISIBLE : GONE);
    }

    private void clearAll()
    {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
        {
            easy.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_ripple));
            medium.setBackgroundDrawable(getResources().getDrawable(R.drawable.gold_ripple));
            hard.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_ripple));
        }
        else
        {
            easy.setBackground(getResources().getDrawable(R.drawable.green_ripple));
            medium.setBackground(getResources().getDrawable(R.drawable.gold_ripple));
            hard.setBackground(getResources().getDrawable(R.drawable.red_ripple));
        }
        easy_text.setTextColor(getResources().getColor(R.color.green_trans));
        medium_text.setTextColor(getResources().getColor(R.color.gold_trans));
        hard_text.setTextColor(getResources().getColor(R.color.red_trans));
    }

    public void setListener(DifficultListener l)
    {
        this.listener = l;
    }

    public interface DifficultListener
    {
        void setDifficult(int dif);
    }
}