package edu.pitt.cs;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.never;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object

	@Before
	public void setUp() throws Exception {
		// Turn on automatic bug injection in the Cat class, to emulate a buggy Cat.
		// Your unit tests should work regardless of these bugs.
		Cat.bugInjectionOn = true;

		// INITIALIZE THE TEST FIXTURE
		// 1. Create a new RentACat object and assign to r
		r = RentACat.createInstance();

		// 2. Create an unrented Cat with ID 1 and name "Jennyanydots", assign to c1
		// TODO: Fill in
		c1 = Mockito.mock(Cat.class);
		Mockito.when(c1.getId()).thenReturn(1);
		Mockito.when(c1.getName()).thenReturn("Jennyanydots");
		Mockito.when(c1.toString()).thenReturn("ID 1. Jennyanydots");

		// 3. Create an unrented Cat with ID 2 and name "Old Deuteronomy", assign to c2
		// TODO: Fill in
		c2 = Mockito.mock(Cat.class);
		Mockito.when(c2.getId()).thenReturn(2);
		Mockito.when(c2.getName()).thenReturn("Old Deuteronomy");
		Mockito.when(c2.toString()).thenReturn("ID 2. Old Deuteronomy");

		// 4. Create an unrented Cat with ID 3 and name "Mistoffelees", assign to c3
		// TODO: Fill in
		c3 = Mockito.mock(Cat.class);
		Mockito.when(c3.getId()).thenReturn(3);
		Mockito.when(c3.getName()).thenReturn("Mistoffelees");
		Mockito.when(c3.toString()).thenReturn("ID 3. Mistoffelees");
		
	}

	@After
	public void tearDown() throws Exception {
		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 * </pre>
	 */

	@Test
	public void testGetCatNullNumCats0() {
		Cat cat = r.getCat(2);
		assertNull("Cat is not null", cat);
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 */

	@Test
	public void testGetCatNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		Cat cat = r.getCat(2);

		assertNotNull("Return cat is null", cat);

		assertEquals("Return cat does not have id 2", 2, cat.getId());
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatAvailableFalseNumCats0() {
		boolean hasCat = r.catAvailable(2);
		assertFalse("Cat is available even though r has no cats.", hasCat);
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c3 is rented.
	 *                c1 and c2 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is true.
	 * </pre>
	 */

	@Test
	public void testCatAvailableTrueNumCats3() {
		// Preconditions: c1, c2, and c3 are added to r using addCat(Cat c) and c3 is rented.
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		Mockito.when(c1.getRented()).thenReturn(false);
		Mockito.when(c2.getRented()).thenReturn(false);

		//Rent cat c3
		Mockito.when(c3.getRented()).thenReturn(true);

		// Execution steps: Call catAvailable(2)
		boolean isCatAvailable = r.catAvailable(2);
		
		//Postconditions
		assertTrue("Cat 2 is unavailbe although it has not been rented.", isCatAvailable);
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 *                c1 and c3 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatAvailableFalseNumCats3() {
		// Preconditions: c1, c2, and c3 are added to r using addCat(Cat c) and c2 is rented.
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		Mockito.when(c1.getRented()).thenReturn(false);
		Mockito.when(c3.getRented()).thenReturn(false);

		//Rent cat c2
		Mockito.when(c2.getRented()).thenReturn(true);

		// Execution steps: Call catAvailable(2)
		boolean isCatAvailable = r.catAvailable(2);

		//Postconditions
		assertFalse("Cat 2 is available although it has been rented.", isCatAvailable);
	}

	/**
	 * Test case for boolean catExists(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatExistsFalseNumCats0() {
		//Execution Steps
		boolean doesCatExist = r.catExists(2);

		//Postconditions
		assertFalse("Cat exists when it has not been added.", doesCatExist);
	}

	/**
	 * Test case for boolean catExists(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is true.
	 * </pre>
	 */

	@Test
	public void testCatExistsTrueNumCats3() {
		// Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		//Execution Steps:
		boolean doesCatExist = r.catExists(2);
		
		assertTrue("Cat 2 does not exist even though it has been added", doesCatExist);
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */

	@Test
	public void testListCatsNumCats0() {
		//Execution Steps:
		String listOfCats = r.listCats();

		assertEquals("", listOfCats);
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */

	@Test
	public void testListCatsNumCats3() {
		// Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		
		//Execution Steps:
		String listOfCats = r.listCats();
		System.out.println(listOfCats);

		assertEquals("ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n", listOfCats);
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testRentCatFailureNumCats0() {
		// Execution steps: Call rentCat(2)
		boolean isCatRented = r.rentCat(2);

		assertFalse("Cat is considered rented although it is not.", isCatRented);
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c1.rentCat(), c2.rentCat(), c3.rentCat() are never called.
	 * </pre>
	 * 
	 * Hint: See sample_code/mockito_example/NoogieTest.java in the course
	 * repository for an example of behavior verification. Refer to the
	 * testBadgerPlayCalled method.
	 */

	@Test
	public void testRentCatFailureNumCats3() {
		// Preconditions: c1, c2, and c3 are added to r using addCat(Cat c) and c2 is rented.
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		Mockito.when(c2.getRented()).thenReturn(true);

		// Execution steps: Call rentCat(2)
		boolean isCatRented = r.rentCat(2);
		Mockito.verify(c1, never()).rentCat();
		Mockito.verify(c2, never()).rentCat();
		Mockito.verify(c3, never()).rentCat();

		assertFalse("Cat was rented although it was already previously rented.", isCatRented);
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testReturnCatFailureNumCats0() {
		//Execution Steps:
		boolean getCat2 = r.returnCat(2);

		assertFalse("Cat with id 2 is shown to be rented when it was not", getCat2);
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2.returnCat() is called exactly once.
	 *                 c1.returnCat() and c3.returnCat are never called.
	 * </pre>
	 * 
	 * Hint: See sample_code/mockito_example/NoogieTest.java in the course
	 * repository for an example of behavior verification. Refer to the
	 * testBadgerPlayCalled method.
	 */

	@Test
	public void testReturnCatNumCats3() {
		// Preconditions: c1, c2, and c3 are added to r using addCat(Cat c). c2 is rented.
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		Mockito.when(c2.getRented()).thenReturn(true);

		// Execution steps: Call returnCat(2)
		boolean didReturn = r.returnCat(2);
		Mockito.verify(c2).returnCat();
		Mockito.verify(c1, never()).returnCat();
		Mockito.verify(c3, never()).returnCat();
		assertTrue("Cat was not successfully returned.", didReturn);
	}
}
