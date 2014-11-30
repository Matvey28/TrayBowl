package it.polimi.traybowl;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Матвей on 27.11.14.
 */
public class TrayBowl {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        int iGameSize = 6;
        int iInitialNumberOfSeeds = 3;
        int iCurrentTurn;

        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        Desk desk = new Desk(iGameSize, iInitialNumberOfSeeds, p1, p2);

        boolean exit = false;
        boolean isCustomized = false;

        System.out.println("Welcome to Tray&Bowl game\n");

        do{
            System.out.println("Insert key:\n" +
                    "1 - start game\n" +
                    "2 - change settings\n" +
                    "3 - make custom desk\n" +
                    "9 - game rules\n" +
                    "0 - exit");

            switch (scanner.nextInt()){
                case 1: // start game

                    if (!isCustomized) {
                        desk = new Desk(iGameSize, iInitialNumberOfSeeds, p1, p2);
                    } else {
                        isCustomized = false;
                    }
                    System.out.println(desk.toString());

                    do {
                        System.out.println("Turn of " + ((desk.getCurrentPlayerTurn()) ? p1.getName() : p2.getName()));
                        iCurrentTurn = scanner.nextInt();
                        if (!desk.isValidTurn(iCurrentTurn)){
                            System.out.println("invalid turn");
                            continue;
                        }
                        
                        desk.moveSeeds(iCurrentTurn);
                        System.out.println(desk.toString());
                    } while (!desk.isLastTurn());

                    System.out.println("And the winner is ... " + ((desk.getWinner()) ? p1.getName() : p2.getName()));
                    desk.updateStatistics();
                    break;
                case 2:
                    System.out.println("game size (now " + iGameSize + "):");

                    iGameSize = scanner.nextInt();
                    System.out.println("initial number of seeds in bowls (now " + iInitialNumberOfSeeds + "):");

                    iInitialNumberOfSeeds = scanner.nextInt();
                    System.out.println("change name " + p1.getName());

                    String sPlayerName = scanner.nextLine();
                    p1.setName(sPlayerName);
                    System.out.println("change name " + p2.getName());

                    sPlayerName = scanner.nextLine();
                    p2.setName(sPlayerName);
                    desk = new Desk(iGameSize, iInitialNumberOfSeeds, p1, p2);
                    break;
                case 3:
                    System.out.println("please insert game state (" + iGameSize + " bowls, than tray, repeat twice)\n" +
                            "start from the first bowl of the 1st player\ngo counter clockwise");

                    ArrayList<Integer> aiCustomDesk = new ArrayList<Integer>();
                    for (int i = 0; i < iGameSize * 2 + 2; i++){
                        aiCustomDesk.add(scanner.nextInt());
                    }

                    desk.makeCustomDesk(aiCustomDesk);
                    isCustomized = true;
                    break;
                case 0: // exit
                    exit = true;
                    break;
            }
        } while (!exit);
    }
}
