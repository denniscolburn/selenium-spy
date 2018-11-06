package com.sogeti.selenium_spy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class WebObjectSpy {

	public static void main(String[] args) {

		String url = args[0];
		// String url = "https://travis-ci.org/denniscolburn/selenium-spy";
		// String url = "https://en.wikipedia.org/wiki/Main_Page";
		System.out.println(url);

		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String title = doc.title();
		System.out.println(title);

		HashMap<String, String> linksData = new HashMap<String, String>();
		System.out.println("*** Links ***");
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			System.out.println(link.text());
			System.out.println(link.attr("href"));
			linksData.put(link.attr("href"), link.text());
		}
		// TODO alt="Home" indicates home button
		
		for (Entry<String, String> entry : linksData.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println("key: : " + key + " value: " + value);

		}

//		System.out.println("*** Buttons ***");
//		Elements buttons = doc.select("input.rounded_button");
//		for (Element button : buttons) {
//			System.out.println(button.attr("value"));
//		}
//
//		System.out.println("*** Inputs ***");
//		Elements inputs = doc.select("input");
//		for (Element input : inputs) {
//			System.out.println(input.attr("value") + ";" + input.attr("name"));
//		}
//
//		System.out.println("*** Media ***");
//		Elements media = doc.select("[src]");
//		for (Element m : media) {
//			System.out.println(m);
//		}

		MongoClient mongo = new MongoClient("localhost", 27017);

		// Accessing the database
		MongoDatabase database = mongo.getDatabase("selenium-spy");

		// Creating a collection
		// database.createCollection("sampleCollection");
		// System.out.println("Collection created successfully");

		// Retieving a collection
		MongoCollection<org.bson.Document> collection = database
				.getCollection("sampleCollection");
		System.out.println("Collection sampleCollection selected successfully");

		// Getting the iterable object
		FindIterable<org.bson.Document> iterDoc = collection.find();

		// Getting the iterator
		Iterator<org.bson.Document> it = iterDoc.iterator();

		while (it.hasNext()) {
			org.bson.Document d = it.next();
			System.out.println(d.get("likes"));
		}

		for (String name : database.listCollectionNames()) {
			System.out.println(name);
		}

		mongo.close();

		System.out.println("Done!");

	}

}
