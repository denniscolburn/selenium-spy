package com.sogeti.selenium_spy;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sogeti.selenium_spy.helpers.HtmlHelper;
//import com.mongodb.client.model.Filters;

public class SeleniumSpy {

	public static void main(String[] args) {
		
		// String html = "<html><head><title>First parse</title></head>"
		// + "<body><p>Parsed HTML into a doc.</p></body></html>";
		// Document doc = Jsoup.parse(html);

		String url = "http://puppies.herokuapp.com/";
		// String url = "https://travis-ci.org/denniscolburn/selenium-spy";
		// String url = "https://en.wikipedia.org/wiki/Main_Page";

		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String title = HtmlHelper.getTitle(doc);
		System.out.println(title);

		System.out.println("*** Links ***");
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			System.out.println(link.text());
			System.out.println(link.attr("href"));
		}
		// alt="Home" indicates home button

		System.out.println("*** Buttons ***");
		Elements buttons = doc.select("input.rounded_button");
		for (Element button : buttons) {
			System.out.println(button.attr("value"));
		}

		System.out.println("*** Inputs ***");
		Elements inputs = doc.select("input");
		for (Element input : inputs) {
			System.out.println(input.attr("value") + ";" + input.attr("name"));
		}

		System.out.println("*** Media ***");
		Elements media = doc.select("[src]");
		for (Element m : media) {
			System.out.println(m);
		}

		// System.out.println();

		MongoClient mongo = new MongoClient("localhost", 27017);

		// Accessing the database
		MongoDatabase database = mongo.getDatabase("sandbox");

		// Creating a collection
		// database.createCollection("sampleCollection");
		// System.out.println("Collection created successfully");

		// Retieving a collection
		MongoCollection<org.bson.Document> collection = database
				.getCollection("sampleCollection");
		System.out.println("Collection sampleCollection selected successfully");

		// org.bson.Document document = new org.bson.Document("title",
		// "MongoDB")
		// .append("id", 1).append("description", "database")
		// .append("likes", 100)
		// .append("url", "http://www.tutorialspoint.com/mongodb/")
		// .append("by", "tutorials point");
		// collection.insertOne(document);
		// System.out.println("Document inserted successfully");

		// org.bson.Document document = new org.bson.Document("title",
		// "MongoDB v2")
		// .append("id", 2).append("description", "database")
		// .append("likes", 100)
		// .append("url", "http://www.tutorialspoint.com/mongodb/")
		// .append("by", "tutorials point");
		// collection.insertOne(document);
		// System.out.println("Document inserted successfully");

		// collection.updateOne(Filters.eq("id", 1), Updates.set("likes", 150));
		// System.out.println("Document update successfully...");

		// Deleting the documents
//		collection.deleteOne(Filters.eq("id", 1));
//		System.out.println("Document deleted successfully...");

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
