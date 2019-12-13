package cn.aorise.petition.staff.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.aorise.common.component.common.Utils;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.ui.base.BaseActivity;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.databinding.PetitionStaffHomeFragmentBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TSsbm;
import cn.aorise.petition.staff.module.network.entity.request.TStaffInfo;
import cn.aorise.petition.staff.module.network.entity.response.REmptyEntity;
import cn.aorise.petition.staff.module.network.entity.response.RStaffInfo;
import cn.aorise.petition.staff.module.network.entity.response.RWorkWarning;
import cn.aorise.petition.staff.ui.activity.PetitionStaffLoginActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffWorkWarningActivity;
import cn.aorise.petition.staff.ui.activity.PetitionStaffWorkWarningActivity01;
import cn.aorise.petition.staff.ui.activity.PetitionStaffWorkWarningActivity02;
import cn.aorise.petition.staff.ui.activity.PetitionStaffWorkWarningActivity03;
import cn.aorise.petition.staff.ui.activity.PetitionStaffWorkWarningActivity04;
import cn.aorise.petition.staff.ui.activity.PetitionStaffWorkWarningActivity05;
import cn.aorise.petition.staff.ui.activity.PetitionStaffWorkWarningActivity06;
import cn.aorise.petition.staff.ui.activity.PetitionStaffWorkWarningActivity07;
import cn.aorise.petition.staff.ui.adapter.StaffInfoAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseFragment;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HomeFragment extends PetitionStaffBaseFragment implements View.OnClickListener {
    private PetitionStaffHomeFragmentBinding mBinding;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();
    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_01==id) {
            Intent intent=new Intent(getActivity(),PetitionStaffWorkWarningActivity.class);
            startActivity(intent);
        } else if (R.id.rl_02==id) {
            Intent intent=new Intent(getActivity(),PetitionStaffWorkWarningActivity01.class);
            startActivity(intent);
        } else if (R.id.rl_03==id) {
            Intent intent=new Intent(getActivity(),PetitionStaffWorkWarningActivity02.class);
            startActivity(intent);
        } else if (R.id.rl_04==id) {
            Intent intent=new Intent(getActivity(),PetitionStaffWorkWarningActivity03.class);
            startActivity(intent);
        } else if (R.id.rl_05==id) {
            Intent intent=new Intent(getActivity(),PetitionStaffWorkWarningActivity04.class);
            startActivity(intent);
        } else if (R.id.rl_06==id) {
            Intent intent=new Intent(getActivity(),PetitionStaffWorkWarningActivity05.class);
            startActivity(intent);
        } else if (R.id.rl_07==id) {
            Intent intent=new Intent(getActivity(),PetitionStaffWorkWarningActivity06.class);
            startActivity(intent);
        } else if (R.id.rl_08==id) {
            Intent intent=new Intent(getActivity(),PetitionStaffWorkWarningActivity07.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(inflater, R.layout.petition_staff_home_fragment,container,false);
        mBinding.rl01.setOnClickListener(this);
        mBinding.rl02.setOnClickListener(this);
        mBinding.rl03.setOnClickListener(this);
        mBinding.rl04.setOnClickListener(this);
        mBinding.rl05.setOnClickListener(this);
        mBinding.rl06.setOnClickListener(this);
        mBinding.rl07.setOnClickListener(this);
        mBinding.rl08.setOnClickListener(this);
        GetWorkWarning();
        return mBinding.getRoot();
    }


    private void GetWorkWarning() {
        TSsbm rEmptyEntity=new TSsbm();
        rEmptyEntity.setSSBM(getActivity().getSharedPreferences(getString(R.string.petition_staff_sp_info), Context.MODE_PRIVATE).getString(getString(R.string.petition_staff_sp_SSBM),""));
        System.out.println(GsonUtil.toJson(rEmptyEntity));
        Subscription subscription = PetitionStaffApiService.Factory.create().GetWorkWarning(GsonUtil.toJson(rEmptyEntity))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber((BaseActivity) getActivity(), Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RWorkWarning>>>() {
                        }.getType(), new APICallback<APIResult<List<RWorkWarning>>>() {
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
                            public void onNext(APIResult<List<RWorkWarning>> listAPIResult) {
                                mBinding.txt01.setText(listAPIResult.getData().get(0).getLF_LJ());
                                mBinding.txt02.setText(listAPIResult.getData().get(0).getLX_LJ());
                                mBinding.txt03.setText(listAPIResult.getData().get(0).getLD_LJ());
                                mBinding.txt04.setText(listAPIResult.getData().get(0).getWX_LJ());
                                mBinding.txt05.setText(listAPIResult.getData().get(0).getLF_YQ());
                                mBinding.txt06.setText(listAPIResult.getData().get(0).getLX_YQ());
                                mBinding.txt07.setText(listAPIResult.getData().get(0).getLD_YQ());
                                mBinding.txt08.setText(listAPIResult.getData().get(0).getWX_YQ());
                            }

                            @Override
                            public void onMock(APIResult<List<RWorkWarning>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }
}