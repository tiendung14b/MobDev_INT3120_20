package com.example.slide12;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Objects;

public class MySMSReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    if (Objects.equals(intent.getAction(), "android.provider.Telephony.SMS_RECEIVED")) {
      SmsMessage[] messages;
      Bundle bundle = intent.getExtras();
      Object[] pdus = (Object[]) bundle.get("pdus");
      messages = new SmsMessage[pdus.length];
      for (int i = 0; i < pdus.length; i++)
          messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
      Toast.makeText(context, messages[0].getMessageBody(), Toast.LENGTH_LONG).show();
    }
  }
}
