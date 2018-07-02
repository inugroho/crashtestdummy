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
package io.github.inugroho.crashtestdummy.domain;

import io.github.inugroho.crashtestdummy.FakerConstants;

@SuppressWarnings("unused")
public class FakeName {
  private String firstName = FakerConstants.BLANK;
  private String middleName = FakerConstants.BLANK;
  private String lastName = FakerConstants.BLANK;
  private String prefix = FakerConstants.BLANK;
  private String suffix = FakerConstants.BLANK;
  private Gender gender = Gender.UNDEFINED;

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getSuffix() {
    return suffix;
  }

  public Gender getGender() {
    return gender;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getFullName() {
    StringBuilder builder = new StringBuilder();
    if (firstName != null && !firstName.isEmpty()) {
      builder.append(firstName);
    }
    if (middleName != null && !middleName.isEmpty()) {
      builder.append(" ").append(middleName);
    }
    if (lastName != null && !lastName.isEmpty()) {
      builder.append(" ").append(lastName);
    }
    return builder.toString().trim();
  }

  public enum Gender {
    UNDEFINED,
    MALE,
    FEMALE

  }
}
