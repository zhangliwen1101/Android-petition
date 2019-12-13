package cn.aorise.petition.ui.activity;

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
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.R;
import cn.aorise.petition.common.Utils;
import cn.aorise.petition.databinding.PetitionActivityRequestAddAddressBinding;
import cn.aorise.petition.module.network.Mock;
import cn.aorise.petition.module.network.PetitionApiService;
import cn.aorise.petition.module.network.entity.request.TLXFJDM;
import cn.aorise.petition.module.network.entity.response.RDMIDMC;
import cn.aorise.petition.ui.adapter.ZJLXAdapter;
import cn.aorise.petition.ui.base.PetitionBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/5/18.
 */

public class PetitionChooseZJLXActivity extends PetitionBaseActivity implements View.OnClickListener {
    private PetitionActivityRequestAddAddressBinding mBinding;
    private ZJLXAdapter mAdapter;
    private List<RDMIDMC> data=new ArrayList<>();
    private static final String TAG = LoginActivity.class.getSimpleName();
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {
        sp=getSharedPreferences(getString(R.string.petition_sharepre_nomal),MODE_PRIVATE);
        editor=sp.edit();
    }

    @Override
    protected void initView() {
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_activity_request_add_address);
        GetZJLX("ZJLX","");

        //mAdapter=new AddAddressAdapter(data,this);

    }

    @Override
    protected void initEvent() {

    }

    private void GetZJLX(String lx,String fjdm) {
        TLXFJDM tlxfjdm=new TLXFJDM();
        tlxfjdm.setLX(lx);
        tlxfjdm.setFJDM(fjdm);
        System.out.println(GsonUtil.toJson(tlxfjdm));
        Subscription subscription = PetitionApiService.Factory.create().getZJLX(GsonUtil.toJson(tlxfjdm))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<List<RDMIDMC>>>() {
                        }.getType(), new APICallback<APIResult<List<RDMIDMC>>>() {
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
                            public void onNext(APIResult<List<RDMIDMC>> listAPIResult) {
                                for (int i=0;i<listAPIResult.getData().size();i++){
                                    data.add(listAPIResult.getData().get(i));
                                }
                                mAdapter=new ZJLXAdapter(data,PetitionChooseZJLXActivity.this);
                                mBinding.listView.setAdapter(mAdapter);
                                mBinding.refresh.setEnabled(false);
                                mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        editor.putString(getString(R.string.petition_sp_ZJLX),data.get(position).getMC());
                                        editor.putString(getString(R.string.petition_sp_ZJLXID),data.get(position).getID());
                                        editor.commit();
                                        PetitionChooseZJLXActivity.this.finish();
                                    }
                                });
                            }

                            @Override
                            public void onMock(APIResult<List<RDMIDMC>> listAPIResult) {

                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }

}
