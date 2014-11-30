package it.polimi.traybowl;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Матвей on 29.11.14.
 */
public class DeskTest extends TestCase {
    private Desk mDeskTest;

    protected void setUp() {
        mDeskTest = new Desk(6, 3, new Player("Petr"), new Player("Vitek"));

        int gameSize = mDeskTest.getGameSize();

        ArrayList<Integer> aiTest = new ArrayList<Integer>();
        for (int p = 0; p < 2; p++) {
            for (int i = 0; i < gameSize; i++){
                aiTest.add(3);
            }
            aiTest.add(0);
        }

        mDeskTest.makeCustomDesk(aiTest);

    }

    public void testIsValidTurn() {
        setUp();

        assertTrue(mDeskTest.isValidTurn(1));
    }

    public void testIsStolen() {
        setUp();

        //mDeskTest.setSeedsAmount();
    }
}
