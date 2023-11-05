package org.auth;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        NumberSequenceSystem numberSequenceSystem = new NumberSequenceSystem();
        numberSequenceSystem.generateRandomSequence(5, 100);
        List<Integer> userInput = List.of(1, 4, 7, 2, 9);

        if (numberSequenceSystem.grantAccess(numberSequenceSystem.getSequence(), userInput)) {
            System.out.println("Access granted.");
        } else {
            System.out.println("Access denied.");
        }
    }
}
