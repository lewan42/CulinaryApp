package com.example.lewan.myapplication.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lewan.myapplication.R;

import java.util.List;

public class PageFragmentMenuGetUserRecipe extends ArrayAdapter<State> {

    private LayoutInflater inflater;
    private int layout;
    private List<State> states;
    private Context mContext;

    public PageFragmentMenuGetUserRecipe(Context context, int resource, List<State> states) {
        super(context, resource, states);
        this.states = states;
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        private TextView textRecipe;
        private ImageView imgRecipe;

    }

    @Override
//    public View getView(int i, View convertView, ViewGroup parent) {
//        View gridViewAndroid;
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        System.err.println("i " + i);
//        if (convertView == null) {
//
//            //gridViewAndroid = new View(mContext);
//
//
//            gridViewAndroid = inflater.inflate(R.layout.fragment_menu_profile_item, null);
//            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.fragmentMenu_fragmentUser_textRecipe);
//            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.fragmentMenu_fragmentUser_imageRecipe);
//            textViewAndroid.setText(gridViewNameRecipe[i]);
//            imageViewAndroid.setImageResource(gridViewImageRecipe[i]);
//        } else {
//            gridViewAndroid = (View) convertView;
//        }
//
//        return gridViewAndroid;
//    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.fragment_menu_profile_item, null);

            PageFragmentMenuGetUserRecipe.ViewHolder viewHolder = new PageFragmentMenuGetUserRecipe.ViewHolder();
            viewHolder.textRecipe = (TextView) view.findViewById(R.id.fragmentMenu_fragmentUser_textRecipe);
            viewHolder.imgRecipe = (ImageView) view.findViewById(R.id.fragmentMenu_fragmentUser_imageRecipe);
            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        PageFragmentMenuGetUserRecipe.ViewHolder holder = (PageFragmentMenuGetUserRecipe.ViewHolder) view.getTag();
        holder.textRecipe.setText(states.get(position).getNameProduct());
        holder.imgRecipe.setImageResource(states.get(position).getImgProduct());

        return view;
    }
}

