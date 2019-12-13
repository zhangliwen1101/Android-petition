package cn.aorise.petition.staff.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.GsonUtil;

import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityChangePasswordBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TChangePassword;
import cn.aorise.petition.staff.module.network.entity.request.TVerifycode;
import cn.aorise.petition.staff.module.network.entity.response.REmptyEntity;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/22.
 */

public class PetitionStaffChangePasswordActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityChangePasswordBinding mBinding;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_submit==id) {
            if (mBinding.edtNewPassword.getText().toString().equals(mBinding.edtResurePassword.getText().toString())&&
                    !mBinding.edtNewPassword.getText().toString().trim().equals("")){
                ChangePassword();
            } else {
                showToast(R.string.petition_staff_error_show);
            }

        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this, R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_change_password);
        mBinding.rlSubmit.setOnClickListener(this);

    }

    @Override
    protected void initEvent() {

    }
    private void ChangePassword() {
        sp=getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE);
        TChangePassword tChangePassword=new TChangePassword();
        tChangePassword.setYHM(sp.getString(getString(R.string.petition_staff_sp_YHM),""));
        tChangePassword.setJMM(mBinding.edtOldPassword.getText().toString());
        tChangePassword.setXMM(mBinding.edtNewPassword.getText().toString());

        System.out.println(GsonUtil.toJson(tChangePassword));
        Subscription subscription = PetitionStaffApiService.Factory.create().petitionChangePassword(GsonUtil.toJson(tChangePassword))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cn.aorise.common.component.common.Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<REmptyEntity>>() {
                        }.getType(), new APICallback<APIResult<REmptyEntity>>() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onNext(APIResult<REmptyEntity> rEmptyEntityAPIResult) {
                                showToast(rEmptyEntityAPIResult.getMsg());
                                if(rEmptyEntityAPIResult.getMsg().equals(getString(R.string.petition_staff_showtoast_08))){
                                    getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE).edit().clear().commit();
                                    Intent intent=new Intent(PetitionStaffChangePasswordActivity.this, PetitionStaffLoginActivity.class);
                                    startActivity(intent);
                                    MainActivity.instance.finish();
                                    PetitionStaffChangePasswordActivity.this.finish();


                                }
                            }

                            @Override
                            public void onMock(APIResult<REmptyEntity> rEmptyEntityAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);

    }
}
