package org.personal.controller.util;

import java.beans.PropertyEditor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

public class MockMvcRequestBuilderUtils
{

    private static final PropertyEditorRegistrySupport propertyEditorRegistry = new SimpleTypeConverter();

    private MockMvcRequestBuilderUtils() {
    }

    public static void registerPropertyEditor(Class type, PropertyEditor propertyEditor) {
        propertyEditorRegistry.registerCustomEditor(type, propertyEditor);
    }

    public static MockHttpServletRequestBuilder postForm(String url, Object form) {
        final MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        final Map<String, String> formFields = getFormFields(form, new TreeMap<>(), StringUtils.EMPTY);
        formFields.forEach(mockHttpServletRequestBuilder::param);

        return mockHttpServletRequestBuilder;
    }

    private static Map<String, String> getFormFields(Object form, Map<String, String> formFields, String path) {
        final List<Field> fields = form != null ? Arrays.asList(FieldUtils.getAllFields(form.getClass())) : new ArrayList<>();
        for (Field field : fields) {
            final Class<?> fieldType = field.getType();
            final Object fieldValue = getFieldValue(form, field);
            if (fieldValue != null) {
                if (isIterable(fieldType)) {
                    final Iterable<?> iterableObject = getIterable(fieldValue, fieldType);
                    if (iterableObject != null) {
                        if (isComplexField(field)) {
                            int i = 0;
                            for (Object object : iterableObject) {
                                final String nestedPath = getPositionedField(path, field, i) + ".";
                                formFields.putAll(getFormFields(object, formFields, nestedPath));
                                i++;
                            }
                        } else {
                            formFields.putAll(getCollectionFields(iterableObject, path, field));
                        }
                    }
                } else if (isMap(fieldType)) {
                    final Map<?, ?> map = getMap(fieldValue, fieldType);
                    if (map != null) {
                        map.forEach((key, value) -> formFields.put(getPositionedField(path, field, getStringValue(key)), getStringValue(value)));
                    }
                } else {
                    if (!isComplexField(field) || hasPropertyEditorFor(fieldType)) {
                        // Resolve field's value either using a property editor or its string representation
                        formFields.put(path + field.getName(), getStringValue(fieldValue));
                    } else {
                        // Iterate over object's fields
                        final String nestedPath = getNestedPath(field);
                        formFields.putAll(getFormFields(ReflectionTestUtils.getField(form, field.getName()), formFields, nestedPath));
                    }
                }
            }
        }
        return formFields;
    }

    private static Map<?, ?> getMap(Object fieldValue, Class<?> type) {
        return Map.class.isAssignableFrom(type) ? (Map) fieldValue : null;
    }

    private static Iterable<?> getIterable(Object fieldValue, Class<?> type) {
        return Iterable.class.isAssignableFrom(type) ? (Iterable<?>) fieldValue : CollectionUtils.arrayToList(fieldValue);
    }

    private static Map<String, String> getCollectionFields(Iterable<?> iterable, String path, Field field) {
        final Map<String, String> fields = new TreeMap<>();
        int i = 0;
        for (Object object : iterable) {
            fields.put(getPositionedField(path, field, i), getStringValue(object));
            i++;
        }
        return fields;
    }

    private static String getPositionedField(String path, Field field, int position) {
        return getPositionedField(path, field, String.valueOf(position));
    }

    private static String getPositionedField(String path, Field field, String positionOrKey) {
        return String.format("%s%s[%s]", path, field.getName(), positionOrKey);
    }

    private static String getNestedPath(Field field) {
        return field.getName() + ".";
    }

    private static Object getFieldValue(Object form, Field field) {
        return ReflectionTestUtils.getField(form, field.getName());
    }

    private static String getStringValue(Object object) {
        if (object != null) {
            final PropertyEditor propertyEditor = getPropertyEditorFor(object);
            if (propertyEditor != null) {
                propertyEditor.setValue(object);
                return propertyEditor.getAsText();
            }
            return String.valueOf(object);
        }
        return StringUtils.EMPTY;
    }

    private static boolean hasPropertyEditorFor(Class<?> type) {
        return propertyEditorRegistry.hasCustomEditorForElement(type, null) ||
                propertyEditorRegistry.getDefaultEditor(type) != null;
    }

    private static PropertyEditor getPropertyEditorFor(Object object) {
        return propertyEditorRegistry.hasCustomEditorForElement(object.getClass(), null) ?
                propertyEditorRegistry.findCustomEditor(object.getClass(), null) :
                propertyEditorRegistry.getDefaultEditor(object.getClass());
    }

    private static boolean isComplexField(Field field) {
        if (isGeneric(field)) {
            final ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            final Type[] actualTypeArguments = genericType.getActualTypeArguments();
            return isComplexType((Class<?>) actualTypeArguments[0]);
        } else {
            return isComplexType(field.getType());
        }
    }

    private static boolean isGeneric(Field field) {
        return !(field.getGenericType() instanceof Class);
    }

    private static boolean isComplexType(Class<?> type) {
        if (type.getComponentType() != null) {
            return isComplexType(type.getComponentType());
        }
        return !ClassUtils.isPrimitiveOrWrapper(type)
                && !String.class.isAssignableFrom(type)
                && !Date.class.isAssignableFrom(type)
                && !Temporal.class.isAssignableFrom(type)
                && type.getSuperclass() != null
                && !Enum.class.isAssignableFrom(type.getSuperclass());
    }

    private static boolean isIterable(Class fieldClass) {
        return Iterable.class.isAssignableFrom(fieldClass) || Object[].class.isAssignableFrom(fieldClass);
    }

    private static boolean isMap(Class<?> type) {
        return Map.class.isAssignableFrom(type);
    }
}
