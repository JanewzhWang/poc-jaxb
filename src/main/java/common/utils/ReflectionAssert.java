/*
 *
 *  * Copyright 2010,  Unitils.org
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package common.utils;


import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import ognl.DefaultMemberAccess;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.unitils.core.UnitilsException;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import org.unitils.reflectionassert.difference.Difference;
import org.unitils.reflectionassert.report.impl.DefaultDifferenceReport;
import org.unitils.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static org.unitils.reflectionassert.ReflectionComparatorFactory.createRefectionComparator;
import static org.unitils.reflectionassert.ReflectionComparatorMode.IGNORE_DEFAULTS;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_ORDER;

public class ReflectionAssert {


    /**
     * Asserts that two objects are equal. Reflection is used to compare all fields of these values.
     * If they are not equal an AssertionFailedError is thrown.
     * <p/>
     * This is identical to assertReflectionEquals with
     * lenient order and ignore defaults set as comparator modes.
     *
     * @param expected the expected object
     * @param actual   the given object
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertLenientEquals(Object expected, Object actual) throws AssertionFailedError {
        assertLenientEquals(null, expected, actual);
    }


    /**
     * Asserts that two objects are equal. Reflection is used to compare all fields of these values.
     * If they are not equal an AssertionFailedError is thrown.
     * <p/>
     * The comparator modes determine how strict to compare the values.
     *
     * @param expected the expected object
     * @param actual   the given object
     * @param modes    the comparator modes
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertReflectionEquals(Object expected, Object actual, ReflectionComparatorMode... modes) throws AssertionFailedError {
        assertReflectionEquals(null, expected, actual, modes);
    }


    /**
     * Asserts that two objects are equal. Reflection is used to compare all fields of these values.
     * If they are not equal an AssertionFailedError is thrown.
     * <p/>
     * This is identical to assertReflectionEquals with
     * lenient order and ignore defaults set as comparator modes.
     *
     * @param message  a message for when the assertion fails
     * @param expected the expected object
     * @param actual   the given object
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertLenientEquals(String message, Object expected, Object actual) throws AssertionFailedError {
        assertReflectionEquals(message, expected, actual, LENIENT_ORDER, IGNORE_DEFAULTS);
    }


    /**
     * Asserts that two objects are equal. Reflection is used to compare all fields of these values.
     * If they are not equal an AssertionFailedError is thrown.
     * <p/>
     * The comparator modes determine how strict to compare the values.
     *
     * @param message  a message for when the assertion fails
     * @param expected the expected object
     * @param actual   the given object
     * @param modes    the comparator modes
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertReflectionEquals(String message, Object expected, Object actual, ReflectionComparatorMode... modes) throws AssertionFailedError {
        ReflectionComparator reflectionComparator = createRefectionComparator(modes);
        Difference difference = reflectionComparator.getDifference(expected, actual);
        if (difference != null) {
            Assert.fail(getFailureMessage(message, difference));
        }
    }


    /**
     * @param message    a custom user-provided message, null if the user didn't provide a message
     * @param difference the difference, not null
     * @return a failure message describing the difference found
     */
    protected static String getFailureMessage(String message, Difference difference) {
        StringBuilder failureMessage = new StringBuilder();
        failureMessage.append(message == null ? "" : message + "\n");
        failureMessage.append(new DefaultDifferenceReport().createReport(difference));
        return failureMessage.toString();
    }


    /**
     * Asserts that the value of a property of an object is equal to the given value.
     * <p/>
     * Bean notation can be used to specify inner properties. Eg myArray[2].innerValue.
     * assertLenientEquals is used to check whether both values are equal.
     * <p/>
     * This is identical to assertPropertyReflectionEquals with
     * lenient order and ignore defaults set as comparator modes.
     *
     * @param propertyName          the property, not null
     * @param expectedPropertyValue the expected value
     * @param actualObject          the object that contains the property
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertPropertyLenientEquals(String propertyName, Object expectedPropertyValue, Object actualObject) throws AssertionFailedError {
        assertPropertyLenientEquals(null, propertyName, expectedPropertyValue, actualObject);
    }


    /**
     * Asserts that the value of a property of an object is equal to the given value.
     * <p/>
     * Bean notation can be used to specify inner properties. Eg myArray[2].innerValue.
     * assertReflectionEquals is used to check whether both values are equal.
     * <p/>
     * The comparator modes determine how strict to compare the values.
     *
     * @param propertyName          the property, not null
     * @param expectedPropertyValue the expected value
     * @param actualObject          the object that contains the property
     * @param modes                 the comparator modes
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertPropertyReflectionEquals(String propertyName, Object expectedPropertyValue, Object actualObject, ReflectionComparatorMode... modes) throws AssertionFailedError {
        assertPropertyReflectionEquals(null, propertyName, expectedPropertyValue, actualObject, modes);
    }


    /**
     * Asserts that the value of a property of an object is equal to the given value.
     * <p/>
     * Bean notation can be used to specify inner properties. Eg myArray[2].innerValue.
     * assertReflectionEquals is used to check whether both values are equal.
     * <p/>
     * This is identical to assertPropertyReflectionEquals with
     * lenient order and ignore defaults set as comparator modes.
     *
     * @param message               a message for when the assertion fails
     * @param propertyName          the property, not null
     * @param expectedPropertyValue the expected value
     * @param actualObject          the object that contains the property
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertPropertyLenientEquals(String message, String propertyName, Object expectedPropertyValue, Object actualObject) throws AssertionFailedError {
        assertPropertyReflectionEquals(message, propertyName, expectedPropertyValue, actualObject, LENIENT_ORDER, IGNORE_DEFAULTS);
    }


    /**
     * Asserts that the value of a property of an object is equal to the given value.
     * <p/>
     * Bean notation can be used to specify inner properties. Eg myArray[2].innerValue.
     * assertReflectionEquals is used to check whether both values are equal.
     * <p/>
     * The comparator modes determine how strict to compare the values.
     *
     * @param message               a message for when the assertion fails
     * @param propertyName          the property, not null
     * @param expectedPropertyValue the expected value
     * @param actualObject          the object that contains the property
     * @param modes                 the comparator modes
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertPropertyReflectionEquals(String message, String propertyName, Object expectedPropertyValue, Object actualObject, ReflectionComparatorMode... modes) throws AssertionFailedError {
        assertNotNull("Actual object is null.", actualObject);
        Object propertyValue = getProperty(actualObject, propertyName);
        String formattedMessage = formatMessage(message, "Incorrect value for property: " + propertyName);
        assertReflectionEquals(formattedMessage, expectedPropertyValue, propertyValue, modes);
    }


    /**
     * Asserts that a property of all objects in the collection are equal to the given values.
     * <p/>
     * Example:  assertPropertyEquals("id", myIdCollection, myObjectCollection) checks whether all values of the
     * id field of the myObjectCollection elements matches the values in the myIdCollection
     * <p/>
     * Bean notation can be used to specify inner properties. Eg myArray[2].innerValue.
     * assertReflectionEquals is used to check whether both values are equal.
     * <p/>
     * This is identical to assertPropertyReflectionEquals with
     * lenient order and ignore defaults set as comparator modes.
     *
     * @param propertyName           the property, not null
     * @param expectedPropertyValues the expected values
     * @param actualObjects          the objects that contain the property
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertPropertyLenientEquals(String propertyName, Collection<?> expectedPropertyValues, Collection<?> actualObjects) throws AssertionFailedError {
        assertPropertyLenientEquals(null, propertyName, expectedPropertyValues, actualObjects);
    }


    /**
     * Asserts that a property of all objects in the collection are equal to the given values.
     * <p/>
     * Example:  assertPropertyEquals("id", myIdCollection, myObjectCollection) checks whether all values of the
     * id field of the myObjectCollection elements matches the values in the myIdCollection
     * <p/>
     * Bean notation can be used to specify inner properties. Eg myArray[2].innerValue.
     * assertReflectionEquals is used to check whether both values are equal.
     * <p/>
     * The comparator modes determine how strict to compare the values.
     *
     * @param propertyName           the property, not null
     * @param expectedPropertyValues the expected values
     * @param actualObjects          the objects that contain the property
     * @param modes                  the comparator modes
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertPropertyReflectionEquals(String propertyName, Collection<?> expectedPropertyValues, Collection<?> actualObjects, ReflectionComparatorMode... modes) throws AssertionFailedError {
        assertPropertyReflectionEquals(null, propertyName, expectedPropertyValues, actualObjects, modes);
    }


    /**
     * Asserts that a property of all objects in the collection are equal to the given values.
     * <p/>
     * Example:  assertPropertyEquals("id", myIdCollection, myObjectCollection) checks whether all values of the
     * id field of the myObjectCollection elements matches the values in the myIdCollection
     * <p/>
     * Bean notation can be used to specify inner properties. Eg myArray[2].innerValue.
     * assertReflectionEquals is used to check whether both values are equal.
     * <p/>
     * This is identical to assertPropertyReflectionEquals with
     * lenient order and ignore defaults set as comparator modes.
     *
     * @param message                a message for when the assertion fails
     * @param propertyName           the property, not null
     * @param expectedPropertyValues the expected values, not null
     * @param actualObjects          the objects that contain the property
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertPropertyLenientEquals(String message, String propertyName, Collection<?> expectedPropertyValues, Collection<?> actualObjects) throws AssertionFailedError {
        assertPropertyReflectionEquals(message, propertyName, expectedPropertyValues, actualObjects, LENIENT_ORDER, IGNORE_DEFAULTS);
    }

    /**
     * All fields are checked for null values (except the static ones).
     * Private fields are also checked.
     * This is NOT recursive, only the values of the first object will be checked.
     * An assertion error will be thrown when a property is null.
     * 
     * @param message    a message for when the assertion fails
     * @param object     the object that will be checked for null values.
     */
    public static void assertPropertiesNotNull(String message, Object object) {
        Set<Field> fields = ReflectionUtils.getAllFields(object.getClass());
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                String formattedMessage = formatMessage(message, "Property '" + field.getName() + "' in object '" + object.toString() + "' is null ");
                assertNotNull(formattedMessage, ReflectionUtils.getFieldValue(object, field));
            }
        }

    }
    
    
    /**
     * Asserts that a property of all objects in the collection are equal to the given values.
     * <p/>
     * Example:  assertPropertyEquals("id", myIdCollection, myObjectCollection) checks whether all values of the
     * id field of the myObjectCollection elements matches the values in the myIdCollection
     * <p/>
     * Bean notation can be used to specify inner properties. Eg myArray[2].innerValue.
     * assertReflectionEquals is used to check whether both values are equal.
     * <p/>
     * The comparator modes determine how strict to compare the values.
     *
     * @param message                a message for when the assertion fails
     * @param propertyName           the property, not null
     * @param expectedPropertyValues the expected values, not null
     * @param actualObjects          the objects that contain the property
     * @param modes                  the comparator modes
     * @throws AssertionFailedError when both objects are not equals
     */
    public static void assertPropertyReflectionEquals(String message, String propertyName, Collection<?> expectedPropertyValues, Collection<?> actualObjects, ReflectionComparatorMode... modes) throws AssertionFailedError {
        assertNotNull("Actual object list is null.", actualObjects);
        Collection<?> actualPropertyValues = CollectionUtils.collect(actualObjects, new OgnlTransformer(propertyName));
        assertReflectionEquals(message, expectedPropertyValues, actualPropertyValues, modes);
    }


    /**
     * Formats the exception message.
     *
     * @param suppliedMessage the user supplied message
     * @param specificMessage the reason
     * @return the formatted message
     */
    protected static String formatMessage(String suppliedMessage, String specificMessage) {
        if (StringUtils.isEmpty(suppliedMessage)) {
            return specificMessage;
        }
        return suppliedMessage + "\n" + specificMessage;
    }


    /**
     * Evaluates the given OGNL expression, and returns the corresponding property value from the given object.
     *
     * @param object         The object on which the expression is evaluated
     * @param ognlExpression The OGNL expression that is evaluated
     * @return The value for the given OGNL expression
     */
    protected static Object getProperty(Object object, String ognlExpression) {
        try {
            OgnlContext ognlContext = new OgnlContext();
            ognlContext.setMemberAccess(new DefaultMemberAccess(true));
            Object ognlExprObj = Ognl.parseExpression(ognlExpression);
            return Ognl.getValue(ognlExprObj, ognlContext, object);
        } catch (OgnlException e) {
            throw new UnitilsException("Failed to get property value using OGNL expression " + ognlExpression, e);
        }
    }

    /**
     * All fields that have a getter with the same name will be checked by an assertNotNull.
     * Other fields will be ignored
     * 
     * @param message
     * @param object
     */
    public static void assertAccessablePropertiesNotNull(String message, Object object) {

        Set<Field> fields = ReflectionUtils.getAllFields(object.getClass());
        for (Field field : fields) {
            Method getter = ReflectionUtils.getGetter(object.getClass(), field.getName(), false);
            if (getter != null) {
                Object result = null;

                try {
                    result = getter.invoke(object, ArrayUtils.EMPTY_OBJECT_ARRAY);
                } catch (Exception e) {
                    throw new UnitilsException(e);
                }
                String formattedMessage = formatMessage(message, "Property '" + field.getName() + "' in object '" + object.toString() + "' is null ");
                assertNotNull(formattedMessage, result);
            }

        }
    }

    /**
     * A commons collections transformer that takes an object and returns the value of the property that is
     * specified by the given ognl expression.
     */
    protected static class OgnlTransformer implements Transformer {

        /* The ognl expression */
        private String ognlExpression;

        /**
         * Creates  a transformer with the given ognl expression.
         *
         * @param ognlExpression The expression, not null
         */
        public OgnlTransformer(String ognlExpression) {
            this.ognlExpression = ognlExpression;
        }

        /**
         * Transforms the given object in the value of the property that is specified by the ognl expression.
         *
         * @param object The object
         * @return The value, null if object was null
         */
        public Object transform(Object object) {
            if (object == null) {
                return null;
            }
            return getProperty(object, ognlExpression);
        }
    }

}