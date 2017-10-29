package com.example.android.porticode;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.util.Log;
import android.widget.Toast;

import java.nio.charset.Charset;

/**
 * Created by Sam on 29/10/2017.
 */

public class NFCThings implements NfcAdapter.OnNdefPushCompleteCallback{

    //the recipient is the one beaming
    NfcAdapter mNfcAdapter;
    Activity c;

    NFCThings(Activity a){
        c = a;
    }

    void PrepareBeamTag(String data){
        mNfcAdapter = NfcAdapter.getDefaultAdapter(c);
        if (mNfcAdapter == null) {
            Log.e("NFCThing", "NFC is not available on this device.");
            return;
        }
        // Register callback
        String type = "application/com.example.android.porticode";
        byte[] mimeBytes = type.getBytes(Charset.forName("UTF-8"));
        byte[] dataBytes = data.getBytes(Charset.forName("UTF-8"));
        byte[] id = new byte[0];
        NdefRecord record = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeBytes, id, dataBytes);
        NdefMessage message = new NdefMessage(new NdefRecord[] { record });
        mNfcAdapter.setNdefPushMessage(message, c);
        mNfcAdapter.setOnNdefPushCompleteCallback(this, c);
        Log.e("nfc", "set push");
    }

    @Override
    public void onNdefPushComplete(NfcEvent nfcEvent) {
        //it's done
        //???
        Toast.makeText(c, "Transfer submitted!", Toast.LENGTH_SHORT).show();
        //c.finish();
    }
}
