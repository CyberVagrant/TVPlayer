package cn.codedoge.tvplayer.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import cn.codedoge.tvplayer.R;

public class LiveActivity extends AppCompatActivity {

    private static final String TAG = "LiveActivity";

    ViewGroup.LayoutParams mVideoViewLayoutParams;
    RelativeLayout mVideoLayout;
    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        mVideoView = findViewById(R.id.live_video_view);
        mVideoLayout = findViewById(R.id.video_layout);

        Intent intent = getIntent();
        String path = intent.getStringExtra("media_path");
        Log.e("MayTheForceWithYou", "media_path: " + path);
        mVideoView.setVideoURI(Uri.parse(path));
        mVideoView.start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int rot = getWindowManager().getDefaultDisplay().getRotation();
        Log.d(TAG, "onConfigurationChanged : " + newConfig + ", rot : " + rot);
        if(rot == Surface.ROTATION_90 || rot == Surface.ROTATION_270){
            mVideoViewLayoutParams = mVideoLayout.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mVideoLayout.setLayoutParams(layoutParams);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }else if(rot == Surface.ROTATION_0){
//            RelativeLayout.LayoutParams lp = new  RelativeLayout.LayoutParams(320,240);
//            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            mVideoLayout.setLayoutParams(mVideoViewLayoutParams);
        }
    }
}
