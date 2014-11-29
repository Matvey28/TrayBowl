package it.polimi.traybowl;

/**
 * Created by Матвей on 27.11.14.
 */
public class Bowl {

    private int iNumberOfSeeds;

    public int getNumberOfSeeds() {
        return iNumberOfSeeds;
    }

    public Bowl(int iNumberOfSeeds){
        this.iNumberOfSeeds = iNumberOfSeeds;
    }

    public int takeAllSeeds(){
        int i = iNumberOfSeeds;
        iNumberOfSeeds = 0;
        return i;
    }

    public  void putSeeds(int iNumberOfSeeds){
        this.iNumberOfSeeds += iNumberOfSeeds;
    }

}
