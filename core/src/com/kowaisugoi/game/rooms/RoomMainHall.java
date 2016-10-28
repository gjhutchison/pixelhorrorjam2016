package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.MusicId;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.graphics.FireAnimation;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.interactables.scenic.ItemInteractableScenic;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

import java.util.LinkedList;
import java.util.List;

import static com.kowaisugoi.game.control.flags.FlagId.FLAG_KEYS_FOUND;
import static com.kowaisugoi.game.control.flags.FlagId.FLAG_KEYS_APPEARED;

public class RoomMainHall extends StandardRoom {

    private static final String ROOM_URL = "rooms/mainhall/mainhall.png";
    private FireAnimation _fireAnimation;
    int _currentSprite = 0;
    float _deltaBuffer = 0;

    private PickupableItem _carkeys;

    private List<Describable> _describableList2;

    /*
    private static final String[] OVERLAY_URLS = {"rooms/mainhall/cozy_overlay_1.png",
            "rooms/mainhall/cozy_overlay_2.png",
            "rooms/mainhall/cozy_overlay_3.png"};
    */
    //ArrayList<Sprite> _overlays = new ArrayList<Sprite>();
    //TODO: Fix overlays
    public RoomMainHall() {
        super(new Sprite(new Texture(ROOM_URL)));
        /*
        for (String overlayUrl : OVERLAY_URLS) {
            Sprite overlay = new Sprite(new Texture(overlayUrl));
            overlay.scale(0.8f);
            _overlays.add(overlay);
        }
        */
        _describableList2 = new LinkedList<Describable>();

        Passage hallDoor = new DirectionalPassage(RoomId.MAIN_HALL,
                RoomId.HALLWAY,
                new Rectangle(5, 25, 30, 40),
                GameUtil.Direction.LEFT);
        Passage turnAround = new DirectionalPassage(RoomId.MAIN_HALL,
                RoomId.FRONTYARD,
                new Rectangle(55, 0, 50, 10),
                GameUtil.Direction.DOWN);
        Passage crawlDoor = new DirectionalPassage(RoomId.MAIN_HALL,
                RoomId.CRAWLSPACE,
                new Rectangle(120, 10, 27, 27),
                GameUtil.Direction.UP);

        Describable paintingDescription = new GeneralDescribable(Messages.getText("mainhall.painting.thought"),
                new Rectangle(55, 50, 40, 30));

        Describable extinguishedFireDescription = new GeneralDescribable(Messages.getText("mainhall.extinguishedfire.thought"),
                new Rectangle(72, 35, 18, 7));

        PickupableItem torch = new PickupableItem(new Sprite(new Texture("items/stickicon_fire.png")),
                new Rectangle(0, 0, 0, 0),
                ItemId.TORCH);

        _carkeys = new PickupableItem(new Sprite(new Texture("rooms/mainhall/key.png")),
                new Sprite(new Texture("items/carkeyicon.png")),
                new Rectangle(75, 28, 8, 5),
                ItemId.CAR_KEYS) {
            @Override
            public boolean click(float curX, float curY) {
                if (super.click(curX, curY)) {
                    PlayGame.getFlagManager().setFlag(FLAG_KEYS_FOUND, true);
                    return true;
                }
                return false;
            }
        };
        _carkeys.setPickupText(Messages.getText("mainhall.pickup.carkeys"));

        ItemInteractableScenic fireDescription = new ItemInteractableScenic(Messages.getText("mainhall.fireplace.thought"),
                Messages.getText("shedinterior.interaction.stickdoused.fireplace"),
                new Rectangle(70, 28, 21, 13),
                ItemId.STICK_RAGS_ALCOHOL, torch);

        turnAround.setSoundEffect(SoundId.DOOR_CREAK);
        hallDoor.setSoundEffect(SoundId.DOOR_CREAK);
        crawlDoor.setSoundEffect(SoundId.CLICK);
        addDescribable(paintingDescription);
        addDescribable(fireDescription);
        addPassage(hallDoor);
        addPassage(turnAround);
        addPassage(crawlDoor);
        _fireAnimation = new FireAnimation(80, 31, 150);
        pushEnterRemark("mainhall.enter.whereuncle");

        _describableList2.add(paintingDescription);
        _describableList2.add(extinguishedFireDescription);
        //_describableList2.add()
    }

    public void drawFx(SpriteBatch batch) {
        //super.draw(batch);
        //_overlays.get(currentSprite).draw(batch, 0.08f);
    }

    @Override
    public void enter() {
        super.enter();
        AudioManager.playMusic(MusicId.COZY, false);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        if (!PlayGame.getFlagManager().getFlag(FLAG_KEYS_APPEARED).getState()) {
            _fireAnimation.draw(renderer);
        }
        super.draw(renderer);
    }

    @Override
    public void flagUpdate() {
        if (PlayGame.getFlagManager().getFlag(FLAG_KEYS_APPEARED).getState()) {
            setDescriptionList(_describableList2);
            if (!_carkeys.isPickedUp()) {
                _pickupableItemList.clear();
                _pickupableItemList.add(_carkeys);
            }
        }
    }

    public void update(float delta) {
        super.update(delta);

        if (!PlayGame.getFlagManager().getFlag(FLAG_KEYS_APPEARED).getState()) {
            _fireAnimation.update(delta);
        }

        /*
        deltaBuffer += delta;
        if (deltaBuffer >= 0.25) { // TODO: Randomly change this max value
            deltaBuffer = 0;
            currentSprite++;
            if (currentSprite >= OVERLAY_URLS.length) {
                currentSprite = 0;
            }
        }*/
    }

    @Override
    public void dispose() {

    }

    // TODO: dispose overlays
}
