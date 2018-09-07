/**
 * 
 * @author Brennan Xavier McManus bm2530@columbia.edu
 *  
 * 
 * Depends on Orthographer.class, DescendingSorts.class, Morpheme.class
 * 
 * Contains public methods scan, replace, and write used for reading in text files, 
 * replacing their contents, and saving the resulting text respectively
 * 
 */
import java.util.Observable;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.stage.FileChooser;
import javafx.collections.*;
import javafx.collections.FXCollections.*;
import javafx.geometry.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Optional;
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
        try {
            File languagesDir = new File("Languages/");
            File[] languages = languagesDir.listFiles();
    
            for(File f: languages) {
                fl.add(f);
                String languageName = f.getName().substring(0,f.getName().length()-4);
                sl.add(languageName);
            } 
            sl.sort(null);
            fl.sort(null);
        } catch (NullPointerException e) {
            System.err.print("No Languages directory found!");
            System.exit(-1);
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
         int j, ArrayList<String> s1, ArrayList<String> s2) {
        data.clear();
        s1.clear();
        s2.clear();
        if( !l.isEmpty() ) {
            try{
                Scanner alphabetScanner = new Scanner(l.get(j));
                String[] ipa = alphabetScanner.nextLine().split(" ");
                String[] prac = alphabetScanner.nextLine().split(" ");
                Morpheme m;
                for(int i=0;i<ipa.length;i++){
                    m = new Morpheme(ipa[i],prac[i]);
                    data.add(m);
                    s1.add(ipa[i]);
                    s2.add(prac[i]);
                }

            } catch (FileNotFoundException e) {
                System.err.print("File not found!");
            }
        }
    }
    
    /**
     * Fills provided TextArea with text stored 
     * in provided Arraylist of Strings
     * @param TextArea t - TextArea to add content to
     * @param ArrayList<String> a - text to be added as content
     * overall program's current focused text
     */
    private static void loadText(TextArea t, ArrayList<String> a) {
        if( !a.isEmpty() ) {
            StringBuilder displayBuilder = new StringBuilder();
            for(int i=0;i<a.size();i++) {
                displayBuilder.append(a.get(i));
            }
            t.setText(displayBuilder.toString());
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
        convertBtn.setDisable(true);
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
                    convertBtn.setDisable(false);
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
                if( openFiles.isEmpty() )
                    convertBtn.setDisable(true);
            } });

        //clicking on the file list changes the displayed text in filePreviewDisplay
        fileListDisplay.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String old, String replacement) {
                    if( openFiles.size() != 0) {
                        ArrayList<String> inputText = Orthographer.scan(openFiles.get(fileListDisplay.getSelectionModel().getSelectedIndex()));
                        loadText(filePreviewDisplay, inputText);
                        currentText.clear();
                        for(int i=0;i<inputText.size();i++) 
                            currentText.add( inputText.get(i) );
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
            
            
            ArrayList<String> ipaList = new ArrayList<>();
            ArrayList<String> pracList = new ArrayList<>();
            String[] ipaMorphemes = null;
            String[] pracMorphemes = null;
            ObservableList<Morpheme> data = FXCollections.observableArrayList();
            loadData(data, languageList, 0, ipaList, pracList);
            ipaMorphemes = ipaList.toArray( new String[ipaList.size()] );
            pracMorphemes = pracList.toArray( new String[pracList.size()] );
    

            ipaCol.setCellValueFactory( new PropertyValueFactory<>("ipa")); //set data properties to columns
            pracCol.setCellValueFactory( new PropertyValueFactory<>("practical"));
            languagePreviewDisplay.setItems(data);
            languagePreviewPane.setCenter(languagePreviewDisplay);

            
            //Language Functionality
            
            //add language button creates new dialog pane 
            //dialog stage prevents parent stages from being used
            //until it is closed through the close button, cancel, 
            //or after sucessfully saving a language 
            addLanguageBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e){
                    //TODO will open new window for morpheme input
                    //AddLanguage GUI
                    final Stage dialogStage = new Stage();
                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                    dialogStage.initOwner(primaryStage);
                    
                    //stage root
                    BorderPane dialogPane = new BorderPane();
                    dialogPane.setPadding(new Insets(10, 10, 10, 10));

                    //stage nodes
                    VBox top = new VBox();
                    HBox nameBox = new HBox(5);
                    nameBox.setAlignment(Pos.CENTER);
                    nameBox.setPadding(new Insets(0,0, 5, 0));
                    dialogPane.setTop(top);

                    VBox center = new VBox(5);
                    dialogPane.setCenter(center);

                    VBox bottom = new VBox();
                    bottom.setPadding(new Insets(7,0,0,0));

                    HBox lowerBottom = new HBox();
                    lowerBottom.setAlignment(Pos.CENTER_RIGHT);
                    lowerBottom.setPadding(new Insets(7, 0, 0, 0));

                    HBox upperBottom = new HBox();
                    upperBottom.setAlignment(Pos.CENTER_LEFT);
                    upperBottom.setPadding(new Insets(7,0,0,0));

                    bottom.getChildren().addAll(upperBottom, lowerBottom);
                    dialogPane.setBottom(bottom);
                    
                    //stage leaf nodes
                    final Label nameLabel = new Label("Language Name:");
                    final TextField nameField = new TextField();
                    nameBox.getChildren().addAll(nameLabel, nameField);

                    final Label instructionLabel = new Label("Enter morphemes below, separated by spaces");
                    top.getChildren().addAll(instructionLabel, nameBox);

                    final Label ipaMorphLabel = new Label("Phonetic Morphemes:");
                    final TextField ipaMorphField = new TextField();
                    final Label pracMorphLabel = new Label("Practical Morphemes:"); 
                    final TextField pracMorphField = new TextField();
                    center.getChildren().addAll(ipaMorphLabel, ipaMorphField, pracMorphLabel, pracMorphField);

                    final Label warningLabel = new Label();
                    warningLabel.setWrapText(true);
                    upperBottom.getChildren().add(warningLabel);

                    final Button cancelButton = new Button("Cancel");
                    final Button addButton = new Button("Save Language");
                    lowerBottom.getChildren().addAll(cancelButton, addButton);

                    //AddLanguage Content
                    StringBuilder name = new StringBuilder();
                    StringBuilder ipaMorphemes = new StringBuilder();
                    StringBuilder pracMorphemes = new StringBuilder();

                    //AddLanguage Functionality
                    //read in name of language 
                    nameField.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e){
                            if( !name.toString().isEmpty() )
                                name.delete(0, name.length());
                            name.append( nameField.getText() );

                            if( languageNames.contains( name.toString() ) ) {
                                warningLabel.setText("Language with this name already exists!");
                                nameField.clear();
                                name.delete(0, name.length());
                            } else {
                                warningLabel.setText("");
                            }
                        } });
                    //read in IPA morphemes and if they are separated by spaces, save them as a String
                    ipaMorphField.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e){
                            if(!ipaMorphemes.toString().isEmpty())
                                ipaMorphemes.delete(0, ipaMorphemes.length());
                            ipaMorphemes.append( ipaMorphField.getText() );
                            String[] ipaformatCheck = ipaMorphemes.toString().split(" ");
                            if(ipaformatCheck.length > 0)
                                if(ipaformatCheck[0].equals( ipaMorphemes.toString() )){
                                    warningLabel.setText("Not correctly formatted! Separate each morpheme with spaces.");
                                    ipaMorphField.clear();
                                    ipaMorphemes.delete(0, ipaMorphemes.length());
                                } else {
                                    warningLabel.setText("");
                                } 
                        } });

                    //same process for morphemes written in practical orthography
                    //TODO premade methods so that enter does not need to be pressed 
                    //for language creation
                    pracMorphField.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e){
                            if( !pracMorphemes.toString().isEmpty() )
                                pracMorphemes.delete(0, pracMorphemes.length());
                            pracMorphemes.append( pracMorphField.getText() );
                            String[] pracformatCheck = pracMorphemes.toString().split(" ");
                            if(pracformatCheck.length > 0)
                                if(pracformatCheck[0].equals( pracMorphemes.toString() )){
                                    warningLabel.setText("Not correctly formatted! Separate each morpheme with spaces.");
                                    pracMorphField.clear();
                                    pracMorphemes.delete(0, pracMorphemes.length());
                                    } else {
                                        warningLabel.setText("");
                                    } 
                        } });

                    //the cancel button closes the dialog
                    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e){
                        dialogStage.close(); 
                        } });

                    //the add language button takes the Strings from language name and uses them 
                    //to write a new file in the languages folder
                    addButton.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e){
                            if( ipaMorphemes == null
                                || ipaMorphemes.toString().isEmpty() 
                                || pracMorphemes == null
                                || pracMorphemes.toString().isEmpty()
                                || name == null
                                || name.toString().isEmpty() )  {
                                warningLabel.setText("Could not save! name and two sets of morphemes required");
                                return;
                            } 

                            if( ipaMorphemes.toString().split(" ").length != pracMorphemes.toString().split(" ").length ) {
                                warningLabel.setText("Could not save! must have same number of morphemes in both lists");
                                return;
                            }

                            if( ipaMorphemes.toString().equals(pracMorphemes.toString() )) {
                                warningLabel.setText("Could not save! morpheme lists are identical");
                                return;
                            }
                            warningLabel.setText("Saving Language");
                            String newLanguageName = "Languages/" + name.toString() + ".txt";
                            File newLanguage = new File(newLanguageName);

                            try {
                                PrintWriter p = new PrintWriter(newLanguage);
                                p.println( ipaMorphemes.toString() );
                                p.println( pracMorphemes.toString() );
                                p.close();
                                warningLabel.setText("Language Saved Successfully!");
                                languageList.add(newLanguage);
                                languageList.sort(null);
                                languageNames.add( name.toString() );
                                languageNames.sort(null);

                                dialogStage.close();

                                Alert confirmation = new Alert(AlertType.INFORMATION);
                                confirmation.setTitle(null);
                                confirmation.setHeaderText(null);
                                confirmation.setContentText("Language Saved Sucessfully!");
                                confirmation.showAndWait();

                            } catch (FileNotFoundException e2) {
                                //warningLabel.setText("Could not write file! Please reopen the application and try agin");
                                System.err.print("Could not write file! This should never happen");
                                System.exit(-2);
                            }
                        } });

                    //AddLanguage Display
                    Scene dialogScene = new Scene(dialogPane, 500, 230);
                    dialogStage.setScene(dialogScene);
                    dialogStage.show();


                } });

            rmLanguageBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e){
                    //TODO will delete file associated with language from Languages/
                    //will cause a warning popUp
                    Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
                    deleteAlert.setTitle(null);
                    deleteAlert.setHeaderText(null);
                    deleteAlert.setContentText("Deletion cannot be undone, are you sure?");
                    
                    Optional<ButtonType> result = deleteAlert.showAndWait();
                    if( result.get() == ButtonType.OK) {
                        int removeIndex = languageListDisplay.getSelectionModel().getSelectedIndex();
                        File toDelete = languageList.get(removeIndex);
                        if( toDelete.delete() ) {
                            languageList.remove(removeIndex);
                            languageNames.remove(removeIndex);
                        }
                    } else{
                        return;
                    }
                } });  
            
            //clicking on different language name changes the preview 
            languageListDisplay.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> ov, String old, String replacement) {
                        int index = languageListDisplay.getSelectionModel().getSelectedIndex();
                        if(index < 0)
                            index = 0;
                        loadData(data, languageList, index, ipaList, pracList);
                    } });                 
