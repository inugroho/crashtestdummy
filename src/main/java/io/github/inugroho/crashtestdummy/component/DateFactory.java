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

import com.google.gson.Gson;
import io.github.inugroho.crashtestdummy.FakerFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@SuppressWarnings("unused")
public class DateFactory {
  private final FakerFactory factory;
  private final Gson gson;

  public DateFactory(FakerFactory factory, Gson gson) {
    this.factory = factory;
    this.gson = gson;
  }

  public LocalDate generate(final LocalDate lowerBound, final LocalDate upperBound) {
    return lowerBound.plusDays(factory.nextLong(ChronoUnit.DAYS.between(lowerBound, upperBound)));
  }

  public LocalDate pastYears(int years, final LocalDate dateReference) {
    LocalDate lowerBound = dateReference.minusYears(years);
    return lowerBound.plusDays(factory.nextLong(ChronoUnit.DAYS.between(lowerBound, dateReference)));

  }

  public LocalDate pastMonths(int months, final LocalDate dateReference) {
    LocalDate lowerBound = dateReference.minusMonths(months);
    return lowerBound.plusDays(factory.nextLong(ChronoUnit.DAYS.between(lowerBound, dateReference)));
  }

  public LocalDate pastThisYear(final LocalDate dateReference) {
    LocalDate lowerBound = LocalDate.of(dateReference.getYear(), 1, 1);
    return lowerBound.plusDays(factory.nextLong(ChronoUnit.DAYS.between(lowerBound, dateReference)));
  }

  public LocalDate pastThisMonth(final LocalDate dateReference) {
    LocalDate lowerBound = LocalDate.of(dateReference.getYear(), dateReference.getMonth(), 1);
    return lowerBound.plusDays(factory.nextLong(ChronoUnit.DAYS.between(lowerBound, dateReference)));
  }

  public LocalDate periodic(final LocalDate dateReference, int withinYears, int unitMultiplier, ChronoUnit unit) {
    if (unitMultiplier == 0 || withinYears == 0) {
      return dateReference;
    }
    int sign = Integer.signum(withinYears) + Integer.signum(unitMultiplier);
    if (sign == 0) {
      unitMultiplier = -unitMultiplier;
    }

    LocalDate bound = dateReference.plusYears(withinYears);
    long upperBound = ((unit.between(dateReference, bound) / (long) unitMultiplier) + 1);
    return dateReference.plus(unitMultiplier * factory.nextLong(upperBound), unit);
  }

  public LocalDate futureYears(int years, final LocalDate dateReference) {
    LocalDate upperBound = dateReference.plusYears(years);
    return dateReference.plusDays(1 + factory.nextLong(ChronoUnit.DAYS.between(dateReference, upperBound)));
  }

  public LocalDate futureMonths(int months, final LocalDate dateReference) {
    LocalDate upperBound = dateReference.plusMonths(months);
    return dateReference.plusDays(1 + factory.nextLong(ChronoUnit.DAYS.between(dateReference, upperBound)));
  }

  public LocalDate futureThisYear(final LocalDate dateReference) {
    LocalDate upperBound = LocalDate.of(dateReference.getYear() + 1, 1, 1);
    if (ChronoUnit.DAYS.between(dateReference, upperBound) == 1) {
      return dateReference;
    }
    return dateReference.plusDays(1 + factory.nextLong(ChronoUnit.DAYS.between(dateReference, upperBound) - 1));
  }

  public LocalDate futureThisMonth(final LocalDate dateReference) {
    LocalDate upperBound = LocalDate.of(dateReference.getYear(), dateReference.getMonth(), 1).plusMonths(1);
    if (ChronoUnit.DAYS.between(dateReference, upperBound) == 1) {
      return dateReference;
    }
    return dateReference.plusDays(1 + factory.nextLong(ChronoUnit.DAYS.between(dateReference, upperBound) - 1));
  }
}
