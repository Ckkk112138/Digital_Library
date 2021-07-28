package com.example.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout homeLy,bookshelfLy,personLy;
    private ImageView homeImg,bookshelfImg,personalImg;

    private FrameLayout frameLayout;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private HomeFragment homeFragment;
    private BookshelfFragment bookshelfFragment;
    private PersonalFragment personalFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setSwipePage(0);
    }

    void initView()
    {
        frameLayout = findViewById(R.id.home_frame);
        homeLy = findViewById(R.id.home_ly);
        bookshelfLy = findViewById(R.id.book_ly);
        personLy = findViewById(R.id.person_ly);
        homeImg = findViewById(R.id.img_home);
        bookshelfImg = findViewById(R.id.img_book);
        personalImg = findViewById(R.id.img_person);

        homeLy.setOnClickListener(this);
        bookshelfLy.setOnClickListener(this);
        personLy.setOnClickListener(this);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();



    }



    public void setSwipePage(int i)
    {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        switch (i)
        {
            case 0:
                resetImgSelect();
                homeImg.setSelected(true);
                if(homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                transaction.replace(R.id.home_frame,homeFragment);
                break;
            case 1:
                resetImgSelect();
                bookshelfImg.setSelected(true);
                if(bookshelfFragment == null)
                {
                    bookshelfFragment = new BookshelfFragment();

                }
                transaction.replace(R.id.home_frame,bookshelfFragment);
                break;
            case 2:
                resetImgSelect();
                personalImg.setSelected(true);
                if(personalFragment == null)
                {
                    personalFragment = new PersonalFragment();

                }
                transaction.replace(R.id.home_frame,personalFragment);
                break;
        }
        transaction.commit();
    }

    /*private void hindFragments(FragmentTransaction transaction)
    {
        if(homeFragment != null)
        {
            transaction.hide(homeFragment);
        }
        if(bookshelfFragment != null)
        {
            transaction.hide(bookshelfFragment);
        }
        if(personalFragment != null)
        {
            transaction.hide(personalFragment);
        }
    }*/

    private void resetImgSelect()
    {
        homeImg.setSelected(false);
        bookshelfImg.setSelected(false);
        personalImg.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.home_ly:
                setSwipePage(0);
                break;
            case R.id.book_ly:
                setSwipePage(1);
                break;
            case R.id.person_ly:
                setSwipePage(2);
                break;


        }

    }
}
