package com.rb.pubgassistant;

import java.util.List;
import java.util.concurrent.Callable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayerViewModel extends ViewModel {
    private MutableLiveData<List<PlayerEntity>> players;

    public MutableLiveData<List<PlayerEntity>> getPlayers() {
        if (players == null) {
            this.players = new MutableLiveData<>();
            loadData();
        }

        return players;
    }

    public void setSeason(PubgSeasonsEnum pubgSeasonsEnum) {
        loadData();
    }

    public void loadData() {

        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerDao playerDao = appDatabase.playerDao();

        TaskRunner<List<PlayerEntity>> taskRunner = new TaskRunner<List<PlayerEntity>>();
        taskRunner.executeAsync(new Callable<List<PlayerEntity>>() {
            @Override
            public List<PlayerEntity> call() throws InterruptedException {
                // Thread.sleep(5000);
                return playerDao.getPlayers();
            }
        }, new TaskRunner.TaskRunnerCallback<List<PlayerEntity>>() {
            @Override
            public void execute(List<PlayerEntity> data) {
                players.postValue(data);
            }
        });
    }
}