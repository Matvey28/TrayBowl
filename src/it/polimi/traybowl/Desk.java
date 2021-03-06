package it.polimi.traybowl;

import java.util.ArrayList;

/**
 * Created by Матвей on 27.11.14.
 */
public class Desk {

    private Player p1, p2;              // players participating the game
    private ArrayList<Bowl> aBowls;     // 'map' of the game, its current state
    private boolean bIfTurnOfFirst;     // true for first player, false for second
    private int iPositionOfTray1;       //
    private int iPositionOfTray2;       // positions of tray of players
    private int iGameSize;              // size of the game (number of bowl of each player
    private int iInitialNumberOfSeeds;  // initial number of seeds in bowls

    public boolean getCurrentPlayerTurn(){
        return this.bIfTurnOfFirst;
    }

    public ArrayList<Bowl> getGameState(){
        return aBowls;
    }

    public Desk(int gameSize, int initialNumberOfSeeds, Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;

        this.iGameSize = gameSize;
        this.iPositionOfTray1 = iGameSize;
        this.iPositionOfTray2 = 2 * iGameSize + 1;
        this.iInitialNumberOfSeeds = initialNumberOfSeeds; //

        aBowls = new ArrayList<Bowl>();

        // make initial state of the game
        for(int n = 0; n < 2; n++){ // 2 times
            for(int i = 0; i < iGameSize; i++){
                aBowls.add(new Bowl(iInitialNumberOfSeeds));
            }
            aBowls.add(new Bowl(0));
        }
        this.bIfTurnOfFirst = true; // first turn is of the first player
    }


    // check if there is any possible turn for the next player
    public boolean isLastTurn(){
        for (int i = 0; i < iGameSize; i++){
            if (aBowls.get(this.absolutePosition(i + 1)).getNumberOfSeeds() > 0){
                return false;
            }
        }
        return true;
    }

    // validation of the turn
    public boolean isValidTurn(int position){
        if (position < 1 || position > iGameSize) return false;
        return aBowls.get(this.absolutePosition(position)).getNumberOfSeeds() != 0;
    }

    // check if it's possible to steal opponent's seeds
    public boolean isAbleToSteal(int position){
        if (position == iPositionOfTray1 || position == iPositionOfTray2) return false;
        if ((position < iPositionOfTray1) != bIfTurnOfFirst) return false;
        return aBowls.get(position).getNumberOfSeeds() == 1;
    }

    // obtain winner by comparing number of seeds in players' trays
    public boolean getWinner(){
        return aBowls.get(iPositionOfTray1).getNumberOfSeeds() > aBowls.get(iPositionOfTray2).getNumberOfSeeds();
    }

    // obtain gamesize
    public int getGameSize() {
        return this.iGameSize;
    }

    public int getSeedsAmount( int bowl ) {
        return aBowls.get(bowl).getNumberOfSeeds();
    }

    public int getSeedsAmount( int player, int bowl ) {
        if ( player < 1 || player > 2)
            return 0;

        if ( bowl < 1 || bowl > iGameSize )
            return 0;

        int bowlId = (player - 1)*(iGameSize + 1) + bowl - 1;
        return getSeedsAmount(bowlId);
    }

    // sets specified seeds amount to the bowl
    public void setSeedsAmount( int bowl, int amount ) {
        if (bowl > aBowls.size())
            return;

        this.aBowls.get(bowl).setNumberOfSeeds(amount);
    }

    public void setSeedsAmount( int player, int bowl, int amount ) {
        if ( player < 1 || player > 2)
            return;

        if ( bowl < 1 || bowl > iGameSize )
            return;

        int bowlId = (player - 1)*(iGameSize + 1) + bowl - 1;

        this.setSeedsAmount(bowlId, amount);
    }

    // get amount of seeds from tray
    public int getSeedsInTray(int player) {
        if (player < 1 || player > 2)
            return 0;

        return getSeedsAmount(iGameSize * player + 1);
    }


    // make specific state out of integer array
    public void makeCustomDesk(ArrayList<Integer> customDesk){

        if ( customDesk.size() < 4 )
            return;

        // if new array has uneven size = shorten it on one element
        if ( customDesk.size()%2 != 0 )
            customDesk.remove( customDesk.size() - 1 );

        this.iGameSize = (customDesk.size() - 2) / 2;
        this.iPositionOfTray1 = iGameSize;
        this.iPositionOfTray2 = 2 * iGameSize + 1;

        for (int i = 0; i < iGameSize; i++){
            aBowls.set(i, new Bowl(customDesk.get(i)));
        }
    }

    // obtain absolute position in the array out of relative position of the player
    private int absolutePosition(int absolutePosition){
        return (bIfTurnOfFirst) ? absolutePosition - 1 : absolutePosition + iGameSize;
    }

    // move seeds according to the rules of the game
    public boolean moveSeeds(int position){
        if ( !isValidTurn(position) )
            return false;

        int iRelativePosition = this.absolutePosition(position);
        int iTempPosition = iRelativePosition;
        int iNumberOfTakenSeeds = aBowls.get(iRelativePosition).takeAllSeeds();
        for (int i = 0; i < iNumberOfTakenSeeds; i++){
            iTempPosition++;
            if (iTempPosition == aBowls.size()){
                iTempPosition = 0;
            }
            aBowls.get(iTempPosition).putSeeds(1);
        }
        if (this.isAbleToSteal(iTempPosition)){
            aBowls.get((bIfTurnOfFirst) ? iPositionOfTray1 : iPositionOfTray2)
                    .putSeeds(aBowls.get(iTempPosition).takeAllSeeds());
            int iPositionOfStolenBowl = (bIfTurnOfFirst) ?
                    iPositionOfTray1 + (iPositionOfTray1 - iTempPosition) :
                    iPositionOfTray2 - iTempPosition - 1;
            aBowls.get((bIfTurnOfFirst) ? iPositionOfTray1 : iPositionOfTray2)
                    .putSeeds(aBowls.get(iPositionOfStolenBowl).takeAllSeeds());
        }
        this.changeTurn();

        return true;
    }

    // change turn
    private void changeTurn(){
        bIfTurnOfFirst = !bIfTurnOfFirst;
    }

    // update statistics of the players
    public void updateStatistics(){

    }

    // representation of the current state of the game
    @Override
    public String toString(){
        String sDesk = "Current desk:\n";
        String s1 = "";
        String s2 = "";
        String s3 = "";

        for (int i = 0; i < iGameSize; i++){
            s1 += aBowls.get(aBowls.size() - 2 - i).getNumberOfSeeds() + " ";
        }
        s2 += aBowls.get(iPositionOfTray2).getNumberOfSeeds();
        for (int i = 0; i < s1.length() - 3; i++){
            s2 += " ";
        }
        s2 += aBowls.get(iPositionOfTray1).getNumberOfSeeds();
        for (int i = 0; i < iGameSize; i++){
            s3 += aBowls.get(i).getNumberOfSeeds() + " ";
        }
        sDesk += s1 + "\n" + s2 + "\n" + s3;
        return sDesk;
    }

}
