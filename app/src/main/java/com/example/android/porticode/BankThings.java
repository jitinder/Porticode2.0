package com.example.android.porticode;

import android.util.Log;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.TransactionMedium;
import com.reimaginebanking.api.nessieandroidsdk.models.Address;
import com.reimaginebanking.api.nessieandroidsdk.models.Customer;
import com.reimaginebanking.api.nessieandroidsdk.models.Transfer;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

/**
 * Created by Sam on 28/10/2017.
 */

public class BankThings {

    NessieClient bankClient = NessieClient.getInstance("78b8262aaa957f0aa1be6b9b6dc2f827");
    static String userId = "";

    //ideally a lot of this should be abstracted to a server but i'm too lazy

    void RegisterNewAccount(final String username, String password, String firstName, String lastName, Address address, final Runnable callback){
        //the password is actually ignored
        bankClient.CUSTOMER.createCustomer(
                new Customer(username, firstName, lastName, address),
                new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object result) {
                        //created account!
                        userId = username;
                        callback.run();
                    }

                    @Override
                    public void onFailure(NessieError error) {
                        Log.e("BankAPIController", "failed to create account with error:");
                        Log.e("BankAPIController", error.getMessage());
                    }
                }
        );
    }

    boolean Login(String username, String password){
        userId = username;
        return true;
    }

    void GetAccounts(NessieResultsListener callback){
            bankClient.ACCOUNT.getCustomerAccounts(userId, callback);
    }

    void GetBalance(String accountId, NessieResultsListener callback){
        if(userId.equals("")) {
            callback.onFailure(null);
            return;
        }
        bankClient.ACCOUNT.getAccount(accountId, callback);
    }

    void MakeTransfer(String accountId, String recipientId, float amount, String description, NessieResultsListener callback){
        Transfer.Builder builder = new Transfer.Builder();
        builder.amount(amount);
        builder.description(description);
        builder.payeeId(recipientId);
        builder.medium(TransactionMedium.BALANCE);
        bankClient.TRANSFER.createTransfer(accountId, builder.build(), callback);
    }



}
