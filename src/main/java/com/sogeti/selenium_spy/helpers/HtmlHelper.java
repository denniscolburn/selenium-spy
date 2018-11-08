package com.sogeti.selenium_spy.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Methods for parsing html
 * 
 * @author Dennis Colburn
 * 
 */
public class HtmlHelper {

	/**
	 * Returns the title of an html file.
	 * 
	 * @param doc
	 *            Jsoup document
	 * @return String
	 */
	public static String getTitle(Document doc) {
		return doc.title();
	}

	/**
	 * Returns a list of links found in an html file.
	 * 
	 * @param doc
	 *            Jsoup document
	 * @return ArrayList of HashMaps
	 */
	public static ArrayList<Map<String, String>> getLinks(Document doc) {
		ArrayList<Map<String, String>> linksList = new ArrayList<Map<String, String>>();
		Elements links = doc.select("a[href]");

		for (Element link : links) {
			Map<String, String> linkDetails = new HashMap<String, String>();
			linkDetails.put("href", link.attr("href"));
			linkDetails.put("text", link.text());
			linksList.add(linkDetails);
		}

		return linksList;
	}

	/**
	 * Returns a list of images found in an html file.
	 * 
	 * @param doc
	 *            Jsoup document
	 * @return ArrayList of HashMaps
	 */
	public static ArrayList<Map<String, String>> getImages(Document doc) {
		ArrayList<Map<String, String>> imagesList = new ArrayList<Map<String, String>>();
		Elements images = doc.select("img");

		for (Element link : images) {
			Map<String, String> imageDetails = new HashMap<String, String>();
			imageDetails.put("alt", link.attr("alt"));
			imageDetails.put("class", link.attr("class"));
			imageDetails.put("src", link.attr("src"));
			imagesList.add(imageDetails);
		}

		return imagesList;
	}

	/**
	 * Returns a list of inputs found in an html file.
	 * 
	 * @param doc
	 *            Jsoup document
	 * @return ArrayList of HashMaps
	 */
	public static ArrayList<Map<String, String>> getInputs(Document doc) {
		ArrayList<Map<String, String>> inputsList = new ArrayList<Map<String, String>>();
		Elements inputs = doc.select("input");

		for (Element input : inputs) {
			Map<String, String> inputDetails = new HashMap<String, String>();
			inputDetails.put("name", input.attr("name"));
			inputDetails.put("class", input.attr("class"));
			inputDetails.put("type", input.attr("type"));
			inputsList.add(inputDetails);
		}

		return inputsList;
	}

}