//************************************************************************************************        

//Section 4: Conversion
//************************************************************************************************        

        //Conversion Functionality
            //no file selection from convert window, must select file first
            //displays file as is, with button to preview toIPA and fromIPA conversions
            //buttons to cancel, save changes + overrwite document, or save changes as new document
            //(opens savechooser windoww)
        convertBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                //Conversion Window GUI
                //no file selection from convert window, must select file first
                //displays file as is, with button to preview toIPA and fromIPA conversions
                //buttons to cancel, save changes + overrwite document, or save changes as new document

                //just in case I'm missing a corner case with button disabling
                if( openFiles.isEmpty() ) {
                    Alert fileNeeded = new Alert(AlertType.WARNING); 
                    fileNeeded.setTitle("File Required!");
                    fileNeeded.setHeaderText(null);
                    fileNeeded.setContentText("No files open, nothing to convert!");
                    fileNeeded.showAndWait();
                    return;
                }
                    
                
                final Stage convertStage = new Stage();
                convertStage.initModality(Modality.APPLICATION_MODAL);
                convertStage.initOwner(primaryStage);

                //Stage root
                BorderPane convertPane = new BorderPane();
                convertPane.setPadding(new Insets(10, 10, 10, 10));

                //Stage nodes
                HBox buttonBox = new HBox();
                buttonBox.setPadding(new Insets(10, 10, 0, 10));
                buttonBox.setAlignment(Pos.BASELINE_RIGHT);
                convertPane.setBottom(buttonBox);

                //Stage leaf nodes
                //Explanatory Label
                Label explainLabel = new Label("Click on \"To IPA\" or \"From IPA\" buttons to preview conversion, then click on one of the save buttons to save the changes shown in the preview or \"Cancel\" to cancel");
                explainLabel.setPadding(new Insets(0, 0, 10, 0));
                explainLabel.setWrapText(true);
                explainLabel.setAlignment(Pos.BASELINE_RIGHT);
                convertPane.setTop(explainLabel);

                //File Display(same as mainscreens asset here)
                TextArea changePreviewDisplay = new TextArea();
                changePreviewDisplay.setWrapText(true);
                changePreviewDisplay.setEditable(false);
                changePreviewDisplay.setFont(Font.font("Monaco", 10));
                convertPane.setCenter(changePreviewDisplay);
                loadText(changePreviewDisplay, currentText);


                //Buttons
                Button toIPAbtn = new Button("To IPA");
                Button fromIPAbtn = new Button("From IPA");
                Button saveAsNewBtn = new Button("Save as new Document");
                Button overrwiteFileBtn = new Button("Save to current Document");
                Button cancelBtn = new Button("Cancel");
                buttonBox.getChildren().addAll(toIPAbtn, fromIPAbtn, saveAsNewBtn, overrwiteFileBtn, cancelBtn);
           
                //Conversion Window Functionality
        
                //clear changePreviewDisplay
                //append each line from currentText to changePreviewDisplay
                //updates toIPA to record which conversion took plac
                toIPAbtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e){
                        Orthographer.replace(currentText, ipaList.toArray(new String[ipaList.size()]), pracList.toArray(new String[ pracList.size() ]));
                        loadText(changePreviewDisplay, currentText);
                    } });

                //clear changePreviewDisplay
                //append each line from currentText to changePreviewDisplay
                fromIPAbtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e){
                        Orthographer.replace(currentText, pracList.toArray(new String[ pracList.size() ]), ipaList.toArray(new String[ipaList.size()]) );
                        //Orthographer.replace(currentText, pracMorphemes, ipaMorphemes);
                        loadText(changePreviewDisplay, currentText);
                    } });

                saveAsNewBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e){
                        FileChooser saveChooser = new FileChooser();
                        saveChooser.setTitle("Save File");
                        File toSave = fileChooser.showSaveDialog(primaryStage);
                        if(toSave != null) {
                            Orthographer.write(currentText, toSave);
                            filePreviewDisplay.setText( changePreviewDisplay.getText() );
                            
                            convertStage.close();
                        }
                    } });  

                overrwiteFileBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e){
                    Alert saveConfirm = new Alert(AlertType.CONFIRMATION);
                    saveConfirm.setTitle(null);
                    saveConfirm.setHeaderText(null);
                    saveConfirm.setContentText("This will overwrite original document, do you want to proceeed?");
                    File currentFile = openFiles.get( fileListDisplay.getSelectionModel().getSelectedIndex() );


                    Optional<ButtonType> result = saveConfirm.showAndWait();
                    if(result.get() == ButtonType.OK)
                        Orthographer.write(currentText, currentFile);
                        filePreviewDisplay.setText( changePreviewDisplay.getText() );      

                        convertStage.close();

                        Alert confirmation = new Alert(AlertType.INFORMATION);
                        confirmation.setTitle(null);
                        confirmation.setHeaderText(null);
                        confirmation.setContentText("File Saved Sucessfully!");
                        confirmation.showAndWait();    
                    } });   
                
                cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e){
                        convertStage.close();
                    } });

                //Conversion Display
                //TODO hide main window during this process
        convertStage.setScene(new Scene(convertPane, 600, 600));
        convertStage.setTitle("Convert Document");
        convertStage.show();   
            } });
//************************************************************************************************        

//Section 5: Stage Finalization and Display
//************************************************************************************************        
        primaryStage.setScene(new Scene(root, 800, 500));
        Image icon = new Image(MainScreen.class.getResourceAsStream("icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();
//************************************************************************************************        
    }
}