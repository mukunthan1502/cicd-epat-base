package sg.edu.nus.iss.d13revision.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sg.edu.nus.iss.d13revision.models.Person;

public class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    public void setUp() {
        personService = new PersonService();
    }

    @Test
    public void testGetPersons_initializesWithTwoPeople() {
        List<Person> persons = personService.getPersons();

        assertEquals(2, persons.size());
        assertEquals("Mark", persons.get(0).getFirstName());
        assertEquals("Zuckerberg", persons.get(0).getLastName());
        assertEquals("Elon", persons.get(1).getFirstName());
        assertEquals("Musk", persons.get(1).getLastName());
    }

    @Test
    public void testAddPerson() {
        Person newPerson = new Person("Ada", "Lovelace");

        personService.addPerson(newPerson);

        List<Person> persons = personService.getPersons();
        assertEquals(3, persons.size());
        assertTrue(persons.stream().anyMatch(
                p -> "Ada".equals(p.getFirstName()) && "Lovelace".equals(p.getLastName())));
    }

    @Test
    public void testUpdatePerson() {
        Person existing = personService.getPersons().get(0);
        Person updated = new Person(existing.getId(), "Marcus", "Zuckerberg");

        personService.updatePerson(updated);

        List<Person> persons = personService.getPersons();
        assertEquals(2, persons.size());
        assertTrue(persons.stream().anyMatch(
                p -> existing.getId().equals(p.getId())
                        && "Marcus".equals(p.getFirstName())
                        && "Zuckerberg".equals(p.getLastName())));
        assertFalse(persons.stream().anyMatch(
                p -> "Mark".equals(p.getFirstName()) && "Zuckerberg".equals(p.getLastName())));
    }

    @Test
    public void testRemovePerson() {
        Person toRemove = personService.getPersons().get(0);

        personService.removePerson(toRemove);

        List<Person> persons = personService.getPersons();
        assertEquals(1, persons.size());
        assertFalse(persons.stream().anyMatch(p -> toRemove.getId().equals(p.getId())));
        assertEquals("Elon", persons.get(0).getFirstName());
    }

    @Test
    public void testRemovePerson_unknownId_leavesListUnchanged() {
        Person unknown = new Person("unknown-id", "Ghost", "Person");

        personService.removePerson(unknown);

        assertEquals(2, personService.getPersons().size());
    }
}
