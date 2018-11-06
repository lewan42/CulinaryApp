package com.example.lewan.myapplication.descriptionRecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lewan.myapplication.R;

public class PageFragmentDescriptionRecipeProducts extends BaseAdapter {

    private Context mContext;
    private final String[] listProduct;
   // private final int[] gridViewImageId;

    public PageFragmentDescriptionRecipeProducts(Context context, String[] listProduct) {
        mContext = context;
        this.listProduct = listProduct;
        //this.gridViewString = gridViewString;
    }

    @Override
    public int getCount() {
        return listProduct.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View listViewRow;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            //gridViewAndroid = new View(mContext);
            listViewRow = inflater.inflate(R.layout.fragment_menu_select_product_and_search_recipe_row2, null);
            TextView textViewAndroid = (TextView) listViewRow.findViewById(R.id.textProduct);
            textViewAndroid.setText(listProduct[i]);

            CheckBox checkBox = (CheckBox) listViewRow.findViewById(R.id.checkedProduct);
            checkBox.setChecked(false);
            checkBox.setId(++i);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    System.out.println(arg0.getId() + " ////");
                }
            });


        } else {
            listViewRow = (View) convertView;
        }

        return listViewRow;
    }


}
