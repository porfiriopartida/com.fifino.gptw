package com.fifino.gptw.helpers;

import com.fifino.framework.assets.Assets;

/**
 * Created by porfiriopartida on 9/12/2015.
 */
public final class GPTWResources {

    /* Score resources */
    //Keys
    public static final String GPTW_SCORE_BG = "GPTW_SCORE_BG";
    public static final String GPTW_SCORE_LIVE = "GPTW_SCORE_LIVE";
    //Images
    public static final String GPTW_SCORE_BG_IMG = Assets.IMAGES_PATH + "/screens/scores/bg.png";
    public static final String GPTW_SCORE_LIVE_IMG = Assets.IMAGES_PATH + "/screens/scores/live.png";

    /* End score resources */

    /* Find the Car */
    //Keys
    public static final String FIND_THE_CAR_BG = "FIND_THE_CAR_BG";
    public static final String FIND_THE_CAR_SIGN = "FIND_THE_CAR_SIGN";
    public static final String FIND_THE_CAR_CAR = "FIND_THE_CAR_CAR";
    public static final String FIND_THE_CAR_WIN = "FIND_THE_CAR_WIN";
    public static final String FIND_THE_CAR_LOSE_1 = "FIND_THE_CAR_LOSE_1";
    public static final String FIND_THE_CAR_LOSE_2 = "FIND_THE_CAR_LOSE_2";
    //End Keys

    //Images
    //Background
    public static final String FIND_THE_CAR_IMG_BG = Assets.IMAGES_PATH + "/screens/games/cars/bg.png";

    //Signs
    public static final String[] FIND_THE_CAR_IMG_SIGNS = new String[]{
            Assets.IMAGES_PATH + "/screens/games/cars/sign-1.png",
            Assets.IMAGES_PATH + "/screens/games/cars/sign-2.png",
            Assets.IMAGES_PATH + "/screens/games/cars/sign-3.png",
            Assets.IMAGES_PATH + "/screens/games/cars/sign-4.png",
            Assets.IMAGES_PATH + "/screens/games/cars/sign-5.png",
            Assets.IMAGES_PATH + "/screens/games/cars/sign-6.png",
            Assets.IMAGES_PATH + "/screens/games/cars/sign-7.png"
    };
    //Cars (red, blue, green)
    public static final String[] FIND_THE_CAR_IMG_CARS = new String[]{
            Assets.IMAGES_PATH + "/screens/games/cars/car-1.png",
            Assets.IMAGES_PATH + "/screens/games/cars/car-2.png",
            Assets.IMAGES_PATH + "/screens/games/cars/car-3.png"
    };
    //Win-Lose images.
    public static final String FIND_THE_CAR_WIN_IMG = Assets.IMAGES_PATH + "/screens/games/cars-0.png";
    public static final String FIND_THE_CAR_LOSE_1_IMG = Assets.IMAGES_PATH + "/screens/games/cars-1.png";
    public static final String FIND_THE_CAR_LOSE_2_IMG = Assets.IMAGES_PATH + "/screens/games/cars-2.png";
    // End Images
    /* End Find the Car*/
}
