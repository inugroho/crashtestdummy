/*
 * Copyright 2018 Isaac A. Nugroho.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.inugroho.crashtestdummy;

import io.github.inugroho.crashtestdummy.component.AddressFactory;
import io.github.inugroho.crashtestdummy.component.DateFactory;
import io.github.inugroho.crashtestdummy.component.NameFactory;
import io.github.inugroho.crashtestdummy.component.PersonFactory;
import io.github.inugroho.crashtestdummy.domain.FakeAddress;
import io.github.inugroho.crashtestdummy.domain.FakeName;
import io.github.inugroho.crashtestdummy.domain.FakePerson;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestFaker {

  public TestFaker() {
    super();
  }

  @Test
  public void testDefaults() {
    FakerFactory factory = new FakerFactory();
    Assert.assertEquals(FakerConstants.DEFAULT_FAKER_LOCALE, factory.getLocale());
  }

  @Test
  public void testRandomSeed() {
    FakerFactory factory = new FakerFactory();
    factory.seed(100);
    int nextVal = factory.nextInt();
    factory.seed(100);
    Assert.assertEquals(factory.nextInt(), nextVal);
  }

  @Test
  public void testSeed() {
    FakerFactory factory1 = new FakerFactory();
    FakerFactory factory2 = new FakerFactory();

    factory1.seed(100);
    int nextVal = factory1.nextInt();
    nextVal = factory1.nextInt();

    factory1.seed(100);
    factory2.nextInt();
    assertThat(factory2.nextInt(), equalTo(nextVal));
  }

  @Test
  public void testName() {
    FakerFactory factory = new FakerFactory("id_ID");
    NameFactory nameFactory = factory.getNameFactory();

    FakeName.Gender[] allowed = { FakeName.Gender.MALE, FakeName.Gender.FEMALE };
    for (int i = 0; i < 100000; i++) {
      FakeName fakeName = nameFactory.generate();
      System.out.println(fakeName.getFullName());
      assertThat(fakeName.getFirstName(), not(isEmptyOrNullString()));
      assertThat(fakeName.getGender(), isIn(allowed));
    }
  }

  @Test
  public void testFormattedNumber() {
    FakerFactory factory = new FakerFactory("id_ID");
    AddressFactory addressFactory = factory.getAddressFactory();
    Pattern pattern = Pattern.compile("^\\d{2}-\\d{4}-\\d{8}-ABC-\\d{3}$");
    String template = "##-####-########-ABC-###";
    for (int i = 0; i < 100000; i++) {
      String r = addressFactory.generateFormattedNumber(template);
      System.out.println(r);
      boolean found = pattern.matcher(r).find();
      assertThat("Result comply format", found, is(Boolean.TRUE));
    }
  }

  @Test
  public void testPostalCode() {
    FakerFactory factory = new FakerFactory("id_ID");
    AddressFactory addressFactory = factory.getAddressFactory();
    Pattern pattern = Pattern.compile("^[1-9]\\d{4}$");
    String template = "#####";
    for (int i = 0; i < 100000; i++) {
      String r = addressFactory.generateFormattedNumber(template, 10000, 90000);
      System.out.println(r);
      boolean found = pattern.matcher(r).find();
      assertThat("Result comply format", found, is(Boolean.TRUE));
    }
  }

  @Test
  public void testAddress() {
    FakerFactory factory = new FakerFactory("id_ID");
    AddressFactory addressFactory = factory.getAddressFactory();
    for (int i = 0; i < 100000; i++) {
      FakeAddress address = addressFactory.generate();
      System.out.printf("%s %s %s, %s %n", address.getStreetAddress(), address.getCityName(), address.getZipCode(), address.getRegionName());
    }
  }

  @Test
  public void testRandom() {
    FakerFactory factory = new FakerFactory();
    int[] dist = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    factory.seed(1000);
    int count = 10000;
    final double deviation = 0.07;
    int upperDev = (int) ((1.0 + deviation) * (double) count / (double) dist.length);
    int lowerDev = (int) ((1.0 - deviation) * (double) count / (double) dist.length);
    for (int i = 0; i < count; i++) {
      int r = factory.nextInt(10);
      assertThat(r, allOf(greaterThanOrEqualTo(0), lessThan(10)));
      dist[r] += 1;
    }
    IntStream.range(0, 10).forEach(x -> assertThat(dist[x], allOf(greaterThanOrEqualTo(lowerDev), lessThanOrEqualTo(upperDev))));
  }

  @Test
  public void testDateRange() {
    FakerFactory factory = new FakerFactory();
    DateFactory dateFactory = factory.getDateFactory();
    LocalDate lowerBound = LocalDate.of(2017, 1, 1);
    LocalDate upperBound = LocalDate.of(2019, 1, 1);
    for (int i = 0; i < 100000; i++) {
      LocalDate r = dateFactory.generate(lowerBound, upperBound);
      assertThat(r, allOf(greaterThanOrEqualTo(lowerBound), lessThan(upperBound)));
    }
  }

  @Test
  public void testPeriodicDate() {
    FakerFactory factory = new FakerFactory();
    DateFactory dateFactory = factory.getDateFactory();
    LocalDate current = LocalDate.now();
    LocalDate upperBound = current.plusYears(2);
    int duration = 3;
    for (int i = 0; i < 100000; i++) {
      LocalDate r = dateFactory.periodic(current, 2, duration, ChronoUnit.WEEKS);
      assertThat(r, allOf(greaterThanOrEqualTo(current), lessThan(upperBound)));
      assertThat((int) (ChronoUnit.WEEKS.between(current, r) % duration), is(0));
    }
  }

  @Test
  public void testBackwardPeriodicDate() {
    FakerFactory factory = new FakerFactory();
    DateFactory dateFactory = factory.getDateFactory();
    LocalDate current = LocalDate.now();
    LocalDate lowerBound = current.plusYears(-2);
    int duration = -3;
    for (int i = 0; i < 100000; i++) {
      LocalDate r = dateFactory.periodic(current, -2, duration, ChronoUnit.WEEKS);
      assertThat(r, allOf(greaterThan(lowerBound), lessThanOrEqualTo(current)));
      assertThat((int) (ChronoUnit.WEEKS.between(r, current) % duration), is(0));
    }
  }

  @Test
  public void testPastYears() {
    FakerFactory factory = new FakerFactory();
    DateFactory dateFactory = factory.getDateFactory();
    LocalDate current = LocalDate.now();
    LocalDate lowerBound = current.minusYears(2);
    for (int i = 0; i < 1000; i++) {
      LocalDate r = dateFactory.pastYears(2, current);
      assertThat(r, allOf(greaterThanOrEqualTo(lowerBound), lessThan(current)));
    }
  }

  @Test
  public void testFutureThisYears() {
    FakerFactory factory = new FakerFactory();
    DateFactory dateFactory = factory.getDateFactory();
    LocalDate current = LocalDate.of(2018, 6, 23);
    LocalDate nextYear = LocalDate.of(2019, 1, 1);
    for (int i = 0; i < 1000; i++) {
      LocalDate r = dateFactory.futureThisYear(current);
      assertThat(r, allOf(greaterThanOrEqualTo(current), lessThan(nextYear)));
    }
  }

  @Test
  public void testFutureThisYearsEndOfYear() {
    FakerFactory factory = new FakerFactory();
    DateFactory dateFactory = factory.getDateFactory();
    LocalDate current = LocalDate.of(2018, 12, 31);
    LocalDate nextYear = LocalDate.of(2019, 1, 1);
    for (int i = 0; i < 1000; i++) {
      LocalDate r = dateFactory.futureThisYear(current);
      assertThat(r, allOf(greaterThanOrEqualTo(current), lessThan(nextYear)));
    }
  }

  @Test
  public void testPastMonths() {
    FakerFactory factory = new FakerFactory();
    DateFactory dateFactory = factory.getDateFactory();
    LocalDate current = LocalDate.now();
    LocalDate lowerBound = current.minusMonths(11);
    for (int i = 0; i < 1000; i++) {
      LocalDate r = dateFactory.pastMonths(11, current);
      assertThat(r, allOf(greaterThanOrEqualTo(lowerBound), lessThan(current)));
    }
  }

  @Test
  public void testFutureYears() {
    FakerFactory factory = new FakerFactory();
    DateFactory dateFactory = factory.getDateFactory();
    LocalDate current = LocalDate.now();
    LocalDate upperBound = current.plusYears(2);
    for (int i = 0; i < 1000; i++) {
      LocalDate r = dateFactory.futureYears(2, current);
      assertThat(r, allOf(greaterThan(current), lessThanOrEqualTo(upperBound)));
    }
  }

  @Test
  public void testFutureMonths() {
    FakerFactory factory = new FakerFactory();
    DateFactory dateFactory = factory.getDateFactory();
    LocalDate current = LocalDate.now();
    LocalDate upperBound = current.plusMonths(11);
    for (int i = 0; i < 1000; i++) {
      LocalDate r = dateFactory.futureMonths(11, current);
      assertThat(r, allOf(greaterThan(current), lessThanOrEqualTo(upperBound)));
    }
  }

  @Test
  public void testPersonAndEmail() {
    FakerFactory factory = new FakerFactory("id_ID");
    PersonFactory personFactory = factory.getPersonFactory();
    LocalDate current = LocalDate.now();

    Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");
    for (int i = 0; i < 10000; i++) {
      FakePerson person = personFactory.generate(current, FakeName.Gender.UNDEFINED);
//      System.out.printf("%s %s (%s) was born at %s%n", person.getFirstName(), person.getLastName(), person.getEmailAddress(), person.getBirthDate().toString());
      assertThat(person.getBirthDate(), lessThan(current));
      assertThat("Invalid email format", emailPattern.matcher(person.getEmailAddress()).find());
    }
  }

  @Test
  public void testProductivePersonAndEmail() {
    FakerFactory factory = new FakerFactory("id_ID");
    PersonFactory personFactory = factory.getPersonFactory();
    LocalDate current = LocalDate.now();
    LocalDate lowerBound = current.minusYears((long) personFactory.getProductiveUpperBound());
    LocalDate upperBound = current.minusYears(personFactory.getProductiveLowerBound());

    Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");
    for (int i = 0; i < 10000; i++) {
      FakePerson person = personFactory.generateProductiveAgePerson(current, FakeName.Gender.UNDEFINED);
//      System.out.printf("%s %s (%s) was born at %s%n", person.getFirstName(), person.getLastName(), person.getEmailAddress(), person.getBirthDate().toString());
      assertThat(person.getBirthDate(), allOf(greaterThanOrEqualTo(lowerBound), lessThan(upperBound)));
      assertThat("Invalid email format", emailPattern.matcher(person.getEmailAddress()).find());
    }
  }

  @Test
  public void testFemaleProductivePerson() {
    FakerFactory factory = new FakerFactory("id_ID");
    PersonFactory personFactory = factory.getPersonFactory();
    LocalDate current = LocalDate.now();
    LocalDate lowerBound = current.minusYears((long) personFactory.getProductiveUpperBound());
    LocalDate upperBound = current.minusYears(personFactory.getProductiveLowerBound());

    for (int i = 0; i < 10000; i++) {
      FakePerson person = personFactory.generateProductiveAgePerson(current, FakeName.Gender.FEMALE);
//      System.out.printf("%s %s %s\n", person.getFirstName(), person.getLastName(), person.getBirthDate().toString());
      assertThat(person.getBirthDate(), allOf(greaterThanOrEqualTo(lowerBound), lessThan(upperBound)));
      assertThat(person.getGender(), is(FakeName.Gender.FEMALE));
    }
  }

  @Test
  public void testNonProductivePerson() {
    FakerFactory factory = new FakerFactory("id_ID");
    PersonFactory personFactory = factory.getPersonFactory();
    LocalDate current = LocalDate.now();
    LocalDate lowerBound = current.minusYears((long) personFactory.getProductiveUpperBound());
    LocalDate upperBound = current.minusYears(personFactory.getProductiveLowerBound());

    for (int i = 0; i < 10000; i++) {
      FakePerson person = personFactory.generateNonProductiveAgePerson(current, FakeName.Gender.UNDEFINED);
//      System.out.printf("%s %s %s\n", person.getFirstName(), person.getLastName(), person.getBirthDate().toString());
      assertThat(person.getBirthDate(), anyOf(lessThan(lowerBound), greaterThanOrEqualTo(upperBound)));
    }
  }

  @Test
  public void testParent() {
    FakerFactory factory = new FakerFactory("id_ID");
    PersonFactory personFactory = factory.getPersonFactory();
    LocalDate current = LocalDate.now();
    for (int i = 0; i < 10000; i++) {
      FakePerson person = personFactory.generateProductiveAgePerson(current, FakeName.Gender.UNDEFINED);

      FakePerson parent = personFactory.generateParent(person, FakeName.Gender.UNDEFINED);
//      System.out.printf("%s %s %s has parent %s %s %s%n", person.getFirstName(), person.getLastName(), person.getBirthDate().toString(),
//          parent.getFirstName(), parent.getLastName(), parent.getBirthDate().toString());
      assertThat(parent.getBirthDate(), lessThan(person.getBirthDate()));
    }
  }
}
