package com.lunger.photoselect.permission.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.lunger.photoselect.R;
import com.lunger.photoselect.permission.Rationale;


/**
 * Created by honey_chen on 2017/2/7.
 */

public class RationaleDialog
{
    private AlertDialogPermission.Builder mBuilder;
    private Rationale mRationale;


    public RationaleDialog(@NonNull Context context, @NonNull Rationale rationale)
    {
        mBuilder = (AlertDialogPermission.Builder) AlertDialogPermission.build(context)
                .setCancelable(false)
                .setTitle(R.string.permission_title_permission_rationale)
                .setMessage(R.string.permission_message_permission_rationale)
                .setPositiveButton(R.string.permission_resume, mClickListener)
                .setNegativeButton(R.string.permission_cancel, mClickListener);
        this.mRationale = rationale;
    }
    @NonNull
    public RationaleDialog setTitle(@NonNull String title) {
        mBuilder.setTitle(title);
        return this;
    }

    @NonNull
    public RationaleDialog setTitle(@StringRes int title) {
        mBuilder.setTitle(title);
        return this;
    }

    @NonNull
    public RationaleDialog setMessage(@NonNull String message) {
        mBuilder.setMessage(message);
        return this;
    }

    @NonNull
    public RationaleDialog setMessage(@StringRes int message) {
        mBuilder.setMessage(message);
        return this;
    }

    @NonNull
    public RationaleDialog setNegativeButton(@NonNull String text, @Nullable DialogInterface.OnClickListener
            negativeListener) {
        mBuilder.setNegativeButton(text, negativeListener);
        return this;
    }

    @NonNull
    public RationaleDialog setNegativeButton(@StringRes int text, @Nullable DialogInterface.OnClickListener
            negativeListener) {
        mBuilder.setNegativeButton(text, negativeListener);
        return this;
    }

    @NonNull
    public RationaleDialog setPositiveButton(@NonNull String text) {
        mBuilder.setPositiveButton(text, mClickListener);
        return this;
    }

    @NonNull
    public RationaleDialog setPositiveButton(@StringRes int text) {
        mBuilder.setPositiveButton(text, mClickListener);
        return this;
    }

    public void show() {
        mBuilder.show();
    }
    /**
     * The dialog's btn click listener.
     */
    private DialogInterface.OnClickListener mClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_NEGATIVE:
                    mRationale.cancel();
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    mRationale.resume();
                    break;
            }
        }
    };
}
