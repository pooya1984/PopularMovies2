package com.example.pooya.popularmovies.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pooya.popularmovies.R;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    // Declare the number of ViewPager pages
    final int PAGE_COUNT = 3;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int arg0) {
        switch (arg0) {

            // Open FragmentTab1.java
            case 0:
                FragmentTab1 fragmenttab1 = new FragmentTab1();
                return fragmenttab1;

            // Open FragmentTab2.java
            case 1:
                FragmentTab1.FragmentTab2 fragmenttab2 = new FragmentTab1.FragmentTab2();
                return fragmenttab2;

            // Open FragmentTab3.java
            case 2:
                FragmentTab1.FragmentTab3 fragmenttab3 = new FragmentTab1.FragmentTab3();
                return fragmenttab3;
        }
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return PAGE_COUNT;
    }

    public static class FragmentTab1 extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Get the view from fragmenttab1.xml
            View view = inflater.inflate(R.layout.fragmenttab1, container, false);
            return view;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            setUserVisibleHint(true);
        }

        public static class FragmentTab3 extends Fragment {

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                // Get the view from fragmenttab3.xml
                View view = inflater.inflate(R.layout.fragmenttab3, container, false);
                return view;
            }

            @Override
            public void onSaveInstanceState(Bundle outState) {
                super.onSaveInstanceState(outState);
                setUserVisibleHint(true);
            }

        }

        public static class FragmentTab2 extends Fragment {

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                // Get the view from fragmenttab2.xml
                View view = inflater.inflate(R.layout.fragmenttab2, container, false);
                return view;
            }

            @Override
            public void onSaveInstanceState(Bundle outState) {
                super.onSaveInstanceState(outState);
                setUserVisibleHint(true);
            }

        }
    }
}
