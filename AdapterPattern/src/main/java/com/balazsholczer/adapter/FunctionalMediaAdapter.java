package com.balazsholczer.adapter;

import java.util.Map;
import java.util.function.Consumer;

public class FunctionalMediaAdapter {
    
    public record MediaFile(String type, String fileName) {}
    
    private static final Map<String, Consumer<String>> PLAYERS = Map.of(
        "mp3", fileName -> System.out.println("Playing mp3 file: " + fileName),
        "vlc", fileName -> new VlcPlayer().playVlc(fileName),
        "mp4", fileName -> new Mp4Player().playMp4(fileName)
    );
    
    public static void play(MediaFile media) {
        Consumer<String> player = PLAYERS.get(media.type().toLowerCase());
        if (player != null) {
            player.accept(media.fileName());
        } else {
            System.out.println("Invalid media. " + media.type() + " format not supported");
        }
    }
    
    public static void play(String audioType, String fileName) {
        play(new MediaFile(audioType, fileName));
    }
}