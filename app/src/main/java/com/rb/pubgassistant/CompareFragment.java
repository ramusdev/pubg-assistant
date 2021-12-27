package com.rb.pubgassistant;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class CompareFragment extends Fragment {

    private CompareViewModel mViewModel;
    private View view;
    private Spinner spinnerOne;
    private Spinner spinnerTwo;
    private Map<String, Long> playersMap = new HashMap<>();
    private static final String BUNDLE_PLAYER_ONE = "bundle_player_one";
    private static final String BUNDLE_PLAYER_TWO = "bundle_player_two";
    private int playersSize = 0;

    public static CompareFragment newInstance() {
        return new CompareFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_player));

        // View
        view = inflater.inflate(R.layout.fragment_compare, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView textView = view.findViewById(R.id.compare_text);
        textView.setText(Html.fromHtml(getResources().getString(R.string.compare_text), Html.FROM_HTML_MODE_COMPACT));

        spinnerOne = (Spinner) view.findViewById(R.id.spinner_one);
        spinnerTwo = (Spinner) view.findViewById(R.id.spinner_two);

        Button buttonSubmit = view.findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(buttonSubmitListener());

        fillSpinners();
    }

    public View.OnClickListener buttonSubmitListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playersSize <= 0) {
                    showSubmitMessage("ERROR, YOU MUST FIRST TO ADD PLAYER");
                    return;
                }

                MainActivity mainActivity = (MainActivity) getActivity();
                MenuItem menuItem = mainActivity.navigationView.getMenu().findItem(R.id.nav_item_statistics);
                menuItem.setChecked(true);

                // Log.d("MyTag", (String) spinnerOne.getSelectedItem());
                // Log.d("MyTag", (String) spinnerOne.getSelectedItem());

                Bundle bundle = new Bundle();
                bundle.putLong(BUNDLE_PLAYER_ONE, playersMap.get((String) spinnerOne.getSelectedItem()));
                bundle.putLong(BUNDLE_PLAYER_TWO, playersMap.get((String) spinnerTwo.getSelectedItem()));

                StatisticsCompareTabFragment statisticsCompareTabFragment = new StatisticsCompareTabFragment();
                statisticsCompareTabFragment.setArguments(bundle);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                transaction.replace(R.id.nav_host_fragment, statisticsCompareTabFragment).commit();

            }
        };
    }

    public void fillSpinners() {

        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerDao playerDao = appDatabase.playerDao();

        TaskRunner<List<PlayerEntity>> taskRunner = new TaskRunner<List<PlayerEntity>>();
        taskRunner.executeAsync(new Callable<List<PlayerEntity>>() {
            @Override
            public List<PlayerEntity> call() throws InterruptedException {
                return playerDao.getPlayers();
            }
        }, new TaskRunner.TaskRunnerCallback<List<PlayerEntity>>() {
            @Override
            public void execute(List<PlayerEntity> players) {

                playersSize = players.size();
                String[] playersArray = new String[players.size()];
                for (int i = 0; i < players.size(); ++i) {
                    playersArray[i] = players.get(i).getName();
                    playersMap.put(players.get(i).getName(), players.get(i).getId());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyApplicationContext.getAppContext(), R.layout.spinner_selected, playersArray);
                adapter.setDropDownViewResource(R.layout.spinner_dropdown);

                spinnerOne.setAdapter(adapter);
                spinnerTwo.setAdapter(adapter);

            }
        });
    }

    private void showSubmitMessage(String message) {
        // String text = MyApplicationContext.getAppContext().getResources().getString(R.string.statistics_message_userdeleted);
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(getResources().getColor(R.color.blue_light_2));
        snackbar.setTextColor(getResources().getColor(R.color.grey_light_light));
        snackbar.show();
    }

}