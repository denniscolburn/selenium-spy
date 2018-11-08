package com.sogeti.selenium_spy;

import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Sandbox {

	public static void main(String[] args) {

		MongoClient mongo = new MongoClient("localhost", 27017);

		MongoDatabase database = mongo.getDatabase("sandbox");
		MongoCollection<org.bson.Document> collection = database
				.getCollection("sampleCollection");
		System.out.println("Collection sampleCollection selected successfully");

		Document doc = new Document();

		doc.append("url", "http://puppies.herokuapp.com/");
		doc.append("title", "Sally's Puppy Adoption Agency");
		ArrayList<String> links = new ArrayList<String>();
		links.add("link 1");
		links.add("link 2");
		links.add("link 3");
		doc.append("links", links);

		collection.insertOne(doc);

		// Getting the iterable object
		FindIterable<org.bson.Document> iterDoc = collection.find();

		// Getting the iterator
		Iterator<org.bson.Document> it = iterDoc.iterator();

		while (it.hasNext()) {
			org.bson.Document d = it.next();
			System.out.println(d);
		}

		mongo.close();

		System.out.println("Done!");

	}

}
