package com.kowaisugoi.game.system;

public class GameUtil {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static boolean isNotNullOrEmpty(String s) {
        return !(s == null || "".equals(s));
    }
}
