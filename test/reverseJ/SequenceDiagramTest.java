package reverseJ;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PrimitiveType;
import org.junit.*;
public class SequenceDiagramTest {
	public static class GeneralTests extends AdapterClassToUml2 {
		private DiagramStrategy strategy;

		@Test
		public void constructorSetsUtilities() {
			AdapterSequenceToUml2 expected = AdapterSequenceToUml2
					.make(null);
			strategy = new SequenceDiagram(expected);
			AdapterToUml2 actual = strategy.getUtil();

			assertEquals(expected, actual);
		}
	}

//	public static class CreateClass extends AdapterClassToUML2 {
//		private DiagramStrategy strategy;
//		private List<String> createdClasses;
//
//		private void assertClassCreated(String className) {
//			assertTrue(createdClasses.contains(className));
//		}
//
//		private void assertNumberOfCreatedClasses(int number) {
//			assertEquals(number, createdClasses.size());
//		}
//
//		@Override
//		public org.eclipse.uml2.uml.Class createConcreteClass(String name) {
//			createdClasses.add(name);
//			return null;
//		}
//
//		@Before
//		public void setup() {
//			strategy = new ClassDiagram(this);
//			createdClasses = new LinkedList<String>();
//		}
//
//		@Test
//		public void ifHasNoClass_DoNotCreate() {
//			List<Information> informations = new LinkedList<Information>();
//
//			strategy.generate(informations);
//
//			assertNumberOfCreatedClasses(0);
//		}
//
//		@Test
//		public void CreateConcreteClass() {
//			String className = "myTestClass";
//			Information info = InformationFactory.createClass(className);
//			List<Information> informations = new LinkedList<Information>();
//			informations.add(info);
//
//			strategy.generate(informations);
//
//			assertClassCreated(className);
//		}
//
//		@Test
//		public void CreateConcreteClassForHandler() {
//			String className = "myTestClassTarget";
//			Information info = InformationFactory.createHandler(className);
//			List<Information> informations = new LinkedList<Information>();
//			informations.add(info);
//
//			strategy.generate(informations);
//
//			assertClassCreated(className);
//		}
//
//		@Test
//		public void doesntDuplicateClasses() {
//			List<Information> informations = completeMethodTrace("001");
//			informations.addAll(completeMethodTrace("001"));
//			informations.addAll(completeMethodTrace("001"));
//			informations.addAll(completeMethodTrace("001"));
//			informations.addAll(completeMethodTrace("001"));
//
//			strategy.generate(informations);
//
//			assertNumberOfCreatedClasses(1);
//		}
//
//		@Test
//		public void doesntDeleteNotDuplicatedClasses() {
//			List<Information> informations = completeMethodTrace("001");
//			informations.addAll(completeMethodTrace("002"));
//
//			strategy.generate(informations);
//
//			assertClassCreated("Class001");
//			assertClassCreated("Class002");
//		}
//	}
}
