package levelfiles;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * A class that describes working with game files
 * @author Dmitriy Stepanov
 */
public class Files {
	private static final String filePath = getDefaultDirectory() + "/BrickBreaker/";
	public static String LEVELPATH = getDefaultDirectory() + "/BrickBreaker/Levels.txt";
	private static Scanner scanner = null;
	
	public Files() {}

	public static void init() {
		createDir(filePath);
		createFile(LEVELPATH);
		for(int i = 0; i < 30; i++) {
			createLevel(filePath + "CustomLevel" + i + ".txt");
		}
	}
	
	public static boolean[] readFile(String filePath) {
		boolean[] lockedLevels = new boolean[Level.MAX_LEVEL + 1];
		File file = new File(filePath);

		if (file.exists()) {
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			int line = 0;
			while (true) {
				assert scanner != null;
				if (!scanner.hasNextBoolean()) break;
				lockedLevels[line] = scanner.nextBoolean();
				line++;
			}

			scanner.close();
		} else {
			createFile(filePath);
		}
		return lockedLevels;

	}

	public static int[][] readLevel(int level) {
		File file = new File(filePath + "CustomLevel" + level + ".txt");
		int[][] grid = new int[8][12];

		if (file.exists()) {
			try {
				scanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			while(true) {
				if (!Objects.requireNonNull(scanner).hasNextLine()) break;
				for (int i = 0; i < grid.length; i++) {
		            String[] line = scanner.nextLine().trim().split(" ");
		            for (int j = 0; j < line.length; j++) {
		               grid[i][j] = Integer.parseInt(line[j]);
		            }
		         }
			}
			scanner.close();
		} else {
			createFile(filePath + "CustomLevel" + level + ".txt");
		}
		return grid;
	}

	public static void SaveProgress(boolean[] scores) {
		deleteFile(filePath + "Levels.txt");
		createFile(filePath + "Levels.txt");
		writeFile(new File(filePath + "Levels.txt"), scores);
	}

	public static void SaveLevel(int level, int[][] grid) {
		deleteFile(filePath + "CustomLevel" + level + ".txt");
		createLevel(filePath + "CustomLevel" + level + ".txt");
		writeLevel(new File(filePath + "CustomLevel" + level + ".txt"), level, grid);
	}
	
	public static void writeLevel(File file, int level, int[][] grid) {
		FileWriter writer;
		try {
			writer = new FileWriter(file);

			for (int[] ints : grid) {
				for (int j = 0; j < grid[0].length; j++) {
					writer.write(ints[j] + " ");
				}
				writer.write("\n");
			}
			System.out.println("Writing to file");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createFile(String filePath) {
			File path = new File(filePath);
			boolean isCreateNewFile = path.exists();

			if (!isCreateNewFile) {
				try {
					path.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}

				boolean[] lockedLevels = new boolean[Level.MAX_LEVEL + 1];
				for(int i = 1; i < lockedLevels.length; i++) {
					lockedLevels[i] = true;
				}

				lockedLevels[0] = false;
				writeFile(path, lockedLevels);
		}
	}

	public static void createLevel(String filePath) {
		File path = new File(filePath);
		boolean isCreateLevel = path.exists();

		if (!isCreateLevel) {
			try {
				path.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			int[][] grid = new int[8][12];
			for (int[] ints : grid) {
				Arrays.fill(ints, 0);
			}

			writeLevel(path, grid);
		}
	}

	public static void createDir(String filePath) {
		File path = new File(filePath);
		boolean isCreateDirectory = path.exists();
		if (!isCreateDirectory) {
			path.mkdir();
		}
	}

	public static void writeFile(File file, boolean[] lockedLevels) {
		FileWriter writer;
		try {
			writer = new FileWriter(file);

			for (boolean lockedLevel : lockedLevels) {
				writer.write(lockedLevel + "\n");
			}

			System.out.println();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeLevel(File file, int[][] grid) {
		FileWriter writer;
		try {
			writer = new FileWriter(file);

			for (int[] ints : grid) {
				for (int anInt : ints) {
					writer.write("" + anInt + " ");
				}

				writer.append("\n");
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		boolean isDeleteFile = file.exists();
		if (isDeleteFile) {
			file.delete();
		}
	}

	public static String getDefaultDirectory() {
		String OS = System.getProperty("os.name").toUpperCase();
		if (OS.contains("WIN")) {
			return System.getenv("APPDATA");
		}
		return System.getProperty("user.home");
	}
}