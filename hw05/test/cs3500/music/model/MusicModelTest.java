package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

// Tests for the {@link Card} class.
public class MusicModelTest {

  @Test
  public void testAddNotes() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(0);
    bList.add(0);
    bList.add(2);
    bList.add(1);
    bList.add(1);
    List<Integer> bList2 = new ArrayList<Integer>();
    bList2.add(2);
    bList2.add(0);
    bList2.add(2);
    bList2.add(1);
    bList2.add(1);
    bList2.add(1);
    bList2.add(0);
    bList2.add(2);
    Pitch p = new Pitch(bList, 1, 0);
    Pitch p2 = new Pitch(bList2, 2, 0);
    Pitch p3 = new Pitch(bList2, 3, 0);
    List<Pitch> pList = new ArrayList<Pitch>();
    pList.add(p2);
    pList.add(p3);
    pList.add(p);
    MusicModel s = new MusicModel(pList, 0);
    assertEquals(p.getNotes(), bList);
    s.addNotes(p, bList);
    assertEquals(p2.getNotes(), bList2);
    assertEquals(p3.getNotes(), bList2);
    bList.addAll(bList);
    assertEquals(p.getNotes(), bList);
  }

  @Test
  public void testConstructorAndToString() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(0);
    bList.add(0);
    bList.add(2);
    bList.add(1);
    bList.add(1);
    List<Integer> bList2 = new ArrayList<Integer>();
    bList2.add(2);
    bList2.add(0);
    bList2.add(2);
    bList2.add(1);
    bList2.add(1);
    bList2.add(1);
    bList2.add(0);
    bList2.add(2);
    Pitch p = new Pitch(bList, 1, 0);
    Pitch p2 = new Pitch(bList2, 2, 0);
    Pitch p3 = new Pitch(bList2, 3, 0);
    List<Pitch> pList = new ArrayList<Pitch>();
    pList.add(p2);
    pList.add(p3);
    pList.add(p);
    MusicModel s = new MusicModel(pList, 0);
    assertEquals("   C0  C#0   D0\n"
            + "0       X    X  \n"
            + "1               \n"
            + "2  X    X    X  \n"
            + "3  |    |    |  \n"
            + "4  |    |    |  \n"
            + "5       |    |  \n"
            + "6               \n"
            + "7       X    X  ", s.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testConstructorAndToStringException() {
    Pitch p = new Pitch(1, 999);
    List<Pitch> pList = new ArrayList<Pitch>();
    pList.add(p);
    MusicModel m1 = new MusicModel(pList, 0);
    m1.toString();
  }

  @Test
  public void testCombineConsecutive() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(0);
    bList.add(0);
    List<Integer> bList2 = new ArrayList<Integer>();
    bList2.add(2);
    bList2.add(1);
    List<Integer> bList3 = new ArrayList<Integer>();
    bList3.add(2);
    bList3.add(1);
    Pitch p = new Pitch(bList, 1, 0);
    Pitch p2 = new Pitch(bList2, 2, 0);
    Pitch p3 = new Pitch(bList3, 3, 0);
    List<Pitch> pList = new ArrayList<Pitch>();
    List<Pitch> pList2 = new ArrayList<Pitch>();
    pList.add(p);
    pList.add(p2);
    pList2.add(p3);
    MusicModel s = new MusicModel(pList, 0);
    MusicModel s2 = new MusicModel(pList2, 0);
    s.combineMusicModelConsecutive(s2);
    assertEquals("   C0  C#0   D0\n"
            + "0       X       \n"
            + "1       |       \n"
            + "2            X  \n"
            + "3            |  ", s.toString());
  }

  @Test
  public void testCombineConsecutive2() {
    List<Integer> bList = new ArrayList<Integer>();
    List<Integer> bList2 = new ArrayList<Integer>();
    bList2.add(2);
    bList2.add(1);
    List<Integer> bList3 = new ArrayList<Integer>();
    bList3.add(2);
    bList3.add(1);
    Pitch p = new Pitch(bList, 1, 0);
    Pitch p2 = new Pitch(bList2, 2, 0);
    Pitch p3 = new Pitch(bList3, 3, 0);
    List<Pitch> pList = new ArrayList<Pitch>();
    List<Pitch> pList2 = new ArrayList<Pitch>();
    pList.add(p);
    pList.add(p2);
    pList.add(p3);
    pList2.add(p3);
    MusicModel s = new MusicModel(pList, 0);
    MusicModel s2 = new MusicModel(pList2, 0);
    s.combineMusicModelConsecutive(s2);
    assertEquals("   C0  C#0   D0\n"
            + "0       X    X  \n"
            + "1       |    |  \n"
            + "2            X  \n"
            + "3            |  ", s.toString());
  }

  @Test
  public void testCombineConsecutive3() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(0);
    bList.add(0);
    bList.add(0);
    bList.add(0);
    bList.add(0);
    List<Integer> bList2 = new ArrayList<Integer>();
    bList2.add(2);
    bList2.add(1);
    List<Integer> bList3 = new ArrayList<Integer>();
    bList3.add(2);
    bList3.add(1);
    List<Integer> bList4 = new ArrayList<Integer>();
    bList4.add(1);
    bList4.add(2);
    Pitch p = new Pitch(bList, 1, 0);
    Pitch p2 = new Pitch(bList2, 2, 0);
    Pitch p3 = new Pitch(bList3, 3, 0);
    Pitch p4 = new Pitch(bList4, 3, 0);
    List<Pitch> pList = new ArrayList<Pitch>();
    List<Pitch> pList2 = new ArrayList<Pitch>();
    pList.add(p);
    pList.add(p2);
    pList.add(p3);
    pList2.add(p4);
    MusicModel s = new MusicModel(pList, 0);
    MusicModel s2 = new MusicModel(pList2, 0);
    s.combineMusicModelConsecutive(s2);
    assertEquals("   C0  C#0   D0\n"
            + "0       X    X  \n"
            + "1       |    |  \n"
            + "2               \n"
            + "3               \n"
            + "4               \n"
            + "5            |  \n"
            + "6            X  ", s.toString());
  }

  @Test
  public void testCombineSimultaneous() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(0);
    bList.add(0);
    bList.add(2);
    bList.add(1);
    bList.add(1);
    List<Integer> bList2 = new ArrayList<Integer>();
    bList2.add(2);
    bList2.add(0);
    bList2.add(2);
    bList2.add(1);
    bList2.add(1);
    bList2.add(1);
    bList2.add(0);
    bList2.add(2);
    Pitch p = new Pitch(bList, 1, 0);
    Pitch p2 = new Pitch(bList2, 2, 0);
    Pitch p3 = new Pitch(bList2, 3, 0);
    List<Pitch> pList = new ArrayList<Pitch>();
    pList.add(p2);
    pList.add(p3);
    pList.add(p);
    MusicModel s = new MusicModel(pList, 0);
    MusicModel s2 = new MusicModel(pList, 0);
    s.combineMusicModelSimultaneous(s2);
    assertEquals("   C0  C#0   D0\n"
            + "0       X    X  \n"
            + "1               \n"
            + "2  X    X    X  \n"
            + "3  |    |    |  \n"
            + "4  |    |    |  \n"
            + "5       |    |  \n"
            + "6               \n"
            + "7       X    X  ", s.toString());
  }

  @Test
  public void testCombineSimultaneous2() {
    List<Integer> bList = new ArrayList<Integer>();
    bList.add(0);
    bList.add(0);
    bList.add(2);
    bList.add(1);
    bList.add(1);
    List<Integer> bList2 = new ArrayList<Integer>();
    bList2.add(2);
    bList2.add(0);
    bList2.add(2);
    bList2.add(1);
    bList2.add(1);
    bList2.add(1);
    bList2.add(0);
    bList2.add(2);
    Pitch p = new Pitch(bList, 1, 0);
    Pitch p2 = new Pitch(bList2, 2, 0);
    Pitch p3 = new Pitch(bList2, 3, 0);
    List<Pitch> pList = new ArrayList<Pitch>();
    List<Pitch> pList2 = new ArrayList<Pitch>();
    pList.add(p2);
    pList.add(p3);
    pList2.add(p);
    MusicModel s = new MusicModel(pList, 0);
    MusicModel s2 = new MusicModel(pList2, 0);
    s.combineMusicModelSimultaneous(s2);
    assertEquals("   C0  C#0   D0\n"
            + "0       X    X  \n"
            + "1               \n"
            + "2  X    X    X  \n"
            + "3  |    |    |  \n"
            + "4  |    |    |  \n"
            + "5       |    |  \n"
            + "6               \n"
            + "7       X    X  ", s.toString());
  }
}
