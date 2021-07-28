package com.example.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BookshelfFragment extends Fragment implements View.OnClickListener {
    private RadioButton book, audiobook, video;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private Fragment bookImageList,audiobookImageList, videoImageList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookshelf_fragment, container, false);
        book = view.findViewById(R.id.rb_tab_book);
        audiobook = view.findViewById(R.id.rb_tab_audiobook);
        video = view.findViewById(R.id.rb_tab_Video);
        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();

        book.setOnClickListener(this);
        audiobook.setOnClickListener(this);
        video.setOnClickListener(this);

        setSwipePage(0);


        return view;
    }

    public void setSwipePage(int i)
    {
        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();

        switch (i)
        {
            case 0:
                resetBtnColor();
                book.setBackgroundColor(getResources().getColor(R.color.brown));
                if(bookImageList == null) {
                    bookImageList = new BookImagesList();
                }
                transaction.replace(R.id.fragment_bookshelf,bookImageList);
                break;
            case 1:
                resetBtnColor();
                audiobook.setBackgroundColor(getResources().getColor(R.color.brown));

                if(audiobookImageList == null)
                {
                    audiobookImageList = new AudiobookImageList();

                }
                transaction.replace(R.id.fragment_bookshelf,audiobookImageList);
                break;
            case 2:
                resetBtnColor();
                video.setBackgroundColor(getResources().getColor(R.color.brown));

                if(videoImageList == null)
                {
                    videoImageList = new VideoImageList();

                }
                transaction.replace(R.id.fragment_bookshelf,videoImageList);
                break;
        }
        transaction.commit();
    }

    private void resetBtnColor()
    {
        book.setBackgroundColor(Color.parseColor("#000000"));

        audiobook.setBackgroundColor(Color.parseColor("#000000"));
        video.setBackgroundColor(Color.parseColor("#000000"));

    }





    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.rb_tab_book:
                setSwipePage(0);
                break;
            case R.id.rb_tab_audiobook:
                setSwipePage(1);
                break;
            case R.id.rb_tab_Video:
                setSwipePage(2);
                break;


        }

    }
}
