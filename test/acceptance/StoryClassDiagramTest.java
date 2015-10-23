package acceptance;

import java.util.LinkedList;
import java.util.List;

import acceptance.story.Story;
import reversej.MakerAndSaver;
import reversej.diagram.DiagramHandler;
import reversej.diagram.DiagramStrategy;
import reversej.diagram.RepositoryProvider;
import reversej.diagram.strategies.ClassDiagram;
import reversej.repository.RepositoryInformation;
import reversej.tracer.RepositoryRecorder;
import reversej.tracer.Tracer;

public class StoryClassDiagramTest {
	public static void main(String[] args) {
		RepositoryInformation i = new RepositoryInformation();
		RepositoryRecorder r = i;
		RepositoryProvider p = i;
		DiagramStrategy dS = new ClassDiagram();
		List<DiagramStrategy> lds = new LinkedList<DiagramStrategy>();
		lds.add(dS);
		DiagramHandler dM = new MakerAndSaver(p, lds);
		String fileName = Thread.currentThread().getStackTrace()[1].getFileName();
		((MakerAndSaver)dM).setFileName(fileName);
		Tracer.start(r);
		
		Story s = new Story();
		s.tellStory();
		
		Tracer.stop();
		dM.make();
	}
}
