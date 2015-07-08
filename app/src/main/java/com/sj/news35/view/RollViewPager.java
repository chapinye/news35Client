package com.sj.news35.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.sj.news35.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动滚动的viewpager
 * <p/>
 * 1.先把几个点传进来
 * 2.具体要展示的图片地址
 * 3.文字描述信息
 * Created by sj on 2015/7/3 0003.
 */
public class RollViewPager extends ViewPager {

    private Context context;
    private TextView top_news_title;

    private List<String> mTitleLists;
    private List<String> mImageUrlLists;

    private List<View> dotLists;
    private Task task;
    private BitmapUtils bitmapUtils;
    private boolean has_adapter = false;

    private int mCurrentPosition = 0;
    private float downX;
    private float downY;

    public RollViewPager(Context context, List<View> dotLists) {
        super(context);
        this.context = context;
        this.dotLists = dotLists;

        task = new Task();

        bitmapUtils = new BitmapUtils(context);
        /**
         * 设置图片的色彩模式
         * 计算图片占用内存的大小
         * bitmap：
         *大小=长x宽x像素的字节数
         * ARGB 透明度 红绿蓝
         * ARGB_4444  各占四位，共占16位 16/8=2字节
         */
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_4444);

        setOnTouchListener(new MyOnTouchListener());
    }

    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setTitleLists(TextView top_news_title, List<String> mTitleLists) {
        this.top_news_title = top_news_title;
        this.mTitleLists = mTitleLists;

        if (top_news_title != null && mTitleLists != null && mTitleLists.size() > 0) {
            top_news_title.setText(mTitleLists.get(0));
        }
    }

    public void setImageUrlLists(List<String> mImageUrlLists) {
        this.mImageUrlLists = mImageUrlLists;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //处理viewpager左右滑动
                getParent().requestDisallowInterceptTouchEvent(true);
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float moveY = ev.getY();
                float dx = Math.abs(moveX - downX);
                float dy = Math.abs(moveY - downY);
                if (dx > dy) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 开始滚动
     */
    public void startRoll() {
        if (!has_adapter) {
            has_adapter = true;

            ViewPagerAdapter adapter = new ViewPagerAdapter();
            RollViewPager.this.setAdapter(adapter);
            this.addOnPageChangeListener(new MyPageChangeListener());
        }
        handler.postDelayed(task, 4000);
    }


    public class MyOnTouchListener implements OnTouchListener {

        private float touchDownX;
        private long startTime;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.removeCallbacksAndMessages(null);
                    touchDownX = event.getX();
                    startTime = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_UP:

                    float touchUpX = event.getX();
                    float upTime = System.currentTimeMillis();
                    if (touchDownX == touchUpX && upTime - startTime < 500) {
                        //点击

                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
            }
            return false;
        }
    }

    int oldPosition = 0;

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            mCurrentPosition = position;
            if (top_news_title != null && mTitleLists != null && mTitleLists.size() > 0) {
                top_news_title.setText(mTitleLists.get(position));
            }
            if (dotLists != null && dotLists.size() > 0) {
                dotLists.get(position).setBackgroundResource(R.drawable.dot_focus);

                dotLists.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            }
            oldPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RollViewPager.this.setCurrentItem(mCurrentPosition);
            startRoll();
        }
    };

    private class Task implements Runnable {

        @Override
        public void run() {
            mCurrentPosition = (mCurrentPosition + 1) % mImageUrlLists.size();
            handler.obtainMessage().sendToTarget();
        }
    }


    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(context, R.layout.viewpager_item, null);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            bitmapUtils.display(image, mImageUrlLists.get(position));
//            ((ViewPager)container).addView(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            ((ViewPager)container).removeView((View) object);
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mImageUrlLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
