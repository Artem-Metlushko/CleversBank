package com.clever.bank;

import com.clever.bank.factory.FactoryGeneric;
import com.clever.bank.factory.FactoryService;
import com.clever.bank.model.Transaction;
import com.clever.bank.service.TransThread;
import com.clever.bank.service.TransactionHandler;
import com.clever.bank.service.TransactionService;
import com.clever.bank.util.DatabaseInitializerUtil;


public class CleverBankApplication {
    public static void main(String[] args) throws InterruptedException {

        DatabaseInitializerUtil.runSqlScripts();
         TransactionHandler transactionHandler = FactoryGeneric.getTransactionHandler();
        TransactionService transactionService = FactoryService.getTransactionService();
        Transaction transaction = transactionService.findById("1");



//        System.out.println(transaction);

        /*for (int i = 0; i < 15; i++) {
            transactionHandler.handle(transaction.getTransactionId());
        }*/


        threads(transaction);


    }

    private static void threads(Transaction transaction) throws InterruptedException {


        TransactionHandler th = FactoryGeneric.getTransactionHandler();


        TransThread thread = new TransThread(th, transaction);
        TransThread thread2 = new TransThread(th, transaction);
        TransThread thread3 = new TransThread(th, transaction);
        TransThread thread4 = new TransThread(th, transaction);
        TransThread thread5 = new TransThread(th, transaction);
        TransThread thread6 = new TransThread(th, transaction);
        TransThread thread7 = new TransThread(th, transaction);
        TransThread thread8 = new TransThread(th, transaction);

        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();

        thread.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
        thread7.join();
        thread8.join();
    }
}