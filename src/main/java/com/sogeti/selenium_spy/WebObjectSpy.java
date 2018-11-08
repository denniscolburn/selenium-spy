package com.sogeti.selenium_spy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.code.CodeBlock;
import net.steppschuh.markdowngenerator.text.emphasis.ItalicText;
import net.steppschuh.markdowngenerator.text.heading.Heading;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sogeti.selenium_spy.helpers.HtmlHelper;

public class WebObjectSpy {

	public static void main(String[] args) {

		String url = args[0];
		// String url = "https://travis-ci.org/denniscolburn/selenium-spy";
		// String url = "https://en.wikipedia.org/wiki/Main_Page";
//		String url = "http://puppies.herokuapp.com/";

		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Connecting to MongoDB...");
		MongoClient mongo = new MongoClient("localhost", 27017);
		
		System.out.println("Getting the selenium-spy database...");
		MongoDatabase database = mongo.getDatabase("selenium-spy");

		System.out.println("Selecting the webObjects collection...");
		MongoCollection<org.bson.Document> collection = database
				.getCollection("webObjects");
		collection.drop();
		
		System.out.println("Getting the web application's title...");
		String title = HtmlHelper.getTitle(doc);
		System.out.println("Getting the web applications's links...");
		List<Map<String, String>> linksList = HtmlHelper.getLinks(doc);
		System.out.println("Getting the web applications's images...");
		List<Map<String, String>> imagesList = HtmlHelper.getImages(doc);
		System.out.println("Getting the web applications's inputs...");
		List<Map<String, String>> inputsList = HtmlHelper.getInputs(doc);

		org.bson.Document document = new org.bson.Document();
		document.append("url", url);
		collection.insertOne(document);
		document.clear();
		document.append("title", title);
		collection.insertOne(document);
		
		System.out.println("Inserting links into selenium-spy.webObjects...");
		for (Map<String, String> link : linksList) {
			document = new org.bson.Document();
			document.append("link", link);
			collection.insertOne(document);
		}

		// TODO alt="Home" indicates home button
		
		System.out.println("Inserting images into selenium-spy.webObjects...");
		for (Map<String, String> image : imagesList) {
			document = new org.bson.Document();
			document.append("image", image);
			collection.insertOne(document);
		}
		
		System.out.println("Inserting inputs into selenium-spy.webObjects...");
		for (Map<String, String> input : inputsList) {
			document = new org.bson.Document();
			document.append("input", input);
			collection.insertOne(document);
		}
		
		System.out.println("Creating output file...");
		String markdownFile = System.getProperty("user.dir") + "/output.md";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(markdownFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		writer.println(new Heading("Knowledge gleened after examining your web application"));
		writer.println();
		
		if (url.matches(".*\\.com/*$")) {
			StringBuilder sb = new StringBuilder();
			sb.append(new Text("This looks like the home page of your application.")).append("\n").append("\n");
			sb.append(new ItalicText("Suggested name for page object class:")).append("\n");
			sb.append(new CodeBlock(title.replaceAll(" ", "").replaceAll("'", "") + ".java", "Java"));
			writer.println(sb);
		}
		
		writer.println();
		writer.println(new Text("These look like a search boxes:"));
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("input.name", "search");
		FindIterable<org.bson.Document> cursor = collection.find(whereQuery);
		Iterator<org.bson.Document> it = cursor.iterator();
		while (it.hasNext()) {
			org.bson.Document d = it.next();
			org.bson.Document d2 = (org.bson.Document) d.get("input");
			writer.println("Input type: " + d2.get("type"));
			writer.println("Input class: " + d2.get("class"));
			writer.println();
		}
		
		writer.close();
		mongo.close();

		System.out.println("Done!");
	}
}
