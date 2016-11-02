package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

// Tests for the {@link Card} class.
public class PitchTest {

  @Test
  public void testConstructorAndGetMethods() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(0);
    bList.add(0);
    bList.add(2);
    bList.add(1);
    bList.add(1);
    Pitch p = new Pitch(bList, 1, 3);
    assertEquals(3, p.getOctave());
    assertEquals(1, p.getPitch());
    assertEquals(bList, p.getNotes());
  }

  @Test
  public void testConstructorAndGetMethods2() {
    List<Integer> bList = new ArrayList<Integer>();
    Pitch p = new Pitch(1, 3);
    assertEquals(3, p.getOctave());
    assertEquals(1, p.getPitch());
    assertEquals(bList, p.getNotes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException() {
    Pitch p = new Pitch(0, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException2() {
    Pitch p = new Pitch(13, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException3() {
    Pitch p = new Pitch(12, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorException4() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(4);
    Pitch p = new Pitch(bList, 12, 0);
  }

  @Test
  public void testIsPlaying() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(0);
    bList.add(0);
    bList.add(2);
    bList.add(1);
    bList.add(1);
    Pitch p = new Pitch(bList, 1, 3);
    assertEquals(2, p.getNote(2));
    assertEquals(1, p.getNote(3));
    assertEquals(1, p.getNote(4));
  }

  @Test
  public void testAddNote() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(0);
    bList.add(0);
    bList.add(2);
    bList.add(1);
    bList.add(1);
    Pitch p = new Pitch(bList, 1, 3);
    p.addNote(6, 0);
    assertEquals(0, p.getNote(5));
    assertEquals(2, p.getNote(6));
    assertEquals(0, p.getNote(7));
  }

  @Test
  public void testAddNote2() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(0);
    bList.add(0);
    bList.add(2);
    bList.add(1);
    bList.add(1);
    Pitch p = new Pitch(bList, 1, 3);
    p.addNote(2, 0);
    assertEquals(0, p.getNote(1));
    assertEquals(2, p.getNote(2));
    assertEquals(0, p.getNote(3));
  }

  @Test
  public void testAddNote3() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(0);
    bList.add(0);
    bList.add(2);
    bList.add(1);
    bList.add(1);
    Pitch p = new Pitch(bList, 1, 3);
    p.addNote(2, 4);
    assertEquals(0, p.getNote(1));
    assertEquals(2, p.getNote(2));
    assertEquals(1, p.getNote(3));
    assertEquals(1, p.getNote(4));
    assertEquals(1, p.getNote(5));
    assertEquals(1, p.getNote(6));
    assertEquals(0, p.getNote(7));
  }

  @Test
  public void testAddNote4() {
    List<Integer> bList = new ArrayList<Integer>();
    Pitch p = new Pitch(bList, 1, 3);
    p.addNote(10, 4);
    assertEquals(0, p.getNote(3));
    assertEquals(0, p.getNote(9));
    assertEquals(2, p.getNote(10));
    assertEquals(1, p.getNote(11));
    assertEquals(1, p.getNote(12));
    assertEquals(1, p.getNote(13));
    assertEquals(1, p.getNote(14));
    assertEquals(0, p.getNote(15));
  }

  @Test
  public void testEquals() {
    Pitch p = new Pitch(1, 3);
    Pitch q = new Pitch(1, 3);
    Pitch w = new Pitch(2, 3);
    assertEquals(true, p.equals(q));
    assertEquals(false, p.equals(w));
  }
}
