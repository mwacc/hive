/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.ql.udf;

import org.apache.hadoop.io.Text;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestUDFFormatDate {

  private String datetime;
  private String fromFormat;
  private String toFormat;
  private String expectedResult;

  public TestUDFFormatDate(String datetime, String fromFormat, String toFormat, String expectedResult) {
    this.datetime = datetime;
    this.fromFormat = fromFormat;
    this.toFormat = toFormat;
    this.expectedResult = expectedResult;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"2013-12-23", "yyyy-MM-dd", "dd/MM/yyyy", "23/12/2013"},
        {"2012-12-06 12:05", "yyyy-MM-dd HH:mm", "KK:mm aa, dd/MM/yy", "00:05 PM, 06/12/12"}
    });

  }

  @Test
  public void test() {
    Assert.assertEquals(expectedResult,
        new UDFFormatDate().evaluate(new Text(datetime), new Text(fromFormat), new Text(toFormat)).toString());
  }
}
