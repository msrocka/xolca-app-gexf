package org.openlca.xolca.app.gefx.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="gefx", namespace="http://www.gexf.net/1.2draft")
@XmlAccessorType(XmlAccessType.FIELD)
public class Gexf {

	@XmlAttribute(name = "version")
	private String version = "1.2";
	
	@XmlElement(name="meta", namespace="http://www.gexf.net/1.2draft")
	private Meta meta;
	
	@XmlElement(name = "graph", namespace="http://www.gexf.net/1.2draft")
	private Graph graph;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
}
