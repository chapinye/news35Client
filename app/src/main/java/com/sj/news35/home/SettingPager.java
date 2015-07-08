package com.sj.news35.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sj.news35.base.BasePager;

/**
 * Created by sj on 2015/6/28 0028.
 */
public class SettingPager extends BasePager {
    public SettingPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("设置");
        return textView;
    }

    @Override
    public void initData() {

    }
}
