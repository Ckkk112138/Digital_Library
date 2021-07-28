package com.example.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ImageListAdapter extends ArrayAdapter<ImageListArray> {
    private int resourceId;
    private List<ImageListArray> imageListArrays;
    private ArrayList<ImageListArray> arrayList;
    public ImageListAdapter(@NonNull Context context, int resource, List<ImageListArray> objects) {
        super(context, resource,objects);
        resourceId = resource;
        imageListArrays = objects;
        this.arrayList = new ArrayList<ImageListArray>();
        this.arrayList.addAll(imageListArrays);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ImageListArray imageListArray = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView imageView = view.findViewById(R.id.image_list);
        TextView textView = view.findViewById(R.id.text_title_list);
        TextView textView2 = view.findViewById(R.id.text_author_list);
        imageView.setImageResource(imageListArray.getImageID());
        textView.setText(imageListArray.getName());
        textView2.setText(imageListArray.getAuthor());
        return view;
    }

    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        imageListArrays.clear();
        if(charText.length() == 0)
        {
            imageListArrays.addAll(arrayList);
        }
        else
        {
            for (ImageListArray array : arrayList)
            {
                if(array.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    imageListArrays.add(array);
                }
            }
        }
        notifyDataSetChanged();
    }
}
