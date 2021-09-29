package com.rb.pubgassistant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatisticsCompareFragmentView extends ViewModel {
    private MutableLiveData<List<PlayerStats>> playerStats;
    // private PubgSeasonsEnum pubgSeasonsEnum;

    public MutableLiveData<List<PlayerStats>> getPlayerStats(long playerIdOne, long playerIdTwo, PubgSeasonsEnum pubgSeasonsEnum) {
        if (playerStats == null) {
            this.playerStats = new MutableLiveData<List<PlayerStats>>();
            loadData(playerIdOne, playerIdTwo, pubgSeasonsEnum);
        }

        return playerStats;
    }

    public void setSeason(long playerIdOne, long playerIdTwo, PubgSeasonsEnum pubgSeasonsEnum) {
        loadData(playerIdOne, playerIdTwo, pubgSeasonsEnum);
    }

    public void loadData(long playerIdOne, long playerIdTwo, PubgSeasonsEnum pubgSeasonsEnum) {

        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerDao playerDao = appDatabase.playerDao();

        TaskRunner<List<PlayerStats>> taskRunner = new TaskRunner<List<PlayerStats>>();
        taskRunner.executeAsync(new Callable<List<PlayerStats>>() {
            @Override
            public List<PlayerStats> call() throws InterruptedException {
                // Thread.sleep(5000);

                List<PlayerStats> playersStats = new ArrayList<>();
                PlayerStats playerStatsOne = playerDao.getPlayerStatsByIdSeasons(playerIdOne, pubgSeasonsEnum.getSeasonId());
                PlayerStats playerStatsTwo = playerDao.getPlayerStatsByIdSeasons(playerIdTwo, pubgSeasonsEnum.getSeasonId());

                playersStats.add(playerStatsOne);
                playersStats.add(playerStatsTwo);

                return playersStats;
            }
        }, new TaskRunner.TaskRunnerCallback<List<PlayerStats>>() {
            @Override
            public void execute(List<PlayerStats> data) {
                playerStats.postValue(data);
            }
        });
    }

}

