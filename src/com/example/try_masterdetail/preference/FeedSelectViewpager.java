package com.example.try_masterdetail.preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.try_masterdetail.R;
import com.example.try_masterdetail.util.CellListUtil;
import com.example.try_masterdetail.util.UserSelectionUtil;

public class FeedSelectViewpager extends ActionBarActivity implements FeedSelectFragment.FeedSelectCallback {

	private static final String TAG = "FEED";

	private static final int NUM_ITEMS = 1;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	private boolean backFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_select_viewpager_activity);

		List<Fragment> fragmentList = new ArrayList<Fragment>();
		fragmentList.add(FeedSelectFragment.create(0));

		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.feedSelectPager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), fragmentList);
		mPager.setAdapter(mPagerAdapter);

		int ActionBarColorId = getIntent().getIntExtra("ActionBarColor", -1);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if (ActionBarColorId != -1) {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ActionBarColorId));
			getSupportActionBar().setDisplayShowTitleEnabled(true);
		} else {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			getSupportActionBar().setBackgroundDrawable(
					new ColorDrawable(getResources().getColor(R.color.actionbar_default_color)));
			getSupportActionBar().setDisplayShowTitleEnabled(true);
		}

		getSupportActionBar().setTitle("Select Feeds");
	}

	@Override
	public void fsFragBackButton() {
		onBackPressed();
	}

	@Override
	public void fsFragDoneButton(HashMap<Integer, boolean[]> mChildCheckStates) {
		Toast.makeText(this, "DONE!", Toast.LENGTH_SHORT).show();
		backFlag = true;
		onBackPressed();

		// Update feedPrefs
		UserSelectionUtil userSelectionUtil = new UserSelectionUtil(this);
		userSelectionUtil.saveUserSelectionSharedPrefs(mChildCheckStates);

		// Update cellList
		CellListUtil cellListObject = new CellListUtil(this);
		cellListObject.updateCellListByUserSelection();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragmentList;

		public ScreenSlidePagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
		}

		@Override
		public Fragment getItem(int position) {

			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}
	}

}
