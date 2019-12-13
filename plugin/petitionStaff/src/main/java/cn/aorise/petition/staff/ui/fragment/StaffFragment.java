package cn.aorise.petition.staff.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.component.common.Utils;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffHomeFragmentBinding;
import cn.aorise.petition.staff.databinding.PetitionStaffStaffFragmentBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TLogin;
import cn.aorise.petition.staff.module.network.entity.request.TStaffInfo;
import cn.aorise.petition.staff.module.network.entity.response.RLogin;
import cn.aorise.petition.staff.module.network.entity.response.RStaffInfo;
import cn.aorise.petition.staff.ui.activity.MainActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffLoginActivity;
import cn.aorise.petition.staff.ui.adapter.StaffInfoAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseFragment;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/25.
 */

public class StaffFragment  extends PetitionStaffBaseFragment implements View.OnClickListener {
    private PetitionStaffStaffFragmentBinding mBinding;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    private List<RStaffInfo> data=new ArrayList<>();
    private StaffInfoAdapter adapter;
    public StaffFragment() {

    }

    public static StaffFragment newInstance() {
        StaffFragment fragment = new StaffFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(inflater, R.layout.petition_staff_staff_fragment,container,false);
        //初始化refresh listview
        //mBinding.refresh.setLoadMoreListView(mBinding.listView);
        mBinding.listView.setRefreshAndLoadMoreView(mBinding.refresh);
        GetStaffInfo();
        SearchListener();
        mBinding.listView.onLoadComplete();//结束下拉加载更多转动条
        mBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetStaffInfo();
            }
        });

        return mBinding.getRoot();
    }

    private void GetStaffInfo() {
        TStaffInfo tStaffInfo=new TStaffInfo();
        tStaffInfo.setWhereStr(mBinding.editText.getText().toString());
        Subscription subscription = PetitionStaffApiService.Factory.create().GetStaffInfo(GsonUtil.toJson(tStaffInfo))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber((BaseActivity) getActivity(), Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RStaffInfo>>>() {
                        }.getType(), new APICallback<APIResult<List<RStaffInfo>>>() {
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
                            public void onNext(APIResult<List<RStaffInfo>> listAPIResult) {
                                data.clear();
                                if (listAPIResult.getData()!=null) {
                                    data = listAPIResult.getData();
                                }
                                adapter=new StaffInfoAdapter(data,getActivity());
                                mBinding.listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                mBinding.refresh.setRefreshing(false);
                            }

                            @Override
                            public void onMock(APIResult<List<RStaffInfo>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }
    private void SearchListener() {
        mBinding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                GetStaffInfo();
            }
        });
    }
}
