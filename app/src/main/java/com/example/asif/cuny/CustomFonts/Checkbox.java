package com.example.asif.cuny.CustomFonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by asif on 3/22/2016.
 */
public class Checkbox extends CheckBox {

    public Checkbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public Checkbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Checkbox(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "gill.TTF");
            setTypeface(tf);
        }
    }
}
