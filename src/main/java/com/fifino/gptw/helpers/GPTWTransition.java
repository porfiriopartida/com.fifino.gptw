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

    public int getFromScore() {
        return fromScore;
    }

    public void setFromScore(int fromScore) {
        this.fromScore = fromScore;
    }

    public int getToScore() {
        return toScore;
    }

    public void setToScore(int toScore) {
        this.toScore = toScore;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setIsWin(boolean isWin) {
        this.isWin = isWin;
    }
}
