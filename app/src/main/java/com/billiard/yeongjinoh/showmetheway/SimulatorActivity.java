package com.billiard.yeongjinoh.showmetheway;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by yeongjinoh on 2016-07-18.
 */
public class SimulatorActivity extends Activity implements UpdateListener {

    BillardTableView billiardTableView;
    private boolean isRunning;

    // database to save score
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String TABLE_NAME = "score";
    private boolean isOpen;

    // the time that last game ends
    String lastgame;

    // to control power gauge bar
    PowerGaugeBarView powerGaugeBar;
    private boolean isHitPressed;
    private float power;

    // request codes for the other activities
    public static final int REQUEST_CODE_GAMEOVER = 1001;
    public static final int REQUEST_CODE_SPINCONTROLLER = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        // open database to save score
        isOpen = openDatabase();

        // set background image
        Resources resources = getResources();
        BitmapDrawable background = (BitmapDrawable) resources.getDrawable(R.drawable.tile);
        ImageView imageView = (ImageView) findViewById(R.id.imgSimulBackground);
        imageView.setImageDrawable(background);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        // set score update listener
        billiardTableView = (BillardTableView) findViewById(R.id.billiardTableView);
        billiardTableView.setUpdateListener(this);

        powerGaugeBar = (PowerGaugeBarView) findViewById(R.id.powerGaugeBar);

        // set menu button
        Button buttonSimulMenu = (Button) findViewById(R.id.btnSimulMenu);
        buttonSimulMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // set hit button
        isHitPressed = false;
        isRunning = false;
        Button buttonSimulHit = (Button) findViewById(R.id.btnSimulHit);
        buttonSimulHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRunning) {
                    isHitPressed = !isHitPressed;
                    if (isHitPressed) {
                        new PowerUpdateThread().start();
                    } else {
                        billiardTableView.hit(power);
                        isRunning = true;
                    }
                }

            }
        });

        // set spin controller
        ImageView spinContorller = (ImageView) findViewById(R.id.imgViewSpinControl);
        spinContorller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), SpinControlActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SPINCONTROLLER);
            }
        });
    }

    class PowerUpdateThread extends Thread {
        public void run () {
            double time = 0;
            long dt = 50;
            while (isHitPressed) {
                try {
                    time += (Math.PI * dt / 1000);
                    power = (float)(1-Math.cos(time))/2.0F;
                    powerGaugeBar.updatePower(power);
                    Thread.sleep(dt);
                } catch (Exception e) {
                    Log.e("Simul","PowerUpdateThread Exception",e);
                }
            }
            return;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    public void onScoreUpdate(int score) {
        TextView textView = (TextView) findViewById(R.id.scoreText);
        String value = "SCORE : " + Integer.toString(score);
        setText(textView, value);

        onAllStop();
    }

    // reset flags when all balls stop
    private void onAllStop() {
        isHitPressed = false;
        isRunning = false;
        power = 0.0F;
        powerGaugeBar.updatePower(power);
    }

    public void notifyGameover(int score) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
        String strDate = sdfDate.format(date);
        String strTime = sdfTime.format(date);
        insertScore(db, Integer.toString(score), strDate, strTime);

        Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
        intent.putExtra("score",score);

        // send the time information of last game
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        lastgame = timeFormat.format(date);
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("lastgame",lastgame);
        setResult(RESULT_OK, resultIntent);

        startActivityForResult(intent, REQUEST_CODE_GAMEOVER);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // for gameover activity
        if (requestCode == REQUEST_CODE_GAMEOVER) {
            if (resultCode == RESULT_OK) { // Retry button pressed
            } else if (resultCode == RESULT_CANCELED) { // Exit button pressed
                finish();
            }
        }
    }

    @Override
    public void onLifeUpdate(final int life, final int score) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

        ImageView life1, life2, life3;
        Resources resources = getResources();
	    BitmapDrawable red, yellow;
	    red = (BitmapDrawable) resources.getDrawable(R.drawable.red_ball);
	    yellow = (BitmapDrawable) resources.getDrawable(R.drawable.yellow_ball);

        switch (life) {
            case 0:
                life3 = (ImageView) findViewById(R.id.imgSimulLife3);
		        life3.setImageDrawable(yellow);
                notifyGameover(score);
                break;
            case 1:
                life2 = (ImageView) findViewById(R.id.imgSimulLife2);
		        life2.setImageDrawable(yellow);
                break;
            case 2:
                life1 = (ImageView) findViewById(R.id.imgSimulLife1);
		        life1.setImageDrawable(yellow);
                break;
            case 3:
		        life1 = (ImageView) findViewById(R.id.imgSimulLife1);
		        life2 = (ImageView) findViewById(R.id.imgSimulLife2);
		        life3 = (ImageView) findViewById(R.id.imgSimulLife3);
		        life1.setImageDrawable(red);
		        life2.setImageDrawable(red);
		        life3.setImageDrawable(red);
                break;
            default:
                break;
        }
        }
            });

    }

    private boolean openDatabase() {
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        return true;
    }


    public void insertScore(SQLiteDatabase db, String score, String date, String time) {
        if (isOpen) {
            dbHelper.println("inserting records.");
            try {
                String query = String.format("INSERT INTO %s (score, date, time) VALUES (%s, '%s', '%s');", TABLE_NAME, score, date, time);
                db.execSQL(query);
            } catch (Exception ex) {
                Log.e("SimulatorActivity", "Exception in insert SQL", ex);
                ex.printStackTrace();
            }
        }
    }

    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }
}
