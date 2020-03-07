package tw.org.iii.leo.leo02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button guess;
    private EditText input;
    private TextView log;
    private String answer;
    private AlertDialog alertDialog = null;
    private int counter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("leo","onCreate()");



        guess = findViewById(R.id.guess);
        input = findViewById(R.id.input);
        log = findViewById(R.id.log);
        //這裡並非初始化 只是把id指向拉回來而已
        //匿名物件實體 按下去會做...
        guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess();
            }
        });
        initNewGame(null);
    }

    private void showDialog(boolean isWinner, String mesg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//      Activity and Server   is a Context
        builder.setTitle(isWinner?"WINNER":"LOSER");
        builder.setMessage(mesg);
        builder.setCancelable(false); //這個是不能取消
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initNewGame(null);
            }
        });


        alertDialog = builder.create();
        alertDialog.show();


    }

    private void guess(){
        counter++;
        String strInput = input.getText().toString();
//        log.setText(strInput);
//        log.append(strInput + "\n");
        String result = checkAB(strInput);
        log.append(counter + ". "+strInput + " => "+ result + "\n");
        input.setText("");

        if(result.equals(guessDig + "A0B")){
            showDialog(true,"WINNER");
        }else if ( counter == 10){
            showDialog(false,"ANS is "+ answer);
        }
    }



    public void initNewGame(View view){
        answer = createAnswer(guessDig);
        input.setText("");
        log.setText("");
        counter = 0;

        Log.v("leo","answer" +answer);
    }

    private String createAnswer(int d){
        int[] poker = new int[10];
        for (int i=0; i<poker.length; i++)poker[i]=i;

        for (int i = poker.length-1; i>0; i--) {
            int rand = (int)(Math.random()*(i+1));
            int temp = poker[rand];
            poker[rand] = poker[i];
            poker[i] = temp;
        }

        StringBuffer sb = new StringBuffer();
        for (int i=0; i<d; i++) {
            sb.append(poker[i]);
        }

        return sb.toString();
    }

    private String checkAB(String g){
        int A, B; A = B = 0;
        for (int i=0; i<answer.length(); i++) {
            if (answer.charAt(i) == g.charAt(i)) {

                A++;
            }else if(answer.indexOf(g.charAt(i)) != -1) {
                B++;
            }
        }
        return A + "A" + B + "B";
    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.v("leo","onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("leo","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("leo","onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("leo","onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("leo","onDestory()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("leo","onRestart()");
    }


    public void exit(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.v("leo","onBackPressed");
    }


    private long lastTime = 0;

    @Override
    public void finish() {
        //super.finish();
        Log.v("leo","finish()");


        if(System.currentTimeMillis() - lastTime <= 3* 1000){
            super.finish();
        }else{
            Toast.makeText(this,"press again to exit",Toast.LENGTH_SHORT).show();
        }
        lastTime = System.currentTimeMillis();


    }

    public void setting(View view) {
        showDialog3();
    }

    private void showDialog1(){
       new AlertDialog.Builder(this)
               .setTitle("INFO")
               .setMessage("hello")
               .setCancelable(false)
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               })
               .setNegativeButton("Cancel",null)
               .create()
               .show();
    }



    private int guessDig = 3;
    //猜幾碼 預設三碼
    private int tempDig = 0;





    private void showDialog2(){
        String[] items = {"Item1","Item2","Item3","Item4"};
        new AlertDialog.Builder(this)
                .setTitle("INFO")

                .setCancelable(false)
                .setItems(items,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("leo","=> "+ which);
                    }
                })

                .create()
                .show();
    }

    private void showDialog3(){
        String[] items = {"3","4","5","6"};
        new AlertDialog.Builder(this)
                .setTitle("猜幾碼")
                .setCancelable(false)
                .setSingleChoiceItems(items, guessDig-3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("leo","=> " + which);
                        tempDig = which + 3; //這個which只是這裡的區域變數、我要努力把它存起來放到下面的ok內取得
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("leo","OK");
                        guessDig = tempDig;
                        initNewGame(null);
                    }
                })
                .setNegativeButton("Cancel",null)
                .create()
                .show();
    }



}
