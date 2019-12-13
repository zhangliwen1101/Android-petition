package cn.aorise.platform.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import cn.aorise.common.R;
import cn.aorise.common.component.common.Utils;
import cn.aorise.common.component.network.AoriseApiService;
import cn.aorise.common.component.network.Mock;
import cn.aorise.common.component.network.entity.response.AccountInfo;
import cn.aorise.common.core.cache.SpCache;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.utils.assist.AoriseLog;
import cn.aorise.common.core.utils.assist.GsonUtil;

import cn.aorise.petition.staff.databinding.PetitionStaffActivityLoginBinding;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TLogin;
import cn.aorise.petition.staff.module.network.entity.response.RLogin;
import cn.aorise.petition.staff.ui.activity.MainActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffLoginActivity;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 公共登录页面
 * Created by tangjy on 2017/3/17.
 */
public class CustomLoginActivity extends PetitionStaffBaseActivity  implements View.OnClickListener{
    private PetitionStaffActivityLoginBinding mBinding;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (cn.aorise.petition.staff.R.id.rl_login==id) {
            toLogin();
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        sp=getSharedPreferences(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_info),MODE_PRIVATE);
        editor=sp.edit();
        System.out.println(sp.getBoolean(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_isLogin),false));;
        if (sp.getBoolean(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_isLogin),false)==true){
            Intent intent=new Intent(CustomLoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        mBinding= DataBindingUtil.setContentView(this, cn.aorise.petition.staff.R.layout.petition_staff_activity_login);
        setWindowStatusBarColor(this, cn.aorise.petition.staff.R.color.petition_staff_0066ba);
        mBinding.rlLogin.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {

    }
    private void toLogin() {
        TLogin tLogin=new TLogin();
        tLogin.setYHM(mBinding.edtIdNum.getText().toString());
        tLogin.setMM(mBinding.edtPassword.getText().toString());
        System.out.println(GsonUtil.toJson(tLogin));
        Subscription subscription = PetitionStaffApiService.Factory.create().PetitionLogin(GsonUtil.toJson(tLogin))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, cn.aorise.petition.staff.module.network.Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RLogin>>() {
                        }.getType(), new APICallback<APIResult<RLogin>>() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onNext(APIResult<RLogin> rLoginAPIResult) {
                                showToast(rLoginAPIResult.getMsg());
                                if (rLoginAPIResult.getMsg().equals(getString(cn.aorise.petition.staff.R.string.petitioin_staff_login_succeed))){
                                    editor.putBoolean(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_isLogin),true);
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_ZP),rLoginAPIResult.getData().getZP());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_XM),rLoginAPIResult.getData().getXM());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_XB),rLoginAPIResult.getData().getXB());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_ZJLX),rLoginAPIResult.getData().getZJLX());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_ZJHM),rLoginAPIResult.getData().getZJHM());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_DHHM),rLoginAPIResult.getData().getDHHM());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_CSRQ),rLoginAPIResult.getData().getCSRQ());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_BH),rLoginAPIResult.getData().getBH());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_XXDZ),rLoginAPIResult.getData().getXXDZ());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_MZ),rLoginAPIResult.getData().getMZ());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_YHM),mBinding.edtIdNum.getText().toString());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_SSBM),rLoginAPIResult.getData().getSSBM());
                                    editor.putString(getString(cn.aorise.petition.staff.R.string.petition_staff_sp_ID),rLoginAPIResult.getData().getID());
                                    editor.commit();
                                    Intent intent=new Intent(CustomLoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    CustomLoginActivity.this.finish();
                                }
                            }

                            @Override
                            public void onMock(APIResult<RLogin> rLoginAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

}
