package com.lunger.photoselect.permission;

/**
 * Created by honey_chen on 2017/2/7.
 */

public interface Rationale extends CancelablePermission
{
    /**
     * Go request permission.
     */
    void resume();
}
