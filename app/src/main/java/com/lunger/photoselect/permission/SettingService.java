package com.lunger.photoselect.permission;

/**
 * Created by honey_chen on 2017/2/8.
 */

public interface SettingService extends CancelablePermission
{
    /**
     * Execute setting.
     */
    void execute();
}
