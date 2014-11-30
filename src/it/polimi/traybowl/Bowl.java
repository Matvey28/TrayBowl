package it.polimi.traybowl;

/**
 * Created by Матвей on 27.11.14.
 */
public class Bowl {

    private int iNumberOfSeeds; // current number of seeds in the bowl

    public Bowl(int iNumberOfSeeds){
        this.iNumberOfSeeds = iNumberOfSeeds;
    }

    public int getNumberOfSeeds() {
        return iNumberOfSeeds;
    }

    public void setNumberOfSeeds(int amount) {
        this.iNumberOfSeeds = amount;
    }

    // take all seeds from the bowl and return its number
    public int takeAllSeeds(){
        int i = iNumberOfSeeds;
        iNumberOfSeeds = 0;
        return i;
    }

    // add specific number of seeds to the bowl
    public void putSeeds(int iNumberOfSeeds){
        this.iNumberOfSeeds += iNumberOfSeeds;
    }

}
