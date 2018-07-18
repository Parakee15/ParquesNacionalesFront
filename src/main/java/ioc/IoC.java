package ioc;

import java.beans.ConstructorProperties;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class IoC {

  private final Map<String, Object> instances = new LinkedHashMap<>();

  public <T> T lookup(@Nullable String name, @Nonnull Class<T> clazz) {
    Optional<Object> objectOptional = lookupByType(clazz).or(() -> {
      if (name != null) {
        return lookupByNameAndType(name, clazz);
      }
      return Optional.empty();
    });
    return (T) objectOptional.orElseThrow(() -> new NoSuchElementException(
        String.format("Not found with name %s and type %s", name, clazz.getSimpleName())));
  }

  public <T> T lookup(@Nonnull Class<T> clazz) {
    return lookup(null, clazz);
  }

  public void register(@Nonnull Class<?> objectClass) {
    register(objectClass.getSimpleName().toLowerCase(), objectClass);
  }

  public void registerInstance(@Nonnull String name, @Nonnull Object instance) {
    instances.put(name, instance);
  }

  public void log() {
    instances.forEach((k, v) -> System.out
        .println(String.format("name: %s -> instanceOf: %s", k, v.getClass().getCanonicalName())));
  }

  private void register(String name, Class<?> objectClass) {
    List<Constructor<?>> constructors = Arrays.asList(objectClass.getConstructors());
    if (constructors.size() > 1) {
      throw new IllegalArgumentException(
          "The registered class must have 1 public constructor or none"
      );
    }

    try {
      Optional<Object> objectOptional = withDefaultConstructor(objectClass);
      if (objectOptional.isPresent()) {
        instances.put(name, objectOptional.get());
      } else {
        instances.put(name, withConstructor(constructors.get(0)));
      }
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private Optional<Object> withDefaultConstructor(Class<?> objectClass)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Constructor<?>[] constructors = objectClass.getConstructors();
    if ((constructors.length == 1 && constructors[0].getParameterCount() == 0)
        || constructors.length == 0) {
      return Optional.of(objectClass.getConstructor().newInstance());
    }
    return Optional.empty();
  }

  private Object withConstructor(Constructor<?> constructor)
      throws IllegalAccessException, InvocationTargetException, InstantiationException {
    List<Parameter> defParameters = Arrays.asList(constructor.getParameters());
    List<Object> args = new ArrayList<>();
    Iterator<String> argsNames = constructorArgsNames(constructor).iterator();
    defParameters.forEach(p -> args.add(lookup(argsNames.next(), p.getType())));
    return constructor.newInstance(args.toArray());
  }

  private Optional<Object> lookupByType(Class<?> clazz) {
    List<Object> objects = instances.values()
        .stream().filter(clazz::isInstance).collect(Collectors.toList());
    if (objects.size() == 1) {
      return Optional.of(objects.get(0));
    }
    return Optional.empty();
  }

  private Optional<Object> lookupByNameAndType(String name, Class<?> clazz) {
    Object o = instances.get(name);
    if (clazz.isInstance(o)) {
      return Optional.of(o);
    }
    return Optional.empty();
  }

  private List<String> constructorArgsNames(Constructor<?> constructor) {
    if (constructor.isAnnotationPresent(ConstructorProperties.class)) {
      return Arrays.asList(constructor.getAnnotation(ConstructorProperties.class).value());
    }
    return Arrays.stream(constructor.getParameters()).map(Parameter::getName)
        .collect(Collectors.toList());
  }
}
