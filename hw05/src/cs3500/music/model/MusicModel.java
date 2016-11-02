package cs3500.music.model;

import java.util.List;
import java.util.ArrayList;
import cs3500.music.util.CompositionBuilder;


/**
 * Represents a MusicModel, which is a list of Pitch objects.
 */
public class MusicModel implements IMusicModel {
  private List<Pitch> pitches;
  private int tempo;

  /**
   * Constructor for MusicModel, if list of pitches contains duplicates,
   * it will only add the first one.
   *
   * @param pitches list of pitches to add to the MusicModel
   * @throws IllegalArgumentException if a pitch has invalid pitch, octave, or notes
   */
  public MusicModel(List<Pitch> pitches, int tempo) {
    this.pitches = new ArrayList<Pitch>();
    for (Pitch p: pitches) {
      if (p.getPitch() < 1 || p.getPitch() > 12) {
        throw new IllegalArgumentException("Pitch integer must be between 1 and 12.");
      }
      if (p.getOctave() < 0) {
        throw new IllegalArgumentException("Octaves cannot be below 0.");
      }
      for (int i = 0; i < p.getNotes().size(); i++) {
        if (p.getNotes().get(i) < 0 || p.getNotes().get(i) > 2) {
          throw new IllegalArgumentException("Pitch notes can only be 0, 1, or 2.");
        }
      }
      if (!(this.pitches.contains(p))) {
        this.pitches.add(p);
      }
    }
    this.tempo = tempo;
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  @Override
  public void setTempo(int t) {
    this.tempo = t;
  }

  @Override
  public Pitch getPitch(int pitch, int octave) {
    Pitch p = new Pitch(pitch, octave);
    for (Pitch a : this.pitches) {
      if (a.equals(p)) {
        return a;
      }
    }
    throw new IllegalArgumentException("MusicModel does not contain this pitch.");
  }

  /**
   * Takes a Pitch object, returns a corresponding Pitch object from this MusicModel.
   *
   * @param p a Pitch object for the desired Pitch in the MusicModel
   * @return a Pitch object matching the given Pitch
   * @throws IllegalArgumentException if the MusicModel does not contain the given Pitch
   */
  public Pitch getPitch(Pitch p) {
    for (Pitch a : this.pitches) {
      if (a.equals(p)) {
        return a;
      }
    }
    throw new IllegalArgumentException("MusicModel does not contain this pitch.");
  }

  @Override
  public List<Pitch> getPitches() {
    return this.pitches;
  }

  @Override
  public void addNote(int pitch, int octave, int beat, int duration) {
    Pitch p = new Pitch(pitch, octave);
    if (this.pitches.contains(p)) {
      this.getPitch(pitch, octave).addNote(beat, duration);
    }
    throw new IllegalArgumentException("MusicModel does not contain this pitch.");
  }

  @Override
  public void addNote(Pitch p, int beat, int duration) {
    if (this.pitches.contains(p)) {
      this.getPitch(p).addNote(beat, duration);
    }
    throw new IllegalArgumentException("MusicModel does not contain this pitch.");
  }

  @Override
  public void addNotes(int pitch, int octave, List<Integer> notes) {
    Pitch p = new Pitch(pitch, octave);
    if (this.pitches.contains(p)) {
      this.getPitch(pitch, octave).addNotes(notes);
    }
    throw new IllegalArgumentException("MusicModel does not contain this pitch.");
  }

  @Override
  public void addNotes(Pitch p, List<Integer> notes) {
    if (this.pitches.contains(p)) {
      this.getPitch(p).addNotes(notes);
    } else {
      throw new IllegalArgumentException("MusicModel does not contain this pitch.");
    }
  }

  @Override
  public void addPitch(Pitch p) {
    if (this.pitches.contains(p)) {
      throw new IllegalArgumentException("MusicModel already contains this Pitch.");
    } else {
      this.pitches.add(p);
    }
  }

  @Override
  public void removePitch(Pitch p) {
    for (int i = 0; i < this.pitches.size(); i++) {
      Pitch a = this.pitches.get(i);
      if (a.equals(p)) {
        this.pitches.remove(i);
        return;
      }
    }
    throw new IllegalArgumentException("MusicModel does not contain this pitch.");
  }

  @Override
  public void combineMusicModelSimultaneous(IMusicModel s) {
    for (Pitch p : s.getPitches()) {
      if (this.pitches.contains(p)) {
        this.removePitch(p);
        this.pitches.add(p);
      } else {
        this.pitches.add(p);
      }
    }
  }

  @Override
  public void combineMusicModelConsecutive(IMusicModel s) {
    int max = this.maxBeats();
    for (int i = 0; i < this.pitches.size(); i++) {
      List<Integer> empty = new ArrayList<Integer>();
      Pitch p = this.pitches.get(i);
      int loop = max - p.getNotes().size();
      for (int a = 0; a < loop; a++) {
        empty.add(0);
      }
      this.addNotes(p, empty);
    }
    for (Pitch p : s.getPitches()) {
      List<Integer> empty = new ArrayList<Integer>();
      if (this.pitches.contains(p)) {
        this.addNotes(p, p.getNotes());
      } else {
        for (int a = 0; a < max; a++) {
          empty.add(0);
        }
        empty.addAll(p.getNotes());
        p.setNotes(empty);
        this.pitches.add(p);
      }
    }
  }

  /**
   * Iterates through all the pitches and finds the longest one.
   *
   * @return the largest number of beats in any Pitch
   **/
  private int maxBeats() {
    int max = 0;
    for (Pitch p : this.pitches) {
      if (p.getNotes().size() > max) {
        max = p.getNotes().size();
      }
    }
    return max;
  }

  /**
   * Builder for MusicModels
   */
  public static final class Builder implements CompositionBuilder<IMusicModel> {

    private List<Pitch> notes;
    private int tempo;

    /**
     * Constructs a new Song Builder object
     */
    public Builder() {
      this.notes = new ArrayList<Pitch>();
      this.tempo = 120;
    }

    /**
     * Constructs an actual composition, given the notes that have been added
     *
     * @return The new composition
     */
    @Override
    public IMusicModel build() {
      return new MusicModel(this.notes, this.tempo);
    }

    /**
     * Sets the tempo of the piece
     *
     * @param tempo The speed, in microseconds per beat
     * @return This builder
     */
    @Override
    public CompositionBuilder<IMusicModel> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    /**
     * Adds a new note to the piece
     *
     * @param start      The start time of the note, in beats
     * @param end        The end time of the note, in beats
     * @param instrument The instrument number (to be interpreted by MIDI)
     * @param pitch      The pitch (in the range [0, 127], where 60 represents C4, the middle-C on a
     *                   piano)
     * @param volume     The volume (in the range [0, 127])
     */
    @Override
    public CompositionBuilder<IMusicModel> addNote(int start, int end, int instrument, int pitch, int volume) {
      int realPitch = (pitch % 12) + 1;
      int realOctave = (int) Math.floor(pitch / 12) - 1;
      Pitch checkPitch = new Pitch(realPitch, realOctave);
      int duration = end - start - 1;
      if (this.notes.contains(checkPitch)) {
        for (Pitch p : this.notes) {
          if (p.equals(checkPitch)) {
            p.addNote(start, duration);
          }
        }
      } else {
        checkPitch.addNote(start, duration);
        this.notes.add(checkPitch);
      }
      return this;
    }
  }
}