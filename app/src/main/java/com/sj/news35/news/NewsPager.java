package com.sj.news35.news;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sj.news35.R;
import com.sj.news35.base.BasePager;
import com.sj.news35.bean.NewCenter;
import com.sj.news35.news.category.NewsItemCategory;
import com.sj.news35.view.pagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * News35  新闻中心--新闻页
 * <p/>
 * 新闻页---titlebar
 * ---tab
 * ---ViewPager 装载NewsItemCategoty
 * Code By sj At 2015/7/2 0002 16:07
 */
public class NewsPager extends BasePager {

    @ViewInject(R.id.indicator)
    TabPageIndicator indicator;

    @ViewInject(R.id.pager)
    ViewPager view_pager;

    private NewCenter.NewsCenter newsCenter;

    public NewsPager(Context context) {
        super(context);
    }

    public NewsPager(Context context, NewCenter.NewsCenter newsCenter) {
        super(context);
        this.newsCenter = newsCenter;
    }

    @Override
    public View initView() {
        //        TextView tv=new TextView(context);
        //        tv.setText("新闻");
        View view = View.inflate(context, R.layout.frag_news, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        lists.clear();
        final List<NewCenter.ChildrenItem> children = newsCenter.getChildren();
        for (NewCenter.ChildrenItem child : children) {
            lists.add(new NewsItemCategory(context, child.url));
        }


        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(lists);
        view_pager.setAdapter(pagerAdapter);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                NewsItemCategory newsItemCategory = lists.get(pos);
                if (!is_load) {
                    newsItemCategory.initData();
                }
                //如果当新闻的分类数据到第一条的时候才允许出现滑动菜单 其他位置不出现
                if (pos == 0) {
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                } else {
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
                NewsPager.this.position = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        indicator.setViewPager(view_pager);
        indicator.setCurrentItem(position);
        lists.get(0).initData();
        is_load = true;
    }

    private int position;
    private List<NewsItemCategory> lists = new ArrayList<NewsItemCategory>();

    private class ViewPagerAdapter extends PagerAdapter {

        private List<NewsItemCategory> lists;

        public ViewPagerAdapter(List<NewsItemCategory> lists) {
            this.lists = lists;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(lists.get(position).getRootView());
            return lists.get(position).getRootView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(lists.get(position).getRootView());
        }

        /**
         * 如果想实现pagerTitle上面的数据，必须实现这个方法
         *
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return newsCenter.getChildren().get(position).title;
        }
    }
}
