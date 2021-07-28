package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class BookImagesList extends Fragment {
    private View view;
    private LinearLayout linearLayout;
    private TextView title;
    private ImageView coverPage;
    private DatabaseHelper myDb;
    private ImageView delete;


    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.book_image, container, false);
        myDb = new DatabaseHelper(getActivity());
        initView();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteBookInBookshelf(CurrentUser.getCurrentUserName(), title.getText().toString());
                if(Book.addedToBookshelf)
                {
                    Book.addedToBookshelf = false;

                }
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Book.class);
                startActivity(i);
            }
        });
        return view;
    }


    private void initView()
    {

        linearLayout = view.findViewById(R.id.bookshelf_LL1);
        title = view.findViewById(R.id.bookshelf_name1);
        coverPage = view.findViewById(R.id.bookshelf_cover1);
        delete = view.findViewById(R.id.bookshelf_delete1);
        if(CurrentUser.getLoginState())
        {
            if(!myDb.getBookCoverName(CurrentUser.getCurrentUserName(),"Normal")[0].equals("-1"))
            {
                linearLayout.setVisibility(View.VISIBLE);

                title.setText(myDb.getBookCoverName(CurrentUser.getCurrentUserName(),"Normal")[0]);
                coverPage.setImageBitmap(myDb.getBookCover(CurrentUser.getCurrentUserName(),"Normal"));
            }
            else
            {
                linearLayout.setVisibility(View.INVISIBLE);

            }

        }
        else
        {
            linearLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }
}
