/*
 * This file is part of Siebe Projects samples.
 *
 * Siebe Projects samples is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Siebe Projects samples is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the Lesser GNU General Public License
 * along with Siebe Projects samples.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.markchan.carrier.presenter.util.keyboard;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

/**
 * The keyboard height provider, this class uses a PopupWindow
 * to calculate the window height when the floating keyboard is opened and closed.
 */
public class KeyboardHeightProvider extends PopupWindow {

    /** The view that is used to calculate the keyboard height */
    private View mPopupView;

    /** The root mActivity that uses this KeyboardHeightProvider */
    private Activity mActivity;

    /** The parent view */
    private View mRootView;

    /** The keyboard height mObserver */
    private KeyboardHeightObserver mObserver;

    /** The cached landscape height of the keyboard */
    private int mKeyboardLandscapeHeight;

    /** The cached portrait height of the keyboard */
    private int mKeyboardPortraitHeight;

    /**
     * Construct a new KeyboardHeightProvider
     *
     * @param activity The parent mActivity
     */
    public KeyboardHeightProvider(Activity activity) {
        super(activity);
        mActivity = activity;

        mPopupView = new FrameLayout(activity);
        mPopupView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));

        setContentView(mPopupView);

        setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        mRootView = activity.findViewById(android.R.id.content);

        setWidth(0);
        setHeight(LayoutParams.MATCH_PARENT);

        mPopupView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (mPopupView != null) {
                    handleOnGlobalLayout();
                }
            }
        });
    }

    /**
     * Start the KeyboardHeightProvider, this must be called after the onResume of the Activity.
     * PopupWindows are not allowed to be registered before the onResume has finished
     * of the Activity.
     */
    public void start() {
        if (!isShowing() && mRootView.getWindowToken() != null) {
            setBackgroundDrawable(new ColorDrawable(0));
            showAtLocation(mRootView, Gravity.NO_GRAVITY, 0, 0);
        }
    }

    /**
     * Close the keyboard height provider,
     * this provider will not be used anymore.
     */
    public void close() {
        mObserver = null;
        dismiss();
    }

    /**
     * Set the keyboard height mObserver to this provider. The
     * mObserver will be notified when the keyboard height has changed.
     * For example when the keyboard is opened or closed.
     *
     * @param observer The mObserver to be added to this provider.
     */
    public void setKeyboardHeightObserver(KeyboardHeightObserver observer) {
        mObserver = observer;
    }

    /**
     * Get the screen orientation
     *
     * @return the screen orientation
     */
    private int getScreenOrientation() {
        return mActivity.getResources().getConfiguration().orientation;
    }

    /**
     * Popup window itself is as big as the window of the Activity.
     * The keyboard can then be calculated by extracting the popup view bottom
     * from the mActivity window height.
     */
    private void handleOnGlobalLayout() {
        Point screenSize = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(screenSize);

        Rect rect = new Rect();
        mPopupView.getWindowVisibleDisplayFrame(rect);

        // REMIND, you may like to change this using the fullscreen size of the phone
        // and also using the status bar and navigation bar heights of the phone to calculate
        // the keyboard height. But this worked fine on a Nexus.
        int orientation = getScreenOrientation();
        int keyboardHeight = screenSize.y - rect.bottom;

        if (keyboardHeight == 0) {
            notifyKeyboardHeightChanged(0, orientation);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            mKeyboardPortraitHeight = keyboardHeight;
            notifyKeyboardHeightChanged(mKeyboardPortraitHeight, orientation);
        } else {
            mKeyboardLandscapeHeight = keyboardHeight;
            notifyKeyboardHeightChanged(mKeyboardLandscapeHeight, orientation);
        }
    }

    private void notifyKeyboardHeightChanged(int height, int orientation) {
        if (mObserver != null) {
            mObserver.onKeyboardHeightChanged(height, orientation);
        }
    }

    public int getKeyboardLandscapeHeight() {
        return mKeyboardLandscapeHeight;
    }

    public int getKeyboardPortraitHeight() {
        return mKeyboardPortraitHeight;
    }
}
