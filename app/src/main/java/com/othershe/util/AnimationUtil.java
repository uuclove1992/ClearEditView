package com.othershe.util;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * AnimationUtil
 *
 * @author baidu
 * @since 2017/1/26
 */
public class AnimationUtil {
    public static RotateAnimation getRotateAmination(boolean isFilterAfter, int duration, int repeatCount) {
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(isFilterAfter);
        LinearInterpolator lirInterpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(lirInterpolator);
        rotateAnimation.setRepeatCount(repeatCount);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        return rotateAnimation;
    }

}
