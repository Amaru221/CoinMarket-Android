package com.example.agd19.barra_busqueda;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by agd19 on 01/02/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String LOGTAG = "android-fcm";
    @Override
    public void onTokenRefresh() {
        //Se obtiene el token actualizado
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(LOGTAG, "Token actualizado: " + refreshedToken);
    }
}
