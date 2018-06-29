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
package io.github.inugroho.crashtestdummy.domain;

import io.github.inugroho.crashtestdummy.domain.FakeName.Gender;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class FakePerson {
  private String firstName = null;
  private String middleName = null;
  private String lastName = null;
  private String prefix = null;
  private String suffix = null;
  private Gender gender = Gender.UNDEFINED;
  private LocalDate birthDate = null;

  private String emailAddress = null;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public FakePerson() {}

  public FakePerson(FakeName name) {
    this.firstName = name.getFirstName();
    this.middleName = name.getMiddleName();
    this.lastName = name.getLastName();
    this.gender = name.getGender();
  }
}
