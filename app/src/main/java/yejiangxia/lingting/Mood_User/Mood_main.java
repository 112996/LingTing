package yejiangxia.lingting.Mood_User;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


import yejiangxia.lingting.Fragment.Frag_add;
import yejiangxia.lingting.Fragment.Frag_circle;
import yejiangxia.lingting.Fragment.Frag_me;
import yejiangxia.lingting.Fragment.ViewPagerAdapter;
import yejiangxia.lingting.R;

public class Mood_main extends AppCompatActivity {


    private ViewPager mood_main_viewPager;
    private BottomNavigationView mood_main_bottom_nav_view;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.btm_nav_item1:
                    mood_main_viewPager.setCurrentItem(0);

                    return true;
                case R.id.btm_nav_item2:
                    mood_main_viewPager.setCurrentItem(1);

                    return true;
                case R.id.btm_nav_item3:
                    mood_main_viewPager.setCurrentItem(2);

                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mood_main);


        mood_main_bottom_nav_view= (BottomNavigationView) findViewById(R.id.mood_main_bottom_nav_view);
        mood_main_viewPager= (ViewPager) findViewById(R.id.mood_main_viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Frag_add());
        adapter.addFragment(new Frag_me());
        adapter.addFragment(new Frag_circle());
        mood_main_viewPager.setAdapter(adapter);
        mood_main_bottom_nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mood_main_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mood_main_bottom_nav_view.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
