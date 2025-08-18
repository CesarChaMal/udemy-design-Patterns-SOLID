package com.balazsholczer.adapter;

public class MediaAdapter implements MediaPlayer {
    
    private AdvancedMediaPlayer advancedPlayer;
    
    public MediaAdapter(String audioType) {
        switch (audioType.toLowerCase()) {
            case "vlc" -> advancedPlayer = new VlcPlayer();
            case "mp4" -> advancedPlayer = new Mp4Player();
            default -> throw new IllegalArgumentException("Unsupported audio type: " + audioType);
        }
    }
    
    @Override
    public void play(String audioType, String fileName) {
        switch (audioType.toLowerCase()) {
            case "vlc" -> advancedPlayer.playVlc(fileName);
            case "mp4" -> advancedPlayer.playMp4(fileName);
        }
    }
}