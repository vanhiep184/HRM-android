package eu.berdosi.app.heartbeat.Tabs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.os.Bundle;

import eu.berdosi.app.heartbeat.OutputAnalyzer;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.scottyab.HeartBeatView;

import java.time.LocalDateTime;

import eu.berdosi.app.heartbeat.R;
import eu.berdosi.app.heartbeat.Utils.HistoryMeasure;
import eu.berdosi.app.heartbeat.Utils.SQLiteHelper;

import static android.content.Context.MODE_PRIVATE;
import static eu.berdosi.app.heartbeat.MainActivity.MESSAGE_PROGRESS_REALTIME;
import static eu.berdosi.app.heartbeat.MainActivity.MESSAGE_UPDATE_FINAL;
import static eu.berdosi.app.heartbeat.MainActivity.MESSAGE_UPDATE_REALTIME;

public class MeasureFragment extends Fragment {

    private View rootView;
    private OutputAnalyzer analyzer;
    private  CameraService cameraService;
    private final boolean justShared = false;

   void init(){
       cameraService = new CameraService(getActivity());
       HeartBeatView heart = rootView.findViewById(R.id.heartbeat);
       heart.start();
       TextView clickMeasure = rootView.findViewById(R.id.measure);
       clickMeasure.setOnClickListener(v -> onClickNewMeasurement());
       clickMeasure.setVisibility(View.GONE);
   }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView =  inflater.inflate(R.layout.fragment_measurement,container,false);
        init();
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        analyzer = new OutputAnalyzer(this.getActivity(), rootView.findViewById(R.id.graphTextureView), mainHandler);

        TextureView cameraTextureView = rootView.findViewById(R.id.textureView2);
        SurfaceTexture previewSurfaceTexture = cameraTextureView.getSurfaceTexture();

        // justShared is set if one clicks the share button.
        if ((previewSurfaceTexture != null) && !justShared) {
            // this first appears when we close the application and switch back - TextureView isn't quite ready at the first onResume.
            Surface previewSurface = new Surface(previewSurfaceTexture);

            // show warning when there is no flash
            if (!this.getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                Snackbar.make(rootView.findViewById(R.id.constraintLayout), getString(R.string.noFlashWarning), Snackbar.LENGTH_LONG);
            }

            cameraService.start(previewSurface);
            analyzer.measurePulse(cameraTextureView, cameraService);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        cameraService.stop();
        if (analyzer != null) analyzer.stop();
        analyzer = new OutputAnalyzer(getActivity(), rootView.findViewById(R.id.graphTextureView), mainHandler);
    }

    @SuppressLint("HandlerLeak")
    private final Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int i = 0;
            if (msg.what == MESSAGE_UPDATE_REALTIME) {
                ((TextView) rootView.findViewById(R.id.textView)).setText(msg.obj.toString());
            }
            if (msg.what == MESSAGE_PROGRESS_REALTIME) {

                ProgressBar progress = rootView.findViewById(R.id.progress);
                progress.setProgress(Integer.parseInt(msg.obj.toString()));
            }

            if (msg.what == MESSAGE_UPDATE_FINAL) {

                HeartBeatView heart = rootView.findViewById(R.id.heartbeat);
                heart.stop();
                ((TextView) rootView.findViewById(R.id.textView)).setTextColor(Color.YELLOW);
                ((TextView) rootView.findViewById(R.id.measure)).setVisibility(View.VISIBLE);
                SQLiteHelper db = new SQLiteHelper(getContext());
                db.addResult( new HistoryMeasure("123",LocalDateTime.now(),Integer.parseInt( msg.obj.toString())));

            }
        }
    };



    public void onClickNewMeasurement() {
    analyzer = new OutputAnalyzer(this.getActivity(), rootView.findViewById(R.id.graphTextureView), mainHandler);
    TextView measure = rootView.findViewById(R.id.measure);
    measure.setVisibility(View.GONE);

    TextureView cameraTextureView = rootView.findViewById(R.id.textureView2);
    SurfaceTexture previewSurfaceTexture = cameraTextureView.getSurfaceTexture();
    ProgressBar progress = rootView.findViewById(R.id.progress);
    progress.setProgress(0);
    ((TextView) rootView.findViewById(R.id.textView)).setTextColor(Color.WHITE);
    if ((previewSurfaceTexture != null) && !justShared) {
        // this first appears when we close the application and switch back - TextureView isn't quite ready at the first onResume.
        Surface previewSurface = new Surface(previewSurfaceTexture);
        cameraService.start(previewSurface);
        analyzer.measurePulse(cameraTextureView, cameraService);
    }
}
}
