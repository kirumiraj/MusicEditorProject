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
   *
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
   *
   * @return a Pitch object matching the given Pitch
   *
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
    }
    else {
      throw new IllegalArgumentException("MusicModel does not contain this pitch.");
    }
  }

  @Override
  public void addPitch(Pitch p) {
    if (this.pitches.contains(p)) {
      throw new IllegalArgumentException("MusicModel already contains this Pitch.");
    }
    else {
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
      }
      else {
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
      }
      else {
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
   * Finds the lowest and highest pitches in the MusicModel so that they can be used to generate an
   * ordered row of pitches and the octave.
   *
   * @return a list of Pitch objects
   **/
  private List<Pitch> findLowestAndHighestPitch() {
    Pitch lowest = this.pitches.get(0);
    Pitch highest = this.pitches.get(0);
    for (Pitch temp : this.pitches) {
      if (temp.getOctave() < lowest.getOctave() || (temp.getOctave() == lowest.getOctave()
              && temp.getPitch() < lowest.getPitch())) {
        lowest = temp;
      }
      if (temp.getOctave() > highest.getOctave() || (temp.getOctave() == highest.getOctave()
              && temp.getPitch() > highest.getPitch())) {
        highest = temp;
      }
    }
    List<Pitch> l = new ArrayList<Pitch>();
    l.add(lowest);
    l.add(highest);
    return l;
  }

  /**
   * Iterates through all the pitches and finds the longest one.
   *
   * @return the largest number of beats in any Pitch
   **/
  private int maxBeats() {
    int max = 0;
    for (Pitch p: this.pitches) {
      if (p.getNotes().size() > max) {
        max = p.getNotes().size();
      }
    }
    return max;
  }

  /**
   * Generates a full row of pitches and their octaves to be used by toString.
   *
   * @param lowestAndHighest a list with the two lowest and highest pitches
   *
   * @return a full list of Pitches with empty Pitch objects for the pitches MusicModel does not
   *     contain
   **/
  private List<Pitch> generateOctaves(List<Pitch> lowestAndHighest) {
    int startPitch = lowestAndHighest.get(0).getPitch();
    int endPitch = lowestAndHighest.get(1).getPitch();
    int startOctave = lowestAndHighest.get(0).getOctave();
    int endOctave = lowestAndHighest.get(1).getOctave();
    if (endOctave >= startOctave) {
      List<Pitch> generatePitches = new ArrayList<Pitch>();
      for (int i = startOctave; i < endOctave + 1; i++) {
        for (int a = 1; a < 13; a++) {
          Pitch p = new Pitch(a, i);
          if (i == startOctave && i == endOctave) {
            if (a >= startPitch && a <= endPitch) {
              if (this.pitches.contains(p)) {
                generatePitches.add(this.getPitch(a, i));
              }
              else {
                generatePitches.add(p);
              }
            }
          }
          else if (i == startOctave) {
            if (a >= startPitch) {
              if (this.pitches.contains(p)) {
                generatePitches.add(this.getPitch(a, i));
              }
              else {
                generatePitches.add(p);
              }
            }
          }
          else if (i == endOctave) {
            if (a <= endPitch) {
              if (this.pitches.contains(p)) {
                generatePitches.add(this.getPitch(a, i));
              }
              else {
                generatePitches.add(p);
              }
            }
          }
          else {
            if (this.pitches.contains(p)) {
              generatePitches.add(this.getPitch(a, i));
            }
            else {
              generatePitches.add(p);
            }
          }
        }
      }
      return generatePitches;
    }
    else {
      throw new IllegalArgumentException("Please put in a valid start and end");
    }
  }

  /**
   * Iterates through all the pitches and finds the longest one.
   *
   * @param p the integer of the pitch
   *
   * @return the largest number of beats in any Pitch
   **/
  private String pitchToString(int p) {
    switch (p) {
      case 1: return "C";
      case 2: return "C#";
      case 3: return "D";
      case 4: return "D#";
      case 5: return "E";
      case 6: return "F";
      case 7: return "F#";
      case 8: return "G";
      case 9: return "G#";
      case 10: return "A";
      case 11: return "A#";
      case 12: return "B";
      default: throw new IllegalArgumentException("Invalid pitch in pitchToString,"
              + " this should not happen.");
    }
  }

  /**
   * Returns a string depending on which note is passed to it.
   *
   * @param getNote the integer of the note
   *
   * @return a string corresponding to the note value
   *
   * @throws IllegalArgumentException if the note is invalid
   **/
  private String generateNote(int getNote) {
    switch (getNote) {
      case 0:
        return "     ";
      case 1:
        return "  |  ";
      case 2:
        return "  X  ";
      default:
        throw new IllegalArgumentException("Invalid note value, must be 0, 1, or 2.");
    }
  }

  /**
   * Pads a given integer depending on what the largest one is.
   *
   * @param beat the integer of the current beat
   * @param max the largest beat
   *
   * @return a padded string with the current beat
   *
   * @throws IllegalArgumentException if the max beat is smaller than the current one
   **/
  private String padBeat(int beat, int max) {
    if (max >= beat) {
      return String.format("%" + String.valueOf(max).length() + "s", beat);
    }
    else {
      throw new IllegalArgumentException("Current beat cannot be bigger than max");
    }
  }

  /**
   * Takes a list of pitches and turns them into a string with their corresponding note names.
   *
   * @param pitchList a list of all pitches to be turned into strings
   *
   * @return a string containing all pitches and their octaves
   *
   * @throws IllegalStateException if an octave is larger than 99, this is a view restriction
   **/
  private String generateOctaveText(List<Pitch> pitchList) {
    String s = "";
    for (int i = 0; i < pitchList.size(); i++) {
      Pitch p = pitchList.get(i);
      if (p.getOctave() > 99) {
        throw new IllegalStateException("The MusicModel toString method does not support more"
                + "than 100 octaves");
      }
      s += String.format("%" + 5 + "s", this.pitchToString(p.getPitch()) + p.getOctave());
    }
    return s;
  }

  /**
   * Generates a row of notes at the given beat.
   *
   * @param pitchList a list of all pitches to be turned into strings
   * @param beat the current beat
   *
   * @return a string of a row of notes at the given beat
   **/
  private String generateRow(List<Pitch> pitchList, int beat) {
    String s = "";
    for (int i = 0; i < pitchList.size(); i++) {
      Pitch p = pitchList.get(i);
      s += this.generateNote(p.getNote(beat));
    }
    return s;
  }

  @Override
  public String toString() {
    List<Pitch> lowestAndHighest = this.findLowestAndHighestPitch();
    List<Pitch> lPitch = this.generateOctaves(lowestAndHighest);
    int maxBeats = this.maxBeats();
    String octaves = String.format("%" + String.valueOf(maxBeats).length() + "s",
            this.generateOctaveText(lPitch));
    String beats = "";
    for (int i = 0; i < maxBeats - 1; i++) {
      beats += this.padBeat(i, maxBeats) + this.generateRow(lPitch, i) + "\n";
    }
    beats += this.padBeat(maxBeats - 1, maxBeats) + this.generateRow(lPitch, maxBeats - 1);
    return octaves + "\n" + beats;
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
     * @param pitch      The pitch (in the range [0, 127], where 60 represents C4,
     *                   the middle-C on a piano)
     * @param volume     The volume (in the range [0, 127])
     */
    @Override
    public CompositionBuilder<IMusicModel> addNote(int start, int end, int instrument, int pitch, int volume) {
      int realPitch = (pitch % 12) + 1;
      int realOctave = (int) Math.floor(pitch / 12) - 1;
      Pitch checkPitch = new Pitch(realPitch, realOctave);
      int duration = end - start - 1;
      if (this.notes.contains(checkPitch)) {
        for (Pitch p: this.notes) {
          if (p.equals(checkPitch)) {
            p.addNote(start, duration);
          }
        }
      }
      else {
        checkPitch.addNote(start, duration);
        this.notes.add(checkPitch);
      }
      return this;
    }
  }
}
