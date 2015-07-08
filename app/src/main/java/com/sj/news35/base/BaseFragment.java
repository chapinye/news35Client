package com.sj.news35.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sj.news35.MainActivity;

/**
 * Fragment的基类
 * 1.初始化View
 * 2.初始化数据
 * 3.获取Context 、根布局 和MainActivity中的SlidingMenu
 * Created by sj on 2015/6/28 0028.
 */
public abstract class BaseFragment extends Fragment {

    protected View view;
    protected Context context;
    /**
     * 是否加载数据
     */
    protected boolean is_load;
    protected SlidingMenu slidingMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        slidingMenu = ((MainActivity) context).getSlidingMenu();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = initView(inflater);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 获取根布局
     *
     * @return
     */
    protected View getRootView() {
        return view;
    }

    /**
     * 初始化布局
     *
     * @param inflater inflater
     * @return
     */
    public abstract View initView(LayoutInflater inflater);

    /**
     * 初始化数据
     */
    protected abstract void initData();
}
