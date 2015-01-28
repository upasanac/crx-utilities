package com.adobe.crxutilities.impl;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.crxutilities.NodeUpdater;

@Component(metatype = true)
@Service(NodeUpdater.class)
public class NodeUpdaterImpl implements NodeUpdater {

	Logger log = LoggerFactory.getLogger(NodeUpdater.class);

	StringBuilder sb = new StringBuilder();
	
	@Override
	public String updateAllNodeProperties(Node node, String propertyName,
			String propertyValue) {
		try {
			node.setProperty(propertyName, propertyValue);
			sb.append("\nSet/Updated property ["+ propertyName+":"+ propertyValue +"] at >>"+ node.getPath());
			sb.append("\n**********************************************");
			log.info("\nSet/Updated property ["+ propertyName+":"+ propertyValue +"] at >>"+ node.getPath());	
			NodeIterator nodeIterator = node.getNodes();

			while (nodeIterator.hasNext()) {
				Node childNode = (Node) nodeIterator.next();
				updateAllNodeProperties(childNode, propertyName, propertyValue);
			}

		} catch (RepositoryException e) {
			log.error("[RepositoryException] Exception while updating the Node property");
			sb.append("\n[RepositoryException] Exception while updating the Node property");
		}
		
		return sb.toString();
	}

}
