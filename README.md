
  
# TextAnalyzer-CEN-3024C

Text analyzer that will take a file and output statistics on frequency of words.
## Development Plan
Below will be all planning and outlining for the problem provided.
<hr>

### Requirements
- Output the word frequencies of all words in the file.
- Output should be sorted by frequency of the words.
- Output should be a set of pairs, each pair containing a word and how many times it occurs.
- Output should:
	- Ignore all HTML tags
	- Ignore all text before the poem's title
	- Ignore all text after the end of the poem
<hr>

### Planning
Going by the first requirement, outputting the frequency of all words in the file seems to be most simply done by use of a HashMap and a loop through the words within the text. This alone will yield an O(N) runtime. As we must loop through all the words and add it to the map.<br><hr>

The second requirement is to have the output sorted. Unfortunately, this will make this slow. Because of this requirement we can't expect much better than O(NlogN). Which is slow, but that's the nature of sorting. I will implement merge sort as I find it to be the simplest to implement, I will also do so recursively.<br><hr>

The third requirement is to have the output has a set of pairs. This should be easily
done using the map as we can retrieve all the entries of the HashMap and output both the key (the string) and the value (the number of times it occurs).<br><hr>

The fourth requirement is to ignore HTML tags and text before and after the poem.<br/>(Who wants to read that license anyway?)

## My Notepad While Brainstorming
Awesome, no more planning. Now, to implement this solution the biggest factor in solving it for me is the ignoring of HTML tags. Which, I assume, for this we aren't meant to create our own HTML parser, so I will opt to use Jsoup. 

Oh lucky me! It even just so happens that the paragraphs of the poem are the only paragraph tags in the entire .html! This will make this system super easy using Jsoup. Simply using 
```java
//Connecting and parsing website
Document doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();
//Select all elements with paragraph tags
Elements paragraphs = doc.select("p");
//Create HashMap, int has to be Integer
HashMap<String, Integer> map = new HashMap<>();
//Loop through each paragraph, then loop through the string of text.
for (Element p : paragraphs) {
	//Get the text from the paragraph
	String pText = p.text();
	//Split text by delimiter of a space character
	String[] words = pText.split(" ");
	for(String s : words) {
		//Get or default of null
		int val = map.getOrDefault(s,null);
		if(!val) { //If its null, set it as 1
			map.put(s,1);
		} else { //Else add to the value by 1
			map.put(s,val + 1);
		};
	};
};

//Now we have the map with data, we just need to sort it.
//Copy to ArrayList
ArrayList<List<String>> list = new ArrayList<>();
for(Map.Entry<String, String> entry : map.entrySet()) {
	String word = entry.getKey();
	String count = entry.getValue();
	List<String> tempList = new ArrayList<String>();
	tempList.add(word); tempList.add(count);
	list.add(tempList);
}

//Use Collections.sort() to sort the ArrayList
Collections.sort(list, new Comparator<List<String>>() {
	@Override
	public int compare(List<String> o1, List<String> o2) {
		return o2.get(1).compareTo(o1.get(1));
	}
});

//Output top 20 words
for(int i = 1; i <= 20; i++) {
	System.out.println( "Word " + i + ": Word '" + list.get(i-1).get(0) + "' Count " + list.get(i-1).get(1) );
}

//This is not final, changes will probably need to occur when actually writing the code.
```

## Output
<p align='center'>
	<img src="https://imgur.com/IkO7cx5.png"><br/>
	The output in the terminal after running the application.
</p>
