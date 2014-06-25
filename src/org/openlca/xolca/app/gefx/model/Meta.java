package org.openlca.xolca.app.gefx.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Meta {

	@XmlElement(name="creator", namespace="http://www.gexf.net/1.2draft")
	private String creator;
	
	@XmlElement(name="description", namespace="http://www.gexf.net/1.2draft")
	private String description;

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
