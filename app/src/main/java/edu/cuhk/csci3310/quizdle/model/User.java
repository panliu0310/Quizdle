package edu.cuhk.csci3310.quizdle.model;

public class User {
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_LEVEL = "level";
    public static final String FIELD_EXP = "experience";
    public static final String FIELD_WIN = "win";
    public static final String FIELD_COIN = "coin";

    private String username;
    private int level;
    private String experience;
    private String win;
    private int coin;

    public User() {}

    public User(String username, int level, String experience, String win,
                      int coin) {
        this.username = username;
        this.level = level;
        this.experience = experience;
        this.win = win;
        this.coin = coin;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWin(){
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
