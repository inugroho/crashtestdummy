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
package io.github.inugroho.crashtestdummy.component;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import io.github.inugroho.crashtestdummy.FakerFactory;
import io.github.inugroho.crashtestdummy.component.seeder.NameSeeder;
import io.github.inugroho.crashtestdummy.domain.FakeName;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class NameFactory {

  private final FakerFactory factory;
  private final Gson gson;

  private final HashMap<String, NameSeeder> seeders = new HashMap<>();

  public NameFactory(FakerFactory factory) {
    this.factory = factory;
    this.gson = factory.getGson();
  }

  private NameSeeder loadSeed(String locale) {
    return seeders.computeIfAbsent(locale, loc -> {
      NameSeeder nameSeeder = new NameSeeder();

      JsonStreamParser parser = null;
      try {
        ClassLoader classLoader = getClass().getClassLoader();
        parser = new JsonStreamParser(new FileReader(Objects.requireNonNull(classLoader.getResource("seeders/" + locale + "/name.json")).getFile()));
      }
      catch (Exception e) {
        e.printStackTrace();
      }

      if (parser != null && parser.hasNext()) {
        JsonElement element = parser.next();
        nameSeeder = gson.fromJson(element, NameSeeder.class);
      }
      return nameSeeder;
    });
  }

  public FakeName generate(FakeName.Gender gender) {
    NameSeeder nameSeeder = loadSeed(factory.getLocale());
    FakeName fakeName = new FakeName();
    if (gender.equals(FakeName.Gender.UNDEFINED)) {
      gender = (factory.nextInt(2) == 0? FakeName.Gender.MALE : FakeName.Gender.FEMALE);
    }
    fakeName.setGender(gender);
    if (gender.equals(FakeName.Gender.FEMALE)) {
      createName(fakeName, nameSeeder.getFemaleFirstNames(), nameSeeder.getFemaleMiddleNames(), nameSeeder.getFemaleLastNames(), nameSeeder.getFamilyNames());
    }
    else {
      createName(fakeName, nameSeeder.getMaleFirstNames(), nameSeeder.getMaleMiddleNames(), nameSeeder.getMaleLastNames(), nameSeeder.getFamilyNames());
    }
    return fakeName;
  }

  public FakeName generate() {
    return generate(factory.nextInt(2) == 0? FakeName.Gender.MALE: FakeName.Gender.FEMALE);
  }

  private void createName(FakeName fakeName, final List<String> first, final List<String> middle, final List<String> last, final List<String> family) {
    fakeName.setFirstName(choose(first));
    int r = factory.nextInt(20);
    if (r < 8) {
      fakeName.setLastName(choose(last));
    }
    else if (r < 12) {
      fakeName.setMiddleName(choose(middle));
      fakeName.setLastName(choose(last));
    }
    else if (r < 16) {
      fakeName.setMiddleName(choose(first));
      fakeName.setLastName(choose(last));
    }
    else {
      fakeName.setMiddleName(choose(last));
      fakeName.setLastName(choose(family));
    }
  }

  private String choose(final List<String> options) {
    if (options == null || options.isEmpty()) {
      return "";
    }
    return options.get(factory.nextInt(options.size()));
  }
}
