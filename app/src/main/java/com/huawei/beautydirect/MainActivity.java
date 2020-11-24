package com.huawei.beautydirect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.beautydirect.model.BeautyData;
import com.huawei.beautydirect.util.NFCUtil;

import org.w3c.dom.Text;

import static com.huawei.beautydirect.util.Constants.PROJECT_TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = PROJECT_TAG + "MainActivity";

    private Button start_camera_btn;
    private TextView beauty_textView;
    private TextView city_textView;
    private TextView description_textView;
    private ImageView beauty_imageView;

    private byte[] mPayload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    // UI界面元素绑定
    private void initView() {
        start_camera_btn = findViewById(R.id.start_camera_btn);
        start_camera_btn.setOnClickListener(this);
        beauty_textView = findViewById(R.id.beauty_textView);
        city_textView = findViewById(R.id.city_textView);
        description_textView = findViewById(R.id.description_textView);
        beauty_imageView = findViewById(R.id.beauty_imageView);
    }

    // 根据某景点数据更新UI界面
    private void setView(String locationData) {
        String[] datas = locationData.split("--");
        String beautyName = datas[1];
        String beautyCity = datas[0];
        String beautyDescription = datas[2];
        int beautyImage = Integer.valueOf(datas[3]);
        beauty_textView.setText(beautyName);
        city_textView.setText(beautyCity);
        description_textView.setText(beautyDescription);
        beauty_imageView.setImageResource(beautyImage);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Log.d(TAG, "onClick: " + id);
        switch (id) {
            case R.id.start_camera_btn:
                startCamera();
                break;
            default:
                break;
        }
    }

    private void writeData() {
        Log.d(TAG, "writeData");
    }

    private void startCamera() {
        Log.d(TAG, "startCamera");
        Intent startCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(startCameraIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPayload = NFCUtil.readData(intent);
        String locationData = BeautyData.sBeautyData.get(mPayload);
        setView(locationData);
    }
}