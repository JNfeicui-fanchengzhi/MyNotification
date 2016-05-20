package com.fanfan.feicui.mynotification;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Camera;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

public class OtherActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "OtherActivity";
    Button mbtnPhone;
    Button mbtnMemory;
    Button mbtnCPU;
    Button mbtnDisplay;
    Button mbtnRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        mbtnPhone = (Button) findViewById(R.id.btn_phone);
        mbtnMemory = (Button) findViewById(R.id.btn_memoey);
        mbtnCPU = (Button) findViewById(R.id.btn_cpu);
        mbtnDisplay = (Button) findViewById(R.id.btn_display);
        mbtnRoot = (Button) findViewById(R.id.btn_root);

        mbtnPhone.setOnClickListener(this);
        mbtnMemory.setOnClickListener(this);
        mbtnCPU.setOnClickListener(this);
        mbtnDisplay.setOnClickListener(this);
        mbtnRoot.setOnClickListener(this);
    }
    public boolean isRoot() {
        boolean bool = false;
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = false;
            } else {
                bool = true;
            }
        } catch (Exception e) {
        }
        return bool;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_phone:
                //电话服务
                TelephonyManager telManager = (TelephonyManager) OtherActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
                //设备ID 获取手机IMEI
                String deviceId = telManager.getDeviceId();
                Log.d(TAG, "TelephonyManager:deviceId " + deviceId);
                //拿到电话号码
                String linelNumber = telManager.getLine1Number();
                Log.d(TAG, "TelephonyManager:linelNumber " + linelNumber);
                //网络状态编码 网络类型
                int networkType = telManager.getNetworkType();
                Log.d(TAG, "TelephonyManager:networkType " + networkType);
                //国家代码
                String countryIso = telManager.getSimCountryIso();
                Log.d(TAG, "TelephonyManager:countryIso " + countryIso);
                //运营商名称
                String operatorName = telManager.getSimOperatorName();
                Log.d(TAG, "TelephonyManager:operatorName " + operatorName);
                //移动终端的类型
                int phonepType = telManager.getPhoneType();
                Log.d(TAG, "TelephonyManager:phoneType " + phonepType);
                break;
            case R.id.btn_cpu:
                //wifi服务
                WifiManager wifi = (WifiManager)OtherActivity.this.getSystemService(WIFI_SERVICE);
                int wifiState =wifi.getWifiState();// WIFI_STATE_ENABLED = 3
                Log.d(TAG, "WifiManager:wifiState " + wifiState);
                WifiInfo wifiinfo = wifi.getConnectionInfo();
                //获取BSSID
                String bssId = wifiinfo.getBSSID();
                Log.d(TAG, "WifiManager: bssid == " + bssId);
                //获得IP地址
                int ipAddress = wifiinfo.getIpAddress();// 503425216 --> byte 2
                Log.d(TAG, "WifiManager: ipAddress == " + ipAddress);
                //获得连接速度
                int linkSpeed = wifiinfo.getLinkSpeed();// 43 --> mbps /8  3.2
                Log.d(TAG, "WifiManager: linkSpeed == " + linkSpeed);
                //获得Mac的地址
                String macAddress = wifiinfo.getMacAddress();
                Log.d(TAG, "WifiManager: macAddress == " + macAddress);
                //获得SSID 服务集标示
                String ssid = wifiinfo.getSSID();// 指的是链接的wifi名称
                Log.d(TAG, "WifiManager: ssid == " + ssid);
                int networkId = wifiinfo.getNetworkId();
                Log.d(TAG, "WifiManager: networkId == " + networkId);
                //设备系统SDK的版本号
                int sdkInt = Build.VERSION.SDK_INT;// sdkInt == 23 --> 6.0
                Log.d(TAG, "SDK:sdkInt==" + sdkInt);
                //设备系统基带版本
                String release = Build.VERSION.RELEASE;// release == 6.0.1
                Log.d(TAG, "SDK: release == " + release);
                File dir = new File("/sys/devices/system/cpu/");
                File[] files = dir.listFiles(new CpuFilter());
                Log.d(TAG, "CPU: " + files.length); // 4 4核心的CPU
