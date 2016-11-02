package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Pitch, which is a list of Integers from 0-3, an integer for the pitch and an
 * integer for the octave. Two pitches are equal when they are being
 * played in the same octave and at the same pitch. The notes represent each beat the Pitch is
 * being played.
 */
public class Pitch {
  private List<Integer> notes;
  private final int pitch;
  private final int octave;

  /**
   * Constructor for Pitch.
   *
   * @param pitch the pitch of the object
   * @param octave the octave of the object
   *
   * @throws IllegalArgumentException if the pitch is given invalid items
   */
  public Pitch(List<Integer> notes, int pitch, int octave) {
    if (pitch < 1 || pitch > 12) {
      throw new IllegalArgumentException("Invalid pitch passed to Pitch constructor.");
    }
    if (octave < 0) {
      throw new IllegalArgumentException("Invalid octave passed to Pitch constructor.");
    }
    for (int i : notes) {
      if (i < 0 || i > 2) {
        throw new IllegalArgumentException("Invalid list of pitches, can only be 0, 1, or 2.");
      }
    }
    this.pitch = pitch;
    this.notes = notes;
    this.octave = octave;
  }

  /**
   * Constructor for Pitch with no list of notes.
   *
   * @param pitch the pitch of the object
   * @param octave the octave of the object
   *
   * @throws IllegalArgumentException if the pitch is given invalid items
   */
  public Pitch(int pitch, int octave) {
    if (pitch < 1 || pitch > 12) {
      throw new IllegalArgumentException("Invalid pitch passed to Pitch constructor.");
    }
    if (octave < 0) {
      throw new IllegalArgumentException("Invalid octave passed to Pitch constructor.");
    }
    List<Integer> l = new ArrayList<Integer>();
    this.pitch = pitch;
    this.notes = l;
    this.octave = octave;
  }

  /**
   * Returns an integer corresponding to what is playing at a given beat. If nothing is playing,
   * it will return 0. If it is the first beat of the note it will return 2. If it is not the
   * first beat of the note it will return 1.
   *
   * @param beat the beat that the note is playing at
   *
   * @return an integer corresponding to what is playing
   **/
  public int getNote(int beat) {
    try {
      return this.notes.get(beat);
    }
    catch (IndexOutOfBoundsException e) {
      return 0;
    }
  }

  /**
   * Adds a note to the pitch at the given beat for the given duration. This will overwrite any
   * notes and durations that are being played at the beats that this note is.
   *
   * @param beat the beat to add a note
   * @param duration the duration of the note
   **/
  public void addNote(int beat, int duration) {
    int pLen = this.notes.size();
    if (pLen > beat) {
      this.removeNotes(beat);
      this.notes.set(beat, 2);
      if (duration > 1) {
        for (int i = 1; i < duration + 1; i++) {
          try {
            this.removeNotes(beat + i);
            this.notes.set(beat + i, 1);
          }
          catch (IndexOutOfBoundsException e) {
            this.notes.add(1);
          }
        }
      }
    }
    else {
      int loopCount = beat - pLen;
      for (int i = 0; i < loopCount; i++) {
        this.notes.add(0);
      }
      this.notes.add(2);
      for (int i = 0; i < duration; i++) {
        this.notes.add(1);
      }
    }
  }

  /**
   * Adds a list of notes to the end of this Pitch.
   *
   * @param notes the list of notes to add
   **/
  public void addNotes(List<Integer> notes) {
    this.notes.addAll(notes);
  }

  /**
   * Sets the notes of this pitch to the given input.
   *
   * @param notes the list of notes to set
   **/
  public void setNotes(List<Integer> notes) {
    this.notes = notes;
  }

  /**
   * Removes a note, will remove the note for the consecutive beats that it is being played as
   * well.
   *
   * @param beat the beat of the Pitch to start removing notes
   **/
  public void removeNotes(int beat) {
    boolean nextItem = true;
    int i = 0;
    while (nextItem) {
      this.notes.set(beat + i, 0);
      i += 1;
      if (beat + i >= this.notes.size()) {
        nextItem = false;
      }
      else if (this.notes.get(beat + i) != 1) {
        nextItem = false;
      }
    }
  }

  /**
   * Returns the notes in this Pitch.
   *
   * @return a list of notes
   **/
  public List<Integer> getNotes() {
    return this.notes;
  }

  /**
   * Returns the pitch of this Pitch.
   *
   * @return a pitch
   **/
  public int getPitch() {
    return this.pitch;
  }

  /**
   * Returns the octave of this Pitch.
   *
   * @return an octave
   **/
  public int getOctave() {
    return this.octave;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Pitch) {
      Pitch p = (Pitch) o;
      return this.getOctave() == p.getOctave() && this.getPitch() == p.getPitch();
    }
    else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return this.octave * 45985 + this.pitch * 23498;
  }
}
