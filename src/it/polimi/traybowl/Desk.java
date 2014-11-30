package it.polimi.traybowl;

import java.util.ArrayList;

/**
 * Created by Матвей on 27.11.14.
 */
public class Desk {

    private Player p1, p2;
    private ArrayList<Bowl> aBowls;
    private boolean bIfTurnOfFirst;
    private int iPositionOfTray1;
    private int iPositionOfTray2;
    private int iGameSize;
    private int iInitialNumberOfSeeds;

    public boolean getCurrentPlayerTurn(){
        return this.bIfTurnOfFirst;
    }
    public ArrayList<Bowl> getGameState(){
        return aBowls;
    }

    public Desk(int GameSize, int InitialNumberOfSeeds, Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;

        this.iGameSize = GameSize;
        this.iPositionOfTray1 = iGameSize;
        this.iPositionOfTray2 = 2 * iGameSize + 1;
        this.iInitialNumberOfSeeds = InitialNumberOfSeeds; //

        aBowls = new ArrayList<Bowl>();
        for(int n = 0; n < 2; n++){
            for(int i = 0; i < iGameSize; i++){
                aBowls.add(new Bowl(iInitialNumberOfSeeds));
            }
            aBowls.add(new Bowl(0));
        }
        this.bIfTurnOfFirst = true;
    }

    public void makeCustomDesk(ArrayList<Integer> aiCustomDesk){
        for (int i = 0; i < aBowls.size(); i++){
            aBowls.set(i, new Bowl(aiCustomDesk.get(i)));
        }
    }

    private int relativePosition(int iAbsolutePosition){
        return (bIfTurnOfFirst) ? iAbsolutePosition - 1 : iAbsolutePosition + iGameSize;
    }

    public boolean isValidTurn(int iPosition){
        if (iPosition < 1 || iPosition > iGameSize) return false;
        return aBowls.get(this.relativePosition(iPosition)).getNumberOfSeeds() != 0;
    }

    public void moveSeeds(int iPosition){
        int iRelativePosition = this.relativePosition(iPosition);
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
    }

    public boolean isAbleToSteal(int iPosition){
        if (iPosition == iPositionOfTray1 || iPosition == iPositionOfTray2) return false;
        if ((iPosition < iPositionOfTray1) != bIfTurnOfFirst) return false;
        return aBowls.get(iPosition).getNumberOfSeeds() == 1;
    }

    private void changeTurn(){
        bIfTurnOfFirst = !bIfTurnOfFirst;
    }

    public boolean isLastTurn(){
        for (int i = 0; i < iGameSize; i++){
            if (aBowls.get(this.relativePosition(i + 1)).getNumberOfSeeds() > 0){
                return false;
            }
        }
        return true;
    }

    public boolean winner(){
        return aBowls.get(iPositionOfTray1).getNumberOfSeeds() > aBowls.get(iPositionOfTray2).getNumberOfSeeds();
    }

    public void updateStatistics(){

    }

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