package com.example.slide12;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneStateChangedReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle bundle = intent.getExtras();
    if (bundle != null) {
      Object[] pdus = (Object[]) bundle.get("pdus");
      SmsMessage[] messages = new SmsMessage[pdus.length];
      for (int i = 0; i < pdus.length; i++)
        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
      for (SmsMessage message : messages) {
        String msg = message.getMessageBody();
        long when = message.getTimestampMillis();
        String from = message.getOriginatingAddress();
        Toast.makeText(context, from+":"+ msg + " when: " + when, Toast.LENGTH_LONG).show();
      }
    }
  }
}