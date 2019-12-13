package cn.aorise.petition.staff.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
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
import cn.aorise.common.core.utils.assist.AoriseUtil;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.commenrefresh.LoadMoreListView;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityImportantObjectBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TDhhm;
import cn.aorise.petition.staff.module.network.entity.request.TImportantPeople;
import cn.aorise.petition.staff.module.network.entity.response.RImportantPetitionPeople;
import cn.aorise.petition.staff.module.network.entity.response.RStaffInfoDetail;
import cn.aorise.petition.staff.ui.adapter.ImportantPetitionPeopleAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2017/5/26.
 */

public class PetitionStaffImportantPetitionPeopleActivity extends PetitionStaffBaseActivity implements View.OnClickListener{
    private PetitionStaffActivityImportantObjectBinding mBinding;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private ImportantPetitionPeopleAdapter mAdapter;
    private List<RImportantPetitionPeople> data=new ArrayList<>();
    private int CurrentPage=1,PageSize=20;
    private String Wherestr="";//1为已评价，0为未评价
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_petition_return==id) {
            PetitionStaffImportantPetitionPeopleActivity.this.finish();
        } else if (R.id.rl_petition_right==id) {
            mBinding.rlSearch.setVisibility(View.VISIBLE);
            mBinding.llToolbar.setVisibility(GONE);
        } else if (R.id.rl_cancle==id) {
            mBinding.edtSearch.setText("");
            mBinding.rlSearch.setVisibility(GONE);
            mBinding.llToolbar.setVisibility(View.VISIBLE);
        } else if (R.id.img_clear==id) {
            mBinding.edtSearch.setText("");
        }

    }

    @Override
    protected void initData() {
        mAdapter=new ImportantPetitionPeopleAdapter(data,this);
        GetImportantPetitionPeople(1,PageSize,"");
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_important_object);
        mBinding.rlPetitionReturn.setOnClickListener(this);
        mBinding.rlPetitionRight.setOnClickListener(this);
        mBinding.rlCancle.setOnClickListener(this);
        mBinding.imgClear.setOnClickListener(this);

        mBinding.listView.setRefreshAndLoadMoreView(mBinding.refresh);
        mBinding.listView.setAdapter(mAdapter);
        mBinding.listView.onLoadComplete();//结束下拉加载更多转动条
        mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(PetitionStaffImportantPetitionPeopleActivity.this,PetitionStaffImportantPetitionPeopleDetailActivity.class);
                intent.putExtra("zjhm",data.get(position).getZJHM());
                startActivity(intent);
            }
        });

        mBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CurrentPage=1;

                GetImportantPetitionPeople(1,PageSize,mBinding.edtSearch.getText().toString());
            }
        });

        mBinding.listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                GetImportantPetitionPeople(1,PageSize*CurrentPage,mBinding.edtSearch.getText().toString());
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
                                    PetitionStaffImportantPetitionPeopleActivity.this
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
                GetImportantPetitionPeople(1,PageSize,s.toString());
            }
        });
    }

    private void GetImportantPetitionPeople(int currentPage,int pageSize,String whereStr) {
        TImportantPeople tImportantPeople=new TImportantPeople();
        tImportantPeople.setCurrentPage(currentPage);
        tImportantPeople.setPageSize(pageSize);
        tImportantPeople.setWhereStr(whereStr);
        System.out.println(GsonUtil.toJson(tImportantPeople));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetImportantPetitionPeople(GsonUtil.toJson(tImportantPeople))
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
                            }

                            @Override
                            public void onMock(APIResult<List<RImportantPetitionPeople>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }
}
