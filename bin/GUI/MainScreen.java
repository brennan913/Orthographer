import java.rmi.server.UID;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.options.Options;
import javafx.geometry.*;

public class MainScreen extends Application {
    
    //driver method
    public static final void main(String[] args){
        launch(args);
    }
    
    //start method override
    @Override
    public void start(Stage primaryStage){

        //Pimrary stage layout grid and subordinate layout grids
        primaryStage.setTitle("Orthographer");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setGridLinesVisible(true);

            GridPane column0grid = new GridPane();
            column0grid.setVgap(10);
            column0grid.setHgap(10);
            column0grid.setPadding(new Insets(10, 10, 10, 10));
            column0grid.setAlignment(Pos.CENTER_LEFT);

            GridPane column2grid = new GridPane();
            column2grid.setVgap(10);
            column2grid.setHgap(10);
            column2grid.setPadding(new Insets(10, 10, 10, 10));
            column2grid.setAlignment(Pos.CENTER_RIGHT);


            grid.add(column0grid,0,0);

        
        //set grid constraints
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPercentWidth(20);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(80);
        grid.getColumnConstraints().addAll(column0, column1);

        //column 0 row constraints
        RowConstraints column0row0 = new RowConstraints();
        column0row0.setPercentHeight(25);
        RowConstraints column0row1 = new RowConstraints();
        column0row1.setPercentHeight(25);
        RowConstraints column0row2 = new RowConstraints();
        column0row2.setPercentHeight(50);

        column0grid.getRowConstraints().addAll(column0row0, column0row1, column0row2);


        //column 1 row constraints
        RowConstraints column1row0 = new RowConstraints();
        column1row0.setPercentHeight(100);
        grid.getRowConstraints().addAll(column1row0); 


        //filePane and member elements
        //to be placed in column 0 row 0 
        //should take up ~25% of height
        BorderPane fileListPane = new BorderPane();
            Label fileListLabel = new Label("Open Files");
            ListView fileListDisplay = new ListView();

        fileListPane.setTop(fileListLabel);
        fileListPane.setCenter(fileListDisplay);

        column0grid.add(fileListPane,0,0);

        
        //languageListPane and member elements
        //to be placed in column 0 row 1 
        //should take up ~25% of height
        BorderPane languageListPane = new BorderPane();
            Label languageListLabel = new Label("Languages");
            ListView languageListDisplay = new ListView();
        
        languageListPane.setTop(languageListLabel);
        languageListPane.setCenter(languageListDisplay);    
        
        column0grid.add(languageListPane,0,1);
        
        //alphabetPreviewPane and member elements
        //to be placed in column 0 row 2 
        //should take up ~50% of height
        BorderPane alphabetPreviewPane = new BorderPane();
            Label alphabetPreviewLabel = new Label("Language Preview");
            Text alphabetPreviewDisplay = new Text();
        
        alphabetPreviewPane.setTop(alphabetPreviewLabel);
        alphabetPreviewPane.setCenter(alphabetPreviewDisplay);

        column0grid.add(alphabetPreviewPane,0,2);

        //filePreviewPane and member elements
        //to be placed in column 1 row 0
        //should take up 100% of height 
        BorderPane filePreviewPane = new BorderPane();
            
                TextArea filePreviewDisplay = new TextArea("This will be a ton of text someday \nwon't it just");
                filePreviewDisplay.setWrapText(true);
                
                HBox OptionsPane = new HBox();
            
                    Button addLanguageBtn = new Button("Add Language");
                    Button rmLanguageBtn = new Button("Remove Language");
            
                    Button openFileBtn = new Button("Open File");
                    Button closeSelectedFileBtn = new Button("Close File");
    
            OptionsPane.getChildren().addAll(addLanguageBtn, rmLanguageBtn, openFileBtn, closeSelectedFileBtn);
        
        filePreviewPane.setCenter(filePreviewDisplay);
        //filePreviewPane.setTop(OptionsPane);

        grid.add(filePreviewPane,1,0);

        //languageOptionsPane and member elements
        //to be placed in column 2 row 0 
        //should take up 40% of height


        primaryStage.setScene(new Scene(grid, 800, 500));
        primaryStage.getScene().getStylesheets().setAll(".root{-fx-background-radius: 30}");
        primaryStage.show();
    }

    //nested EventListener class
    private class MainHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

        }
    }    
}