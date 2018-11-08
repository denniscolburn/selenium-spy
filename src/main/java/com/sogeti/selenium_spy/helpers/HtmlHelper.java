package com.sogeti.selenium_spy.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlHelper {

	public static String getTitle(Document doc) {
		return doc.title();		
	}
	
	public static ArrayList<Map<String, String>> getLinks(Document doc) {
		ArrayList<Map<String , String>> linksList  = new ArrayList<Map<String,String>>();
		Elements links = doc.select("a[href]");
		
		for (Element link : links) {
			Map<String,String> linkDetails = new HashMap<String, String>();
			linkDetails.put("href", link.attr("href"));
			linkDetails.put("text", link.text());
			linksList.add(linkDetails);
		}
		
		return linksList;
	}

}
