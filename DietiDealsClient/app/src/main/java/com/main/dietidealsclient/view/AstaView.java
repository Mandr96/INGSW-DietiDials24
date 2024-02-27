package com.main.dietidealsclient.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.main.dietidealsclient.R;

public class AstaView extends RelativeLayout {
    public AstaView(Context context) {
        super(context);
        init(context);
    }

    //Potrebbe servire
    public AstaView(Context context, String nome, String descrizione, String det1, String det2) {
        super(context);
        init(context);
        setAstaView(nome,descrizione,det1,det2);
    }

    public AstaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AstaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_asta,this);
    }

    public void setAstaView(String nome, String descrizione, String det1, String det2){
        ((TextView)findViewById(R.id.asta_nome)).setText(nome);
        ((TextView)findViewById(R.id.asta_descrizione)).setText(descrizione);
        ((TextView)findViewById(R.id.asta_det1)).setText(det1);
        ((TextView)findViewById(R.id.asta_det2)).setText(det2);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas){
        super.onDraw(canvas);
    }
}
