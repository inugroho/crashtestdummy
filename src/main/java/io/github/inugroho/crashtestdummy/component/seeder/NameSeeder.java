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
package io.github.inugroho.crashtestdummy.component.seeder;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class NameSeeder {
  @SerializedName("female_first_name")
  private List<String> femaleFirstNames = new ArrayList<>();
  @SerializedName("female_middle_name")
  private List<String> femaleMiddleNames = new ArrayList<>();
  @SerializedName("female_last_name")
  private List<String> femaleLastNames = new ArrayList<>();
  @SerializedName("male_first_name")
  private List<String> maleFirstNames = new ArrayList<>();
  @SerializedName("male_middle_name")
  private List<String> maleMiddleNames = new ArrayList<>();
  @SerializedName("male_last_name")
  private List<String> maleLastNames = new ArrayList<>();
  @SerializedName("family_name")
  private List<String> familyNames = new ArrayList<>();

  public List<String> getFemaleFirstNames() {
    return femaleFirstNames;
  }

  public void setFemaleFirstNames(List<String> femaleFirstNames) {
    this.femaleFirstNames = femaleFirstNames;
  }

  public List<String> getFemaleMiddleNames() {
    return femaleMiddleNames;
  }

  public void setFemaleMiddleNames(List<String> femaleMiddleNames) {
    this.femaleMiddleNames = femaleMiddleNames;
  }

  public List<String> getFemaleLastNames() {
    return femaleLastNames;
  }

  public void setFemaleLastNames(List<String> femaleLastNames) {
    this.femaleLastNames = femaleLastNames;
  }

  public List<String> getMaleFirstNames() {
    return maleFirstNames;
  }

  public void setMaleFirstNames(List<String> maleFirstNames) {
    this.maleFirstNames = maleFirstNames;
  }

  public List<String> getMaleMiddleNames() {
    return maleMiddleNames;
  }

  public void setMaleMiddleNames(List<String> maleMiddleNames) {
    this.maleMiddleNames = maleMiddleNames;
  }

  public List<String> getMaleLastNames() {
    return maleLastNames;
  }

  public void setMaleLastNames(List<String> maleLastNames) {
    this.maleLastNames = maleLastNames;
  }

  public List<String> getFamilyNames() {
    return familyNames;
  }

  public void setFamilyNames(List<String> familyNames) {
    this.familyNames = familyNames;
  }
}
