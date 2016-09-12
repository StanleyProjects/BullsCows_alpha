package stan.bulls.cows.core;

import android.util.Log;

public class GameSettings
{
    public GameResult quality = new GameResult()
    {
        private int result;
        @Override
        public boolean isReward()
        {
            if(getDifficultLevel()<1)
            {
                return false;
            }
            int r = (getDifficultLevel()+1)/2;
            return getResult() <= r/4;
        }
        @Override
        public boolean isMulct()
        {
            if(getDifficultLevel()<3 || isReward() || isEndGame())
            {
                return false;
            }
            int m = (getDifficultLevel()+1)/2;
            return getResult() >= (m/2)*2;
        }
        @Override
        public boolean isEndGame()
        {
            if(getDifficultLevel()<5)
            {
                return false;
            }
            int e = (getDifficultLevel()+1)/2;
            return getResult() >= e;
        }
        @Override
        public int getReward()
        {
            return getDifficultLevel()*(count-2);
        }
        @Override
        public int getMulct()
        {
            return getDifficultLevel()*(count-2)/2 + (count-2)*(difficult/3)/2;
        }
        @Override
        public int getResult()
        {
            return result;
        }
        @Override
        public void updateResult(int r)
        {
            result = r;
        }
    };
    public GameResult time_game = new GameResult()
    {
        private int time;
        @Override
        public boolean isReward()
        {
            if(getDifficultLevel()<2)
            {
                return false;
            }
            int r = getTimeGame() * getDifficultLevel();
            r -= getTimeGame() * ((getDifficultLevel()-1)/2);
            r /= getDifficultLevel();
            return time <= r;
        }
        @Override
        public boolean isMulct()
        {
            if(getDifficultLevel()<3 || isReward() || isEndGame())
            {
                return false;
            }
            int m = getTimeGame();
            m -= getTimeGame() / ((8-getDifficultLevel())*2);
            return time >= m;
        }
        @Override
        public boolean isEndGame()
        {
            if(getDifficultLevel()<5)
            {
                return false;
            }
            return getResult() >= getTimeGame();
        }
        @Override
        public int getReward()
        {
            return getDifficultLevel()*(count-1);
        }
        @Override
        public int getMulct()
        {
            return getDifficultLevel()*(count-1)/2 + (count-1)*(difficult/3)/2;
        }
        @Override
        public int getResult()
        {
            return time;
        }
        @Override
        public void updateResult(int r)
        {
            time = r;
        }
    };
    public GameResult time_offer = new GameResult()
    {
        private int time;
        @Override
        public boolean isReward()
        {
            if(getDifficultLevel()<4)
            {
                return false;
            }
            int r = (getDifficultLevel()+1)/2;
            return getResult() <= r/4;
        }
        @Override
        public boolean isMulct()
        {
            if(getDifficultLevel()<4 || isReward() || isEndGame())
            {
                return false;
            }
            int m = (getDifficultLevel()+1)/2;
            return getResult() >= (m/2)*2;
        }
        @Override
        public boolean isEndGame()
        {
            if(getDifficultLevel()<4)
            {
                return false;
            }
            int e = (getDifficultLevel()+1)/2;
            return getResult() >= e;
        }
        @Override
        public int getReward()
        {
            return getDifficultLevel()*(count-2);
        }
        @Override
        public int getMulct()
        {
            return getDifficultLevel()*(count-2)/2 + (count-2)*(difficult/3)/2;
        }
        @Override
        public int getResult()
        {
            return time;
        }
        @Override
        public void updateResult(int t)
        {
            time = t;
        }
    };
    public int count;
    public int difficult;

    public GameSettings(int c, int a)
    {
        count = c;
        difficult = a;
        test();
    }

    private void test()
    {
        Log.e("GameSettings", "DifficultLevel " + getDifficultLevel());
        Log.e("GameSettings", "TimeGame " + getTimeGame());
        Log.e("GameSettings", "TimeOffer " + getTimeOffer());
        Log.e("GameSettings", "CountOffers " + getCountOffers());
        Log.e("quality", "isReward " + (getDifficultLevel()+1)/2/4);
        Log.e("quality", "isMulct " + ((getDifficultLevel()+1)/2/2)*2);
        int e = (getDifficultLevel()+1)/2;
        Log.e("quality", "isEndGame " + e);
        Log.e("quality", "getMulct " + quality.getMulct());
        Log.e("quality", "getReward " + quality.getReward());
//        int r = getTimeGame() * getDifficultLevel();
//        r -= getTimeGame() * ((getDifficultLevel()-1)/2);
//        r /= getDifficultLevel();
//        Log.e("time_game", "isReward " + r);
        int m = getTimeGame();
        m -= getTimeGame() / ((8-getDifficultLevel())*2);
        Log.e("time_game", "isMulct " + m);
        Log.e("time_game", "getMulct " + time_game.getMulct());
        Log.e("time_game", "getReward " + time_game.getReward());
    }

    public int getDifficultLevel()
    {
        return count-2 + difficult/3 - 2;
    }
    public int getTimeGame()
    {
        return (count-2)*(count-2)*(difficult/3)*10*1000;
    }
    public int getTimeOffer()
    {
        return (getTimeGame()/getCountOffers());
    }
    public int getCountOffers()
    {
//        return 3 * (difficult/3) + ((count-2)+2)/2 + ((difficult/3)+2)/2;
        return count + difficult;
    }
}