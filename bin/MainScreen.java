//TODO: refactor, organize source code by function and not placement on screen
import java.util.Observable;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.collections.*;
import javafx.collections.FXCollections.*;
import javafx.geometry.*;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import javafx.scene.control.cell.*;

public class MainScreen extends Application {
    //static methods
    /**
     * Loads languages from Languages/ directory
     * stores their names in the provided list of Strings
     * stores the files in provided list of Files 
     * @param ObservableList<String> sl list of language names
     * @param ObservableList<File> fl list of language files
     */
    private static void loadLanguages(ObservableList<String> sl, ObservableList<File> fl){
        File languagesDir = new File("Languages/");
        File[] languages = languagesDir.listFiles();
    
        for(File f: languages) {
            fl.add(f);
            String languageName = f.getName().substring(0,f.getName().length()-4);
            sl.add(languageName);
        } 
    }

    /**
     * Fills provided Observable list with each morpheme from language file 
     * stored at provided index in provided Observable list of Files 
     * @param ObservableList<Morpheme> data list to be filled from selected file
     * @param ObservableList<File> l list of loaded language files
     * @param int j index at which language is stored
     * @param String[] s1 to be filled with first line from selected file
     * @param String[] s2 to be filled with second line from selected file
     */
    private static void loadData(ObservableList<Morpheme> data, ObservableList<File> l,
         int j, String[] s1, String[] s2) {

        if( !l.isEmpty() ) {
            try{
                Scanner alphabetScanner = new Scanner(l.get(j));
                s1 = alphabetScanner.nextLine().split(" ");
                s2 = alphabetScanner.nextLine().split(" ");
                Morpheme m;
                for(int i=0;i<s1.length;i++){
                    m = new Morpheme(s1[i],s2[i]);
                    data.add(m);
                }
                
            } catch (FileNotFoundException e) {
                System.err.print("File not found!");
            }
        }
    }

    //driver method
    public static final void main(String[] args){
        launch(args);
    }
    
    //start method override
    @Override
    public void start(Stage primaryStage){

//Section 1: General Layout
//************************************************************************************************        
        //Pimrary stage layout, central grid and subordinate layout grid
        primaryStage.setTitle("Orthographer");
        BorderPane root = new BorderPane();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        root.setCenter(grid);

        //lay out smaller panels on left side
        GridPane column0grid = new GridPane();
        column0grid.setVgap(10);
        column0grid.setHgap(10);
        column0grid.setPadding(new Insets(0, 10, 0, 10));
        column0grid.setAlignment(Pos.CENTER_LEFT);

        grid.add(column0grid,0,0);

        
        //set grid column constraints
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

        //left side GUI members
        BorderPane fileListPane = new BorderPane();
        column0grid.add(fileListPane,0,0);
        BorderPane languageListPane = new BorderPane();
        column0grid.add(languageListPane,0,1);
        BorderPane languagePreviewPane = new BorderPane();
        column0grid.add(languagePreviewPane,0,2);

        //right side GUI members
        BorderPane filePreviewPane = new BorderPane();
        grid.add(filePreviewPane,1,0);

        HBox optionsPane = new HBox();
        optionsPane.setPadding(new Insets(0, 10, 10, 10));
        optionsPane.setAlignment(Pos.CENTER_RIGHT);
        Button addLanguageBtn = new Button("Add Language");
        Button rmLanguageBtn = new Button("Remove Language");
        Button openFileBtn = new Button("Open File");
        Button closeFileBtn = new Button("Close File");
        Button convertBtn = new Button("Convert Document");
        optionsPane.getChildren().addAll(addLanguageBtn, rmLanguageBtn, openFileBtn, closeFileBtn, convertBtn);
        root.setBottom(optionsPane);
//************************************************************************************************        

//Section 2: Files 
//************************************************************************************************        
        //File GUI 
        Label fileListLabel = new Label("Open Files");
        fileListPane.setTop(fileListLabel);

        ListView<String> fileListDisplay = new ListView<>();
        fileListPane.setCenter(fileListDisplay);

        Label filePreviewLabel = new Label("File Preview");
        filePreviewPane.setTop(filePreviewLabel);

        TextArea filePreviewDisplay = new TextArea();
        filePreviewDisplay.setWrapText(true);
        filePreviewDisplay.setEditable(false);
        filePreviewDisplay.setFont(Font.font("Monaco", 10));
        filePreviewPane.setCenter(filePreviewDisplay);


        //File Content
        ObservableList<File> openFiles = FXCollections.observableArrayList();
        ObservableList<String> fileNames = FXCollections.observableArrayList();
        fileListDisplay.setItems(fileNames);
        ArrayList<String> currentText = new ArrayList<>();


        //File Functionality
        //clicking open file adds a file to the openFiles list
        //and adds its filename to the displayed list of files, fileNames
        FileChooser fileChooser = new FileChooser();
        openFileBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                File file = fileChooser.showOpenDialog(primaryStage);
                if(file != null) {
                    fileNames.add( file.getName() );
                    openFiles.add(file);
                }
            }
        } );

        //clicking close file removes file and filename from openFiles and fileNames
        //if it is pressed with one file remaining, the preview display is cleared
        closeFileBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                if( openFiles.size() < 2) {
                    openFiles.clear();
                    fileNames.clear();
                    filePreviewDisplay.clear();
                } else{
                    openFiles.remove(fileListDisplay.getSelectionModel().getSelectedIndex());
                    fileNames.remove(fileListDisplay.getSelectionModel().getSelectedIndex());
                }
            } });

        //clicking on the file list changes the displayed text in filePreviewDisplay
        fileListDisplay.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String old, String replacement) {
                    if( openFiles.size() != 0) {
                        ArrayList<String> selectedFile = Orthographer.scan(openFiles.get(fileListDisplay.getSelectionModel().getSelectedIndex()));
                        if( !selectedFile.isEmpty() ) {
                            StringBuilder displayBuilder = new StringBuilder();
                            currentText.clear();
                            for(int i=0;i<selectedFile.size();i++) {
                                currentText.add(selectedFile.get(i));
                                displayBuilder.append(selectedFile.get(i));
                            }
                            filePreviewDisplay.setText(displayBuilder.toString());
                        }
                    }
                } });
