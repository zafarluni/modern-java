package com.luni.streams;

import java.util.Comparator;
import java.util.stream.Stream;

import com.luni.streams.domain.DS;
import com.luni.streams.domain.Transaction;

public class StreamBasics {

    public void getAllTransactionsByYear(int year) {
        Stream<Transaction> transactions = DS.getTransactions().stream().filter(x -> x.getYear() == year)
                .sorted(Comparator.comparing(Transaction::getValue));

        transactions.forEach(System.out::println);
    }
}
