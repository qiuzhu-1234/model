package com.cnnp.model.activity;

import java.io.File;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import org.w3c.dom.Text;

import com.cnnp.model.R;
import com.cnnp.model.common.CameraUtils;

public class CustomCameraRecordingActivity  extends Activity implements View.OnClickListener {

    private SurfaceView mCameraPreview;
    private SurfaceHolder mSurfaceHolder;
    private ImageButton mShutter;
    private TextView mMinutePrefix;
    private TextView mMinuteText;
    private TextView mSecondPrefix;
    private TextView mSecondText;
    private Camera mCamera;
    private MediaRecorder mRecorder;
    private final static int CAMERA_ID = 0;
    private boolean mIsRecording = false;
    private boolean mIsSufaceCreated = false;
    private static final String TAG = "Jackie";
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera_recording);
        mCameraPreview = (SurfaceView) findViewById(R.id.camera_preview);
        mMinutePrefix = (TextView) findViewById(R.id.timestamp_minute_prefix);
        mMinuteText = (TextView) findViewById(R.id.timestamp_minute_text);
        mSecondPrefix = (TextView) findViewById(R.id.timestamp_second_prefix);
        mSecondText = (TextView) findViewById(R.id.timestamp_second_text);
        mSurfaceHolder = mCameraPreview.getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallback);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mShutter = (ImageButton) findViewById(R.id.record_shutter);
        mShutter.setOnClickListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIsRecording) {
            stopRecording();
        }
        stopPreview();
    }

    private SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mIsSufaceCreated = true;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mIsSufaceCreated = false;
        }
    };

    //启动预览
    private void startPreview() {
        //保证仅仅有一个Camera对象
        if (mCamera != null || !mIsSufaceCreated) {
            Log.d(TAG, "startPreview will return");
            return;
        }
        mCamera = Camera.open(CAMERA_ID);
        Parameters parameters = mCamera.getParameters();
//        Size size = getBestPreviewSize(parameters.PREVIEW_WIDTH, CameraUtils.PREVIEW_HEIGHT, parameters);  //原始的找不到工具类
        Size size = getBestPreviewSize(800, 1200, parameters);
        if (size != null) {
            parameters.setPreviewSize(size.width, size.height);
        }
        parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        parameters.setPreviewFrameRate(20);
        //设置相机预览方向
        mCamera.setDisplayOrientation(90);
        mCamera.setParameters(parameters);
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
        // mCamera.setPreviewCallback(mPreviewCallback);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        mCamera.startPreview();
    }
    private void stopPreview() {
        //释放Camera对象
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(null);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        return result;
    }
    @Override
    public void onClick(View v) {
        if (mIsRecording) {
            stopRecording();
        } else {
            initMediaRecorder();
            startRecording();
        //開始录像后，每隔1s去更新录像的时间戳
            mHandler.postDelayed(mTimestampRunnable, 1000);
        }
    }
    private void initMediaRecorder() {
        mRecorder = new MediaRecorder();//实例化
        mCamera.unlock();
        //给Recorder设置Camera对象，保证录像跟预览的方向保持一致
        mRecorder.setCamera(mCamera);
        mRecorder.setOrientationHint(90); //改变保存后的视频文件播放时是否横屏(不加这句。视频文件播放的时候角度是反的)
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置从麦克风採集声音
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); // 设置从摄像头採集图像
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // 设置视频的输出格式 为MP4
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT); // 设置音频的编码格式
        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264); // 设置视频的编码格式
        mRecorder.setVideoSize(176, 144); // 设置视频大小
        mRecorder.setVideoFrameRate(20); // 设置帧率
        // mRecorder.setMaxDuration(10000); //设置最大录像时间为10s
        mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        //设置视频存储路径
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + File.separator +
                "VideoRecorder");
        if (!file.exists()) {
        //多级目录的创建
            file.mkdirs();
        }
        mRecorder.setOutputFile(file.getPath() + File.separator + "VID_" + System.currentTimeMillis() + ".mp4");
    }
    private void startRecording() {
        if (mRecorder != null) {
            try {
                mRecorder.prepare();
                mRecorder.start();
            } catch (Exception e) {
                mIsRecording = false;
                Log.e(TAG, e.getMessage());
            }
        }
        mShutter.setImageDrawable(getResources().getDrawable(R.drawable.recording_shutter_hl));
        mIsRecording = true;
    }
    private void stopRecording() {
        if (mCamera != null) {
            mCamera.lock();
        }
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        mShutter.setImageDrawable(getResources().getDrawable(R.drawable.recording_shutter));
        mIsRecording = false;
        mHandler.removeCallbacks(mTimestampRunnable);
        //将录像时间还原
        mMinutePrefix.setVisibility(View.VISIBLE);
        mMinuteText.setText("0");
        mSecondPrefix.setVisibility(View.VISIBLE);
        mSecondText.setText("0");
        //重新启动预览
        startPreview();
    }
    private Runnable mTimestampRunnable = new Runnable() {
        @Override
        public void run() {
            updateTimestamp();
            mHandler.postDelayed(this, 1000);
        }
    };
    private void updateTimestamp() {
        int second = Integer.parseInt(mSecondText.getText().toString());
        int minute = Integer.parseInt(mMinuteText.getText().toString());
        second++;
        Log.d(TAG, "second: " + second);
        if (second < 10) {
            mSecondText.setText(String.valueOf(second));
        } else if (second >= 10 && second < 60) {
            mSecondPrefix.setVisibility(View.GONE);
            mSecondText.setText(String.valueOf(second));
        } else if (second >= 60) {
            mSecondPrefix.setVisibility(View.VISIBLE);
            mSecondText.setText("0");
            minute++;
            mMinuteText.setText(String.valueOf(minute));
        } else if (minute >= 60) {
            mMinutePrefix.setVisibility(View.GONE);
        }
    }
}