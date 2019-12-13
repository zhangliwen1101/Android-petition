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
import cn.aorise.petition.databinding.PetitionActivityRequestAddAddress1Binding;
import cn.aorise.petition.databinding.PetitionActivityRequestAddAddressBinding;
import cn.aorise.petition.module.network.Mock;
import cn.aorise.petition.module.network.PetitionApiService;
import cn.aorise.petition.module.network.entity.request.TQuestion;
import cn.aorise.petition.module.network.entity.response.RAllAddress;
import cn.aorise.petition.module.network.entity.response.RAllAddress1;
import cn.aorise.petition.module.network.entity.response.RQuestion;
import cn.aorise.petition.ui.adapter.AddAddressAdapter;
import cn.aorise.petition.ui.base.PetitionBaseActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/5.
 */

public class PersonalChangeInformationAddressActivity01 extends PetitionBaseActivity implements View.OnClickListener{
    private PetitionActivityRequestAddAddress1Binding mBinding;
    private List<RQuestion> data=new ArrayList<>();
    private List<RQuestion> data1=new ArrayList<>();
    private AddAddressAdapter mAdapter;
    private AddAddressAdapter mAdapter1;
    private static final String TAG = AboutActivity.class.getSimpleName();
    public static Activity instance=null;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String DM;
    private List<RAllAddress> list=new ArrayList<>();
    private List<RAllAddress1> list1=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DM=getIntent().getExtras().getString("DM");
        //GetQuestionType("02",DM);
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
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_activity_request_add_address1);

        list=GsonUtil.fromJson(getSharedPreferences("LongSp",MODE_PRIVATE).getString("AllAddress",""),new TypeToken<List<RAllAddress>>(){}.getType());
        for (int i=0;i<list.size();i++) {
            if (list.get(i).getDM().equals(getIntent().getExtras().getString("DM"))) {
                list1=list.get(i).getChildren();
                System.out.println(list1);
                for (int j=0;j<list.get(i).getChildren().size();j++) {
                    RQuestion rQuestion=new RQuestion();
                    rQuestion.setMC(list.get(i).getChildren().get(j).getMC());
                    rQuestion.setDM(list.get(i).getChildren().get(j).getDM());
                    rQuestion.setFJDM(list.get(i).getChildren().get(j).getFJDM());
                    data.add(rQuestion);
                }
            }

        }
        mAdapter=new AddAddressAdapter(data,this);
        mBinding.listView.setAdapter(mAdapter);
        mBinding.refresh.setEnabled(false);
        mBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*editor.putString(getString(R.string.petition_share_registAddress_QY_S),data.get(position).getMC());
                editor.putString(getString(R.string.petition_regist_addaddress_QYSDM),data.get(position).getDM().toString());
                editor.commit();*/
                /*Intent intent=new Intent(PersonalChangeInformationAddressActivity01.this,PersonalChangeInformationAddressActivity02.class);
                intent.putExtra("DM",data.get(position).getDM().toString());
                startActivity(intent);*/
                data1.clear();
                for (int i=0;i<list1.get(position).getChildren().size();i++) {

                        RQuestion rQuestion=new RQuestion();
                        rQuestion.setMC(list1.get(position).getChildren().get(i).getMC());
                        rQuestion.setDM(list1.get(position).getChildren().get(i).getDM());
                        rQuestion.setFJDM(list1.get(position).getChildren().get(i).getFJDM());
                        data1.add(rQuestion);

                }
                if (data1.size()==0) {
                    for (int i=0;i<list.size();i++) {
                        if (list.get(i).getDM().equals(getIntent().getExtras().getString("DM"))) {
                            editor.putString(getString(R.string.petition_share_registAddress_QY_SH),list.get(i).getMC());
                            editor.putString(getString(R.string.petition_regist_addaddress_QYSHDM),list.get(i).getDM());
                        }
                    }
                            editor.putString(getString(R.string.petition_share_registAddress_QY_S),data.get(position).getMC());
                            editor.putString(getString(R.string.petition_regist_addaddress_QYSDM),data.get(position).getDM().toString());

                            editor.putString(getString(R.string.petition_share_registAddress_QY_X),"");
                            editor.putString(getString(R.string.petition_regist_addaddress_QYXDM),"");

                            editor.commit();
                    PersonalChangeInformationAddressActivity.instance.finish();
                    PersonalChangeInformationAddressActivity01.this.finish();
                }
                mAdapter1=new AddAddressAdapter(data1,PersonalChangeInformationAddressActivity01.this);
                mBinding.listview1.setAdapter(mAdapter1);

            }
        });
        mBinding.listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i=0;i<list.size();i++) {
                    if (list.get(i).getDM().equals(getIntent().getExtras().getString("DM"))) {
                        editor.putString(getString(R.string.petition_share_registAddress_QY_SH),list.get(i).getMC());
                        editor.putString(getString(R.string.petition_regist_addaddress_QYSHDM),list.get(i).getDM());
                    }
                }
                for (int i=0;i<list1.size();i++) {
                    if (data1.get(position).getFJDM().equals(list1.get(i).getDM())) {
                        editor.putString(getString(R.string.petition_share_registAddress_QY_S),list1.get(i).getMC());
                        editor.putString(getString(R.string.petition_regist_addaddress_QYSDM),list1.get(i).getDM());
                    }
                }

                editor.putString(getString(R.string.petition_share_registAddress_QY_X),data1.get(position).getMC());
                editor.putString(getString(R.string.petition_regist_addaddress_QYXDM),data1.get(position).getDM().toString());
                editor.commit();

                PersonalChangeInformationAddressActivity.instance.finish();
                PersonalChangeInformationAddressActivity01.this.finish();

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
