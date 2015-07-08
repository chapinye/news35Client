package com.sj.news35;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class GuidActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final Button button = (Button) findViewById(R.id.button);

        List<View> lists = new ArrayList<View>();

        ImageView imageView1 = new ImageView(this);
        imageView1.setBackgroundResource(R.drawable.guide_1);

        ImageView imageView2 = new ImageView(this);
        imageView2.setBackgroundResource(R.drawable.guide_2);

        ImageView imageView3 = new ImageView(this);
        imageView3.setBackgroundResource(R.drawable.guide_3);

        lists.add(imageView1);
        lists.add(imageView2);
        lists.add(imageView3);

        ViewPagerAdapter adapter = new ViewPagerAdapter(lists);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    button.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuidActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private class ViewPagerAdapter extends PagerAdapter {

        private List<View> pages;

        public ViewPagerAdapter(List<View> lists) {
            this.pages = lists;
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(pages.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(pages.get(position), 0);
            return pages.get(position);
        }
    }
}
