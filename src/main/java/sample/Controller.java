package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Controller {

	@FXML
	private StackPane stackPane;

	@FXML
	private VBox rootPane;

	public void initialize() throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(new File("./schedule.txt")));
		Schedule schedule = Schedule.from(reader.lines());
		Timeline drawTime = new Timeline(
				new KeyFrame(Duration.seconds(1),
						new TimeDrawer(stackPane, schedule)
				));
		drawTime.setCycleCount(Timeline.INDEFINITE);
		drawTime.play();
		ScheduleDrawer.draw(rootPane, schedule);
	}
}
