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
        _soundMap.put(SoundId.SNOW_CRUNCH, Gdx.audio.newSound(Gdx.files.internal("audio/effects/snowsteps_2.mp3")));
        _soundMap.put(SoundId.FLOOR_STEP, Gdx.audio.newSound(Gdx.files.internal("audio/effects/footsteps_floor.mp3")));
        _soundMap.put(SoundId.CLICK, Gdx.audio.newSound(Gdx.files.internal("audio/effects/caclick.mp3")));
        _soundMap.put(SoundId.UNCLE_GASP, Gdx.audio.newSound(Gdx.files.internal("audio/effects/uncleNoise.mp3")));

        _musicMap.put(MusicId.MAIN_MENU, Gdx.audio.newMusic(Gdx.files.internal("audio/music/bensound-betterdays.mp3")));

        Music dark = Gdx.audio.newMusic(Gdx.files.internal("audio/music/244961__patricklieberkind__dark-ambience.mp3"));
        dark.setLooping(true);
        _musicMap.put(MusicId.DARK, dark);

        Music drone = Gdx.audio.newMusic(Gdx.files.internal("audio/music/144921__thesoundcatcher__deepdrone.mp3"));
        drone.setLooping(true);
        _musicMap.put(MusicId.DRONE, drone);

        Music bedroom = Gdx.audio.newMusic(Gdx.files.internal("audio/music/simpleloops/bedroom.mp3"));
        bedroom.setLooping(true);
        _musicMap.put(MusicId.BEDROOM, bedroom);

        Music cozy = Gdx.audio.newMusic(Gdx.files.internal("audio/music/simpleloops/cozy_2.mp3"));
        cozy.setLooping(true);
        _musicMap.put(MusicId.COZY, cozy);

        Music crawlspace = Gdx.audio.newMusic(Gdx.files.internal("audio/music/simpleloops/crawlspace.mp3"));
        crawlspace.setLooping(true);
        _musicMap.put(MusicId.CRAWLSPACE, crawlspace);

        Music howl = Gdx.audio.newMusic(Gdx.files.internal("audio/music/simpleloops/howl.mp3"));
        howl.setLooping(true);
        _musicMap.put(MusicId.HOWL, howl);

        Music wind = Gdx.audio.newMusic(Gdx.files.internal("audio/music/simpleloops/wind.mp3"));
        wind.setLooping(true);
        _musicMap.put(MusicId.WIND, wind);
    }

    public static void playSound(SoundId id) {
        if (id == null) {
            return;
        }

        if (_soundMap.containsKey(id)) {
            _soundMap.get(id).play();
        }
    }

    public static void playMusic(MusicId id, boolean restart) {
        if (id == null) {
            return;
        }
        if (_currentSong == id && !restart) {
            return;
        }

        stopSong(_currentSong);
        _musicMap.get(id).play();
        _currentSong = id;
    }

    public static void playMusic(MusicId id) {
        playMusic(id, true);
    }

    public static void stopSong(MusicId id) {
        if (_currentSong != MusicId.NONE) {
            _musicMap.get(id).stop();
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
