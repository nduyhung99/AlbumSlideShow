package com.example.albumslideshow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView imgImage;
    ImageButton btnPrevious,btnNext;
    CheckBox chkTuDong;
    EditText txtChonHinh;
    Button btnChon;

    int currentPosition=-1;
    ArrayList<String>albums;
    Timer timer=null;
    TimerTask timerTask=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        final MediaPlayer sound=MediaPlayer.create(this, R.raw.beep22);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.start();
                xuLyXemHinhKeTiep();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.start();
                xuLyXemHinhPhiaTruoc();
            }
        });
        chkTuDong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sound.start();
                if (isChecked=true){
                    btnPrevious.setEnabled(false);
                    btnNext.setEnabled(false);
                }else{
                    btnPrevious.setEnabled(true);
                    btnNext.setEnabled(true);
                    if (timer!=null){
                        timer.cancel();
                    }
                }
                xuLyTuDongChayHinh();
            }
        });
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonHinh();
            }
        });
    }

    private void xuLyChonHinh() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo!");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Bạn có chắc không?");

        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentPosition=Integer.parseInt(txtChonHinh.getText().toString());
                imageTask task = new imageTask();
                task.execute(albums.get(currentPosition));
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
    }

    private void xuLyTuDongChayHinh() {
        timerTask=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentPosition++;
                        if (currentPosition==albums.size()){
                            currentPosition=0;
                        }
                        imageTask task =new imageTask();
                        task.execute(albums.get(currentPosition));

                    }
                });
            }
        };
        timer=new Timer();
        timer.schedule(timerTask,0,5000);
    }

    private void xuLyXemHinhPhiaTruoc() {
        currentPosition--;
        if (currentPosition==-1){
            currentPosition=albums.size()-1;
        }
        imageTask task = new imageTask();
        task.execute(albums.get(currentPosition));
    }

    private void xuLyXemHinhKeTiep() {
        currentPosition++;
        if (currentPosition==albums.size()){
            currentPosition=0;
        }
        imageTask task = new imageTask();
        task.execute(albums.get(currentPosition));
    }

    private void addControls() {
        imgImage=findViewById(R.id.imgImage);
        btnPrevious=findViewById(R.id.btnPrevious);
        btnNext=findViewById(R.id.btnNext);
        chkTuDong=findViewById(R.id.chkTuDong);
        txtChonHinh=findViewById(R.id.txtChonHinh);
        btnChon=findViewById(R.id.btnChon);

        albums=new ArrayList<>();
        albums.add("https://static.wikia.nocookie.net/yugioh/images/0/09/MonsterReincarnation-JP-Anime-VR.png/revision/latest/scale-to-width-down/340?cb=20180425131055");
        albums.add("https://images-na.ssl-images-amazon.com/images/I/51xOULsN5ML._AC_.jpg");
        albums.add("https://images-na.ssl-images-amazon.com/images/I/51Qs-bCsVaL.jpg");
        albums.add("https://i.pinimg.com/originals/a8/52/98/a852989ac4826574a7a15955a00ef54c.jpg");
        albums.add("https://thuthuatchoi.com/media/photos/shares/trochoikhac/Yugioh/quai_vat_manh_nhat/Five-Headed_Dragon.jpg");
        albums.add("https://cf.shopee.vn/file/2bcb8172abda5379e28144331b386f7d");
        albums.add("https://qph.fs.quoracdn.net/main-qimg-c1da1fb103d7369fba6289a9a6b255d9.webp");
        albums.add("https://i.etsystatic.com/14026475/r/il/7b8d24/1864294234/il_570xN.1864294234_5t3h.jpg");
        albums.add("https://images.saymedia-content.com/.image/t_share/MTc3OTEzNjAwNzkzOTEyOTM2/egyptian-god-supports-yugioh.jpg");
        albums.add("https://images.saymedia-content.com/.image/t_share/MTc0NDU3MTIyMjQ1OTc3NzM0/yu-gi-oh-egyptian-god-card-countdown.png");
        currentPosition=0;
        imageTask task = new imageTask();
        task.execute(albums.get(currentPosition));
    }
    class imageTask extends AsyncTask<String,Void, Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgImage.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap=null;
            try {
                String string=strings[0];
                bitmap= BitmapFactory.decodeStream((InputStream) new URL(string).getContent());
                return bitmap;
            }
            catch (Exception ex){
                Log.e("Lỗi:",ex.toString());
            }
            return null;
        }
    }
}