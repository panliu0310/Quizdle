package edu.cuhk.csci3310.quizdle.model;

public class User {
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_LEVEL = "level";
    public static final String FIELD_EXP = "experience";
    public static final String FIELD_WIN = "win";
    public static final String FIELD_COIN = "coin";

    private String email;
    private String username;
    private int level;
    private int experience;
    private int expBound;
    private int upgradeRequired;
    private int victory;
    private int totalMatch;
    private int coin;

    public User() {}

    public User(String email, String username, int level, int experience, int expBound,
                int upgradeRequired, int victory, int totalMatch, int coin) {
        this.email = email;
        this.username = username;
        this.level = level;
        this.experience = experience;
        this.expBound = 100;
        this.upgradeRequired = upgradeRequired;
        this.victory = victory;
        this.totalMatch = totalMatch;
        this.coin = coin;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public int getExperience() { return experience; }

    public void setExperience(int experience) { this.experience = experience; }

    public int getExpBound() {return expBound; }

    public void setExpBound(int expBound) { this.expBound = expBound; }

    public int getUpgradeRequired() { return upgradeRequired; }

    public void setUpgradeRequired(int upgradeRequired) { this.upgradeRequired = upgradeRequired; }

    public int getVictory() { return victory; }

    public void setVictory(int victory){ this.victory = victory; }

    public int getTotalMatch() { return totalMatch; }

    public void setTotalMatch(int totalMatch) { this.totalMatch = totalMatch; }

    public int getCoin() { return coin; }

    public void setCoin(int coin) { this.coin = coin; }
}
