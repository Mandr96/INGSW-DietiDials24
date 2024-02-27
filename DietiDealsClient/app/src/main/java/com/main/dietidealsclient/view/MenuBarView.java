package com.main.dietidealsclient.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.main.dietidealsclient.R;

public class MenuBarView extends RelativeLayout {

    public MenuBarView(Context context) {
        super(context);
    }

    public MenuBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_menu_bar, this);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
    }
}
