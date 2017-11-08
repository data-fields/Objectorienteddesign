import static org.junit.Assert.*;

import org.junit.Test;

public class StrictCoinGameModelTest {

  @Test
  public void getWinner() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("-O---");
    strictGame.addPlayer();
    strictGame.addPlayer();
    strictGame.move(0, 0);
    assertEquals(0, strictGame.getWinner());
  }

  @Test
  public void turnCount() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("----O");
    strictGame.addPlayer();
    strictGame.move(0, 3);
    assertEquals(strictGame.turnCount(), 1);
  }

  @Test
  public void addPlayer() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("----O", 3);
    strictGame.addPlayer();
  }

  @Test
  public void numberOfPlayers() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("----O", 3);
    assertEquals(strictGame.playerCount(), 3);
  }

  @Test
  public void nextPlayer() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("----O");
    strictGame.addPlayer();
    strictGame.addPlayer();
    assertEquals(strictGame.whosTurn(), 0);
    strictGame.move(0, 3);
    assertEquals(strictGame.whosTurn(), 1);
    strictGame.move(0, 2);
    assertEquals(strictGame.whosTurn(), 0);
  }

  @Test(expected = IllegalStateException.class)
     public void noWinner() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("----O");
    strictGame.getWinner();
  }


  @Test(expected = IllegalStateException.class)
  public void noPlayers() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("----O");
    strictGame.move(0, 0);
  }

  @Test
  public void tenPlayers() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("----O", 10);
    strictGame.move(0,1);
    assertEquals(strictGame.playerCount(), 10);
  }

  @Test
  public void addPlayerInGame() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("---------O", 2);
    strictGame.move(0, 8);
    strictGame.addPlayer();
    strictGame.move(0,7);
    strictGame.move(0, 6);
    strictGame.addPlayer(0);
    assertEquals(strictGame.whosTurn(), 0);
    strictGame.move(0, 5);
    assertEquals(strictGame.whosTurn(), 1);
    strictGame.move(0, 4);
    strictGame.move(0, 3);
    assertEquals(strictGame.whosTurn(), 3);
  }

  @Test
  public void addPlayerInFront() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("---------O", 1);
    strictGame.move(0, 8);
    strictGame.addPlayer(0);
    assertEquals(strictGame.whosTurn(), 0);
    strictGame.addPlayer();
    strictGame.move(0, 7);
    assertEquals(strictGame.whosTurn(), 2);
    strictGame.move(0, 6);
    assertEquals(strictGame.whosTurn(), 1);
  }

  @Test(expected = IllegalStateException.class)
  public void moveNoPlayers() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("----O");
    strictGame.move(0, 0);
  }

  @Test
  public void moveSuccessful() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("----O");
    strictGame.addPlayer();
    strictGame.move(0, 0);
  }

  @Test
  public void checkPlayerList() {
    StrictCoinGameModel s = new StrictCoinGameModel("----O");
    s.addPlayer();
    s.addPlayer();
    s.addPlayer();
    s.move(0, 3);
    s.addPlayer(0);
    assertEquals(s.playerOrder(), "3 0 |1| 2");
  }

  @Test
  public void addNextToLast() {
    StrictCoinGameModel strictGame = new StrictCoinGameModel("----O", 3);
    strictGame.move(0, 3);
    strictGame.move(0, 2);
    strictGame.addPlayer(3);
    assertEquals(strictGame.whosTurn(), 2);
  }

  // make a function called print player order with | | around whos turn it is

  @Test
  public void testScenario1_1() {
    String board = CoinGameTestScenarios.scenario1("-OOOO");
    assertEquals(board, "OOOO-");
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testScenario1_2() {
    String board = CoinGameTestScenarios.scenario1("O-OOO-");
  }


  @Test
  public void testScenario2() {
    String board = CoinGameTestScenarios.scenario2("O--O-O");
    assertEquals(board, "OO--O-");
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testScenario2_2() {
    String board = CoinGameTestScenarios.scenario2("-O-O-O");
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testScenario3() {
    String board = CoinGameTestScenarios.scenario3("-OOOOOO-");
    assertEquals(board, "OOOO--");
  }

  @Test
  public void testScenario4() {
    String board = CoinGameTestScenarios.scenario4("OOO--O");
    assertEquals(board, "OOO-O-");
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testScenario4_2() {
    String board = CoinGameTestScenarios.scenario4("-O-O-OO");
  }

  @Test
  public void testScenario5() {
    boolean gameOver = CoinGameTestScenarios.scenario5("O------O");
    assertEquals(gameOver, true);
  }

  @Test(expected = CoinGameModel.IllegalMoveException.class)
  public void testScenario5_2() {
    boolean gameOver = CoinGameTestScenarios.scenario5("-O-O-OO");
  }
}