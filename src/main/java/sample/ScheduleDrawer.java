package sample;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static sample.Main.MINUTES_TO_PIXEL;

class ScheduleDrawer {

	private ScheduleDrawer() {
		// private constructor to prevent utility class instantiation
	}

	public static void draw(Pane root, Schedule schedule) {
		int bound = schedule.items().size();
		for (int i = 0; i < bound; i++) {
			root.getChildren().add(scheduleItemAsNode(schedule.items().get(i), i % 2 == 0));
		}
	}

	private static Node scheduleItemAsNode(Schedule.Item item, boolean isEven) {
		if (item.description().isEmpty())
			return emptyNode(item);
		VBox timeBox = new VBox(
				new Text(item.from().format(DateTimeFormatter.ofPattern("HH:mm"))),
				new Text(item.to().format(DateTimeFormatter.ofPattern("HH:mm")))
		);
		timeBox.setAlignment(Pos.CENTER_LEFT);
		HBox box = new HBox(
				timeBox,
				new Text(item.description())
		);
		box.setSpacing(10);

		box.setAlignment(Pos.CENTER_LEFT);
		box.setMinHeight(ChronoUnit.MINUTES.between(item.from(), item.to()) * MINUTES_TO_PIXEL);
		box.setMaxHeight(ChronoUnit.MINUTES.between(item.from(), item.to()) * MINUTES_TO_PIXEL);

		if (isEven)
			box.getStyleClass().add("scheduleItemEven");
		else
			box.getStyleClass().add("scheduleItemOdd");
		return box;
	}

	private static Node emptyNode(Schedule.Item item) {
		HBox empty = new HBox();
		empty.setMinHeight(ChronoUnit.MINUTES.between(item.from(), item.to()) * MINUTES_TO_PIXEL);
		empty.getStyleClass().add("empty");

		return empty;
	}
}
