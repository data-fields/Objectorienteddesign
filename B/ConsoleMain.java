import java.io.ByteArrayInputStream;
import java.io.InputStream;



public class ConsoleMain {

  static NetworkClientTester.GameRunner gameRunner = (InputStream input, Appendable output) -> {
    Model model = new Board();
    BoardView view = new BoardView(output);
    Controller controller = new GameController(model, view, output, input);
    controller.reset();
    while (!controller.gameOver()) {
      controller.step();
    }
  };



  public static void main(String[] args) throws Exception {
    gameRunner.runGame(System.in, System.out);


  }
}