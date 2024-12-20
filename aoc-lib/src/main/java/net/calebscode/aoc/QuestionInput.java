package net.calebscode.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import net.calebscode.aoc.data.MapGrid;
import net.calebscode.aoc.util.Utils;

public class QuestionInput {

	private List<String> lines;

	public QuestionInput(List<String> lines) {
		this.lines = new ArrayList<>(lines);
	}
	
	public QuestionInput(String resourcePath) {
		lines = new ArrayList<String>();
		try (
			var inputStream = getClass().getResourceAsStream(resourcePath);
			var reader = new BufferedReader(new InputStreamReader(inputStream));
		) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public List<String> getLines() {
		return Collections.unmodifiableList(lines);
	}

	public List<QuestionInput> splitByBlankLine() {
		var inputs = new ArrayList<QuestionInput>();
		var current = new ArrayList<String>();
		
		for (var line : lines) {
			if (line.isBlank()) {
				if (!current.isEmpty()) {
					inputs.add(new QuestionInput(current));
					current = new ArrayList<>();
				}
			} else {
				current.add(line);
			}
		}

		if (!current.isEmpty()) {
			inputs.add(new QuestionInput(current));
		}

		return inputs;
	}
	
	public Stream<String> stream() {
		return lines.stream();
	}
	
	public Stream<String> parallelStream() {
		return lines.parallelStream();
	}
	
	public String asOneLine() {
		return lines.stream().reduce("", (acc, val) -> acc + val);
	}
	
	public Character[][] asCharacterArray() {
		Character[][] chars = new Character[lines.size()][];
		
		for (int i = 0; i < lines.size(); i++) {
			var line = lines.get(i);
			chars[i] = line.chars().mapToObj(c -> (char) c).toList().toArray(new Character[line.length()]);
		}
		
		Utils.transpose(chars);

		return chars;
	}
	
	public char[][] asCharArray() {
		char[][] chars = new char[lines.size()][];

		for (int i = 0; i < lines.size(); i++) {
			chars[i] = lines.get(i).toCharArray();
		}
		
		Utils.transpose(chars);

		return chars;
	}
	
	public Integer[][] asIntegerArray() {
		Integer[][] ints = new Integer[lines.size()][];

		for (int row = 0; row < lines.size(); row++) {
			var line = lines.get(row);
			ints[row] = new Integer[line.length()];

			for (int col = 0; col < line.length(); col++) {
				ints[row][col] = line.charAt(col) - '0';
			}
		}
		
		Utils.transpose(ints);

		return ints;
	}

	public int[][] asIntArray() {
		int[][] ints = new int[lines.size()][];

		for (int row = 0; row < lines.size(); row++) {
			var line = lines.get(row);
			ints[row] = new int[line.length()];

			for (int col = 0; col < line.length(); col++) {
				ints[row][col] = line.charAt(col) - '0';
			}
		}
		
		Utils.transpose(ints);

		return ints;
	}
	
	private <T> T[][] convertCharacterArray(Function<Character, T> converter) {
		var charArray = asCharacterArray();
		var rows = new ArrayList<List<T>>();
		
		for (int row = 0; row < charArray.length; row++) {
			var cols = new ArrayList<T>();
			for (int col = 0; col < charArray[row].length; col++) {
				cols.add(converter.apply(charArray[row][col]));
			}
			rows.add(cols);
		}
		
		@SuppressWarnings("unchecked")
		var tArray = (T[][]) rows.parallelStream().map(list -> (T[]) list.toArray()).toArray();
		return tArray;
	}
	
	public <T> MapGrid<T> asGridFromCharacters(Function<Character, T> converter, boolean wrap) {
		var tArray = convertCharacterArray(converter);		
		return new MapGrid<T>(tArray, wrap);
	}

	
	public <T> MapGrid<T> asGridFromCharacters(Function<Character, T> converter, boolean wrap, BiFunction<Integer, Integer, T> outOfBoundsSupplier) {
		var tArray = convertCharacterArray(converter);
		return new MapGrid<T>(tArray, wrap, outOfBoundsSupplier);
	}
	
	private <T> T[][] convertIntegerArray(Function<Integer, T> converter) {
		var charArray = asIntegerArray();
		var rows = new ArrayList<List<T>>();
		
		for (int row = 0; row < charArray.length; row++) {
			var cols = new ArrayList<T>();
			for (int col = 0; col < charArray[row].length; col++) {
				cols.add(converter.apply(charArray[row][col]));
			}
			rows.add(cols);
		}
		
		@SuppressWarnings("unchecked")
		var tArray = (T[][]) rows.parallelStream().map(list -> (T[]) list.toArray()).toArray();
		return tArray;
	}
	
	public <T> MapGrid<T> asGridFromIntegers(Function<Integer, T> converter, boolean wrap, boolean expand) {
		var tArray = convertIntegerArray(converter);		
		return new MapGrid<T>(tArray, wrap);
	}

	
	public <T> MapGrid<T> asGridFromIntegers(Function<Integer, T> converter, boolean wrap, BiFunction<Integer, Integer, T> outOfBoundsSupplier) {
		var tArray = convertIntegerArray(converter);
		return new MapGrid<T>(tArray, wrap, outOfBoundsSupplier);
	}
	
	public MapGrid<Character> asCharacterGrid(boolean wrap, boolean expand) {
		return new MapGrid<Character>(asCharacterArray(), wrap);
	}
	
	public MapGrid<Character> asCharacterGrid(boolean wrap, boolean expand, BiFunction<Integer, Integer, Character> outOfBoundsSupplier) {
		return new MapGrid<Character>(asCharacterArray(), wrap, outOfBoundsSupplier);
	}

	public MapGrid<Integer> asIntegerGrid(boolean wrap) {
		return new MapGrid<Integer>(asIntegerArray(), wrap);
	}
	
	public MapGrid<Integer> asIntegerGrid(boolean wrap, BiFunction<Integer, Integer, Integer> outOfBoundsSupplier) {
		return new MapGrid<Integer>(asIntegerArray(), wrap, outOfBoundsSupplier);
	}
	
}
