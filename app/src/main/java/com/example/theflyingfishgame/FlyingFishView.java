package com.example.theflyingfishgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {

    private Bitmap fish[] = new Bitmap[2];
    private Bitmap background;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    private int score, lifeCounter;

    private int fishX = 10;
    private int fishY;
    private int fishSpeed;
    private int canvasWidth, canvasHeight;
    private boolean isTouch = false;

    private int yellowX, yellowY, yellowSpeed = 15;
    private Paint yellow = new Paint();

    private int greenX, greenY, greenSpeed = 20;
    private Paint green = new Paint();

    private int redX, redY, redSpeed = 25;
    private Paint red = new Paint();

    public FlyingFishView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellow.setColor(Color.YELLOW);
        yellow.setAntiAlias(false);

        green.setColor(Color.GREEN);
        green.setAntiAlias(false);

        red.setColor(Color.RED);
        red.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounter = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(background, 0, 0, null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight -  fish[0].getHeight() * 2;
        fishY += fishSpeed;

        if(fishY < minFishY){
            fishY = minFishY;
        }
        if(fishY > maxFishY){
            fishY = maxFishY;
        }

        fishSpeed += 2;

        if(isTouch){
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            isTouch = false;
        }
        else{
            canvas.drawBitmap(fish[1], fishX, fishY, null);
        }

        yellowX -= yellowSpeed;

        if(hitBallChecker(yellowX, yellowY)){ //when the fish hit the ball - gets 10 point
            score += 10;
            yellowX = -100;
        }

        if(yellowX < 0){
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX, yellowY, 35, yellow);

        greenX -= greenSpeed;

        if(hitBallChecker(greenX, greenY)){ //when the fish hit the ball - gets 10 point
            score += 15;
            greenX = -100;
        }

        if(greenX < 0){
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX, greenY, 30, green);

        redX -= redSpeed;

        if(hitBallChecker(redX, redY)){ //when the fish hit the ball - gets 10 point
            //score += 20;
            lifeCounter--;
            redX = -100;
            if(lifeCounter == 0){
                Intent intent = new Intent(getContext(), GameOverActivity.class);
                intent.putExtra("score", score);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(intent);
            }
        }

        if(redX < 0){
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        canvas.drawCircle(redX, redY, 25, red);
        canvas.drawText("Score: " + score, 20, 60, scorePaint);

        for(int i = 0; i < 3; i++){
            int x = (int) (580+ life[0].getWidth() * 1.5 * i);
            int y = 30;

            if(i < lifeCounter){
                canvas.drawBitmap(life[0], x, y, null);
            }
            else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }


     //   canvas.drawBitmap(life[0], 780, 10, null);
     //   canvas.drawBitmap(life[0], 890, 10, null);
     //   canvas.drawBitmap(life[0], 1000, 10, null);
    }

    public boolean hitBallChecker(int x, int y){
        if(fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight())){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            isTouch = true;
            fishSpeed = -22;
        }
        return true;
    }
}
