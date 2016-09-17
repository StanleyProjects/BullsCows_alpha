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
import stan.bulls.cows.core.GameController;
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
    private View count_offers_tries;
    private View count_offers_much;

    private View offers_list_submessage_ll;
    private TextView offers_list_submessage;
    private RecyclerView list;

    //___________________FIELDS
    private BullsCowsAdapter adapter;
    private IGameFragmentListener listener;
    private CountDownTimer timeGame;
    private RotateAnimation timeGameAnimation;
    private CountDownTimer timeOffer;
    private ArrayList<OfferListItem> data;
    private GameController gameController;

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
            initTimeGameFromDifficult();
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
        count_offers_tries = v.findViewById(R.id.count_offers_tries);
        count_offers_much = v.findViewById(R.id.count_offers_much);
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
        gameController = new GameController(new GameSettings(getArguments().getInt(COUNT_KEY), getArguments().getInt(DIFFICULT_KEY)), new GameController.GameControllerListener()
        {
            @Override
            public Offer createSecret(GameSettings settings)
            {
                NumberOfferElement[] offerElements = new NumberOfferElement[settings.count];
                Random random = new Random();
                for (int i = 0; i < settings.count; i++)
                {
                    offerElements[i] = new NumberOfferElement(random.nextInt(settings.difficult + 1));
                }
                return new NumberOffer(offerElements);
            }
            @Override
            public void initFromFirstOffer(Offer newOffer)
            {
                OfferListItem newOfferListItem = new OfferListItem();
                results_frame.setVisibility(View.VISIBLE);
                switch(gameController.getSettings().getDifficultLevel())
                {
                    case 5:
                    case 4:
                        gameController.updateCountOffers();
                        try_count.setText((gameController.getSettings().getCountOffers() - data.size()) + "");
                        try_left.setVisibility(View.VISIBLE);
                    case 3:
                        time_offer.setVisibility(View.VISIBLE);
                        adapter.setCheckingTime(true);
                    case 2:
                        time_game.setVisibility(View.VISIBLE);
                        time_is_over.setVisibility(View.GONE);
                        initTimeGameFromDifficult();
                    case 1:
                        adapter.setCheckingQuality(true);
                        break;
                    case 0:
                }
                newOfferListItem.offer = newOffer;
                data.add(newOfferListItem);
                adapter.swapData(data);
            }
            @Override
            public void initFromOffer(GameSettings settings, Offer newOffer, long timeOffer)
            {
                OfferListItem newOfferListItem = new OfferListItem();
                switch (settings.getDifficultLevel())
                {
                    case 5:
                    case 4:
                        if(gameController.canUpdateCountOffers())
                        {
                            gameController.updateCountOffers();
                            try_count.setText((gameController.getSettings().getCountOffers() - data.size()) + "");
                        }
                        else
                        {
                            count_offers_tries.setVisibility(View.GONE);
                            count_offers_much.setVisibility(View.VISIBLE);
                        }
                    case 3:
                        int r = settings.getTimeOffer() * settings.getDifficultLevel();
                        r -= settings.getTimeOffer() * ((settings.getDifficultLevel()-1)/2);
                        r /= settings.getDifficultLevel();
                        if(timeOffer <= r)
                        {
                            newOfferListItem.time = OfferListItem.TIME_OFFER_GOOD;
                            settings.time_offer.addGoodOffer(data.size(), settings);
                        }
                        else
                        {
                            int m = settings.getTimeOffer();
                            m -= settings.getTimeOffer() / ((8-settings.getDifficultLevel())*2);
                            if(timeOffer >= m)
                            {
                                newOfferListItem.time = OfferListItem.TIME_OFFER_BAD;
                                settings.time_offer.addBadOffer(data.size(), settings);
                            }
                            else
                            {
                                newOfferListItem.time = OfferListItem.TIME_OFFER_NEUTRAL;
                                settings.time_offer.addNeutralOffer(data.size(), settings);
                            }
                        }
                    case 2:
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
                                settings.quality.updateResult(settings.quality.getResult()+1);
                                break;
                            }
                        }
                }
                newOfferListItem.offer = newOffer;
                data.add(newOfferListItem);
                adapter.swapData(data);
            }
            @Override
            public void refreshUIFromOffersCount(GameSettings settings, int count)
            {
                if(count == 1)
                {
                    if(settings.getDifficultLevel() > 1)
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
                if(timeOffer != null)
                {
                    timeOffer.cancel();
                }
                initTimeOfferFromDifficult();
            }
            @Override
            public void endWinGame(ResultGame resultGame)
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
                count_offers_tries.setVisibility(View.GONE);
                count_offers_much.setVisibility(View.GONE);
                listener.result(resultGame);
            }
        });
        data = new ArrayList<>();
        adapter = new BullsCowsAdapter(getActivity());
        adapter.setCheckingQuality(false);
        adapter.setCheckingTime(false);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        offers_list_submessage_ll.setVisibility(View.VISIBLE);
        if(gameController.getSettings().getDifficultLevel() > 0)
        {
            easy_lable.setVisibility(View.GONE);
        }
        else
        {
            easy_lable.setVisibility(View.VISIBLE);
        }
        initResultsFrame();
        initTimeGameView();
    }
    private void initResultsFrame()
    {
        results_frame.setVisibility(View.GONE);
        time_game.setVisibility(View.GONE);
        time_offer.setVisibility(View.GONE);
        try_left.setVisibility(View.GONE);
        count_offers_much.setVisibility(View.GONE);
    }
    private void initTimeGameView()
    {
        progress.setMaxProgress(gameController.getSettings().getTimeGame());
        progress.setColor(getActivity().getResources().getColor(R.color.colorPrimary));
        progress.setThickness((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getActivity().getResources().getDisplayMetrics()));
        timeGameAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        timeGameAnimation.setDuration(gameController.getSettings().getDifficultLevel()*500);
        timeGameAnimation.setRepeatMode(Animation.RESTART);
        timeGameAnimation.setRepeatCount(Animation.INFINITE);
        timeGameAnimation.setInterpolator(new LinearInterpolator());
    }

    private void initTimeGameFromDifficult()
    {
        progress.setCurrentProgress(gameController.getSpendTimeGame());
        progress.setAnimation(timeGameAnimation);
        timeGame = new CountDownTimer(gameController.getSettings().getTimeGame() - gameController.getSpendTimeGame(), gameController.getSettings().getTimeGame()/1000*(6-gameController.getSettings().getDifficultLevel()))
        {
            @Override
            public void onTick(long l)
            {
                progress.setCurrentProgress(gameController.getSpendTimeGame());
                gameController.updateTimeGame();
            }
            @Override
            public void onFinish()
            {
                progress.clearAnimation();
                progress.setVisibility(View.GONE);
                time_is_over.setVisibility(View.VISIBLE);
                gameController.updateTimeGame();
                gameController.checkGameLose();
            }
        }.start();
    }
    private void initTimeOfferFromDifficult()
    {
        if(gameController.getSettings().getDifficultLevel() < 3)
        {
            return;
        }
        time_offer_secconds.setVisibility(View.VISIBLE);
        time_offer_much.setVisibility(View.GONE);
        timeOffer = new CountDownTimer(gameController.getLeftTimeOffer(), 500)
        {
            @Override
            public void onTick(long l)
            {
                time.setText(gameController.getLeftTimeOffer()/1000 + "");
            }
            @Override
            public void onFinish()
            {
//                int r = gameController.getSettings().getTimeOffer() * gameController.getSettings().getDifficultLevel();
//                r -= gameController.getSettings().getTimeOffer() * ((gameController.getSettings().getDifficultLevel()-1)/2);
//                r /= gameController.getSettings().getDifficultLevel();
//                if(gameController.getSpendTimeOffer() > r)
//                {
//                    gameController.getSettings().time_offer.addBadOffer(data.size(), gameController.getSettings());
//                }
                gameController.checkGameLose();
                time_offer_secconds.setVisibility(View.GONE);
                time_offer_much.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void addOffer()
    {
        AddOfferDialog.newInstance(new AddOfferDialog.AddOfferDialogListener()
        {
            @Override
            public void addOffer(NumberOffer offer)
            {
                gameController.addOffer(offer);
            }
        }, gameController.getSettings().count, gameController.getSettings().difficult).show(getActivity().getSupportFragmentManager(), AddOfferDialog.class.getCanonicalName());
    }

    public interface IGameFragmentListener
    {
        void result(ResultGame result);
    }
}