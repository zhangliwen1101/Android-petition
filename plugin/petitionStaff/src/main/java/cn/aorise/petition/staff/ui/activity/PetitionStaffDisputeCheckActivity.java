package cn.aorise.petition.staff.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.component.common.Utils;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.commenrefresh.LoadMoreListView;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityDisputeCheckBinding;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityImportantPetitionMatterBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TImportantPetitionMatter;
import cn.aorise.petition.staff.module.network.entity.response.RDisputeCheck;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionMatter;
import cn.aorise.petition.staff.module.network.entity.response.RPetitionType;
import cn.aorise.petition.staff.ui.adapter.DisputeCheckAdapter;
import cn.aorise.petition.staff.ui.adapter.ImportantPetitionMatterAdapter;
import cn.aorise.petition.staff.ui.adapter.PetitionLevelAdapter;
import cn.aorise.petition.staff.ui.adapter.PetitionTypeAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2017/6/1.
 */

public class PetitionStaffDisputeCheckActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityDisputeCheckBinding mBinding;
    private List<RPetitionType> typeData=new ArrayList<>();
    private List<RPetitionType> levelData=new ArrayList<>();
    private PetitionTypeAdapter typeAdapter;
    private PetitionLevelAdapter levelAdapter;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private DisputeCheckAdapter mAdapter;
    private List<RDisputeCheck> data=new ArrayList<>();
    private int CurrentPage=1,PageSize=20;
    private String Wherestr="";//1为已评价，0为未评价
    private String strType,strLevel;
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_petition_return==id) {
            PetitionStaffDisputeCheckActivity.this.finish();
        } else if (R.id.rl_petition_right==id) {
            mBinding.rlSearch.setVisibility(View.VISIBLE);
            mBinding.llToolbar.setVisibility(GONE);
        } else if (R.id.rl_cancle==id) {
            mBinding.edtSearch.setText("");
            mBinding.rlSearch.setVisibility(GONE);
            mBinding.llToolbar.setVisibility(View.VISIBLE);
        } else if (R.id.img_clear==id) {
            mBinding.edtSearch.setText("");
        } else if (R.id.rl_type==id) {
            mBinding.txtSex.setTextColor(this.getResources().getColor(R.color.petition_staff_565656));
            mBinding.imgSex.setImageResource(R.drawable.petition_staff_grey_sanjiao);
            mBinding.listviewSex.setVisibility(GONE);
            if (mBinding.listviewType.getVisibility()==View.VISIBLE){
                mBinding.listviewType.setVisibility(GONE);
                mBinding.txtType.setTextColor(this.getResources().getColor(R.color.petition_staff_565656));
                mBinding.imgType.setImageResource(R.drawable.petition_staff_grey_sanjiao);
            } else {
                mBinding.listviewType.setVisibility(View.VISIBLE);
                mBinding.txtType.setTextColor(this.getResources().getColor(R.color.petition_staff_0066ba));
                mBinding.imgType.setImageResource(R.drawable.petition_staff_blue_sanjiao);
            }
        } else if (R.id.rl_sex==id) {
            mBinding.txtType.setTextColor(this.getResources().getColor(R.color.petition_staff_565656));
            mBinding.imgType.setImageResource(R.drawable.petition_staff_grey_sanjiao);
            mBinding.listviewType.setVisibility(GONE);
            if (mBinding.listviewSex.getVisibility()==View.VISIBLE){
                mBinding.listviewSex.setVisibility(GONE);
                mBinding.txtSex.setTextColor(this.getResources().getColor(R.color.petition_staff_565656));
                mBinding.imgSex.setImageResource(R.drawable.petition_staff_grey_sanjiao);
            } else {
                mBinding.listviewSex.setVisibility(View.VISIBLE);
                mBinding.txtSex.setTextColor(this.getResources().getColor(R.color.petition_staff_0066ba));
                mBinding.imgSex.setImageResource(R.drawable.petition_staff_blue_sanjiao);
            }
        }

    }

    @Override
    protected void initData() {
        mAdapter=new DisputeCheckAdapter(data,this);
        typeAdapter=new PetitionTypeAdapter(typeData,this);
        levelAdapter=new PetitionLevelAdapter(levelData,this);

        GetImportantPetitionMatter(1,PageSize,"","","");
        GetPetitionLevel();
        GetPetitionType();
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_dispute_check);
        mBinding.rlPetitionReturn.setOnClickListener(this);
        mBinding.rlPetitionRight.setOnClickListener(this);
        mBinding.rlCancle.setOnClickListener(this);
        mBinding.imgClear.setOnClickListener(this);
        mBinding.rlType.setOnClickListener(this);
        mBinding.rlSex.setOnClickListener(this);

        SetTypeOrLevelView();
        mBinding.listView.setRefreshAndLoadMoreView(mBinding.refresh);
        mBinding.listView.setAdapter(mAdapter);
        mBinding.listView.onLoadComplete();//结束下拉加载更多转动条
        mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(PetitionStaffDisputeCheckActivity.this,PetitionStaffDisputeCheckDetailActivity.class);
                intent.putExtra("lx",data.get(position).getJFLX());
                intent.putExtra("jb",data.get(position).getJFJB());
                intent.putExtra("nr",data.get(position).getJFNR());
                intent.putExtra("dz",data.get(position).getJFDZ());
                intent.putExtra("sfd",data.get(position).getSFD());
                startActivity(intent);
            }
        });

        mBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CurrentPage=1;
                getTxtTypeLevel();
                GetImportantPetitionMatter(1,PageSize,mBinding.edtSearch.getText().toString(),strLevel,strType);
            }
        });

        mBinding.listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getTxtTypeLevel();
                GetImportantPetitionMatter(1,PageSize*CurrentPage,mBinding.edtSearch.getText().toString(),strLevel,strType);
                mBinding.refresh.setRefreshing(true);
            }
        });

        SearchListener();
        mBinding.edtSearch.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER){//修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    PetitionStaffDisputeCheckActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    //跳转到搜索结果界面
                    mBinding.llToolbar.setVisibility(View.VISIBLE);
                    mBinding.rlSearch.setVisibility(GONE);

                }
                return false;
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    private void SearchListener() {
        mBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /*GetGuideRules(GGLX,s.toString(),1,pageSize);*/
                getTxtTypeLevel();
                GetImportantPetitionMatter(1,PageSize,s.toString(),strLevel,strType);
            }
        });
    }

    private void GetImportantPetitionMatter(int currentPage,int pageSize,String whereStr,String JB,String LXY) {
        TImportantPetitionMatter tImportantPetitionMatter=new TImportantPetitionMatter();
        tImportantPetitionMatter.setCurrentPage(currentPage);
        tImportantPetitionMatter.setPageSize(pageSize);
        tImportantPetitionMatter.setWhereStr(whereStr);
        tImportantPetitionMatter.setJB(JB);
        tImportantPetitionMatter.setLXY(LXY);
        System.out.println(GsonUtil.toJson(tImportantPetitionMatter));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetDisputeCheck(GsonUtil.toJson(tImportantPetitionMatter))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RDisputeCheck>>>() {
                        }.getType(), new APICallback<APIResult<List<RDisputeCheck>>>() {
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
                            public void onNext(APIResult<List<RDisputeCheck>> listAPIResult) {
                                data.clear();
                                for (int i=0;i<listAPIResult.getData().size();i++){
                                    data.add(listAPIResult.getData().get(i));
                                }
                                mAdapter.notifyDataSetChanged();
                                CurrentPage++;
                                mBinding.refresh.setRefreshing(false);
                                mBinding.listView.onLoadComplete();
                            }

                            @Override
                            public void onMock(APIResult<List<RDisputeCheck>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    private void GetPetitionType() {
        Subscription subscription = PetitionStaffApiService.Factory.create().GetPetitionType("")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RPetitionType>>>() {
                        }.getType(), new APICallback<APIResult<List<RPetitionType>>>() {
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
                            public void onNext(APIResult<List<RPetitionType>> listAPIResult) {
                                RPetitionType rPetitionType=new RPetitionType();
                                rPetitionType.setMC(getString(R.string.petitionStaff_no_where));
                                typeData.add(rPetitionType);
                                for (int i=0;i<listAPIResult.getData().size();i++){
                                    typeData.add(listAPIResult.getData().get(i));
                                }
                                typeAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onMock(APIResult<List<RPetitionType>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    private void GetPetitionLevel() {
        Subscription subscription = PetitionStaffApiService.Factory.create().GetPetitionLevel("")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RPetitionType>>>() {
                        }.getType(), new APICallback<APIResult<List<RPetitionType>>>() {
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
                            public void onNext(APIResult<List<RPetitionType>> listAPIResult) {
                                RPetitionType rPetitionType=new RPetitionType();
                                rPetitionType.setMC(getString(R.string.petitionStaff_no_where));
                                levelData.add(rPetitionType);
                                for (int i=0;i<listAPIResult.getData().size();i++){
                                    levelData.add(listAPIResult.getData().get(i));
                                }
                                typeAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onMock(APIResult<List<RPetitionType>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    private void SetTypeOrLevelView(){
        mBinding.listviewType.setAdapter(typeAdapter);
        mBinding.listviewSex.setAdapter(levelAdapter);
        mBinding.listviewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (typeData.get(position).getMC().equals(getString(R.string.petitionStaff_no_where))){
                    mBinding.txtType.setText(getString(R.string.petitionStaff_no_where));
                }else {
                    mBinding.txtType.setText(typeData.get(position).getMC());
                }
                getTxtTypeLevel();
                GetImportantPetitionMatter(1,PageSize,mBinding.edtSearch.getText().toString(),strLevel,strType);
                mBinding.listviewType.setVisibility(GONE);
            }
        });
        mBinding.listviewSex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (typeData.get(position).getMC().equals(getString(R.string.petitionStaff_no_where))){
                    mBinding.txtSex.setText(getString(R.string.petitionStaff_no_where));
                } else {
                    mBinding.txtSex.setText(levelData.get(position).getMC());
                }
                getTxtTypeLevel();
                GetImportantPetitionMatter(1,PageSize,mBinding.edtSearch.getText().toString(),strLevel,strType);
                mBinding.listviewSex.setVisibility(GONE);
            }
        });
    }
    private void getTxtTypeLevel(){
        if (mBinding.txtType.getText().toString().equals("类型")||
                mBinding.txtType.getText().toString().equals(getString(R.string.petitionStaff_no_where))){
            strType="";
        } else {
            strType=mBinding.txtType.getText().toString();
        }
        if (mBinding.txtSex.getText().toString().equals("级别")||
                mBinding.txtSex.getText().toString().equals(getString(R.string.petitionStaff_no_where))){
            strLevel="";
        } else {
            strLevel=mBinding.txtSex.getText().toString();
        }
    }
}
