package cn.aorise.petition.staff.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.component.common.Utils;
import cn.aorise.common.core.module.glide.GlideManager;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.commenrefresh.LoadMoreListView;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityWorkWarningDetailBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TWorkWarningListDetail;
import cn.aorise.petition.staff.module.network.entity.request.TWorkWarningListt;
import cn.aorise.petition.staff.module.network.entity.response.RWorkWarningList;
import cn.aorise.petition.staff.module.network.entity.response.RWorkWarningListDetail;
import cn.aorise.petition.staff.ui.adapter.WorkWarningDetailContactPeopleAdapter;
import cn.aorise.petition.staff.ui.adapter.WorkWarningDetailOperateAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import cn.aorise.petition.staff.ui.bean.ContactpeopleInfo;
import cn.aorise.petition.staff.ui.bean.OperateInfo;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/16.
 */

public class PetitionStaffWorkWarningDetailActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityWorkWarningDetailBinding mBinding;
    private String bh = "", zjhm = "", xbsj = "";
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private String fj = "";

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.rl_left == id) {
            mBinding.rlLeft.setBackgroundResource(R.drawable.petition_staff_left_02);
            mBinding.txtLeft.setTextColor(this.getResources().getColor(R.color.petition_staff_0066ba));
            mBinding.rlMiddle01.setBackgroundResource(R.drawable.petition_staff_middle_01);
            mBinding.txtMiddle01.setTextColor(this.getResources().getColor(R.color.petition_staff_ffffff));
            mBinding.rlRight.setBackgroundResource(R.drawable.petition_staff_right_01);
            mBinding.txtRight.setTextColor(this.getResources().getColor(R.color.petition_staff_ffffff));
            mBinding.scrPetitionInfo.setVisibility(View.VISIBLE);
            mBinding.scrPetitionMatter.setVisibility(View.GONE);
            mBinding.scrPetitionOperation.setVisibility(View.GONE);

        } else if (R.id.rl_middle_01 == id) {
            mBinding.rlLeft.setBackgroundResource(R.drawable.petition_staff_left_01);
            mBinding.txtLeft.setTextColor(this.getResources().getColor(R.color.petition_staff_ffffff));
            mBinding.rlMiddle01.setBackgroundResource(R.drawable.petition_staff_middle_02);
            mBinding.txtMiddle01.setTextColor(this.getResources().getColor(R.color.petition_staff_0066ba));
            mBinding.rlRight.setBackgroundResource(R.drawable.petition_staff_right_01);
            mBinding.txtRight.setTextColor(this.getResources().getColor(R.color.petition_staff_ffffff));
            mBinding.scrPetitionInfo.setVisibility(View.GONE);
            mBinding.scrPetitionMatter.setVisibility(View.VISIBLE);
            mBinding.scrPetitionOperation.setVisibility(View.GONE);
        } else if (R.id.rl_right == id) {
            mBinding.rlLeft.setBackgroundResource(R.drawable.petition_staff_left_01);
            mBinding.txtLeft.setTextColor(this.getResources().getColor(R.color.petition_staff_ffffff));
            mBinding.rlMiddle01.setBackgroundResource(R.drawable.petition_staff_middle_01);
            mBinding.txtMiddle01.setTextColor(this.getResources().getColor(R.color.petition_staff_ffffff));
            mBinding.rlRight.setBackgroundResource(R.drawable.petition_staff_right_02);
            mBinding.txtRight.setTextColor(this.getResources().getColor(R.color.petition_staff_0066ba));
            mBinding.scrPetitionInfo.setVisibility(View.GONE);
            mBinding.scrPetitionMatter.setVisibility(View.GONE);
            mBinding.scrPetitionOperation.setVisibility(View.VISIBLE);
        } else if (R.id.rl_fj == id) {
            Intent intent = new Intent(PetitionStaffWorkWarningDetailActivity.this, PetitionStaffWorkWarningAdjunctActivity.class);
            intent.putExtra("fj", fj);
            startActivity(intent);
        } else if (R.id.rl_petition_return == id) {
            this.finish();
        } else if (R.id.rl_petition_right == id) {
            Intent intent = new Intent(this,TransPortActivity.class);
            intent.putExtra("bh",getIntent().getStringExtra("id"));
            startActivity(intent);
        }
    }

    @Override
    protected void initData() {
        bh = getIntent().getStringExtra("bh");
        zjhm = getIntent().getStringExtra("zjhm");
        xbsj = getIntent().getStringExtra("xbsj");
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this, R.color.petition_staff_0066ba);
        mBinding = DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_work_warning_detail);
        mBinding.rlLeft.setOnClickListener(this);
        mBinding.rlMiddle01.setOnClickListener(this);
        mBinding.rlRight.setOnClickListener(this);
        mBinding.rlFj.setOnClickListener(this);
        mBinding.rlPetitionReturn.setOnClickListener(this);
        mBinding.rlPetitionRight.setOnClickListener(this);
        GetWorkWarningListDetail(bh, zjhm);
    }

    @Override
    protected void initEvent() {

    }

    private void GetWorkWarningListDetail(String BH, String ZJHM) {
        TWorkWarningListDetail tAnalyzeOrgan = new TWorkWarningListDetail();
        tAnalyzeOrgan.setBH(BH);
        tAnalyzeOrgan.setZJHM(ZJHM);
        System.out.println(GsonUtil.toJson(tAnalyzeOrgan));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetWorkWarningListDetail(GsonUtil.toJson(tAnalyzeOrgan))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<RWorkWarningListDetail>>() {
                        }.getType(), new APICallback<APIResult<RWorkWarningListDetail>>() {
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
                            public void onNext(APIResult<RWorkWarningListDetail> rWorkWarningListDetailAPIResult) {
                                System.out.println("是否联名信"+rWorkWarningListDetailAPIResult.getData().getSFLMX());
                                if (rWorkWarningListDetailAPIResult.getData().getZJLX() != null) {
                                    mBinding.txtZjlx.setText(rWorkWarningListDetailAPIResult.getData().getZJLX());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getZJHM() != null) {
                                    mBinding.txtZjhm.setText(rWorkWarningListDetailAPIResult.getData().getZJHM());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getXM() != null) {
                                    mBinding.txtName.setText(rWorkWarningListDetailAPIResult.getData().getXM());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getZP() != null) {
                                    GlideManager.getInstance().load(PetitionStaffWorkWarningDetailActivity.this, mBinding.img
                                            , cn.aorise.petition.staff.common.Utils.url + rWorkWarningListDetailAPIResult.getData().getZP());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getCSRQ() != null) {
                                    mBinding.txtCsrq.setText(rWorkWarningListDetailAPIResult.getData().getCSRQ());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getXB() != null) {
                                    mBinding.txtXb.setText(rWorkWarningListDetailAPIResult.getData().getXB());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getMZ() != null) {
                                    mBinding.txtMz.setText(rWorkWarningListDetailAPIResult.getData().getMZ());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getDZ() != null) {
                                    mBinding.txtAddress.setText(rWorkWarningListDetailAPIResult.getData().getDZ());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getDHHM() != null) {
                                    mBinding.txtDhhm.setText(rWorkWarningListDetailAPIResult.getData().getDHHM());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getCYZK() != null) {
                                    mBinding.txtCyzt.setText(rWorkWarningListDetailAPIResult.getData().getCYZK());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getZZMM() != null) {
                                    mBinding.txtZzmm.setText(rWorkWarningListDetailAPIResult.getData().getZZMM());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFD() != null) {
                                    mBinding.txt01.setText(rWorkWarningListDetailAPIResult.getData().getSFD());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFDDZ() != null) {
                                    mBinding.txt02.setText(rWorkWarningListDetailAPIResult.getData().getSFDDZ());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getLX() != null) {
                                    mBinding.txt03.setText(rWorkWarningListDetailAPIResult.getData().getLX());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getXFMD() != null) {
                                    mBinding.txt04.setText(rWorkWarningListDetailAPIResult.getData().getXFMD());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getLY() != null) {
                                    mBinding.txt05.setText(rWorkWarningListDetailAPIResult.getData().getLY());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getLFRQ() != null) {
                                    mBinding.txt06.setText(rWorkWarningListDetailAPIResult.getData().getLFRQ());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getFFSJ() != null) {
                                    mBinding.txt07.setText(rWorkWarningListDetailAPIResult.getData().getFFSJ());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getCLQX() != null) {
                                    mBinding.txt08.setText(rWorkWarningListDetailAPIResult.getData().getCLQX());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getGK() != null) {
                                    mBinding.txt10.setText(rWorkWarningListDetailAPIResult.getData().getGK());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getNR() != null) {
                                    mBinding.txt11.setText(rWorkWarningListDetailAPIResult.getData().getNR());
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getFJ() != null) {
                                    fj = rWorkWarningListDetailAPIResult.getData().getFJ();
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFFH() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFFH().equals("1")) {
                                        mBinding.txt13.setText("是");
                                    } else {
                                        mBinding.txt13.setText("否");
                                    }
                                }else {
                                    mBinding.txt13.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getFYSFSL() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getFYSFSL().equals("1")) {
                                        mBinding.txt14.setText("是");
                                    } else {
                                        mBinding.txt14.setText("否");
                                    }
                                }else {
                                    mBinding.txt14.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFXZFY() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFXZFY().equals("1")) {
                                        mBinding.txt15.setText("是");
                                    } else {
                                        mBinding.txt15.setText("否");
                                    }
                                }else {
                                    mBinding.txt15.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getZCJGSFSL() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getZCJGSFSL().equals("1")) {
                                        mBinding.txt16.setText("是");
                                    } else {
                                        mBinding.txt16.setText("否");
                                    }
                                }else {
                                    mBinding.txt16.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFGK() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFGK().equals("1")) {
                                        mBinding.txt17.setText("是");
                                    } else {
                                        mBinding.txt17.setText("否");
                                    }
                                }else {
                                    mBinding.txt17.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFZDJJ() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFZDJJ().equals("1")) {
                                        mBinding.txt18.setText("是");
                                    } else {
                                        mBinding.txt18.setText("否");
                                    }
                                }else {
                                    mBinding.txt18.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFJA() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFJA().equals("1")) {
                                        mBinding.txt19.setText("是");
                                    } else {
                                        mBinding.txt19.setText("否");
                                    }
                                }else {
                                    mBinding.txt19.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFLMX() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFLMX().equals("1")) {
                                        mBinding.txt20.setText("是");
                                    } else {
                                        mBinding.txt20.setText("否");
                                    }
                                }else {
                                    mBinding.txt20.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFYY() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFYY().equals("1")) {
                                        mBinding.txt21.setText("是");
                                    } else {
                                        mBinding.txt21.setText("否");
                                    }
                                }else {
                                    mBinding.txt21.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFSS() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFSS().equals("1")) {
                                        mBinding.txt22.setText("是");
                                    } else {
                                        mBinding.txt22.setText("否");
                                    }
                                }else {
                                    mBinding.txt22.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFSG() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFSS().equals("1")) {
                                        mBinding.txt23.setText("是");
                                    } else {
                                        mBinding.txt23.setText("否");
                                    }
                                }else {
                                    mBinding.txt23.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFSA() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFSA().equals("1")) {
                                        mBinding.txt24.setText("是");
                                    } else {
                                        mBinding.txt24.setText("否");
                                    }
                                }else {
                                    mBinding.txt24.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFST() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFST().equals("1")) {
                                        mBinding.txt25.setText("是");
                                    } else {
                                        mBinding.txt25.setText("否");
                                    }
                                }else {
                                    mBinding.txt25.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFSW() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFSW().equals("1")) {
                                        mBinding.txt26.setText("是");
                                    } else {
                                        mBinding.txt26.setText("否");
                                    }
                                }else {
                                    mBinding.txt26.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getSFSQ() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFSQ().equals("1")) {
                                        mBinding.txt27.setText("是");
                                    } else {
                                        mBinding.txt27.setText("否");
                                    }
                                }else {
                                    mBinding.txt27.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getCFF() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getCFF().equals("1")) {
                                        mBinding.txt28.setText("是");
                                    } else {
                                        mBinding.txt28.setText("否");
                                    }
                                }else {
                                    mBinding.txt28.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getYJF() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getYJF().equals("1")) {
                                        mBinding.txt29.setText("是");
                                    } else {
                                        mBinding.txt29.setText("否");
                                    }
                                }else {
                                    mBinding.txt29.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getFZCSF() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getFZCSF().equals("1")) {
                                        mBinding.txt30.setText("是");
                                    } else {
                                        mBinding.txt30.setText("否");
                                    }
                                }else {
                                    mBinding.txt30.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getYZFWDJ() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getYZFWDJ().equals("1")) {
                                        mBinding.txt31.setText("是");
                                    } else {
                                        mBinding.txt31.setText("否");
                                    }
                                }else {
                                    mBinding.txt31.setText("否");
                                }

                                if (rWorkWarningListDetailAPIResult.getData().getSFJYLC() != null) {
                                    if (rWorkWarningListDetailAPIResult.getData().getSFJYLC().equals("1")) {
                                        mBinding.txt32.setText("是");
                                    } else {
                                        mBinding.txt32.setText("否");
                                    }
                                }else {
                                    mBinding.txt32.setText("否");
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getPeopleAll() != null) {
                                    List<ContactpeopleInfo> data = new ArrayList<ContactpeopleInfo>();
                                    for (int i = 0; i < rWorkWarningListDetailAPIResult.getData().getPeopleAll().size(); i++) {
                                        data.add(rWorkWarningListDetailAPIResult.getData().getPeopleAll().get(i));
                                    }
                                    WorkWarningDetailContactPeopleAdapter adapter = new WorkWarningDetailContactPeopleAdapter(data, PetitionStaffWorkWarningDetailActivity.this);
                                    mBinding.mylist.setAdapter(adapter);
                                }
                                if (rWorkWarningListDetailAPIResult.getData().getOperation() != null) {
                                    List<OperateInfo> data = new ArrayList<OperateInfo>();
                                    for (int i = 0; i < rWorkWarningListDetailAPIResult.getData().getOperation().size(); i++) {
                                        data.add(rWorkWarningListDetailAPIResult.getData().getOperation().get(i));
                                    }
                                    WorkWarningDetailOperateAdapter adapter = new WorkWarningDetailOperateAdapter(data, PetitionStaffWorkWarningDetailActivity.this);
                                    mBinding.operateListview.setAdapter(adapter);

                                }
                                mBinding.txt08.setText(xbsj);
                                if (rWorkWarningListDetailAPIResult.getData().getJTR() != null) {
                                    mBinding.txt09.setText(rWorkWarningListDetailAPIResult.getData().getJTR());
                                }

                                mBinding.txtContactPeople.setText(rWorkWarningListDetailAPIResult.getData().getPeopleAll().size() + "人");


                            }

                            @Override
                            public void onMock(APIResult<RWorkWarningListDetail> rWorkWarningListDetailAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }
}
