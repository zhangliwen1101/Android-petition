package cn.aorise.petition.staff.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
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
import cn.aorise.petition.staff.databinding.PetitionStaffActivitySuggestCollectBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TImportantMonitor;
import cn.aorise.petition.staff.module.network.entity.request.TImportantPeople;
import cn.aorise.petition.staff.module.network.entity.request.TImportantPetitionMatter;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionMatter;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionPeople;
import cn.aorise.petition.staff.ui.adapter.ImportantPetitionMatterAdapter;
import cn.aorise.petition.staff.ui.adapter.ImportantPetitionPeopleAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/28.
 */

public class PetitionStaffImportantMonitorActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivitySuggestCollectBinding mBinding;
    private List<RImportantPetitionPeople> data=new ArrayList<>();
    private List<RImportantPetitionMatter> data1=new ArrayList<>();

    private ImportantPetitionPeopleAdapter mAdapter;
    private ImportantPetitionMatterAdapter mAdapter1;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private int CurrentPage=1,PageSize=20;
    private int ZT=1;//已回复状态为1，未回复状态为0；
    private String wherestr="";
    private SharedPreferences sp;
    public static Activity instance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_has_answer==id) {
            mBinding.rlHasAnswer.setBackgroundResource(R.drawable.petition_staff_left_02);
            mBinding.txtHasAnswer.setTextColor(this.getResources().getColor(R.color.petition_staff_status_color));
            mBinding.rlNotAnswer.setBackgroundResource(R.drawable.petition_staff_right_01);
            mBinding.txtNotAnswer.setTextColor(this.getResources().getColor(R.color.petition_staff_ffffff));//设置点击按钮颜色变化

            CurrentPage=1;
            ZT=1;
            GetImportantPetitionPeople(1,PageSize,wherestr);
            mBinding.refresh.setVisibility(View.VISIBLE);
            mBinding.refresh1.setVisibility(View.GONE);
            setPetition_title(this,getString(R.string.petition_staff_import_submission),
                    getString(R.string.petition_staff_add),PetitionStaffImportantMonitorAddPeopleActivity.class);


        } else if (R.id.rl_not_answer==id) {
            mBinding.rlHasAnswer.setBackgroundResource(R.drawable.petition_staff_left_01);
            mBinding.txtHasAnswer.setTextColor(this.getResources().getColor(R.color.petition_staff_ffffff));
            mBinding.rlNotAnswer.setBackgroundResource(R.drawable.petition_staff_right_02);
            mBinding.txtNotAnswer.setTextColor(this.getResources().getColor(R.color.petition_staff_status_color));

            CurrentPage=1;
            ZT=0;
            GetImportantPetitionMatter(1,PageSize,wherestr);
            //getSuggestCollect(tempPageIndex,pagesize,ZT);
            mBinding.refresh.setVisibility(View.GONE);
            mBinding.refresh1.setVisibility(View.VISIBLE);
            setPetition_title(this,getString(R.string.petition_staff_import_submission),
                    getString(R.string.petition_staff_add),PetitionStaffImportantMonitorAddMatterActivity.class);
        }
    }

    @Override
    protected void initData() {
        sp=getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE);
        wherestr=sp.getString(getString(R.string.petition_staff_sp_XM),"");
        mAdapter=new ImportantPetitionPeopleAdapter(data,this);
        mAdapter1=new ImportantPetitionMatterAdapter(data1,this);
        GetImportantPetitionPeople(1,PageSize,wherestr);

    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_suggest_collect);
        setPetition_title(this,getString(R.string.petition_staff_import_submission),
                getString(R.string.petition_staff_add),PetitionStaffImportantMonitorAddPeopleActivity.class);

        mBinding.rlHasAnswer.setOnClickListener(this);
        mBinding.rlNotAnswer.setOnClickListener(this);
        mBinding.listView.setAdapter(mAdapter);
        mBinding.listView1.setAdapter(mAdapter1);
        //初始化refresh listview
        //mBinding.refresh.setLoadMoreListView(mBinding.listView);
        mBinding.listView.setRefreshAndLoadMoreView(mBinding.refresh);
        mBinding.listView.onLoadComplete();//结束下拉加载更多转动条
        mBinding.listView1.setRefreshAndLoadMoreView(mBinding.refresh1);
        mBinding.listView1.onLoadComplete();



    }

    @Override
    protected void initEvent() {

    }


    private void GetImportantPetitionPeople(int currentPage,int pageSize,String where) {
        TImportantMonitor tImportantPeople=new TImportantMonitor();
        tImportantPeople.setCurrentPage(currentPage);
        tImportantPeople.setPageSize(pageSize);
        tImportantPeople.setCurrentUser(where);
        System.out.println(GsonUtil.toJson(tImportantPeople));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetImportantMonitor(GsonUtil.toJson(tImportantPeople))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RImportantPetitionPeople>>>() {
                        }.getType(), new APICallback<APIResult<List<RImportantPetitionPeople>>>() {
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
                            public void onNext(APIResult<List<RImportantPetitionPeople>> listAPIResult) {
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
                                        GetImportantPetitionPeople(1,PageSize*CurrentPage,wherestr);
                                    }
                                });

                                mBinding.listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
                                    @Override
                                    public void onLoadMore() {
                                        GetImportantPetitionPeople(1,PageSize*CurrentPage,wherestr);
                                        mBinding.refresh.setRefreshing(true);
                                    }
                                });
                                mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent=new Intent(PetitionStaffImportantMonitorActivity.this,PetitionStaffImportantPetitionPeopleDetailActivity.class);
                                        intent.putExtra("zjhm",data.get(position).getZJHM());
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onMock(APIResult<List<RImportantPetitionPeople>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    private void GetImportantPetitionMatter(int currentPage,int pageSize,String whereStr) {
        TImportantMonitor tImportantPetitionMatter=new TImportantMonitor();
        tImportantPetitionMatter.setCurrentPage(currentPage);
        tImportantPetitionMatter.setPageSize(pageSize);
        tImportantPetitionMatter.setCurrentUser(whereStr);

        System.out.println(GsonUtil.toJson(tImportantPetitionMatter));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetImportantPetitionMatter(GsonUtil.toJson(tImportantPetitionMatter))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RImportantPetitionMatter>>>() {
                        }.getType(), new APICallback<APIResult<List<RImportantPetitionMatter>>>() {
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
                            public void onNext(APIResult<List<RImportantPetitionMatter>> listAPIResult) {

                                data1.clear();
                                for (int i=0;i<listAPIResult.getData().size();i++){
                                    data1.add(listAPIResult.getData().get(i));
                                }
                                mAdapter1.notifyDataSetChanged();
                                CurrentPage++;
                                mBinding.refresh1.setRefreshing(false);
                                mBinding.listView1.onLoadComplete();

                                mBinding.refresh1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                    @Override
                                    public void onRefresh() {
                                        CurrentPage=1;
                                        GetImportantPetitionMatter(1,PageSize*CurrentPage,wherestr);
                                    }
                                });

                                mBinding.listView1.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
                                    @Override
                                    public void onLoadMore() {
                                        GetImportantPetitionMatter(1,PageSize*CurrentPage,wherestr);
                                        mBinding.refresh.setRefreshing(true);
                                    }
                                });
                                mBinding.listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent=new Intent(PetitionStaffImportantMonitorActivity.this,PetitionStaffImportantPetitionMatterDetailActivity.class);
                                        intent.putExtra("letterId",data1.get(position).getBH());
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onMock(APIResult<List<RImportantPetitionMatter>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

    @Override
    protected void onResume() {   /*返回刷新*/
        super.onResume();
        if (ZT==1) {/*重点信访人员*/
            GetImportantPetitionPeople(1,PageSize*CurrentPage,wherestr);
        } else { /*重点信访事项*/
            GetImportantPetitionMatter(1,PageSize*CurrentPage,wherestr);
        }
    }
}
