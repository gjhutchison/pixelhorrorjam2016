package com.kowaisugoi.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class AudioManager implements Disposable {
    private static final Map<SoundId, Sound> _soundMap = new HashMap<SoundId, Sound>();
    private static final Map<MusicId, Music> _musicMap = new HashMap<MusicId, Music>();

    private static MusicId _currentSong = MusicId.NONE;

    public static void initSounds() {
        _soundMap.put(SoundId.DOOR_CREAK, Gdx.audio.newSound(Gdx.files.internal("audio/effects/Door.mp3")));
        _soundMap.put(SoundId.DOOR_LOCKED, Gdx.audio.newSound(Gdx.files.internal("audio/effects/Door Locked.mp3")));

        _musicMap.put(MusicId.MAIN_MENU, Gdx.audio.newMusic(Gdx.files.internal("audio/music/bensound-betterdays.mp3")));
    }

    public static void playSound(SoundId id) {
        if (id == null) {
            return;
        }

        if (_soundMap.containsKey(id)) {
            _soundMap.get(id).play();
        }
    }

    public static void playMusic(MusicId id) {
        if (id == null) {
            return;
        }
        if (_musicMap.containsKey(id)) {
            stopSong(_currentSong);

            _musicMap.get(id).play();
            _currentSong = id;
        }
    }

    public static void stopSong(MusicId id) {
        if (_currentSong != MusicId.NONE) {
            _musicMap.get(_currentSong).stop();
            _currentSong = MusicId.NONE;
        }
    }

    public static void stopMusic() {
        for (MusicId id : _musicMap.keySet()) {
            if (_musicMap.get(id).isPlaying()) {
                _musicMap.get(id).stop();
                _currentSong = MusicId.NONE;
                return;
            }
        }
    }

    @Override
    public void dispose() {
        for (SoundId id : _soundMap.keySet()) {
            _soundMap.get(id).dispose();
        }
    }
}
