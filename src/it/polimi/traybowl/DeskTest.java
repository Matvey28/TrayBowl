package it.polimi.traybowl;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Матвей on 29.11.14.
 */
public class DeskTest extends TestCase {
    public void testIsValidTurn() throws Exception {
        Desk deskTest = new Desk(6, 3, new Player("Petr"), new Player("Vitek"));
        ArrayList<Integer> aiTest = new ArrayList<Integer>();
        for (int i = 0; i < aiTest.size(); i++){
            aiTest.add(3);
        }
        aiTest.set(1, 0);
        deskTest.makeCustomDesk(aiTest);
        assertTrue(!deskTest.isValidTurn(1));
    }
}
