package cn.aorise.platform.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.R;
import cn.aorise.common.component.common.Utils;
import cn.aorise.common.component.network.AoriseApiService;
import cn.aorise.common.component.network.Mock;
import cn.aorise.common.component.network.entity.response.AccountInfo;
import cn.aorise.common.core.cache.SpCache;
import cn.aorise.common.core.manager.ActivityManager;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.utils.assist.AoriseLog;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.common.databinding.AoriseActivityComponentLoginBinding;
import cn.aorise.petition.databinding.PetitionActivityMainBinding;
import cn.aorise.petition.module.network.PetitionApiService;
import cn.aorise.petition.module.network.entity.response.RAllAddress;
import cn.aorise.petition.module.network.entity.response.RLogin;
import cn.aorise.petition.ui.activity.LoginActivity;
import cn.aorise.petition.ui.activity.MainActivity;
import cn.aorise.petition.ui.adapter.TabPagerAdapter;
import cn.aorise.petition.ui.base.PetitionBaseActivity;
import cn.aorise.petition.ui.fragment.ChatFragment;
import cn.aorise.petition.ui.fragment.HomeFragment;
import cn.aorise.petition.ui.fragment.NoticeFragment;
import cn.aorise.petition.ui.fragment.PersonalFragment;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 公共登录页面
 * Created by tangjy on 2017/3/17.
 */
public class CustomLoginActivity extends PetitionBaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    // 时间间隔
    private static final long EXIT_INTERVAL = 2000L;
    // 需要监听几次点击事件数组的长度就为几
    // 如果要监听双击事件则数组长度为2，如果要监听3次连续点击事件则数组长度为3...
    private long[] mHints = new long[2];

    private PetitionActivityMainBinding mBinding;
    private ArrayList<Fragment> mFragments;
    private TabPagerAdapter mAdapter;

    private int[] mTitles = new int[]{cn.aorise.petition.R.string.petition_tab_label_home, cn.aorise.petition.R.string.petition_tab_label_notice,
            cn.aorise.petition.R.string.petition_tab_label_chat, cn.aorise.petition.R.string.petition_tab_label_personal};
    private int[] mIcons = new int[]{cn.aorise.petition.R.drawable.petition_tab_home_selector, cn.aorise.petition.R.drawable.petition_tab_notice_selector,
            cn.aorise.petition.R.drawable.petition_tab_chat_selector, cn.aorise.petition.R.drawable.petition_tab_personal_selector};

    private int id=0;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(NoticeFragment.newInstance());
        mFragments.add(ChatFragment.newInstance());
        mFragments.add(PersonalFragment.newInstance());
    }

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, cn.aorise.petition.R.layout.petition_activity_main);
        mBinding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
        getToolBar().setNavigationIcon(null);
        getToolBar().setVisibility(View.GONE);
        /*sharedPreferences=getSharedPreferences("LongSp",MODE_PRIVATE);
        if (sharedPreferences.getString("AllType","").equals("")){
            getWTTypeAll();
        } else {

        }
        if (sharedPreferences.getString("AllAddress","").equals("")){
            getAddressAll();
        } else {

        }*/

        getWTTypeAll();
        getAddressAll();

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
                if (getSharedPreferences(getString(cn.aorise.petition.R.string.petition_sharepre_name),MODE_PRIVATE).
                        getBoolean(getString(cn.aorise.petition.R.string.petition_sharepre_islogin),false)
                        ==false&&i==3){
                    tab.setCustomView(creatTabView(mTitles[3], mIcons[3]));
                    //mBinding.viewPager.setCurrentItem(2);
                } else {
                    tab.setCustomView(creatTabView(mTitles[i], mIcons[i]));
                }
            }

        }
    }

    private View creatTabView(@StringRes int title, @DrawableRes int imageResId) {
        View view = LayoutInflater.from(this).inflate(cn.aorise.petition.R.layout.petition_content_tab_indicator, null);
        TextView tv = (TextView) view.findViewById(cn.aorise.petition.R.id.txt_tips);
        tv.setText(title);
        ImageView image = (ImageView) view.findViewById(cn.aorise.petition.R.id.iv_icon);
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
                showToast(getString(cn.aorise.petition.R.string.aorise_label_double_exit));
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
            getToolBar().setVisibility((0 == position) ? View.GONE : View.VISIBLE);
            System.out.println("第"+position+"个fragment");
            id=position;
            if (position==3){
                if (getSharedPreferences(getString(cn.aorise.petition.R.string.petition_sharepre_name),MODE_PRIVATE).
                        getBoolean(getString(cn.aorise.petition.R.string.petition_sharepre_islogin),false)
                        ==false){
                    //mBinding.viewPager.setCurrentItem(2);
                    Intent intent=new Intent(CustomLoginActivity.this,LoginActivity.class);
                    startActivity(intent);
                    id=2;
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getSharedPreferences(getString(cn.aorise.petition.R.string.petition_sharepre_name),MODE_PRIVATE).
                getBoolean(getString(cn.aorise.petition.R.string.petition_sharepre_islogin),false)
                ==false) {
            mBinding.viewPager.setCurrentItem(id);
        }
    }

    private void getAddressAll(){
        Subscription subscription = PetitionApiService.Factory.create().getAllAddress("")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cn.aorise.petition.common.Utils.mockSubscriber(this, cn.aorise.petition.module.network.Mock.PETITION_LOGIN,
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
                                        System.out.println("获取所有问题类型3·");
                                    }
                                } else {
                                    editor.putString("AllAddress",GsonUtil.toJson(listAPIResult.getData()));
                                    System.out.println("获取所有问题类型4·");
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

        Subscription subscription = PetitionApiService.Factory.create().getAllType("")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cn.aorise.petition.common.Utils.mockSubscriber(this, cn.aorise.petition.module.network.Mock.PETITION_LOGIN,
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
                                        System.out.println("获取所有问题类型1·");
                                    }
                                } else {
                                    editor.putString("AllType",GsonUtil.toJson(listAPIResult.getData()));
                                    System.out.println("获取所有问题类型2·");
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
            AssetManager assetManager = CustomLoginActivity.this.getAssets();
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

}
