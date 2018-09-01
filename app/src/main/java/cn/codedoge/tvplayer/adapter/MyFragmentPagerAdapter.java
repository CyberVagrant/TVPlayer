package cn.codedoge.tvplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.codedoge.tvplayer.fragment.LiveFragment;
import cn.codedoge.tvplayer.fragment.LocalFragment;

/**
 * Created by codedoge on 2018/1/4.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new LocalFragment();
                break;
            case 1:
                fragment = new LiveFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
