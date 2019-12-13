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
import cn.aorise.petition.staff.databinding.PetitionStaffActivityWorkWarningBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TWorkWarningListt;
import cn.aorise.petition.staff.module.network.entity.response.RWorkWarningList;
import cn.aorise.petition.staff.ui.adapter.WorkWarningListAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2017/6/15.
 */

public class PetitionStaffWorkWarningActivity05 extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityWorkWarningBinding mBinding;
    private int CurrentPage=1,PageSize=20;
    private String LY="2";
    private String ZT="0";
    private String ZZJG="";
    private String condition="";
    private List<RWorkWarningList> data=new ArrayList<>();
    private WorkWarningListAdapter mAdapter;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_petition_return==id) {
            PetitionStaffWorkWarningActivity05.this.finish();
        } else if (R.id.rl_petition_right==id) {
            mBinding.rlSearch.setVisibility(View.VISIBLE);
            mBinding.llToolbar.setVisibility(GONE);
        } else if (R.id.rl_cancle==id) {
            mBinding.edtSearch.setText("");
            mBinding.rlSearch.setVisibility(GONE);
            mBinding.llToolbar.setVisibility(View.VISIBLE);
        } else if (R.id.img_clear==id) {
            mBinding.edtSearch.setText("");
        }  else if (R.id.rl_has_answer==id) {
            mBinding.rlHasAnswer.setBackgroundResource(R.drawable.petition_staff_left_02);
            mBinding.txtHasAnswer.setTextColor(this.getResources().getColor(R.color.petition_staff_status_color));
            mBinding.rlNotAnswer.setBackgroundResource(R.drawable.petition_staff_right_01);
            mBinding.txtNotAnswer.setTextColor(this.getResources().getColor(R.color.petition_staff_ffffff));//设置点击按钮颜色变化
            ZT="0";
            CurrentPage=1;
            GetWorkWarningList(1,PageSize,LY,ZZJG,ZT,condition);

        } else if (R.id.rl_not_answer==id) {
            mBinding.rlHasAnswer.setBackgroundResource(R.drawable.petition_staff_left_01);
            mBinding.txtHasAnswer.setTextColor(this.getResources().getColor(R.color.petition_staff_ffffff));
            mBinding.rlNotAnswer.setBackgroundResource(R.drawable.petition_staff_right_02);
            mBinding.txtNotAnswer.setTextColor(this.getResources().getColor(R.color.petition_staff_status_color));
            ZT="1";
            CurrentPage=1;
            GetWorkWarningList(1,PageSize,LY,ZZJG,ZT,condition);

            //getSuggestCollect(tempPageIndex,pagesize,ZT);
        }
    }

    @Override
    protected void initData() {
        mAdapter=new WorkWarningListAdapter(data,this);
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this,R.layout.petition_staff_activity_work_warning);
        ZZJG=getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE).getString(getString(R.string.petition_staff_sp_SSBM),"");
        mBinding.txtTitle.setText(getString(R.string.petition_staff_work_warning_02));
        mBinding.rlPetitionReturn.setOnClickListener(this);
        mBinding.rlPetitionRight.setOnClickListener(this);
        mBinding.rlCancle.setOnClickListener(this);
        mBinding.imgClear.setOnClickListener(this);
        mBinding.rlHasAnswer.setOnClickListener(this);
        mBinding.rlNotAnswer.setOnClickListener(this);
        mBinding.listView.setRefreshAndLoadMoreView(mBinding.refresh);
        mBinding.listView.onLoadComplete();//结束下拉加载更多转动条
        mBinding.listView.setAdapter(mAdapter);
        GetWorkWarningList(1,PageSize,LY,ZZJG,ZT,condition);
        SearchListener();
        mBinding.edtSearch.setOnKeyListener(new View.OnKeyListener() {//输入完后按键盘上的搜索键【回车键改为了搜索键】

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER){//修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    PetitionStaffWorkWarningActivity05.this
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
               // GetImportantPetitionPeople(1,PageSize,s.toString());
                CurrentPage=1;
                condition=s.toString();
                GetWorkWarningList(1,PageSize,LY,ZZJG,ZT,condition);
            }
        });
    }

    private void GetWorkWarningList(int currentPage, int pageSize, final String ly, String zzjg, String zt, final String condition) {
        TWorkWarningListt tWorkWarningListt=new TWorkWarningListt();
        tWorkWarningListt.setCurrentPage(currentPage);
        tWorkWarningListt.setPageSize(pageSize);
        tWorkWarningListt.setLY(ly);
        tWorkWarningListt.setZZJG(zzjg);
        tWorkWarningListt.setZT(zt);
        tWorkWarningListt.setCondition(condition);
        System.out.println(GsonUtil.toJson(tWorkWarningListt));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetWorkWarningListOverdue(GsonUtil.toJson(tWorkWarningListt))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RWorkWarningList>>>() {
                        }.getType(), new APICallback<APIResult<List<RWorkWarningList>>>() {
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
                            public void onNext(APIResult<List<RWorkWarningList>> listAPIResult) {
                                data.clear();

                                for (int i=0;i<listAPIResult.getData().size();i++){
                                    data.add(listAPIResult.getData().get(i));
                                }
                                mAdapter.notifyDataSetChanged();
                                CurrentPage++;
                                mBinding.refresh.setRefreshing(false);
                                mBinding.listView.onLoadComplete();
                                mBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                    @Override
                                    public void onRefresh() {
                                        CurrentPage=1;
                                        GetWorkWarningList(1,PageSize*CurrentPage,LY,ZZJG,ZT,condition);
                                    }
                                });

                                mBinding.listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
                                    @Override
                                    public void onLoadMore() {
                                        GetWorkWarningList(1,PageSize*CurrentPage,LY,ZZJG,ZT,condition);
                                        mBinding.refresh.setRefreshing(true);
                                    }
                                });
                                mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent=new Intent(PetitionStaffWorkWarningActivity05.this,PetitionStaffWorkWarningDetailActivity.class);
                                        intent.putExtra("bh",data.get(position).getBH());
                                        intent.putExtra("zjhm",data.get(position).getZJHM());
                                        intent.putExtra("id",data.get(position).getID());
                                        intent.putExtra("xbsj",data.get(position).getXBSJ());
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onMock(APIResult<List<RWorkWarningList>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

}
