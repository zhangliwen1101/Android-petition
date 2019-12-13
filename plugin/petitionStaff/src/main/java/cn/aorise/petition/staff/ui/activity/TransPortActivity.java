package cn.aorise.petition.staff.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.aorise.common.component.common.Utils;
import cn.aorise.common.core.module.network.APICallback;
import cn.aorise.common.core.module.network.APIResult;
import cn.aorise.common.core.module.network.RxAPIManager;
import cn.aorise.common.core.utils.assist.GsonUtil;
import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.commenrefresh.LoadMoreListView;
import cn.aorise.petition.staff.module.network.Mock;
import cn.aorise.petition.staff.module.network.PetitionStaffApiService;
import cn.aorise.petition.staff.module.network.entity.request.TWorkWarningListt;
import cn.aorise.petition.staff.module.network.entity.request.addimportantmatter.ContactPeople;
import cn.aorise.petition.staff.module.network.entity.response.REmptyEntity;
import cn.aorise.petition.staff.module.network.entity.response.RWorkWarningList;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;
import cn.aorise.petition.staff.ui.bean.TransPortInfo;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TransPortActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private RelativeLayout rlPetitionReturn;
    private RelativeLayout rlPetitionTrans,tijiao;
    private TextView txtQX,txtRQ,txtJBR,txtJBJG;
    private String DM;
    private String MC;
    private SharedPreferences sp;
    @Override
    protected void initData() {
        sp=getSharedPreferences(getString(R.string.petition_staff_sp_info),MODE_PRIVATE);
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this, R.color.petition_staff_0066ba);
        setContentView(R.layout.activity_trans_port);
    }

    @Override
    protected void initEvent() {
        rlPetitionReturn = (RelativeLayout) findViewById(R.id.rl_petition_return);
        rlPetitionTrans = (RelativeLayout) findViewById(R.id.rl_date);
        txtQX = (TextView) findViewById(R.id.txt_csrq);
        txtRQ = (TextView) findViewById(R.id.txt_bh);
        txtJBR = (TextView) findViewById(R.id.txt_zw);
        txtJBJG = (TextView) findViewById(R.id.txt_bm);
        tijiao = (RelativeLayout) findViewById(R.id.rl_exit_login);
        rlPetitionTrans.setOnClickListener(this);
        rlPetitionReturn.setOnClickListener(this);
        tijiao.setOnClickListener(this);
        txtJBJG.setText(sp.getString("SSBM",""));
        txtJBR.setText(sp.getString("xm",""));
        txtRQ.setText(gettime());
    }

    public  static String gettime () {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss   参数： yyyy年MM月dd日 HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.rl_petition_return == id) {
            this.finish();
        } else if (R.id.rl_date == id) {
            Intent intent = new Intent(this, TransPortChooseActivity.class);
            startActivityForResult(intent, 1001);
        } else if (R.id.rl_exit_login==id) {
            if (DM==null||MC==null) {
                showToast("请选择去向");
            } else {
                submit();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            try {
                DM =data.getStringExtra("DM");
                MC = data.getStringExtra("MC");
                txtQX.setText(MC);

            } catch (Exception e) {

            }
        }
        System.out.println(DM+MC);

    }


    private void submit() {
        TransPortInfo transPortInfo = new TransPortInfo();
        transPortInfo.setBH(getIntent().getStringExtra("bh"));
        transPortInfo.setBB(getVersion());
        transPortInfo.setBLFS("转送");
        transPortInfo.setBLQX(txtQX.getText().toString());
        transPortInfo.setBLQXID(DM);
        transPortInfo.setPT("Android");
        transPortInfo.setXM(txtJBR.getText().toString());
        transPortInfo.setZZJG(txtJBJG.getText().toString());
        System.out.println(GsonUtil.toJson(transPortInfo));
        Subscription subscription = PetitionStaffApiService.Factory.create().AddTransPort(GsonUtil.toJson(transPortInfo))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cn.aorise.petition.staff.common.Utils.mockSubscriber(this, Mock.PETITION_LOGIN,
                        new TypeToken<APIResult<REmptyEntity>>() {
                        }.getType(), new APICallback<APIResult<REmptyEntity>>() {
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
                            public void onNext(APIResult<REmptyEntity> rEmptyEntityAPIResult) {
                                showToast(rEmptyEntityAPIResult.getMsg());
                                if (rEmptyEntityAPIResult.getMsg().equals("办理完成")) {
                                    TransPortActivity.this.finish();
                                }
                            }

                            @Override
                            public void onMock(APIResult<REmptyEntity> rEmptyEntityAPIResult) {

                            }
                        }));
        //RxAPIManager.getInstance().add(TAG, subscription);
    }
}
