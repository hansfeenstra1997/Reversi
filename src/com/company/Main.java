package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {

    private GameFactory gameFactory = new GameFactory();
    Controller gameController;

    //feur game is shitty
    Stage gameStage = new Stage();
    BorderPane gamePane = new BorderPane();
    VBox gameMain = new VBox();
    GridPane gameControlPane = new GridPane();

    static String playerName;
    static int playerMode = 1;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        mainPane.setMinSize(200, 200);
        VBox main = new VBox();
        GridPane controlPane = new GridPane();

        Button startBtn = new Button("Start Tic-Tac-Toe");
        startBtn.setOnAction((event) -> startGame("Tic-tac-toe", 0));
        controlPane.add(startBtn, 0, 0);

        Button startReversiBtn = new Button("Start Reversi");
        startReversiBtn.setOnAction((event) -> startGame("Reversi", 0));
        controlPane.add(startReversiBtn, 0, 1);

        TextField usernameField = new TextField();
        controlPane.add(usernameField, 0, 2);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction((event) -> login(usernameField.getText()));
        controlPane.add(loginBtn, 1, 2);

        TextField commandField = new TextField();
        controlPane.add(commandField,0, 3);
        Button commandButton = new Button("Send");
        commandButton.setOnAction((event) -> {sendCommand(commandField.getText()); commandField.setText("");});
        controlPane.add(commandButton,1,3);

        main.getChildren().add(controlPane);

        mainPane.setCenter(main);

        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void sendCommand(String command) {
        Connection.getInstance().sendCommand(command);
    }

    private void login(String username) {
        playerName = username;
        Connection.getInstance().sendCommand("login " + username);
    }

    private void startGame(String gameName, int gameMode) {
        Connection.getInstance().sendCommand("subscribe " + gameName);
        gameController = gameFactory.makeGame(gameName, gameStage);

        makeScene(gameController.board);

        Thread thread = new Thread(gameController);
        thread.start();
    }

    public void makeScene(Board board){
        gamePane.setMinSize(200, 200);
        //gameController.updateBoard();
        updateScene(board);
        gameController.setupFX();
    }

    public void updateScene(Board board){

        gameControlPane.getChildren().clear();

        for(int x = 0; x < board.getSize(); x++){
            for(int y = 0; y < board.getSize(); y++){
                Button button = new Button();
                int state = board.getBoard()[x][y].getState();
                button.setText(Integer.toString(state));
                button.setOnAction((event) -> {
                    int position = gameControlPane.getChildren().indexOf(event.getSource());
                    gameController.setMove(position);
                    System.out.println(position);
                });

                button.setMinSize(50,50);
                gameControlPane.add(button, y, x);
            }
        }

        gameMain.getChildren().add(gameControlPane);
        Text turn = new Text("test");
        turn.setId("turnText");
        gamePane.setCenter(gameMain);
        gamePane.setBottom(turn);

        Scene scene = new Scene(gamePane);
        gameStage.setScene(scene);
        gameStage.show();
    }

}


