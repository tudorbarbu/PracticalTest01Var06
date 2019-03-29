package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Random;

public class ProcessingThread extends Thread {

    private Context context = null;

    private Random random = new Random();
    boolean isRunning = true;
    private int my_num;

    public ProcessingThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        while (isRunning) {
            Log.d("mytag", "started");
            sendMessage();
            sleep();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("guess_serv");
        intent.putExtra("number", random.nextInt(10));
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        Log.d("mytag", "stoped");

        isRunning = false;
    }
}
