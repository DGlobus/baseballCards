package com.dasha;

import com.dasha.getCards.GetCardsFromTxt;
import com.dasha.printCards.PrintToConsole;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here

        System.out.println("Enter file-path");
        Scanner in = new Scanner(System.in);
        System.out.println();
        String path = in.nextLine();

        GetCardsFromTxt getCards = new GetCardsFromTxt();
        List<Employee> employees = getCards.read(path);
        PrintToConsole print= new PrintToConsole(employees);
        print.sort();
        print.print();
    }
}
