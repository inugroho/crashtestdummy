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

import com.google.gson.*;
import io.github.inugroho.crashtestdummy.FakerFactory;
import io.github.inugroho.crashtestdummy.component.seeder.NameSeeder;
import io.github.inugroho.crashtestdummy.domain.FakeName;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class NameFactory {

  private final FakerFactory factory;
  private final Gson gson;

  private final HashMap<String, NameSeeder> seeders = new HashMap<>();

  private ArrayList<String> femaleFirstName = new ArrayList<>();
  private ArrayList<String> femaleMiddleName = new ArrayList<>();
  private ArrayList<String> femaleLastName = new ArrayList<>();
  private ArrayList<String> maleFirstName = new ArrayList<>();
  private ArrayList<String> maleMiddleName = new ArrayList<>();
  private ArrayList<String> maleLastName = new ArrayList<>();

  public NameFactory(FakerFactory factory, Gson gson) {
    this.factory = factory;
    this.gson = gson;
  }

  private NameSeeder loadSeed(String locale) {
    return seeders.computeIfAbsent(factory.getLocale(), loc -> {
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
        JsonObject object = element.getAsJsonObject();
        nameSeeder = gson.fromJson(element, NameSeeder.class);
      }
      return nameSeeder;
    });
  }

  public FakeName generate(FakeName.Gender gender) {
    NameSeeder nameSeeder = loadSeed(factory.getLocale());
    FakeName fakeName = new FakeName();
    if (gender.equals(FakeName.Gender.FEMALE)) {
      fakeName.setFirstName(choose(nameSeeder.getFemaleFirstNames()));
      fakeName.setMiddleName(choose(nameSeeder.getFemaleMiddleNames()));
      fakeName.setLastName(choose(nameSeeder.getFemaleLastNames()));
      fakeName.setGender(FakeName.Gender.FEMALE);
    }
    else {
      fakeName.setFirstName(choose(nameSeeder.getMaleFirstNames()));
      fakeName.setMiddleName(choose(nameSeeder.getMaleMiddleNames()));
      fakeName.setLastName(choose(nameSeeder.getMaleLastNames()));
      fakeName.setGender(FakeName.Gender.MALE);
    }
    return fakeName;
  }

  public FakeName generate() {
    return generate(factory.nextInt(2) == 0? FakeName.Gender.MALE: FakeName.Gender.FEMALE);
  }

  private String choose(final List<String> options) {
    if (options == null || options.isEmpty()) {
      return "";
    }
    return options.get(factory.nextInt(options.size()));
  }
}
