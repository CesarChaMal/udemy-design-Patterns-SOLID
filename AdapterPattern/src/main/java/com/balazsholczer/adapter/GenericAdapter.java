package com.balazsholczer.adapter;

import java.util.Map;
import java.util.function.Function;

public class GenericAdapter<T, R> {
    
    private final Map<String, Function<T, R>> adapters;
    
    public GenericAdapter(Map<String, Function<T, R>> adapters) {
        this.adapters = Map.copyOf(adapters);
    }
    
    public R adapt(String type, T input) {
        Function<T, R> adapter = adapters.get(type.toLowerCase());
        if (adapter == null) {
            throw new IllegalArgumentException("No adapter for type: " + type);
        }
        return adapter.apply(input);
    }
    
    public static GenericAdapter<String, Void> createMediaAdapter() {
        return new GenericAdapter<>(Map.of(
            "mp3", fileName -> {
                System.out.println("Playing mp3 file: " + fileName);
                return null;
            },
            "vlc", fileName -> {
                new VlcPlayer().playVlc(fileName);
                return null;
            },
            "mp4", fileName -> {
                new Mp4Player().playMp4(fileName);
                return null;
            }
        ));
    }
}