package com.kowaisugoi.game.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kowaisugoi.game.audio.AudioManager;
import com.kowaisugoi.game.audio.MusicId;
import com.kowaisugoi.game.audio.SoundId;
import com.kowaisugoi.game.graphics.FireAnimation;
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

import static com.kowaisugoi.game.control.flags.FlagId.FLAG_TORCH_PLACED;

public class RoomCrawlspace extends StandardRoom {

    private static final String ROOM_URL = "rooms/crawlspace/crawlspace_dark.png";
    private static final String ROOM_URL2 = "rooms/crawlspace/crawlspace_torch.png";

    private Sprite _roomSprite2;

    private List<Describable> _describableList2;
    private List<Passage> _passageList2;
    private FireAnimation _fireAnimation;
    private PickupableItem _prybar;

    public RoomCrawlspace() {
        super(new Sprite(new Texture(ROOM_URL)));

        _roomSprite2 = new Sprite(new Texture(ROOM_URL2));

        _fireAnimation = new FireAnimation(127, 31, 45);

        Passage turnAround = new DirectionalPassage(RoomId.CRAWLSPACE,
                RoomId.MAIN_HALL,
                new Rectangle(55, 0, 50, 10),
                GameUtil.Direction.DOWN);
        turnAround.setSoundEffect(SoundId.CLICK);
        addPassage(turnAround);

        _prybar = new PickupableItem(new Sprite(new Texture("rooms/crawlspace/prybar.png")),
                new Sprite(new Texture("items/prybaricon.png")),
                new Rectangle(31, 25, 15, 8),
                ItemId.PRYBAR);
        _prybar.setPickupText(Messages.getText("crawlspace.pickup.prybar"));

        BlockedPassage dirtPatch = new BlockedPassage(RoomId.CRAWLSPACE,
                RoomId.CRAWLSPACE,
                new Rectangle(116, 5, 20, 14),
                GameUtil.Direction.UP,
                ItemId.TORCH,
                Messages.getText("crawlspace.dirtpatch.locked"),
                Messages.getText("crawlspace.dirtpatch.unlocked"),
                null);
        dirtPatch.setUnlockedToggleFlag(FLAG_TORCH_PLACED);
        addPassage(dirtPatch);

        Describable torchDescription = new GeneralDescribable(
                Messages.getText("crawlspace.torch.thought"),
                new Rectangle(123, 9, 9, 25));
        _describableList2 = new LinkedList<Describable>();
        _describableList2.add(torchDescription);

        _passageList2 = new LinkedList<Passage>();
        _passageList2.add(turnAround);

        pushEnterRemark("crawlspace.enter.thought");
    }

    @Override
    public void enter() {
        super.enter();
        AudioManager.playMusic(MusicId.CRAWLSPACE, false);
    }

    @Override
    public void flagUpdate() {
        if (PlayGame.getFlagManager().getFlag(FLAG_TORCH_PLACED).getState()) {
            setPassageList(_passageList2);
            setSprite(_roomSprite2);
            setDescriptionList(_describableList2);
            if (!_prybar.isPickedUp()) {
                _pickupableItemList.clear();
                _pickupableItemList.add(_prybar);
            }
        }
    }

    @Override
    public void update(float delta) {
        if (PlayGame.getFlagManager().getFlag(FLAG_TORCH_PLACED).getState()) {
            _fireAnimation.update(delta);
        }
        super.update(delta);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        if (PlayGame.getFlagManager().getFlag(FLAG_TORCH_PLACED).getState()) {
            _fireAnimation.draw(renderer);
        }
        super.draw(renderer);
    }
}
