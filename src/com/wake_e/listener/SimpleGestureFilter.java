package com.wake_e.listener;


import com.wake_e.MainActivity;
import com.wake_e.StationActivity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class SimpleGestureFilter implements OnTouchListener {

	static final String logTag = "ActivitySwipeDetector";
	private Activity activity;
	static final int MIN_DISTANCE = 100;// TODO change this runtime based on screen resolution. for 1920x1080 is to small the 100 distance
	private float downX, downY, upX, upY;

	// private MainActivity mMainActivity;

	public SimpleGestureFilter(MainActivity mainActivity) {
		activity = mainActivity;
	}

	public void onRightToLeftSwipe() {
		Log.i(logTag, "RightToLeftSwipe!");
		Toast.makeText(activity, "RightToLeftSwipe", Toast.LENGTH_SHORT).show();
		// activity.doSomething();
	}

	public void onLeftToRightSwipe() {
		Log.i(logTag, "LeftToRightSwipe!");
		Toast.makeText(activity, "LeftToRightSwipe", Toast.LENGTH_SHORT).show();
		// activity.doSomething();
	}

	public void onTopToBottomSwipe() {
		Log.i(logTag, "onTopToBottomSwipe!");
		Toast.makeText(activity, "onTopToBottomSwipe", Toast.LENGTH_SHORT).show();
		// activity.doSomething();
	}

	public void onBottomToTopSwipe() {
		Intent intent = new Intent(activity, StationActivity.class);
		activity.startActivity(intent);
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			downX = event.getX();
			downY = event.getY();
			return true;
		}
		case MotionEvent.ACTION_UP: {
			upX = event.getX();
			upY = event.getY();

			float deltaX = downX - upX;
			float deltaY = downY - upY;

			// swipe horizontal?
					if (Math.abs(deltaX) > MIN_DISTANCE) {
						// left or right
						if (deltaX < 0) {
							this.onLeftToRightSwipe();
							return true;
						}
						if (deltaX > 0) {
							this.onRightToLeftSwipe();
							return true;
						}
					} else {
						Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long horizontally, need at least " + MIN_DISTANCE);
						// return false; // We don't consume the event
					}

					// swipe vertical?
					if (Math.abs(deltaY) > MIN_DISTANCE) {
						// top or down
						if (deltaY < 0) {
							this.onTopToBottomSwipe();
							return true;
						}
						if (deltaY > 0) {
							this.onBottomToTopSwipe();
							return true;
						}
					} else {
						Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long vertically, need at least " + MIN_DISTANCE);
						// return false; // We don't consume the event
					}

					return false; // no swipe horizontally and no swipe vertically
		}// case MotionEvent.ACTION_UP:
		}
		return false;
	}
}