package cn.aorise.petition.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.R;
import cn.aorise.petition.common.Utils;
import cn.aorise.petition.common.popupwindow.Popuwindow_evaluate_type;
import cn.aorise.petition.common.popupwindow.Popuwindow_evaluate_type01;
import cn.aorise.petition.databinding.PetitionActivityQueryEvaluationDetailBinding;
import cn.aorise.petition.module.network.Mock;
import cn.aorise.petition.module.network.PetitionApiService;

import cn.aorise.petition.module.network.entity.request.TAddEvaluate;
import cn.aorise.petition.module.network.entity.request.TQueryEvaluateDetail;
import cn.aorise.petition.module.network.entity.response.RQueryEvaluate;
import cn.aorise.petition.module.network.entity.response.RQueryEvaluateDetail;
import cn.aorise.petition.module.network.entity.response.RRegister;
import cn.aorise.petition.ui.adapter.QueryEvaluateListAdapter;
import cn.aorise.petition.ui.base.PetitionBaseActivity;
import cn.aorise.petition.ui.bean.Petition_contact_people;
import cn.aorise.petition.ui.bean.QueryEvaluateListInfo;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/2.
 */

public class QueryEvaluateDetailActivity extends PetitionBaseActivity implements View.OnClickListener {
    private PetitionActivityQueryEvaluationDetailBinding mBinding;
    private RQueryEvaluateDetail rQueryEvaluateDetail=new RQueryEvaluateDetail();
    private static final String TAG = AboutActivity.class.getSimpleName();
    private List<QueryEvaluateListInfo> data=new ArrayList<>();
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private List<Petition_contact_people> peoples=new ArrayList<>();
    private String adjunctList;
    private Popuwindow_evaluate_type popuwindow_evaluate_type01;
    private Popuwindow_evaluate_type01 popuwindow_evaluate_type02;
    private String ZT="1";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getQueryValuate(getIntent().getStringExtra("BH"));
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_left==id) {
            mBinding.rlLeft.setBackgroundResource(R.drawable.petition_left_02);
            mBinding.txtLeft.setTextColor(this.getResources().getColor(R.color.petition_status_color));
            mBinding.rlMiddle.setBackgroundResource(R.drawable.petition_middle_01);
            mBinding.txtMiddle.setTextColor(this.getResources().getColor(R.color.petition_ffffff));
            mBinding.rlRight.setBackgroundResource(R.drawable.petition_right_01);
            mBinding.txtRight.setTextColor(this.getResources().getColor(R.color.petition_ffffff));
            mBinding.llPetitionLetterDetail.setVisibility(View.VISIBLE);
            mBinding.llOperationInfo.setVisibility(View.GONE);
            mBinding.llEvaluationDetail.setVisibility(View.GONE);
        } else if (R.id.rl_middle==id) {
            mBinding.rlLeft.setBackgroundResource(R.drawable.petition_left_01);
            mBinding.txtLeft.setTextColor(this.getResources().getColor(R.color.petition_ffffff));
            mBinding.rlMiddle.setBackgroundResource(R.drawable.petition_middle_02);
            mBinding.txtMiddle.setTextColor(this.getResources().getColor(R.color.petition_status_color));
            mBinding.rlRight.setBackgroundResource(R.drawable.petition_right_01);
            mBinding.txtRight.setTextColor(this.getResources().getColor(R.color.petition_ffffff));
            mBinding.llPetitionLetterDetail.setVisibility(View.GONE);
            mBinding.llOperationInfo.setVisibility(View.VISIBLE);
            mBinding.llEvaluationDetail.setVisibility(View.GONE);
        } else if (R.id.rl_right==id) {
            mBinding.rlLeft.setBackgroundResource(R.drawable.petition_left_01);
            mBinding.txtLeft.setTextColor(this.getResources().getColor(R.color.petition_ffffff));
            mBinding.rlMiddle.setBackgroundResource(R.drawable.petition_middle_01);
            mBinding.txtMiddle.setTextColor(this.getResources().getColor(R.color.petition_ffffff));
            mBinding.rlRight.setBackgroundResource(R.drawable.petition_right_02);
            mBinding.txtRight.setTextColor(this.getResources().getColor(R.color.petition_status_color));
            mBinding.llPetitionLetterDetail.setVisibility(View.GONE);
            mBinding.llOperationInfo.setVisibility(View.GONE);
            mBinding.llEvaluationDetail.setVisibility(View.VISIBLE);
        } else if(R.id.rl_contact_people==id) {
            Intent intent=new Intent(QueryEvaluateDetailActivity.this,RequestContactListActivity.class);
            editor.putString(getString(R.string.petition_shareprefers_save_list),GsonUtil.toJson(peoples));
            editor.commit();
            intent.putExtra(getString(R.string.petition_activity_to_activity_number),"1");
            startActivity(intent);
        } else if (R.id.rl_content==id) {
            Intent intent=new Intent(QueryEvaluateDetailActivity.this,RequestFillContentActivity.class);
            intent.putExtra(getString(R.string.petition_activity_to_activity_number),"1");
            startActivity(intent);
        } else if (R.id.rl_adjunct==id) {

            Intent intent=new Intent(QueryEvaluateDetailActivity.this,QueryEvaluateDetailAdjunctActivity.class);
            intent.putExtra("fj",adjunctList);
            startActivity(intent);
        } else if (R.id.rl_type_01==id) {
            backgroundAlpha(0.3f);
            //实例化SelectPicPopupWindow
            popuwindow_evaluate_type01 = new Popuwindow_evaluate_type(this,this);
            popuwindow_evaluate_type01.setOnDismissListener(new poponDismissListener());
            //显示窗口
            popuwindow_evaluate_type01.showAtLocation(this.findViewById(R.id.txt_30), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        } else if (R.id.rl_type_02==id) {
            backgroundAlpha(0.3f);
            //实例化SelectPicPopupWindow
            popuwindow_evaluate_type02 = new Popuwindow_evaluate_type01(this,this);
            popuwindow_evaluate_type02.setOnDismissListener(new poponDismissListener());
            //显示窗口
            popuwindow_evaluate_type02.showAtLocation(this.findViewById(R.id.txt_30), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        } else if (R.id.btn_1==id) {
            popuwindow_evaluate_type01.dismiss();
            backgroundAlpha(1f);
            mBinding.txtEvaluateType.setText("满意");
        } else if (R.id.btn_2==id) {
            popuwindow_evaluate_type01.dismiss();
            backgroundAlpha(1f);
            mBinding.txtEvaluateType.setText("基本满意");
        }
        else if (R.id.btn_3==id) {
            popuwindow_evaluate_type01.dismiss();
            backgroundAlpha(1f);
            mBinding.txtEvaluateType.setText("不满意");
        }
        else if (R.id.btn_4==id) {
            popuwindow_evaluate_type01.dismiss();
            backgroundAlpha(1f);
            mBinding.txtEvaluateType.setText("非常不满意");
        }else if (R.id.btn_01==id) {
            popuwindow_evaluate_type02.dismiss();
            backgroundAlpha(1f);
            mBinding.txtEvaluateType1.setText("满意");
        } else if (R.id.btn_02==id) {
            popuwindow_evaluate_type02.dismiss();
            backgroundAlpha(1f);
            mBinding.txtEvaluateType1.setText("基本满意");
        }
        else if (R.id.btn_03==id) {
            popuwindow_evaluate_type02.dismiss();
            backgroundAlpha(1f);
            mBinding.txtEvaluateType1.setText("不满意");
        }
        else if (R.id.btn_04==id) {
            popuwindow_evaluate_type02.dismiss();
            backgroundAlpha(1f);
            mBinding.txtEvaluateType1.setText("非常不满意");
        } else if (R.id.rl_submit==id) {
            if (ZT.equals("1")){

            } else {
                if (!mBinding.txtEvaluateContent.getText().toString().trim().equals("")&&!mBinding.txtEvaluateContent1.getText().toString().trim().equals("")
                        &&!mBinding.txtEvaluateType1.getText().equals("")&&!mBinding.txtEvaluateType.getText().equals("")){
                    Addevaluate();
                } else {
                    showToast(getString(R.string.petition_toast_01));
                }
            }
        }
    }

    @Override
    protected void initData() {
        sp=getSharedPreferences(getString(R.string.petition_sharepre_nomal),MODE_PRIVATE);
        editor=sp.edit();

    }

    @Override
    protected void initView() {
        mBinding= DataBindingUtil.setContentView(this,R.layout.petition_activity_query_evaluation_detail);
        ZT= getIntent().getStringExtra("ZT");
        mBinding.rlLeft.setOnClickListener(this);
        mBinding.rlMiddle.setOnClickListener(this);
        mBinding.rlRight.setOnClickListener(this);
        mBinding.rlContactPeople.setOnClickListener(this);
        mBinding.rlContent.setOnClickListener(this);
        mBinding.rlAdjunct.setOnClickListener(this);
        mBinding.rlType01.setOnClickListener(this);
        mBinding.rlType02.setOnClickListener(this);
        mBinding.rlSubmit.setOnClickListener(this);
        if (ZT.equals("1")){
            mBinding.textView6.setText("已评价");
            mBinding.rlSubmit.setEnabled(false);
            mBinding.rlType01.setEnabled(false);
            mBinding.rlType02.setEnabled(false);
            mBinding.txtEvaluateContent.setEnabled(false);
            mBinding.txtEvaluateContent1.setEnabled(false);

        } else {
            mBinding.textView6.setText("评价");

        }
    }

    @Override
    protected void initEvent() {

    }

    private void getQueryValuate(String BH){
        final TQueryEvaluateDetail tQueryEvaluateDetail=new TQueryEvaluateDetail();
        tQueryEvaluateDetail.setBH(BH);
        System.out.println(GsonUtil.toJson(tQueryEvaluateDetail));
        Subscription subscription = PetitionApiService.Factory.create().
                GetQueryEvaluateDetail(GsonUtil.toJson(tQueryEvaluateDetail))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_QUESTION_TYPE,
                        new TypeToken<APIResult<RQueryEvaluateDetail>>() {
                        }.getType(), new APICallback<APIResult<RQueryEvaluateDetail>>() {
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
                            public void onNext(APIResult<RQueryEvaluateDetail> rQueryEvaluateDetailAPIResult) {
                                rQueryEvaluateDetail=rQueryEvaluateDetailAPIResult.getData();
                                System.out.println(rQueryEvaluateDetailAPIResult.toString());
                                if (rQueryEvaluateDetailAPIResult.getData().getFJ()==null){
                                    adjunctList="";
                                } else {
                                    adjunctList = rQueryEvaluateDetailAPIResult.getData().getFJ();
                                }
                                System.out.println("adjunct:"+adjunctList);
                                setView();
                            }

                            @Override
                            public void onMock(APIResult<RQueryEvaluateDetail> rQueryEvaluateDetailAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    private void Addevaluate(){
        TAddEvaluate tAddEvaluate=new TAddEvaluate();
        tAddEvaluate.setBMZT("1");
        tAddEvaluate.setSXZT("1");
        tAddEvaluate.setXFID(getIntent().getStringExtra("BH"));
        tAddEvaluate.setBMJG(mBinding.txtEvaluateType.getText().toString());
        tAddEvaluate.setBMBZ(mBinding.txtEvaluateContent.getText().toString());
        tAddEvaluate.setSXJG(mBinding.txtEvaluateType1.getText().toString());
        tAddEvaluate.setSXBZ(mBinding.txtEvaluateContent1.getText().toString());
        System.out.println(GsonUtil.toJson(tAddEvaluate));

        Subscription subscription = PetitionApiService.Factory.create().
                AddEvaluate(GsonUtil.toJson(tAddEvaluate))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_QUESTION_TYPE,
                        new TypeToken<APIResult<RRegister>>() {
                        }.getType(), new APICallback<APIResult<RRegister>>() {
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
                            public void onNext(APIResult<RRegister> rRegisterAPIResult) {
                                showToast(rRegisterAPIResult.getMsg());
                                if (rRegisterAPIResult.getMsg().equals("修改成功")){
                                    QueryEvaluateDetailActivity.this.finish();
                                }
                            }

                            @Override
                            public void onMock(APIResult<RRegister> rRegisterAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    private void setView(){
        mBinding.txtAddress.setText(rQueryEvaluateDetail.getSFD());
        mBinding.txtQuestType.setText(rQueryEvaluateDetail.getWTLX());
        if (rQueryEvaluateDetail.getSFDDZ()!=null) {
            mBinding.txtSfddz.setText(rQueryEvaluateDetail.getSFDDZ());
        }
        if (rQueryEvaluateDetail.getSFFH().equals("1")){
            mBinding.txtSFFH.setText("是");
        } else {
            mBinding.txtSFFH.setText("否");
        }
        if (rQueryEvaluateDetail.getFYSFSL().equals("1")){
            mBinding.txtFYSFSL.setText("是");
        }else {
            mBinding.txtFYSFSL.setText("否");
        }
        if (rQueryEvaluateDetail.getSFXZFY().equals("1")){
            mBinding.txtXZSFFY.setText("是");
        }else {
            mBinding.txtXZSFFY.setText("否");
        }if (rQueryEvaluateDetail.getZCJGSFSL().equals("1")){
            mBinding.txtZCJGSFSL.setText("是");
        }else {
            mBinding.txtZCJGSFSL.setText("否");
        }if (rQueryEvaluateDetail.getSFGK().equals("1")){
            mBinding.txtSFGK.setText("是");
        } else {
            mBinding.txtSFGK.setText("否");
        }
        System.out.println(rQueryEvaluateDetail.getPeopleAll());
        if (rQueryEvaluateDetail.getPeopleAll()==null){
            mBinding.txtContactNumber.setText("0人");
        }else {
            mBinding.txtContactNumber.setText(rQueryEvaluateDetail.getPeopleAll().size() + "人");
        }
        mBinding.txtContent.setText(rQueryEvaluateDetail.getNR());
        if (rQueryEvaluateDetail.getPingjia()!=null) {
            mBinding.txtEvaluateType.setText(rQueryEvaluateDetail.getPingjia().getBMJG());
            mBinding.txtEvaluateContent.setText(rQueryEvaluateDetail.getPingjia().getBMBZ());
            mBinding.txtEvaluateType1.setText(rQueryEvaluateDetail.getPingjia().getSXJG());
            mBinding.txtEvaluateContent1.setText(rQueryEvaluateDetail.getPingjia().getSXBZ());
        }
        /*if (rQueryEvaluateDetail.getOpretion().size()==0){
            QueryEvaluateListInfo queryEvaluateListInfo=new QueryEvaluateListInfo();
            queryEvaluateListInfo.setJBJG("经办机构");
            queryEvaluateListInfo.setJBRY("经办人员");
            queryEvaluateListInfo.setCZLX("操作类型");
            queryEvaluateListInfo.setBLSC("办理时限");
            queryEvaluateListInfo.setCZSJ("开始办理时间");
            data.add(queryEvaluateListInfo);

        } else {*/
        QueryEvaluateListInfo info=new QueryEvaluateListInfo();
        info.setJBJG(getString(R.string.petition_JBJG));
        info.setJBRY(getString(R.string.petition_JBRY));
        info.setCZSJ(getString(R.string.petition_BLSJ));
        info.setBLSC(getString(R.string.petition_BLSX));
        info.setCZLX(getString(R.string.petition_CZLX));
        data.add(info);
            for (int i = 0; i < rQueryEvaluateDetail.getOpretion().size(); i++) {
                data.add(rQueryEvaluateDetail.getOpretion().get(i));
            /*}*/
        }

        System.out.println("操作信息："+data);
        QueryEvaluateListAdapter queryEvaluateListAdapter=new QueryEvaluateListAdapter(data,this);
        queryEvaluateListAdapter.notifyDataSetChanged();
        mBinding.listView.setAdapter(queryEvaluateListAdapter);
        for (int i=0;i<rQueryEvaluateDetail.getPeopleAll().size();i++) {
            Petition_contact_people people=new Petition_contact_people();
            people.setXM(rQueryEvaluateDetail.getPeopleAll().get(i).getXM());
            people.setZJHM(rQueryEvaluateDetail.getPeopleAll().get(i).getZJHM());
            people.setDZ(rQueryEvaluateDetail.getPeopleAll().get(i).getDZ());
            people.setQY_SH(rQueryEvaluateDetail.getPeopleAll().get(i).getQY_SH());
            people.setQY_S(rQueryEvaluateDetail.getPeopleAll().get(i).getQY_S());
            people.setQY_X(rQueryEvaluateDetail.getPeopleAll().get(i).getQY_X());
            people.setQY_SHDM(rQueryEvaluateDetail.getPeopleAll().get(i).getQY_SHDM());
            people.setQY_SDM(rQueryEvaluateDetail.getPeopleAll().get(i).getQY_SDM());
            people.setQY_XDM(rQueryEvaluateDetail.getPeopleAll().get(i).getQY_XDM());
            peoples.add(people);
        }
        editor.putString(getString(R.string.petition_shareprefers_request_content),rQueryEvaluateDetail.getNR());
        editor.commit();

    }

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }


}
