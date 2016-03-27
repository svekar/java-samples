package org.example.patterns.creational;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

public class SimpleFactoryTest {

	@Rule
	public final Timeout defaultTimeout = new Timeout(1000);
	@Rule
	public final ExpectedException expectedExc = ExpectedException.none();
	

	static interface Interface {
	}

	static interface SubInterface extends Interface {
	}
	
	static final class Impl implements Interface {
	}
	
	static final class NonInstantiableImpl implements Interface {

		private NonInstantiableImpl() {
		}
	}

	enum Singleton implements Interface {
		INSTANCE;
	}
	
	@Test
	public void testCreate() {
		System.clearProperty(Impl.class.getName());
		SimpleFactory factory = new SimpleFactory(Impl.class.getName());
		Interface result = factory.create();
		assertNotNull(result);
	}

	@Test
	public void testCreateWithCustomProp() {
		final String pn = "my.impl.class.prop";
		SimpleFactory factory = new SimpleFactory(pn);
		System.setProperty(pn, Impl.class.getName());
		Interface result = factory.create();
		assertNotNull(result);
	}

	private static final class MySimpleFactory extends SimpleFactory {

		public MySimpleFactory() {
			super(Impl.class.getName());
		}
		
	}
	
	@Test
	public void testCreateFailureNoClassFound() {
		SimpleFactory factory = new MySimpleFactory();
		System.setProperty(Impl.class.getName(), "no.such.class.Impl");
		expectedExc.expect(IllegalStateException.class);
		expectedExc.expectMessage("Can't load class object for impl. class: ");
		factory.create();
	}

	@Test
	public void testCreateAtIllegalAccessException() {
		SimpleFactory factory = new SimpleFactory(Impl.class.getName());
		System.setProperty(Impl.class.getName(), NonInstantiableImpl.class.getName());
		expectedExc.expect(IllegalArgumentException.class);
		expectedExc.expectMessage("Cant instantiate object of class: ");
		factory.create();
	}
	
	@Test
	public void testCreateAtInstantiationException() {
		SimpleFactory factory = new SimpleFactory(Impl.class.getName());
		System.setProperty(Impl.class.getName(), SubInterface.class.getName());
		expectedExc.expect(IllegalArgumentException.class);
		expectedExc.expectMessage("Cant instantiate object of class: ");
		factory.create();
	}
	
	@Test
	public void testCreateEnumSingleton() {
		SimpleFactory factory = new SimpleFactory(Singleton.class.getName());
		Interface result = factory.create();
		assertNotNull(result);
	}
	
}
