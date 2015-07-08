package com.sj.news35.news;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sj.news35.base.BasePager;

/**
 * News35
 * Code By sj At 2015/7/2 0002 16:08
 */
public class TopicPager extends BasePager {
    public TopicPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(context);
        tv.setText("话题");
        return tv;
    }

    @Override
    public void initData() {

    }
}
