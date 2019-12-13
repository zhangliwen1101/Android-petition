package cn.aorise.petition.staff.ui.activity;


import android.databinding.DataBindingUtil;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.Utils;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityPetitionInfoSubmissionBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TZZJG;
import cn.aorise.petition.staff.module.network.entity.response.REmptyEntity;
import cn.aorise.petition.staff.module.network.entity.response.RPetitionInfoSubmission;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/6.
 */

public class PetitionStaffPetitionInfoSubmission extends PetitionStaffBaseActivity implements View.OnClickListener{
    private PetitionStaffActivityPetitionInfoSubmissionBinding mBinding;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this, R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_petition_info_submission);
        GetPetitionSubmission();
    }

    @Override
    protected void initEvent() {

    }

    private void GetPetitionSubmission() {
        TZZJG tzzjg=new TZZJG();

        Subscription subscription = PetitionStaffApiService.Factory.create().GetPetitionInfoPush(getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE).getString(getString(R.string.petition_staff_sp_SSBM),""))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cn.aorise.common.component.common.Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RPetitionInfoSubmission>>() {
                        }.getType(), new APICallback<APIResult<RPetitionInfoSubmission>>() {
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
                            public void onNext(APIResult<RPetitionInfoSubmission> rEmptyEntityAPIResult) {
                                //showToast(rEmptyEntityAPIResult.getMsg());
                                mBinding.txtLf.setText("("+rEmptyEntityAPIResult.getData().getVisit()+")件");
                                mBinding.txtLx.setText("("+rEmptyEntityAPIResult.getData().getLetter()+")件");
                                mBinding.txtWx.setText("("+rEmptyEntityAPIResult.getData().getWeChat()+")件");
                                mBinding.txtRx.setText("("+rEmptyEntityAPIResult.getData().getTEL()+")件");
                                mBinding.txtXx.setText("("+rEmptyEntityAPIResult.getData().getMialbox()+")件");
                                mBinding.txtApp.setText("("+rEmptyEntityAPIResult.getData().getAPP()+")件");
                                mBinding.txtDsl.setText("("+rEmptyEntityAPIResult.getData().getAceepte()+")件");
                                mBinding.txtDbl.setText("("+rEmptyEntityAPIResult.getData().getTransaction()+")件");
                                mBinding.txtDsh.setText("("+rEmptyEntityAPIResult.getData().getCheck()+")件");
                                mBinding.txtLjj.setText("("+rEmptyEntityAPIResult.getData().getNear()+")件");
                                mBinding.txtYqj.setText("("+rEmptyEntityAPIResult.getData().getOverdue()+")件");
                                mBinding.txtWebsite.setText("("+rEmptyEntityAPIResult.getData().getInternet()+")件");
                            }

                            @Override
                            public void onMock(APIResult<RPetitionInfoSubmission> rEmptyEntityAPIResult) {

                            }
                        }));

        RxAPIManager.getInstance().add(TAG, subscription);
    }

}
