package com.example.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    private List<ImageView> mList;

    public BannerAdapter(List<ImageView> list)
    {
        this.mList = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        int currentPosition = (position % mList.size());
        ImageView iv = mList.get(currentPosition);
        if(iv.getParent() != null)
        {
            container.removeView(iv);

        }
        container.addView(iv,0);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {

    }

}
