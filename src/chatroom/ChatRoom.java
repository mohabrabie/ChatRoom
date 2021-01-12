/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 *
 * @author Mohab
 */
public class ChatRoom extends Application {
    TextArea chatBox;
    TextField msgBox,nameBox;
    Button sendMsg,sendName;
    Scene scene;
    Socket s;
    DataInputStream hear;
    PrintStream talk;
    String msg,name;
    BorderPane root;
    @Override
    public void init()
    {
        //chat scene
        chatBox = new TextArea();
        msgBox = new TextField();
        sendMsg = new Button("Send");
        sendMsg.setDefaultButton(true);
        chatBox.setPrefWidth(300);
        chatBox.setPrefHeight(300);
        msgBox.setPrefWidth(200);
        
        
        FlowPane flow = new FlowPane();
        flow.setVgap(10);
        flow.setHgap(55);
        flow.getChildren().add(msgBox);
        flow.getChildren().add(sendMsg);
        
        root = new BorderPane();

        root.setCenter(chatBox);
        root.setBottom(flow);
        //login theam
        nameBox = new TextField();
        sendName = new Button("Connect");
        sendName.setDefaultButton(true);
        
        Text nameLable = new Text("Enter you'r Name please");
        nameLable.setFont(Font.font ("Verdana", 18));
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setMinSize(400, 200);
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.add(nameLable, 0, 0);
        grid.add(nameBox, 0, 1);
        grid.add(sendName, 0, 2);
        
        
        scene = new Scene(grid, 300, 300);
        
    }
    @Override
    public void start(Stage primaryStage) {
        Stage window = primaryStage;
        sendMsg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                msg = msgBox.getText();
                msgBox.clear();
                talk.println(msg);
            }
        });
        sendName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    s = new Socket("127.0.0.1",5005);
                    hear = new DataInputStream(s.getInputStream());
                    talk = new PrintStream(s.getOutputStream());
                    name = nameBox.getText();
                    talk.println(name);
                    msg = hear.readLine();
                    chatBox.setText(msg);
                    scene = new Scene(root, 300, 250);
                    window.setScene(scene);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(msg);
                            try {
                                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                while(true)
                                {
                                    msg = hear.readLine();
                                    chatBox.appendText("\n");
                                    chatBox.appendText(msg);
                                    System.out.println(msg);
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }).start();
                } catch (IOException ex) {
                    //Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("sorry can't reach server");
                }
            }
        });
        
        
        primaryStage.setTitle("Chat Room");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
