import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.control.ButtonBar.ButtonData;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashSet;
import java.io.PrintWriter;


public class AddLanguageScreen extends Application {
    //member items
    Label nameLabel = new Label("Language Name:");
    TextField nameField = new TextField();
    String name;

    Label instructionLabel = new Label("Enter morphemes below, separated by spaces");

    Label ipaMorphLabel = new Label("Phonetic Morphemes:");
    TextField ipaMorphField = new TextField();
    String ipaMorphemes;

    Label pracMorphLabel = new Label("Practical Morphemes:"); 
    TextField pracMorphField = new TextField();
    String pracMorphemes;

    Button cancelButton = new Button("Cancel");
    Button addButton = new Button("Save Language");

    Label warningLabel = new Label();
    

    public static final void main(String[] args){
        launch(args);
    }

    //start method override
    @Override
    public void start(Stage primaryStage){
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        //temporary for test file
        File languagesDir = new File("Languages/");
        File[] languages = languagesDir.listFiles();
        HashSet<String> existingNames = new HashSet<>();
    
        for(File f: languages) {            
            String languageName = f.getName().substring(0,f.getName().length()-4);
            existingNames.add(languageName);
        }

        //read in name of language
        nameField.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                name = nameField.getText();
                if(existingNames.contains(name)) {
                    warningLabel.setText("Language with this name already exists!");
                    nameField.clear();
                } else {
                    warningLabel.setText("");
                }
            }
        } );
        //read in IPA morphemes and if they are separated by spaces, save them as a String
        ipaMorphField.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                ipaMorphemes = ipaMorphField.getText();
                if(ipaMorphemes.split(" ")[0].equals(ipaMorphemes)){
                    warningLabel.setText("Not correctly formatted! Separate each morpheme with spaces.");
                    ipaMorphField.clear();
                } else {
                    warningLabel.setText("");
                } 
            
            }
        } );
        //same process for morphemes written in practical orthography
        pracMorphField.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                pracMorphemes = pracMorphField.getText();
                if(pracMorphemes.split(" ")[0].equals(pracMorphemes)){
                    warningLabel.setText("Not correctly formatted! Separate each morpheme with spaces.");
                    pracMorphField.clear();
                } else {
                    warningLabel.setText("");
                } 
            
            }   
        } );

        HBox top = new HBox(5);
        top.setAlignment(Pos.CENTER);
        top.getChildren().addAll(nameLabel, nameField);
        top.setPadding(new Insets(0,0, 5, 0));
        
        root.setTop(top);


        VBox center = new VBox(5);
        center.getChildren().addAll(ipaMorphLabel, ipaMorphField, pracMorphLabel, pracMorphField);
        
        root.setCenter(center);

        //the cancel button closes the dialog
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //TODO close this dialog 
                }
            } );

        //the add language button takes the Strings from language name and uses them 
        //to write a new file in the languages folder
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //TODO
                String newLanguageName = "Languages/" + name + ".txt";
                File newLanguage = new File(newLanguageName);

        try {
            PrintWriter p = new PrintWriter(newLanguage);
            p.println(ipaMorphemes);
            p.println(pracMorphemes);
            p.close();
        } catch (FileNotFoundException e2) {
            System.err.print("Could not write file! This should never happen");
            System.exit(-2);
        }
                }
            } );

        StackPane bottom = new StackPane();
        bottom.setPadding(new Insets(7,0,0,0));

        HBox bottomRight = new HBox();
        bottomRight.setAlignment(Pos.CENTER_RIGHT);
        bottomRight.setPadding(new Insets(7, 0, 0, 0));
        bottomRight.getChildren().addAll(cancelButton, addButton);
        
        HBox bottomLeft = new HBox();
        bottomLeft.setAlignment(Pos.CENTER_LEFT);
        bottomLeft.setPadding(new Insets(7,0,0,0));
        bottomLeft.getChildren().add(warningLabel);

        
        bottom.getChildren().addAll(bottomRight, bottomLeft);
        root.setBottom(bottom);
        
        primaryStage.setTitle("Add New Language");
        primaryStage.setScene(new Scene(root, 600, 187));
        primaryStage.show();

    }


}