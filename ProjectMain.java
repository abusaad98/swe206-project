package application;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ProjectMain extends Application {

	public static Stage mainstage;
	public static Scene mainscene;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Competition System");
		mainstage = primaryStage;
		
		HBox options = new HBox();
		HBox options1 = new HBox();
		HBox options2 = new HBox();
		HBox options3 = new HBox();
		
		Button add = new Button("Add a team/a student");
		Button showComp = new Button("Show all competitions");
		Button trackComp = new Button("Track a new competition");
		Button sendEmail = new Button("Send Email to a Participants");
		
		options.getChildren().addAll(add);
		options1.getChildren().addAll(showComp);
		options2.getChildren().addAll(trackComp);
		options3.getChildren().add(sendEmail);
		
		options.setPadding(new Insets(30));
		options.setSpacing(30);
		options.setAlignment(Pos.CENTER);
		options1.setPadding(new Insets(30));
		options1.setSpacing(30);
		options1.setAlignment(Pos.CENTER);		
		options2.setPadding(new Insets(30));
		options2.setSpacing(30);
		options2.setAlignment(Pos.CENTER);
		options3.setPadding(new Insets(30));
		options3.setSpacing(30);
		options3.setAlignment(Pos.CENTER);
		
		VBox optionsVbox1 = new VBox(options,options1,options2,options3);
		
		Text title = new Text("Competition System");
		title.setFont(new Font(30));
		title.setTextAlignment(TextAlignment.CENTER);
		title.setWrappingWidth(280);

		BorderPane border = new BorderPane();
		VBox optionsVbox = new VBox(title, optionsVbox1);
		optionsVbox.setAlignment(Pos.CENTER);
		border.setPadding(new Insets(10));
		border.setCenter(optionsVbox);
		mainscene = new Scene(border, 450, 450);
		primaryStage.setScene(mainscene);
		primaryStage.setResizable(true);
		primaryStage.show();
		
		trackComp.setOnAction(e -> {
			primaryStage.setScene(new TrackComp().getTrackCompScene());
		});
		
		add.setOnAction(e -> {
			try {
				primaryStage.setScene(new AddParticipations().getAddParticipationsScene());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		sendEmail.setOnAction(e -> {
			try {
				primaryStage.setScene(new AddParticipations(true).getAddParticipationsScene());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
}