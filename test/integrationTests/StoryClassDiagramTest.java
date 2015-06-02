package integrationTests;

import java.util.LinkedList;
import java.util.List;

import reverseJ.ClassDiagram;
import reverseJ.AdapterClassToUML2;
import reverseJ.DiagramMaker;
import reverseJ.DiagramStrategy;
import reverseJ.InformationProvider;
import reverseJ.InformationStorageProvider;
import reverseJ.MakerAndSaver;
import reverseJ.RecorderStorage;
import reverseJ.Tracer;
import StoryPackage.Story;

public class StoryClassDiagramTest {
	public static void main(String[] args) {
		InformationStorageProvider i = new InformationStorageProvider();
		RecorderStorage r = i;
		InformationProvider p = i;
		AdapterClassToUML2 cDFA = new AdapterClassToUML2();
		DiagramStrategy dS = new ClassDiagram(cDFA);
		List<DiagramStrategy> lds = new LinkedList<DiagramStrategy>();
		lds.add(dS);
		DiagramMaker dM = new MakerAndSaver(p, lds);
		
		Tracer.start(r);
		
		Story s = new Story();
		s.tellStory();
		
		Tracer.stop();
		for (String info : r.describeAll()) {
			System.out.println(info);
		}
		dM.make();
	}
}