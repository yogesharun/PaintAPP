package com.example.paintapp;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Set;


public class Display extends View {

    float initx,inity,csize,centerx,centery,tlen,otherendx,otherendy;
    String Selectedtool="Pen";
    Path tripath=new Path();
    Paint mypaint=new Paint();
    ArrayList<Path> mypaths=new ArrayList<Path>();
    ArrayList<Path> rpath=new ArrayList<Path>();
    ArrayList<Paint> rpaint=new ArrayList<Paint>();
    ArrayList<Paint> mypaints=new ArrayList<Paint>();
    boolean stock,fill;
    boolean iscompleted=true;
    RectF drawrect=new RectF();
    public int drawcolor=0;
    public Display(Context context) {
        super(context);
    }
    public void settool(String toolname)
    {
        Selectedtool=toolname;
    }

    public void setcolor(int value)
    {

        drawcolor=value;
        createpaint();
    }
    public Display(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Display(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void paintmode(boolean stock,boolean fill)
    {
        this.stock=stock;
        this.fill=fill;
        createpaint();
    }

    private void createpaint() {
        mypaint=new Paint();
        mypaint.setColor(drawcolor);
        if(stock && fill)
        {
            mypaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mypaint.setStrokeWidth(3);
        }
        else if(stock)
        {
            mypaint.setStyle(Paint.Style.STROKE);
            mypaint.setStrokeWidth(3);
        }
        else if( fill)
            mypaint.setStyle(Paint.Style.FILL);

    }

    public void Undopaint()
    {
        if(mypaints.size()>0)
        {
            rpaint.add(mypaints.get(mypaints.size()-1));
            rpath.add(mypaths.get(mypaths.size()-1));
            mypaints.remove(mypaints.size() -1);
            mypaths.remove(mypaths.size() - 1);
            invalidate();
        }
    }
    public void redopaint()
    {
        if(rpath.size()>0)
        {
            mypaints.add(rpaint.get(rpaint.size()-1));
            mypaths.add(rpath.get(rpath.size()-1));
            rpath.remove(rpath.size()-1);
            rpaint.remove(rpaint.size()-1);
            invalidate();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {

        for(int i=0;i<mypaths.size();i++){
            canvas.drawPath(mypaths.get(i),mypaints.get(i));
        }
        if(Selectedtool.equals("Circle") && !iscompleted)
            canvas.drawCircle(initx,inity,csize,mypaint);
        else if(Selectedtool.equals("Rectangle") && !iscompleted)
            canvas.drawRect(drawrect,mypaint);
        else if(Selectedtool.equals(("Triangle") )&& !iscompleted)
            canvas.drawPath(tripath,mypaint);
        else if(Selectedtool.equals(("Diamond"))&& !iscompleted)
            canvas.drawPath(tripath,mypaint);
        else if(Selectedtool.equals(("Parallel"))&& !iscompleted)
            canvas.drawPath(tripath,mypaint);
        else if(Selectedtool.equals("Oval")&& !iscompleted)
            canvas.drawOval(drawrect,mypaint);
        else if(Selectedtool.equals("Line")&& !iscompleted)
            canvas.drawPath(tripath,mypaint);
        else if(Selectedtool.equals("Pen")&& !iscompleted)
            canvas.drawPath(tripath,mypaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Path temp=new Path();
        if(Selectedtool.equals("Circle"))
        {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                initx = event.getX();
                inity = event.getY();
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                centerx = (initx + event.getX()) / 2;
                centery = (inity + event.getY()) / 2;
                csize = (float) Math.sqrt(Math.pow(centerx - event.getX(), 2) + Math.pow(centery - event.getY(), 2));
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                centerx = (initx + event.getX()) / 2;
                centery = (inity + event.getY()) / 2;
                csize = (float) Math.sqrt(Math.pow(centerx - event.getX(), 2) + Math.pow(centery - event.getY(), 2));
                temp.addCircle(initx,inity,csize,Path.Direction.CW);
                mypaths.add(temp);
                mypaints.add(mypaint);
                iscompleted=true;
                invalidate();



            }
        }
        else if(Selectedtool.equals("Rectangle"))
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                drawrect.left = (int) event.getX();
                drawrect.top = (int) event.getY();
                drawrect.right = (int) event.getX();
                drawrect.bottom = (int) event.getY();
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                drawrect.right=(int)event.getX();
                drawrect.bottom=(int)event.getY();
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                drawrect.right=(int)event.getX();
                drawrect.bottom=(int)event.getY();
                temp.reset();
                temp.moveTo(drawrect.left,drawrect.top);
                temp.lineTo(drawrect.right,drawrect.top);
                temp.lineTo(drawrect.right,drawrect.bottom);
                temp.lineTo(drawrect.left, drawrect.bottom);
                temp.lineTo(drawrect.left,drawrect.top);
                mypaths.add(temp);
                mypaints.add(mypaint);
                iscompleted=true;
                invalidate();
            }

        }
        else if(Selectedtool.equals("Triangle"))
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                initx = event.getX();
                inity = event.getY();
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                otherendy=event.getY();
                otherendx=initx+(initx-event.getX());
                tripath.reset();
                tripath.moveTo(initx,inity);
                tripath.lineTo(event.getX(),event.getY());
                tripath.lineTo(otherendx,otherendy);
                tripath.lineTo(initx,inity);
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                tripath.reset();
                otherendy=event.getY();
                otherendx=initx+(initx-event.getX());
                tripath.moveTo(initx,inity);
                tripath.lineTo(event.getX(),event.getY());
                tripath.lineTo(otherendx,otherendy);
                tripath.lineTo(initx,inity);
                iscompleted=true;
                mypaths.add(tripath);
                mypaints.add(mypaint);
                tripath=new Path();
                invalidate();
            }
        }
        else if(Selectedtool.equals("Diamond"))
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                initx = event.getX();
                inity = event.getY();
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                otherendy=event.getY();
                otherendx=initx+(initx-event.getX());
                tripath.reset();
                tripath.moveTo(initx,inity);
                tripath.lineTo(event.getX(),event.getY());
                tripath.lineTo(initx,event.getY()+(event.getY()-inity));
                tripath.lineTo(otherendx,otherendy);
                tripath.lineTo(initx,inity);
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                tripath.reset();
                otherendy=event.getY();
                otherendx=initx+(initx-event.getX());
                tripath.moveTo(initx,inity);
                tripath.lineTo(event.getX(),event.getY());
                tripath.lineTo(initx,event.getY()+(event.getY()-inity));
                tripath.lineTo(otherendx,otherendy);
                tripath.lineTo(initx,inity);
                iscompleted=true;
                mypaths.add(tripath);
                mypaints.add(mypaint);
                tripath=new Path();
                invalidate();
            }
        }
        else if(Selectedtool.equals("Parallel"))
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                initx = event.getX();
                inity = event.getY();
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                tripath.reset();
                tripath.moveTo(initx,inity);
                float nextpoint=(event.getX()-initx)/5;
                tripath.lineTo(event.getX()-nextpoint,inity);
                tripath.lineTo(event.getX(), event.getY());
                tripath.lineTo(initx+nextpoint,event.getY());
                tripath.lineTo(initx,inity);
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                tripath.reset();
                tripath.moveTo(initx,inity);
                float nextpoint=(event.getX()-initx)/5;
                tripath.lineTo(event.getX()-nextpoint,inity);
                tripath.lineTo(event.getX(), event.getY());
                tripath.lineTo(initx+nextpoint,event.getY());
                tripath.lineTo(initx,inity);
                iscompleted=true;
                mypaths.add(tripath);
                mypaints.add(mypaint);
                tripath=new Path();
                invalidate();
            }
        }
        else if(Selectedtool.equals("Oval"))
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                drawrect.left = (int) event.getX();
                drawrect.top = (int) event.getY();
                drawrect.right = (int) event.getX();
                drawrect.bottom = (int) event.getY();
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                drawrect.right=(int)event.getX();
                drawrect.bottom=(int)event.getY();
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                drawrect.right=(int)event.getX();
                drawrect.bottom=(int)event.getY();
                temp.reset();
                temp.addOval(drawrect,Path.Direction.CW);
                mypaths.add(temp);
                mypaints.add(mypaint);
                iscompleted=true;
                invalidate();
            }
        }
        else if(Selectedtool.equals("Line"))
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                initx=event.getX();
                inity=event.getY();
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                tripath.reset();
                tripath.moveTo(initx,inity);
                tripath.lineTo(event.getX(),event.getY());
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                tripath.reset();
                tripath.moveTo(initx,inity);
                tripath.lineTo(event.getX(),event.getY());
                mypaths.add(tripath);
                mypaints.add(mypaint);
                tripath=new Path();
                iscompleted=true;
                invalidate();
            }
        }
        else if(Selectedtool.equals("Pen"))
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                initx=event.getX();
                inity=event.getY();
                tripath.moveTo(initx,inity);
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                tripath.quadTo(initx,inity,event.getX(),event.getY());
                initx=event.getX();
                inity= event.getY();
                iscompleted=false;
                invalidate();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                tripath.quadTo(initx,inity,event.getX(),event.getY());
                mypaths.add(tripath);
                mypaints.add(mypaint);
                tripath=new Path();
                iscompleted=true;
                invalidate();
            }
        }



        return true;
    }
}

