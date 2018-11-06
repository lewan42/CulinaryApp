package com.example.lewan.myapplication.descriptionRecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lewan.myapplication.R;
import com.example.lewan.myapplication.menu.PageFragmentMenuGetUserRecipe;
import com.example.lewan.myapplication.menu.PageFragmentMenuSelectProductAndSearchRecipe;
import com.example.lewan.myapplication.menu.PageFragmentsMenu;

import java.util.ArrayList;

public class PageFragmentsDescriptionRecipe extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private ListView listView;
    private SearchView searchProducts;


    ArrayList<String> items = new ArrayList<>();

    String[] nameProduct = {
            "prod1", "prod2", "prod3", "prod4", "prod5", "prod6", "prod7", "prod8", "prod9",
            "prod10", "prod11", "prod12", "prod13", "prod14", "prod15",

    };


    public static PageFragmentsDescriptionRecipe newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragmentsDescriptionRecipe fragment = new PageFragmentsDescriptionRecipe();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        if (mPage == 1) {
            PageFragmentDescriptionRecipeProducts adapterListViewAndroid = new PageFragmentDescriptionRecipeProducts(getContext(), nameProduct);

            view = inflater.inflate(R.layout.fragment_description_recipe_products, container, false);

            listView = view.findViewById(R.id.listViewRecipeProducts);
            listView.setItemsCanFocus(true);
            listView.setAdapter(adapterListViewAndroid);


            //searchProducts = view.findViewById(R.id.searchProduct);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    Toast.makeText(getContext(), "List Item: " + nameProduct[+i] + " " + id, Toast.LENGTH_SHORT).show();
                    items.add(nameProduct[+i]);


                }
            });

            return view;
        }

        view = inflater.inflate(R.layout.fragment_page_3, container, false);
        TextView textView = (TextView) view;
        textView.setText("Fragment description recipe");
        return view;
    }
}