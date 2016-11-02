package cs3500.music.view;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.*;

/**
 * represents the view of a musical editor through sound of the song
 */
public abstract class Midi implements ViewInterface {
  private final Synthesizer synth;
  private final Receiver receiver;
  private MusicModel model;

  public Midi() {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      throw new IllegalStateException("Cannot start Midi device");
    }
  }



}