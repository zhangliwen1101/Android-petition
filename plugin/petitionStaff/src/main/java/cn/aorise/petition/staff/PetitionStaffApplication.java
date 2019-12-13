package cn.aorise.petition.staff;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.util.Log;

import cn.aorise.common.core.interfaces.IAppCycle;
import cn.aorise.common.core.utils.assist.DebugUtil;
import cn.aorise.petition.staff.config.Config;

public class PetitionStaffApplication implements IAppCycle {
    private static final String TAG = PetitionStaffApplication.class.getSimpleName();


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void create(Application context) {
        Log.i(TAG, "init");
        // AoriseLog.init(DebugUtil.isDebug(), BuildConfig.APPLICATION_ID);
        // DbHelper.getInstance().init(context);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        DebugUtil.setDebug(Config.sIsDebug);
    }

    @Override
    public void destroy(Application context, boolean isKillProcess) {
        // DbHelper.getInstance().close();
    }
}
