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

package com.xwtec.xwserver.util.json;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtec.xwserver.util.json.util.JSONUtils;
import com.xwtec.xwserver.util.json.util.JsonEventListener;

/**
 * Base class for JSONObject and JSONArray.
 *
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
@SuppressWarnings("unchecked")
abstract class AbstractJSON {
private static class CycleSet extends ThreadLocal {
      protected Object initialValue() {
         return new SoftReference(new HashSet());
      }

      public Set getSet() {
         Set set = (Set) ((SoftReference)get()).get();
         if( set == null ) {
             set = new HashSet();
             set(new SoftReference(set));
         }
         return set;
      }
   }
   
   private static CycleSet cycleSet = new CycleSet();

   private static final Log log = LogFactory.getLog( AbstractJSON.class );

   /**
    * Adds a reference for cycle detection check.
    *
    * @param instance the reference to add
    * @return true if the instance has not been added previously, false
    *        otherwise.
    */
   protected static boolean addInstance( Object instance ) {
      return getCycleSet().add( instance );
   }

   /**
    * Fires an end of array event.
    */
   protected static void fireArrayEndEvent( JsonConfig jsonConfig ) {
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onArrayEnd();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   /**
    * Fires a start of array event.
    */
   protected static void fireArrayStartEvent( JsonConfig jsonConfig ) {
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onArrayStart();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   /**
    * Fires an element added event.
    *
    * @param index the index where the element was added
    * @param element the added element
    */
   protected static void fireElementAddedEvent( int index, Object element, JsonConfig jsonConfig ) {
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onElementAdded( index, element );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   /**
    * Fires an error event.
    *
    * @param jsone the thrown exception
    */
   protected static void fireErrorEvent( JSONException jsone, JsonConfig jsonConfig ) {
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onError( jsone );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   /**
    * Fires an end of object event.
    */
   protected static void fireObjectEndEvent( JsonConfig jsonConfig ) {
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onObjectEnd();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   /**
    * Fires a start of object event.
    */
   protected static void fireObjectStartEvent( JsonConfig jsonConfig ) {
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onObjectStart();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   /**
    * Fires a property set event.
    *
    * @param key the name of the property
    * @param value the value of the property
    * @param accumulated if the value has been accumulated over 'key'
    */
   protected static void firePropertySetEvent( String key, Object value, boolean accumulated,
         JsonConfig jsonConfig ) {
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onPropertySet( key, value, accumulated );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   /**
    * Fires a warning event.
    *
    * @param warning the warning message
    */
   protected static void fireWarnEvent( String warning, JsonConfig jsonConfig ) {
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onWarning( warning );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   /**
    * Removes a reference for cycle detection check.
    */
   protected static void removeInstance( Object instance ) {
      getCycleSet().remove( instance );
   }

   protected Object _processValue( Object value, JsonConfig jsonConfig ) {
      if( JSONNull.getInstance().equals( value ) ) {
         return JSONNull.getInstance();
      } else if( Class.class.isAssignableFrom( value.getClass() ) || value instanceof Class ) {
         return ((Class) value).getName();
      } else if( JSONUtils.isFunction( value ) ) {
         if( value instanceof String ) {
            value = JSONFunction.parse( (String) value );
         }
         return value;
      } else if( value instanceof JSONString ) {
         return JSONSerializer.toJSON( (JSONString) value, jsonConfig );
      } else if( value instanceof JSON ) {
         return JSONSerializer.toJSON( value, jsonConfig );
      } else if( JSONUtils.isArray( value ) ) {
         return JSONArray.fromObject( value, jsonConfig );
      } else if( JSONUtils.isString( value ) ) {
         String str = String.valueOf( value );
         if( JSONUtils.hasQuotes( str ) ){
            str = JSONUtils.stripQuotes( str );
            if( JSONUtils.isFunction( str )){
               return JSONUtils.DOUBLE_QUOTE + str + JSONUtils.DOUBLE_QUOTE;
            }
            return str;
         } else if( JSONUtils.isJsonKeyword( str, jsonConfig ) ) {
            if( jsonConfig.isJavascriptCompliant() && "undefined".equals( str )){
               return JSONNull.getInstance();
            }
            return str;
         } else if( JSONUtils.mayBeJSON( str ) ) {
            try {
               return JSONSerializer.toJSON( str, jsonConfig );
            } catch( JSONException jsone ) {
               return str;
            }
         }
         return str;
      } else if( JSONUtils.isNumber( value ) ) {
         JSONUtils.testValidity( value );
         return JSONUtils.transformNumber( (Number) value );
      } else if( JSONUtils.isBoolean( value ) ) {
         return value;
      } else {
         JSONObject jsonObject = JSONObject.fromObject( value, jsonConfig );
         if( jsonObject.isNullObject() ) {
            return JSONNull.getInstance();
         } else {
            return jsonObject;
         }
      }
   }
   
   private static Set getCycleSet() {
      return cycleSet.getSet();
   }
}