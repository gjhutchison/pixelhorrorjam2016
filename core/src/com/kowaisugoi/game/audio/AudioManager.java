package com.kowaisugoi.game.audio;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class AudioManager implements Disposable {
    private static final Map<SoundId, Sound> soundMap = new HashMap<SoundId, Sound>();

    public static void initSounds() {
        soundMap.put(SoundId.DOOR_CREAK, Gdx.audio.newSound(Gdx.files.internal("audio/effects/REEEE.mp3")));
        soundMap.put(SoundId.DOOR_LOCKED, Gdx.audio.newSound(Gdx.files.internal("audio/effects/lockedhehe.mp3")));
    }

    public static void playSound(SoundId id) {
        if (id == null) {
            return;
        }

        if (soundMap.containsKey(id)) {
            soundMap.get(id).play();
        }
    }

    @Override
    public void dispose() {
        for (SoundId id : soundMap.keySet()) {
            soundMap.get(id).dispose();
        }
    }
}
