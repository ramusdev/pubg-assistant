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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class StatisticsCompareFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private AboutViewModel mViewModel;
    View view;
    AppDatabase appDatabase;
    PlayerDao playerDao;
    public int mode;
    private static final String BUNDLE_MODE = "bundle_mode";
    private static final String BUNDLE_ID_ONE = "bundle_id_one";
    private static final String BUNDLE_ID_TWO = "bundle_id_two";
    private StatisticsCompareFragmentView statisticsCompareFragmentView;
    public Spinner spinner;
    public boolean preventSelection = true;
    public long playerIdOne;
    public long playerIdTwo;

    // Statistics
    private TextView nameOne;
    private TextView nameTwo;
    private TextView gameMode;
    private TextView assistsOne;
    private TextView assistsTwo;
    private TextView boostsOne;
    private TextView boostsTwo;
    private TextView knockedOne;
    private TextView knockedTwo;
    private TextView teamkillsOne;
    private TextView teamkillsTwo;
    private TextView toptenOne;
    private TextView toptenTwo;
    private TextView suicidesOne;
    private TextView suicidesTwo;
    private TextView roundsplayedOne;
    private TextView roundsplayedTwo;
    private TextView roundmostkillsOne;
    private TextView roundmostkillsTwo;
    private TextView roadkillsOne;
    private TextView roadkillsTwo;
    private TextView revivesOne;
    private TextView revivesTwo;
    private TextView rankpointsOne;
    private TextView rankpointsTwo;
    // private TextView rankpointstitle;
    private TextView mostsurvivaltimeOne;
    private TextView mostsurvivaltimeTwo;
    private TextView lossesOne;
    private TextView lossesTwo;
    private TextView longesttimesurvivedOne;
    private TextView longesttimesurvivedTwo;
    private TextView longestkillOne;
    private TextView longestkillTwo;
    private TextView healsOne;
    private TextView healsTwo;
    private TextView daysOne;
    private TextView daysTwo;
    private TextView damagedealtOne;
    private TextView damagedealtTwo;

    // Kills
    private TextView killsOne;
    private TextView killsTwo;
    private TextView dailykillsOne;
    private TextView dailykillsTwo;
    private TextView weeklykillsOne;
    private TextView weeklykillsTwo;
    private TextView headshotkillsOne;
    private TextView headshotkillsTwo;
    private TextView killstreaksOne;
    private TextView killstreaksTwo;

    // Wins
    private TextView winsOne;
    private TextView winsTwo;
    private TextView dailywinsOne;
    private TextView dailywinsTwo;
    private TextView weeklywinsOne;
    private TextView weeklywinsTwo;
    private TextView winpointsOne;
    private TextView winpointsTwo;

    // Information
    private TextView weaponsacquiredOne;
    private TextView weaponsacquiredTwo;
    private TextView walkdistanceOne;
    private TextView walkdistanceTwo;
    private TextView wehicledestroysOne;
    private TextView wehicledestroysTwo;
    private TextView timesurvivedOne;
    private TextView timesurvivedTwo;
    private TextView swimdistanceOne;
    private TextView swimdistanceTwo;
    private TextView ridedistanceOne;
    private TextView ridedistanceTwo;

    public static DonateFragment newInstance() {
        return new DonateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_statistics));

        mode = getArguments().getInt(BUNDLE_MODE);
        playerIdOne = getArguments().getLong(BUNDLE_ID_ONE);
        playerIdTwo = getArguments().getLong(BUNDLE_ID_TWO);
        // mode = 0;
        view = inflater.inflate(R.layout.fragment_stats_compare, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        appDatabase = MyApplication.getInstance().getDatabase();
        playerDao = appDatabase.playerDao();

        // Statistics
        nameOne = view.findViewById(R.id.user_name_one);
        nameTwo = view.findViewById(R.id.user_name_two);

        gameMode = view.findViewById(R.id.data_mode);
        assistsOne = view.findViewById(R.id.data_assists_one);
        assistsTwo = view.findViewById(R.id.data_assists_two);
        boostsOne = view.findViewById(R.id.data_boosts_one);
        boostsTwo = view.findViewById(R.id.data_boosts_two);
        knockedOne = view.findViewById(R.id.data_knocked_one);
        knockedTwo = view.findViewById(R.id.data_knocked_two);

        teamkillsOne = view.findViewById(R.id.data_teamkills_one);
        teamkillsTwo = view.findViewById(R.id.data_teamkills_two);
        toptenOne = view.findViewById(R.id.data_topten_one);
        toptenTwo = view.findViewById(R.id.data_topten_two);
        suicidesOne = view.findViewById(R.id.data_suicides_one);
        suicidesTwo = view.findViewById(R.id.data_suicides_two);
        roundsplayedOne = view.findViewById(R.id.data_roundsplayed_one);
        roundsplayedTwo = view.findViewById(R.id.data_roundsplayed_two);
        roundmostkillsOne = view.findViewById(R.id.data_roundmostkills_one);
        roundmostkillsTwo = view.findViewById(R.id.data_roundmostkills_two);
        roadkillsOne = view.findViewById(R.id.data_roadkills_one);
        roadkillsTwo = view.findViewById(R.id.data_roadkills_two);
        revivesOne = view.findViewById(R.id.data_revives_one);
        revivesTwo = view.findViewById(R.id.data_revives_two);
        rankpointsOne = view.findViewById(R.id.data_rankpoints_one);
        rankpointsTwo = view.findViewById(R.id.data_rankpoints_two);
        // rankpointstitle = view.findViewById(R.id.data_rankpointstitle);
        mostsurvivaltimeOne = view.findViewById(R.id.data_mostsurvivaltime_one);
        mostsurvivaltimeTwo = view.findViewById(R.id.data_mostsurvivaltime_two);
        lossesOne = view.findViewById(R.id.data_losses_one);
        lossesTwo = view.findViewById(R.id.data_losses_two);
        longesttimesurvivedOne = view.findViewById(R.id.data_longesttimesurvived_one);
        longesttimesurvivedTwo = view.findViewById(R.id.data_longesttimesurvived_two);
        longestkillOne = view.findViewById(R.id.data_longestkill_one);
        longestkillTwo = view.findViewById(R.id.data_longestkill_two);
        healsOne = view.findViewById(R.id.data_heals_one);
        healsTwo = view.findViewById(R.id.data_heals_two);
        daysOne= view.findViewById(R.id.data_days_one);
        daysTwo = view.findViewById(R.id.data_days_two);
        damagedealtOne = view.findViewById(R.id.data_damagedealt_one);
        damagedealtTwo = view.findViewById(R.id.data_damagedealt_two);

        // Kills
        killsOne = view.findViewById(R.id.data_kills_one);
        killsTwo = view.findViewById(R.id.data_kills_two);
        dailykillsOne = view.findViewById(R.id.data_dailykills_one);
        dailykillsTwo = view.findViewById(R.id.data_dailykills_two);
        weeklykillsOne = view.findViewById(R.id.data_weeklykills_one);
        weeklykillsTwo = view.findViewById(R.id.data_weeklykills_two);
        headshotkillsOne = view.findViewById(R.id.data_headshotkills_one);
        headshotkillsTwo = view.findViewById(R.id.data_headshotkills_two);
        killstreaksOne = view.findViewById(R.id.data_killstreaks_one);
        killstreaksTwo = view.findViewById(R.id.data_killstreaks_two);

        // Wins
        winsOne = view.findViewById(R.id.data_wins_one);
        winsTwo = view.findViewById(R.id.data_wins_two);
        dailywinsOne = view.findViewById(R.id.data_dailywins_one);
        dailywinsTwo = view.findViewById(R.id.data_dailywins_two);
        weeklywinsOne = view.findViewById(R.id.data_weeklywins_one);
        weeklywinsTwo = view.findViewById(R.id.data_weeklywins_two);
        winpointsOne = view.findViewById(R.id.data_winpoints_one);
        winpointsTwo = view.findViewById(R.id.data_winpoints_two);

        // Information
        weaponsacquiredOne = view.findViewById(R.id.data_weaponsacquired_one);
        weaponsacquiredTwo = view.findViewById(R.id.data_weaponsacquired_two);
        walkdistanceOne = view.findViewById(R.id.data_walkdistance_one);
        walkdistanceTwo = view.findViewById(R.id.data_walkdistance_two);
        wehicledestroysOne = view.findViewById(R.id.data_wehicledestroys_one);
        wehicledestroysTwo = view.findViewById(R.id.data_wehicledestroys_two);
        timesurvivedOne = view.findViewById(R.id.data_timesurvived_one);
        timesurvivedTwo = view.findViewById(R.id.data_timesurvived_two);
        swimdistanceOne = view.findViewById(R.id.data_swimdistance_one);
        swimdistanceTwo = view.findViewById(R.id.data_swimdistance_two);
        ridedistanceOne = view.findViewById(R.id.data_ridedistance_one);
        ridedistanceTwo = view.findViewById(R.id.data_ridedistance_two);

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
        statisticsCompareFragmentView = ViewModelProviders.of(getActivity()).get(StatisticsCompareFragmentView.class);
        LiveData<List<PlayerStats>> playerStatsLiveData = statisticsCompareFragmentView.getPlayerStats(playerIdOne, playerIdTwo, PubgSeasonsEnum.PC_2018_15);
        playerStatsLiveData.observe(getViewLifecycleOwner(), new Observer<List<PlayerStats>>() {
            @Override
            public void onChanged(List<PlayerStats> playersStats) {
                updateStatistics(playersStats);

                String seasonIdString = playersStats.get(0).getStats().get(mode).getSeasonId();
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

            statisticsCompareFragmentView.setSeason(playerIdOne, playerIdTwo, pubgSeasonsEnum);
        }

        preventSelection = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void updateStatistics(List<PlayerStats> playerStats) {

        nameOne.setText(String.valueOf(playerStats.get(0).getName()));
        nameTwo.setText(String.valueOf(playerStats.get(1).getName()));
        gameMode.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getMode()));
        assistsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getAssists()));
        assistsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getAssists()));
        boostsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getBoosts()));
        boostsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getBoosts()));
        // knocked.setText(String.valueOf(playerStats.getStats().get(mode).getKnocked()));
        teamkillsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getTeamKills()));
        teamkillsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getTeamKills()));
        toptenOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getTop10s()));
        toptenTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getTop10s()));
        suicidesOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getSuicides()));
        suicidesTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getSuicides()));
        roundsplayedOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getRoundsPlayed()));
        roundsplayedTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getRoundsPlayed()));
        roundmostkillsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getRoundMostKills()));
        roundmostkillsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getRoundMostKills()));
        roadkillsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getRoadKills()));
        roadkillsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getRoadKills()));
        revivesOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getRevives()));
        revivesTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getRevives()));
        rankpointsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getRankPoints()));
        rankpointsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getRankPoints()));
        // rankpointstitle.setText(String.valueOf(playerStats.getStats().get(mode).getRankPointsTitle()));
        mostsurvivaltimeOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getMostSurvivalTime()));
        mostsurvivaltimeTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getMostSurvivalTime()));
        lossesOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getLosses()));
        lossesTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getLosses()));
        longesttimesurvivedOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getLongestTimeSurvived()));
        longesttimesurvivedTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getLongestTimeSurvived()));
        longestkillOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getLongestKill()));
        longestkillTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getLongestKill()));
        healsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getHeals()));
        healsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getHeals()));
        daysOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getDays()));
        daysTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getDays()));
        damagedealtOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getDamageDealt()));
        damagedealtTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getDamageDealt()));


        killsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getKills()));
        killsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getKills()));
        dailykillsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getDailyKills()));
        dailykillsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getDailyKills()));
        weeklykillsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getWeeklyKills()));
        weeklykillsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getWeeklyKills()));
        headshotkillsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getHeadshotKills()));
        headshotkillsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getHeadshotKills()));
        killstreaksOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getMaxKillStreaks()));
        killstreaksTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getMaxKillStreaks()));
        winsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getWins()));
        winsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getWins()));
        dailywinsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getDailyWins()));
        dailywinsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getDailyWins()));
        weeklywinsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getWeeklyWins()));
        weeklywinsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getWeeklyWins()));
        winpointsOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getWinPoints()));
        winpointsTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getWinPoints()));
        weaponsacquiredOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getWeaponsAcquired()));
        weaponsacquiredTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getWeaponsAcquired()));
        walkdistanceOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getWalkDistance()));
        walkdistanceTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getWalkDistance()));
        // wehicledestroys.setText(String.valueOf(playerStats.getStats().get(mode).getWehicleDestroys()));
        timesurvivedOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getTimeSurvived()));
        timesurvivedTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getTimeSurvived()));
        swimdistanceOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getSwimDistance()));
        swimdistanceTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getSwimDistance()));
        ridedistanceOne.setText(String.valueOf(playerStats.get(0).getStats().get(mode).getRideDistance()));
        ridedistanceTwo.setText(String.valueOf(playerStats.get(1).getStats().get(mode).getRideDistance()));

    }
}