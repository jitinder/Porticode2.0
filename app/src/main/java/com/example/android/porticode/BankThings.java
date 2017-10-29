package com.example.android.porticode;

import android.app.Activity;
import android.util.Log;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.AccountType;
import com.reimaginebanking.api.nessieandroidsdk.constants.TransactionMedium;
import com.reimaginebanking.api.nessieandroidsdk.models.Account;
import com.reimaginebanking.api.nessieandroidsdk.models.Address;
import com.reimaginebanking.api.nessieandroidsdk.models.Customer;
import com.reimaginebanking.api.nessieandroidsdk.models.PostResponse;
import com.reimaginebanking.api.nessieandroidsdk.models.Transfer;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

import java.util.List;

/**
 * Created by Sam on 28/10/2017.
 */

public class BankThings {

    static NessieClient bankClient = NessieClient.getInstance("78b8262aaa957f0aa1be6b9b6dc2f827");
    static String userId = "";
    public static String accountId = "";
    static Customer me;
    static Account mine;

    //ideally a lot of this should be abstracted to a server but i'm too lazy

    static void RegisterNewAccount(final String username, String password, String firstName, String lastName, final Runnable callback){
        //the password is actually ignored
        Address.Builder builder = new Address.Builder();
        builder.streetNumber("602");
        builder.streetName("Tadpole Run Road");
        builder.city("Belpre");
        builder.state("Ohio");
        builder.zip("45714");
        Customer.Builder bd = new Customer.Builder();

        bankClient.CUSTOMER.createCustomer(
                new Customer(username, firstName, lastName, builder.build()),
                new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object result) {
                        //created account!
                        PostResponse<Customer> res = (PostResponse<Customer>)result;
                        userId = res.getObjectCreated().getId();
                        me = res.getObjectCreated();
                        CreateAccount(callback);
                    }

                    @Override
                    public void onFailure(NessieError error) {
                        Log.e("BankAPIController", "failed to create customer with error:");
                        Log.e("BankAPIController", error.getMessage());
                    }
                }
        );
    }

    static boolean Login(String username, String password){
        if(username.equals("sam")) userId = "59f543a0b390353c953a1953";
        else if (username.equals("americo")) userId = "59f4517aa73e4942cdafe4a5";
        else if (username.equals("otilia")) userId = "59f4517aa73e4942cdafe4a4";
        else return false;
        bankClient.CUSTOMER.getCustomer(userId, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                me = ((List<Customer>)result).get(0);
                bankClient.ACCOUNT.getCustomerAccounts(userId, new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object rest) {
                        mine = ((List<Account>)rest).get(0);
                        accountId = mine.getId();
                        //ok.
                    }

                    @Override
                    public void onFailure(NessieError error) {
                        Log.e("BankAPIController", "could not get account for customer. perhaps customer has no accounts?");
                    }
                });
            }

            @Override
            public void onFailure(NessieError error) {
                Log.e("BankAPIController", "could not find account with id, error:");
                Log.e("BankAPIController", error.getMessage());
            }
        });
        return true;
    }

    static void CreateAccount(final Runnable callback){
        Account.Builder builder = new Account.Builder();
        builder.balance((int)(Math.random() * 2000));
        builder.nickname("Account");
        builder.type(AccountType.CHECKING);
        bankClient.ACCOUNT.createAccount(userId, builder.build(), new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                PostResponse<Account> res = (PostResponse<Account>) result;
                accountId = res.getObjectCreated().getId();
                mine = res.getObjectCreated();
                callback.run();
            }

            @Override
            public void onFailure(NessieError error) {
                Log.e("BankAPIController", "failed to create account with error:");
                Log.e("BankAPIController", error.getMessage());
            }
        });

    }

    /*void GetAccounts(NessieResultsListener callback){
        bankClient.ACCOUNT.getCustomerAccounts(userId, callback);
    }*/

    static double GetBalance(){
        if(userId.equals("")) {
            return 0.0f;
        }
        return mine.getBalance();
    }

    static void MakeTransfer(String recipientId, double amount, String description, NessieResultsListener callback){
        Transfer.Builder builder = new Transfer.Builder();
        builder.amount(amount);
        builder.description(description);
        builder.payeeId(recipientId);
        builder.medium(TransactionMedium.BALANCE);
        bankClient.TRANSFER.createTransfer(accountId, builder.build(), callback);
    }

    static void BeamAccountId(Activity a){
        (new NFCThings(a)).PrepareBeamTag(accountId);
    }

    static void GetTransferHistory(NessieResultsListener callback){
        bankClient.TRANSFER.getTransfers(accountId, callback);
    }

}
