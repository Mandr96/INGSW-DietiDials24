package com.main.dietidealsclient.Utility;

import android.os.Environment;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalTime;

public class Logger {

    private static boolean firstTime = true;
    private static final String LOG_FILE_NAME =
            Environment.getExternalStorageDirectory().getPath() + "/Documents/" + "my_log.txt";

    public static void log(String context, String message) {
        Log.d("MyLogger -> txt", context + " :- " + message);

        boolean append;
        if (firstTime){
            firstTime = false;
            append = false;
        }else {
            append = true;
        }

        message = LocalTime.now() + " - " + context + " :- " + message;

        // Check storage state and permission (implement permission request logic)
        if (isExternalStorageWritable()) {
            try (PrintWriter out = new PrintWriter(new FileOutputStream(LOG_FILE_NAME, append))) {
                out.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Handle read-only scenario (e.g., log to Logcat)
            Log.w("Logger", "External storage not writable or permission denied");
        }
    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
