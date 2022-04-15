package plan3.recruitment.backend.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import plan3.recruitment.backend.dao.PersonStorageImpl;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.model.PersonStorage;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonsResourceTest {

	private static PersonStorage storage = new PersonStorageImpl();
	private static final ResourceExtension RESOURCE_EXTENSION = ResourceExtension.builder()
			.addResource(new PersonResource(storage)).build();

	@Test
	@Order(1)
	public void listSortedOnLastname() {
		final Person stefan = Person.valueOf("Stefan", "Petersson", "stefan@example.com");
		final Person markus = Person.valueOf("Markus", "Gustavsson", "markus@example.com");
		final Person ian = Person.valueOf("Ian", "Vännman", "ian@example.com");
		final Person marten = Person.valueOf("Mårten", "Gustafson", "marten@example.com");

		final List<Person> actualPersons = RESOURCE_EXTENSION.target("/persons").request()
				.get(new GenericType<List<Person>>() {
				});

		final List<Person> expectedPersons = List.of(marten, markus, stefan, ian);
		assertEquals(expectedPersons.toString(), actualPersons.toString());
	}

	@Test
	@Order(2)
	public void readExistingPerson() {
		final Person actualPerson = RESOURCE_EXTENSION.target("/persons/marten@example.com").request()
				.get(Person.class);
		final Person expectedPerson = Person.valueOf("Mårten", "Gustafson", "marten@example.com");
		assertEquals(expectedPerson.toString(), actualPerson.toString());
	}

	@Test
	@Order(3)
	public void readNonExistentPerson() {
		final Response response = RESOURCE_EXTENSION.target("/persons/user@example.com").request().get();
		final int expectedRespStatus = HttpStatus.INTERNAL_SERVER_ERROR_500;
		assertEquals(expectedRespStatus, response.getStatus());
	}

	@Test
	@Order(4)
	public void createNewPerson() {
		final Person payload = Person.valueOf("Mårten", "Gustafson", "marten@example.com");
		final Response response = RESOURCE_EXTENSION.target("/persons").request().post(Entity.json(payload));
		final int expectedRespStatus = HttpStatus.CREATED_201;
		assertEquals(expectedRespStatus, response.getStatus());
		assertEquals("/persons/marten@example.com", response.getLocation().getPath());
	}

	@Test
	@Order(5)
	public void createPersonThatAlreadyExists() {
		final Person payload = Person.valueOf("Mårten", "Gustafson", "marten@example.com");
		final Response response = RESOURCE_EXTENSION.target("/persons").request().post(Entity.json(payload));
		final int expectedRespStatus = HttpStatus.CREATED_201;
		assertEquals(expectedRespStatus, response.getStatus());
	}

	@Test
	@Order(6)
	public void updateExistingPerson() {
		final Person payload = Person.valueOf("Mårten2", "Gustafson2", "marten@example.com");
		final Person actualPerson = RESOURCE_EXTENSION.target("/persons/marten@example.com").request()
				.put(Entity.json(payload), Person.class);
		final Person expectedPerson = Person.valueOf("Mårten2", "Gustafson2", "marten@example.com");
		assertEquals(expectedPerson.toString(), actualPerson.toString());
	}

	@Test
	@Order(7)
	public void updateNonExistentPerson() {
		final Person payload = Person.valueOf("Mårten", "Gustafson", "marten@example.com");
		final Response response = RESOURCE_EXTENSION.target("/persons/user@example.com").request()
				.put(Entity.json(payload));
		final int expectedRespStatus = HttpStatus.INTERNAL_SERVER_ERROR_500;
		assertEquals(expectedRespStatus, response.getStatus());
	}

	@Test
	@Order(8)
	public void deleteExistingPerson() {
		final Response response = RESOURCE_EXTENSION.target("/persons/marten@example.com").request().delete();
		final int expectedRespStatus = HttpStatus.OK_200;
		assertEquals(expectedRespStatus, response.getStatus());
	}

	@Test
	@Order(9)
	public void deleteNonExistentPerson() {
		final Response response = RESOURCE_EXTENSION.target("/persons/user@example.com").request().delete();
		final int expectedRespStatus = HttpStatus.INTERNAL_SERVER_ERROR_500;
		assertEquals(expectedRespStatus, response.getStatus());
	}
}
