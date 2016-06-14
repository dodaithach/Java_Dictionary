package ddthach.homework02;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AppControl {
	public static final int c_eng_vi_mode = 1;
	public static final int c_vi_eng_mode = 2;
	
	private static final String c_eng_vi_dict = "Anh_Viet.xml";
	private static final String c_vi_eng_dict = "Viet_Anh.xml";
	private static final String c_eng_vi_fav = "Anh_Viet_Fav.xml";
	private static final String c_vi_eng_fav = "Viet_Anh_Fav.xml";
	private static final String c_statistic = "Statistic.xml";
	
	private String m_defaultPath;
	
	private int m_mode;
	private ArrayList<MyNode> m_eng_vi_dict;
	private ArrayList<MyNode> m_vi_eng_dict;
	
	private ArrayList<MyNode> m_eng_vi_fav;
	private ArrayList<MyNode> m_vi_eng_fav;
	
	private int m_current_item;
	
	private MyStatistic m_statistic;
	
	public AppControl() {
		m_defaultPath = System.getProperty("user.dir");
		
		m_mode = AppControl.c_eng_vi_mode;
		
		m_eng_vi_dict = new ArrayList<>();
		m_vi_eng_dict = new ArrayList<>();
		
		m_eng_vi_fav = new ArrayList<>();
		m_vi_eng_fav = new ArrayList<>();
		
		// Init dictionary
		this.initEngViDict(m_defaultPath + File.separator + c_eng_vi_dict);
		this.initViEngDict(m_defaultPath + File.separator + c_vi_eng_dict);
		
		// Init favorite list
		this.initDict(m_eng_vi_fav, m_defaultPath + File.separator + c_eng_vi_fav);
		this.initDict(m_vi_eng_fav, m_defaultPath + File.separator + c_vi_eng_fav);
		
		// Init statistic
		m_statistic = new MyStatistic(m_defaultPath + File.separator + c_statistic);
		
		m_current_item = -1;
	}
	
	//
	// Public functions
	//
	public boolean initEngViDict(String srcPath) {
		return this.initDict(m_eng_vi_dict, srcPath);
	}
	
	public boolean initViEngDict(String srcPath) {
		return this.initDict(m_vi_eng_dict, srcPath);
	}
	
	public boolean isEngViDictExist() {
		if (m_eng_vi_dict == null || m_eng_vi_dict.size() == 0)
			return false;
		
		return true;
	}
	
	public boolean isViEngDictExist() {
		if (m_vi_eng_dict == null || m_vi_eng_dict.size() == 0)
			return false;
		
		return true;
	}
	
	public ArrayList<MyNode> getDict() {
		ArrayList<MyNode> result = null;
		
		switch (m_mode) {
		case AppControl.c_eng_vi_mode:
			result = m_eng_vi_dict;
			break;
			
		case AppControl.c_vi_eng_mode:
			result = m_vi_eng_dict;
			break;
			
		default:
			break;
		}
		
		return result;
	}
	
	public void setMode(int mode) {
		m_mode = mode;
	}
	
	public String getMeaning(int idx) {
		String result = null;
		
		switch (m_mode) {
		case AppControl.c_eng_vi_mode:			
			if (idx >= 0 && idx < m_eng_vi_dict.size()) {
				result = m_eng_vi_dict.get(idx).getValue();
			}
			break;
			
		case AppControl.c_vi_eng_mode:
			if (idx >= 0 && idx < m_vi_eng_dict.size()) {
				result = m_vi_eng_dict.get(idx).getValue();
			}
			break;
			
		default:
			break;
		}
		
		return result;
	}
	
	public String getMeaning(String key) {
		String result = null;
		
		switch (m_mode) {
		case AppControl.c_eng_vi_mode:
			result = this.binaryLookup(key, m_eng_vi_dict);
			break;
			
		case AppControl.c_vi_eng_mode:
			result = this.binaryLookup(key, m_vi_eng_dict);
			break;
			
		default:
			break;
		}
		
		return result;
	}
	
	public String getKey(int idx) {
		String result = null;
		
		switch (m_mode) {
		case AppControl.c_eng_vi_mode:			
			if (idx >= 0 && idx < m_eng_vi_dict.size()) {
				result = m_eng_vi_dict.get(idx).getKey();
			}
			break;
			
		case AppControl.c_vi_eng_mode:
			if (idx >= 0 && idx < m_vi_eng_dict.size()) {
				result = m_vi_eng_dict.get(idx).getKey();
			}
			break;
			
		default:
			break;
		}
		
		return result;
	}
	
	public void setCurrentItem(int idx) {
		if (idx >= 0)
			m_current_item = idx;
	}
	
	public int getCurrentItem() {
		return m_current_item;
	}
	
	public boolean addItemToFav(MyNode item) {
		if (item == null)
			return false;
		
		switch (m_mode) {
		case AppControl.c_eng_vi_mode:
			boolean isExist1 = m_eng_vi_fav.contains(item);
			
			if (m_eng_vi_fav != null && !isExist1) {
				m_eng_vi_fav.add(item);
				return true;
			}
			break;
			
		case AppControl.c_vi_eng_mode:
			boolean isExist2 = m_vi_eng_fav.contains(item);
			
			if (m_vi_eng_fav != null && !isExist2) {
				m_vi_eng_fav.add(item);
				return true;
			}
			break;
			
		default:
			break;
		}
		
		return false;
	}
	
	public boolean addCurrentItemToFav() {
		if (m_current_item < 0)
			return false;
		
		switch (m_mode) {
		case AppControl.c_eng_vi_mode:
			if (m_current_item < m_eng_vi_dict.size()) {
				// Test if current item has been added yet
				MyNode item = m_eng_vi_dict.get(m_current_item);
				
				if (m_eng_vi_fav != null && !m_eng_vi_fav.contains(item)) {
					m_eng_vi_fav.add(item);
					return true;
				}
			}
			break;
			
		case AppControl.c_vi_eng_mode:
			if (m_current_item < m_vi_eng_dict.size()) {
				// Test if current item has been added yet
				MyNode item = m_vi_eng_dict.get(m_current_item);
				
				if (m_vi_eng_fav != null && !m_vi_eng_fav.contains(item)) {
					m_vi_eng_fav.add(item);
					return true;
				}
			}
			break;
			
		default:
			break;
		}
		
		return false;
	}
	
	public ArrayList<MyNode> getEngViFavList() {
		return m_eng_vi_fav;
	}
	
	public ArrayList<MyNode> getViEngFavList() {
		return m_vi_eng_fav;
	}

	public void save() {
		this.saveDict(m_eng_vi_fav, m_defaultPath + File.separator + c_eng_vi_fav);
		this.saveDict(m_vi_eng_fav, m_defaultPath + File.separator + c_vi_eng_fav);
		
		m_statistic.save(m_defaultPath + File.separator + c_statistic);
	}
	
	//
	// Statistic functions
	//
	public MyStatistic getStatistic() {
		return m_statistic;
	}
	
	public void addWordToStatistic(String word) {
		m_statistic.addWord(word);
	}
	
	//
	// Private functions
	//
	private boolean initDict(ArrayList<MyNode> dict, String srcPath) {
		if (dict == null || srcPath == null || srcPath.compareTo("") == 0)
			return false;
		
		dict.clear();
		
		try {
			File xmlFile = new File(srcPath);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = db.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("record");
			for (int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					
					String key = element.getElementsByTagName("word").item(0).getTextContent();
					String value = element.getElementsByTagName("meaning").item(0).getTextContent();
					
					MyNode myNode = new MyNode(key, value);
					if (myNode != null)
						dict.add(myNode);
				}
			}
			
			// Sort dict
			Collections.sort(dict);
			
			return true;
		}
		catch (Exception ex) {
			System.err.print(ex.getMessage());
			return false;
		}
	}

	private boolean saveDict(ArrayList<MyNode> dict, String destPath) {
		if (dict == null || dict.size() == 0 || destPath == null || destPath.compareTo("") == 0)
			return false;
		
		try {
			// 1. Init doc
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = db.newDocument();
			
			Element root = doc.createElement("dictionary");
			doc.appendChild(root);
			
			for (int i = 0; i < dict.size(); i++) {
				Element record = doc.createElement("record");
				
				Element word = doc.createElement("word");
				word.appendChild(doc.createTextNode(dict.get(i).getKey()));
				record.appendChild(word);
				
				Element meaning = doc.createElement("meaning");
				meaning.appendChild(doc.createTextNode(dict.get(i).getValue()));
				record.appendChild(meaning);
				
				root.appendChild(record);
			}
			
			// 2. Transform doc to file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			DOMSource domSource = new DOMSource(doc);
			
			File dest = new File(destPath);
			StreamResult streamResult = new StreamResult(dest);
			
			transformer.transform(domSource, streamResult);
			
			return true;
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
			return false;
		}
	}
	
	private String binaryLookup(String key, ArrayList<MyNode> dict) {
		String result = null;
		
		if (dict == null || dict.size() == 0)
			return result;
		
		int left = 0, right = dict.size() - 1;
		while (left <= right) {
			int mid = (left + right) / 2;
			int compare = dict.get(mid).getKey().compareTo(key);
			
			if (compare == 0) {
				result = dict.get(mid).getValue();
				return result;
			}
			else if (compare > 0) {
				right = mid - 1;
			}
			else {
				left = mid + 1;
			}
		}
		
		return result;
	}
}
