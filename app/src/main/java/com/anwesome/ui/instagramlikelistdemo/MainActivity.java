package com.anwesome.ui.instagramlikelistdemo;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.ui.instagramlikelayout.InstagramLikeLayout;
import com.anwesome.ui.instagramlikelayout.PicTextElement;

public class MainActivity extends AppCompatActivity {
    private int imageIds[] = {R.drawable.car1,R.drawable.car2,R.drawable.car3,R.drawable.car4};
    private String titles[] = {"Awesome Car1","Terrific Car2","Beautiful Car3","Jaw Dropping Car4"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InstagramLikeLayout mainLayout = (InstagramLikeLayout)findViewById(R.id.main_layout);

        for(int i=0;i<imageIds.length;i++){
            mainLayout.addElement(new PicTextElement(titles[i], BitmapFactory.decodeResource(getResources(),imageIds[i]),BitmapFactory.decodeResource(getResources(),R.drawable.profile)));
        }
        mainLayout.create();
    }
}
