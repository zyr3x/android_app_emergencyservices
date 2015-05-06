package com.emergencyservices;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyPhoneListener phoneListener = new MyPhoneListener();
        TelephonyManager telephonyManager =
            (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onClick(View view) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+getResources().getString(R.string.tel)));
        startActivity(callIntent);
        finish();
    }

    private class MyPhoneListener extends PhoneStateListener {
        private boolean onCall = false;

        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    onCall = true;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (onCall == true) {
                        Intent restart = getBaseContext().getPackageManager().
                        getLaunchIntentForPackage(getBaseContext().getPackageName());
                        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(restart);
                        onCall = false;
                    }
                    break;
                default:
                    break;
            }
        }
    }



}
