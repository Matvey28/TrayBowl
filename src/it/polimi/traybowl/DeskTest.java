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

    protected void setUpZero() {
        mDeskTest = new Desk(6, 3, new Player("Petr"), new Player("Vitek"));

        int gameSize = mDeskTest.getGameSize();

        ArrayList<Integer> aiTest = new ArrayList<Integer>();
        for (int p = 0; p < 2; p++) {
            for (int i = 0; i < gameSize; i++){
                aiTest.add(0);
            }
            aiTest.add(0);
        }

        mDeskTest.makeCustomDesk(aiTest);
    }

    public void testMovementValidityOffRange() {
        setUp();

        assertFalse(mDeskTest.moveSeeds(42));
        assertFalse(mDeskTest.moveSeeds(-42));
    }

    public void testMovementValidityEmptyBowl() {
        setUp();

        mDeskTest.setSeedsAmount(1, 1, 0);

        assertFalse(mDeskTest.moveSeeds(1));
    }

    public void testIsValidTurn() {
        setUp();

        assertTrue(mDeskTest.isValidTurn(1));
    }

    public void testBowlEmptied() {
        setUp();

        mDeskTest.moveSeeds(2);

        assertTrue(mDeskTest.getSeedsAmount(1, 2) == 0);
    }

    public void testSeedsMove() {
        setUp();

        mDeskTest.moveSeeds(2);

        assertTrue(mDeskTest.getSeedsAmount(1, 3) == 4 && mDeskTest.getSeedsAmount(1, 4) == 4 && mDeskTest.getSeedsAmount(1, 5) == 4);
    }

    public void testLoopSeedMovement() {
        setUp();

        mDeskTest.setSeedsAmount(1, 1, 2*mDeskTest.getGameSize() + 3);

        assertTrue(mDeskTest.getSeedsAmount(1, 1) != 0);
    }

    public void testIsTrayFilled() {
        setUp();

        mDeskTest.moveSeeds(6);

        assertTrue(mDeskTest.getSeedsAmount(1) != 0);
    }

    public void testIsStolen() {
        setUp();

        mDeskTest.setSeedsAmount(1, 3, 1);
        mDeskTest.setSeedsAmount(1, 4, 0);

        mDeskTest.moveSeeds(3);

        assertTrue(mDeskTest.getSeedsAmount(mDeskTest.getSeedsAmount(2, 4)) == 0);
    }

    public void testIsNotStolenIfEnemyBowl() {
        setUp();

        mDeskTest.setSeedsAmount(2, 1, 0);
        mDeskTest.moveSeeds(5);

        assertTrue(mDeskTest.getSeedsAmount(2, 1) != 0 && mDeskTest.getSeedsAmount(1, 6) != 0);
    }

    public void testGameEnd() {
        setUpZero();

        mDeskTest.setSeedsAmount(1, 6, 1);
        mDeskTest.setSeedsAmount(2, 6, 1);

        mDeskTest.moveSeeds(6);
        assertFalse(mDeskTest.isLastTurn());


        mDeskTest.moveSeeds(6);
        assertTrue(mDeskTest.isLastTurn());
    }

}
