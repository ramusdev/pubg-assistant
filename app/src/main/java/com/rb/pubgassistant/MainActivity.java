package com.rb.pubgassistant;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;
import com.rb.pubgassistant.data.DataDbHelper;

import java.util.List;
import java.util.concurrent.Callable;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public DataDbHelper dbHelper;
    // public static String AD_INTERSTITIAL_ID = "24534e1901884e398f1253216226017e";
    // public static String AD_INTERSTITIAL_ID = "a51cf41cb09d40f3b9cad9837a74ccfc";
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_VERSION = "version";
    private static final String ADMOB_INTERSTITIAL_ID = "ca-app-pub-4140002463111288/1213456548";
    // private static final String ADMOB_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String BUNDLE_ID = "bundle_id";
    public NavigationView navigationView;
    private Long playerDefaultId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        AdmobInterstitialAd admobInterstitialAd = new AdmobInterstitialAd.Builder()
                .show(true)
                .activity(this)
                .testMode(false)
                .adId(ADMOB_INTERSTITIAL_ID)
                .build();

        */

        // admobInterstitialAd.show();

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.grey_light_light));
        setSupportActionBar(toolbar);

        // Tasks after create
        createTasks();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();

                if (item.getItemId() == R.id.nav_item_player) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, PlayerFragment.class, null).commit();
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_statistics) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (playerDefaultId != null) {
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                                MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_item_statistics);
                                menuItem.setChecked(true);

                                Bundle bundle = new Bundle();
                                bundle.putLong(BUNDLE_ID, playerDefaultId);

                                StatisticsTabFragment statisticsTabFragment = new StatisticsTabFragment();
                                statisticsTabFragment.setArguments(bundle);

                                transaction.replace(R.id.nav_host_fragment, statisticsTabFragment).commit();
                            } else {
                                MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_item_player);
                                menuItem.setChecked(true);

                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                transaction.replace(R.id.nav_host_fragment, PlayerFragment.class, null).commit();
                            }
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_compare) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, CompareFragment.class, null).commit();
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_donate) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, DonateFragment.class, null).commit();
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_about) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, AboutFragment.class, null).commit();
                        }
                    }, 275);
                }

                if (item.getItemId() == R.id.nav_item_twitter) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.setChecked(true);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.nav_host_fragment, TweetsFragment.class, null).commit();
                        }
                    }, 275);
                }

                return false;
            }
        });

        // Set window top and bottom color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.blue_light_2));
        window.setNavigationBarColor(this.getResources().getColor(R.color.blue_light_2));

        loadDefaultPlayer();

        // getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, NewsFragment.class, null).commit();
    }

    public void loadDefaultPlayer() {
        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerDao playerDao = appDatabase.playerDao();

        TaskRunner<Long> taskRunner = new TaskRunner<Long>();
        taskRunner.executeAsync(new Callable<Long>() {
            @Override
            public Long call() throws InterruptedException {
                return playerDao.getTopOnePlayerId();
            }
        }, new TaskRunner.TaskRunnerCallback<Long>() {
            @Override
            public void execute(Long id) {
                playerDefaultId = id;

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                if (id != null) {
                    MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_item_statistics);
                    menuItem.setChecked(true);

                    Bundle bundle = new Bundle();
                    bundle.putLong(BUNDLE_ID, id);

                    StatisticsTabFragment statisticsTabFragment = new StatisticsTabFragment();
                    statisticsTabFragment.setArguments(bundle);

                    transaction.replace(R.id.nav_host_fragment, statisticsTabFragment).commit();
                } else {
                    MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_item_player);
                    menuItem.setChecked(true);

                    PlayerFragment playerFragment = new PlayerFragment();
                    transaction.replace(R.id.nav_host_fragment, playerFragment).commit();
                }

                Log.d("MyTag", "Top player id");
                Log.d("MyTag", String.valueOf(id));
                // players.postValue(data);
            }
        });
    }

    public void updateDefaultPlayerTab() {
        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        PlayerDao playerDao = appDatabase.playerDao();

        TaskRunner<Long> taskRunner = new TaskRunner<Long>();
        taskRunner.executeAsync(new Callable<Long>() {
            @Override
            public Long call() throws InterruptedException {
                return playerDao.getTopOnePlayerId();
            }
        }, new TaskRunner.TaskRunnerCallback<Long>() {
            @Override
            public void execute(Long id) {
                playerDefaultId = id;
            }
        });
    }

    public void createTasks() {
        Log.d("MyTag", "create task -->");

        // AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        // PlayerDao playerDao = appDatabase.playerDao();

        // Player player = new Player();
        // player.setName("Tom");

        // TaskRunner<Integer> taskRunner = new TaskRunner();
        // Callable callable = new PlayerStatsCallable();
        // taskRunner.executeAsync(callable);


        // NewsLoader newsLoader = new NewsLoader(this.getApplicationContext());
        // List<News> news = newsLoader.load();

        TweetsLoader tweetsLoader = new TweetsLoader(this.getApplicationContext());
        List<Tweet> tweets = tweetsLoader.load();

        TaskRunner<Integer> taskRunner = new TaskRunner<Integer>();
        // Callable playerUpdateCallable = new PlayerUpdateCallable();
        // taskRunner.executeAsync(playerUpdateCallable);

        // if (news.size() <= 0) {
            // Callable callable = new NewsUpdateCallable(5);
            // taskRunner.executeAsync(callable);
        // }

        if (tweets.size() <= 0) {
            Callable callable = new TweetsUpdateCallable();
            taskRunner.executeAsync(callable);
        }

        /*
        SharedPreferences sharedPreferences = MyApplicationContext.getAppContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String currentVersion = sharedPreferences.getString(APP_PREFERENCES_VERSION, "");

        if (! currentVersion.equals(getVersionName())) {

            Callable populatorWallapeper = new DatabasePopulatorCallable<Wallpaper>("Wallpapers/wallpapers.json", Wallpaper.class);
            taskRunner.executeAsync(populatorWallapeper);

            Callable populatorLegend = new DatabasePopulatorCallable<Legend>(getResources().getString(R.string.legends_json_dir), Legend.class);
            taskRunner.executeAsync(populatorLegend);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(APP_PREFERENCES_VERSION, getVersionName());
            editor.apply();

            Log.d("MyTag", "Save new version name");
        }
        */

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            );
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }

    public String getVersionName() {
        String versionName = null;

        try {
            ComponentName componentName = new ComponentName(MyApplicationContext.getAppContext(), MyApplicationContext.getAppContext().getClass());
            PackageInfo packageInfo = MyApplicationContext.getAppContext().getPackageManager().getPackageInfo(componentName.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("MyTag", "Error: getVersionName" + e.getMessage());
        }

        return versionName;
    }
}