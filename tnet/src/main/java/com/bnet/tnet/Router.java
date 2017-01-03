package com.bnet.tnet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bnet.shared.model.backend.Providable;
import com.bnet.shared.model.services.utils.ProvidableUtils;

public class Router {
    private static Router ourInstance = new Router();

    public static Router getInstance() {
        return ourInstance;
    }

    private Router() {
    }

    public void startActivity(Context context, Class<? extends Activity> activity, Providable providable) {
        Intent intent = new Intent(context, activity);

        Bundle bundle = ProvidableUtils.bundleConvert(providable);
        intent.putExtra(ProvidableUtils.getURIPath(providable), bundle);

        context.startActivity(intent);
    }
}
