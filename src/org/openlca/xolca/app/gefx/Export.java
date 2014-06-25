package org.openlca.xolca.app.gefx;

import java.io.File;
import java.util.Map;

import javax.xml.bind.JAXB;

import org.openlca.app.util.Labels;
import org.openlca.core.database.EntityCache;
import org.openlca.core.database.IDatabase;
import org.openlca.core.database.ProductSystemDao;
import org.openlca.core.matrix.LongIndex;
import org.openlca.core.model.ProcessLink;
import org.openlca.core.model.ProductSystem;
import org.openlca.core.model.descriptors.BaseDescriptor;
import org.openlca.core.model.descriptors.ProcessDescriptor;
import org.openlca.xolca.app.gefx.model.Edge;
import org.openlca.xolca.app.gefx.model.Gexf;
import org.openlca.xolca.app.gefx.model.Graph;
import org.openlca.xolca.app.gefx.model.Meta;
import org.openlca.xolca.app.gefx.model.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Export {

	private Logger log = LoggerFactory.getLogger(getClass());

	private final ProductSystemDao dao;
	private final EntityCache cache;
	private final File dir;

	public Export(IDatabase database, File dir, EntityCache cache) {
		this.dao = new ProductSystemDao(database);
		this.dir = dir;
		this.cache = cache;
	}

	public void doIt(BaseDescriptor descriptor) {
		if (descriptor == null)
			return;
		try {
			log.trace("export product system {}", descriptor);
			ProductSystem system = dao.getForId(descriptor.getId());
			Gexf gexf = mapModel(system);
			String fileName = system.getName().replaceAll("\\W+", "_")
					+ ".gexf";
			File file = new File(dir, fileName);
			JAXB.marshal(gexf, file);
		} catch (Exception e) {
			log.error("failed to export system " + descriptor, e);
		}
	}

	private Gexf mapModel(ProductSystem system) {
		Gexf gexf = new Gexf();
		Meta meta = new Meta();
		gexf.setMeta(meta);
		meta.setCreator("openLCA GEXF plugin");
		Graph graph = new Graph();
		gexf.setGraph(graph);
		graph.setDefaultEdgeType("directed");
		LongIndex processIndex = new LongIndex();
		createNodes(graph, processIndex, system);
		createEdges(graph, processIndex, system);
		return gexf;
	}

	private void createNodes(Graph graph, LongIndex processIndex,
			ProductSystem system) {
		Map<Long, ProcessDescriptor> map = cache.getAll(
				ProcessDescriptor.class, system.getProcesses());
		for (Long processId : system.getProcesses()) {
			ProcessDescriptor d = map.get(processId);
			if (d == null)
				continue;
			int id = processIndex.put(processId);
			Node node = new Node();
			graph.getNodes().add(node);
			node.setId(id);
			node.setLabel(Labels.getDisplayName(d));
		}
	}

	private void createEdges(Graph graph, LongIndex processIndex,
			ProductSystem system) {
		int edgeId = 1;
		for (ProcessLink link : system.getProcessLinks()) {
			int source = processIndex.getIndex(link.getProviderId());
			int target = processIndex.getIndex(link.getRecipientId());
			if (source < 0 || target < 0)
				continue;
			Edge edge = new Edge();
			graph.getEdges().add(edge);
			edge.setId(edgeId);
			edge.setSource(source);
			edge.setTarget(target);
			edgeId++;
		}
	}
}
