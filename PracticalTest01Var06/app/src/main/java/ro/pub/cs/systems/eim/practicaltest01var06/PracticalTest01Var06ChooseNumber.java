package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class PracticalTest01Var06ChooseNumber extends AppCompatActivity {
    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.play:
                    if(((EditText)findViewById(R.id.etnumber)).getText()==null)
                        break;
                    String s = ((EditText)findViewById(R.id.etnumber)).getText().toString();
                    if (s == null)
                        break;
                    try {
                        int nr = Integer.parseInt(s);
                        if (nr >= 0 && nr <= 9) {
                            Intent serv = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
                            startService(serv);
                            Intent i = new Intent(getApplicationContext(), PracticalTest01Var06PlayActivity.class);
                            i.putExtra("number", nr);
                            startActivityForResult(i, 1);

                        }
                    } catch (Exception e) {

                    }

                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_choose_number);

        ((Button)findViewById(R.id.play)).setOnClickListener(buttonClickListener);

    }
}
