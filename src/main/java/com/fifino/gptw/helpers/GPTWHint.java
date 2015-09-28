package com.fifino.gptw.helpers;

/**
 * Created by porfiriopartida on 9/27/2015.
 */
public class GPTWHint {
    public String hint;
    public int color;
    public GPTWHint(String hint, int color){
        this.hint = hint;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public String getHint() {
        return hint;
    }
}
