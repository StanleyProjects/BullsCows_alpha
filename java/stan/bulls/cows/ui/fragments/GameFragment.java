package stan.bulls.cows.ui.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import stan.bulls.cows.R;
import stan.bulls.cows.core.GameResult;
import stan.bulls.cows.core.GameSettings;
import stan.bulls.cows.core.ResultGame;
import stan.bulls.cows.core.offers.NumberOffer;
import stan.bulls.cows.core.offers.NumberOfferElement;
import stan.bulls.cows.core.offers.Offer;
import stan.bulls.cows.logic.BullsCowsLogic;
import stan.bulls.cows.marks.GameMark;
import stan.bulls.cows.models.OfferListItem;
import stan.bulls.cows.ui.adapters.BullsCowsAdapter;
import stan.bulls.cows.ui.fragments.dialogs.AddOfferDialog;
import stan.bulls.cows.ui.views.progress.CircleProgressView;

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
    private View results_frame;

    private View easy_lable;

    private View time_game;
    private CircleProgressView progress;
    private View time_is_over;
    private View time_offer;
    private View time_offer_secconds;
    private TextView time;
    private View time_offer_much;
    private View try_left;
    private TextView try_count;

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
    private long dateOffer;
    private CountDownTimer timeGame;
    private CountDownTimer timeOffer;
    private ArrayList<OfferListItem> data;

    @Override
    public void onStop()
    {
        super.onStop();
        progress.clearAnimation();
        if(timeGame != null)
        {
            timeGame.cancel();
        }
        if(timeOffer != null)
        {
            timeOffer.cancel();
        }
    }
    @Override
    public void onStart()
    {
        super.onStart();
        if(timeGame != null)
        {
            timeGame.cancel();
            initProgressFromDifficult();
        }
        if(timeOffer != null)
        {
            timeOffer.cancel();
            initTimeOfferFromDifficult();
        }
    }
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
        results_frame = v.findViewById(R.id.results_frame);
        easy_lable = v.findViewById(R.id.easy_lable);
        time_game = v.findViewById(R.id.time_game);
        time_is_over = v.findViewById(R.id.time_is_over);
        time_offer = v.findViewById(R.id.time_offer);
        time_offer_secconds = v.findViewById(R.id.time_offer_secconds);
        time_offer_much = v.findViewById(R.id.time_offer_much);
        try_left = v.findViewById(R.id.try_left);
        time = (TextView) v.findViewById(R.id.time);
        try_count = (TextView) v.findViewById(R.id.try_count);
        progress = (CircleProgressView) v.findViewById(R.id.progress);
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
        data = new ArrayList<>();
        adapter = new BullsCowsAdapter(getActivity());
        adapter.setCheckingQuality(false);
        adapter.setCheckingTime(false);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        gameSettings = new GameSettings(getArguments().getInt(COUNT_KEY), getArguments().getInt(DIFFICULT_KEY));
        offers_list_submessage_ll.setVisibility(View.VISIBLE);
        if(gameSettings.getDifficultLevel() > 0)
        {
            easy_lable.setVisibility(View.GONE);
        }
        else
        {
            easy_lable.setVisibility(View.VISIBLE);
        }
        initResultsFrame();
    }
    private void initResultsFrame()
    {
        results_frame.setVisibility(View.GONE);
        time_game.setVisibility(View.GONE);
        time_offer.setVisibility(View.GONE);
        try_left.setVisibility(View.GONE);
    }

    private void initFromDifficult()
    {
        results_frame.setVisibility(View.VISIBLE);
        int d = gameSettings.getDifficultLevel();
        Log.e(GameFragment.class.getCanonicalName(), "dif = " + d);
        switch (d)
        {
            case 5:
            case 4:
                try_left.setVisibility(View.VISIBLE);
            case 3:
                time_offer.setVisibility(View.VISIBLE);
                adapter.setCheckingTime(true);
            case 2:
                time_game.setVisibility(View.VISIBLE);
                time_is_over.setVisibility(View.GONE);
                initProgressFromDifficult();
            case 1:
                adapter.setCheckingQuality(true);
                break;
            case 0:
        }
    }
    private void initProgressFromDifficult()
    {
        progress.setMaxProgress(gameSettings.getTimeGame());
        progress.setColor(getActivity().getResources().getColor(R.color.colorPrimary));
        progress.setThickness((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getActivity().getResources().getDisplayMetrics()));
        long currentTime = System.currentTimeMillis() - date;
        progress.setCurrentProgress(currentTime);
        RotateAnimation r;
        r = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        r.setDuration(gameSettings.getDifficultLevel()*500);
        r.setRepeatMode(Animation.RESTART);
        r.setRepeatCount(Animation.INFINITE);
        r.setInterpolator(new LinearInterpolator());
        progress.setAnimation(r);
        timeGame = new CountDownTimer(gameSettings.getTimeGame() - currentTime, gameSettings.getTimeGame()/1000*(6-gameSettings.getDifficultLevel()))
        {
            @Override
            public void onTick(long l)
            {
                progress.setCurrentProgress(System.currentTimeMillis() - date);
                gameSettings.time_game.updateResult((int)(System.currentTimeMillis() - date));
            }
            @Override
            public void onFinish()
            {
                progress.clearAnimation();
                progress.setVisibility(View.GONE);
                time_is_over.setVisibility(View.VISIBLE);
                gameSettings.time_game.updateResult((int)(System.currentTimeMillis() - date));
                if(checkLose())
                {
                    endWinGame(false);
                }
            }
        }.start();
    }
    private void initTimeOfferFromDifficult()
    {
        time_offer_secconds.setVisibility(View.VISIBLE);
        time_offer_much.setVisibility(View.GONE);
        long currentTime = System.currentTimeMillis() - dateOffer;
        time.setText(currentTime + "");
        timeOffer = new CountDownTimer(gameSettings.getTimeOffer() - currentTime, 500)
        {
            @Override
            public void onTick(long l)
            {
                long currentTime = System.currentTimeMillis() - dateOffer;
                time.setText(currentTime/1000 + "");
            }
            @Override
            public void onFinish()
            {
                long currentTime = System.currentTimeMillis() - dateOffer;
                int r = gameSettings.getTimeOffer() * gameSettings.getDifficultLevel();
                r -= gameSettings.getTimeOffer() * ((gameSettings.getDifficultLevel()-1)/2);
                r /= gameSettings.getDifficultLevel();
                if(currentTime > r)
                {
                    gameSettings.time_offer.updateResult(gameSettings.time_offer.getResult()+1);
                }
                if(checkLose())
                {
                    endWinGame(false);
                }
                else
                {
                    time_offer_secconds.setVisibility(View.GONE);
                    time_offer_much.setVisibility(View.VISIBLE);
                }
            }
        }.start();
    }

    private void newOffer(Offer newOffer)
    {
        OfferListItem newOfferListItem = new OfferListItem();
        if(offersCount == 0)
        {
            setSecretFromFirstOffer(newOffer);
            date = System.currentTimeMillis();
            initFromDifficult();
        }
        else
        {
            BullsCowsLogic.checkCountBullsAndCows(newOffer, secret);
            switch (gameSettings.getDifficultLevel())
            {
                case 5:
                case 4:
                case 3:
                    //
                    long currentTime = System.currentTimeMillis() - dateOffer;
                    int r = gameSettings.getTimeOffer() * gameSettings.getDifficultLevel();
                    r -= gameSettings.getTimeOffer() * ((gameSettings.getDifficultLevel()-1)/2);
                    r /= gameSettings.getDifficultLevel();
                    if(currentTime <= r)
                    {
                        newOfferListItem.time = OfferListItem.TIME_OFFER_GOOD;
                    }
                    else
                    {
                        gameSettings.time_offer.updateResult(gameSettings.time_offer.getResult()+1);
                        int m = gameSettings.getTimeOffer();
                        m -= gameSettings.getTimeOffer() / ((8-gameSettings.getDifficultLevel())*2);
                        if(currentTime >= m)
                        {
                            newOfferListItem.time = OfferListItem.TIME_OFFER_BAD;
                        }
                        else
                        {
                            newOfferListItem.time = OfferListItem.TIME_OFFER_NEUTRAL;
                        }
                    }
                case 1:
                    newOfferListItem.quality = true;
                    for(int i = 0; i<adapter.getData().size(); i++)
                    {
                        Offer o = BullsCowsLogic.checkCountBullsAndCows(new Offer(adapter.getData().get(i).offer.getOfferElements())
                        {
                            @Override
                            public String getStringValues()
                            {
                                return null;
                            }
                        }, newOffer);
                        if(adapter.getData().get(i).offer.bulls != o.bulls || adapter.getData().get(i).offer.cows != o.cows)
                        {
                            newOfferListItem.quality = false;
                            gameSettings.quality.updateResult(gameSettings.quality.getResult()+1);
                            break;
                        }
                    }
            }
        }
        if(timeOffer != null)
        {
            timeOffer.cancel();
        }
        dateOffer = System.currentTimeMillis();
        initTimeOfferFromDifficult();
        newOfferListItem.offer = newOffer;
        data.add(newOfferListItem);
        adapter.swapData(data);
        offersCount++;
        refreshUIFromOffersCount(offersCount);
        if(checkWin(newOffer))
        {
            endWinGame(true);
        }
        else if(checkLose())
        {
            endWinGame(false);
        }
    }
    private void refreshUIFromOffersCount(int count)
    {
        if(count == 1)
        {
            if(gameSettings.getDifficultLevel() > 1)
            {
                offers_list_submessage.setText(R.string.offers_list_submessage_begin_game);
            }
            else
            {
                offers_list_submessage_ll.setVisibility(View.GONE);
            }
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
    private boolean checkLose()
    {
        if(gameSettings.quality.isEndGame())
        {
            return true;
        }
        if(gameSettings.time_game.isEndGame())
        {
            return true;
        }
        if(gameSettings.time_offer.isEndGame())
        {
            return true;
        }
        return false;
    }
    private void endWinGame(boolean win)
    {
        offers_list_submessage_ll.setVisibility(View.GONE);
        ResultGame resultGame = new ResultGame();
        resultGame.time_spend = System.currentTimeMillis() - date;
        resultGame.gameSettings = gameSettings;
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
        refreshUIFromEndGame();
    }
    private int calculateGoldEarned()
    {
        int gold = 0;
        gold += gameSettings.getDifficultLevel() * (gameSettings.count-2);
        gold += calculateGoldEarnedFromResult(gameSettings.quality);
        gold += calculateGoldEarnedFromResult(gameSettings.time_game);
        if(gold < 0)
        {
            gold = 0;
        }
        return gold;
    }
    private int calculateGoldEarnedFromResult(GameResult result)
    {
        int gold = 0;
        if(result.isReward())
        {
            gold += result.getReward();
        }
        else if(result.isMulct())
        {
            gold -= result.getMulct();
        }
        return gold;
    }

    private void refreshUIFromEndGame()
    {
        if(timeGame != null)
        {
            timeGame.cancel();
            timeGame = null;
        }
        progress.clearAnimation();
        progress.setVisibility(View.GONE);
        if(timeOffer != null)
        {
            timeOffer.cancel();
            timeOffer = null;
        }
        time_offer_secconds.setVisibility(View.GONE);
        time_offer_much.setVisibility(View.GONE);
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