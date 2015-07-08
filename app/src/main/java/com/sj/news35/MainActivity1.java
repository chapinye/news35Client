package com.sj.news35;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.FrameLayout;
import android.widget.RadioGroup;


public class MainActivity1 extends FragmentActivity {

    private FrameLayout mFrameLayout;
    private RadioGroup main_radio;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ctrl+alt+f
//        mFrameLayout = (FrameLayout) findViewById(R.id.layout_content);
        main_radio = (RadioGroup) findViewById(R.id.main_radio);

        main_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_function:
                        index = 0;
                        break;
                    case R.id.rb_news_center:
                        index = 1;
                        break;
                    case R.id.rb_smart_service:
                        index = 2;
                        break;
                    case R.id.rb_gov_affairs:
                        index = 3;
                        break;
                    case R.id.rb_setting:
                        index = 4;
                        break;
                }
                //初始化fragment
                Fragment fragment = (Fragment) fragments.instantiateItem(mFrameLayout, index);
                //设置第一个fragment
                fragments.setPrimaryItem(mFrameLayout, 0, fragment);
                //提交事务
                fragments.finishUpdate(mFrameLayout);
            }
        });

        main_radio.check(R.id.rb_function);
    }

    FragmentStatePagerAdapter fragments = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;

            }
            return null;
        }
    };
}
