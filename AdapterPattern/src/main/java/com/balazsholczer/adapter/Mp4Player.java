package com.balazsholczer.adapter;

public class Mp4Player implements AdvancedMediaPlayer {
    
    @Override
    public void playVlc(String fileName) {
        // Do nothing - MP4 player doesn't support VLC
    }
    
    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file: " + fileName);
    }
}