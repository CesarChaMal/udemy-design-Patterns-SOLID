package com.balazsholczer.memento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamMemento {
    
    public record StateSnapshot<T>(String id, T state, LocalDateTime timestamp, String tag) {
        public StateSnapshot(String id, T state, String tag) {
            this(id, state, LocalDateTime.now(), tag);
        }
        
        public boolean isTagged(String tag) {
            return this.tag.equals(tag);
        }
        
        public boolean isAfter(LocalDateTime time) {
            return timestamp.isAfter(time);
        }
        
        public boolean isBefore(LocalDateTime time) {
            return timestamp.isBefore(time);
        }
    }
    
    public static class StreamStateManager<T> {
        private final List<StateSnapshot<T>> snapshots = new CopyOnWriteArrayList<>();
        private T currentState;
        
        public StreamStateManager(T initialState) {
            this.currentState = initialState;
            saveSnapshot("initial", "Initial state");
        }
        
        public void setState(T state) {
            this.currentState = state;
            saveSnapshot("auto", "State updated");
        }
        
        public void saveSnapshot(String id, String tag) {
            snapshots.add(new StateSnapshot<>(id, currentState, tag));
            System.out.println("StreamMemento: Snapshot saved - " + tag + " (id: " + id + ")");
        }
        
        public T getState() {
            return currentState;
        }
        
        public Optional<T> restoreById(String id) {
            return snapshots.stream()
                          .filter(snapshot -> snapshot.id().equals(id))
                          .reduce((first, second) -> second) // Get latest with this id
                          .map(snapshot -> {
                              currentState = snapshot.state();
                              System.out.println("StreamMemento: Restored state from id: " + id);
                              return currentState;
                          });
        }
        
        public Optional<T> restoreByTag(String tag) {
            return snapshots.stream()
                          .filter(snapshot -> snapshot.isTagged(tag))
                          .reduce((first, second) -> second) // Get latest with this tag
                          .map(snapshot -> {
                              currentState = snapshot.state();
                              System.out.println("StreamMemento: Restored state from tag: " + tag);
                              return currentState;
                          });
        }
        
        public List<StateSnapshot<T>> findSnapshots(Predicate<StateSnapshot<T>> predicate) {
            return snapshots.stream()
                          .filter(predicate)
                          .toList();
        }
        
        public List<StateSnapshot<T>> getSnapshotsByTag(String tag) {
            return findSnapshots(snapshot -> snapshot.isTagged(tag));
        }
        
        public List<StateSnapshot<T>> getSnapshotsAfter(LocalDateTime time) {
            return findSnapshots(snapshot -> snapshot.isAfter(time));
        }
        
        public List<StateSnapshot<T>> getSnapshotsBefore(LocalDateTime time) {
            return findSnapshots(snapshot -> snapshot.isBefore(time));
        }
        
        public Stream<T> getStateHistory() {
            return snapshots.stream().map(StateSnapshot::state);
        }
        
        public void cleanup(Predicate<StateSnapshot<T>> shouldRemove) {
            int sizeBefore = snapshots.size();
            snapshots.removeIf(shouldRemove);
            int sizeAfter = snapshots.size();
            System.out.println("StreamMemento: Cleaned up " + (sizeBefore - sizeAfter) + " snapshots");
        }
        
        public void keepOnlyLatest(int count) {
            if (snapshots.size() > count) {
                List<StateSnapshot<T>> toKeep = snapshots.stream()
                                                       .skip(Math.max(0, snapshots.size() - count))
                                                       .toList();
                snapshots.clear();
                snapshots.addAll(toKeep);
                System.out.println("StreamMemento: Kept only latest " + count + " snapshots");
            }
        }
    }
}