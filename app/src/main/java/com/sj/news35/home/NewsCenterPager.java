package com.sj.news35.home;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sj.news35.MainActivity;
import com.sj.news35.R;
import com.sj.news35.base.BasePager;
import com.sj.news35.bean.NewCenter;
import com.sj.news35.news.NewsPager;
import com.sj.news35.news.PicPager;
import com.sj.news35.news.TopicPager;
import com.sj.news35.utils.HMApi;
import com.sj.news35.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻中心
 * Created by sj on 2015/6/28 0028.
 */
public class NewsCenterPager extends BasePager {
    private static final String TAG = "NewsCenterPager";
    private NewCenter newCenter;


    @ViewInject(R.id.title_bar)
    LinearLayout title_bar;

    @ViewInject(R.id.news_center_fl)
    FrameLayout news_center_fl;

    private List<BasePager> pages = new ArrayList<BasePager>();


    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
//        TextView textView = new TextView(context);
//        textView.setText("新闻中心");
        view = View.inflate(context, R.layout.news_center_frame, null);
        ViewUtils.inject(this, view);
        initTitleBar(view);
        return view;
    }

    /**
     * 如何正确显示ListVIew
     * 1.首先必须从缓存中读数据
     * 2.如果本地缓存中有数据，先把缓存中的数据加载出来
     * 3.如果有网络，从网络获取数据，并缓存
     * 4.所有数据加载完之后，判断当前数据是否阅读过
     * 5.假设是新闻的话，就需要在新闻的实体类中添加字段is_read
     * 6.在adapter的getView中判断当前新闻，为true设置一种颜色，否则设置另一种颜色
     */
    @Override
    public void initData() {
        String result = SPUtils.getString(context, HMApi.NEWS_CENTER_CATEGORIES, "");
        if (!TextUtils.isEmpty(result)) {
            Log.i(TAG, "正在读取SP中的缓存数据");
            processData(result);
        }
        getNewsCenterData();
    }

    public void switchFragment(int position) {
        switch (position) {
            case 0:
                txt_title.setText("新闻");
                break;
            case 1:
                txt_title.setText("专题");
                break;
            case 2:
                txt_title.setText("组图");
                break;
            case 3:
                txt_title.setText("互动");
                break;

        }
        BasePager basePager = pages.get(position);
        news_center_fl.removeAllViews();
        news_center_fl.addView(basePager.getRootView());
        basePager.initData();
    }

    /**
     * 初始化新闻中心的数据
     * <p/>
     * 缓存数据的方式：
     * 1.数据库
     * 2.sp
     * 3.文件缓存
     * 4.内存缓存
     * 5.
     */
    private void getNewsCenterData() {
        loadData(HttpRequest.HttpMethod.GET, HMApi.NEWS_CENTER_CATEGORIES, null, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                System.out.print(responseInfo.result);
                processData(responseInfo.result);
                SPUtils.saveString(context, HMApi.NEWS_CENTER_CATEGORIES, responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG, "获取新闻中心目录信息失败：" + s);
            }
        });
    }

    private List<String> menuLists = new ArrayList<String>();

    private void processData(String result) {
        Gson gson = new Gson();
        newCenter = gson.fromJson(result, NewCenter.class);
        //如果code==200 说明服务器交互成功
        if (newCenter.retcode == 200) {
            is_load = true;
            menuLists.clear();
            for (NewCenter.NewsCenter menu : newCenter.data) {
                menuLists.add(menu.title);
            }
//            MenuFragment2 menuFragment2 = new MenuFragment2();
//            menuFragment2.initMenu(menuLists);
            ((MainActivity) context).getMenuFragment2().initMenu(menuLists);

            pages.clear();
            pages.add(new NewsPager(context, newCenter.data.get(0)));
            pages.add(new TopicPager(context));
            pages.add(new PicPager(context));
            pages.add(new NewsPager(context));
            switchFragment(0);
        }
    }


}
