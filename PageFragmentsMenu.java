package com.example.lewan.myapplication.menu;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lewan.myapplication.descriptionRecipe.DescriptionRecipeActivity;
import com.example.lewan.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PageFragmentsMenu extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private GridView androidGridView;
    private ListView listView;
    private Button btn_check_items;
    private CheckBox checkBox_item;

    private SearchView searchProducts;

    private List<State> states = new ArrayList();

    private List<State> states2 = new ArrayList();

    String[] listRecipeName = {
            "1", "2", "3", "4", "5", "6", "7",
    };

    ArrayList<String> items = new ArrayList<>();
    ListView countriesList;

    String[] nameProduct = {
            "prod1", "prod2", "prod3", "prod4", "prod5", "prod6", "prod7", "prod8", "prod9",
            "prod10", "prod11", "prod12", "prod13", "prod14", "prod15",

    };
    int[] gridViewImageId = {
            R.drawable.pitsa, R.drawable.r2, R.drawable.r3, R.drawable.r4, R.drawable.r5, R.drawable.r6, R.drawable.r7,
    };

    public static PageFragmentsMenu newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragmentsMenu fragment = new PageFragmentsMenu();
        fragment.setArguments(args);

        System.out.println("newInstance " + page);
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

        System.err.println("PAGE ----");


        if (mPage == 1) {
            System.err.println("PAGE 1");

            view = inflater.inflate(R.layout.fragment_menu_profile, container, false);

            initGridView();
            androidGridView = view.findViewById(R.id.gridView);
            PageFragmentMenuGetUserRecipe adapterViewAndroid = new PageFragmentMenuGetUserRecipe(getContext(), 1, states2);

            androidGridView.setAdapter(adapterViewAndroid);

            AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    // получаем выбранный пункт
                    State selectedState = (State) parent.getItemAtPosition(position);
                    Toast.makeText(getContext(), "Был выбран пункт " + selectedState.getNameProduct() + " " + selectedState.getImgProduct(),
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), DescriptionRecipeActivity.class);
                    intent.putExtra("key", selectedState.getNameProduct());
                    intent.putExtra("idRecipe", selectedState.getImgProduct());
                    startActivity(intent);
                }
            };
            androidGridView.setOnItemClickListener(itemListener);
            System.err.println("PAGE 1++++");
            return view;


        } else if (mPage == 2) {

            System.err.println("PAGE 2");

            view = inflater.inflate(R.layout.fragment_menu_select_product_and_search_recipe, container, false);

            setInitialData();
            countriesList = view.findViewById(R.id.countriesList);
            final PageFragmentMenuSelectProductAndSearchRecipe stateAdapter = new PageFragmentMenuSelectProductAndSearchRecipe(getContext(), 1, states);

            countriesList.setAdapter(stateAdapter);

            AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    // получаем выбранный пункт
                    State selectedState = (State) parent.getItemAtPosition(position);
                    Toast.makeText(getContext(), "Был выбран пункт " + selectedState.getName() + " = " + selectedState.getFlag(),
                            Toast.LENGTH_SHORT).show();
                }
            };
            countriesList.setOnItemClickListener(itemListener);

            btn_check_items = view.findViewById(R.id.checkItems);
            btn_check_items.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = 0;
                    for (int i = 0; i < states.size(); i++) {
                        if (states.get(i).getFlag()) count++;
                    }
                    Toast.makeText(getContext(), "Количество " + count,
                            Toast.LENGTH_SHORT).show();

                }
            });
            System.err.println("PAGE 2++++");

            searchProducts = view.findViewById(R.id.searchProducts);

            searchProducts.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    System.out.println(newText + " ----");
                    stateAdapter.getFilter().filter(newText);
                    return true;
                }
            });
            //final PageFragmentMenuSelectProductAndSearchRecipe adapterListViewAndroid = new PageFragmentMenuSelectProductAndSearchRecipe(getContext(), nameProduct);

