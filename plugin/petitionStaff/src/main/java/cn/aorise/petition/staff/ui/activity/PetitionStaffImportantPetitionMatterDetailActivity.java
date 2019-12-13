package cn.aorise.petition.staff.ui.activity;

import android.content.Intent;
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
import cn.aorise.petition.staff.databinding.PetitionStaffActivityImportantPetitionMatterDetailBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TImportantPetitionMatter;
import cn.aorise.petition.staff.module.network.entity.request.TLetterId;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionMatter;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionMatterDetail;
import cn.aorise.petition.staff.ui.adapter.ImportantPetitionMatterDetailPeopleAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/1.
 */

public class PetitionStaffImportantPetitionMatterDetailActivity extends PetitionStaffBaseActivity implements View.OnClickListener{
    private PetitionStaffActivityImportantPetitionMatterDetailBinding mBinding;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_important_petition_matter_detail);

        //将视图锁定在viewpager
        mBinding.txtBh.setFocusable(true);
        mBinding.txtBh.setFocusableInTouchMode(true);
        mBinding.txtBh.requestFocus();
        GetImportantPetitionMatterDetail();
    }

    @Override
    protected void initEvent() {

    }

    private void GetImportantPetitionMatterDetail() {
        TLetterId tLetterId=new TLetterId();
        tLetterId.setLetterID(getIntent().getStringExtra("letterId"));
        System.out.println(GsonUtil.toJson(tLetterId));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetImportantPetitionMatterDetail(GsonUtil.toJson(tLetterId))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RImportantPetitionMatterDetail>>() {
                        }.getType(), new APICallback<APIResult<RImportantPetitionMatterDetail>>() {
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
                            public void onNext(final APIResult<RImportantPetitionMatterDetail> rImportantPetitionMatterDetailAPIResult) {
                                    mBinding.txtBh.setText(getIntent().getStringExtra("letterId"));
                                if (rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getXM()!=null){
                                    mBinding.txtXm.setText(rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getXM());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getDHHM()!=null){
                                    mBinding.txtDhhm.setText(rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getDHHM());
                                }

                                if (rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getXXDZ()!=null){
                                        mBinding.txtDz.setText(
                                        rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getXXDZ());
                                    }

                                if (rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getZJLX()!=null){
                                        mBinding.txtZjlx.setText(rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getZJLX());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getZJHM()!=null){
                                        mBinding.txtZjhm.setText(rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getZJHM());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getCSRQ()!=null){
                                        mBinding.txtCsrq.setText(rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getCSRQ());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getXB()!=null){
                                        mBinding.txtXb.setText(rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getXB());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getMZ()!=null){
                                        mBinding.txtMz.setText(rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getMZ());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getCYZK()!=null){
                                        mBinding.txtCyzt.setText(rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getCYZK());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getZZMM()!=null){
                                        mBinding.txtZzmm.setText(rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getZZMM());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getJB()!=null){
                                        mBinding.txtJb.setText(rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getJB());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFD()!=null){
                                        mBinding.txtSfd.setText(rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFD());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getWTLX()!=null){
                                        mBinding.txtWtlx.setText(rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getWTLX());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFFH()!=null){
                                        if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFFH().equals("0")){
                                            mBinding.txtSffh.setText("否");
                                        }else {
                                            mBinding.txtSffh.setText("是");
                                        }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getFYSFSL()!=null){
                                        if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getFYSFSL().equals("0")){
                                            mBinding.txtFysfsl.setText("否");
                                        } else {
                                            mBinding.txtFysfsl.setText("是");
                                        }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFXZFY()!=null){
                                        if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFXZFY().equals("0")){
                                            mBinding.txtXzyf.setText("否");
                                        }else {
                                            mBinding.txtXzyf.setText("是");
                                        }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getZCJGSFSL()!=null){
                                        if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getZCJGSFSL().equals("0")){
                                            mBinding.txtZcjg.setText("否");
                                        } else {
                                            mBinding.txtZcjg.setText("是");
                                        }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFGK()!=null){
                                        if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFGK().equals("0")){
                                            mBinding.txtSfgk.setText("否");
                                        } else {
                                            mBinding.txtSfgk.setText("是");
                                        }
                                }


                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFZDJJ()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFZDJJ().equals("0")){
                                        mBinding.txt18.setText("否");
                                    } else {
                                        mBinding.txt18.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFJA()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFJA().equals("0")){
                                        mBinding.txt19.setText("否");
                                    } else {
                                        mBinding.txt19.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFLMX()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFLMX().equals("0")){
                                        mBinding.txt20.setText("否");
                                    } else {
                                        mBinding.txt20.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFYY()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFYY().equals("0")){
                                        mBinding.txt21.setText("否");
                                    } else {
                                        mBinding.txt21.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFSSBZ()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFSSBZ().equals("0")){
                                        mBinding.txt22.setText("否");
                                    } else {
                                        mBinding.txt22.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSGBZ()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSGBZ().equals("0")){
                                        mBinding.txt23.setText("否");
                                    } else {
                                        mBinding.txt23.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSABZ()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSABZ().equals("0")){
                                        mBinding.txt24.setText("否");
                                    } else {
                                        mBinding.txt24.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSTBZ()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSTBZ().equals("0")){
                                        mBinding.txt25.setText("否");
                                    } else {
                                        mBinding.txt25.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSWBZ()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSWBZ().equals("0")){
                                        mBinding.txt26.setText("否");
                                    } else {
                                        mBinding.txt26.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSQBZ()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSQBZ().equals("0")){
                                        mBinding.txt27.setText("否");
                                    } else {
                                        mBinding.txt27.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getCFF()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getCFF().equals("0")){
                                        mBinding.txt28.setText("否");
                                    } else {
                                        mBinding.txt28.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getYJF()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getYJF().equals("0")){
                                        mBinding.txt29.setText("否");
                                    } else {
                                        mBinding.txt29.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getFZCSF()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getFZCSF().equals("0")){
                                        mBinding.txt30.setText("否");
                                    } else {
                                        mBinding.txt30.setText("是");
                                    }
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getYZFWDJ()!=null){
                                    if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getYZFWDJ().equals("0")){
                                        mBinding.txt31.setText("否");
                                    } else {
                                        mBinding.txt31.setText("是");
                                    }
                                }

                                if (rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getZPID()!=null){
                                    AoriseUtil.loadImage(PetitionStaffImportantPetitionMatterDetailActivity.this,mBinding.img,
                                            cn.aorise.petition.staff.common.Utils.url
                                                    +rImportantPetitionMatterDetailAPIResult.getData().getComplainEntity().getZPID(),R.drawable.petition_staff_header,
                                            R.drawable.petition_staff_header);

                                   /* Picasso.with(PetitionStaffChangePersonalInfoActivity.this).
                                            load(new File("http://192.168.1.40:1111/"+rPersonalInfoAPIResult.getData().getZP())).
                                            error(R.drawable.petition_staff_header).fit().centerCrop().
                                            into(mBinding.img);*/
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getLY()!=null) {
                                    mBinding.txtXfxs.setText(rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getLY());
                                }

                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getNR()!=null){
                                    mBinding.txtNr.setText(rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getNR());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFDDZ()!=null){
                                    mBinding.txtXxdz.setText(rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getSFDDZ());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getXFMD()!=null){
                                    mBinding.txtXfmd.setText(rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getXFMD());
                                }
                                if (rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getGK()!=null){
                                    mBinding.txtGk.setText(rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getGK());
                                }
                                mBinding.txtLmrnum.setText(rImportantPetitionMatterDetailAPIResult.getData().getPeopleAll().size()+"人");
                                ImportantPetitionMatterDetailPeopleAdapter adapter=new ImportantPetitionMatterDetailPeopleAdapter(rImportantPetitionMatterDetailAPIResult.getData().getPeopleAll(),
                                        PetitionStaffImportantPetitionMatterDetailActivity.this);
                                mBinding.listviewPeople.setAdapter(adapter);
                                mBinding.rlFj.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent =new Intent(PetitionStaffImportantPetitionMatterDetailActivity.this,PetitionStaffWorkWarningAdjunctActivity.class);
                                        intent.putExtra("fj",rImportantPetitionMatterDetailAPIResult.getData().getPetitionEntity().getFJ());
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onMock(APIResult<RImportantPetitionMatterDetail> rImportantPetitionMatterDetailAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }
}
