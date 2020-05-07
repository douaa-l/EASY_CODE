package com.example.easycode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SuivanteActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    String description="";
    String titre="";
    String image="";
    TextView des=null;
    TextView title=null;
    ImageView img=null;
    ArrayList<String> titres=new ArrayList<>();
    ArrayList<String>images=new ArrayList<>();
    ArrayList<String>descriptions=new ArrayList<>();
    int position;
    GestureDetector detector;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivante);
        Intent intent= getIntent();
        if(intent!=null){

            if (intent.hasExtra("description")){
                description= (intent.getStringExtra("description"));
            }
            if (intent.hasExtra("titre")){
                titre= (intent.getStringExtra("titre"));
            }
            if (intent.hasExtra("image")){
                image= (intent.getStringExtra("image"));
            }
            if (intent.hasExtra("images")){
                images= (intent.getStringArrayListExtra("images"));
            }
            if (intent.hasExtra("descriptions")){
                descriptions= (intent.getStringArrayListExtra("descriptions"));
            }
            if (intent.hasExtra("titres")){
                titres= (intent.getStringArrayListExtra("titres"));
            }
            if (intent.hasExtra("position")){
                position= (intent.getIntExtra("position",0));
            }


        }
        des=findViewById(R.id.description);
        title=findViewById(R.id.titre);
        img=findViewById(R.id.img);

        des.setText(description);
        title.setText(titre);
        int imgid=getResources().getIdentifier(image,"drawable","com.example.easycode");
        img.setImageDrawable(getResources().getDrawable(imgid));

        detector=new GestureDetector(this,this);


    }

        public void onSwipeRight() {
            if(position<images.size()){
                position++;

                des.setText(descriptions.get(position));
                title.setText(titres.get(position));
                int imgid=getResources().getIdentifier(images.get(position)+"g","drawable","com.example.easycode");
                img.setImageDrawable(getResources().getDrawable(imgid));
}else {System.out.println("swipe right///////////////////////");}

        }

        public void onSwipeLeft() {
            if(position!=0){
                position--;

                des.setText(descriptions.get(position));
                title.setText(titres.get(position));
                int imgid=getResources().getIdentifier(images.get(position)+"g","drawable","com.example.easycode");
                img.setImageDrawable(getResources().getDrawable(imgid));
             }else {System.out.println("swipe left///////////////////////");}
        }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX < 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
