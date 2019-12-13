package cn.aorise.petition.staff.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.core.manager.ActivityManager;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityMainBinding;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.response.RAllAddress;
import cn.aorise.petition.staff.ui.adapter.TabPagerAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import cn.aorise.petition.staff.ui.fragment.AnalyzeFragment;
import cn.aorise.petition.staff.ui.fragment.HomeFragment;
import cn.aorise.petition.staff.ui.fragment.ObjectFragment;
import cn.aorise.petition.staff.ui.fragment.PersonalFragment;
import cn.aorise.petition.staff.ui.fragment.StaffFragment;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends PetitionStaffBaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    // 时间间隔
    private static final long EXIT_INTERVAL = 2000L;
    // 需要监听几次点击事件数组的长度就为几
    // 如果要监听双击事件则数组长度为2，如果要监听3次连续点击事件则数组长度为3...
    private long[] mHints = new long[2];

    private PetitionStaffActivityMainBinding mBinding;
    private ArrayList<Fragment> mFragments;
    private TabPagerAdapter mAdapter;

    private int[] mTitles = new int[]{R.string.petition_staff_home_name,R.string.petition_staff_object_name,
            R.string.petition_staff_staff_name,R.string.petition_staff_analyze_name,
            R.string.petition_staff_person_name};
    private int[] mIcons = new int[]{R.drawable.petition_staff_tab_home_selector, R.drawable.petition_staff_tab_object_selector,
            R.drawable.petition_staff_tab_staff_selector, R.drawable.petition_staff_tab_analyze_selector,
            R.drawable.petition_staff_tab_person_selector};
    public static Activity instance;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static String[] PERMISSIONS_STORAGE = {  //读写权限
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int REQUEST_PHONE_STATE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(ObjectFragment.newInstance());
        mFragments.add(StaffFragment.newInstance());
        mFragments.add(AnalyzeFragment.newInstance());
        mFragments.add(PersonalFragment.newInstance());

    }

    @Override
    protected void initView() {
        //setColor(this, Color.parseColor(getString(R.string.petition_staff_color_006ba)));
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding = DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_main);
        mBinding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mBinding.viewPager.setOffscreenPageLimit(5);
        getToolBar().setNavigationIcon(null);
        getToolBar().setVisibility(View.GONE);
        instance=this;
        getAddressAll();
        getWTTypeAll();
        requestPermission();//在进入程序的时候需要先请求文件读写权限，避免在8.0版本上的图片选择器拍照获取不到图片的问题
    }

    @Override
    protected void initEvent() {
        mAdapter = new TabPagerAdapter(getSupportFragmentManager(), mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.addOnPageChangeListener(new PageChangeListener());
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        initTabView();
    }

    private void initTabView() {
        for (int i = 0, size = mBinding.tabLayout.getTabCount(); i < size; i++) {
            TabLayout.Tab tab = mBinding.tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(creatTabView(mTitles[i], mIcons[i]));
            }
        }
    }

    private View creatTabView(@StringRes int title, @DrawableRes int imageResId) {
        View view = LayoutInflater.from(this).inflate(R.layout.petition_staff_content_tab_indicator, null);
        TextView tv = (TextView) view.findViewById(R.id.txt_tips);
        tv.setText(title);
        ImageView image = (ImageView) view.findViewById(R.id.iv_icon);
        image.setImageResource(imageResId);
        return view;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            // 将mHints数组内的所有元素左移一个位置
            System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
            // 获得当前系统已经启动的时间
            mHints[mHints.length - 1] = SystemClock.uptimeMillis();
            if ((SystemClock.uptimeMillis() - mHints[0]) > EXIT_INTERVAL) {
                showToast(getString(R.string.aorise_label_double_exit));
            } else {
                finish();
                ActivityManager.getInstance().appExit(getApplicationContext());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setToolBarTitle(getString(mTitles[position]));
            /*getToolBar().setVisibility((0 == position) ? View.GONE : View.VISIBLE);*/
            getToolBar().setVisibility(View.GONE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void getAddressAll(){
        Subscription subscription = PetitionStaffApiService.Factory.create().getAllAddress("")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cn.aorise.petition.staff.common.Utils.mockSubscriber(this, cn.aorise.petition.staff.module.network.Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RAllAddress>>>() {
                        }.getType(), new APICallback<APIResult<List<RAllAddress>>>() {
                            @Override
                            public void onStart() {
                                sharedPreferences=getSharedPreferences("LongSp",MODE_PRIVATE);
                                editor=sharedPreferences.edit();
                                editor.putString("AllAddress",getJson("Address.json")).commit();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onNext(APIResult<List<RAllAddress>> listAPIResult) {/*
                                sharedPreferences=getSharedPreferences("LongSp",MODE_PRIVATE);
                                editor=sharedPreferences.edit();
                                if (!sharedPreferences.getString("AllAddress","").equals("")){
                                    if (!listAPIResult.getData().equals(sharedPreferences.getString("AllAddress",""))){
                                        editor.putString("AllAddress",GsonUtil.toJson(listAPIResult.getData()));
                                    }
                                } else {
                                    editor.putString("AllAddress", GsonUtil.toJson(listAPIResult.getData()));
                                }
                                editor.commit();
                            */}

                            @Override
                            public void onMock(APIResult<List<RAllAddress>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    private void getWTTypeAll(){

        Subscription subscription = PetitionStaffApiService.Factory.create().getAllType("")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cn.aorise.petition.staff.common.Utils.mockSubscriber(this, cn.aorise.petition.staff.module.network.Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RAllAddress>>>() {
                        }.getType(), new APICallback<APIResult<List<RAllAddress>>>() {
                            @Override
                            public void onStart() {
                                sharedPreferences=getSharedPreferences("LongSp",MODE_PRIVATE);
                                editor=sharedPreferences.edit();
                                editor.putString("AllType",getJson("Type.json")).commit();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onNext(APIResult<List<RAllAddress>> listAPIResult) {/*
                                sharedPreferences=getSharedPreferences("LongSp",MODE_PRIVATE);
                                editor=sharedPreferences.edit();
                                if (!sharedPreferences.getString("AllType","").equals("")){
                                    if (!listAPIResult.getData().equals(sharedPreferences.getString("AllType",""))){
                                        editor.putString("AllType",GsonUtil.toJson(listAPIResult.getData()));
                                    }
                                } else {
                                    editor.putString("AllType",GsonUtil.toJson(listAPIResult.getData()));
                                }
                                editor.commit();
                            */}

                            @Override
                            public void onMock(APIResult<List<RAllAddress>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    public String getJson(String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = MainActivity.this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        //申请相机权限
        /*ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, 1);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(this)
                        .setMessage("申请相机权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请相机权限
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.CAMERA}, 1);
                            }
                        })
                        .show();
            } else {
                //申请相机权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, 1);
            }
        } else {

        }*/
//
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            //        Android6 .0 需要动态获取权限

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 2);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                //用户勾选了不再询问
                //提示用户手动打开权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "相机权限已被禁止", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == 2) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        } else if (requestCode == REQUEST_PHONE_STATE) {
            TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        }
    }
}
