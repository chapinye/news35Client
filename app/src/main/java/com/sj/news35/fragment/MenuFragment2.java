package com.sj.news35.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sj.news35.MainActivity;
import com.sj.news35.R;
import com.sj.news35.base.BaseFragment;
import com.sj.news35.base.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment2 extends BaseFragment {

    @ViewInject(R.id.lv_menu_news_center)
    private ListView lv_menu_news_center;
    private MenuAdapter menuAdapter;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.layout_left_menu, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
        lv_menu_news_center.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setSelected(position);
                ((MainActivity) context).getHomeFragment().getNewsCenterPager().switchFragment(position);
                slidingMenu.toggle();
            }
        });
    }

    private List<String> lists = new ArrayList<String>();

    public void initMenu(List<String> menuLists) {
        lists.clear();
        lists.addAll(menuLists);
        Toast.makeText(context, "size:" + lists.size(), Toast.LENGTH_SHORT).show();
        menuAdapter = new MenuAdapter(context, lists);
        lv_menu_news_center.setAdapter(menuAdapter);
    }

    public class MenuAdapter extends MyBaseAdapter<String, ListView> {

        private int selected = 0;

        public MenuAdapter(Context context, List<String> lists) {
            super(context, lists);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.layout_item_menu, null);
            }
            ImageView iv_menu_item = (ImageView) convertView.findViewById(R.id.iv_menu_item);
            TextView tv_menu_item = (TextView) convertView.findViewById(R.id.tv_menu_item);
            String menu = mLists.get(position);
            tv_menu_item.setText(menu);
            /**
             * 目的：当选中item时变颜色
             * 1.如果选中的item==点击的item时 变色
             * 2.如果选中的item！=点击的item时 默认白色
             */
            if (position == selected) {
                tv_menu_item.setTextColor(Color.RED);
                iv_menu_item.setBackgroundResource(R.drawable.menu_arr_select);
            } else {
                tv_menu_item.setTextColor(Color.WHITE);
                iv_menu_item.setBackgroundResource(R.drawable.menu_arr_normal);
            }
            return convertView;
        }

        public void setSelected(int selected) {
            this.selected = selected;
            notifyDataSetChanged();
            //两种写法都可以
//            notifyDataSetInvalidated();
        }
    }

}
