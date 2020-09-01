package com.longcheng.lifecareplan.utils.Permission;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import java.util.List;

public class PermissionUtil {

    private static final String TAG = PermissionUtil.class.getSimpleName();

    private PermissionFragment fragment;

    public PermissionUtil(@NonNull FragmentActivity activity) {
        fragment = getRxPermissionsFragment(activity);
    }

    private PermissionFragment getRxPermissionsFragment(FragmentActivity activity) {
        PermissionFragment fragment = (PermissionFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
        boolean isNewInstance = fragment == null;
        if (isNewInstance) {
            fragment = new PermissionFragment();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, TAG)
                    .commit();
            fragmentManager.executePendingTransactions();
        }

        return fragment;
    }

    /**
     * 外部使用 申请权限
     * @param permissions 申请授权的权限
     * @param listener 授权回调的监听
     */
    public void requestPermissions(String[] permissions, PermissionListener listener) {
        fragment.setListener(listener);
        fragment.requestPermissions(permissions);
    }
    /**
     * 点击检查 相机、打电话 权限
     */
    public void setPermiss(FragmentActivity context) {
        requestPermissions(new String[]{
                        Manifest.permission.NFC,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                new PermissionListener() {
                    @Override
                    public void onGranted() {
                        //所有权限都已经授权
                        Toast.makeText(context, "所有权限都已授权", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        //Toast第一个被拒绝的权限
                        Toast.makeText(context, "拒绝了权限" + deniedPermission.get(0), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onShouldShowRationale(List<String> deniedPermission) {
                        //Toast第一个勾选不在提示的权限
                        Toast.makeText(context, "这个权限" + deniedPermission.get(0) + "勾选了不在提示，要像用户解释为什么需要这权限", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
