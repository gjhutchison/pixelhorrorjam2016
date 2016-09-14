package com.kowaisugoi.game.audio;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class AudioManager implements Disposable {
    private static final Map<SoundId, Sound> soundMap = new HashMap<SoundId, Sound>();
    private static final Map<MusicId, Music> musicMap = new HashMap<MusicId, Music>();

    public static void initSounds() {
        soundMap.put(SoundId.DOOR_CREAK, Gdx.audio.newSound(Gdx.files.internal("audio/effects/Door.mp3")));
        soundMap.put(SoundId.DOOR_LOCKED, Gdx.audio.newSound(Gdx.files.internal("audio/effects/Door Locked.mp3")));

        musicMap.put(MusicId.GENERAL_MUSIC, Gdx.audio.newMusic(Gdx.files.internal("audio/music/bensound-betterdays.mp3")));
    }

    public static void playSound(SoundId id) {
        if (id == null) {
            return;
        }

        if (soundMap.containsKey(id)) {
            soundMap.get(id).play();
        }
    }

    public static void playMusic(MusicId id) {
        if (id == null) {
            return;
        }

        if (musicMap.containsKey(id)) {
            musicMap.get(id).play();
        }
    }

    @Override
    public void dispose() {
        for (SoundId id : soundMap.keySet()) {
            soundMap.get(id).dispose();
        }
    }
}
