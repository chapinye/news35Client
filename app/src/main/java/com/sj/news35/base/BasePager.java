package com.sj.news35.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sj.news35.MainActivity;
import com.sj.news35.R;
import com.sj.news35.fragment.MenuFragment2;

/**
 * Created by sj on 2015/6/28 0028.
 */
public abstract class BasePager {

    protected View view;
    protected boolean is_load;

    protected Context context;
    protected TextView txt_title;

    protected SlidingMenu slidingMenu;

    public BasePager(Context context) {
        this.context = context;
        view = initView();
        slidingMenu = ((MainActivity) context).getSlidingMenu();

    }

    protected void initTitleBar(View view) {
        Button btn_left = (Button) view.findViewById(R.id.btn_left);
        btn_left.setVisibility(View.GONE);
        ImageButton imgbtn_left = (ImageButton) view.findViewById(R.id.imgbtn_left);
        imgbtn_left.setImageResource(R.drawable.img_menu);
        imgbtn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        txt_title = (TextView) view.findViewById(R.id.txt_title);

        ImageButton btn_right = (ImageButton) view.findViewById(R.id.btn_right);
        btn_right.setVisibility(View.GONE);

    }

    public void loadData(HttpRequest.HttpMethod method, String url, RequestParams params, RequestCallBack<String> callBack) {
        HttpUtils utils = new HttpUtils();
        if (params == null) {
            params = new RequestParams();
        }

        utils.send(method, url, params, callBack);
    }

    public abstract View initView();

    public View getRootView() {
        return view;
    }

    public abstract void initData();
}
