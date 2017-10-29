package com.example.android.porticode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.reimaginebanking.api.nessieandroidsdk.models.Transfer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sam on 29/10/2017.
 */


public class TransferHistoryAdaptor extends ArrayAdapter<Transfer> {
    public TransferHistoryAdaptor (Context ctx, ArrayList<Transfer> trfs){
        super(ctx, 0, trfs);
    }

    HashMap<String, String> mapper = new HashMap<String, String>();

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        mapper.put("59f5acd8b390353c953a19dd", "Sam Gao");
        mapper.put("59f5ad7cb390353c953a19df", "Sidak");


        Transfer trfx = getItem(pos);
        View row = null;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            row = inflater.inflate(R.layout.item_transfer, parent,
                    false);
        } else {
            row = convertView;
        }
        //if(convertView == null) {
        //    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_transfer, null);
        //}
        TextView tvName = (TextView) row.findViewById(R.id.tvName);
        TextView tvDesc = (TextView) row.findViewById(R.id.tvDesc);
        TextView tvAmt = (TextView) row.findViewById(R.id.tvAmount);
        TextView tvDir = (TextView) row.findViewById(R.id.tvDirection);
        TextView tvState = (TextView) row.findViewById(R.id.tvStatus);

        if(trfx.getPayeeId().equals(BankThings.accountId)) {
            //we are the payee
            tvName.setText(mapper.get(trfx.getPayerId()));
            tvDesc.setText(trfx.getDescription());
            tvAmt.setText(String.format("$%.2f", trfx.getAmount()));
            tvDir.setText(" ↓ ");
        } else {
            //we are the payee
            tvName.setText(mapper.get(trfx.getPayeeId()));
            tvDesc.setText(trfx.getDescription());
            tvAmt.setText(String.format("$%.2f", trfx.getAmount()));
            tvDir.setText(" ↑ ");
        }

        tvState.setText(trfx.getStatus());

        return row;
    }
}
