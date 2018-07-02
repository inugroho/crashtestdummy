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
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import io.github.inugroho.crashtestdummy.FakerFactory;
import io.github.inugroho.crashtestdummy.component.seeder.AddressSeeder;
import io.github.inugroho.crashtestdummy.component.seeder.RegionSeed;
import io.github.inugroho.crashtestdummy.domain.FakeAddress;
import io.github.inugroho.crashtestdummy.domain.FakeName;

import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressFactory {
  private final FakerFactory factory;
  private final NameFactory nameFactory;
  private final Gson gson;
  private final HashMap<String, AddressSeeder> seeders = new HashMap<>();

  public AddressFactory(final FakerFactory factory) {
    this.factory = factory;
    this.nameFactory = factory.getNameFactory();
    this.gson = factory.getGson();
  }

  private AddressSeeder loadSeed(String locale) {
    return seeders.computeIfAbsent(factory.getLocale(), loc -> {
      AddressSeeder addressSeeder = new AddressSeeder();

      JsonStreamParser parser = null;
      try {
        ClassLoader classLoader = getClass().getClassLoader();
        parser = new JsonStreamParser(new FileReader(Objects.requireNonNull(classLoader.getResource("seeders/" + locale + "/address.json")).getFile()));
      }
      catch (Exception e) {
        e.printStackTrace();
      }

      if (parser != null && parser.hasNext()) {
        JsonElement element = parser.next();
        JsonObject object = element.getAsJsonObject();
        addressSeeder = gson.fromJson(element, AddressSeeder.class);
      }
      return addressSeeder;
    });
  }
  public FakeAddress generate() {
    FakeAddress address = new FakeAddress();
    AddressSeeder seeder = loadSeed(factory.getLocale());

    address.setStreetAddress(generateStreetAddress(seeder));

    RegionSeed regionSeed = seeder.getRegionSeed().get(factory.nextInt(seeder.getRegionSeed().size()));

    address.setCityName(regionSeed.getCityName());
    address.setCountryCode("ID");
    address.setCountryName("INDONESIA");
    address.setRegionName(regionSeed.getRegionName());
    address.setStateCode(regionSeed.getRegionCode());

    String zip = choose(regionSeed.getZipCodeTemplates());

    if (zip.isEmpty()) {
      zip = "#####";
      address.setZipCode(generateFormattedNumber(zip, 10000, 90000));
    }
    else {
      address.setZipCode(generateFormattedNumber(zip, 10000));
    }
    return address;
  }

  static final Pattern pattern = Pattern.compile("#+");

  public String generateFormattedNumber(final String numberTemplate) {
    return generateFormattedNumber(numberTemplate, 0, 1000000);
  }

  public String generateFormattedNumber(final String numberTemplate, int bound) {
    return generateFormattedNumber(numberTemplate, 0, bound);
  }

  public String generateFormattedNumber(final String numberTemplate, int origin, int bound) {
    Matcher matcher = pattern.matcher(numberTemplate);
    StringBuilder sb = new StringBuilder(numberTemplate);
    while (matcher.find()) {

      String r = String.valueOf(factory.nextInt(origin, bound));

      int len = matcher.end() - matcher.start();

      if (len < r.length()) {
        r = r.substring(0, len);
      }
      else if (len > r.length()) {
        char[] buffer = new char[len];
        Arrays.fill(buffer, '0');
        System.arraycopy(r.toCharArray(), 0, buffer, len - r.length(), r.length());
        r = new String(buffer);
      }
      sb.replace(matcher.start(), matcher.end(), r);
    }
    return sb.toString();
  }

  private String generateStreetAddress(AddressSeeder seeder) {
    StringBuilder sb = new StringBuilder();
    sb.append(choose(seeder.getStreetTitle())).append(" ");
    if (factory.nextInt(10) == 0) {
      sb.append(choose(seeder.getStreetPrefix())).append(" ");
    }
    FakeName fakeName = nameFactory.generate();
    sb.append(fakeName.getFirstName()).append(" ");
    if (factory.nextInt(5) == 0) {
      sb.append(choose(seeder.getStreetNameSeed1())).append(" ");
    }
    if (factory.nextInt(5) == 0) {
      sb.append(choose(seeder.getStreetNameSeed2())).append(" ");
    }
    sb.append("NO. ").append(factory.nextInt(200));
    return sb.toString().trim();
  }

  private String choose(final List<String> options) {
    if (options == null || options.isEmpty()) {
      return "";
    }
    if (options.size() == 1) {
      return options.get(0);
    }
    return options.get(factory.nextInt(options.size()));
  }

}
