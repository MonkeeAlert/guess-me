package com.monkeealert.guessmegame;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean userWillStartPlaying = startGame();

        if(userWillStartPlaying) {
            beginGuessing();
        }
    }

    private static boolean startGame() {
        Scanner scanner = new Scanner(System.in);

        String name = prompt("Welcome to wonderland! What is your name?\n> ", scanner);

        String greeting = "Hello " + name + "! Shall we start?\n1. Yes\n2. No";
        boolean gameWillStart = getYesOrNo(greeting, scanner);

        return gameWillStart;
    }

    private static void beginGuessing() {
        Scanner scanner = new Scanner(System.in);

        Random random = new Random();
        int x = random.nextInt(10) + 1;

        // should not be reset
        int triesGlobal = 0;
        int gamesPlayed = 0;

        int triesLocal = 0;
        boolean shouldFinish = false;
        boolean userWantsToPlay = true;

        int userInput = getNumber("Please guess a number between 1 and 10\n> ", scanner);

        while(userWantsToPlay) {
            while (!shouldFinish) {
                triesLocal++;

                if(userInput == x) {
                    shouldFinish = true;
                } else {
                    userInput = getNumber("Guess " + (userInput > x ? "lower" : "higher" ) + "\n> ", scanner);
                }
            }

            if(gamesPlayed < 2) {
                System.out.println("Congratulations! You have guessed in your " + triesLocal + " guess.");
            } else {
                double percent = (double) gamesPlayed / triesGlobal;

                System.out.println("Congratulations! You have guessed in your " + triesLocal + " guess this time");
                System.out.println("Stats:");
                System.out.println("> Percent of winning: " + String.format("%.2f", percent));
                System.out.println("> All amount of tries: " + triesGlobal);
                System.out.println("> Games played: " + gamesPlayed);
            }

            boolean userWantsToPlayAgain = getYesOrNo("Want to play again?", scanner);
            if(userWantsToPlayAgain) {
                triesGlobal += triesLocal;

                x = random.nextInt(10) + 1;
                triesLocal = 0;
                shouldFinish = false;
                gamesPlayed++;

                userInput = getNumber("Please guess a number between 1 and 10\n> ", scanner);
            } else {
                userWantsToPlay = false;
            }
        }

        scanner.close();
    }

    private static int getNumber(String title, Scanner s) {
        int input = 0;
        boolean scannerError = true;

        System.out.print(title);
        while(scannerError) {
            if(s.hasNextInt()) {
                input = s.nextInt();
                scannerError = false;
            } else {
                scannerError = true;
                prompt("Not a number, try again\n> ", s);

                continue;
            }
        }

        return input;
    }

    private static boolean getYesOrNo(String title, Scanner s) {
        String answer = prompt(title + "\n> ", s);

        boolean answerIsYes = checkAnswer(answer, "yes");
        boolean answerIsNo = checkAnswer(answer, "no");

        while (!answerIsYes) {
            if(answerIsNo) {
                System.out.println("Bye!");
                return false;
            }

            answer = prompt("Please, choose between 'yes' and 'no':\n> ", s);
            answerIsYes = checkAnswer(answer, "yes");
            answerIsNo = checkAnswer(answer, "no");
        }

        return true;
    }

    private static String prompt(String title, Scanner s) {
        System.out.print(title);
        return s.next();
    }

    private static boolean checkAnswer(String answer, String compareWith) {
        String a = "";

        if(answer.equals("1") || answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
            a = "yes";
        } else if (answer.equals("2") || answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n")) {
            a = "no";
        }

        return a.equalsIgnoreCase(compareWith);
    }

}
