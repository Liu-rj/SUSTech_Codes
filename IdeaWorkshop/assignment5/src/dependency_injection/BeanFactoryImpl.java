package dependency_injection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Properties;

/**
 * TODO you should complete the class
 */
public class BeanFactoryImpl implements BeanFactory {

    // {testclass.J=testclass.JImpl, testclass.F=testclass.FEnhanced, testclass.E=testclass.EImpl}
    Properties injectProperties;
    // {d.val=10, j.integers=1-4-8-34-14-6, l.val=true}
    Properties valueProperties;

    @Override
    public void loadInjectProperties(File file) {
        try {
            injectProperties = new Properties();
            FileInputStream in = new FileInputStream(file);
            injectProperties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadValueProperties(File file) {
        try {
            valueProperties = new Properties();
            FileInputStream in = new FileInputStream(file);
            valueProperties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T createInstance(Class<T> clazz) {
        try {
            T ins;
            Constructor constructor = null;
            for (Constructor c : clazz.getDeclaredConstructors()) {
                if (c.getAnnotation(Inject.class) != null) {
                    constructor = c;
                    break;
                }
            }
            if (constructor == null) {
                ins = (T) clazz.getDeclaredConstructor().newInstance();
            } else {
                Parameter[] parameters = constructor.getParameters();
                Object[] objects = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    Parameter p = parameters[i];
                    if (p.getAnnotation(Value.class) != null) {
                        Value valueAnnotation = p.getAnnotation(Value.class);
                        String property = valueProperties.getProperty(valueAnnotation.value());
                        if (p.getType() == boolean.class) {
                            objects[i] = Boolean.parseBoolean(property);
                        } else if (p.getType() == float.class) {
                            objects[i] = Float.parseFloat(property);
                        } else if (p.getType() == double.class) {
                            objects[i] = Double.parseDouble(property);
                        } else if (p.getType() == char.class) {
                            objects[i] = property.charAt(0);
                        } else {
                            String[] values = property.split(valueAnnotation.delimiter());
                            int min = valueAnnotation.min();
                            int max = valueAnnotation.max();
                            if (p.getType() == String.class) {
                                for (String value : values) {
                                    if (value.length() > min && value.length() < max) {
                                        objects[i] = value;
                                        break;
                                    }
                                }
                            } else if (p.getType() == byte.class) {
                                for (String value : values) {
                                    byte b = Byte.parseByte(value);
                                    if (b > min && b < max) {
                                        objects[i] = b;
                                        break;
                                    }
                                }
                            } else if (p.getType() == short.class) {
                                for (String value : values) {
                                    short b = Short.parseShort(value);
                                    if (b > min && b < max) {
                                        objects[i] = b;
                                        break;
                                    }
                                }
                            } else if (p.getType() == int.class) {
                                for (String value : values) {
                                    int b = Integer.parseInt(value);
                                    if (b > min && b < max) {
                                        objects[i] = b;
                                        break;
                                    }
                                }
                            } else if (p.getType() == long.class) {
                                for (String value : values) {
                                    long b = Long.parseLong(value);
                                    if (b > min && b < max) {
                                        objects[i] = b;
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        Object obj = p.getType().getDeclaredConstructor().newInstance();
                    }
                }
                ins = (T) constructor.newInstance(objects);
            }
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(Value.class) != null) {
                    Value valueAnnotation = field.getAnnotation(Value.class);
                    String property = valueProperties.getProperty(valueAnnotation.value());
                    if (field.getType() == boolean.class) {
                        field.setAccessible(true);
                        field.set(ins, Boolean.parseBoolean(property));
                        field.setAccessible(false);
                    } else if (field.getType() == float.class) {
                        field.setAccessible(true);
                        field.set(ins, Float.parseFloat(property));
                        field.setAccessible(false);
                    } else if (field.getType() == double.class) {
                        field.setAccessible(true);
                        field.set(ins, Double.parseDouble(property));
                        field.setAccessible(false);
                    } else if (field.getType() == char.class) {
                        field.setAccessible(true);
                        field.set(ins, property.charAt(0));
                        field.setAccessible(false);
                    } else {
                        String[] values = property.split(valueAnnotation.delimiter());
                        int min = valueAnnotation.min();
                        int max = valueAnnotation.max();
                        boolean find = false;
                        if (field.getType() == String.class) {
                            for (String value : values) {
                                if (value.length() > min && value.length() < max) {
                                    field.setAccessible(true);
                                    field.set(ins, value);
                                    field.setAccessible(false);
                                    find = true;
                                    break;
                                }
                            }
                        } else if (field.getType() == byte.class) {
                            for (String value : values) {
                                byte b = Byte.parseByte(value);
                                if (b > min && b < max) {
                                    field.setAccessible(true);
                                    field.set(ins, b);
                                    field.setAccessible(false);
                                    break;
                                }
                            }
                        } else if (field.getType() == short.class) {
                            for (String value : values) {
                                short b = Short.parseShort(value);
                                if (b > min && b < max) {
                                    field.setAccessible(true);
                                    field.set(ins, b);
                                    field.setAccessible(false);
                                    break;
                                }
                            }
                        } else if (field.getType() == int.class) {
                            for (String value : values) {
                                int b = Integer.parseInt(value);
                                if (b > min && b < max) {
                                    field.setAccessible(true);
                                    field.set(ins, b);
                                    field.setAccessible(false);
                                    break;
                                }
                            }
                        } else if (field.getType() == long.class) {
                            for (String value : values) {
                                long b = Long.parseLong(value);
                                if (b > min && b < max) {
                                    field.setAccessible(true);
                                    field.set(ins, b);
                                    field.setAccessible(false);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return ins;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
