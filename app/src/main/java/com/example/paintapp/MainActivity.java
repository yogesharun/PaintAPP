package com.example.paintapp;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButtonToggleGroup;
import top.defaults.colorpicker.ColorPickerPopup;

public class MainActivity extends AppCompatActivity {
    public Button rectangle,circle,triangle,undo,redo,parallel,diamond,oval,line,pen;
    public ImageButton colorpick;
    Display displayobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rectangle=(Button)findViewById(R.id.rectangleBTN);
        circle=(Button) findViewById(R.id.roundBTN);
        triangle=(Button)findViewById(R.id.triangleBTN);
        displayobj=(Display) findViewById(R.id.DrawDisplay);
        undo=(Button) findViewById(R.id.undoBTN);
        redo=(Button)findViewById(R.id.redoBTN);
        parallel=(Button)findViewById(R.id.paraBTN);
        diamond=(Button)findViewById(R.id.diamondBTN);
        oval=(Button)findViewById(R.id.ovalBTN);
        line=(Button)findViewById(R.id.lineBTN);
        pen=(Button)findViewById(R.id.penBTN);
        displayobj.paintmode(true,true);
        displayobj.setcolor(Color.parseColor("black"));
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayobj.Undopaint();
            }
        });
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayobj.redopaint();
            }
        });
        rectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayobj.settool("Rectangle");
                displayobj.paintmode(true,true);
            }
        });
        triangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayobj.settool("Triangle");
                displayobj.paintmode(true,true);
            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayobj.settool("Circle");
                displayobj.paintmode(true,true);
            }
        });
        parallel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayobj.settool("Parallel");
                displayobj.paintmode(true,true);
            }
        });
        diamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayobj.settool("Diamond");
                displayobj.paintmode(true,true);
            }
        });
        oval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayobj.settool("Oval");
                displayobj.paintmode(true,true);
            }
        });
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayobj.settool("Line");
                displayobj.paintmode(true,true);
            }
        });
        pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayobj.settool("Pen");
                displayobj.paintmode(true,false);
            }
        });

        colorpick=(ImageButton)findViewById(R.id.rgbBTN);

        colorpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new ColorPickerPopup.Builder(MainActivity.this).initialColor(Color.RED).enableBrightness(true)
                        .enableAlpha(true).okTitle("Choose").cancelTitle("Cancel").showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(v,new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void
                            onColorPicked(int color) {
                                displayobj.setcolor(color);
                            }}); }});
    }
}
