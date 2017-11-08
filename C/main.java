import java.util.Hashtable;

/**
 * Created by Kedryn on 3/14/2015.
 */
public class main {

  public static void main(String[] args) {
    StrictCoinGameModel s = new StrictCoinGameModel("----O");
    s.addPlayer();
    s.addPlayer();
    s.addPlayer();
    s.move(0, 3);
    s.addPlayer(0);
    System.out.println(s.playerOrder());
  }

}
