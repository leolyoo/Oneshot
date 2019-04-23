package com.example.a82102.oneshot;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SensorGame extends Activity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor accelerometer;
    Sensor magneticField;
    float[] accData = null;
    float[] magData = null;
    float[] rotationMatrix = new float[9];
    float[] values = new float[3];
    int pitch;
    int roll;
    MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        myView = new MyView(this);
        setContentView(myView); //activity_main.xml파일을 사용하지 않고 아래 생성한 MyView클래스로 코드 레벨에서 화면 구현, activity_main.xml파일에서 뷰컨테이너를 만든 후 연결하여 위젯처럼 사용할 수 있음
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) { //SensorEventListener 클래스를 상속받아 오버라이드한 메소드, 센서값의 변화가 있으면 이 메소드의 SensorEvent event 안에 값이 들어감
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                accData = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magData = event.values.clone();
                break;
        }

        if (accData != null && magData != null) { // 들어온 값으로 기기의 방향을 계산해주는 과정
            SensorManager.getRotationMatrix(rotationMatrix, null, accData, magData);
            SensorManager.getOrientation(rotationMatrix, values);
            pitch = (int) Math.toDegrees(values[1]);
            roll = (int) Math.toDegrees(values[2]);
            myView.updateImagePosition(pitch, roll);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

class MyView extends View {
    Bitmap image;
    Paint paint;
    int centerX;
    int centerY;
    int imageX;
    int imageY;
    int imageWidth;
    int imageHeight;
    int width;
    int height;
    boolean isRun = true;
    boolean isOver = false;

    public MyView(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isRun) { //코드 실행에서 한 번만 실행되는 코드들
            paint = new Paint();
            paint.setTextSize(30);
            width = getWidth();
            height = getHeight();
            centerX = width / 2;
            centerY = height / 2;
            imageX = centerX;
            imageY = centerY;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            image = BitmapFactory.decodeResource(getResources(), R.drawable.bottle, options);
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
            isRun = false;
        }
        canvas.drawBitmap(image, imageX - (imageWidth / 2), imageY - (imageHeight / 2), null);
        if (isOver) {
            canvas.drawText("병이 깨졌습니다.", 10, 50, paint);
        }
    }

    void updateImagePosition(int pitch, int roll) { //이미지의 위치를 방향에 따라 이동시켜주는 메소드
        Log.i("김치전", "pitch : " + pitch + ", roll : " + roll);
        int thisX = imageX;
        int thisY = imageY;

        thisX = thisX + roll;
        thisY = thisY - pitch;

        if (thisX < 0) {
            thisX = 0;
            isOver = true;
        }

        if (thisX > width) {
            thisX = width;
            isOver = true;
        }

        if (thisY < 0) {
            thisY = 0;
            isOver = true;
        }

        if (thisY > height) {
            thisY = height;
            isOver = true;
        }

        imageX = thisX;
        Log.i("김치전", "imageX = " + imageX + ", imageY = " + imageY);
        imageY = thisY;

        invalidate();
    }
}
