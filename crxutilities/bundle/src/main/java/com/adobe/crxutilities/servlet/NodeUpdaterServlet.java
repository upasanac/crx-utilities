package com.adobe.crxutilities.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.ServletException;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.crxutilities.NodeUpdater;


@SlingServlet( paths="/services/updatenode",methods="POST")
public class NodeUpdaterServlet extends SlingAllMethodsServlet{
	
	Logger log = LoggerFactory.getLogger(NodeUpdaterServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Reference
	private NodeUpdater nodeUpdater;
	
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)  throws ServletException, IOException {
		doPost(request,response);
    }
	
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		out.println("-------------------------------");
		log.info("Processing [NodeUpdaterServlet]...");
		out.println("Processing [NodeUpdaterServlet]...");
		out.println("-------------------------------");
		
		String nodeJcrPath = request.getParameter("path");
		String propertyName = request.getParameter("propertyName");
		String propertyValue = request.getParameter("propertyValue");	
		
		ResourceResolver resourceResolver = request.getResourceResolver();
		Resource resource = resourceResolver.getResource(nodeJcrPath);
		
		if(resource == null) {
			log.error("Invalid node path>> {} >>Terminating..", nodeJcrPath);
			out.println("Invalid node path>> "+nodeJcrPath + ">>Terminating");
		}
		else if(StringUtils.isBlank(propertyName)) {
			log.error("Invalid property name>> {} >> Terminating..", propertyName);
			out.println("Invalid property name>> "+ propertyName + ">>Terminating");
		}
		else {
			Node node = resource.adaptTo(Node.class);
			
			out.println(nodeUpdater.updateAllNodeProperties(node, propertyName, propertyValue));
			try{
			   resourceResolver.adaptTo(Session.class).save();
			}
			catch(Exception e) {
				log.error("[Exception] Error while saving the Node Properties");
				out.println("[Exception] Error while saving the Node Properties");
			}
		}
		out.println("-------------------------------");
		log.info("Finished updating the JCR Node properties");
		out.println("Finished updating the JCR Node properties");
	}
	
}
