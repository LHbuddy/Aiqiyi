package com.jju.edu.aiqiyi;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;


import com.jju.edu.aiqiyi.entity.Video;

import java.io.File;

/**
 * Created by Administrator on 2016/11/20 0020.
 */

public class VideoPlayer extends Activity {

    private VideoView videoView;
    private MediaController mController;
    private RelativeLayout.LayoutParams layoutParams;
    private Video playVideoFile;
    private int width, height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.videoplayer_layout);
        videoView = (VideoView) findViewById(R.id.video);
        mController = new MediaController(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        if (width / height > 0) {
            //横屏
            layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        } else if (width / height == 0) {
            //竖屏
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        }
        Bundle bundle = getIntent().getExtras();
        playVideoFile = (Video) bundle.getSerializable("video");
        File video = new File(playVideoFile.getPath());
        if (video.exists()) {
            videoView.setLayoutParams(layoutParams);
            videoView.setVideoPath(video.getAbsolutePath());
            videoView.setMediaController(mController);
            mController.setMediaPlayer(videoView);
            videoView.start();
            videoView.requestFocus();
        }

    }

}



