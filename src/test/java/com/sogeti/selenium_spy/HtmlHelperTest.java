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

		ArrayList<Map<String, String>> links = HtmlHelper.getLinks(doc);

		List<Map<String, String>> expectedListOfLinks = new ArrayList<Map<String, String>>();

		Map<String, String> firstLinkDetails = new HashMap<String, String>();
		firstLinkDetails.put("href", "http://www.example.com");
		firstLinkDetails.put("text", "Example link");
		expectedListOfLinks.add(firstLinkDetails);

		Map<String, String> secondLinkDetails = new HashMap<String, String>();
		secondLinkDetails.put("href", "http://www.example2.com");
		secondLinkDetails.put("text", "Another example link");
		expectedListOfLinks.add(secondLinkDetails);

		assertEquals(expectedListOfLinks, links);
	}

	public void testGetImages() {
		String html = "<html><img alt=\"alternate text 1\" class=\"class_name_1\" src=\"/image1.png\">"
				+ "<img alt=\"alternate text 2\" src=\"/image2.png\"></html>";
		Document doc = Jsoup.parse(html);
		
		ArrayList<Map<String, String>> images = HtmlHelper.getImages(doc);
		
		List<Map<String, String>> expectedListOfImages = new ArrayList<Map<String, String>>();

		Map<String, String> firstImageDetails = new HashMap<String, String>();
		firstImageDetails.put("alt", "alternate text 1");
		firstImageDetails.put("class", "class_name_1");
		firstImageDetails.put("src", "/image1.png");
		expectedListOfImages.add(firstImageDetails);

		Map<String, String> secondImageDetails = new HashMap<String, String>();
		secondImageDetails.put("alt", "alternate text 2");
		secondImageDetails.put("class", "");
		secondImageDetails.put("src", "/image2.png");
		expectedListOfImages.add(secondImageDetails);

		assertEquals(expectedListOfImages, images);
	}
	
	public void testGetInputs() {
		String html = "<html><input name=\"input_name\" class=\"input_class\" type=\"text\"/></html>";
		Document doc = Jsoup.parse(html);
		
		ArrayList<Map<String, String>> inputs = HtmlHelper.getInputs(doc);
		
		List<Map<String, String>> expectedListOfInputs = new ArrayList<Map<String, String>>();

		Map<String, String> inputDetails = new HashMap<String, String>();
		inputDetails.put("name", "input_name");
		inputDetails.put("class", "input_class");
		inputDetails.put("type", "text");
		expectedListOfInputs.add(inputDetails);

		assertEquals(expectedListOfInputs, inputs);
	}

}
