package org.acme.service.person;

import org.acme.domain.Address;
import org.acme.domain.Person;
import org.acme.domain.PhoneNumber;
import org.acme.exception.ServiceException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

@ApplicationScoped
public class PersonService {

    @Inject
    PersonRepository personRepository;

    public Person findById(Long id) {
        Person entity = personRepository.findById(id);
        if (entity == null) {
            throw new ServiceException("Person with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    public List<Person> findByName(String name) {
        return personRepository.findByName(name);
    }

    public Person create(Person person) {
        personRepository.persist(person);

        return person;
    }

    public Person update(Person person) {
        Person entity = findById(person.getId());
        entity.setGivenName(person.getGivenName());
        entity.setFamilyName(person.getFamilyName());

        return entity;
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    public String loadSampleData() {
        String resourceName = "/FakeNameGenerator.com_f0c476f9.csv";
        String[] HEADERS = { "Number","Gender","Title","GivenName","Surname","StreetAddress","City","State","StateFull","ZipCode","CountryFull","EmailAddress","TelephoneNumber","TelephoneCountryCode","Birthday","NationalID","Occupation","Company","GUID" };
        URL url = getClass().getResource(resourceName);
        if(url == null) {
            throw new ServiceException("Unable to load CSV resource " + resourceName);
        }

        try {
            Reader in =  new InputStreamReader(url.openStream());

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(in);

            int createdCount = 0;
            for (CSVRecord record : records) {
                String gender = record.get("Gender");
                String givenName = record.get("GivenName");
                String familyName = record.get("Surname");
                String emailAddress = record.get("EmailAddress");

                String street = record.get("StreetAddress");
                String cityTown = record.get("City");
                String stateRegion = record.get("StateFull");
                String postcode = record.get("ZipCode");
                String country = record.get("CountryFull");

                String telephoneNumber = record.get("TelephoneNumber");
                String countryCode = record.get("TelephoneCountryCode");

                Person person = new Person();
                person.setGender(gender);
                person.setGivenName(givenName);
                person.setFamilyName(familyName);
                person.setEmailAddress(emailAddress);

                Address address = new Address();
                address.setStreet(street);
                address.setCityTown(cityTown);
                address.setStateRegion(stateRegion);
                address.setPostcode(postcode);
                address.setCountry(country);

                person.setAddress(address);

                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setPhoneNumber(telephoneNumber);
                phoneNumber.setCountryCode(countryCode);

                person.getPhoneNumberList().add(phoneNumber);

                personRepository.persist(person);
                createdCount++;
            }

            return "Created " + createdCount + " person rows";
        }
        catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }
}
