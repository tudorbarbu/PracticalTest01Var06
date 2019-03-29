package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class PracticalTest01Var06PlayActivity extends AppCompatActivity {

    int generated_number = 0;
    int true_number = 0;
    int score = 0;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.generate:
                    EditText e = (EditText)findViewById(R.id.number);
                    generated_number = new Random().nextInt(10);
                    e.setText(Integer.toString(generated_number));
                    break;
                case R.id.back:
                    finish();
                    break;
                case R.id.check:
                    if(generated_number == true_number) {
                        score++;
                        EditText e2 = (EditText) findViewById(R.id.score);
                        e2.setText(Integer.toString(score));
                    }
                    break;
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("mytag", "recieved");
            generated_number = intent.getIntExtra("number", 0);
            EditText e = (EditText)findViewById(R.id.number);
            e.setText(Integer.toString(generated_number));
        }
    }

    private IntentFilter intentFilter = new IntentFilter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_play);
        intentFilter.addAction("guess_serv");
        Intent intent = getIntent();
        if (intent != null) {
            true_number = intent.getIntExtra("number", 0);
        }

        Log.d("mytag", Integer.toString(true_number));

        ((Button)findViewById(R.id.generate)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.check)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.back)).setOnClickListener(buttonClickListener);
        ((EditText)findViewById(R.id.number)).setText(Integer.toString(generated_number));
        ((EditText)findViewById(R.id.score)).setText(Integer.toString(score));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // apelarea metodei din activitatea parinte este recomandata, dar nu obligatorie
        super.onSaveInstanceState(savedInstanceState);
        // ...

        savedInstanceState.putString("number", ((EditText)findViewById(R.id.number)).getText().toString());
        savedInstanceState.putString("score", ((EditText)findViewById(R.id.score)).getText().toString());
        savedInstanceState.putInt("score2", score);
        savedInstanceState.putInt("true_number", true_number);
        savedInstanceState.putInt("generated_number", generated_number);

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // apelarea metodei din activitatea parinte este recomandata, dar nu obligatorie
        super.onRestoreInstanceState(savedInstanceState);
        // ...
        ((EditText)findViewById(R.id.number)).setText(savedInstanceState.getString("number"));
        ((EditText)findViewById(R.id.score)).setText(savedInstanceState.getString("score"));
        score = savedInstanceState.getInt("score2");
        true_number = savedInstanceState.getInt("true_number");
        generated_number = savedInstanceState.getInt("generated_number");

    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        Intent serv = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
        stopService(serv);
        super.onDestroy();
    }
}
