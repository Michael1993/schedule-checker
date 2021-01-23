package sample;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.time.LocalTime.now;

public class Schedule {

	private final List<Item> items = new ArrayList<>();

	private Schedule() {
		// must be instantiated through static factory method
	}

	public static Schedule from(Stream<String> lines) {
		Schedule schedule = new Schedule();
		lines.forEach(line -> createItem(schedule, line));
		schedule.items.sort(Comparator.comparing(Item::from));
		return schedule;
	}

	private static void createItem(Schedule schedule, String line) {
		String[] s = line.split(" ", 3);
		if (s.length != 3)
			throw new RuntimeException("Must have exactly 3 space-separated values in a line");
		LocalTime from = LocalTime.parse(withLeadingZero(s[0]), DateTimeFormatter.ofPattern("HH:mm"));
		LocalTime to = LocalTime.parse(withLeadingZero(s[1]), DateTimeFormatter.ofPattern("HH:mm"));
		if (to.isBefore(from))
			throw new DateTimeException("Start time must be earlier than end time.");

		String description = s[2];
		schedule.items.add(new Item(
				from,
				to,
				description
		));
	}

	private static String withLeadingZero(String time) {
		if (time.matches("\\d:\\d\\d"))
			return "0" + time;
		return time;
	}

	public List<Item> items() {
		return items;
	}

	public static class Item {
		private final LocalTime from;
		private final LocalTime to;
		private final String description;

		public Item(LocalTime from, LocalTime to, String description) {
			this.from = from;
			this.to = to;
			this.description = description;
		}

		public LocalTime from() {
			return from;
		}

		public LocalTime to() {
			return to;
		}

		public String description() {
			return description;
		}

		public boolean isActive() {
			return now().isAfter(this.from) && now().isBefore(this.to);
		}

		@Override
		public String toString() {
			return String.format("[from = %s, to = %s, description = %s]", from, to, description);
		}
	}
}
