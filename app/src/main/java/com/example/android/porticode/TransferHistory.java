package com.example.android.porticode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.models.Transfer;

import java.util.ArrayList;
import java.util.List;

public class TransferHistory extends AppCompatActivity {

    private TransferHistoryAdaptor mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_history);


        BankThings.GetTransferHistory(new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                Log.d("balance", "pull success");
                mAdapter = new TransferHistoryAdaptor(TransferHistory.this, (ArrayList<Transfer>) result);
                ListView listView = findViewById(R.id.list_view);
                listView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(NessieError error) {
                Log.e("balance", "failed with err:");
                Log.e("balance", error.getMessage());
            }
        });

        //TransferHistoryAdaptor transferHistoryAdaptor = new TransferHistoryAdaptor(BankThings.GetTransferHistory());
        //ListView listView = findViewById(R.id.list_view);
        //listView.setAdapter(transferHistoryAdaptor);
    }
}
