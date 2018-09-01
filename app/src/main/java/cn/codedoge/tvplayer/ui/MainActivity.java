package cn.codedoge.tvplayer.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cn.codedoge.tvplayer.R;
import cn.codedoge.tvplayer.adapter.MyFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MyFragmentPagerAdapter pagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "Search...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.open_url:
                Toast.makeText(this, "无法读取本地文件，请授予权限", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Log.d(TAG, "Clicked Settings");

                Toast.makeText(getApplicationContext(), "无法读取本地文件，请授予权限", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    private void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.removeAllTabs();
        mTabLayout.addTab(mTabLayout.newTab().setText("本地视频").setIcon(R.drawable.local));
        mTabLayout.addTab(mTabLayout.newTab().setText("在线直播").setIcon(R.drawable.live));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switch (pos) {
                    case 0:
                        tab.setIcon(R.drawable.localfff);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.livefff);
                        break;
                    default:
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switch (pos) {
                    case 0:
                        tab.setIcon(R.drawable.local);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.live);
                        break;
                    default:
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }


    private void getLoadMedia() {
        Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)); // id
                String displayName =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 显示名称
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
                long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
                String resolution =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));
                String category =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.CATEGORY));
                String mimetype =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));

                Log.d(TAG, "\nid:" + id + "\ndisplayName:" + displayName + "\ntitle:" + title
                        + "\npath:" + path + "\nduration:" + duration + "\nsize:" + size
                        + "\nresolution:" + resolution + "\ncategory:" + category
                        + "\nmimetype:" + mimetype + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }
}
