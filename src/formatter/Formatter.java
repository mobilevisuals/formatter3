package formatter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Formatter {

	public static void main(String[] args) {

		Formatter main = new Formatter();
		ArrayList<String> list = main.readAndSort();
		main.writeAndCount(list);

	}

	private ArrayList<String> readAndSort() {

		String textToRead = null;
		File file = new File("src/formatter/kunder.txt");
		ArrayList<String> list = new ArrayList<>();
		try (BufferedReader instream = new BufferedReader(new FileReader(file))) {
			while ((textToRead = instream.readLine()) != null) {
				if (textToRead.length() > 0) {
					StringBuilder stringBuilder = new StringBuilder(textToRead);
					if (stringBuilder.charAt(0) == '0')
						stringBuilder.deleteCharAt(0);
					int position = stringBuilder.indexOf("-");
					if (position != -1)
						stringBuilder.deleteCharAt(position);
					if (stringBuilder.charAt(0) != '+')
						stringBuilder.insert(0, "+46");
					list.add(stringBuilder.toString());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Collections.sort(list);
		return list;
	}

	private void writeAndCount(ArrayList<String> list) {
		// A list of all of the numbers without doublets, so all numbers
		//only occurs only once:
		ArrayList<String> numberList = new ArrayList<>();
		//The amounts of the numbers:
		ArrayList<Integer> countList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			//numberList is empty at the start, so index will be -1 then.
			//numberList is then increasing as the loop goes on and when numbers are added to it.
			//This makes index increase also.
			int index = numberList.size() - 1;
			//The code line below determines if 1 number occurs several times. This is done by
			//testing if the current number is the same as the next number in the list.
			if (index >= 0 && numberList.get(index).equals(list.get(i))) {
				//This means that it is a doublet. Then set a count larger than 1. 
				int count = countList.get(index);
				++count;
				countList.set(index, count);
			} else {//if only 1 occurrence of a number
				numberList.add(list.get(i));
				countList.add(1);
			}

		}

		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/formatter/kunder2.txt"));) {

			for (int i = 0; i < numberList.size(); i++) {
				//only write the doublets
				if (countList.get(i) > 1) {
					bufferedWriter.write(numberList.get(i) + ";" + countList.get(i));
					bufferedWriter.newLine();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
