package main;

public enum GameStates {
    PLAYING,
    EDITING,
    MENU,
    SETTINGS,
    UPGRADE,
    GAME_OVER;

    public static GameStates gameStates = MENU;
    public static void SetGameState(GameStates state){
        gameStates = state;
    }
}
