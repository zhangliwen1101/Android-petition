package cn.aorise.petition.staff.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.GsonUtil;

import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.Utils;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityChangeBundingPhone01Binding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TDhhm;
import cn.aorise.petition.staff.module.network.entity.request.TVerifycode;
import cn.aorise.petition.staff.module.network.entity.request.Txsjh;
import cn.aorise.petition.staff.module.network.entity.response.REmptyEntity;
import cn.aorise.petition.staff.module.network.entity.response.RLogin;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/19.
 */

public class PetitionStaffChangeBundingPhoneActivity01 extends PetitionStaffBaseActivity implements View.OnClickListener{
    private PetitionStaffActivityChangeBundingPhone01Binding mBinding;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.txt_verification==id) {
            getVerificationCode();

        } else if (R.id.rl_submit==id) {
            VerifyCode();
        }
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            mBinding.txtVerification.setText((int) (l / 1000) + "s后重发");
            mBinding.txtVerification.setBackgroundResource(R.drawable.verification_staff_color_false);
            mBinding.txtVerification.setClickable(false);
        }

        @Override
        public void onFinish() {
            mBinding.txtVerification.setClickable(true);
            mBinding.txtVerification.setText("获取验证码");
            mBinding.txtVerification.setBackgroundResource(R.drawable.verification_staff_color_true);

        }
    };
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_change_bunding_phone_01);
        mBinding.txtVerification.setOnClickListener(this);
        mBinding.rlSubmit.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {

    }

    private void getVerificationCode() {
        TDhhm tDhhm=new TDhhm();
        tDhhm.setDHHM(mBinding.edtPhoneNumber.getText().toString());
        tDhhm.setLX("3");
        System.out.println(GsonUtil.toJson(tDhhm));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetVerificationCode(GsonUtil.toJson(tDhhm))
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
                                if (rEmptyEntityAPIResult.getMsg().equals(getString(R.string.petition_verifycode_send))){
                                    timer.start();
                                }
                            }

                            @Override
                            public void onMock(APIResult<REmptyEntity> rEmptyEntityAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);

    }
    private void VerifyCode () {

        TVerifycode tVerifyCode=new TVerifycode();
        tVerifyCode.setDHHM(mBinding.edtPhoneNumber.getText().toString());
        tVerifyCode.setYZM(mBinding.edtYzm.getText().toString());
        System.out.println(GsonUtil.toJson(tVerifyCode));
        Subscription subscription = PetitionStaffApiService.Factory.create().VerifyCode(GsonUtil.toJson(tVerifyCode))
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
                                //showToast(rEmptyEntityAPIResult.getMsg());
                                if (rEmptyEntityAPIResult.getMsg().equals(getString(R.string.petition_staff_verifycode_true))){
                                    ChangePhoneNum();
                                }
                            }

                            @Override
                            public void onMock(APIResult<REmptyEntity> rEmptyEntityAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    private void ChangePhoneNum () {
        sp=getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE);
        Txsjh txsjh=new Txsjh();
        txsjh.setXSJH(mBinding.edtPhoneNumber.getText().toString());
        txsjh.setYHM(sp.getString(getString(R.string.petition_staff_sp_YHM),""));
        System.out.println(GsonUtil.toJson(txsjh));
        Subscription subscription = PetitionStaffApiService.Factory.create().petitionChangePhoneNum(GsonUtil.toJson(txsjh))
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
                                    if (rEmptyEntityAPIResult.getMsg().equals(getString(R.string.petition_staff_showtoast_04))){
                                        PetitionStaffChangeBundingPhoneActivity01.this.finish();
                                        PetitionStaffChangeBundingPhoneActivity.instance.finish();
                                    }
                            }

                            @Override
                            public void onMock(APIResult<REmptyEntity> rEmptyEntityAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

}
