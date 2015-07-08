package com.sj.news35.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sj.news35.base.BasePager;

/**
 * Created by sj on 2015/6/28 0028.
 */
public class GovAffairsPager extends BasePager {
    public GovAffairsPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("政务");
        return textView;
    }

    @Override
    public void initData() {

    }
}
