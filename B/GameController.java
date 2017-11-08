import java.io.InputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class GameController implements Controller {

  Model model;
  BoardView view;
  private final Appendable out;
  private final InputStream in;

  public GameController(Model model, BoardView view, Appendable out, InputStream in) {
    this.view = view;
    this.model = model;
    this.out = out;
    this.in = in;
  }

  @Override
  public boolean gameOver() {
    return model.gameOver();
  }

  @Override
  public void run() throws IOException {
    while (!gameOver()) {
      move();
      if (gameOver()) {
        view.draw(model);
        if (model.getWinner() == Player.First) {
          out.append("Player 1 Wins!!!").append("\n");
        } else {
          out.append("Player 2 Wins!!!").append("\n");
        }
      }
    }
  }

  public void step() throws IOException {
    if (!gameOver()) {
      move();
      if (gameOver()) {
        view.draw(model);
        if (model.getWinner() == Player.First) {
          out.append("Player 1 Wins!!!").append("\n");
        } else {
          out.append("Player 2 Wins!!!").append("\n");
        }
      }
    }
  }

  @Override
  public void move() throws IOException {
    int pieces = model.setDisplayPieces();
    view.draw(model);
    int
        what =
        getInput("[" + model.getTurn().toChar() + "]" + " Choose a piece to move: ", pieces, in,
                 out) - 1;
    int moves = model.setDisplayMoves(what);
    view.draw(model);
    int
        where =
        getInput("[" + model.getTurn().toChar() + "]" + " Choose where to move to: ", moves, in,
                 out) - 1;
    model.move(what, where);
    model.flip();
  }

  /**
   * retrieves input from user
   * @param prompt the prompt to give the user
   * @param options the options that the user has
   * @param input the input stream
   * @param out the output stream
   * @return the selected input
   * @throws IOException
   */


  private Integer getInput(String prompt, int options, InputStream input, Appendable out)
      throws IOException {
    Scanner in = new Scanner(input);
    int where = 0;
    while (where <= 0 || where > options) {
      out.append(prompt);
      try {
        where = in.nextInt();
      } catch (InputMismatchException _e) {
        out.append("That isn't a number! Please try again.\n");
        in.nextLine();
        where = 0;
        continue;
      } catch (NoSuchElementException e) {
        throw new IntReader.NoNextIntException(e);
      }
      if (where <= 0 || where > options) {
        out.append("Not a valid choice. Choose a number between 1 and " + options + ".\n");
      }
    }
    return where;
  }

  @Override
  public void reset() {
    Checker.reset();
  }
}