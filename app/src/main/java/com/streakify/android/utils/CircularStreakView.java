package com.streakify.android.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.streakify.android.R;

import info.abdolahi.CircularMusicProgressBar;

public class CircularStreakView extends CircularMusicProgressBar {
    public CircularStreakView(Context context) {
        super(context);
    }

    public CircularStreakView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircularStreakView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TTT, defStyle, 0);

        super.setValue(a.getFloat(R.styleable.TTT_progress_value, 0));
    }
}
