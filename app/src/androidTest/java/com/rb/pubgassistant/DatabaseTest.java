package com.rb.pubgassistant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    public static PlayerDao playerDao;

    @BeforeClass
    public static void setUpBeforeAll() {
        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        playerDao = appDatabase.playerDao();
    }

    @Before
    public void setUpBefore() {
        playerDao.deleteAllRecords();
    }

    @After
    public void setUpAfter() {
        Log.d("MyTag", "Setup after");
    }

    @Test
    public void insertPlayerAndRead() {
        PlayerEntity player = new PlayerEntity();
        player.setName("player read");
        player.setPlayerId("player id read");

        playerDao.insert(player);
        PlayerEntity playerFromBd = playerDao.getByName(player.getName());

        assertEquals(player.getName(), playerFromBd.getName());
    }

    @Test
    public void insertPlayerAndDelete() {
        PlayerEntity player = new PlayerEntity();
        player.setName("player delete");
        player.setPlayerId("player id delete");

        long playerFromBdId = playerDao.insert(player);
        player.setId(playerFromBdId);
        playerDao.delete(player);

        PlayerEntity playerFromBd = playerDao.getByName(player.getName());

        assertNull(playerFromBd);
    }
}
