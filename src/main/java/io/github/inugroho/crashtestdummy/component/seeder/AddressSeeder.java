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

public class AddressSeeder {
  @SerializedName("street_title")
  private List<String> streetTitle = new ArrayList<>();
  @SerializedName("street_prefix")
  private List<String> streetPrefix = new ArrayList<>();
  @SerializedName("street_name_suffix_1")
  private List<String> streetNameSeed1 = new ArrayList<>();
  @SerializedName("street_name_suffix_2")
  private List<String> streetNameSeed2 = new ArrayList<>();
  @SerializedName("region_seed")
  private List<RegionSeed> regionSeed = new ArrayList<>();

  public List<String> getStreetTitle() {
    return streetTitle;
  }

  public void setStreetTitle(List<String> streetTitle) {
    this.streetTitle = streetTitle;
  }

  public List<String> getStreetPrefix() {
    return streetPrefix;
  }

  public void setStreetPrefix(List<String> streetPrefix) {
    this.streetPrefix = streetPrefix;
  }

  public List<String> getStreetNameSeed1() {
    return streetNameSeed1;
  }

  public void setStreetNameSeed1(List<String> streetNameSeed1) {
    this.streetNameSeed1 = streetNameSeed1;
  }

  public List<String> getStreetNameSeed2() {
    return streetNameSeed2;
  }

  public void setStreetNameSeed2(List<String> streetNameSeed2) {
    this.streetNameSeed2 = streetNameSeed2;
  }

  public List<RegionSeed> getRegionSeed() {
    return regionSeed;
  }

  public void setRegionSeed(List<RegionSeed> regionSeed) {
    this.regionSeed = regionSeed;
  }
}
