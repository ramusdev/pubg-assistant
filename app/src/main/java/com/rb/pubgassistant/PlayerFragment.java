package com.rb.pubgassistant;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.pm.ActivityInfo;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class PlayerFragment extends Fragment {

    private PlayerViewModel viewModel;
    private View view;
    private static final String BUNDLE_ID = "bundle_id";
    private List<PlayerEntity> listUserInterface;

    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_player));

        // View
        view = inflater.inflate(R.layout.fragment_user, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView textView = view.findViewById(R.id.statistics_text);
        textView.setText(Html.fromHtml(getResources().getString(R.string.players_text), Html.FROM_HTML_MODE_COMPACT));

        final Button submitButton = view.findViewById(R.id.button_submit);
        submitButton.setOnClickListener(submitButtonListener());

        LinearLayout playersBlock = view.findViewById(R.id.players_block);
        // LayoutTransition layoutTransition = playersBlock.getLayoutTransition();
        // layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, null);

        viewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        MutableLiveData<List<PlayerEntity>> players = viewModel.getPlayers();
        players.observe(getViewLifecycleOwner(), new Observer<List<PlayerEntity>>() {
            @Override
            public void onChanged(List<PlayerEntity> playerEntities) {

                List<PlayerEntity> playersForInterface = new ArrayList<>();
                if (listUserInterface != null) {
                    playersForInterface = playerEntities
                            .stream()
                            .filter(playerEntity -> !listUserInterface.contains(playerEntity))
                            .collect(Collectors.toList());
                } else {
                    playersForInterface = playerEntities;
                }

                listUserInterface = playerEntities;

                for (PlayerEntity playerEntity : playersForInterface) {

                    LinearLayout viewBlock = (LinearLayout) getLayoutInflater().inflate(R.layout.player_block, null);
                    playersBlock.addView(viewBlock);

                    String name = playerEntity.getName();
                    long id = playerEntity.getId();

                    TextView textView = viewBlock.findViewById(R.id.player_name);
                    textView.setText(name);

                    Button buttonInfo = viewBlock.findViewById(R.id.player_info);
                    buttonInfo.setOnClickListener(infoButtonListener(id));

                    Button buttonDelete = viewBlock.findViewById(R.id.player_delete);
                    buttonDelete.setOnClickListener(deleteButtonListener(id, playersBlock, viewBlock));
                }
            }
        });

    }

    private View.OnClickListener deleteButtonListener(long id, LinearLayout playersBlock, View viewBlock) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
                PlayerDao playerDao = appDatabase.playerDao();

                TaskRunner<Integer> taskRunner = new TaskRunner<Integer>();
                taskRunner.executeAsync(new Callable<Integer>() {
                    @Override
                    public Integer call() throws InterruptedException {
                        PlayerEntity playerEntity = new PlayerEntity();
                        playerEntity.setId(id);
                        playerDao.delete(playerEntity);

                        return 1;
                    }
                }, new TaskRunner.TaskRunnerCallback<Integer>() {
                    @Override
                    public void execute(Integer data) {
                        playersBlock.removeView(viewBlock);
                        showSubmitMessage("PLAYER DELETED");

                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.updateDefaultPlayerTab();
                    }
                });

            }
        };
    }

    private View.OnClickListener infoButtonListener(long id) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                MenuItem menuItem = mainActivity.navigationView.getMenu().findItem(R.id.nav_item_statistics);
                menuItem.setChecked(true);

                Bundle bundle = new Bundle();
                bundle.putLong(BUNDLE_ID, id);

                StatisticsTabFragment statisticsTabFragment = new StatisticsTabFragment();
                statisticsTabFragment.setArguments(bundle);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                transaction.replace(R.id.nav_host_fragment, statisticsTabFragment).commit();


                Log.d("MyTag", "click");
            }
        };
    }

    private View.OnClickListener submitButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = view.findViewById(R.id.edit_text);
                String name = editText.getText().toString();

                InputMethodManager inputManager = (InputMethodManager) MyApplicationContext.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                if (name.trim().isEmpty()) {
                    showSubmitMessage("ERROR FIELD IS EMPTY");
                    return;
                }

                TaskRunner<Integer> taskRunner = new TaskRunner();
                Callable callable = new PlayerStatsCallable(name);
                taskRunner.executeAsync(callable, new TaskRunner.TaskRunnerCallback<Integer>() {
                    @Override
                    public void execute(Integer responseCode) {
                        if (responseCode == 404) {
                            showSubmitMessage("PLAYER NOT FOUND");
                        } else if (responseCode == 200){
                            viewModel.loadData();
                            editText.setText("");
                            showSubmitMessage("PLAYER ADDED SUCCESSFULLY");

                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.updateDefaultPlayerTab();
                        }
                    }
                });
            }
        };
    }

    private void showSubmitMessage(String message) {
        // String text = MyApplicationContext.getAppContext().getResources().getString(R.string.statistics_message_userdeleted);
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(getResources().getColor(R.color.blue_light_2));
        snackbar.setTextColor(getResources().getColor(R.color.grey_light_light));
        snackbar.show();
    }

}