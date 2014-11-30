package it.polimi.traybowl;

import java.util.Scanner;

public class GameLoop {
    public Scanner scanner = new Scanner(System.in);

    private int iGameSize = 6;
    private int iInitialNumberOfSeeds = 3;
    private int iCurrentTurn;

    private Player p1 = new Player("Player 1");
    private Player p2 = new Player("Player 2");

    private Desk desk = new Desk(iGameSize, iInitialNumberOfSeeds, p1, p2);

}
