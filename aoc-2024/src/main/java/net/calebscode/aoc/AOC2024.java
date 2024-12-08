package net.calebscode.aoc;

import net.calebscode.aoc.solutions.AOC2024_Day8;

public class AOC2024 {
	public static void main(String[] args) {
		
		var solution = new AOC2024_Day8();
		System.out.printf(
			"First Solution: %d\n",
			solution.timeFirst()
		);

		System.out.printf(
			"Second Solution: %d\n",
			solution.timeSecond()
		);

	}
}