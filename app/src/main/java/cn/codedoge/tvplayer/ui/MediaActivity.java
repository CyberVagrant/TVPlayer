package cn.codedoge.tvplayer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import cn.codedoge.tvplayer.R;
import cn.codedoge.tvplayer.common.PlayerManager;
import cn.codedoge.tvplayer.widget.media.IjkVideoView;

public class MediaActivity extends AppCompatActivity implements PlayerManager.PlayerStateListener{

    private IjkVideoView videoView;

    private PlayerManager player;

    private boolean mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

//        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
//
        Intent intent = getIntent();
        String path = intent.getStringExtra("media_path");
        int type = intent.getIntExtra("type", 0);
//        videoView = findViewById(R.id.video_view);
//        videoView.setVideoPath(path);
//        videoView.start();

        player = new PlayerManager(this);
        player.setFullScreenOnly(true);
        player.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
        player.playInFullScreen(true);
        player.setPlayerStateListener(this);
        player.play(path, type);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (player.gestureDetector.onTouchEvent(event))
            return true;
        return super.onTouchEvent(event);
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError() {
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onPlay() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();

    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
    }
}
