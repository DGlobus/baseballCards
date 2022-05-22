package com.dasha;

import com.dasha.getCards.GetCardsFromTxt;
import com.dasha.printCards.PrintToConsole;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        if(args.length == 0)
            throw new IllegalArgumentException("There is no imparted parameters");
        String path = args[0];

        GetCardsFromTxt getCards = new GetCardsFromTxt();
        List<Employee> employees = getCards.read(path);
        PrintToConsole print= new PrintToConsole(employees);
        print.sort();
        print.print();
    }
}
