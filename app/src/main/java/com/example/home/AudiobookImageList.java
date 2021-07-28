package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AudiobookImageList extends Fragment {
    private View view;
    private LinearLayout linearLayout;
    private TextView title;
    private ImageView coverPage;
    private DatabaseHelper myDb;
    private ImageView delete;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.audiobook_image, container, false);
        myDb = new DatabaseHelper(getActivity());
        initView();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteBookInBookshelf(CurrentUser.getCurrentUserName(), title.getText().toString());
                if(Audiobook.addedToBookshelf)
                {
                    Audiobook.addedToBookshelf = false;

                }
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Audiobook.class);
                startActivity(i);
            }
        });
        return view;
    }


    private void initView()
    {

        linearLayout = view.findViewById(R.id.bookshelf_LL1_audio);
        title = view.findViewById(R.id.bookshelf_name1_audio);
        coverPage = view.findViewById(R.id.bookshelf_cover1_audio);
        delete = view.findViewById(R.id.bookshelf_delete1_audio);
        if(CurrentUser.getLoginState())
        {
            if(!myDb.getBookCoverName(CurrentUser.getCurrentUserName(),"Audio")[0].equals("-1"))
            {
                linearLayout.setVisibility(View.VISIBLE);

                title.setText(myDb.getBookCoverName(CurrentUser.getCurrentUserName(),"Audio")[0]);
                coverPage.setImageBitmap(myDb.getBookCover(CurrentUser.getCurrentUserName(),"Audio"));
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
