package com.balazsholczer.adapter;

import java.util.function.Consumer;

public enum EnumMediaAdapter {
    MP3(fileName -> System.out.println("Playing mp3 file: " + fileName)),
    VLC(fileName -> new VlcPlayer().playVlc(fileName)),
    MP4(fileName -> new Mp4Player().playMp4(fileName));
    
    private final Consumer<String> player;
    
    EnumMediaAdapter(Consumer<String> player) {
        this.player = player;
    }
    
    public void play(String fileName) {
        player.accept(fileName);
    }
    
    public static void play(String audioType, String fileName) {
        try {
            EnumMediaAdapter adapter = EnumMediaAdapter.valueOf(audioType.toUpperCase());
            adapter.play(fileName);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid media. " + audioType + " format not supported");
        }
    }
}