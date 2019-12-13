package cn.aorise.petition.ui.activity;

import android.app.Activity;
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
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.R;
import cn.aorise.petition.common.Utils;
import cn.aorise.petition.databinding.PetitionActivityRequestAddAddressBinding;
import cn.aorise.petition.module.network.Mock;
import cn.aorise.petition.module.network.PetitionApiService;
import cn.aorise.petition.module.network.entity.request.TQuestion;
import cn.aorise.petition.module.network.entity.response.RAllAddress;
import cn.aorise.petition.module.network.entity.response.RQuestion;
import cn.aorise.petition.ui.adapter.AddAddressAdapter;
import cn.aorise.petition.ui.base.PetitionBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/5.
 */

public class RequestAddressChooseActivity extends PetitionBaseActivity implements View.OnClickListener{
    private PetitionActivityRequestAddAddressBinding mBinding;
    private List<RQuestion> data=new ArrayList<>();
    private AddAddressAdapter mAdapter;
    private static final String TAG = AboutActivity.class.getSimpleName();
    public static Activity instance=null;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private List<RAllAddress> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*GetQuestionType("","");*/
        instance=this;
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
        list=GsonUtil.fromJson(getSharedPreferences("LongSp",MODE_PRIVATE).getString("AllAddress",""),new TypeToken<List<RAllAddress>>(){}.getType());
        for (int i=0;i<list.size();i++){
            RQuestion rQuestion=new RQuestion();
            rQuestion.setMC(list.get(i).getMC());
            rQuestion.setDM(list.get(i).getDM());
            rQuestion.setFJDM(list.get(i).getFJDM());
            data.add(rQuestion);
        }
        mAdapter=new AddAddressAdapter(data,this);
        mBinding.listView.setAdapter(mAdapter);
        mBinding.refresh.setEnabled(false);
        mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*editor.putString(getString(R.string.petition_share_registAddress_QY_SH),data.get(position).getMC());
                editor.putString(getString(R.string.petition_regist_addaddress_QYSHDM),data.get(position).getDM().toString());
                editor.commit();*/
                if (list.get(position).getChildren().size()==0) {
                    editor.putString(getString(R.string.petition_sharepre_SFD_SH),list.get(position).getMC());
                    editor.putString(getString(R.string.petition_sharepre_SFD_SHDM),list.get(position).getDM());
                    editor.putString(getString(R.string.petition_sharepre_SFD_S),"");
                    editor.putString(getString(R.string.petition_sharepre_SFD_SDM),"");
                    editor.putString(getString(R.string.petition_sharepre_SFD_X),"");
                    editor.putString(getString(R.string.petition_sharepre_SFD_XDM),"");
                    editor.commit();
                    RequestAddressChooseActivity.this.finish();
                } else {
                    Intent intent = new Intent(RequestAddressChooseActivity.this, RequestAddressChooseActivity01.class);
                    intent.putExtra("DM", data.get(position).getDM().toString());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        sp=getSharedPreferences(getString(R.string.petition_sharepre_nomal),MODE_PRIVATE);
        editor=sp.edit();
    }
    /*private void GetQuestionType(String JB,String DM) {
        TQuestion tLogin = new TQuestion();
        tLogin.setJB(JB);
        tLogin.setDM(DM);
        String request= GsonUtil.toJson(tLogin);
        System.out.println(request);
        Subscription subscription = PetitionApiService.Factory.create().getAddress(request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Utils.mockSubscriber(this, Mock.PETITION_QUESTION_TYPE,
                        new TypeToken<APIResult<List<RQuestion>>>() {
                        }.getType(), new APICallback<APIResult<List<RQuestion>>>() {
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
                            public void onNext(APIResult<List<RQuestion>> listAPIResult) {
                                System.out.println(listAPIResult);
                                data.clear();
                                for (int i=0;i<listAPIResult.getData().size();i++){
                                    data.add(listAPIResult.getData().get(i));
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onMock(APIResult<List<RQuestion>> listAPIResult) {
                                System.out.println(listAPIResult);
                            }
                        }));
        RxAPIManager.getInstance().add(TAG, subscription);
    }*/
}
