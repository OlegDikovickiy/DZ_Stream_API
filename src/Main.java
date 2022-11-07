import java.sql.Struct;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {

    List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
    List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
    Collection<Person> persons = new ArrayList<>();
    for (int i = 0; i < 10_000_000; i++) {
      persons.add(new Person(
          names.get(new Random().nextInt(names.size())),
          families.get(new Random().nextInt(families.size())),
          new Random().nextInt(100),
          Sex.values()[new Random().nextInt(Sex.values().length)],
          Education.values()[new Random().nextInt(Education.values().length)])
      );
    }

    // Поиск несовершенно летних =< 18
    long countMinors = persons.stream()
        .filter(person -> person.getAge() <= 18)
        .count();


    //Поиск призывников MAN >= 18 && <= 27
    List<String> collectFamilyConscripts = persons.stream()
        .filter(person -> person.getSex() == Sex.MAN)
        .filter(person -> (person.getAge() >= 18) && (person.getAge() <= 27))
        .map(Person::getFamily)
        .collect(Collectors.toList());


    //Отсортированный  по фамилии список работоспособных HIGHER + (MAN (>= 18 && <= 65) || WONAM (>= 18 && <= 60))
    List<Person> workablePerson = persons.stream()
        .filter(person -> person.getEducation() == Education.HIGHER)
        .filter(person -> person.getAge() >= 18)
        .filter(person -> (person.getSex() == Sex.MAN && person.getAge() < 65) || (person.getSex() == Sex.WOMAN && person.getAge() < 60))
        .sorted(Comparator.comparing(person -> person.getFamily())).collect(Collectors.toList());
  }
}

