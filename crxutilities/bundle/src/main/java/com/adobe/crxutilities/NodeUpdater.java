package com.adobe.crxutilities;

import javax.jcr.Node;

public interface NodeUpdater {

	public String updateAllNodeProperties(Node node, String propertyName, String propertyValue);
	
}
