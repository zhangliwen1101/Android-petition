package cn.aorise.petition.staff.ui.activity;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.aorise.common.component.common.Utils;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.AoriseUtil;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityImportantObjectDetailBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TImportantPeople;
import cn.aorise.petition.staff.module.network.entity.request.TZjhm;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionPeople;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionPeopleDetail;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/31.
 */

public class PetitionStaffImportantPetitionPeopleDetailActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private PetitionStaffActivityImportantObjectDetailBinding mBinding;
    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this, R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this,R.layout.petition_staff_activity_important_object_detail);
        GetImportantPetitionPeopleDetail();
    }

    @Override
    protected void initEvent() {

    }

    private void GetImportantPetitionPeopleDetail() {
        TZjhm tZjhm=new TZjhm();
        tZjhm.setZJHM(getIntent().getStringExtra("zjhm"));
        System.out.println(GsonUtil.toJson(tZjhm));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetImportantPetitionPeopleDetail(GsonUtil.toJson(tZjhm))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RImportantPetitionPeopleDetail>>() {
                        }.getType(),
                        new APICallback<APIResult<RImportantPetitionPeopleDetail>>() {
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
                            public void onNext(APIResult<RImportantPetitionPeopleDetail> rImportantPetitionPeopleDetailAPIResult) {

                                if (rImportantPetitionPeopleDetailAPIResult.getData().getZJLX()!=null){
                                    mBinding.txtZjlx.setText(rImportantPetitionPeopleDetailAPIResult.getData().getZJLX());
                                }
                                if (rImportantPetitionPeopleDetailAPIResult.getData().getCSRQ()!=null){
                                    mBinding.txtCsrq.setText(rImportantPetitionPeopleDetailAPIResult.getData().getCSRQ());
                                }
                                if (rImportantPetitionPeopleDetailAPIResult.getData().getXB()!=null){
                                    mBinding.txtXb.setText(rImportantPetitionPeopleDetailAPIResult.getData().getXB());
                                }
                                if (rImportantPetitionPeopleDetailAPIResult.getData().getMZ()!=null){
                                    mBinding.txtMz.setText(rImportantPetitionPeopleDetailAPIResult.getData().getMZ());
                                }
                                if (rImportantPetitionPeopleDetailAPIResult.getData().getZT()!=null){
                                    mBinding.txtCyzt.setText(rImportantPetitionPeopleDetailAPIResult.getData().getZT());
                                }
                                if (rImportantPetitionPeopleDetailAPIResult.getData().getZZMM()!=null){
                                    mBinding.txtZzmm.setText(rImportantPetitionPeopleDetailAPIResult.getData().getZZMM());
                                }
                                if (rImportantPetitionPeopleDetailAPIResult.getData().getXM()!=null){
                                    mBinding.txtName.setText(rImportantPetitionPeopleDetailAPIResult.getData().getXM());
                                }
                                if (rImportantPetitionPeopleDetailAPIResult.getData().getDHHM()!=null){
                                    mBinding.txtDhhm.setText(rImportantPetitionPeopleDetailAPIResult.getData().getDHHM());
                                }
                                if (rImportantPetitionPeopleDetailAPIResult.getData().getZJHM()!=null){
                                    mBinding.txtZjhm.setText(rImportantPetitionPeopleDetailAPIResult.getData().getZJHM());
                                }
                                if (rImportantPetitionPeopleDetailAPIResult.getData().getXXDZ()!=null){
                                    mBinding.txtAddress.setText(rImportantPetitionPeopleDetailAPIResult.getData().getDQ()+rImportantPetitionPeopleDetailAPIResult.getData().getXXDZ());
                                }
                                if (rImportantPetitionPeopleDetailAPIResult.getData().getZPID()!=null){
                                    AoriseUtil.loadImage(PetitionStaffImportantPetitionPeopleDetailActivity.this,mBinding.img,
                                            cn.aorise.petition.staff.common.Utils.url
                                                    +rImportantPetitionPeopleDetailAPIResult.getData().getZPID(),R.drawable.petition_staff_header,
                                            R.drawable.petition_staff_header);

                                   /* Picasso.with(PetitionStaffChangePersonalInfoActivity.this).
                                            load(new File("http://192.168.1.40:1111/"+rPersonalInfoAPIResult.getData().getZP())).
                                            error(R.drawable.petition_staff_header).fit().centerCrop().
                                            into(mBinding.img);*/
                                }

                            }

                            @Override
                            public void onMock(APIResult<RImportantPetitionPeopleDetail> rImportantPetitionPeopleDetailAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }
}
