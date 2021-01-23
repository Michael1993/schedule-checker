package sample;

import java.time.temporal.ChronoUnit;

import static sample.Main.MINUTES_TO_PIXEL;

public class Utils {
	public static double pixels(Schedule.Item item) {
		return ChronoUnit.MINUTES.between(item.from(), item.to()) * MINUTES_TO_PIXEL;
	}
}
