package com.innodep.touchandswipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    float iTouchDownX = 0;
    float iTouchDownY = 0;
    private View view_draw;
    private Rect mRootBoundRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_draw                    = findViewById(R.id.view_draw);
        ConstraintLayout root_layout = findViewById(R.id.root_layout);
        root_layout.setOnTouchListener(this);

        mRootBoundRect = new Rect();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float iTouchUpX = 0;
        float iTouchUpY = 0;

        switch (view.getId()) {
            case R.id.root_layout: {
                Log.e("onTouch", "root_layout Area");

            final int centerX = getResources().getDisplayMetrics().widthPixels / 2;
            final int centerY = getResources().getDisplayMetrics().heightPixels / 2;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        iTouchDownX = event.getX();
                        iTouchDownY = event.getY();
                        break; }

                    case MotionEvent.ACTION_UP: {
                        iTouchUpX = event.getX();
                        iTouchUpY = event.getY();

                        Log.e("iTouchUpX",   String.valueOf(iTouchUpX));
                        Log.e("iTouchDownX", String.valueOf(iTouchDownX));
                        Log.e("result" , String.valueOf(iTouchUpX - iTouchDownX));

                        float resultX = iTouchUpX - iTouchDownX;
                        if(Math.abs(resultX) > 100) { // 숫자를 조정하면 Swipe 허용 범위를 변경할 수 있다.
                            // Swipe Action 일 경우..
                            Log.e("onTouch", "============== SWIPE EVENT ==============");
                            if(resultX > 0)
                                Log.e("SWIPE", "LEFT TO RIGHT");
                            else
                                Log.e("SWIPE", "RIGHT TO LEFT");

                        } else {
                            // Touch 일 경우..
                            Log.e("onTouch", "============== TOUCH EVENT ==============");

                            // get boundary for DrawLineView
                            view_draw.getHitRect(mRootBoundRect);
                            if(mRootBoundRect.contains((int)iTouchUpX,(int)iTouchUpY)) {
                                Log.e("TEST", "Hello Child View");

                                if (iTouchUpX < centerX) {
                                    if (iTouchUpY < centerY) {
                                        // TOP , LEFT 1
                                        Log.e("touchArea", "TOP LEFT");
                                    }
                                    if (iTouchUpY > centerY) {
                                        // Bottom , LEFT 3

                                        Log.e("touchArea", "BOTTOM LEFT");
                                    }
                                }

                                if (iTouchUpX > centerX) {
                                    if (iTouchUpY < centerY) {
                                        // TOP , RIGHT 2
                                        Log.e("touchArea", "TOP RIGHT");

                                    }
                                    if (iTouchUpY > centerY) {
                                        // BOTTOM , RIGHT 4

                                        Log.e("touchArea", "BOTTOM RIGHT");
                                    }
                                }
                            }
                        }
                        break; }
                }

                break; }
        }

        return true;
    }


    private Rect getLocationOnScreen(View mView) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mView.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mView.getWidth();
        mRect.bottom = location[1] + mView.getHeight();

        return mRect;
    }

}