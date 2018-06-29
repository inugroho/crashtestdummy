/*
 * Copyright 2018 Isaac A. Nugroho
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
package io.github.inugroho.crashtestdummy.component;

import io.github.inugroho.crashtestdummy.FakerFactory;
import io.github.inugroho.crashtestdummy.domain.FakeName;
import io.github.inugroho.crashtestdummy.domain.FakePerson;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class PersonFactory {
  private final FakerFactory factory;
  private final NameFactory nameFactory;
  private final DateFactory dateFactory;
  private final EmailFactory emailFactory;

  private int productiveLowerBound = 18;
  private int productiveUpperBound = 60;

  private int maxAge = 90;

  private int marriageAge = 15;

  private int bottomRatio = 10;
  private int productiveRatio = 60;
  private int upperRatio = 10;

  public int getProductiveLowerBound() {
    return productiveLowerBound;
  }

  public void setProductiveLowerBound(int productiveLowerBound) {
    this.productiveLowerBound = productiveLowerBound;
  }

  public int getProductiveUpperBound() {
    return productiveUpperBound;
  }

  public void setProductiveUpperBound(int productiveUpperBound) {
    this.productiveUpperBound = productiveUpperBound;
  }

  public int getMaxAge() {
    return maxAge;
  }

  public void setMaxAge(int maxAge) {
    this.maxAge = maxAge;
  }

  public int getMarriageAge() {
    return marriageAge;
  }

  public void setMarriageAge(int marriageAge) {
    this.marriageAge = marriageAge;
  }

  public int getBottomRatio() {
    return bottomRatio;
  }

  public void setBottomRatio(int bottomRatio) {
    this.bottomRatio = bottomRatio;
  }

  public int getProductiveRatio() {
    return productiveRatio;
  }

  public void setProductiveRatio(int productiveRatio) {
    this.productiveRatio = productiveRatio;
  }

  public int getUpperRatio() {
    return upperRatio;
  }

  public void setUpperRatio(int upperRatio) {
    this.upperRatio = upperRatio;
  }

  public PersonFactory(FakerFactory factory, NameFactory nameFactory, DateFactory dateFactory, EmailFactory emailFactory) {
    this.factory = factory;
    this.nameFactory = nameFactory;
    this.dateFactory = dateFactory;
    this.emailFactory = emailFactory;
  }

  public FakePerson generate() {
    return generate(LocalDate.now(), FakeName.Gender.UNDEFINED);
  }

  public FakePerson generate(LocalDate dateReference, FakeName.Gender gender) {
    FakeName name = getFakeName(gender);
    FakePerson person = new FakePerson(name);

    int r = factory.nextInt(bottomRatio + productiveRatio + upperRatio);

    if (r < bottomRatio) {
      person.setBirthDate(youngerAgeBirths(dateReference));
    }
    else if (r - bottomRatio < productiveRatio) {
      person.setBirthDate(productiveAgeBirths(dateReference));
    }
    else {
      person.setBirthDate(olderAgeBirths(dateReference));
    }
    person.setEmailAddress(emailFactory.generate(name, true, person.getBirthDate()));
    return person;
  }

  public FakePerson generateNonProductiveAgePerson(LocalDate dateReference, FakeName.Gender gender) {
    FakeName name = getFakeName(gender);
    FakePerson person = new FakePerson(name);

    int r = factory.nextInt(bottomRatio + upperRatio);

    if (r < bottomRatio) {
      person.setBirthDate(youngerAgeBirths(dateReference));
    }
    else {
      person.setBirthDate(olderAgeBirths(dateReference));
    }
    person.setEmailAddress(emailFactory.generate(name, true, person.getBirthDate()));
    return person;
  }

  public FakePerson generateProductiveAgePerson(LocalDate dateReference, FakeName.Gender gender) {
    FakeName name = getFakeName(gender);
    FakePerson person = new FakePerson(name);

    person.setBirthDate(productiveAgeBirths(dateReference));
    person.setEmailAddress(emailFactory.generate(name, true, person.getBirthDate()));

    return person;
  }

  public FakePerson generateParent(FakePerson person, FakeName.Gender gender) {
    FakeName name = getFakeName(gender);
    FakePerson parent = new FakePerson(name);

    parent.setBirthDate(dateFactory.pastYears(maxAge, person.getBirthDate().minusYears(marriageAge)));
    person.setEmailAddress(emailFactory.generate(name, true, person.getBirthDate()));

    return parent;
  }

  private FakeName getFakeName(FakeName.Gender gender) {
    return gender.equals(FakeName.Gender.UNDEFINED) ? nameFactory.generate(): nameFactory.generate(gender);
  }

  private LocalDate olderAgeBirths(final LocalDate dateReference) {
    LocalDate lowerBound = dateReference.minusYears(maxAge);
    LocalDate upperBound = dateReference.minusYears(productiveUpperBound);
    return dateFactory.generate(lowerBound, upperBound);
  }

  private LocalDate productiveAgeBirths(final LocalDate dateReference) {
    LocalDate lowerBound = dateReference.minusYears(productiveUpperBound);
    LocalDate upperBound = dateReference.minusYears(productiveLowerBound);
    return dateFactory.generate(lowerBound, upperBound);
  }

  private LocalDate youngerAgeBirths(final LocalDate dateReference) {
    return dateFactory.pastYears(productiveLowerBound, dateReference);
  }
}
