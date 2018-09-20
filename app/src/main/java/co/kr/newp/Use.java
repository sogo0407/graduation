package co.kr.newp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;



public class Use extends FragmentActivity{

	FragmentPagerAdapter adapterViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
	findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
    }
    
    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 9;

            public MyPagerAdapter(FragmentManager fragmentManager) {
                super(fragmentManager);
            }

            // Returns total number of pages
            @Override
            public int getCount() {
                return NUM_ITEMS;
            }

            // Returns the fragment to display for that page
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FirstFragment.newInstance(0);
                case 1: // Fragment # 0 - This will shw FirstFragment different title
                    return FirstFragment.newInstance(1);
                case 2: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(2);
                case 3: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(3);
                case 4: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(4);
                case 5: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(5);
                case 6: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(6);
                case 7: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(7);
                case 8: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(8);
                    
                default:
                    return null;
                }
            }

            // Returns the page title for the top indicator
            @Override
            public CharSequence getPageTitle(int position) {
                return "Page " + position;
            }

        }
    
}
