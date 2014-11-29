package it.polimi.traybowl;

/**
 * Created by Матвей on 27.11.14.
 */
public class Player {

    private String sName;
    private int iGames, iWins, iScore;

    public String getName(){
        return sName;
    }

    public void setName(String sName){
        this.sName = sName;
    }

    public Player(String sName){
        this.sName = sName;
        this.iGames = this.iWins = this.iScore = 0;
    }

}
