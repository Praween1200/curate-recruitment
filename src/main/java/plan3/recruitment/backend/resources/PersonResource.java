package plan3.recruitment.backend.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.List;

import plan3.recruitment.backend.dao.PersonStorageImpl;
import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.model.PersonStorage;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("persons")
@Produces(PersonResource.APPLICATION_JSON_UTF8)
@Consumes(PersonResource.APPLICATION_JSON_UTF8)
public class PersonResource {

	public static final String APPLICATION_JSON_UTF8 = APPLICATION_JSON + "; charset=utf-8";
	private static final String EMAIL_PARAM = "email";
	private static final String EMAIL_PATH_PARAM = '{' + EMAIL_PARAM + '}';

	private PersonStorage storage = new PersonStorageImpl();

	public PersonResource(PersonStorage storage) {
		this.storage = storage;
	}

	@POST
	public Response create(final Person person) {
		storage.save(person);
		return Response.created(URI.create("/persons/" + person.getEmail())).build();
	}

	@GET
	@Path(EMAIL_PATH_PARAM)
	public Response read(@PathParam(EMAIL_PARAM) final String email) {
		Person person = storage.fetch(email).orElseThrow(() -> new IllegalArgumentException("Email is not present"));
		return Response.ok(person).build();
	}

	@PUT
	@Path(EMAIL_PATH_PARAM)
	public Response update(@PathParam(EMAIL_PARAM) final String email, final Person person) {
		storage.fetch(email).orElseThrow(() -> new IllegalArgumentException("Email is not present"));
		storage.save(person);
		return Response.ok(person).build();
	}

	@DELETE
	@Path(EMAIL_PATH_PARAM)
	public Response delete(@PathParam(EMAIL_PARAM) final String email) {
		Person person = storage.fetch(email).orElseThrow(() -> new IllegalArgumentException("Email is not present"));
		Boolean result = storage.remove(person);
		if (result) {
			return Response.ok().build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	public Response list() {
		List<Person> person = storage.list();
		return Response.ok(person).build();
	}
}
