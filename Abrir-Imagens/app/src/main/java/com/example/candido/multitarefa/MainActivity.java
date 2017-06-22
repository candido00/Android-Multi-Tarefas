package com.example.candido.multitarefa;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView imageDownload;
    Button btDownload;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referenciando os objetos da nossa tela
        imageDownload  = (ImageView) findViewById(R.id.imageDownload);
        btDownload = (Button) findViewById(R.id.btDownload);

        //click botao
        btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baixarImagens();


            }
        });
    }
    public void baixarImagens(){
        progressDialog = ProgressDialog.show(MainActivity.this,"Unibratec","Baixando imagem...");

        //criando a nova thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1-transformando a url em objeto
                    URL url = new URL("https://img.ibxk.com.br//2016/02/12/12181349212022-t1200x480.jpg");
                    //2-abrindo a conexao http
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    //3-pegando uma imagem em byte code
                    InputStream imputStream = http.getInputStream();
                    //4-transformando em bit map
                    final Bitmap bitmap = BitmapFactory.decodeStream(imputStream);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            imageDownload.setImageBitmap(bitmap);

                        }
                    });

                    Log.i("MainActivity","Baixou a imagem");

                }catch (Exception e){
                    Log.i("MainActivity","Nao baixou a imagem");
                    e.printStackTrace();

                }
            }
        }).start();
    }
}
