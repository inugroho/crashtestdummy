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
import io.github.inugroho.crashtestdummy.FakerConstants;

import java.util.ArrayList;
import java.util.List;

public class RegionSeed {
  @SerializedName("city_name")
  private String cityName = FakerConstants.BLANK;
  @SerializedName("region_name")
  private String regionName = FakerConstants.BLANK;
  @SerializedName("region_code")
  private String regionCode = FakerConstants.BLANK;
  @SerializedName("zip_code_template")
  private List<String> ZipCodeTemplates = new ArrayList<>();

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public String getRegionCode() {
    return regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }

  public List<String> getZipCodeTemplates() {
    return ZipCodeTemplates;
  }

  public void setZipCodeTemplates(List<String> zipCodeTemplates) {
    ZipCodeTemplates = zipCodeTemplates;
  }
}
