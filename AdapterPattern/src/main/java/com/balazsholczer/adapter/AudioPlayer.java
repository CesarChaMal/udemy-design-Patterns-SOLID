package com.balazsholczer.adapter;

public class AudioPlayer implements MediaPlayer {
    
    private MediaAdapter adapter;
    
    @Override
    public void play(String audioType, String fileName) {
        switch (audioType.toLowerCase()) {
            case "mp3" -> System.out.println("Playing mp3 file: " + fileName);
            case "vlc", "mp4" -> {
                adapter = new MediaAdapter(audioType);
                adapter.play(audioType, fileName);
            }
            default -> System.out.println("Invalid media. " + audioType + " format not supported");
        }
    }
}