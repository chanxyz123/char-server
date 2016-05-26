package com.example.chandra.asynctask;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button button;
    ImageView image;
    String image_url ="http://www.in.com/contents/elements/SaveImage.php?image=11aee4c0baeac0dd2c19be0eccc4500d_w_s.jpg&id=317146&ctid=WALLPAPERS&dimension=1024X768";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        button = (Button)findViewById(R.id.button);
        image = (ImageView)findViewById(R.id.image);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button)
        {
            Downloadtask downloadtask = new Downloadtask();
            downloadtask.execute(image_url);
        }
    }

    private class Downloadtask extends AsyncTask<String,Integer,String>
    {
//        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setTitle("Downloading in progress....");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progressDialog.setMax(100);
//            progressDialog.setProgress(0);
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String ... params) {
                String path = params[0];
                int file_length = 0;
                try {
                    URL url = new URL(path);
                    URLConnection connection = url.openConnection();
                    connection.connect();
//                file_length = connection.getContentLength();
                    File new_folder = new File("sdcard/photos");
                    if (!new_folder.exists()) {
                        new_folder.mkdir();
                    }
                    File inputfile = new File(new_folder, "Lion.jpg");
                    InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                    byte[] data = new byte[1024];
                    int count = 0;
                    int total = 0;
                    OutputStream outputStream = new FileOutputStream(inputfile);
                    while ((count = inputStream.read(data)) != -1) {
                        total += count;
                        outputStream.write(data, 0, count);
//                    int progess = (int) total*100/file_length;
//                    publishProgress(progess);
                    }
                    inputStream.close();
                    outputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return "downloading completed";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
//            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String v) {
//            progressDialog.hide();
            Toast.makeText(getApplicationContext(),v,Toast.LENGTH_LONG).show();
            String path = "sdcard/photos/Lion.jpg";
            image.setImageDrawable(Drawable.createFromPath(path));
        }
    }
}
