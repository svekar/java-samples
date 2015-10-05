package org.example.test;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Check that all generated classes override Object's methods equals, hashCode
 * and toString.
 * 
 * @author Sven-Jørgen Karlsen
 *
 */
public final class GeneratedCodeTest {

	private static final void assertThatClassOverridesMethods(Class<?> cls)
			throws Throwable {
		assertThatClassOverridesMethod(cls, "equals", Object.class);
		assertThatClassOverridesMethod(cls, "hashCode");
		assertThatClassOverridesMethod(cls, "toString");
	}

	private static Collection<File> listFiles(File dir, File path) {
		Set<File> fileTree = new HashSet<File>();
		for (File entry : dir.listFiles()) {
			if (entry.isFile()) {
				if (entry.getName().endsWith(".java")
						&& !"package-info.java".equals(entry.getName())
						&& !"ObjectFactory.java".equals(entry.getName())) {
					fileTree.add(new File(path, entry.getName()));
				}
			} else {
				fileTree.addAll(
						listFiles(entry, new File(path, entry.getName())));
			}
		}
		return fileTree;
	}

	private static void assertThatClassOverridesMethod(Class<?> cls,
		String methodName, Class<?>... paramTypes)
				throws NoSuchMethodException {
		Method method = cls.getMethod(methodName, paramTypes);
		assertEquals(
				String.format("Generated class %s doesn't override Object.%s:",
						cls, methodName),
				cls, method.getDeclaringClass());
	}

	@Test
	public final void checkAllGeneratedClasses() throws Throwable {
		Collection<File> files = listFiles(
				new File("target/generated-sources/xjc"), new File(""));
		for (File file : files) {
			String fn = file.toString();
			fn = fn.replaceAll("/|\\\\", ".").substring(1,
					fn.length() - ".java".length());
			Class<?> cls = Class.forName(fn);
			assertThatClassOverridesMethods(cls);
		}
	}

}
