package com.example.flyingfish;

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

    private int fishX=10;
    private int fishY;
    private int fishSpeed;
    private int canvasWidth,canvasHeight;
    private int yellowX,yelloY,yellowSpeed = 16;
    private Paint yellowPaint=new Paint();
    private int greenX,greenY,greenSpeed  = 20;
    private Paint greenPaint = new Paint();
    private int redX,redY,redSpeed  = 25;
    private Paint redPaint = new Paint();
    private int score,lifecounter;
    private boolean touch=false;
    private Bitmap fish[]=new Bitmap[2];
    private Bitmap backimg;
    private Paint scorePaint=new Paint();
    private Bitmap Life[]=new Bitmap[2];
    public FlyingFishView(Context context) {
        super(context);
        fish[0]= BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        backimg= BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);

        Life[0]=BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        Life[1]=BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY=550;
        score=0;
        lifecounter=3;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();
        canvas.drawBitmap(backimg,0,0,null);

        int minfishY=fish[0].getHeight();
        int maxfinshY=canvasHeight-fish[0].getHeight()*3;
        fishY=fishY+fishSpeed;

        if(fishY<minfishY)
        {
            fishY=minfishY;
        }
        if(fishY> maxfinshY)
        {
            fishY=maxfinshY;
        }
        fishSpeed=fishSpeed+2;
        if(touch)
        {
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch=false;
        }
        else
        {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }
        yellowX=yellowX-yellowSpeed;
        if(hitballcheck(yellowX,yelloY))
        {
            score=score+10;
            yellowX=-100;
        }
        if(yellowX<0)
        {
            yellowX=canvasWidth+21;
            yelloY=(int)Math.floor(Math.random() * (maxfinshY-minfishY))+minfishY;
        }
        canvas.drawCircle(yellowX,yelloY,25,yellowPaint);

        greenX=greenX-greenSpeed;
        if(hitballcheck(greenX,greenY))
        {
            score=score+20;
            greenX=-100;
        }
        if(greenX<0)
        {
            greenX=canvasWidth+21;
            greenY=(int)Math.floor(Math.random() * (maxfinshY-minfishY))+minfishY;
        }
        canvas.drawCircle(greenX,greenY,35,greenPaint);

        redX=redX-redSpeed;
        if(hitballcheck(redX,redY))
        {
            redX=-100;
            lifecounter--;
            if(lifecounter==0)
            {
                Toast.makeText(getContext(),"Game over",Toast.LENGTH_LONG).show();
                Intent gameover=new Intent(getContext(), GameOver.class);
                gameover.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(gameover);
            }
        }
        if(redX<0)
        {
            redX=canvasWidth+21;
            redY=(int)Math.floor(Math.random() * (maxfinshY-minfishY))+minfishY;
        }
        canvas.drawCircle(redX,redY,30,redPaint);

        canvas.drawText("Score: "+score,20,60,scorePaint);

        for(int i=0;i<3;i++)
        {
            int x=(int) (380+ Life[0].getWidth()*1.5*i);
            int y=30;
            if(i<lifecounter)
            {
                canvas.drawBitmap(Life[0],x,y,null);
            }
            else
            {
                canvas.drawBitmap(Life[1],x,y,null);
            }
        }


//        canvas.drawBitmap(Life[0],380,10,null);
//        canvas.drawBitmap(Life[0],480,10,null);
//        canvas.drawBitmap(Life[0],580,10,null);
    }

    public boolean hitballcheck(int x,int y)
    {
        if(fishX<x &&  x<(fishX+ fish[0].getWidth()) && fishY<y && y<(fishY+fish[0].getHeight()))
        {
            return true;
        }
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch=true;
            fishSpeed=-22;
        }
        return true;
    }
}
