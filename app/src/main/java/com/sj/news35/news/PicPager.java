package com.sj.news35.news;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sj.news35.base.BasePager;

/**
 * News35
 * Code By sj At 2015/7/2 0002 16:09
 */
public class PicPager extends BasePager {
    public PicPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(context);
        tv.setText("图片");
        return tv;
    }

    @Override
    public void initData() {

    }
}
