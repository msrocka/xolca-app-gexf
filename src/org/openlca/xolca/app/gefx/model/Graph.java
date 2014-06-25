package org.openlca.xolca.app.gefx.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class Graph {

	@XmlAttribute(name = "defaultedgetype")
	private String defaultEdgeType = "undirected";
	
	@XmlAttribute(name="mode")
	private String mode = "static";
	
	@XmlElementWrapper(name="nodes", namespace="http://www.gexf.net/1.2draft")
	@XmlElement(name = "node", namespace="http://www.gexf.net/1.2draft")
	private List<Node> nodes = new ArrayList<>();
	
	@XmlElementWrapper(name="edges", namespace="http://www.gexf.net/1.2draft")
	@XmlElement(name = "edge", namespace="http://www.gexf.net/1.2draft")
	private List<Edge> edges = new ArrayList<>();

	public String getDefaultEdgeType() {
		return defaultEdgeType;
	}

	public void setDefaultEdgeType(String defaultEdgeType) {
		this.defaultEdgeType = defaultEdgeType;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}
	
}
