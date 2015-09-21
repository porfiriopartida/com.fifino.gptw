package com.fifino.gptw.helpers;

/**
 * Created by porfiriopartida on 9/20/2015.
 */
public class GPTWTransition {
    private int fromScore;
    private int toScore;
    private boolean isWin;
    private int extra;

    public GPTWTransition(int fromScore, int toScore, boolean isWin, int extra){
        this.fromScore = fromScore;
        this.toScore = toScore;
        this.isWin = isWin;
        this.extra = extra;
    }
}
