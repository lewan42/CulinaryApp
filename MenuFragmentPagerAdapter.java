package com.example.lewan.myapplication.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.lewan.myapplication.R;

public class MenuFragmentPagerAdapter extends FragmentPagerAdapter {
    private int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"1", "2", "3"};
    private Context context;

    MenuFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        //System.err.println("return getCount "+PAGE_COUNT);
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        System.err.println("position " + position);
        return PageFragmentsMenu.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int[] imageResId = {
                R.drawable.profile, R.drawable.search_recipe, R.drawable.create
        };
        //System.err.println("getPageTitle "+ position);
        // генерируем название в зависимости от позиции
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // заменяем пробел иконкой
        SpannableString sb = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
