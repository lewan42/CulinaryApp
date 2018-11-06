package com.example.lewan.myapplication.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.lewan.myapplication.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PageFragmentMenuSelectProductAndSearchRecipe extends ArrayAdapter<State> implements Filterable {

    private LayoutInflater inflater;
    private int layout;
    private List<State> states;
    private Context mContext;


    //    private Context mContext;
//    private String[] nameProduct;
    private List<String> listFilterProducts;

//    public PageFragmentMenuSelectProductAndSearchRecipe(Context context, String[] gridViewString) {
//        mContext = context;
//        this.nameProduct = gridViewString;
//    }

    public PageFragmentMenuSelectProductAndSearchRecipe(Context context, int resource, List<State> states) {
        super(context, resource, states);
        this.states = states;
        mContext = context;
        this.inflater = LayoutInflater.from(context);
    }


    /*
    Данный фильтр требует доработки!
    Фильтр фильтрует (performFiltering), но в publishResults удаляется весь список и добавляются элементы входящие в фильтр.
    Постоянно уменьшается список (states)
     */
    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
//                System.out.println("11111");
//                listFilterProducts = (List<String>) results.values;
//
//                System.out.println("publishResult " + results.values);
//                notifyDataSetChanged();

                clear();
                for (int i = 0; i < states.size(); i++) {
                    String dataNames = states.get(i).getName();
                }


                for (String item : (List<String>) results.values) {
                    add(new State(item));
                    System.err.println("item " + item);
                }
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<String> FilteredArrayNames = new ArrayList<String>();

                constraint = constraint.toString().toLowerCase();

                if (!constraint.equals("")) {
                    System.err.println("Filter");
                    for (int i = 0; i < states.size(); i++) {
                        String dataNames = states.get(i).getName();
                        //System.err.println("i " + i + "\nname " + dataNames + " ? search " + constraint);
                        if (dataNames.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrayNames.add(dataNames);
                        } else {

                        }
                    }
                } else {
                    System.err.println("Empty");
                    for (int i = 0; i < states.size(); i++) {
                        String dataNames = states.get(i).getName();
                        FilteredArrayNames.add(dataNames);
                    }
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                Log.e("VALUES", results.values.toString() + " / " + states.size());

                // System.out.println("result " + results);
                return results;
            }
        };

        return filter;
    }


    private static class ViewHolder {
        private TextView nameProduct;
        //private TextView stolitsa;
        //private ImageView flagResource; // ресурс флага
        private CheckBox flag;//
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        // System.err.println("start " + position);
        View view;
        //LayoutInflater inflater =  mContext.getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.fragment_menu_select_product_and_search_recipe_row, null);


//            ImageView flagView = view.findViewById(R.id.flag);
//            TextView nameView = view.findViewById(R.id.name);
//            TextView capitalView = view.findViewById(R.id.capital);
//            //CheckBox checkBox = view.findViewById(R.id.checkbox);//
//
//            State state = states.get(position);
//
////            checkBox.setChecked(state.getFlag()); //
////            checkBox.setId(position);
//
//            flagView.setImageResource(state.getFlagResource());
//            nameView.setText(state.getName());
//            capitalView.setText(state.getCapital());

            //---------------------

            final PageFragmentMenuSelectProductAndSearchRecipe.ViewHolder viewHolder = new PageFragmentMenuSelectProductAndSearchRecipe.ViewHolder();
            viewHolder.nameProduct = (TextView) view.findViewById(R.id.nameProduct);
            //viewHolder.stolitsa = (TextView) view.findViewById(R.id.capital);
            viewHolder.flag = (CheckBox) view.findViewById(R.id.nameProductCheckbox);
            //viewHolder.flagResource = (ImageView) view.findViewById(R.id.flag);

            viewHolder.flag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    State element = (State) viewHolder.flag.getTag();
                    element.setFlag(buttonView.isChecked());
                }
            });

            view.setTag(viewHolder);
            viewHolder.flag.setTag(states.get(position));

            //---------------------

        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).flag.setTag(states.get(position));
        }

        PageFragmentMenuSelectProductAndSearchRecipe.ViewHolder holder = (PageFragmentMenuSelectProductAndSearchRecipe.ViewHolder) view.getTag();
        holder.nameProduct.setText(states.get(position).getName());
        //holder.stolitsa.setText(states.get(position).getCapital());
        holder.flag.setChecked(states.get(position).getFlag());
        //holder.flagResource.setImageResource(states.get(position).getFlagResource());

        // System.err.println("end   " + position);
        return view;
    }

//    @Override
//    public View getView(int i, View convertView, ViewGroup parent) {
//        View listViewRow;
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        if (convertView == null) {
//
//            //gridViewAndroid = new View(mContext);
//            listViewRow = inflater.inflate(R.layout.fragment_menu_select_product_and_search_recipe_row, null);
//            TextView textViewAndroid = (TextView) listViewRow.findViewById(R.id.textProduct);
//            textViewAndroid.setText(nameProduct[i]);
//
//            CheckBox checkBox = (CheckBox) listViewRow.findViewById(R.id.checkedProduct);
//            checkBox.setChecked(false);
//            checkBox.setId(++i);
//
//            checkBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    //boolean isChecked = checkBox.isChecked();
//                    // Do something here.
//
//                    System.out.println(arg0.getId() + " ////");
//                }
//            });
//
//            //CheckBox checkBox = (CheckBox) listViewRow.findViewById(R.id.rowCheckBox);
//            // checkBox.setChecked(false);
////
////            checkBox.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    //Toast.makeText(v, "GridView Item: " + gridViewString[0], Toast.LENGTH_LONG).show();
////                    System.out.println(v.getId());
////                }
////            });
//            // ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
//
//            //imageViewAndroid.setImageResource(gridViewImageId[i]);
//
//        } else {
//            listViewRow = (View) convertView;
//        }
//        return listViewRow;
//    }
}

// class MyListener implements CheckBox.OnCheckedChangeListener {
//    @Override
//    public void onCheckedChanged(CompoundButton view, boolean isChecked) {
//        switch (view.getId()) {
//                case ...
//        }
//    }
//}

class State {

    private String name; // название
    private String capital;  // столица
    private int flagResource; // ресурс флага
    private boolean flag;//

    private String nameProduct;
    private int imgProduct;

    public State(String name) {

        this.name = name;
        this.flag = false;
    }

    public State(String nameProduct, int imgProduct) {
        this.nameProduct = nameProduct;
        this.imgProduct = imgProduct;
    }

    public String getNameProduct() {
        return this.nameProduct;
    }

    public void setNameProduct(String name) {
        this.nameProduct = name;
    }

    public int getImgProduct() {
        return this.imgProduct;
    }

    public void setImgProduct(int img) {
        this.imgProduct = img;
    }
//----------------------------


    public void setFlag(boolean f) {
        this.flag = f;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getCapital() {
//        return this.capital;
//    }
//
//    public void setCapital(String capital) {
//        this.capital = capital;
//    }
//
//    public int getFlagResource() {
//        return this.flagResource;
//    }
//
//    public void setFlagResource(int flagResource) {
//        this.flagResource = flagResource;
//    }
}