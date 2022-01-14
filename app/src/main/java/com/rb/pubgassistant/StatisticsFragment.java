package com.rb.pubgassistant;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class StatisticsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private AboutViewModel mViewModel;
    View view;
    AppDatabase appDatabase;
    PlayerDao playerDao;
    public int mode;
    private static final String BUNDLE_MODE = "bundle_mode";
    private static final String BUNDLE_ID = "bundle_id";
    private StatisticsFragmentView statisticsFragmentView;
    public Spinner spinner;
    public boolean preventSelection = true;
    public long playerId;

    // Statistics
    private TextView name;
    private TextView gameMode;
    private TextView assists;
    private TextView boosts;
    private TextView knocked;
    private TextView teamkills;
    private TextView topten;
    private TextView suicides;
    private TextView roundsplayed;
    private TextView roundmostkills;
    private TextView roadkills;
    private TextView revives;
    private TextView rankpoints;
    // private TextView rankpointstitle;
    private TextView mostsurvivaltime;
    private TextView losses;
    private TextView longesttimesurvived;
    private TextView longestkill;
    private TextView heals;
    private TextView days;
    private TextView damagedealt;

    // Kills
    private TextView kills;
    private TextView dailykills;
    private TextView weeklykills;
    private TextView headshotkills;
    private TextView killstreaks;

    // Wins
    private TextView wins;
    private TextView dailywins;
    private TextView weeklywins;
    private TextView winpoints;

    // Information
    private TextView weaponsacquired;
    private TextView walkdistance;
    private TextView wehicledestroys;
    private TextView timesurvived;
    private TextView swimdistance;
    private TextView ridedistance;

    public static DonateFragment newInstance() {
        return new DonateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_statistics));

        mode = getArguments().getInt(BUNDLE_MODE);
        playerId = getArguments().getLong(BUNDLE_ID);
        // mode = 0;
        view = inflater.inflate(R.layout.fragment_stats, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        appDatabase = MyApplication.getInstance().getDatabase();
        playerDao = appDatabase.playerDao();

        // Statistics
        name = view.findViewById(R.id.user_name);
        gameMode = view.findViewById(R.id.data_mode);
        assists = view.findViewById(R.id.data_assists);
        boosts = view.findViewById(R.id.data_boosts);
        knocked = view.findViewById(R.id.data_knocked);
        teamkills = view.findViewById(R.id.data_teamkills);
        topten = view.findViewById(R.id.data_topten);
        suicides = view.findViewById(R.id.data_suicides);
        roundsplayed = view.findViewById(R.id.data_roundsplayed);
        roundmostkills = view.findViewById(R.id.data_roundmostkills);
        roadkills = view.findViewById(R.id.data_roadkills);
        revives = view.findViewById(R.id.data_revives);
        rankpoints = view.findViewById(R.id.data_rankpoints);
        // rankpointstitle = view.findViewById(R.id.data_rankpointstitle);
        mostsurvivaltime = view.findViewById(R.id.data_mostsurvivaltime);
        losses = view.findViewById(R.id.data_losses);
        longesttimesurvived = view.findViewById(R.id.data_longesttimesurvived);
        longestkill = view.findViewById(R.id.data_longestkill);
        heals = view.findViewById(R.id.data_heals);
        days = view.findViewById(R.id.data_days);
        damagedealt = view.findViewById(R.id.data_damagedealt);

        // Kills
        kills = view.findViewById(R.id.data_kills);
        dailykills = view.findViewById(R.id.data_dailykills);
        weeklykills = view.findViewById(R.id.data_weeklykills);
        headshotkills = view.findViewById(R.id.data_headshotkills);
        killstreaks = view.findViewById(R.id.data_killstreaks);

        // Wins
        wins = view.findViewById(R.id.data_wins);
        dailywins = view.findViewById(R.id.data_dailywins);
        weeklywins = view.findViewById(R.id.data_weeklywins);
        winpoints = view.findViewById(R.id.data_winpoints);

        // Information
        weaponsacquired = view.findViewById(R.id.data_weaponsacquired);
        walkdistance = view.findViewById(R.id.data_walkdistance);
        wehicledestroys = view.findViewById(R.id.data_wehicledestroys);
        timesurvived = view.findViewById(R.id.data_timesurvived);
        swimdistance = view.findViewById(R.id.data_swimdistance);
        ridedistance = view.findViewById(R.id.data_ridedistance);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MyApplicationContext.getAppContext(), R.array.seasons_pc, R.layout.statistics_spinner_row);
        String[] seasons = {"Season 15", "Season 14", "Season 13", "Season 12", "Season 11"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyApplicationContext.getAppContext(), R.layout.spinner_selected, seasons);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        // R.layout.statistics_spinner_row
        // adapter.setDropDownViewResource(R.layout.statistics_spinner_row);
        spinner.setAdapter(adapter);
        // spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(this);

        Log.d("MyTag", "Create");
        statisticsFragmentView = ViewModelProviders.of(getActivity()).get(StatisticsFragmentView.class);
        LiveData<PlayerStats> playerStatsLiveData = statisticsFragmentView.getPlayerStats(playerId, PubgSeasonsEnum.PC_2018_15);
        playerStatsLiveData.observe(getViewLifecycleOwner(), new Observer<PlayerStats>() {
            @Override
            public void onChanged(PlayerStats playerStats) {
                updateStatistics(playerStats);

                String seasonIdString = playerStats.getStats().get(mode).getSeasonId();
                switch (seasonIdString) {
                    case "division.bro.official.pc-2018-15":
                        preventSelection = true;
                        spinner.setSelection(0, true);
                        break;
                    case "division.bro.official.pc-2018-14":
                        preventSelection = true;
                        spinner.setSelection(1, true);
                        break;
                    case "division.bro.official.pc-2018-13":
                        preventSelection = true;
                        spinner.setSelection(2, true);
                        break;
                    case "division.bro.official.pc-2018-12":
                        preventSelection = true;
                        spinner.setSelection(3, true);
                        break;
                    case "division.bro.official.pc-2018-11":
                        preventSelection = true;
                        spinner.setSelection(4, true);
                        break;
                }

                preventSelection = false;
                Log.d("MyTag", "Change");
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("MyTag", "Selected in");
        if (!preventSelection) {
            Log.d("MyTag", "Selected not prevent");
            PubgSeasonsEnum pubgSeasonsEnum = null;
            switch (i) {
                case 0:
                    pubgSeasonsEnum = PubgSeasonsEnum.PC_2018_15;
                    break;
                case 1:
                    pubgSeasonsEnum = PubgSeasonsEnum.PC_2018_14;
                    break;
                case 2:
                    pubgSeasonsEnum = PubgSeasonsEnum.PC_2018_13;
                    break;
                case 3:
                    pubgSeasonsEnum = PubgSeasonsEnum.PC_2018_12;
                    break;
                case 4:
                    pubgSeasonsEnum = PubgSeasonsEnum.PC_2018_11;
                    break;
            }

            statisticsFragmentView.setSeason(playerId, pubgSeasonsEnum);
        }

        preventSelection = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void updateStatistics(PlayerStats playerStats) {
        name.setText(String.valueOf(playerStats.getName()));
        gameMode.setText(String.valueOf(playerStats.getStats().get(mode).getMode()));
        assists.setText(String.valueOf(playerStats.getStats().get(mode).getAssists()));
        boosts.setText(String.valueOf(playerStats.getStats().get(mode).getBoosts()));
        kills.setText(String.valueOf(playerStats.getStats().get(mode).getKills()));
        // knocked.setText(String.valueOf(playerStats.getStats().get(mode).getKnocked()));
        teamkills.setText(String.valueOf(playerStats.getStats().get(mode).getTeamKills()));
        topten.setText(String.valueOf(playerStats.getStats().get(mode).getTop10s()));
        suicides.setText(String.valueOf(playerStats.getStats().get(mode).getSuicides()));
        roundsplayed.setText(String.valueOf(playerStats.getStats().get(mode).getRoundsPlayed()));
        roundmostkills.setText(String.valueOf(playerStats.getStats().get(mode).getRoundMostKills()));
        roadkills.setText(String.valueOf(playerStats.getStats().get(mode).getRoadKills()));
        revives.setText(String.valueOf(playerStats.getStats().get(mode).getRevives()));
        rankpoints.setText(String.valueOf(playerStats.getStats().get(mode).getRankPoints()));
        // rankpointstitle.setText(String.valueOf(playerStats.getStats().get(mode).getRankPointsTitle()));
        mostsurvivaltime.setText(String.valueOf(playerStats.getStats().get(mode).getMostSurvivalTime()));
        losses.setText(String.valueOf(playerStats.getStats().get(mode).getLosses()));
        longesttimesurvived.setText(String.valueOf(playerStats.getStats().get(mode).getLongestTimeSurvived()));
        longestkill.setText(String.valueOf(playerStats.getStats().get(mode).getLongestKill()));
        heals.setText(String.valueOf(playerStats.getStats().get(mode).getHeals()));
        days.setText(String.valueOf(playerStats.getStats().get(mode).getDays()));
        damagedealt.setText(String.valueOf(playerStats.getStats().get(mode).getDamageDealt()));
        dailykills.setText(String.valueOf(playerStats.getStats().get(mode).getDailyKills()));
        weeklykills.setText(String.valueOf(playerStats.getStats().get(mode).getWeeklyKills()));
        headshotkills.setText(String.valueOf(playerStats.getStats().get(mode).getHeadshotKills()));
        killstreaks.setText(String.valueOf(playerStats.getStats().get(mode).getMaxKillStreaks()));
        wins.setText(String.valueOf(playerStats.getStats().get(mode).getWins()));
        dailywins.setText(String.valueOf(playerStats.getStats().get(mode).getDailyWins()));
        weeklywins.setText(String.valueOf(playerStats.getStats().get(mode).getWeeklyWins()));
        winpoints.setText(String.valueOf(playerStats.getStats().get(mode).getWinPoints()));
        weaponsacquired.setText(String.valueOf(playerStats.getStats().get(mode).getWeaponsAcquired()));
        walkdistance.setText(String.valueOf(playerStats.getStats().get(mode).getWalkDistance()));
        // wehicledestroys.setText(String.valueOf(playerStats.getStats().get(mode).getWehicleDestroys()));
        timesurvived.setText(String.valueOf(playerStats.getStats().get(mode).getTimeSurvived()));
        swimdistance.setText(String.valueOf(playerStats.getStats().get(mode).getSwimDistance()));
        ridedistance.setText(String.valueOf(playerStats.getStats().get(mode).getRideDistance()));
    }
}