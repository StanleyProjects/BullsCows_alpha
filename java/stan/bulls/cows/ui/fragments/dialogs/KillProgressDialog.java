package stan.bulls.cows.ui.fragments.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import stan.bulls.cows.R;

public class KillProgressDialog
        extends DialogFragment
{
    static public DialogFragment newInstance(Listener l)
    {
        KillProgressDialog fragment = new KillProgressDialog();
        fragment.listener = l;
        return fragment;
    }

    private Listener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return new Dialog(getActivity(), R.style.Dialog);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.kill_progress_dialog, null);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        v.findViewById(R.id.apply).setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v)
            {
                listener.apply();
                dismiss();
                return false;
            }
        });
        v.findViewById(R.id.apply).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Toast.makeText(getActivity(), R.string.kill_progress_long_click_required, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void init()
    {
    }

    public interface Listener
    {
        void apply();
        void cancel();
    }
}