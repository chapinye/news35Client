package com.sj.news35.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sj.news35.base.BasePager;

/**
 * Created by sj on 2015/6/28 0028.
 */
public class FunctionPager extends BasePager {

    public FunctionPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("首页");
        return textView;
    }


    @Override
    public void initData() {

    }
}
