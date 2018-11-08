package com.sogeti.selenium_spy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sogeti.selenium_spy.helpers.HtmlHelper;

public class WebObjectSpy {

	public static void main(String[] args) {

		// String url = args[0];
		// String url = "https://travis-ci.org/denniscolburn/selenium-spy";
		// String url = "https://en.wikipedia.org/wiki/Main_Page";
		String url = "http://puppies.herokuapp.com/";

		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase database = mongo.getDatabase("selenium-spy");

		MongoCollection<org.bson.Document> collection = database
				.getCollection("webObjects");
		
		String title = HtmlHelper.getTitle(doc);
		List<Map<String, String>> linksList = HtmlHelper.getLinks(doc);

		org.bson.Document document = new org.bson.Document();
		document.append("url", url);
		collection.insertOne(document);
		document.clear();
		document.append("title", title);
		collection.insertOne(document);
		
		for (Map<String, String> link : linksList) {
			document = new org.bson.Document();
			document.append("link", link);
			collection.insertOne(document);
		}
//		document.append("links", linksList);
		// TODO alt="Home" indicates home button

		FindIterable<org.bson.Document> iterDoc = collection.find();

		Iterator<org.bson.Document> it = iterDoc.iterator();

		while (it.hasNext()) {
			org.bson.Document d = it.next();
			System.out.println(d);
		}

		mongo.close();

		System.out.println("Done!");

	}

}
