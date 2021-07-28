package com.example.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private ViewPager mViewPager;
    private List<ImageView> mList;
    private int[] bannerImages = {R.drawable.hp_banner,R.drawable.os_banner};
    private BannerAdapter mAdapter;
    private BannerListener bannerListener;

    private ImageView bookIcon,audiobookIcon,checkIcon,videoIcon;

    private View view;

    private  boolean isStop = false;

    private TextView login,search;
    private LinearLayout osBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        initView();
        initData();
        initAction();
        checkIcon.setOnClickListener(this);
        login.setOnClickListener(this);
        search.setOnClickListener(this);
        bookIcon.setOnClickListener(this);
        audiobookIcon.setOnClickListener(this);
        videoIcon.setOnClickListener(this);
        isStop = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop)
                {
                    SystemClock.sleep(4000);
                    if(!isStop)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);

                            }
                        });
                    }

                }
            }
        }).start();
        return view;
    }

    private void initView()
    {
        osBook = view.findViewById(R.id.ly_home_os);
        osBook.setOnClickListener(this);
        mViewPager = view.findViewById(R.id.vpger_home);
        bookIcon = view.findViewById(R.id.icon_home_book);
        audiobookIcon = view.findViewById(R.id.icon_home_audiobook);
        videoIcon = view.findViewById(R.id.icon_home_Video);
        checkIcon = view.findViewById(R.id.icon_home_ISBN);
        login = view.findViewById(R.id.login_home);
        search = view.findViewById(R.id.search_home);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.icon_home_ISBN:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.isbn-check.com/"));
                startActivity(Intent.createChooser(i,"ISBN Checker"));
                break;
            case R.id.login_home:
                Intent i2 = new Intent(getActivity(),Login.class);
                startActivity(i2);
                break;
            case R.id.search_home:
                Intent i3 = new Intent(getActivity(),SearchBook.class);
                startActivity(i3);
                break;
            case R.id.ly_home_os:
                Intent i4 = new Intent(getActivity(),Audiobook.class);
                startActivity(i4);
                break;
            case R.id.icon_home_book:

            case R.id.icon_home_audiobook:

            case R.id.icon_home_Video:
                Intent i7 = new Intent(getActivity(),Category.class);
                startActivity(i7);
                break;



        }

    }

    class BannerListener implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initAction()
    {
        bannerListener = new BannerListener();
        mViewPager.setOnPageChangeListener(bannerListener);
        int index = 0;
        mViewPager.setCurrentItem(index);
    }

    private void initData()
    {
        mList = new ArrayList<ImageView>();
        View view;
        for(int i = 0; i < bannerImages.length; i++)
        {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundResource(bannerImages[i]);
            mList.add(imageView);
        }
        mAdapter = new BannerAdapter(mList);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy()
    {
        isStop = true;
        super.onDestroy();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        isStop = true;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        isStop = false;
        if(CurrentUser.getLoginState())
        {
            login.setVisibility(View.INVISIBLE);
        }
        else
        {
            login.setVisibility(View.VISIBLE);

        }
    }

}
