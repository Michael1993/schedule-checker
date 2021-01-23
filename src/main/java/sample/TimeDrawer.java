package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static java.util.function.Predicate.not;
import static sample.Main.MINUTES_TO_PIXEL;

public class TimeDrawer implements EventHandler<ActionEvent> {

	private static final String TIME_ID = "time";

	private final Pane root;
	private final Schedule schedule;

	public TimeDrawer(Pane root, Schedule schedule) {
		this.root = root;
		this.schedule = schedule;
	}

	@Override
	public void handle(ActionEvent actionEvent) {
		root.getChildren().removeIf(TimeDrawer::hasTimeId);
		if (schedule.items().stream().anyMatch(Schedule.Item::isActive)) {
			double previousTaskTimes = schedule.items().stream()
					.takeWhile(not(Schedule.Item::isActive))
					.mapToDouble(Utils::pixels).sum();
			double currentTaskElapsedTime = schedule.items().stream()
					.filter(Schedule.Item::isActive)
					.findFirst()
					.map(item -> ChronoUnit.MINUTES.between(item.from(), LocalTime.now()) * MINUTES_TO_PIXEL)
					.orElseThrow();
			double w = root.getWidth();

			Line line = new Line();
			line.setStartX(0);
			line.setEndX(w - 2);
			line.getStyleClass().add("redLine");
			line.setId(TIME_ID);

			VBox timePusher = new VBox();
			timePusher.setMinHeight(previousTaskTimes + currentTaskElapsedTime);
			timePusher.setMinWidth(root.getWidth());
			timePusher.setId(TIME_ID);

			VBox timeContainer = new VBox(timePusher, line);
			timeContainer.setId(TIME_ID);

			root.getChildren().add(timeContainer);
		}
	}

	private static boolean hasTimeId(Node node) {
		return TIME_ID.equals(node.getId());
	}
}
