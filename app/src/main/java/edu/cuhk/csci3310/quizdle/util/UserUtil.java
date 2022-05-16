package edu.cuhk.csci3310.quizdle.util;

import edu.cuhk.csci3310.quizdle.model.User;

public class UserUtil {

    private static final String TAG = "UserUtil";

    public static User createNewUser(String email) {
        User user = new User();

        user.setEmail(email);
        user.setUsername(email);
        user.setLevel(1);
        user.setExperience(0);
        user.setExpBound(100);
        user.setUpgradeRequired(100);
        user.setVictory(0);
        user.setTotalMatch(0);
        user.setCoin(0);

        return user;
    }

}