//                String string = Build.SUPPORTED_ABIS.toString();
//                Log.d(TAG, "abis: CPU品牌" + string);
                String brand = Build.BRAND;
                Log.d(TAG, "abis: 设备 brand " + brand); // 手机品牌
                String manufacturer = Build.MANUFACTURER;
                Log.d(TAG, "abis: 设备 manufacturer " + manufacturer); // 制造商
                String model = Build.MODEL;
                Log.d(TAG, "abis: 设备 model " + model); // 具体型号
                String radioVersion = Build.getRadioVersion();//基带版本
                Log.d(TAG, "abis: radioVersion == " + radioVersion);
                break;
            case R.id.btn_memoey:
                ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
                ActivityManager manager = (ActivityManager) OtherActivity.this.getSystemService(
                        ACTIVITY_SERVICE);
                manager.getMemoryInfo(info);
                long availMem = info.availMem;
                Log.d(TAG, "getMemoryInfo: " + availMem / 1024 / 1024); // 272158720  拿到的是瞬间值
                //获取手机内置sdcard路径, 为null表示无
                String sdCardPath = MemorManager.getPhoneInSDCardPath();
                Log.d(TAG, "sdCardPath: " + sdCardPath);
                //设备自身存储全部大小 单位B
                long phoneSelfSize = MemorManager.getPhoneSelfSize(); // 1397428224 byte值 /1024 kb /1024 mb
                long mb = phoneSelfSize / 1024 / 1024;
                Log.d(TAG, "phoneSelfSize: " + mb); //phoneSelfSize: 1332
                //设备自身存储空闲大小 单位B
                long phoneSelfFreeSize = MemorManager.getPhoneSelfFreeSize();
                Log.d(TAG, "phoneSelfFreeSize: " + phoneSelfFreeSize);  //681328640 --> 649
                //获取到的是手机自带的储存空间 单位是byte
                float phoneSize = MemorManager.getPhoneSelfSDCardSize(); // 手机内部储存 不是SD卡
                Log.d(TAG, "phoneSize: " + (phoneSize / 1024 / 1024 / 1024));  // 5204983808  约等于5G
                //  4963MB 4.847519GB
                //获取到的是手机自带的储存空间的剩余空间  单位byte
                float phoneFreeSize = MemorManager.getPhoneSelfSDCardFreeSize(); // 约900MB
                Log.d(TAG,
                        "phoneFreeSize: " + (phoneFreeSize / 1024 / 1024 / 1024));  //947101696   phoneFreeSize: 0.8819313
                //获取手机总存储大小
                long phoneAllSize = MemorManager.getPhoneAllSize();
                long phoneAllFreeSize = MemorManager.getPhoneAllFreeSize();
                Log.d(TAG,
                        "phoneAllSize: ==" + phoneAllSize / 1024 / 1024 + "   phoneAllFreeSize:==" +
                                phoneAllFreeSize / 1024 / 1024); //phoneAllSize: ==6296   phoneAllFreeSize:==1552
                break;
            case R.id.btn_display:
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay()
                        .getMetrics(metrics);
                int widthPixels = metrics.widthPixels;
                int heightPixels = metrics.heightPixels;
                Log.d(TAG, "DisplayMetrics: " + widthPixels + "===" + heightPixels);  // width
                // 720 height 1184
                // Camera
                Camera camera = Camera.open();
                Camera.Parameters parameters = camera.getParameters();
                List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
                Camera.Size s = null;
                for (Camera.Size size : sizes) {
                    if (s == null) {
                        s = size;
                    } else if (s.width * s.height < size.width * size.height) {
                        s = size;
                    }
                }
                camera.release();
                Log.d(TAG, "onClick: " + s.width + "---" + s.height); // 3264px---2448px 1920*1080
                break;
            case R.id.btn_root:
                Log.d(TAG, "btn_root:  " + isRoot());
                break;

        }

    }
    class CpuFilter implements FileFilter {
        public boolean accept(File pathname) {
            if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                return true;
            }
            return false;
        }
    }
}