//
//            listView = view.findViewById(R.id.listViewSelectAndSearchRecipe);
//            listView.setItemsCanFocus(true);
//            listView.setAdapter(adapterListViewAndroid);
//
//            btn_check_items = view.findViewById(R.id.checkItems);
//
//            checkBox_item = view.findViewById(R.id.checkedProduct);
//
//            searchProducts = view.findViewById(R.id.searchProducts);

//            searchProducts.addTextChangedListener(new TextWatcher() {
//
//                @Override
//                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                    // When user changed the Text
//                    //MainActivity.this.adapter.getFilter().filter(cs);
//
//                    adapterListViewAndroid.getFilter().filter(cs);
//
//                }
//
//                @Override
//                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable arg0) {
//                }
//            });


//            btn_check_items.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    for (String s : items)
//                        System.out.println(s);
//                }
//            });
//
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//                    Toast.makeText(getContext(), "List Item: " + nameProduct[+i] + " " + id, Toast.LENGTH_SHORT).show();
//                    items.add(nameProduct[+i]);
//
//
//                }
//            });
            return view;

//            final PageFragmentMenuSelectProductAndSearchRecipe adapterListViewAndroid = new PageFragmentMenuSelectProductAndSearchRecipe(getContext(), nameProduct);
//
//            view = inflater.inflate(R.layout.fragment_menu_select_product_and_search_recipe, container, false);
//
//            listView = view.findViewById(R.id.listViewSelectAndSearchRecipe);
//            listView.setItemsCanFocus(true);
//            listView.setAdapter(adapterListViewAndroid);
//
//            btn_check_items = view.findViewById(R.id.checkItems);
//
//            checkBox_item = view.findViewById(R.id.checkedProduct);
//
//            searchProducts = view.findViewById(R.id.searchProducts);
//
//            searchProducts.addTextChangedListener(new TextWatcher() {
//
//                @Override
//                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                    // When user changed the Text
//                    //MainActivity.this.adapter.getFilter().filter(cs);
//
//                    adapterListViewAndroid.getFilter().filter(cs);
//
//                }
//
//                @Override
//                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable arg0) {
//                }
//            });
//
////            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////                @Override
////                public boolean onQueryTextSubmit(String query) {
////                    listView.setFilterText(query);
////                    return false;
////                }
////
////                @Override
////                public boolean onQueryTextChange(String newText) {
////                    listView.setFilterText(newText);
////                    System.out.println(newText+ " ---------------");
////                    return false;
////                }
////            });
//
//
//            btn_check_items.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    for (String s : items)
//                        System.out.println(s);
//                }
//            });
//
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//                    Toast.makeText(getContext(), "List Item: " + nameProduct[+i] + " " + id, Toast.LENGTH_SHORT).show();
//                    items.add(nameProduct[+i]);
//
//
//                }
//            });
//
//
//            return view;
        } else if (mPage == 3) {

            System.err.println("PAGE 3");

            view = inflater.inflate(R.layout.fragment_page_3, container, false);
            TextView textView = (TextView) view;
            textView.setText("Fragment test #" + mPage);

            System.err.println("PAGE 3++++");
            return view;
        }

        System.err.println("PAGE NULL");
        return null;
    }

    private void setInitialData() {

        states.add(new State("Продукт1"));
        states.add(new State("Продукт2"));
        states.add(new State("Продукт3"));
        states.add(new State("Продукт4"));
        states.add(new State("Продукт5"));
        states.add(new State("Продукт6"));
        states.add(new State("Продукт7"));
        states.add(new State("Продукт8"));
        states.add(new State("Продукт9"));
        states.add(new State("Продукт10"));
        states.add(new State("Продукт11"));
        states.add(new State("Продукт12"));
        states.add(new State("Продукт13"));
        states.add(new State("q"));
        states.add(new State("qw"));
    }

    private void initGridView() {

        states2.add(new State("1", R.drawable.pitsa));
        states2.add(new State("2", R.drawable.r2));
        states2.add(new State("3", R.drawable.r3));
        states2.add(new State("4", R.drawable.r4));
        states2.add(new State("5", R.drawable.r5));
        states2.add(new State("6", R.drawable.r6));
        states2.add(new State("7", R.drawable.r7));

    }
}
