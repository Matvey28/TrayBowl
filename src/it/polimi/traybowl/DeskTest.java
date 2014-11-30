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
    }


    public void testIsValidTurn() {
        setUp();
        ArrayList<Integer> aiTest = new ArrayList<Integer>();
        for (int i = 0; i < aiTest.size(); i++){
            aiTest.add(3);
        }
        aiTest.set(1, 0);
        mDeskTest.makeCustomDesk(aiTest);
        assertTrue(!mDeskTest.isValidTurn(1));
    }
}
