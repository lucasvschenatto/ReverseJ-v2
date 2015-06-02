package reverseJ;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;

public class AdapterSequenceToUML2 {
	private Context context;
	private Package rootPackage;

	protected AdapterSequenceToUML2(String packageName, Context context) {
		context = Context.getInstance();
		rootPackage = context.getModel().createNestedPackage(packageName);
	}

	public AdapterSequenceToUML2() {
		context = Context.getInstance();
		rootPackage = context.getModel().createNestedPackage("default");
	}

	public static AdapterSequenceToUML2 make(Context context) {
		return new AdapterSequenceToUML2("classDiagram", context);
	}

	public Package getPackage() {
		return rootPackage;
	}
//	public AdapterSequenceToUML2(){
//		lifelines = new LinkedList<Lifeline>();
//	}
//	public Lifeline getLifeline(String name) {
//		for (Lifeline lifeline : lifelines) {
//			if (lifeline.getName() == name)
//				return lifeline;
//		}
//		return null;
//	}
//
//	public boolean createLifeline(String name) {
//		if (context != null){
//			Lifeline newLifeline = UMLFactory.eINSTANCE.createLifeline();
////			newLifeline.setInteraction(context.getInteraction());
//			newLifeline.setName(name);
//			lifelines.add(newLifeline);
//			return true;
//		}else
//			return false;
//	}
//	public List<Lifeline> getLifelines() {
//		return lifelines;
//	}
}
