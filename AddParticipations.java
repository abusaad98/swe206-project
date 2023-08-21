package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.apache.poi.util.StringUtil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class AddParticipations {

	static Scene AddParticipationsScene;
	boolean isTeam = false;
	boolean Email = false;

	public AddParticipations() throws IOException{
		showFirstAddScene();
	}

	public AddParticipations(boolean Email) throws IOException{
		this.Email = Email;
		showFirstAddScene();
	}

	public Scene getAddParticipationsScene() {
		return AddParticipationsScene;
	}

	public void showFirstAddScene() throws IOException {

		ComboBox<String> compList = new ComboBox<>();
		FileManager addObj = new FileManager();

		for (int i = 0; i < addObj.getSheets().size(); i++) {
			compList.getItems().add(addObj.getSheets().get(i));
		}

		Label label1 = new Label("Competitions: ");
		label1.setFont(new Font("Arial", 24));

		Button submet = new Button("submit");
		Button back = new Button("Go Back ");
		Label trackLabel = new Label("adding participation");
		if (this.Email) {
			trackLabel.setText("Sending an Email");
		}
		trackLabel.setFont(new Font("Arial", 32));
		Label errorLabel = new Label("");
		errorLabel.setStyle("-fx-font-weight: bold");
		errorLabel.setTextFill(Color.RED);
		errorLabel.setFont(new Font("Arial", 32));

		submet.setOnAction(e -> {

			if (compList.getValue() != null) {
				FileManager comp = new FileManager(compList.getValue());
				try {

					if(comp.isSheetExist()) {
						isTeam = comp.isTeam();
						errorLabel.setText("");
						showSecondAddScene(compList.getValue());
					}

					else {
						errorLabel.setText("The compitition you entered does not exist!");
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			else {
				errorLabel.setText("You need to choose a competition to submit!");
			}

		});

		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(15, 15, 15, 15));
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(label1, compList);

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

		AddParticipationsScene = new Scene(borderPane, 800, 700);
		ProjectMain.mainstage.setScene(AddParticipations.AddParticipationsScene);

		back.setOnAction(e -> {
			ProjectMain.mainstage.setScene(ProjectMain.mainscene);
		});

	}

	public void showSecondAddScene(String compName) throws IOException {
		if (!this.Email) {
			TextField studentID = new TextField();
			studentID.setPromptText("Enter student ID");
			TextField studentName = new TextField();
			studentName.setPromptText("Enter student name");
			TextField major = new TextField();
			major.setPromptText("Enter student major");
			TextField teamNum = new TextField();
			teamNum.setPromptText("Enter team number");
			TextField teamName = new TextField();
			teamName.setPromptText("Enter team name");
			TextField rank = new TextField();
			rank.setPromptText("Enter rank");

			Label label1 = new Label("Student ID: ");
			Label label2 = new Label("student name: ");
			Label label3 = new Label("student major: ");
			Label label4 = new Label("team number: ");
			Label label5 = new Label("team name: ");
			Label label6 = new Label("rank: ");

			Button submet = new Button("submit");
			Button back = new Button("Go Back ");
			Label trackLabel = new Label("adding participation to " + compName);
			trackLabel.setFont(new Font("Arial", 32));
			Label errorLabel = new Label("");
			errorLabel.setStyle("-fx-font-weight: bold");
			errorLabel.setTextFill(Color.RED);
			errorLabel.setFont(new Font("Arial", 32));

			submet.setOnAction(e -> {

				if(isTeam) {

					if (studentName.getText() != "" && studentID.getText() != "" && major.getText() != "" && teamNum.getText() != "" && teamName.getText() != "" && rank.getText() != "") {
						FileManager comp = new FileManager(compName);
						try {
							ArrayList<String> list = new ArrayList<String>();

							list.add(studentName.getText());
							list.add(studentID.getText());
							list.add(major.getText());
							list.add(teamNum.getText());
							list.add(teamName.getText());
							list.add(rank.getText());

							comp.writeParticipations(list);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						errorLabel.setText("");
						studentName.clear();
						studentID.clear();
						major.clear();
						teamNum.clear();
						teamName.clear();
						rank.clear();
					}

					else {
						errorLabel.setText("You need to fill all the fields in the right format to submit!");
					}

				}

				else {

					if (studentName.getText() != "" && studentID.getText() != "" && major.getText() != "" && rank.getText() != "") {
						FileManager comp = new FileManager(compName);
						try {
							ArrayList<String> list = new ArrayList<String>();

							list.add(studentName.getText());
							list.add(studentID.getText());
							list.add(major.getText());
							list.add(rank.getText());

							comp.writeParticipations(list);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						errorLabel.setText("");
						studentName.clear();
						studentID.clear();
						major.clear();
						rank.clear();
					}

					else {
						errorLabel.setText("You need to fill all the fields in the right format to submit!");
					}

				}

			});

			VBox vbox = new VBox();
			vbox.setSpacing(10);
			vbox.setPadding(new Insets(15, 15, 15, 15));
			vbox.setAlignment(Pos.CENTER_LEFT);
			if(isTeam)
				vbox.getChildren().addAll(label1, studentName, label2, studentID, label3, major, label4, teamNum, label5, teamName, label6, rank);
			else {
				vbox.getChildren().addAll(label1, studentName, label2, studentID, label3, major, label6, rank);
			}

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

			AddParticipationsScene = new Scene(borderPane, 800, 700);
			ProjectMain.mainstage.setScene(AddParticipations.AddParticipationsScene);

			back.setOnAction(e -> {
				try {
					showFirstAddScene();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});

		}
		else {

			ComboBox<String> PartList = new ComboBox<>();
			FileManager addObj = new FileManager(compName);

			if (addObj.isTeam()) {
				for (int i = 8; i < addObj.readFile().size(); i++) {
					if((i-8)%7  == 0) {
						if (!PartList.getItems().contains(addObj.readFile().get(i))) {
							PartList.getItems().add(addObj.readFile().get(i));
						}
					}
				}
			}
			else {
				for (int i = 5; i < addObj.readFile().size(); i++) {
					if((i-5)%5  == 0) {
						if (!PartList.getItems().contains(addObj.readFile().get(i))) {
							PartList.getItems().add(addObj.readFile().get(i));
						}
					}
				}
			}

			Label label1 = new Label("Student: ");
			if(addObj.isTeam()) {
				label1.setText("Teams: ");
			}

			label1.setFont(new Font("Arial", 24));

			Button back = new Button("Go Back ");
			Button email = new Button("Email"); 
			Label trackLabel = new Label("Select a Student");
			if(addObj.isTeam()) {
				trackLabel.setText("Select a Team");
			}
			trackLabel.setFont(new Font("Arial", 32));
			Label errorLabel = new Label("");
			errorLabel.setStyle("-fx-font-weight: bold");
			errorLabel.setTextFill(Color.RED);
			errorLabel.setFont(new Font("Arial", 32));



			VBox vbox = new VBox();
			vbox.setSpacing(10);
			vbox.setPadding(new Insets(15, 15, 15, 15));
			vbox.setAlignment(Pos.CENTER);
			vbox.getChildren().addAll(label1, PartList);

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
			buttonPane.getChildren().addAll(back,email);

			BorderPane borderPane = new BorderPane();
			borderPane.setTop(trackPane);
			borderPane.setCenter(vbox);
			borderPane.setBottom(buttonPane);

			AddParticipationsScene = new Scene(borderPane, 800, 700);
			ProjectMain.mainstage.setScene(AddParticipations.AddParticipationsScene);

			back.setOnAction(e -> {
				try {
					ProjectMain.mainstage.setScene(new AddParticipations(true).getAddParticipationsScene());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});

			email.setOnAction(e ->{
				Desktop desktop;
				ArrayList<String> TeamEmails = new ArrayList<String>(); 
				String Header = "";
				String rank = "";
				try {
					if (addObj.isTeam()) {
						for (int i = 4; i < addObj.readFile().size(); i++) {
							if((i-4)%7  == 0) {
								if(addObj.readFile().get(i+4).equals(PartList.getValue())) {
									TeamEmails.add("s"+addObj.readFile().get(i)+"@kfupm.edu.sa");
									if(rank.length()==0) {
										rank+= addObj.readFile().get(i+5);
									}
								}
							}
						}
						Header += TeamEmails.get(0);
						if (TeamEmails.size()>1) {
							Header+="?cc=";
							for(int i= 1 ; i<TeamEmails.size()-1 ;i++) {
								Header+= TeamEmails.get(i)+", ";
							}
						}
						Header+= TeamEmails.get(TeamEmails.size()-1);

					}
					else {
						for (int i = 4; i < addObj.readFile().size(); i++) {
							if((i-4)%5 == 0) {
								if(addObj.readFile().get(i+1).equals(PartList.getValue())) {
									Header+="s"+addObj.readFile().get(i)+"@kfupm.edu.sa";
									rank+= addObj.readFile().get(i+3);
								}
							}
						}
					}
					String competition = StringUtil.join(compName.split(" "),"%20");
					String participant = StringUtil.join(PartList.getValue().split(" "),"%20");
					if (addObj.isTeam()) {
						Header+=",&";
					}
					else {
						Header+="?";
					}
					
					competition = StringUtil.join(competition.split("&"),"%26");
					if (Desktop.isDesktopSupported() 
							&& (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
						URI mailto;
						String[] Mail = ("mailto:"+Header+"subject=Congratulation on achieving "+rank+" place in "+competition+"&body=Dear"+participant+",%0A%0A"
								+ "Conguratulation on your achievement in "+competition+". This achievement is deeply appreciated by the unversity and we will anounce it in the approbrite medias.%0A%0A"
								+ "In case you have Photos you want to share with the news post, reply to this email with the photos.%0A%0A"
								+ "Regards and Congrats,%0A%0A"
								+ "KFUPM News Team").split(" ");
						String mail = StringUtil.join(Mail,"%20");
						mailto = new URI(mail);
						desktop.mail(mailto);
					}
				}
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
	}
}
