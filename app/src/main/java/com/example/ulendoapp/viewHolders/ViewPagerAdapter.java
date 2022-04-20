package com.example.ulendoapp.viewHolders;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * The type View pager adapter.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final  ArrayList<String> fragmentTitle = new ArrayList<>();

    /**
     * Instantiates a new View pager adapter.
     *
     * @param fm the fm
     */
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    /**
     * Add fragment.
     *
     * @param fragment the fragment
     * @param title    the title
     */
    public  void addFragment(Fragment fragment, String title){
        fragmentArrayList.add(fragment);
        fragmentTitle.add(title);
    }
    public CharSequence getPageTitle(int position){
        return fragmentTitle.get(position);
    }
}
