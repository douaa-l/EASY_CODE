package com.example.easycode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import com.example.easycode.Outils.Outils;

public class TutoActivity extends AppCompatActivity {
VideoView videoView=null;
Boolean pause=false;
String id_user;
int stopPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuto);

         Intent intent=getIntent();
         if(intent!=null){
             if(intent.hasExtra("id_user")){
                 id_user=intent.getStringExtra("id_user");
             }
         }
        videoView=findViewById(R.id.tuto);

        Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
        videoView.setVideoURI(uri);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pause){pause=false;
                    Log.d("on doit continuer", "onClick: resume");
                    videoView.seekTo(stopPosition);
                    videoView.start();
                }else
                {videoView.pause();pause=true;
                    stopPosition = videoView.getCurrentPosition();
                    Log.d("c'est une pause", "onClick: pause");}
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

               Intent intent=new Intent(TutoActivity.this,NiveauActivity.class);
               intent.putExtra("id_user",id_user);
               startActivity(intent);
               finish();
                videoView=null;
            }
        });
        videoView.start();
    }
}
