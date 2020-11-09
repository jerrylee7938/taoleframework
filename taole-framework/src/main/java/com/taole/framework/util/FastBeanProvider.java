package com.taole.framework.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.opensymphony.provider.BeanProvider;
import com.opensymphony.provider.ProviderConfigurationException;

public class FastBeanProvider implements BeanProvider {

    //~ Static fields/initializers /////////////////////////////////////////////

    private static String GET = "get";
    //private static String SET = "set";
    private static String IS = "is";

    //~ Methods ////////////////////////////////////////////////////////////////

    public boolean setProperty(Object object, String property, Object value) {
        if ((property == null) || (object == null)) {
            return false;
        }

        // Split out property on dots ( "person.name.first" -> "person","name","first" -> getPerson().getName().getFirst() )
        StringTokenizer st = new StringTokenizer(property, ".");

        if (st.countTokens() == 0) {
            return false;
        }

        // Holder for Object at current depth along chain.
        Object current = object;

        try {
            // Loop through properties in chain.
            for (int i = 0; st.hasMoreTokens(); i++) {
                String currentPropertyName = st.nextToken();

                if (i < st.countTokens()) {
                    // This is a getter
                    current = invokeProperty(current, currentPropertyName);
                } else {
                    // Final property in chain, hence setter
                    try {
                        // Call setter
                        //Class cls = current.getClass();
                        PropertyDescriptor pd = new PropertyDescriptor(currentPropertyName, current.getClass());
                        pd.getWriteMethod().invoke(current, new Object[] {value});

                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
            }

            // Return holder Object
            return true;
        } catch (NullPointerException e) {
            // It is very likely that one of the properties returned null. If so, catch the exception and return null.
            return false;
        }
    }

    public Object getProperty(Object object, String property) {
        if ((property == null) || (object == null)) {
            return null;
        }
        // Split out property on dots ( "person.name.first" -> "person","name","first" -> getPerson().getName().getFirst() )
        StringTokenizer st = new StringTokenizer(property, ".");

        if (st.countTokens() == 0) {
            return null;
        }

        // Holder for Object at current depth along chain.
        Object result = object;

        try {
            // Loop through properties in chain.
            while (st.hasMoreTokens()) {
                String currentPropertyName = st.nextToken();

                // Assign to holder the next property in the chain.
                result = invokeProperty(result, currentPropertyName);
            }

            // Return holder Object
            return result;
        } catch (NullPointerException e) {
            // It is very likely that one of the properties returned null. If so, catch the exception and return null.
            return null;
        }
    }

    public void destroy() {
    }

    public void init() throws ProviderConfigurationException {
    }

    /**
     * Convert property name into getProperty name ( "something" -> "getSomething" )
     */
    private String createMethodName(String prefix, String propertyName) {
        return prefix + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
    }

    /**
     * Invoke the method/field getter on the Object.
     * It tries (in order) obj.getProperty(), obj.isProperty(), obj.property(), obj.property.
     */
    private Object invokeProperty(Object obj, String property) {
        if ((property == null) || (property.length() == 0)) {
            return null; // just in case something silly happens.
        }

        Class<?> cls = obj.getClass();
        Object[] oParams = {};
        //Class[] cParams = {};

        try {
            // First try object.getProperty()
            //Method method = cls.getMethod(createMethodName(GET, property), cParams);
        	Method method = getReflectMethod(cls, property, GET);
            return method.invoke(obj, oParams);
        } catch (Exception e1) {
            try {
                // First try object.isProperty()
                //Method method = cls.getMethod(createMethodName(IS, property), cParams);
            	Method method = getReflectMethod(cls, property, IS);
                return method.invoke(obj, oParams);
            } catch (Exception e2) {
                try {
                    // Now try object.property()
                    //Method method = cls.getMethod(property, cParams);
                	Method method = getReflectMethod(cls, property, null);
                    return method.invoke(obj, oParams);
                } catch (Exception e3) {
                    try {
                        // Now try object.property()
                        Field field = cls.getField(property);
                        return field.get(obj);
                    } catch (Exception e4) {
                        // oh well
                        return null;
                    }
                }
            }
        }
    }
    
    static Map<String, Object> reflectMethodMap = new HashMap<String, Object>();

    public Method getReflectMethod(Class<?> cls, String property, String methodType) throws SecurityException, NoSuchMethodException {
    	String getMethodName = methodType != null ? createMethodName(methodType, property) : property;
    	String key = cls.getName() + "@" + getMethodName;
    	Method method = (Method) reflectMethodMap.get(key);
    	if (method != null) {
    		return method;
    	}
    	method = cls.getMethod(getMethodName, new Class[0]);
    	reflectMethodMap.put(key, method);
    	return method;
    }
    
    
}
