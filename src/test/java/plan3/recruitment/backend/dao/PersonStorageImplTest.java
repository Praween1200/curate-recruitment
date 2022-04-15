package plan3.recruitment.backend.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.model.PersonStorage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonStorageImplTest {

	private static PersonStorage storage;

	@BeforeEach
	void setUp() {
		storage = new PersonStorageImpl();
	}

	@Test
	@Order(1)
	public void listTest() {
		final Person stefan = Person.valueOf("Stefan", "Petersson", "stefan@example.com");
		final Person markus = Person.valueOf("Markus", "Gustavsson", "markus@example.com");
		final Person ian = Person.valueOf("Ian", "Vännman", "ian@example.com");
		final Person marten = Person.valueOf("Mårten", "Gustafson", "marten@example.com");

		final List<Person> actualPersons = storage.list();
		final List<Person> expectedPersons = List.of(marten, markus, stefan, ian);
		assertEquals(expectedPersons.toString(), actualPersons.toString());
	}

	@Test
	@Order(2)
	public void fetchTest() {
		Optional<Person> actualPerson = storage.fetch("marten@example.com");
		final Person expectedPerson = Person.valueOf("Mårten", "Gustafson", "marten@example.com");
		assertEquals(expectedPerson.toString(), actualPerson.get().toString());
	}

	@Test
	@Order(3)
	public void saveSuccessTest() {
		final Person payload = Person.valueOf("Praween", "Kumar", "praween.kmr@example.com");
		storage.save(payload);
		final List<Person> actualPersons = storage.list();
		assertEquals(5, actualPersons.size());
	}

	@Test
	@Order(4)
	public void deleteSuccessTest() {
		final Person payload = Person.valueOf("Praween", "Kumar", "praween.kmr@example.com");
		boolean removeResult = storage.remove(payload);
		assertEquals(true, removeResult);
	}

	@Test
	@Order(5)
	public void unsuccessfulDeletetionTest() {
		final Person payload = Person.valueOf("test", "test", "testSample@example.com");
		boolean removeResult = storage.remove(payload);
		assertEquals(false, removeResult);
	}
}
