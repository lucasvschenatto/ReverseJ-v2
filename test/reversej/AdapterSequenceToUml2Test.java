package reversej;

import static org.junit.Assert.*;
import static reversej.TestUtilities.*;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.junit.*;

import reversej.diagram.Diagram;
import reversej.diagram.strategies.uml2adapter.AdapterClassToUml2;
import reversej.diagram.strategies.uml2adapter.AdapterSequenceToUml2;

public class AdapterSequenceToUml2Test {
	protected AdapterSequenceToUml2 adapter;
	@Before
	public void setup(){
		Diagram.resetInstance();
		adapter = AdapterSequenceToUml2.make();
	}
	public static class General extends AdapterSequenceToUml2Test{
		@Test
		public void testMake() {
			adapter = AdapterSequenceToUml2.make();
			assertNotNull(adapter);
		}
		@Test
		public void getPackage(){
			Package p = adapter.getPackage();
			assertNotNull(p);
		}
		@Test
		public void packageNameIsSequenceDiagram(){
			String actual = adapter.getPackage().getName();
			String expected = AdapterSequenceToUml2.PACKAGE_NAME;
			assertEquals(expected, actual);
		}
		@Test
		public void getContext(){
			Diagram c = adapter.getContext();
			assertNotNull(c);
		}
		@Test
		public void getInteraction(){
			NamedElement i = adapter.getInteraction();
			
			assertNotNull(i);
		}
		@Test
		public void interactionNameIsSetToDiagramType(){
			NamedElement actual = adapter.getInteraction();
			assertEquals(AdapterSequenceToUml2.DIAGRAM_TYPE, actual.getName());
		}
		@Test
		public void bindsInteractionToPackage(){			
			NamedElement actual = adapter.getPackage().getOwnedMember(AdapterSequenceToUml2.DIAGRAM_TYPE);
			assertNotNull(actual);
			assertTrue(actual instanceof org.eclipse.uml2.uml.Interaction);
		}
	}
	public static class LinkToClassPackage extends AdapterSequenceToUml2Test{
		AdapterClassToUml2 classAdapter;
		@Override@Before
		public void setup(){
			Diagram.resetInstance();
			classAdapter = AdapterClassToUml2.make();
			adapter = AdapterSequenceToUml2.make();
		}
		@Test
		public void importsClassPackage(){
			Package classPackage = classAdapter.getPackage();
//			Package classPackage = (Package)context.getModel().getPackagedElement(AdapterClassToUml2.PACKAGE_NAME);
			List<Package> found = new LinkedList<Package>();
			for(PackageImport p : adapter.getPackage().getPackageImports()){
				found.add(p.getImportedPackage());
			}
			assertListContains(found, classPackage);
		}
		@Test
		public void Lifeline_RepresentsCorrespondingClass(){
			String name = "myClass";
			Class expected = classAdapter.createConcreteClass(name);
			Lifeline lifeline = adapter.createLifeline(name);
			Class actual = (Class)lifeline.getRepresents().getType();
			assertEquals(expected,actual);
		}
		@Test
		public void whenThereIsNoClass_Represents_nothing(){
			String name = "nothing";
			Lifeline lifeline = adapter.createLifeline(name);
			ConnectableElement actual = lifeline.getRepresents();
			assertNull(actual);
		}
	}
	public static class CreateLifeline extends AdapterSequenceToUml2Test{
		@Test
		public void createLifeline_ReturnsLifeline(){
			Lifeline actual = adapter.createLifeline(null);			
			assertNotNull(actual);
		}
		@Test
		public void lifelineNameIsSet(){
			String name = "lifelineTest";
			
			Lifeline actual = adapter.createLifeline(name);
			
			assertEquals(name, actual.getName());
		}
		@Test
		public void bindsLifelineToInteraction(){
			String name = "car";
			
			Lifeline expected = adapter.createLifeline(name);
			
			Lifeline actual = adapter.getInteraction().getLifeline(name);
			assertEquals(expected,actual);
		}
		@Test
		public void createThreeLifelines(){
			String one = "lifelineOne";
			String two = "lifelineTwo";
			String three = "lifelineThree";		
			
			adapter.createLifeline(one);
			adapter.createLifeline(two);
			adapter.createLifeline(three);
			
			assertEquals(one,   adapter.getInteraction().getLifeline(one).getName());
			assertEquals(two,   adapter.getInteraction().getLifeline(two).getName());
			assertEquals(three, adapter.getInteraction().getLifeline(three).getName());
		}
	}
	public static class CreateMessage extends AdapterSequenceToUml2Test{
		@Test
		public void createMessage_ReturnsMessage(){
			Message actual = adapter.createMessage(null, null, null);			
			assertNotNull(actual);
		}
		@Test
		public void messageNameIsSet(){
			String name = "Hello my friend";
			Message actual = adapter.createMessage(null, name, null);
			assertEquals(name,actual.getName());
		}
		@Test
		public void messageHasCaller(){
			Message actual = adapter.createMessage(null, null, null);
			assertNotNull(actual.getSendEvent());
		}
		@Test
		public void sendEventIsCoveredBySender(){
			String sender = "aSender";
			Lifeline expected = adapter.createLifeline(sender);
			Message m = adapter.createMessage(sender, null, null);
			Lifeline actual = ((MessageOccurrenceSpecification)m.getSendEvent()).getCovered();
			assertEquals(expected,actual);
		}
		@Test
		public void sendEventHasEnclosingInteraction(){
			String sender = "aSender";
			adapter.createLifeline(sender);
			Message m = adapter.createMessage(sender, null, null);
			Interaction expected = adapter.getInteraction();
			Interaction actual = ((MessageOccurrenceSpecification)m.getSendEvent()).getEnclosingInteraction();
			assertEquals(expected,actual);
		}
		@Test
		public void sendEventHasMessageSet(){
			String sender = "aSender";
			adapter.createLifeline(sender);
			Message expected = adapter.createMessage(sender, null, null);
			Message actual = ((MessageOccurrenceSpecification)expected.getSendEvent()).getMessage();
			assertEquals(expected,actual);
		}
		@Test
		public void sendEventHasNameSet(){
			String sender = "aSender";
			String messageName = "Hello";
			String expected = sender + AdapterSequenceToUml2.SEPARATOR + messageName;
			
			adapter.createLifeline(sender);
			Message m = adapter.createMessage(sender, messageName, null);
			
			String actual = ((MessageOccurrenceSpecification)m.getSendEvent()).getName();
			assertEquals(expected,actual);
		}
		@Test
		public void messageHasReceiver(){
			Message actual = adapter.createMessage(null, null, null);
			assertNotNull(actual.getReceiveEvent());
		}
		@Test
		public void receiveEventIsCoveredBySender(){
			String receiver = "aReceiver";
			Lifeline expected = adapter.createLifeline(receiver);
			Message m = adapter.createMessage(null, null, receiver);
			Lifeline actual = ((MessageOccurrenceSpecification)m.getReceiveEvent()).getCovered();
			assertEquals(expected,actual);
		}
		@Test
		public void receiveEventHasEnclosingInteraction(){
			String receiver = "aReceiver";
			adapter.createLifeline(receiver);
			Message m = adapter.createMessage(null, null, receiver);
			Interaction expected = adapter.getInteraction();
			Interaction actual = ((MessageOccurrenceSpecification)m.getReceiveEvent()).getEnclosingInteraction();
			assertEquals(expected,actual);
		}
		@Test
		public void receiveEventHasMessageSet(){
			String receiver = "aReceiver";
			adapter.createLifeline(receiver);
			Message expected = adapter.createMessage(null, null, receiver);
			Message actual = ((MessageOccurrenceSpecification)expected.getReceiveEvent()).getMessage();
			assertEquals(expected,actual);
		}
		@Test
		public void receiveEventHasNameSet(){
			String receiver = "aReceiver";
			String messageName = "Hello";
			String expected = receiver + AdapterSequenceToUml2.SEPARATOR + messageName;
			
			adapter.createLifeline(receiver);
			Message m = adapter.createMessage(null, messageName, receiver);
			
			String actual = ((MessageOccurrenceSpecification)m.getReceiveEvent()).getName();
			assertEquals(expected,actual);
		}
	}
	public static class ExecutionSpecification{
		@Test
		public void createExecutionSpecificationWhenReceiveAMessage(){
			
		}
		
	}
}
