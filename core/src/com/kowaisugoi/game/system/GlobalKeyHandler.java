package com.kowaisugoi.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.control.flags.FlagId;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.rooms.RoomId;
import com.kowaisugoi.game.screens.PlayGame;

/**
 * Prevents code duplication across MainMenu/PlayGame,
 * allows for easy modification of any global hotkeys
 */
public final class GlobalKeyHandler {
    static final int FULLSCREEN_KEY = Input.Keys.F4;
    static final int DEBUG_KEY = Input.Keys.F8;
    static final int PLACEMENT_HELPER = Input.Keys.F9;
    static final int EXIT_KEY = Input.Keys.ESCAPE;
    static final int YES_KEY = Input.Keys.Y;
    static final int NO_KEY = Input.Keys.N;
    static final int SHED_INTERIOR_KEY = Input.Keys.S;
    static final int TORCH_KEY = Input.Keys.T;
    static final int CABINET_KEY = Input.Keys.C;
    static final int ENDGAME_KEY = Input.Keys.E;
    static final int CREDITS_KEY = Input.Keys.X;

    public static boolean keyUp(int keycode) {
        if (keycode == FULLSCREEN_KEY) {
            if (!Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setDisplayMode(
                        Gdx.graphics.getDesktopDisplayMode().width,
                        Gdx.graphics.getDesktopDisplayMode().height,
                        true);
            } else {
                Gdx.graphics.setDisplayMode(
                        800,
                        450,
                        false);
            }
            return true;
        }

        if (keycode == EXIT_KEY) {
            if (PlayGame.getPaused()) {
                PlayGame.setPaused(false);
            } else {
                PlayGame.setPaused(true);
            }
        }

        if (keycode == YES_KEY) {
            if (PlayGame.getPaused()) {
                Gdx.app.exit();
            }
        }

        if (keycode == NO_KEY) {
            if (PlayGame.getPaused()) {
                PlayGame.setPaused(false);
            }
        }

        if (keycode == DEBUG_KEY) {
            if (PlayGame.getDebug()) {
                PlayGame.setDebug(false);
            } else {
                PlayGame.setDebug(true);
            }
        }

        if (keycode == ENDGAME_KEY) {
            if (PlayGame.getDebug()) {
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_BODY_FOUND, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_KEYS_MISSING, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_BOARDS_REMOVED, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_CAR_FOUND, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_CAR_SNOWREMOVED, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_NIGHT_TIME, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_SHED_OPENED, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_ENTERED_BATHROOM, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_TORCH_PLACED, true);
            }
        }

        if (keycode == CREDITS_KEY) {
            if (PlayGame.getDebug()) {
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_BODY_FOUND, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_KEYS_MISSING, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_BOARDS_REMOVED, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_CAR_FOUND, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_CAR_SNOWREMOVED, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_NIGHT_TIME, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_SHED_OPENED, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_ENTERED_BATHROOM, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_TORCH_PLACED, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_KEYS_APPEARED, true);
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_KEYS_FOUND, true);
            }
        }

        if (keycode == PLACEMENT_HELPER) {
            if (PlayGame.getDebug()) {
                if (!PlayGame.getPlacementMode()) {
                    PlayGame.setPlacementMode(true);
                } else {
                    PlayGame.setPlacementMode(false);
                }
            }
        }

        if (keycode == SHED_INTERIOR_KEY) {
            if (PlayGame.getDebug()) {
                PickupableItem glassWater = new PickupableItem(new Sprite(new Texture("items/glass_water.png")),
                        new Rectangle(0, 0, 0, 0),
                        ItemId.GLASS_WATER);
                PlayGame.getPlayer().getInventory().addItem(glassWater);
                PlayGame.getPlayer().enterRoom(RoomId.SHED_INTERIOR);
            }
        }

        if (keycode == CABINET_KEY) {
            if (PlayGame.getDebug()) {
                PlayGame.getPlayer().enterRoom(RoomId.BATHROOM_CABINET);
            }
        }

        if (keycode == TORCH_KEY) {
            PickupableItem torch = new PickupableItem(new Sprite(new Texture("items/stickicon_fire.png")),
                    new Rectangle(0, 0, 0, 0),
                    ItemId.TORCH);
            if (PlayGame.getDebug()) {
                PlayGame.getPlayer().getInventory().addItem(torch);
            }

        }

        return false;
    }
}
