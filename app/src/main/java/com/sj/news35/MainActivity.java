package com.sj.news35;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.sj.news35.fragment.HomeFragment;
import com.sj.news35.fragment.MenuFragment2;


public class MainActivity extends SlidingFragmentActivity {

    private SlidingMenu slidingMenu;

    private MenuFragment2 menu;
    private HomeFragment home;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置滑动菜单的页面
        setBehindContentView(R.layout.menu_frame);
        setContentView(R.layout.content_frame);

        //获取滑动菜单的引用（对象）
        slidingMenu = getSlidingMenu();
        //设置滑动模式 左 右 both
        slidingMenu.setMode(SlidingMenu.LEFT);
        //当滑动菜单出来以后，内容页面的剩余宽度
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        //设置滑动菜单touch模式
        //TOUCHMODE_FULLSCREEN 全屏
        //TOUCHMODE_MARGIN 只能在边缘滑动
        //TOUCHMODE_NO   不能滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置滑动阴影
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);

        menu = new MenuFragment2();
        home = new HomeFragment();
        /**
         * 第一个参数  需要替换的id
         * 第二个参数  需要替换的fragment
         * 第三个参数  tag 标签 别名
         */
        getSupportFragmentManager().beginTransaction().replace(R.id.menu, menu, "MENU").commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, home, "HOME").commit();

//        RightMenuFragment rightMenuFragment = new RightMenuFragment();
//        slidingMenu.setSecondaryMenu(R.layout.right_menu_frame);
//        slidingMenu.setSecondaryShadowDrawable(R.drawable.shadow);
//        getSupportFragmentManager().beginTransaction().replace(R.id.right_menu,rightMenuFragment,"RIGHT").commit();
    }


    public void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
        slidingMenu.toggle();
    }

    public MenuFragment2 getMenuFragment2() {
        menu = (MenuFragment2) getSupportFragmentManager().findFragmentByTag("MENU");
        return menu;
    }

    public HomeFragment getHomeFragment() {
        home = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HOME");
        return home;
    }
}
