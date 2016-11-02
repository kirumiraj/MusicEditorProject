package cs3500.music.view;

import cs3500.music.model.*;

/**
 * Factory object for constructing various types of views for the given model
 */
public final class ViewBuilder {
  public static ViewInterface run(String type, IMusicModel model) {
    switch (type.toLowerCase()) {
      case "console": return new Console(model);
      //case "visual":  return new Gui(model);
      //case "midi":    return new Midi(model);
      default:        throw new IllegalArgumentException("\"" + type + "\" is not a supported "
              + "view type");
    }
  }
}