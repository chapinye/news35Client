package com.sj.news35.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sj.news35.R;
import com.sj.news35.base.BaseFragment;
import com.sj.news35.base.BasePager;
import com.sj.news35.home.FunctionPager;
import com.sj.news35.home.GovAffairsPager;
import com.sj.news35.home.NewsCenterPager;
import com.sj.news35.home.SettingPager;
import com.sj.news35.home.SmartServicePager;
import com.sj.news35.view.LazyViewPager;
import com.sj.news35.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页的fragment
 * <p/>
 * 为什么不使用fragment+fragment？
 * 1.必须使用系统V4包的fragment,系统会自动在我们的所有有fragment的界面嵌套一层层级结构
 * Created by sj on 2015/6/28 0028.
 */
public class HomeFragment extends BaseFragment {
    @ViewInject(R.id.main_radio)
    private RadioGroup main_radio;
    @ViewInject(R.id.view_pager)
    private MyViewPager view_pager;

    List<BasePager> lists = new ArrayList<BasePager>();
    private ViewpagerAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.frag_home, null);
        ViewUtils.inject(this, view);
        return view;
    }

    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) lists.get(1);
    }

    @Override
    protected void initData() {
        lists.clear();
        //定义好底部tab
        lists.add(new FunctionPager(context));
        lists.add(new NewsCenterPager(context));
        lists.add(new SmartServicePager(context));
        lists.add(new GovAffairsPager(context));
        lists.add(new SettingPager(context));

        if (adapter == null) {
            adapter = new ViewpagerAdapter(lists);
            view_pager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        main_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                {
                    switch (checkedId) {
                        case R.id.rb_function:
                            view_pager.setCurrentItem(0, false);
                            //设置不允许滑动
                            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                            break;
                        case R.id.rb_news_center:
                            view_pager.setCurrentItem(1, false);
                            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                            NewsCenterPager newsCenterPager = new NewsCenterPager(context);
                            newsCenterPager.initData();
                            break;
                        case R.id.rb_smart_service:
                            view_pager.setCurrentItem(2, false);
                            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                            break;
                        case R.id.rb_gov_affairs:
                            view_pager.setCurrentItem(3, false);
                            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                            break;
                        case R.id.rb_setting:
                            view_pager.setCurrentItem(4, false);
                            //设置不允许滑动
                            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                            break;
                    }
                }
            }
        });

        //设置单选按钮组默认的首页
        main_radio.check(R.id.rb_function);
        view_pager.addOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                BasePager pager = lists.get(position);
                if (!is_load) {
                    is_load = true;
                    pager.initData();
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private class ViewpagerAdapter extends PagerAdapter {

        private List<BasePager> lists;

        public ViewpagerAdapter(List<BasePager> lists) {
            this.lists = lists;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((MyViewPager) container).addView(lists.get(position).getRootView(), 0);
            return lists.get(position).getRootView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((MyViewPager) container).removeView(lists.get(position).getRootView());

        }
    }
}
