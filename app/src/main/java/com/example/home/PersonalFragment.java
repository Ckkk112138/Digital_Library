package com.example.home;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class PersonalFragment extends Fragment implements View.OnClickListener {
    View view;
    private TextView username,cardNumber;
    private LinearLayout logout,collection,readerCard,myBooks;
    private ImageView avatar;
    DatabaseHelper myDb;
    private Bitmap bitmapIn = null,bitmapOut = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.personal_fragment, container, false);
        myDb = new DatabaseHelper(getActivity());

        initView();
        logout.setOnClickListener(this);
        collection.setOnClickListener(this);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentUser.getLoginState())
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    startActivityForResult(intent, 1);
                }
                else
                {
                    Toast.makeText(getActivity(),"Please login first",Toast.LENGTH_SHORT).show();
                }



            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            if(data != null)
            {
                Uri uri = data.getData();
                ContentResolver contentResolver = getActivity().getContentResolver();
                try {
                    bitmapIn = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                    avatar.setImageBitmap(bitmapIn);
                    myDb.setAvatar(CurrentUser.getCurrentUserName(),bitmapIn);
                    Toast.makeText(getActivity(),"store successful",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            super.onActivityResult(requestCode,resultCode,data);

        }
    }

    void initView()
    {
        readerCard = view.findViewById(R.id.reader_card_entry);
        readerCard.setOnClickListener(this);
        username = view.findViewById(R.id.username_personal);
        cardNumber = view.findViewById(R.id.cardnumber_personal);
        logout = view.findViewById(R.id.personal_logout);
        collection = view.findViewById(R.id.personal_collection);
        avatar = view.findViewById(R.id.avatar_personal);
        myBooks = view.findViewById(R.id.personal_borrow);
        myBooks.setOnClickListener(this);
        if(CurrentUser.getLoginState())
        {
            if(myDb.getAvatar(CurrentUser.getCurrentUserName()) != null)
                avatar.setImageBitmap(myDb.getAvatar(CurrentUser.getCurrentUserName()));
            if(myDb.getCardNumber(CurrentUser.getCurrentUserName()) != null)
                cardNumber.setText(myDb.getCardNumber(CurrentUser.getCurrentUserName()));
        }
        else
        {
            avatar.setImageResource(R.drawable.person1);
        }

        if(CurrentUser.getLoginState())
        {
            username.setText(CurrentUser.getCurrentUserName());
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(CurrentUser.getLoginState())
        {
            username.setText(CurrentUser.getCurrentUserName());
            if(myDb.getAvatar(CurrentUser.getCurrentUserName()) != null)
                avatar.setImageBitmap(myDb.getAvatar(CurrentUser.getCurrentUserName()));
        }
        else
        {
            username.setText("username");
            avatar.setImageResource(R.drawable.person1);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.personal_logout:
                if(CurrentUser.getCurrentUserName() != null)
                {
                    Toast.makeText(getActivity(),"Good Bye!" + CurrentUser.getCurrentUserName(),Toast.LENGTH_LONG).show();
                    CurrentUser.setCurrentUserName(null);
                    CurrentUser.setLoginState(false);
                    if(Book.addedToBookshelf)
                    {
                        Book.addedToBookshelf = false;

                    }
                    if (Audiobook.addedToBookshelf)
                    {
                        Audiobook.addedToBookshelf = false;
                    }
                    Intent i = new Intent(getActivity(),MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getActivity(),"Please log in first before logging out",Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.reader_card_entry:
                if(CurrentUser.getLoginState())
                {
                    if(cardNumber.getText().toString().length() == 0)
                    {
                        Intent intent = new Intent(getActivity(),ReaderCard.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"You've already bound a reader card",Toast.LENGTH_LONG).show();

                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Please log in first",Toast.LENGTH_LONG).show();

                }
            case R.id.personal_borrow:
                if(!CurrentUser.getLoginState())
                {
                    Toast.makeText(getActivity(),"Please login first",Toast.LENGTH_LONG).show();

                }
                else
                {
                    if(cardNumber.getText().toString().length() != 0)
                    {

                        Intent intent = new Intent(getActivity(),MyBooks.class);
                        intent.putExtra("Card_Number",cardNumber.getText().toString());
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please bind the reader card to check your books",Toast.LENGTH_LONG).show();

                    }
                }

        }

    }
}
