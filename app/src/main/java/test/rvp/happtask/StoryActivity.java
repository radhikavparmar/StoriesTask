package test.rvp.happtask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.VideoView;

import java.util.ArrayList;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class StoryActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener{
    private StoriesProgressView storiesProgressView;
    private VideoView videoView;
    private int progressCount;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    private FrameLayout frameLayout;
    private ProgressBar progressBar;
    ArrayList<String> videoList;
    int counter=0;
    long pressTime = 0L;
    long limit = 500L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_story);
        initView();
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pressTime = System.currentTimeMillis();
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:
                    long now = System.currentTimeMillis();
                    storiesProgressView.resume();
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    private void initView() {
        videoList = getIntent().getStringArrayListExtra("video list");
        progressCount=videoList.size();
        Log.e("tag",videoList.size()+"                       pc");
        progressBar = findViewById(R.id.progress_circular);
        frameLayout = findViewById(R.id.frame_layout);
        frameLayout.setOnTouchListener(new OnSwipeTouchListener(StoryActivity.this) {

            public void onSwipeBottom() {
                finish();
            }

        });
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);
        storiesProgressView.setStoriesCount(progressCount); // <- set stories
        storiesProgressView.setStoryDuration(12000L); // <- set a story duration
        storiesProgressView.setStoriesListener(this); // <- set listener
        storiesProgressView.startStories();
        videoView = findViewById(R.id.video_view);
        videoView.setVideoPath(videoList.get(counter));
        videoView.start();

        if(videoView.isPlaying())
        {


        }
        // bind reverse view
        Button reverse = (Button) findViewById(R.id.reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        reverse.setOnTouchListener(onTouchListener);

        // bind skip view
        Button skip = (Button) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
        skip.setOnTouchListener(onTouchListener);
    }
    @Override
    public void onNext() {

        videoView.setVideoPath(videoList.get(++counter));
        videoView.start();
    }

    @Override
    public void onPrev() {
        if ((counter - 1) < 0) return;
        videoView.setVideoPath(videoList.get(--counter));
        videoView.start();

    }

    @Override
    public void onComplete() {

    }
    @Override
    protected void onDestroy() {
        // Very important !
        storiesProgressView.destroy();
        super.onDestroy();
    }

}
