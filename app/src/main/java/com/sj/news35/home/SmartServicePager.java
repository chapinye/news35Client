package com.sj.news35.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sj.news35.base.BasePager;

/**
 * Created by sj on 2015/6/28 0028.
 */
public class SmartServicePager extends BasePager {
    public SmartServicePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("智慧服务");
        return textView;
    }

    @Override
    public void initData() {

    }
}
