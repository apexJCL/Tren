package com.ariel.android;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import com.ariel.ActionResolver;

/**
 * Created by apex on 12/15/15.
 */
public class ActionResolverAndroid implements ActionResolver {

    private Context context;
    private Handler handler;

    public ActionResolverAndroid(Context context) {
        handler = new Handler();
        this.context = context;
    }

    @Override
    public void showToast(final CharSequence message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
