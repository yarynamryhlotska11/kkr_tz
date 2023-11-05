package org.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberSequenceSystem {
    private List<Integer> sequence;

    public NumberSequenceSystem() {
        sequence = new ArrayList<>();
    }

    public void generateRandomSequence(int length, int bound) {
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sequence.add(random.nextInt(bound));
        }
        System.out.println("Згенерована послідовність: " + sequence);
    }

    // Method to validate user input against the generated sequence
    public boolean validateUserInput(List<Integer> userInput) {
        return sequence.equals(userInput);
    }

    // Method to grant access based on user input validation
    public boolean grantAccess(List<Integer> sequence, List<Integer> userInput) {
        if (sequence.size() != userInput.size()) {
            return false;
        }
        for (int i = 0; i < sequence.size(); i++) {
            if (!sequence.get(i).equals(userInput.get(i))) {
                return false;
            }
        }
        System.out.println("Доступ надано.");
        return true;
    }



    // Method to clear the sequence
    public void clearSequence() {
        sequence.clear();
    }

    // Method to get the generated sequence
    public List<Integer> getSequence() {
        return sequence;
    }
}
