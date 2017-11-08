

import java.util.Iterator;




/**
 * A minimal interface for communicating to the board view what it should render. The view (which I
 * will provide) will have a method {@code void draw(ReadOnlyBoardViewModel)} that takes an instance
 * of this interface and renders it.
 *
 * <p> This interface describes a view model--that is, a model of the state of the view that is more
 * abstract than the view itself. A view model helps decouple the controller (or model), which would
 * create instances of this interface, from the view, in which the lower-level details of rendering
 * are thus isolated.
 */


public interface ReadOnlyBoardViewModel {

 
 /**
   * Returns the text to display in the board square for the given row and column. The third
   * parameter, {@code width}, specifies how wide the field is where the text will be displayed, in
   * characters. This parameter is merely a hint to the view-model, and implementations of this
   * interface are free to ignore it. If the text is shorter than {@code width} then the view will
   * center it in the field, and if the text is longer then the view will truncate it.
   *
   * @param row    the row
   * @param column the column
   * @param width  the width available for printing
   * @return the string to print at row and column (non-null)
   * @throws IndexOutOfBoundsException if {@code !isValidPosition(row, column)}
   */
  String get(int row, int column, int width);

  /**
   * Determines whether a row and column represent a valid board position.
   *
   * @param row    the row
   * @param column the column
   * @return whether the represented position is valid
   */
 

 boolean isValidPosition(int row, int column);

  /**
   * Returns an iterator over the rows of the board from top-to-bottom in rendering order. While the
   * number of rows is fixed by the rules of the game, flexibility may still be useful here, perhaps
   * for testing with smaller boards or for coordinate transformations.
   *
   * @return an iterator over the rows (non-null)
   */
  Iterator<Integer> rows();

  /**
   * Returns an iterator over the columns of the board from left-to-right in rendering order.
   *
   * @return an iterator over the columns (non-null)
   */
  Iterator<Integer> columns();
}

