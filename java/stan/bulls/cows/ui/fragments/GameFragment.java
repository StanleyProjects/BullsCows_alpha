package stan.bulls.cows.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import stan.bulls.cows.R;
import stan.bulls.cows.core.GameSettings;
import stan.bulls.cows.core.ResultGame;
import stan.bulls.cows.core.offers.NumberOffer;
import stan.bulls.cows.core.offers.NumberOfferElement;
import stan.bulls.cows.core.offers.Offer;
import stan.bulls.cows.logic.BullsCowsLogic;
import stan.bulls.cows.marks.GameMark;
import stan.bulls.cows.ui.adapters.BullsCowsAdapter;
import stan.bulls.cows.ui.fragments.dialogs.AddOfferDialog;

public class GameFragment
        extends Fragment
    implements GameMark
{
    static public GameFragment newInstance(int count, int dif, IGameFragmentListener l)
    {
        GameFragment fragment = new GameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(COUNT_KEY, count);
        bundle.putInt(DIFFICULT_KEY, dif);
        fragment.setArguments(bundle);
        fragment.listener = l;
        return fragment;
    }

    //___________________VIEWS
    private View offers_list_submessage_ll;
    private TextView offers_list_submessage;
    private RecyclerView list;

    //___________________FIELDS
    private BullsCowsAdapter adapter;
    private IGameFragmentListener listener;
    private GameSettings gameSettings;
    private Offer secret;
    private int offersCount;
    private long date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.game_fragment, container, false);
        initViews(v);
        init();
        return v;
    }
    private void initViews(View v)
    {
        offers_list_submessage_ll = v.findViewById(R.id.offers_list_submessage_ll);
        offers_list_submessage = (TextView) v.findViewById(R.id.offers_list_submessage);
        list = (RecyclerView) v.findViewById(R.id.list);
        v.findViewById(R.id.add_offer).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addOffer();
            }
        });
    }
    private void init()
    {
        adapter = new BullsCowsAdapter(getActivity());
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        gameSettings = new GameSettings();
        gameSettings.count = getArguments().getInt(COUNT_KEY);
        gameSettings.difficult = getArguments().getInt(DIFFICULT_KEY);
        offers_list_submessage_ll.setVisibility(View.VISIBLE);
    }

    private void newOffer(Offer newOffer)
    {
        if(offersCount == 0)
        {
            setSecretFromFirstOffer(newOffer);
            date = System.currentTimeMillis();
        }
        else
        {
            BullsCowsLogic.checkCountBullsAndCows(newOffer, secret);
        }
        adapter.addOffer(newOffer);
        offersCount++;
        refreshUIFromOffersCount(offersCount);
        if(checkWin(newOffer))
        {
            endWinGame(true);
        }
    }
    private void refreshUIFromOffersCount(int count)
    {
        if(count == 1)
        {
            offers_list_submessage.setText(R.string.offers_list_submessage_begin_game);
        }
        else if (count == 2)
        {
            offers_list_submessage_ll.setVisibility(View.GONE);
        }
        list.smoothScrollToPosition(count);
    }

    private Offer setSecretFromFirstOffer(Offer firstOffer)
    {
        secret = createSecret();
        Offer offer = BullsCowsLogic.checkCountBullsAndCows(firstOffer, secret);
        if(checkWin(offer))
        {
            return setSecretFromFirstOffer(firstOffer);
        }
        return offer;
    }
    protected Offer createSecret()
    {
        NumberOfferElement[] offerElements = new NumberOfferElement[gameSettings.count];
        Random random = new Random();
        for (int i = 0; i < gameSettings.count; i++)
        {
            offerElements[i] = new NumberOfferElement(random.nextInt(gameSettings.difficult + 1));
        }
        return new NumberOffer(offerElements);
    }
    private boolean checkWin(Offer offer)
    {
        return offer.bulls == secret.getLenght();
    }
    private void endWinGame(boolean win)
    {
        offers_list_submessage_ll.setVisibility(View.GONE);
        ResultGame resultGame = new ResultGame();
        resultGame.time_spend = System.currentTimeMillis() - date;
        if(win)
        {
            resultGame.win = true;
            resultGame.amount_offers = offersCount;
            resultGame.gold_earned = calculateGoldEarned();
            listener.result(resultGame);
        }
        else
        {
            resultGame.win = false;
            resultGame.amount_offers = offersCount;
            resultGame.gold_earned = 0;
            listener.result(resultGame);
        }
    }
    private int calculateGoldEarned()
    {
        int gold = 0;
        gold += gameSettings.difficult - 3;
        gold += gameSettings.count/3;
        return gold;
    }

    private void addOffer()
    {
        AddOfferDialog.newInstance(new AddOfferDialog.AddOfferDialogListener()
        {
            @Override
            public void addOffer(NumberOffer offer)
            {
                newOffer(offer);
            }
        }, gameSettings.count, gameSettings.difficult).show(getActivity().getSupportFragmentManager(), AddOfferDialog.class.getCanonicalName());
    }

    public interface IGameFragmentListener
    {
        void result(ResultGame result);
    }
}