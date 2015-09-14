package dk.incipio.nerdlauncher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class NerdLauncherActivity extends FragmentActivity {

    private static NerdRunningFragment runningFragment = null;
    private static NerdLauncherFragment launcherFragment = null;

    // remove later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            if (pos == 1) {
                if (runningFragment==null) runningFragment = new NerdRunningFragment();
                return runningFragment;
            } else {
                if (launcherFragment==null) launcherFragment= new NerdLauncherFragment();
                return launcherFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }}
