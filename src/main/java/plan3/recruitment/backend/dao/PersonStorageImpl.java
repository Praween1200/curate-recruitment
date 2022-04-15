package plan3.recruitment.backend.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import plan3.recruitment.backend.model.Person;
import plan3.recruitment.backend.model.PersonStorage;

public class PersonStorageImpl implements PersonStorage {
	public static HashMap<String, Person> people = new HashMap<>();
	static {
		people.put("stefan@example.com", new Person("Stefan", "Petersson", "stefan@example.com"));
		people.put("markus@example.com", new Person("Markus", "Gustavsson", "markus@example.com"));
		people.put("ian@example.com", new Person("Ian", "Vännman", "ian@example.com"));
		people.put("marten@example.com", new Person("Mårten", "Gustafson", "marten@example.com"));
	}

	@Override
	public List<Person> list() {
		ArrayList<Person> personList = new ArrayList<Person>(people.values());
		personList.sort((Person p1, Person p2) -> p1.toString().split(" ")[1].compareTo(p2.toString().split(" ")[1]));
		return personList;
	}

	@Override
	public Optional<Person> fetch(String email) {
		Optional<Person> personData = Optional.ofNullable(people.get(email));
		return personData;
	}

	@Override
	public void save(Person person) {
		String email = person.getEmail();
		people.put(email, person);
	}

	@Override
	public boolean remove(Person person) {
		String email = person.getEmail();
		boolean result = people.containsKey(email);
		if (result) {
			people.remove(email);
			return true;
		}
		return false;
	}
}
