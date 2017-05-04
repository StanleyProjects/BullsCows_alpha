package stan.bulls.cows.ui.fragments.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import stan.bulls.cows.R;
import stan.bulls.cows.core.Levels;
import stan.bulls.cows.helpers.PreferenceHelper;
import stan.bulls.cows.helpers.ui.LevelsNamesHelper;

public class LevelUpDialog
        extends DialogFragment
{
    static public LevelUpDialog newInstance()
    {
        LevelUpDialog fragment = new LevelUpDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    //___________________VIEWS
    private TextView level_name;
    private View stars;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private TextView description;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return new Dialog(getActivity(), R.style.Dialog);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.level_up_dialog, null);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        level_name = (TextView)v.findViewById(R.id.level_name);
        stars = v.findViewById(R.id.stars);
        star2 = (ImageView)v.findViewById(R.id.star2);
        star3 = (ImageView)v.findViewById(R.id.star3);
        star4 = (ImageView)v.findViewById(R.id.star4);
        star5 = (ImageView)v.findViewById(R.id.star5);
        description = (TextView)v.findViewById(R.id.description);
        v.findViewById(R.id.level_up_ok).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });
    }
    private void init()
    {
        int lvl = PreferenceHelper.getLevel(getActivity());
        level_name.setText(LevelsNamesHelper.getLevelName(lvl));
        switch(lvl)
        {
            case Levels.bronze:
                star2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border_white_48dp));
            case Levels.silver:
                star3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border_white_48dp));
            case Levels.diamond:
                star4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border_white_48dp));
            case Levels.master:
                star5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border_white_48dp));
        }
        switch(lvl)
        {
            case Levels.godlike:
                star5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_white_48dp));
            case Levels.master:
                star4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_white_48dp));
            case Levels.diamond:
                star3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_white_48dp));
            case Levels.silver:
                star2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_white_48dp));
        }
        switch(lvl)
        {
            case Levels.bronze:
                description.setText(R.string.bronze_level_up_description);
                break;
            case Levels.silver:
                description.setText(R.string.silver_level_up_description);
                break;
            case Levels.diamond:
                description.setText(R.string.diamond_level_up_description);
                break;
            case Levels.master:
                description.setText(R.string.master_level_up_description);
                break;
            case Levels.godlike:
                description.setText(R.string.godlike_level_up_description);
                break;
        }
    }
}