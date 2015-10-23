package reversej.diagram;

import java.util.List;

public class DiagramHandler {
	protected RepositoryProvider infoProvider;
	protected List<DiagramStrategy> strategies;
	
	public DiagramHandler(RepositoryProvider infoProvider, List<DiagramStrategy> diagrams) {
		this.infoProvider = infoProvider;
		this.strategies = diagrams;
	}

	public Diagram make() {
		for (DiagramStrategy diagram : strategies)
			diagram.generate(infoProvider.getAll());
		return Diagram.getInstance();
	}

	public RepositoryProvider getProvider() {
		return infoProvider;
	}

	public List<DiagramStrategy> getDiagramStrategies() {
		return strategies;
	}
}