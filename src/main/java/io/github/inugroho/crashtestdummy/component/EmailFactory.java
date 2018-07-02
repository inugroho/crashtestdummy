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
import io.github.inugroho.crashtestdummy.component.seeder.EmailDomainSeeder;
import io.github.inugroho.crashtestdummy.domain.FakeName;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

public class EmailFactory {
  private final Gson gson;
  private final FakerFactory factory;

  private final HashMap<String, EmailDomainSeeder> emailDomainSeeders = new HashMap<>();

  private static final String[] emailSeparators = {
      "",
      ".",
      "_"
  };

  public EmailFactory(FakerFactory factory) {
    this.factory = factory;
    this.gson = factory.getGson();
  }

  private EmailDomainSeeder loadSeed(String locale) {
    return emailDomainSeeders.computeIfAbsent(locale, loc -> {
      EmailDomainSeeder emailDomainSeeder = new EmailDomainSeeder();

      JsonStreamParser parser = null;
      try {
        ClassLoader classLoader = getClass().getClassLoader();
        parser = new JsonStreamParser(new FileReader(Objects.requireNonNull(classLoader.getResource("seeders/" + loc + "/email_domain.json")).getFile()));
      }
      catch (Exception e) {
        e.printStackTrace();
      }

      if (parser != null && parser.hasNext()) {
        JsonElement element = parser.next();
        emailDomainSeeder = gson.fromJson(element, EmailDomainSeeder.class);
      }
      return emailDomainSeeder;
    });
  }

  public String generate(FakeName name, boolean includeInternational, LocalDate birthDate) {
    String domain;
    if (includeInternational && factory.nextInt(2) == 1) {
      EmailDomainSeeder seeder = loadSeed("international");
      domain = seeder.getEmailDomains().get(factory.nextInt(seeder.getEmailDomains().size()));
    }
    else {
      EmailDomainSeeder seeder = loadSeed(factory.getLocale());
      domain = seeder.getEmailDomains().get(factory.nextInt(seeder.getEmailDomains().size()));
    }
    StringBuilder builder = new StringBuilder();

    String emailSeparator = emailSeparators[factory.nextInt(emailSeparators.length)];

    if (name.getFirstName().isEmpty() && name.getLastName().isEmpty()) {
      builder.append("dummy").append(emailSeparator).append(factory.nextInt(100, 1000));
    }
    else if (name.getLastName().isEmpty()) {
      builder.append(name.getFirstName().toLowerCase());
    }
    else if (name.getFirstName().isEmpty()) {
      builder.append(name.getLastName().toLowerCase());
    }
    else {
      boolean swapped = (factory.nextInt(100) == 0);
      String firstWord = (swapped ? name.getLastName() : name.getFirstName()).toLowerCase();
      String secondWord = (swapped ? name.getFirstName() : name.getLastName()).toLowerCase();
      int sel = factory.nextInt(85);
      if (sel < 50) {
        builder.append(firstWord).append(secondWord);
      }
      else if (sel < 70) {
        builder.append(firstWord).append(emailSeparator).append(secondWord);
      }
      else if (sel < 80) {
        builder.append(firstWord.charAt(0)).append(secondWord);
      }
      else {
        builder.append(firstWord).append(secondWord.charAt(0));
      }
    }
    if (birthDate != null && factory.nextInt(50) == 0) {
      builder.append(emailSeparator).append(birthDate.format(DateTimeFormatter.ofPattern("yy")));
    }
    return builder.append("@").append(domain).toString();
  }
}
