package com.sogeti.selenium_spy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sogeti.selenium_spy.helpers.HtmlHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HtmlHelperTest extends TestCase {

	public void testGetTitle() {
		String html = "<html><head><title>Awesome web application</title></head></html>";
		Document doc = Jsoup.parse(html);
		String title = HtmlHelper.getTitle(doc);

		String expected = "Awesome web application";

		assertEquals(expected, title);
	}

	public void testGetLinks() {
		String html = "<html><a href=\"http://www.example.com\">Example link</a><a href=\"http://www.example2.com\">Another example link</a></html>";
		Document doc = Jsoup.parse(html);

		ArrayList<Map<String,String>> links = HtmlHelper.getLinks(doc);
		
		List<Map<String , String>> expectedListOfLinks  = new ArrayList<Map<String,String>>();
		
		Map<String,String> firstLinkDetails = new HashMap<String, String>();
		firstLinkDetails.put("href", "http://www.example.com");
		firstLinkDetails.put("text", "Example link");
		expectedListOfLinks.add(firstLinkDetails);
		
		Map<String,String> secondLinkDetails = new HashMap<String, String>();
		secondLinkDetails.put("href", "http://www.example2.com");
		secondLinkDetails.put("text", "Another example link");
		expectedListOfLinks.add(secondLinkDetails);

		assertEquals(expectedListOfLinks, links);
	}

}
