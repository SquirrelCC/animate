package com.scconsulting.animate;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Animate extends AppCompatActivity {

    private ObjectAnimator cloudAnim;
    private ObjectAnimator droidJump;
    private ObjectAnimator droidCartwheel;
    private ObjectAnimator droidMove;

    private ImageView cloud1;
    private ImageView droid;
    private RelativeLayout main;

    private float degreesStart = 0f;
    private float degreesEnd = 30f;
    private float startX;
    private float startY;
    private float stopY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animate);

        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        main = (RelativeLayout) findViewById(R.id.main_layout);
        cloud1 = (ImageView) findViewById(R.id.cloud1);
        droid = (ImageView) findViewById(R.id.droid);

        main.setOnTouchListener(mainListen);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {

            cloudAnim = ObjectAnimator.ofFloat(cloud1, "x", 0 - cloud1.getWidth(), main.getWidth());
            cloudAnim.setDuration(30000);
            cloudAnim.setRepeatCount(ValueAnimator.INFINITE);
            cloudAnim.setRepeatMode(ValueAnimator.RESTART);
            cloudAnim.start();

            startY = droid.getY();
            stopY = startY - 100f;
            startX = droid.getX();

            doJump();

        }
    }

    private void doJump() {

        droidJump = ObjectAnimator.ofFloat( droid, "y", startY, stopY, startY );
        droidJump.setDuration(1000);
        droidJump.setRepeatCount(2);
        droidJump.setRepeatMode(ValueAnimator.REVERSE);
        droidJump.addListener(jumpListen);
        droidJump.start();

    }

    private void doCartwheel() {

        droidCartwheel = new ObjectAnimator();
        droidCartwheel = ObjectAnimator.ofFloat( droid, "rotation", degreesStart, degreesEnd );
        droidCartwheel.setDuration(20);
        droidCartwheel.addListener(cartwheelListen);
        droidCartwheel.start();

    }

    Animator.AnimatorListener jumpListen = new Animator.AnimatorListener() {

        @Override
        public void onAnimationCancel(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            doCartwheel();
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationStart(Animator animation) {
            // TODO Auto-generated method stub

        }

    };

    Animator.AnimatorListener cartwheelListen = new Animator.AnimatorListener() {

        @Override
        public void onAnimationCancel(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            if (degreesEnd < 360) {
                droidMove = ObjectAnimator.ofFloat(droid, "x", startX, startX + droid.getWidth() / 12);
                droidMove.setDuration(20);
                droidMove.start();
                startX += droid.getWidth()/12;

                degreesStart += 30f;
                degreesEnd += 30f;
                droidCartwheel = ObjectAnimator.ofFloat(droid, "rotation", degreesStart, degreesEnd);
                droidCartwheel.setDuration(20);
                droidCartwheel.addListener(cartwheelListen);
                droidCartwheel.start();
            }
            else {
                degreesStart = 0f;
                degreesEnd = 30f;
            }

            if (startX + droid.getWidth()/12 > main.getWidth()) {
                startX = 0;
            }
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationStart(Animator animation) {
            // TODO Auto-generated method stub

        }
    };

    View.OnTouchListener mainListen = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                doJump();
            }
            return true;
        }
    };

}
