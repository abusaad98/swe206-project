package application;

import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TrackComp {

	Scene TrackCompScene;
	boolean isTeam = false;
	
	public TrackComp(){
		
		TextField compName = new TextField();
		compName.setPromptText("Enter the name of the compitition");
		TextField compLink = new TextField();
		compLink.setPromptText("Enter the link of the compitition");
		TextField compDate = new TextField();
		compDate.setPromptText("Enter the date of the compitition");

		Label label1 = new Label("Name: ");
		Label label2 = new Label("Link: ");
		Label label3 = new Label("Date: ");
		
		ToggleGroup tg = new ToggleGroup();
		RadioButton isTeamRadio = new RadioButton("Team based");
		RadioButton isStudentRadio = new RadioButton("Student based");
		isTeamRadio.setToggleGroup(tg);
        isStudentRadio.setToggleGroup(tg);
        
        tg.selectedToggleProperty().addListener(
        		   (observable, oldToggle, newToggle) -> {
        		       if (newToggle == isTeamRadio) 
        		           isTeam = true;
        		       else
        		    	   isTeam = false;
        		    }
        		);

		Button submet = new Button("submit");
		Button back = new Button("Go Back ");
		Label trackLabel = new Label("adding a compitition");
		trackLabel.setFont(new Font("Arial", 32));
		Label errorLabel = new Label("");
		errorLabel.setStyle("-fx-font-weight: bold");
		errorLabel.setTextFill(Color.RED);
		errorLabel.setFont(new Font("Arial", 32));

		submet.setOnAction(e -> {

			if (compName.getText() != "" && compLink.getText() != "" && compDate.getText() != "" && (isTeamRadio.isSelected() || isStudentRadio.isSelected())) {
				FileManager comp = new FileManager(compName.getText());
				try {
					if(!comp.isSheetExist()) {
						comp.writeComp(compLink.getText(), compDate.getText(), isTeam);
						errorLabel.setText("");
						compName.clear();
						compLink.clear();
						compDate.clear();
						isTeamRadio.setSelected(false);
						isStudentRadio.setSelected(false);
					}
					else {
						errorLabel.setText("This compitition is already tracked!");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}

			else {
				errorLabel.setText("You need to fill all the fields in the right format to submit!");
			}

		});

		TilePane radioPane = new TilePane();
		radioPane.setPadding(new Insets(50, 50, 50, 50));
		radioPane.setAlignment(Pos.CENTER);
		radioPane.getChildren().addAll(isTeamRadio, isStudentRadio);
		
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(15, 15, 15, 15));
		vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.getChildren().addAll(label1, compName, label2, compLink, label3, compDate, radioPane);

		VBox twoLabels = new VBox();
		twoLabels.setSpacing(10);
		twoLabels.setPadding(new Insets(15, 15, 15, 15));
		twoLabels.setAlignment(Pos.CENTER);
		twoLabels.getChildren().addAll(trackLabel, errorLabel);

		StackPane trackPane = new StackPane();
		trackPane.setPadding(new Insets(50, 50, 50, 50));
		trackPane.setAlignment(Pos.CENTER);
		trackPane.getChildren().add(twoLabels);

		HBox buttonPane = new HBox();
		buttonPane.setSpacing(10);
		buttonPane.setPadding(new Insets(50, 50, 50, 50));
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.getChildren().addAll(back, submet);

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(trackPane);
		borderPane.setCenter(vbox);
		borderPane.setBottom(buttonPane);

		TrackCompScene = new Scene(borderPane, 800, 700);
		
		back.setOnAction(e -> {
			ProjectMain.mainstage.setScene(ProjectMain.mainscene);
		});
		
	}
	
	public Scene getTrackCompScene() {
		return TrackCompScene;
	}
	
}
