package acceptance;

import java.util.LinkedList;
import java.util.List;

import acceptance.story.Story;
import reversej.MakerAndSaver;
import reversej.diagram.DiagramEngine;
import reversej.diagram.DiagramStrategy;
import reversej.diagram.RepositoryProvider;
import reversej.diagram.strategies.ClassDiagram;
import reversej.diagram.strategies.SequenceDiagram;
import reversej.information.InformationFactory;
import reversej.information.impl.InformationFactoryImpl;
import reversej.repository.RepositoryInMemory;
import reversej.tracer.RepositoryRecorder;
import reversej.tracer.Tracer;

public class StoryClassAndSequenceDiagramTest {
	public static void main(String[] args) {
		RepositoryInMemory i = new RepositoryInMemory();
		RepositoryRecorder r = i;
		RepositoryProvider p = i;
		InformationFactory f = new InformationFactoryImpl();
		List<DiagramStrategy> lds = new LinkedList<DiagramStrategy>();
		lds.add(new SequenceDiagram());
		lds.add(new ClassDiagram());
		DiagramEngine dM = new MakerAndSaver(p, f, lds);
		String fileName = Thread.currentThread().getStackTrace()[1].getFileName();
		((MakerAndSaver)dM).setFileName(fileName);
		Tracer.start(r);
		
		Story s = new Story();
		s.tellStory();
		
		Tracer.stop();
		dM.make();
	}
}
