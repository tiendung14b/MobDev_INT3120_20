package com.example.slide5;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabFragmentManager extends FragmentStateAdapter {
    public TabFragmentManager(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new Fragment1();
            default:
                return new Fragment2();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}