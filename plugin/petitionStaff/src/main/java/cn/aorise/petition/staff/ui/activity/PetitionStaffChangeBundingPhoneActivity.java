package cn.aorise.petition.staff.ui.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import cn.aorise.common.component.common.Utils;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityChangeBundingPhoneBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TChangePersonalInfo;
import cn.aorise.petition.staff.module.network.entity.request.TLogin;
import cn.aorise.petition.staff.module.network.entity.response.REmptyEntity;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/5.
 */

public class PetitionStaffChangeBundingPhoneActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityChangeBundingPhoneBinding mBinding;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private SharedPreferences sp;
    public static Activity instance;
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_submit==id) {
            if (mBinding.edtPassword.getText().toString().trim().equals("")){
                showToast(R.string.petition_staff_showtoast_error);
            }else {
                VerifyPassword();
            }

        }
    }

    @Override
    protected void initData() {
        instance=this;
        sp=getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE);
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_change_bunding_phone);
        mBinding.rlSubmit.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {

    }


    private void VerifyPassword() {
        TLogin tLogin=new TLogin();
        tLogin.setYHM(sp.getString(getString(R.string.petition_staff_sp_YHM),""));
        tLogin.setMM(mBinding.edtPassword.getText().toString());
        System.out.println(GsonUtil.toJson(tLogin));
        Subscription subscription = PetitionStaffApiService.Factory.create().VerifyPassword(GsonUtil.toJson(tLogin))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
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
                                if (rEmptyEntityAPIResult.getMsg().equals(getString(R.string.petition_staff_password_true))){
                                    openActivity(PetitionStaffChangeBundingPhoneActivity01.class);
                                }
                            }

                            @Override
                            public void onMock(APIResult<REmptyEntity> rEmptyEntityAPIResult) {

                            }
                        }));

        RxAPIManager.getInstance().add(TAG, subscription);
    }
}
