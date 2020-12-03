package eu.berdosi.app.heartbeat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.Date;

import eu.berdosi.app.heartbeat.Tabs.Adapter;
import eu.berdosi.app.heartbeat.Tabs.CameraService;

import static android.media.MediaCodec.MetricsConstants.MODE;


public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private final int REQUEST_CODE_CAMERA = 0;
    public static final int MESSAGE_UPDATE_REALTIME = 1;
    public static final int MESSAGE_UPDATE_FINAL = 2;
    public static final int MESSAGE_PROGRESS_REALTIME = 3;

    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        cameraService.stop();
//        if (analyzer != null) analyzer.stop();
//        analyzer = new OutputAnalyzer(this, findViewById(R.id.graphTextureView), mainHandler);
    }
    void initView(){
        Toolbar toolbar = findViewById(R.id.topAppBar);
        TabLayout tabs =  findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewpage);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewPager);
        tabs.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        TabLayout.Tab tab = tabs.getTabAt(1);
        tab.select();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                REQUEST_CODE_CAMERA);
        initView();
        createDatabase();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Snackbar.make(
                        findViewById(R.id.constraintLayout),
                        getString(R.string.cameraPermissionRequired),
                        Snackbar.LENGTH_LONG).show();
            }
        }
    }

    SQLiteDatabase createDatabase(){
          return  openOrCreateDatabase("measureapp.db",MODE_PRIVATE,null);
     }

}
