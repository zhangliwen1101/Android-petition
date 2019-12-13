package cn.aorise.petition.staff.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.PopupWindow;

import java.util.Calendar;

import cn.aorise.petition.staff.R;
import cn.aorise.petition.staff.common.popupwindow.Popuwindow_jb;
import cn.aorise.petition.staff.databinding.PetitionStaffActivityImportantMonitorAddmatterBinding;
import cn.aorise.petition.staff.ui.base.PetitionStaffBaseActivity;

/**
 * Created by Administrator on 2017/6/21.
 */

public class PetitionStaffImportantMonitorAddMatterActivity extends PetitionStaffBaseActivity implements View.OnClickListener {
    private PetitionStaffActivityImportantMonitorAddmatterBinding mBinding;
    private Popuwindow_jb popuwindow_jb;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public static Activity instance;
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (R.id.rl_next==id){
            if (mBinding.txtJb.getText().toString().trim().equals("")||
                    mBinding.txtAddress.getText().toString().trim().equals("")||
                    mBinding.txtDetailAddress.getText().toString().trim().equals("")||
                    mBinding.txtWtlx.getText().toString().trim().equals("")||
                    mBinding.txtXfmd.getText().toString().trim().equals("")||
                    /*mBinding.txtLfrq.getText().toString().trim().equals("")||
                    mBinding.txtFfrq.getText().toString().trim().equals("")||
                    mBinding.txtXbrq.getText().toString().trim().equals("")||*/
                    mBinding.txtJtr.getText().toString().trim().equals("")||
                    mBinding.txtNr.getText().toString().trim().equals("")){
                showToast(getString(R.string.petition_staff_toast_error_02));
            } else {
                save();
                Intent intent=new Intent(PetitionStaffImportantMonitorAddMatterActivity.this,
                        PetitionStaffImportantMonitorAddMatterActivity01.class);
                intent.putExtra("jb",mBinding.txtJb.getText().toString());
                intent.putExtra("xxdz",mBinding.txtDetailAddress.getText().toString());
                intent.putExtra("xfmd",mBinding.txtXfmd.getText().toString());
                intent.putExtra("lfrq",mBinding.txtLfrq.getText().toString());
                intent.putExtra("ffrq",mBinding.txtFfrq.getText().toString());
                intent.putExtra("xbrq",mBinding.txtXbrq.getText().toString());
                intent.putExtra("jtr",mBinding.txtJtr.getText().toString());
                intent.putExtra("gk",mBinding.txtGk.getText().toString());
                intent.putExtra("nr",mBinding.txtNr.getText().toString());
                startActivity(intent);
            }


        }else if (R.id.btn_gao==id) {//性别选择
            popuwindow_jb.dismiss();
            backgroundAlpha(1f);
            mBinding.txtJb.setText(R.string.petition_staff_gao);
        } else if (R.id.btn_zhong==id) {//性别选择
            popuwindow_jb.dismiss();
            backgroundAlpha(1f);
            mBinding.txtJb.setText(R.string.petition_staff_zhong);
        }else if (R.id.btn_di==id) {//性别选择
            popuwindow_jb.dismiss();
            backgroundAlpha(1f);
            mBinding.txtJb.setText(R.string.petition_staff_di);
        }else if (R.id.rl_jb==id) {//性别relayout
            backgroundAlpha(0.3f);
            popuwindow_jb=new Popuwindow_jb(PetitionStaffImportantMonitorAddMatterActivity.this,this);
            popuwindow_jb.setOnDismissListener(new PetitionStaffImportantMonitorAddMatterActivity.poponDismissListener());
            //显示窗口
            popuwindow_jb.showAtLocation(this.findViewById(R.id.textView33), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        }else if (R.id.rl_dz==id){
            openActivity(PetitionStaffChangePersonalInfoAddress.class);
        }else if (R.id.rl_wtlx==id){
            openActivity(PetitionStaffImportantMonitorAddMatterWTTypeActivity.class);
        }else if (R.id.rl_xfmd==id){
            openActivity(PetitionStaffImportantMonitorAddMatterXFMDActivity.class);
        }else if (R.id.rl_lfrq==id){
            final Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(PetitionStaffImportantMonitorAddMatterActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    c.set(year, monthOfYear, dayOfMonth);
                    mBinding.txtLfrq.setText(DateFormat.format("yyy-MM-dd", c));
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if (R.id.rl_ffrq==id){
            final Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(PetitionStaffImportantMonitorAddMatterActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    c.set(year, monthOfYear, dayOfMonth);
                    mBinding.txtFfrq.setText(DateFormat.format("yyy-MM-dd", c));
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if (R.id.rl_xbrq==id){
            final Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(PetitionStaffImportantMonitorAddMatterActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    c.set(year, monthOfYear, dayOfMonth);
                    mBinding.txtXbrq.setText(DateFormat.format("yyy-MM-dd", c));
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }else if (R.id.rl_fj==id){
            openActivity(PetitionStaffImportantMonitorAddMatterAdjunctActivity.class);
        }
    }

    @Override
    protected void initData() {
        sp=getSharedPreferences(getString(R.string.petition_staff_short_time_sp),MODE_PRIVATE);
        editor=sp.edit();
    }

    @Override
    protected void initView() {
        setWindowStatusBarColor(this,R.color.petition_staff_0066ba);
        mBinding= DataBindingUtil.setContentView(this, R.layout.petition_staff_activity_important_monitor_addmatter);
        instance=this;
        mBinding.rlJb.setOnClickListener(this);
        mBinding.rlDz.setOnClickListener(this);
        mBinding.rlWtlx.setOnClickListener(this);
        mBinding.rlXfmd.setOnClickListener(this);
        mBinding.rlLfrq.setOnClickListener(this);
        mBinding.rlFfrq.setOnClickListener(this);
        mBinding.rlXbrq.setOnClickListener(this);
        mBinding.rlFj.setOnClickListener(this);
        mBinding.rlNext.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!sp.getString(getString(R.string.petition_staff_QY_SH),"").equals("")){
            mBinding.txtAddress.setText(sp.getString(getString(R.string.petition_staff_QY_SH),"")+
                    sp.getString(getString(R.string.petition_staff_QY_S),"")+
                    sp.getString(getString(R.string.petition_staff_QY_X),""));
        }
        if (!sp.getString(getString(R.string.petition_staff_sp_JYLXY),"").equals("")){
            mBinding.txtWtlx.setText(sp.getString(getString(R.string.petition_staff_sp_JYLXY),"")
                    +sp.getString(getString(R.string.petition_staff_JYLXE),"")
                    +sp.getString(getString(R.string.petition_staff_JYLXS),""));
        }
        if (!sp.getString(getString(R.string.petition_staff_sp_XFMD),"").equals("")){
            mBinding.txtXfmd.setText(sp.getString(getString(R.string.petition_staff_sp_XFMD),""));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.clear().commit();
    }

    @Override
    protected void initEvent() {

    }

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }
    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    private void save(){
        if (mBinding.ckbSFFH.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_1),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_1),"0");
        }
        if (mBinding.ckbFYSFSL.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_2),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_2),"0");
        }
        if (mBinding.ckbSFXZFY.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_3),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_3),"0");
        }
        if (mBinding.ckbZCJGSFSL.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_4),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_4),"0");
        }
        if (mBinding.ckbSFGK.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_5),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_5),"0");
        }
        if (mBinding.ckbSFZDJJ.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_6),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_6),"0");
        }
        if (mBinding.ckbSFJA.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_7),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_7),"0");
        }
        if (mBinding.ckbSFLMX.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_8),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_8),"0");
        }
        if (mBinding.ckbSFYY.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_9),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_9),"0");
        }
        if (mBinding.ckbSFSS.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_10),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_10),"0");
        }
        if (mBinding.ckbSGBZ.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_11),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_11),"0");
        }
        if (mBinding.ckbSABZ.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_12),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_12),"0");
        }
        if (mBinding.ckbSTBZ.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_13),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_13),"0");
        }
        if (mBinding.ckbSWBZ.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_14),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_14),"0");
        }
        if (mBinding.ckbSQBZ.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_15),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_15),"0");
        }
        if (mBinding.ckbCFF.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_16),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_16),"0");
        }
        if (mBinding.ckbYJF.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_17),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_17),"0");
        }
        if (mBinding.ckbFZCSF.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_18),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_18),"0");
        }
        if (mBinding.ckbYZFWDJ.isChecked()==true){
            editor.putString(getString(R.string.petition_staff_sp_19),"1");
        }else {
            editor.putString(getString(R.string.petition_staff_sp_19),"0");
        }
            editor.commit();
    }

    private void sfWancheng(){
        if (mBinding.txtJb.getText().equals("")){

        }

    }

}
