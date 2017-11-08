import java.io.IOException;

public final class BoardViewer {

  public static final int CELL_WIDTH = 5;

  private final Appendable out;

  /**
   * Constructs a new view with the given output channel.
   *
   * @param out where to render the view
   */
  public BoardView(Appendable out) {
    this.out = out;
  }

  /**
   * Renders the given board view-model to the output channel.
   *
   * @param bvm the state of the board to render
   * @throws IOException if there's an error writing to the output channel.
   */
  public void draw(BoardViewReadOnlyModel bvm)
      throws IOException {
    int columnCount = 0;

    for (int column : iterable(bvm::columns)) {
      ++columnCount;
    }

    for (int row : iterable(bvm::rows)) {
      hRule(columnCount);

      for (int column : iterable(bvm::columns)) {
        out.append('|');

        String text = "";

        if (bvm.isValidPosition(row, column)) {
          text = bvm.get(row, column, CELL_WIDTH);

          if (text.length() > CELL_WIDTH) {
            text = text.substring(0, CELL_WIDTH);
          }
        }

        int padding = CELL_WIDTH - text.length();
        repeat((padding + 1) / 2, ' ');
        out.append(text);
        repeat(padding / 2, ' ');
      }

      out.append("|\n");
    }

    hRule(columnCount);
  }

  private void hRule(int columnCount) throws IOException {
    for (int i = 0; i < columnCount; ++i) {
      out.append('+');
      repeat(CELL_WIDTH, '-');
    }

    out.append("+\n");
  }

  private void repeat(int count, char value) throws IOException {
    while (count-- > 0) {
      out.append(value);
    }
  }

  private static <T>
  Iterable<T> iterable(Iterable<T> iterable) {
    return iterable;
  }
}