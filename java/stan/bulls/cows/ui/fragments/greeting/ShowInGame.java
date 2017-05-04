package stan.bulls.cows.ui.fragments.greeting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import stan.bulls.cows.R;
import stan.bulls.cows.ui.views.progress.CircleProgressView;

public class ShowInGame
        extends Fragment
{
    //___________________VIEWS
    private CircleProgressView progress;
    private TextView link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.show_in_game, container, false);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        progress = (CircleProgressView)v.findViewById(R.id.progress);
        link = (TextView)v.findViewById(R.id.link);
    }
    private void init()
    {
        progress.setColor(getActivity().getResources().getColor(R.color.colorPrimary));
        progress.setThickness((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getActivity().getResources().getDisplayMetrics()));
        progress.setMaxProgress(100);
        progress.setCurrentProgress(66);
//        link.setMovementMethod(LinkMovementMethod.getInstance());
    }
}