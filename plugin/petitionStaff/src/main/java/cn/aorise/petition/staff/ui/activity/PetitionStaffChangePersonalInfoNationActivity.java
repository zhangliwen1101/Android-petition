package cn.aorise.petition.staff.ui.activity;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.Utils;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityRequestAddAddressBinding;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.response.RDmMc;
import cn.aorise.petition.staff.module.network.entity.response.RQuestion;
import cn.aorise.petition.staff.ui.adapter.AddAddressAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/5/18.
 */

public class PetitionStaffChangePersonalInfoNationActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityRequestAddAddressBinding mBinding;
    private AddAddressAdapter mAdapter;
    private List<RQuestion> data=new ArrayList<>();
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static final String TAG = PetitionStaffLoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {
        sp=getSharedPreferences(getString(R.string.petition_staff_short_time_sp),MODE_PRIVATE);
        editor=sp.edit();
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_request_add_address);
        GetNation();
    }

    @Override
    protected void initEvent() {

    }

    private void GetNation() {

        System.out.println();
        Subscription subscription = PetitionStaffApiService.Factory.create().getNation("")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RQuestion>>>() {
                        }.getType(), new APICallback<APIResult<List<RQuestion>>>() {
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
                            public void onNext(APIResult<List<RQuestion>> listAPIResult) {
                                for (int i=0;i<listAPIResult.getData().size();i++){
                                    data.add(listAPIResult.getData().get(i));
                                }
                                mAdapter=new AddAddressAdapter(data,PetitionStaffChangePersonalInfoNationActivity.this);
                                mBinding.listView.setAdapter(mAdapter);
                                mBinding.listView.setAdapter(mAdapter);
                                mBinding.refresh.setEnabled(false);
                                mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        editor.putString(getString(R.string.petition_staff_sp_MZ),data.get(position).getMC());
                                        editor.putString(getString(R.string.petition_staff_sp_MZID), (String) data.get(position).getDM());
                                        editor.commit();
                                        PetitionStaffChangePersonalInfoNationActivity.this.finish();
                                    }
                                });
                            }

                            @Override
                            public void onMock(APIResult<List<RQuestion>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }
}
