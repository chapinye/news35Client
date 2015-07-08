package com.sj.news35.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sj.news35.R;
import com.sj.news35.base.MyBaseAdapter;
import com.sj.news35.bean.NewsItemCategoryBean;

import java.util.List;

/**
 * 新闻适配器
 * Created by sj on 2015/7/3 0003.
 */
public class NewsAdapter extends MyBaseAdapter<NewsItemCategoryBean.News, ListView> {

    private final BitmapUtils bitmapUtils;

    public NewsAdapter(Context context, List<NewsItemCategoryBean.News> lists) {
        super(context, lists);
        bitmapUtils = new BitmapUtils(context);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_4444);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_news_item, null);
        }

        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        tv_title.setText(mLists.get(position).title);

        if (mLists.get(position).is_read) {
            tv_title.setTextColor(mContext.getResources().getColor(R.color.news_item_has_read_textcolor));
        } else {
            tv_title.setTextColor(mContext.getResources().getColor(R.color.news_item_no_read_textcolor));

        }

        TextView tv_pub_date = (TextView) convertView.findViewById(R.id.tv_pub_date);
        tv_pub_date.setText(mLists.get(position).pubdate);
//        TextView tv_comment_count = (TextView) convertView.findViewById(R.id.tv_comment_count);
//        tv_comment_count.setText(mLists.get(position).);

        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
        bitmapUtils.display(iv_img, mLists.get(position).listimage);

        return convertView;
    }
}
