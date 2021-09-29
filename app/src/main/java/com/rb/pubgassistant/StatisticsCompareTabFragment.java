package com.rb.pubgassistant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class StatisticsCompareTabFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 6;
    private static final String BUNDLE_MODE = "bundle_mode";
    private static final String BUNDLE_ID_ONE = "bundle_id_one";
    private static final String BUNDLE_ID_TWO = "bundle_id_two";
    private static final String BUNDLE_PLAYER_ONE = "bundle_player_one";
    private static final String BUNDLE_PLAYER_TWO = "bundle_player_two";
    private long playerIdOne;
    private long playerIdTwo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        viewPager.setId(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        playerIdOne = getArguments().getLong(BUNDLE_PLAYER_ONE);
        playerIdTwo = getArguments().getLong(BUNDLE_PLAYER_TWO);

        return x;

    }



    class MyAdapter extends FragmentPagerAdapter {
        // final Bundle arguments = new Bundle();
        // final StatisticsFragment statisticsFragment = new StatisticsFragment();

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {

            final Bundle arguments = new Bundle();
            final StatisticsCompareFragment statisticsCompareFragment = new StatisticsCompareFragment();

            switch (position){
                case 0 :
                    arguments.putInt(BUNDLE_MODE, 2);
                    break;
                case 1 :
                    arguments.putInt(BUNDLE_MODE, 3);
                    break;
                case 2:
                    arguments.putInt(BUNDLE_MODE, 0);
                    break;
                case 3:
                    arguments.putInt(BUNDLE_MODE, 1);
                    break;
                case 4:
                    arguments.putInt(BUNDLE_MODE, 4);
                    break;
                case 5:
                    arguments.putInt(BUNDLE_MODE, 5);
                    break;
            }

            arguments.putLong(BUNDLE_ID_ONE, playerIdOne);
            arguments.putLong(BUNDLE_ID_TWO, playerIdTwo);
            statisticsCompareFragment.setArguments(arguments);
            return statisticsCompareFragment;
        }


        @Override
        public int getCount() {
            return int_items;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public String getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Solo";
                case 1 :
                    return "Solo fpp";
                case 2 :
                    return "Duo";
                case 3 :
                    return "Duo fpp";
                case 4 :
                    return "Squad";
                case 5 :
                    return "Squad fpp";

            }

            return null;
        }

    }
}
