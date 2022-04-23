package edu.cuhk.csci3310.quizdle.util;

import android.content.Context;

import edu.cuhk.csci3310.quizdle.model.User;

public class UserUtil {

    private static final String TAG = "UserUtil";

    public static User createNewUser(Context context) {
        User user = new User();

        user.setName("NewUser");
        user.setLevel(1);
        user.setExperience("0/100");
        user.setWin("0/0");
        user.setCoin(0);

        return user;
    }

}
