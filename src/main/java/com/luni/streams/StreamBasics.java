package com.luni.streams;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import com.luni.streams.domain.DS;
import com.luni.streams.domain.Trader;
import com.luni.streams.domain.Transaction;

public class StreamBasics {

    private void printAllTransactionsByYear(int year) {
        Stream<Transaction> transactions = DS.getTransactions().stream().filter(x -> x.getYear() == year)
                .sorted(Comparator.comparing(Transaction::getValue));

        transactions.forEach(System.out::println);
    }

    private void printUniqueCities() {
        Stream<String> uniqueCities = DS.getTransactions().stream().map(x -> x.getTrader().getCity()).distinct();
        uniqueCities.forEach(System.out::println);
    }

    private void printTradersByCity(String city) {
        Stream<Trader> traders = DS.getTransactions().stream().map(Transaction::getTrader).distinct()
                .filter(x -> x.getCity().equals(city)).sorted(Comparator.comparing(Trader::getName));
        traders.forEach(System.out::println);
    }

    private void printIfAnyTraderInMilan() {
        Optional<Trader> traders = DS.getTransactions().stream().map(Transaction::getTrader)
                .filter(x -> x.getCity().equals("Milan")).findAny();
        System.out.println("Are any traders from Milan: " + traders.isPresent());
    }

    private void printHighestTransactionByValue() {
        Optional<Transaction> maxTransaction = DS.getTransactions().stream()
                .max(Comparator.comparing(Transaction::getValue));
        System.out.println("Max Transaction: " + maxTransaction.get());
    }

    private void printSortedStringOfTraderNames() {
        String names = DS.getTransactions().stream().map(x -> x.getTrader().getName()).distinct().sorted().reduce("",
                (a, b) -> a + " " + b);
        System.out.println("Sorted Names of Traders: " + names);
    }

    public static void execute(){
        StreamBasics basics = new StreamBasics();
        basics.printAllTransactionsByYear(2011);
        basics.printUniqueCities();
        basics.printTradersByCity("Cambridge");
        basics.printIfAnyTraderInMilan();
        basics.printHighestTransactionByValue();
        basics.printSortedStringOfTraderNames();
    }
}
