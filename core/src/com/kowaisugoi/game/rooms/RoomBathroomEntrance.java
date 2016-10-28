package com.kowaisugoi.game.rooms;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.MusicId;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.control.flags.FlagId;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.player.Player;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

import java.util.LinkedList;
import java.util.List;

import static com.kowaisugoi.game.control.flags.FlagId.FLAG_BOARDS_REMOVED;

public class RoomBathroomEntrance extends StandardRoom {

    private static final String ROOM_URL = "rooms/bathroom/bathroom_entrance_boards.png";
    private static final String ROOM_URL2 = "rooms/bathroom/bathroom_entrance.png";
    private static final String ROOM_URL3 = "rooms/bathroom/bathroom_entrance_2.png";
    private static final String ROOM_URL4 = "rooms/bathroom/bathroom_entrance_empty.png";

    private final Sprite _roomSprite1 = new Sprite(new Texture(ROOM_URL));
    private final Sprite _roomSprite2 = new Sprite(new Texture(ROOM_URL2));
    private final Sprite _roomSprite3 = new Sprite(new Texture(ROOM_URL3));
    private final Sprite _roomSprite4 = new Sprite(new Texture(ROOM_URL4));

    private Describable _uncle;

    private List<Describable> _descriptionList1;
    private List<Describable> _descriptionList2;
    private List<Passage> _passageList2;

    public RoomBathroomEntrance() {
        super(new Sprite(new Texture(ROOM_URL)));

        _passageList2 = new LinkedList<Passage>();

        Passage passageBack = new DirectionalPassage(RoomId.BATHROOM,
                RoomId.BEDROOM,
                new Rectangle(50, 0, 50, 15),
                GameUtil.Direction.DOWN);

        Passage passageForward = new DirectionalPassage(RoomId.BATHROOM,
                RoomId.BATHROOM_CABINET,
                new Rectangle(100, 16, 22, 61),
                GameUtil.Direction.UP);

        passageBack.setSoundEffect(SoundId.CLICK);

        addPassage(passageBack);

        _descriptionList1 = new LinkedList<Describable>();
        _descriptionList2 = new LinkedList<Describable>();

        _uncle = new GeneralDescribable(Messages.getText("bathroom.uncle.thought.1"),
                new Rectangle(68, 25, 27, 58));
        _uncle.addDescription(Messages.getText("bathroom.uncle.thought.2"));

        _descriptionList1.add(_uncle);
        setDescriptionList(_descriptionList1);

        _passageList2.add(passageForward);
        _passageList2.add(passageBack);
    }

    @Override
    public boolean click(float curX, float curY) {
        return super.click(curX, curY);
    }

    @Override
    public void flagUpdate() {
        if (PlayGame.getFlagManager().getFlag(FLAG_BOARDS_REMOVED).getState()) {
            setSprite(_roomSprite2);
            setPassageList(_passageList2);
        }

        if (PlayGame.getFlagManager().getFlag(FlagId.FLAG_KEYS_MISSING).getState()) {
            setSprite(_roomSprite4);
            setDescriptionList(_descriptionList2);
        }
    }

    @Override
    public void enter() {
        super.enter();
        AudioManager.playMusic(MusicId.DRONE, false);

        if (PlayGame.getFlagManager().getFlag(FlagId.FLAG_KEYS_MISSING).getState()) {
            // Disable mouse for a *little* bit longer the first time
            if (!PlayGame.getFlagManager().getFlag(FlagId.FLAG_KEYS_APPEARED).getState()) {
                PlayGame.getFlagManager().setFlag(FlagId.FLAG_KEYS_APPEARED, true);
                Timer.schedule(new Timer.Task() {
                                   @Override
                                   public void run() {
                                       PlayGame.getPlayer().setInteractionMode(Player.InteractionMode.NORMAL);
                                   }
                               }
                        , 2.0f // Initial delay
                        , 0 // Fire every X seconds
                        , 1 // Number of times to fire
                );
            } else {
                Timer.schedule(new Timer.Task() {
                                   @Override
                                   public void run() {
                                       PlayGame.getPlayer().setInteractionMode(Player.InteractionMode.NORMAL);
                                   }
                               }
                        , 0.8f // Initial delay TODO: Should make global, or shouldn't set this here
                        , 0 // Fire every X seconds
                        , 1 // Number of times to fire
                );
            }
            PlayGame.getFlagManager().setFlag(FlagId.FLAG_KEYS_APPEARED, true);
            return;
        }

        PlayGame.getPlayer().setInteractionMode(Player.InteractionMode.NONE);

        if (!PlayGame.getFlagManager().getFlag(FlagId.FLAG_BODY_FOUND).getState()) {
            PlayGame.getPlayer().think(Messages.getText("bathroom.uncle.clickhimdammit"));

            // Big reveal timer
            Timer.schedule(new Timer.Task() {
                               @Override
                               public void run() {
                                   PlayGame.getPlayer().setInteractionMode(Player.InteractionMode.NONE);
                                   AudioManager.playSound(SoundId.CLICK);
                                   setSprite(_roomSprite3);
                               }
                           }
                    , 3.5f // Initial delay
                    , 0 // Fire every X seconds
                    , 1 // Number of times to fire
            );

            // Un-reveal
            Timer.schedule(new Timer.Task() {
                               @Override
                               public void run() {
                                   PlayGame.getPlayer().setInteractionMode(Player.InteractionMode.NONE);
                                   AudioManager.playSound(SoundId.CLICK);
                                   setSprite(_roomSprite1);
                               }
                           }
                    , 5.5f // Initial delay
                    , 0 // Fire every X seconds
                    , 1 // Number of times to fire
            );

            // Re-allow interaction
            Timer.schedule(new Timer.Task() {
                               @Override
                               public void run() {
                                   PlayGame.getPlayer().setInteractionMode(Player.InteractionMode.NORMAL);
                               }
                           }
                    , 6.5f // Initial delay
                    , 0 // Fire every X seconds
                    , 1 // Number of times to fire
            );

            PlayGame.getFlagManager().setFlag(FlagId.FLAG_BODY_FOUND, true);

            PlayGame.getRoomManager().getRoomFromId(RoomId.PARKING_AREA).pushEnterRemark("carpark.enter.snowcovered");
        } else {
            // Re-allow interaction in normal cases
            Timer.schedule(new Timer.Task() {
                               @Override
                               public void run() {
                                   PlayGame.getPlayer().setInteractionMode(Player.InteractionMode.NORMAL);
                               }
                           }
                    , 0.4f // Initial delay TODO: Should make global, or shouldn't set this here
                    , 0 // Fire every X seconds
                    , 1 // Number of times to fire
            );

            PlayGame.getFlagManager().setFlag(FlagId.FLAG_BODY_FOUND, true);
        }
    }
}
