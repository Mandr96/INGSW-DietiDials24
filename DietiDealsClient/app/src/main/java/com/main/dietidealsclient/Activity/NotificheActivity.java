package com.main.dietidealsclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.main.dietidealsclient.Adapters.AsteAdapterList;
import com.main.dietidealsclient.Adapters.NotificheAdapterList;
import com.main.dietidealsclient.Controller.NotificheController;
import com.main.dietidealsclient.Model.Notifica;
import com.main.dietidealsclient.R;

import java.util.List;

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

        findViewById(R.id.search_btton_back).setOnClickListener(view -> {
            goBack();
        });
    }

    private void updateNotifiche() {
        List<Notifica> list = notificheController.getAll();
        notificheAdapter = new NotificheAdapterList(list, (int pos) -> {});
        notificheView.setAdapter(notificheAdapter);
    }

    private void goBack() {
        Intent myIntent = new Intent(NotificheActivity.this, HomeActivity.class);
        NotificheActivity.this.startActivity(myIntent);
        finish();
    }
}
