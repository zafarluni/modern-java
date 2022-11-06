package com.luni.main;

import com.luni.streams.StreamBasics;

public class App 
{
    public static void main( String[] args )
    {
        StreamBasics basics = new StreamBasics();
        basics.getAllTransactionsByYear(2011);
    }
}
