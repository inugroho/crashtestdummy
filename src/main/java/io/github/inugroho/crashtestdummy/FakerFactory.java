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
package io.github.inugroho.crashtestdummy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.inugroho.crashtestdummy.component.DateFactory;
import io.github.inugroho.crashtestdummy.component.EmailFactory;
import io.github.inugroho.crashtestdummy.component.NameFactory;
import io.github.inugroho.crashtestdummy.component.PersonFactory;
import io.github.inugroho.crashtestdummy.util.ThreadedRandom;

@SuppressWarnings("unused")
public class FakerFactory {


  private String locale;
  private final Gson gson;

  private NameFactory nameFactory = null;
  private DateFactory dateFactory = null;
  private PersonFactory personFactory = null;
  private EmailFactory emailFactory = null;

  public FakerFactory() {
    this.locale = FakerConstants.DEFAULT_FAKER_LOCALE;
    this.gson = new GsonBuilder().create();
  }

  public FakerFactory(String locale) {
    this.locale = locale;
    this.gson = new GsonBuilder().create();
  }

  public void setLocale(final String locale) {
    this.locale = locale;
  }

  public String getLocale() {
    return locale;
  }

  public void seed(long randomSeed) {
    ThreadedRandom.seed(randomSeed);
  }

  public int nextInt() {
    return ThreadedRandom.nextInt();
  }

  public int nextInt(int bound) {
    return ThreadedRandom.nextInt(bound);
  }

  public int nextInt(int origin, int bound) {
    return ThreadedRandom.nextInt(origin, bound);
  }

  public long nextLong() {
    return ThreadedRandom.nextLong();
  }

  public long nextLong(long bound) {
    return ThreadedRandom.nextLong(bound);
  }

  public long nextLong(long origin, long bound) {
    return ThreadedRandom.nextLong(origin, bound);
  }

  public NameFactory getNameFactory() {
    if (nameFactory == null) {
      nameFactory = new NameFactory(this, gson);
    }
    return nameFactory;
  }

  public DateFactory getDateFactory() {
    if (dateFactory == null) {
      dateFactory = new DateFactory(this, gson);
    }
    return dateFactory;
  }

  public EmailFactory getEmailFactory() {
    if (emailFactory == null) {
      emailFactory = new EmailFactory(this, gson);
    }
    return emailFactory;
  }

  public PersonFactory getPersonFactory() {
    if (personFactory == null) {
      personFactory = new PersonFactory(this, getNameFactory(), getDateFactory(), getEmailFactory());
    }
    return personFactory;
  }
}
