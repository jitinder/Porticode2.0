package com.example.android.porticode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.reimaginebanking.api.nessieandroidsdk.models.Transfer;

import java.util.ArrayList;

/**
 * Created by Sam on 29/10/2017.
 */

public class TransferHistoryAdaptor extends ArrayAdapter<Transfer> {
    public TransferHistoryAdaptor (Context ctx, ArrayList<Transfer> trfs){
        super(ctx, 0, trfs);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        Transfer trfx = getItem(pos);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_transfer, parent);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
        TextView tvAmt = (TextView) convertView.findViewById(R.id.tvAmount);
        TextView tvDir = (TextView) convertView.findViewById(R.id.tvDirection);

        if(trfx.getPayeeId().equals(BankThings.accountId)) {
            //we are the payee
            tvName.setText(trfx.getPayerId());
            tvDesc.setText(trfx.getDescription());
            tvAmt.setText(String.format("$%.2lf", trfx.getAmount()));
            tvDir.setText(" ↑ ");
        } else {
            //we are the payee
            tvName.setText(trfx.getPayeeId());
            tvDesc.setText(trfx.getDescription());
            tvAmt.setText(String.format("$%.2lf", trfx.getAmount()));
            tvDir.setText(" ↓ ");
        }

        return convertView;
    }
}
