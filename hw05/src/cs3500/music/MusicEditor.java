package cs3500.music;

import cs3500.music.model.MusicModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.model.IMusicModel;
import cs3500.music.view.ViewInterface;
import cs3500.music.view.ViewBuilder;

import java.io.FileReader;

public final class MusicEditor {
  public static void main(String[] args) {
    String filename = "";
    String mode = "";
    if (args.length >= 2) {
      filename = args[0].substring(1, args[0].length() - 1);
      mode = args[1].substring(0, args[1].length() - 1);
    }
    else if (args.length == 1) {
      filename = args[0];
    }

    try {
      CompositionBuilder<IMusicModel> builder = new MusicModel.Builder();
      IMusicModel model = MusicReader.parseFile(new FileReader(filename), builder);
      ViewInterface view = ViewBuilder.run(mode, model);
      view.run();
    }
    catch (Exception e) {
      System.err.println("Unable to open and run " + filename + ". Error message "
              + "is: " + e.getMessage());
    }
  }
}