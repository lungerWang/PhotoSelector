package com.lunger.photoselect.permission;

import java.util.List;

/**
 * Created by honey_chen on 2017/2/9.
 */

public interface PermissionListener
{
    void onSucceed(int requestCode, List<String> grantPermissions);

    void onFailed(int requestCode, List<String> deniedPermissions);

}
