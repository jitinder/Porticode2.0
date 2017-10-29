package com.example.android.porticode;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;

public class MainActivity extends AppCompatActivity {

    private PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView balanceView = (TextView) findViewById(R.id.balance_text);
        balanceView.setText(String.valueOf(BankThings.GetBalance()));
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    private NdefMessage[] getNdefMessages(Intent intent) {
        Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMessages != null) {
            NdefMessage[] messages = new NdefMessage[rawMessages.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = (NdefMessage) rawMessages[i];
            }
            return messages;
        } else {
            return null;
        }
    }

    static String displayByteArray(byte[] bytes) {
        String res="";
        StringBuilder builder = new StringBuilder().append("[");
        for (int i = 0; i < bytes.length; i++) {
            res+=(char)bytes[i];
        }
        return res;
    }

    boolean awaitTransfer = false;

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        if(!awaitTransfer) return;
        NdefMessage[] messages = getNdefMessages(intent);
        Log.d("NfcReceiver", displayByteArray(messages[0].toByteArray()));
        TextView amtTv = findViewById(R.id.amtTv);
        //BankThings.MakeTransfer(displayByteArray(messages[0].toByteArray()), Double.parseDouble(amtTv.getText().toString()), );
    }
}
