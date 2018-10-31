package com.lunger.photoselect.permission.dialog;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by honey_chen on 2017/2/7.
 */

public class AlertDialogPermission extends AlertDialog
{

    protected AlertDialogPermission(Context context)
    {
        super(context);
    }

    protected AlertDialogPermission(Context context, boolean cancelable, OnCancelListener cancelListener)
    {
        super(context, cancelable, cancelListener);
    }

    protected AlertDialogPermission(Context context, int themeResId)
    {
        super(context, themeResId);
    }
    public static Builder build(Context context){
        return new Builder(context);
    }
    public static class Builder extends AlertDialog.Builder{

        public Builder(Context context)
        {
            super(context);
        }

        public Builder(Context context, int themeResId)
        {
            super(context, themeResId);
        }
    }
}
