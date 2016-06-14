package ddthach.homework02;

public class MyNode implements Comparable {
	private String m_key;
	private String m_value;
	
	public MyNode() {
		m_key = null;
		m_value = null;
	}
	
	public MyNode(String key, String value) {
		m_key = key;
		m_value = value;
	}
	
	public String getKey() {
		return m_key;
	}
	
	public void setKey(String key) {
		m_key = key;
	}
	
	public String getValue() {
		return m_value;
	}
	
	public void setValue(String value) {
		m_value = value;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		MyNode obj = (MyNode) o;
		return this.getKey().compareTo(obj.getKey());
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		MyNode node = (MyNode) obj;
		return this.getKey().compareTo(node.getKey()) == 0;
	}
}
