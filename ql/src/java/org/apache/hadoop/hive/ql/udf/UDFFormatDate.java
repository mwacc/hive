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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

import java.text.SimpleDateFormat;

@Description(name = "format_date",
    value = "_FUNC_(date, [from_format], to_format) - convert datetime presentation from one format to another",
    extended = "Example:\n"
        + "  > SELECT _FUNC_('2013-12-23 20:13:45.0', 'yyyy-MM-dd HH:mm:ss.S', 'yyyy/MM/dd') FROM src LIMIT 1;\n"
        + "  '2013/12/23'")
public class UDFFormatDate  extends UDF {
  static final Log LOG = LogFactory.getLog(UDFFormatDate.class);

  private final Text defaultFormat = new Text("yyyy-MM-dd HH:mm:ss.S");
  private final Text result = new Text();


  public Text evaluate(Text datetime, Text toFormat) {
    if(datetime == null || toFormat == null) {
      return null;
    }

    return evaluate(datetime, defaultFormat, toFormat);
  }

  public Text evaluate(Text datetime, Text fromFormat, Text toFormat) {
    if(datetime == null || fromFormat == null || toFormat == null) {
      return null;
    }

    try {
      String converted = new SimpleDateFormat(toFormat.toString()).format(
        new SimpleDateFormat(fromFormat.toString()).parse(datetime.toString()));

      result.set(converted);
      return result;
    } catch (Exception e) {
      LOG.info(String.format("Exception during converting %s from format %s to %s ",
          datetime, fromFormat, toFormat));
    }
    return null;
  }

}
