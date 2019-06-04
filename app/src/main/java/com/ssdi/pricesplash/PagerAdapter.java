package com.ssdi.pricesplash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    String product_name;

    public PagerAdapter(FragmentManager fm, int NumOfTabs,String product_name) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.product_name = product_name;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragment1 tab1 = new TabFragment1();
                Bundle bundle = new Bundle();
                bundle.putInt("position",0);
                bundle.putString("product_name",product_name);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                TabFragment1 tab2 = new TabFragment1();
                bundle = new Bundle();

                bundle.putInt("position",1);
                bundle.putString("product_name",product_name);
                tab2.setArguments(bundle);
                return tab2;
            case 2:
                TabFragment1 tab3 = new TabFragment1();
                bundle = new Bundle();

                bundle.putInt("position",2);
                bundle.putString("product_name",product_name);
                tab3.setArguments(bundle);
                return tab3;
            case 3:
                TabFragment1 tab4 = new TabFragment1();
                bundle = new Bundle();

                bundle.putInt("position",3);
                bundle.putString("product_name",product_name);
                tab4.setArguments(bundle);
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}