package com.example.android.porticode;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.models.Account;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView balanceView = (TextView) findViewById(R.id.balance_text);
        BankThings.GetBalance(new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                balanceView.setText(((List<Account>)result).get(0).getBalance().toString());
            }

            @Override
            public void onFailure(NessieError error) {

            }
        });
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        Button send = findViewById(R.id.sendButton),
                recv = findViewById(R.id.receiveButton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                awaitTransfer = true;
            }
        });
        recv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BankThings.BeamAccountId(MainActivity.this);
            }
        });
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
        awaitTransfer = false;
        NdefMessage[] messages = getNdefMessages(intent);
        Log.d("NfcReceiver", displayByteArray(messages[0].toByteArray()));
        TextView amtTv = findViewById(R.id.amtTv);
        TextView descTv = findViewById(R.id.descTv);
        BankThings.MakeTransfer(displayByteArray(messages[0].toByteArray()), Double.parseDouble(amtTv.getText().toString()), descTv.getText().toString(), new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                //easy
                Toast.makeText(MainActivity.this, "Transfer submitted!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(NessieError error) {
                //not easy
            }
        });
    }
}
