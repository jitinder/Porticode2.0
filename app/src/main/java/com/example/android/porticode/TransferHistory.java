package com.example.android.porticode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class TransferHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_history);

        //TransferHistoryAdaptor transferHistoryAdaptor = new TransferHistoryAdaptor(BankThings.GetTransferHistory());
        //ListView listView = findViewById(R.id.list_view);
        //listView.setAdapter(transferHistoryAdaptor);
    }
}
