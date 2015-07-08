package com.sj.news35.news.category;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sj.news35.DetalActivity;
import com.sj.news35.R;
import com.sj.news35.adapter.NewsAdapter;
import com.sj.news35.base.BasePager;
import com.sj.news35.bean.NewsItemCategoryBean;
import com.sj.news35.utils.CommonUtil;
import com.sj.news35.utils.HMApi;
import com.sj.news35.utils.SPUtils;
import com.sj.news35.view.RollViewPager;
import com.sj.news35.view.pullrefreshview.PullToRefreshBase;
import com.sj.news35.view.pullrefreshview.PullToRefreshListView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示可滚动的viewpager+ListView的数据
 */
public class NewsItemCategory extends BasePager {

    private String url;
    //    private Object newsData;
    @ViewInject(R.id.top_news_title)
    private TextView top_news_title;
    @ViewInject(R.id.dots_ll)
    private LinearLayout dots_ll;
    private List<View> dotsLists;
    @ViewInject(R.id.top_news_viewpager)
    private LinearLayout top_news_viewpager;
    @ViewInject(R.id.lv_item_news)
    private PullToRefreshListView ptrlv;
    private View view_top;
    private NewsAdapter newsAdapter;
    private String moreUrl;

    public NewsItemCategory(Context ct, String url) {
        super(ct);
        this.url = url;
    }

    @Override
    public View initView() {
        //下拉刷新listview
        View view = View.inflate(context, R.layout.frag_item_news, null);
        //可滚动viewpager
        view_top = View.inflate(context, R.layout.layout_roll_view, null);
        ViewUtils.inject(this, view);
        ViewUtils.inject(this, view_top);

        //设置上拉加载不可用
        ptrlv.setPullLoadEnabled(false);
        //设置滚动加载可用
        ptrlv.setScrollLoadEnabled(true);
        ptrlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getNewsData(url, true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getNewsData(moreUrl, false);
            }
        });

        ptrlv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItemCategoryBean.News news = null;
                if (ptrlv.getRefreshableView().getHeaderViewsCount() > 0) {
                    news = NewsItemCategory.this.news.get(position - 1);
                } else {
                    news = NewsItemCategory.this.news.get(position);
                }
                if (!news.is_read) {
                    news.is_read = true;
                }
                newsAdapter.notifyDataSetChanged();

                Intent intent = new Intent(context, DetalActivity.class);
                intent.putExtra("url", news.url);
                context.startActivity(intent);

            }
        });
        ptrlv.setLastUpdatedLabel(CommonUtil.getStringDate());

        return view;
    }

    @Override
    public void initData() {
        String result = SPUtils.getString(context, HMApi.BASE_URL + url, "");
        if (!TextUtils.isEmpty(result)) {
            processData(result, true);
        }

        getNewsData(url, true);
    }

    public void getNewsData(final String url, final boolean isRefresh) {
        loadData(HttpRequest.HttpMethod.GET, HMApi.BASE_URL + url, null, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogUtils.d(responseInfo.result);
                processData(responseInfo.result, isRefresh);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                LogUtils.e(e.toString() + "...." + s);
            }
        });
    }

    private List<String> mTitleLists = new ArrayList<>();
    private List<String> mImageUrlLists = new ArrayList<>();
    private List<NewsItemCategoryBean.News> news = new ArrayList<>();

    private void processData(String result, boolean isRefresh) {
        Gson gson = new Gson();
        NewsItemCategoryBean newsItemCategoryBean = gson.fromJson(result, NewsItemCategoryBean.class);

        if (isRefresh) {
            if (newsItemCategoryBean.retcode == 200) {
                is_load = true;
                NewsItemCategoryBean.NewsItem data = newsItemCategoryBean.data;
                //自动滑动的viewpager的数据
                for (NewsItemCategoryBean.TopNews item : data.topnews) {
                    mTitleLists.add(item.title);
                    mImageUrlLists.add(item.topimage);
                }

                int size = data.topnews.size();
                initDot(size);

                RollViewPager rollViewPager = new RollViewPager(context, dotsLists);
                rollViewPager.setTitleLists(top_news_title, mTitleLists);
                rollViewPager.setImageUrlLists(mImageUrlLists);
                //让viewpager自己跳动
                rollViewPager.startRoll();
                //添加滑动Viewpager到线性布局
                top_news_viewpager.removeAllViews();
                top_news_viewpager.addView(rollViewPager);

                if (ptrlv.getRefreshableView().getHeaderViewsCount() < 1) {
                    ptrlv.getRefreshableView().addHeaderView(view_top);
                }

            }

            moreUrl = newsItemCategoryBean.data.more;
            if (isRefresh) {
                news.clear();
            }
            if (TextUtils.isEmpty(moreUrl)) {
                ptrlv.setHasMoreData(false);
            } else {
                ptrlv.setHasMoreData(true);
            }

            news.addAll(newsItemCategoryBean.data.news);

            if (newsAdapter == null) {
                newsAdapter = new NewsAdapter(context, news);
                ptrlv.getRefreshableView().setAdapter(newsAdapter);
            } else {
                newsAdapter.notifyDataSetChanged();
            }

            //设置加载完成的方法
            ptrlv.onPullUpRefreshComplete();
            ptrlv.onPullDownRefreshComplete();
        }

    }

    /**
     * 初始化 点
     *
     * @param size 个数
     */
    private void initDot(int size) {
        dotsLists = new ArrayList<>();
        dots_ll.removeAllViews();
        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CommonUtil.dip2px(context, 6.0f), CommonUtil.dip2px(context, 6.0f));
            layoutParams.setMargins(5, 0, 5, 0);
            View m = new View(context);
            m.setLayoutParams(layoutParams);
            if (i == 0) {
                m.setBackgroundResource(R.drawable.dot_focus);
            } else {
                m.setBackgroundResource(R.drawable.dot_normal);
            }
            dotsLists.add(m);
            dots_ll.addView(m);
        }
    }
}
