package ceg4110.fa2018.group21.snapasnack;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultView extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ArrayList<PlaceHolderImage> data;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = getIntent().getParcelableArrayListExtra("data");
        pos = getIntent().getIntExtra("pos", 0);
        setTitle(data.get(pos).getTitle());

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), data);


        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(pos);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(data.get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment
    {
        private String title;
        private Integer img;
        private int pos;

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_IMG_TITLE = "image_title";
        private static final String ARG_IMG_IMG = "image_img";

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            this.pos = args.getInt(ARG_SECTION_NUMBER);
            this.title = args.getString(ARG_IMG_TITLE);
            this.img = args.getInt(ARG_IMG_IMG);
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String name, Integer img) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_IMG_TITLE, name);
            args.putInt(ARG_IMG_IMG, img);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onStart() {
            super.onStart();

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_result_view, container, false);

            final ImageView imageView = (ImageView) rootView.findViewById(R.id.detail_image);

          //  Glide.with(getActivity()).load(url).thumbnail(0.1f).into(imageView);

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        private ArrayList<PlaceHolderImage> data;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<PlaceHolderImage> data)
        {
            super(fm);
            this.data = data;
        }
        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position, data.get(position).getTitle(), data.get(position).getImg());
        }

        @Override
        public int getCount()
        {
            return data.size();
        }
    }

}
