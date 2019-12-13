package cn.aorise.petition.staff.ui.activity;

import android.content.Intent;
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
import cn.aorise.petition.staff.module.network.entity.response.RQuestion;
import cn.aorise.petition.staff.ui.adapter.AddAddressAdapter;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import cn.aorise.petition.staff.ui.bean.WhereInfo;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/5/18.
 */

public class TransPortChooseActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityRequestAddAddressBinding mBinding;
    private AddAddressAdapter mAdapter;
    private List<RQuestion> data=new ArrayList<>();
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
        Subscription subscription = PetitionStaffApiService.Factory.create().getWhere("")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<WhereInfo>>>() {
                        }.getType(), new APICallback<APIResult<List<WhereInfo>>>() {
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
                            public void onNext(APIResult<List<WhereInfo>> listAPIResult) {
                                for (int i=0;i<listAPIResult.getData().size();i++){
                                    RQuestion rQuestion = new RQuestion();
                                    rQuestion.setDM(listAPIResult.getData().get(i).getJGDM());
                                    rQuestion.setMC(listAPIResult.getData().get(i).getJGMC());
                                    data.add(rQuestion);
                                }
                                mAdapter=new AddAddressAdapter(data, TransPortChooseActivity.this);
                                mBinding.listView.setAdapter(mAdapter);
                                mBinding.listView.setAdapter(mAdapter);
                                mBinding.refresh.setEnabled(false);
                                mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent();
                                        intent.putExtra("DM", data.get(position).getDM().toString());
                                        intent.putExtra("MC", data.get(position).getMC());
                                        setResult(1001, intent);
                                        TransPortChooseActivity.this.finish();
                                    }
                                });
                            }

                            @Override
                            public void onMock(APIResult<List<WhereInfo>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }
}
