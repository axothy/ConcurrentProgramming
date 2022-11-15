package lab_reflection;


import jdk.jfr.Name;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Native;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Необходимо реализовать метод reflectiveToString, который для произвольного объекта
 * возвращает его строковое описание в формате:
 * <p>
 * {field_1: value_1, field_2: value_2, ..., field_n: value_n}
 * <p>
 * где field_i - имя поля
 * value_i - его строковое представление (String.valueOf),
 * за исключением массивов, для которых value формируется как:
 * [element_1, element_2, ..., element_m]
 * где element_i - строковое представление элемента (String.valueOf)
 * элементы должны идти в том же порядке, что и в массиве.
 * <p>
 * Все null'ы следует представлять строкой "null".
 * <p>
 * Порядок полей
 * Сначала следует перечислить в алфавитном порядке поля, объявленные непосредственно в классе объекта,
 * потом в алфавитном порядке поля объявленные в родительском классе и так далее по иерархии наследования.
 * Примеры можно посмотреть в тестах.
 * <p>
 * Какие поля выводить
 * Необходимо включать только нестатические поля. Также нужно пропускать поля, помеченные аннотацией @SkipField
 * <p>
 * Упрощения
 * Чтобы не усложнять задание, предполагаем, что нет циклических ссылок, inner классов, и transient полей
 * <p>
 * Реализация
 * В пакете ru.mail.polis.homework.reflection можно редактировать только этот файл
 * или добавлять новые (не рекомендуется, т.к. решение вполне умещается тут в несколько методов).
 * Редактировать остальные файлы нельзя.
 * <p>
 * Баллы
 * В задании 3 уровня сложности, для каждого свой набор тестов:
 * Easy - простой класс, нет наследования, массивов, статических полей, аннотации SkipField (4 балла)
 * Easy + Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 * Easy + Medium + Hard - нужно реализовать все требования задания (10 баллов)
 * <p>
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */

public class ReflectionToStringHelper {
    private static StringBuilder stringBuilder = new StringBuilder();
    private static Set<Class<?>> classes = new HashSet<>();

    private static void appendClassesSet(Class<?> c) {
        classes.add(c);
        if (c.getSuperclass() != null) {
            classes.add(c.getSuperclass());
        }
        while (c.getSuperclass() != null) {
            classes.add(c.getSuperclass());
            c = c.getSuperclass();
        }
    }

    private static List<Field> getSortedDeclaredFields(Class<?> c) {
        return Arrays.stream(c.getDeclaredFields())
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList());
    }

    public static String reflectiveToString(Object object) {
        appendClassesSet(object.getClass());

        if (object == null) {
            return "null";
        }

        stringBuilder.append("{");
        for (Class<?> c : classes) {
            for (Field f : getSortedDeclaredFields(c)) {
                if (!Modifier.isStatic(f.getModifiers()) && (f.getAnnotation(SkipField.class) == null)) {
                    f.setAccessible(true);

                    if (f.getType().isArray()) {
                        List<Object> array = new ArrayList<>();
                        try {
                            for (int i = 0; i < Array.getLength(f.get(object)); i++) {
                                array.add(Array.get(f.get(object), i));
                            }
                            stringBuilder.append(f.getName() + ": " + array + ", ");
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    } else try {
                        stringBuilder.append(f.getName() + ": " + f.get(object) + ", ");
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length() - 1, "}");
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(reflectiveToString(new Object()));
    }
}