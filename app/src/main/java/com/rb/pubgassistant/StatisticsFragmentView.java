package com.rb.pubgassistant;

import java.util.concurrent.Callable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatisticsFragmentView extends ViewModel {
    private MutableLiveData<PlayerStats> playerStats;
    // private PubgSeasonsEnum pubgSeasonsEnum;

    public MutableLiveData<PlayerStats> getPlayerStats(long playerId, PubgSeasonsEnum pubgSeasonsEnum) {
        if (playerStats == null) {
            this.playerStats = new MutableLiveData<PlayerStats>();
            loadData(playerId, pubgSeasonsEnum);
        }

        return playerStats;
    }

    public void setSeason(long playerId, PubgSeasonsEnum pubgSeasonsEnum) {
        loadData(playerId, pubgSeasonsEnum);
    }

    public void loadData(long playerId, PubgSeasonsEnum pubgSeasonsEnum) {

        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerDao playerDao = appDatabase.playerDao();

        TaskRunner<PlayerStats> taskRunner = new TaskRunner<PlayerStats>();
        taskRunner.executeAsync(new Callable<PlayerStats>() {
            @Override
            public PlayerStats call() throws InterruptedException {
                // Thread.sleep(5000);
                return playerDao.getPlayerStatsByIdSeasons(playerId, pubgSeasonsEnum.getSeasonId());
            }
        }, new TaskRunner.TaskRunnerCallback<PlayerStats>() {
            @Override
            public void execute(PlayerStats data) {
                playerStats.postValue(data);
            }
        });
    }

}

