package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.MusicId;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.graphics.SnowAnimation;
import com.kowaisugoi.game.interactables.objects.ItemId;
import com.kowaisugoi.game.interactables.objects.PickupableItem;
import com.kowaisugoi.game.interactables.passages.BlockedPassage;
import com.kowaisugoi.game.interactables.passages.DirectionalPassage;
import com.kowaisugoi.game.interactables.passages.Passage;
import com.kowaisugoi.game.interactables.scenic.Describable;
import com.kowaisugoi.game.interactables.scenic.GeneralDescribable;
import com.kowaisugoi.game.messages.Messages;
import com.kowaisugoi.game.screens.PlayGame;
import com.kowaisugoi.game.system.GameUtil;

import java.util.LinkedList;
import java.util.List;

import static com.kowaisugoi.game.control.flags.FlagId.*;

public class RoomCarPark extends StandardRoom {

    private SnowAnimation _snowAnimation;
    private static final String ROOM_URL = "rooms/parking/carpark.png";
    private static final String ROOM_URL2 = "rooms/parking/snowed_car.png";
    private static final String ROOM_URL3 = "rooms/parking/cardamaged.png";

    private final Sprite _roomSprite1 = new Sprite(new Texture(ROOM_URL));
    private final Sprite _roomSprite2 = new Sprite(new Texture(ROOM_URL2));
    private final Sprite _roomSprite3 = new Sprite(new Texture(ROOM_URL3));

    private List<Passage> _passageList1;
    private List<Passage> _passageList2;
    private List<Passage> _passageList3;

    private List<Describable> _descriptionList1;

    private PickupableItem _stick;

    public RoomCarPark() {
        super(new Sprite(new Texture(ROOM_URL)));

        _snowAnimation = new SnowAnimation(50, 6);

        Passage enterCar = new DirectionalPassage(RoomId.PARKING_AREA,
                RoomId.CAR,
                new Rectangle(31, 11, 45, 34),
                GameUtil.Direction.UP);
        Passage toPath = new DirectionalPassage(RoomId.PARKING_AREA,
                RoomId.ROAD,
                new Rectangle(97, 15, 36, 62),
                GameUtil.Direction.UP);
        toPath.setSoundEffect(SoundId.SNOW_CRUNCH);
        addPassage(enterCar);
        addPassage(toPath);

        BlockedPassage snowCar = new BlockedPassage(RoomId.PARKING_AREA,
                RoomId.CAR,
                new Rectangle(31, 11, 45, 34),
                GameUtil.Direction.UP,
                ItemId.SHOVEL,
                Messages.getText("carpark.snow.interact.locked"),
                Messages.getText("carpark.snow.interact.unlocked"),
                null);
        snowCar.setUnlockedToggleFlag(FLAG_CAR_SNOWREMOVED);
        snowCar.setItemInteractionMessage(ItemId.GLASS, Messages.getText("carpark.interaction.glass.snow"));
        snowCar.setItemInteractionMessage(ItemId.GLASS_SNOW, Messages.getText("carpark.interaction.glasssnow.snow"));
        snowCar.setItemInteractionMessage(ItemId.GLASS_WATER, Messages.getText("carpark.interaction.glasswater.snow"));

        BlockedPassage jammedCar = new BlockedPassage(RoomId.PARKING_AREA,
                RoomId.CAR,
                new Rectangle(47, 7, 44, 41),
                GameUtil.Direction.UP,
                ItemId.PRYBAR,
                Messages.getText("carpark.jammeddoor.interact.locked"),
                Messages.getText("carpark.jammeddoor.interact.unlocked"),
                null);
        jammedCar.setItemInteractionMessage(ItemId.HAMMER, Messages.getText("carpark.interaction.hammer.cardoor"));
        jammedCar.setItemInteractionMessage(ItemId.STICK, Messages.getText("carpark.interaction.stick.cardoor"));
        jammedCar.setTravelFlag(FLAG_KEYS_MISSING);

        Describable carDamageDescription = new GeneralDescribable(Messages.getText("carpark.damage.description"),
                new Rectangle(15, 19, 28, 10));

        _descriptionList1 = new LinkedList<Describable>();
        _descriptionList1.add(carDamageDescription);

        _passageList1 = new LinkedList<Passage>();
        _passageList1.add(enterCar);
        _passageList1.add(toPath);

        _passageList2 = new LinkedList<Passage>();
        _passageList2.add(toPath);
        _passageList2.add(snowCar);

        _passageList3 = new LinkedList<Passage>();
        _passageList3.add(toPath);
        _passageList3.add(jammedCar);

        _stick = new PickupableItem(new Sprite(new Texture("rooms/parking/stick.png")),
                new Sprite(new Texture("items/stickicon.png")),
                new Rectangle(60, 4, 19, 6),
                ItemId.STICK);
        _stick.setPickupText(Messages.getText("carpark.pickup.stick"));
    }

    @Override
    public void enter() {
        super.enter();
        AudioManager.playMusic(MusicId.WIND, false);
    }

    @Override
    public void update(float delta) {
        _snowAnimation.updateSnow(delta);
        super.update(delta);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        _snowAnimation.draw(renderer);
        super.draw(renderer);
    }

    @Override
    public void flagUpdate() {
        if (PlayGame.getFlagManager().getFlag(FLAG_BODY_FOUND).getState()
                && !PlayGame.getFlagManager().getFlag(FLAG_CAR_SNOWREMOVED).getState()) {
            setPassageList(_passageList2);
            setSprite(_roomSprite2);
            pushEnterRemark("carpark.enter.snowcovered");
        } else if (PlayGame.getFlagManager().getFlag(FLAG_CAR_SNOWREMOVED).getState()) {
            setPassageList(_passageList3);
            setSprite(_roomSprite3);
            setDescriptionList(_descriptionList1);
            if (!_stick.isPickedUp()) {
                _pickupableItemList.clear();
                addPickupableItem(_stick);
            }
        } else {
            setPassageList(_passageList1);
            setSprite(_roomSprite1);
        }
    }
}