//*************************************************************************************************        

//Section 3: Languages 
//************************************************************************************************        
            //Language GUI
            Label languageListLabel = new Label("Languages");
            languageListPane.setTop(languageListLabel);
            
            ListView<String> languageListDisplay = new ListView<>();
            languageListPane.setCenter(languageListDisplay);    

            Label languagePreviewLabel = new Label("Language Preview");
            languagePreviewPane.setTop(languagePreviewLabel);

            TableView<Morpheme> languagePreviewDisplay = new TableView<>();
            TableColumn<Morpheme, String> ipaCol = new TableColumn<>("   IPA  ");
            TableColumn<Morpheme, String> pracCol = new TableColumn<>("Practical");
            languagePreviewDisplay.getColumns().add(ipaCol);
            languagePreviewDisplay.getColumns().add(pracCol);
            languagePreviewDisplay.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


            //Language Content
            ObservableList<String> languageNames = FXCollections.observableArrayList();
            ObservableList<File> languageList = FXCollections.observableArrayList();
            loadLanguages(languageNames, languageList);
            languageListDisplay.setItems(languageNames);
            
            String[] ipaMorphemes = null;
            String[] pracMorphemes = null;
            ObservableList<Morpheme> data = FXCollections.observableArrayList();
            loadData(data, languageList, 0, ipaMorphemes, pracMorphemes);
            ipaCol.setCellValueFactory( new PropertyValueFactory<>("ipa")); //set data properties to columns
            pracCol.setCellValueFactory( new PropertyValueFactory<>("practical"));
            languagePreviewDisplay.setItems(data);
            languagePreviewPane.setCenter(languagePreviewDisplay);

            
            //Language Functionality
            addLanguageBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e){
                    //TODO will open new window for morpheme input
                } });

            rmLanguageBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e){
                    //TODO will delete file associated with language from Languages/
                    //will cause a warning popUp
                } });  

            languageListDisplay.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> ov, String old, String replacement) {
                        // TODO change selected language for conversions and update preview
                        int index = languageListDisplay.getSelectionModel().getSelectedIndex();
                        loadData(data, languageList, index, ipaMorphemes, pracMorphemes);
                    } });                 
//************************************************************************************************        

//Section 4: Conversion
//************************************************************************************************        
        //Conversion GUI
            //no file selection from convert window, must select file first
            //displays file as is, with button to preview toIPA and fromIPA conversions
            //buttons to cancel, save changes + overrwite document, or save changes as new document
            //(opens savechooser windoww)
        //Conversion Content
        //Conversion Functionality
        convertBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //TODO
            } });
//************************************************************************************************        

//Section 5: Stage Finalization and Display
//************************************************************************************************        
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.getIcons().add(new Image(MainScreen.class.getResourceAsStream("icon.png")));
        primaryStage.show();
//************************************************************************************************        
    }
}