package com.main.dietidealsclient.Activity;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.main.dietidealsclient.Adapters.AsteAdapterList;
import com.main.dietidealsclient.Adapters.NotificheAdapterList;
import com.main.dietidealsclient.Controller.NotificheController;
import com.main.dietidealsclient.R;

public class NotificheActivity extends ComponentActivity {

    private RecyclerView notificheView;
    private NotificheAdapterList notificheAdapter;
    private NotificheController notificheController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiche);
        notificheController = new NotificheController();

        notificheView = findViewById(R.id.recycle_view_notifiche);

        updateNotifiche();
    }

    private void updateNotifiche() {

    }
}
