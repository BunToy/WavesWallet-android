package com.wavesplatform.wallet.v1.data.datamanagers;

import android.support.annotation.NonNull;

import com.wavesplatform.wallet.v1.api.NodeManager;
import com.wavesplatform.wallet.v1.data.stores.TransactionListStore;
import com.wavesplatform.wallet.v1.payload.AssetBalance;
import com.wavesplatform.wallet.v1.payload.Transaction;
import com.wavesplatform.wallet.v1.payload.TransactionMostRecentDateComparator;

import java.util.ArrayList;
import java.util.List;

public class TransactionListDataManager {

    private TransactionListStore transactionListStore;

    public TransactionListDataManager(TransactionListStore transactionListStore) {
        this.transactionListStore = transactionListStore;
    }

       public void generateTransactionList(AssetBalance ab) {
        transactionListStore.insertTransactions(getAllTransactions(ab));
        transactionListStore.sort(new TransactionMostRecentDateComparator());
    }

    @NonNull
    public List<Transaction> getTransactionList() {
        return transactionListStore.getList();
    }

    public void clearTransactionList() {
        transactionListStore.clearList();
    }


    private List<Transaction> getAllTransactions(AssetBalance ab) {
        List<Transaction> transactions = new ArrayList<>();

        if (ab == null ) {
            transactions.addAll(NodeManager.get().pendingTransactions);
            transactions.addAll(NodeManager.get().transactions);
        } else {
            transactions.addAll(NodeManager.get().getPendingAssetTransactions(ab));
            transactions.addAll(NodeManager.get().getAssetTransactions(ab));
        }

        return transactions;
    }


}
