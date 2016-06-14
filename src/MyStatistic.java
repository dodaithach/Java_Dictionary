package ddthach.homework02;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MyStatistic {
	private int m_current_week;
	private int m_current_month;
	
	private ArrayList<String> m_words_in_week;
	private ArrayList<Integer> m_times_in_week;
	
	private ArrayList<String> m_words_in_month;
	private ArrayList<Integer> m_times_in_month;
	
	public MyStatistic() {
		Calendar cal = Calendar.getInstance();
		
		m_current_month = cal.get(Calendar.MONTH);
		m_current_week = cal.get(Calendar.WEEK_OF_YEAR);
		
		m_words_in_week = new ArrayList<>();
		m_times_in_week = new ArrayList<>();
		
		m_words_in_month = new ArrayList<>();
		m_times_in_month = new ArrayList<>();
	}
	
	public MyStatistic(String srcPath) {
		Calendar cal = Calendar.getInstance();
		m_current_week = cal.get(Calendar.WEEK_OF_YEAR);
		m_current_month = cal.get(Calendar.MONTH);
		
		m_words_in_week = new ArrayList<>();
		m_times_in_week = new ArrayList<>();
		
		m_words_in_month = new ArrayList<>();
		m_times_in_month = new ArrayList<>();
		
		if (srcPath == null || srcPath.compareTo("") == 0)
			return;
		
		File src = new File(srcPath);
		if (src == null)
			return;
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbFactory.newDocumentBuilder();
			Document doc = db.parse(src);
			
			doc.getDocumentElement().normalize();
			
			// Get words in week
			Node node = doc.getElementsByTagName("week").item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element week = (Element) node;
				int id = Integer.parseInt(week.getAttribute("id"));
				
				// week is up to date
				if (id == m_current_week) {					
					NodeList nodeList = week.getElementsByTagName("record");
					for (int i = 0; i < nodeList.getLength(); i++) {
						Element element = (Element) nodeList.item(i);
						
						String word = element.getElementsByTagName("word").item(0).getTextContent();
						int times = Integer.parseInt(element.getElementsByTagName("times").item(0).getTextContent());
						
						m_words_in_week.add(word);
						m_times_in_week.add(new Integer(times));
					}
				}
			}
			
			// Get words in month
			node = doc.getElementsByTagName("month").item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element month = (Element) node;
				int id = Integer.parseInt(month.getAttribute("id"));
				
				// month is up to date
				if (id == m_current_month) {
					NodeList nodeList = month.getElementsByTagName("record");
					for (int i = 0; i < nodeList.getLength(); i++) {
						Element element = (Element) nodeList.item(i);
						
						String word = element.getElementsByTagName("word").item(0).getTextContent();
						int times = Integer.parseInt(element.getElementsByTagName("times").item(0).getTextContent());
						
						m_words_in_month.add(word);
						m_times_in_month.add(new Integer(times));
					}
				}
			}
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

	public void addWord(String word) {
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		int month = cal.get(Calendar.MONTH);
		
		// Add word to week
		if (m_current_week != week) {
			m_current_week = week;
			
			m_words_in_week.clear();
			m_times_in_week.clear();
			
			m_words_in_week.add(word);
			m_times_in_week.add(new Integer(1));
		}
		else {
			int idx = m_words_in_week.indexOf(word);
			
			if (idx >= 0) {
				int times = m_times_in_week.get(idx).intValue();
				times++;
				
				m_times_in_week.set(idx, new Integer(times));
			}
			else {
				m_words_in_week.add(word);
				m_times_in_week.add(new Integer(1));
			}
		}
		
		// Add word to month
		if (m_current_month != month) {
			m_current_month = month;
			
			m_words_in_month.clear();
			m_times_in_month.clear();
			
			m_words_in_month.add(word);
			m_times_in_month.add(new Integer(1));
		}
		else {
			int idx = m_words_in_month.indexOf(word);
			
			if (idx >= 0) {
				int times = m_times_in_month.get(idx).intValue();
				times++;
				
				m_times_in_month.set(idx, new Integer(times));
			}
			else {
				m_words_in_month.add(word);
				m_times_in_month.add(new Integer(1));
			}
		}
	}

	public void save(String destPath) {
		if (m_words_in_week == null || m_words_in_week.size() == 0
				|| m_words_in_month == null || m_words_in_month.size() == 0
				|| destPath == null || destPath.compareTo("") == 0)
			return;
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbFactory.newDocumentBuilder();
			Document doc = db.newDocument();
			
			Calendar cal = Calendar.getInstance();
			String weekId = "" + cal.get(Calendar.WEEK_OF_YEAR);
			String monthId = "" + cal.get(Calendar.MONTH);
			
			// Create root
			Element root = doc.createElement("root");
			doc.appendChild(root);
			
			// Write week
			Element week = doc.createElement("week");
			Attr attr = doc.createAttribute("id");
			attr.setValue(weekId);
			week.setAttributeNode(attr);
			root.appendChild(week);
			
			for (int i = 0; i < m_words_in_week.size(); i++) {
				Element record = doc.createElement("record");
				
				Element word = doc.createElement("word");
				word.appendChild(doc.createTextNode(m_words_in_week.get(i)));
				record.appendChild(word);
				
				Element times = doc.createElement("times");
				times.appendChild(doc.createTextNode(m_times_in_week.get(i).toString()));
				record.appendChild(times);
				
				week.appendChild(record);
			}
			
			// Write month
			Element month = doc.createElement("month");
			attr = doc.createAttribute("id");
			attr.setValue(monthId);
			month.setAttributeNode(attr);
			root.appendChild(month);
			
			for (int i = 0; i < m_words_in_month.size(); i++) {
				Element record = doc.createElement("record");
				
				Element word = doc.createElement("word");
				word.appendChild(doc.createTextNode(m_words_in_month.get(i)));
				record.appendChild(word);
				
				Element times = doc.createElement("times");
				times.appendChild(doc.createTextNode(m_times_in_month.get(i).toString()));
				record.appendChild(times);
				
				month.appendChild(record);
			}
			
			// Transform to file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource domSource = new DOMSource(doc);
			
			File dest = new File(destPath);
			StreamResult streamResult = new StreamResult(dest);
			
			transformer.transform(domSource, streamResult);
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	public ArrayList<String> getWordsInWeek() {
		return m_words_in_week;
	}
	
	public ArrayList<Integer> getTimesInWeek() {
		return m_times_in_week;
	}
	
	public ArrayList<String> getWordsInMonth() {
		return m_words_in_month;
	}
	
	public ArrayList<Integer> getTimesInMonth() {
		return m_times_in_month;
	}
}
