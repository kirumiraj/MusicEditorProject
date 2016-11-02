package cs3500.music.view;

import cs3500.music.model.IMusicModel;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.Pitch;

/**
 * Class for the console view of a model, implements ViewInterface
 */
public class Console implements ViewInterface {

  private IMusicModel model;

  public Console(IMusicModel model) {
    this.model = model;
  }

  /**
   * Finds the lowest and highest pitches in the MusicModel so that they can be used to generate an
   * ordered row of pitches and the octave.
   *
   * @return a list of Pitch objects
   **/
  private List<Pitch> findLowestAndHighestPitch() {
    Pitch lowest = this.model.getPitches().get(0);
    Pitch highest = this.model.getPitches().get(0);
    for (Pitch temp : this.model.getPitches()) {
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
    for (Pitch p: this.model.getPitches()) {
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
              if (this.model.getPitches().contains(p)) {
                generatePitches.add(this.model.getPitch(a, i));
              }
              else {
                generatePitches.add(p);
              }
            }
          }
          else if (i == startOctave) {
            if (a >= startPitch) {
              if (this.model.getPitches().contains(p)) {
                generatePitches.add(this.model.getPitch(a, i));
              }
              else {
                generatePitches.add(p);
              }
            }
          }
          else if (i == endOctave) {
            if (a <= endPitch) {
              if (this.model.getPitches().contains(p)) {
                generatePitches.add(this.model.getPitch(a, i));
              }
              else {
                generatePitches.add(p);
              }
            }
          }
          else {
            if (this.model.getPitches().contains(p)) {
              generatePitches.add(this.model.getPitch(a, i));
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
  public void run() {
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
    System.out.println(octaves + "\n" + beats);
  }

}
