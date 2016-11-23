package com.jju.edu.aiqiyi;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;


import com.jju.edu.aiqiyi.entity.Video;

import java.io.File;

/**
 * Created by Administrator on 2016/11/20 0020.
 */

public class VideoPlayer extends BaseActivity {
    private ImageView img_full;
    private VideoView videoView;
    private MediaController mController;
    private RelativeLayout.LayoutParams layoutParams;
    private Video playVideoFile;
    private int width, height;
    private static boolean islandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.videoplayer_layout);

        videoView = (VideoView) findViewById(R.id.video);
        img_full = (ImageView) findViewById(R.id.img_full);


        // ~~~ 获取播放地址和标题
        Bundle bundle = getIntent().getExtras();
        playVideoFile = (Video) bundle.getSerializable("video");

        player_(playVideoFile);


        img_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!islandscape) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    img_full.setImageResource(R.drawable.suoxiao);
                    islandscape = true;
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    img_full.setImageResource(R.drawable.full_scren);
                    islandscape = false;
                }
            }
        });


    }


    private void player_(Video playVideo) {
        mController = new MediaController(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        if (width / height > 0) {
            islandscape = true;
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
            islandscape = false;
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        }


        File video = new File(playVideo.getPath());
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



