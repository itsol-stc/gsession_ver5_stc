/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.sjts.util.json.filters;

import jp.co.sjts.util.json.util.PropertyFilter;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
//Changes by JAPAN TOTAL SYSTEM CO.,LTD
//・Changed "package": net.sf.json.filters → jp.co.sjts.util.json.filters
//・Added annotation "@SuppressWarnings"
@SuppressWarnings({"unchecked", "all" })
public class NotPropertyFilter implements PropertyFilter {
   private PropertyFilter filter;

   public NotPropertyFilter( PropertyFilter filter ) {
      this.filter = filter;
   }

   public boolean apply( Object source, String name, Object value ) {
      if( filter != null ){
         return !filter.apply( source, name, value );
      }
      return false;
   }
}