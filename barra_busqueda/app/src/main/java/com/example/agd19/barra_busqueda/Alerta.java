package com.example.agd19.barra_busqueda;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.EditText;

/**
 * Created by agd19 on 01/06/2018.
 */

public class Alerta implements Runnable {

    private activity_alertas p;
    //private String num;
    private EditText etvalor;
    private Double n1;
    private Boolean boleano = true;

    public Alerta(EditText et1, activity_alertas p1, Double establecido)
    {
        //num = num1;
        p = p1;
        etvalor = et1;
        n1 = establecido;
    }


    @Override
    public void run() {
        while(boleano) {

            SystemClock.sleep(5000);
            String num = etvalor.getText().toString();
            Double numero = Double.parseDouble(num);
            if (numero < n1) {
                NotificationCompat.Builder notificacion = new NotificationCompat.Builder(p);
                notificacion.setSmallIcon(R.drawable.wallet);
                notificacion.setTicker("Nueva notificación");
                notificacion.setContentTitle("Mensaje");
                Uri sonido = RingtoneManager.getDefaultUri(Notification.DEFAULT_SOUND);
                notificacion.setSound(sonido);

               /* Bitmap icono = BitmapFactory.decodeResource(getResources(), R.drawable.wallet);
                notificacion.setLargeIcon(icono);*/

                PendingIntent pendingIntent;
                Intent intent = new Intent();

                Context context = p.getApplicationContext();
                intent.setClass(context, MainActivity.class);
                intent.putExtra("ID", 1);
                pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                notificacion.setContentIntent(pendingIntent);

                Notification n = notificacion.build();
                NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(1, n);
                boleano = false;
            }
        }

    }

}
