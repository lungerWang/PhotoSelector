package com.lunger.photoselect.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by honey_chen on 2017/2/8.
 */

public class DefaultPermission implements Permission
{
    private String[] permissions;
    private String[] deniedPermissions;
    private int requestCode;
    private Object object;
    private RationaleListener listener;

    DefaultPermission(Object object)
    {
        if (object == null)
        {
            throw new IllegalArgumentException("The object can not be null.");
        }
        this.object = object;
    }

    @NonNull
    @Override
    public Permission permission(String... permissions)
    {
        if (permissions == null)
            throw new IllegalArgumentException("The permissions can not be null.");
        this.permissions = permissions;
        return this;
    }

    @NonNull
    @Override
    public Permission requestCode(int requestCode)
    {
        this.requestCode = requestCode;
        return this;
    }

    @NonNull
    @Override
    public Permission rationale(RationaleListener listener)
    {
        this.listener = listener;
        return this;
    }

    /**
     * Rationale.
     */
    private Rationale rationale = new Rationale()
    {
        @Override
        public void cancel()
        {
            int[] results = new int[permissions.length];
            Context context = PermissionUtils.getContext(object);
            for (int i = 0; i < results.length; i++)
            {
                results[i] = ActivityCompat.checkSelfPermission(context, permissions[i]);
            }
            onRequestPermissionsResult(object, requestCode, permissions, results);
        }

        @Override
        public void resume()
        {
            requestPermissions(object, requestCode, deniedPermissions);
        }
    };

    @Override
    public void send()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            Context context = PermissionUtils.getContext(object);

            final int[] grantResults = new int[permissions.length];
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();

            final int permissionCount = permissions.length;
            for (int i = 0; i < permissionCount; i++)
            {
                grantResults[i] = packageManager.checkPermission(permissions[i], packageName);
            }
            onRequestPermissionsResult(object, requestCode, permissions, grantResults);
        } else
        {
            deniedPermissions = getDeniedPermissions(object, permissions);
            // Denied permissions size > 0.
            if (deniedPermissions.length > 0)
            {
                // Remind users of the purpose of permissions.
                boolean showRationale = PermissionUtils.shouldShowRationalePermissions(object, deniedPermissions);
                if (showRationale && listener != null)
                {
                    listener.showRequestPermissionRationale(requestCode, rationale);
                } else
                {
                    rationale.resume();
                }
            } else
            { // All permission granted.
                final int[] grantResults = new int[permissions.length];

                final int permissionCount = permissions.length;
                for (int i = 0; i < permissionCount; i++)
                {
                    grantResults[i] = PackageManager.PERMISSION_GRANTED;
                }
                onRequestPermissionsResult(object, requestCode, permissions, grantResults);
            }
        }
    }

    private static String[] getDeniedPermissions(Object object, String... permissions)
    {
        Context context = PermissionUtils.getContext(object);
        List<String> deniedList = new ArrayList<>(1);
        for (String permission : permissions)
        {
            if (!PermissionManage.hasPermission(context, permission))
            {
                deniedList.add(permission);
            }
        }
        return deniedList.toArray(new String[deniedList.size()]);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void requestPermissions(Object object, int requestCode, String... permissions)
    {
        if (object instanceof Activity)
        {
            ActivityCompat.requestPermissions(((Activity) object), permissions, requestCode);
        } else if (object instanceof android.support.v4.app.Fragment)
        {
            ((android.support.v4.app.Fragment) object).requestPermissions(permissions, requestCode);
        } else if (object instanceof android.app.Fragment)
        {
            ((android.app.Fragment) object).requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void onRequestPermissionsResult(Object o, int requestCode, @NonNull String[] permissions,
                                                   @NonNull int[] grantResults)
    {
        if (o instanceof Activity)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                ((Activity) o).onRequestPermissionsResult(requestCode, permissions, grantResults);
            } else if (o instanceof ActivityCompat.OnRequestPermissionsResultCallback)
            {
                ((ActivityCompat.OnRequestPermissionsResultCallback) o).onRequestPermissionsResult(requestCode,
                        permissions, grantResults);
            } else
            {
            }
        } else if (o instanceof android.support.v4.app.Fragment)
        {
            ((android.support.v4.app.Fragment) o).onRequestPermissionsResult(requestCode, permissions,
                    grantResults);
        } else if (o instanceof android.app.Fragment)
        {
            ((android.app.Fragment) o).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
