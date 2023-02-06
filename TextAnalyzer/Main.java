package TextAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static void main(String[] args) {
		//Connect and parse website
		Document doc;
		try {
			doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		//Get all paragraph tags
		Elements paragraphs = doc.select("p");
		
		//Create hashmaps to store value
		HashMap<String, String> map = new HashMap<>();
		//Go through each paragraph
		for(Element p : paragraphs) {
			//Retrieve text and split by characters
			String pText = p.text();
			String[] words = pText.split("[ —“”.,?!;-]");
			//For each word in the paragraph
			for(String s : words) {
				s = s.toLowerCase();
				//Check if there is a value in the map, add if not
				String val = map.getOrDefault(s, null);
				if(val == null) {
					map.put(s, "1");
				} else {
					int temp = Integer.valueOf(val);
					temp++;
					map.put(s, String.valueOf(temp));
				}
			}
		}
		
		//Copy data to ArrayList
		ArrayList<List<String>> list = new ArrayList<>();
		for(Map.Entry<String, String> entry : map.entrySet()) {
			String word = entry.getKey();
			String count = entry.getValue();
			List<String> tempList = new ArrayList<String>();
			tempList.add(word);
			tempList.add(count);
			list.add(tempList);
		}
		
		//Use Collections.sort to sort 2D array
		Collections.sort(list, new Comparator<List<String>>() {
			@Override
			public int compare(List<String> o1, List<String> o2) {
				return o2.get(1).compareTo(o1.get(1));
			}
		});
		
		//Oh my goodness we have the sorted list!!
		
		//Output hardcoded top 20 words:
		for(int i = 1; i <= 20; i++) {
			String wordyword = "Word ";
			if(i < 10) {
				wordyword += " ";
			}
			System.out.println( wordyword + i + ": Word '" + list.get(i-1).get(0) + "' Count " + list.get(i-1).get(1) );
		}
	}
}
