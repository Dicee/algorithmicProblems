package miscellaneous.utils.reflection.serialization;

import static miscellaneous.utils.check.Check.notNull;
import static miscellaneous.utils.exceptions.ExceptionUtils.uncheckExceptionsAndGet;

import java.io.Serializable;
import java.lang.reflect.Constructor;

public class SerializableConstructor<T> implements Serializable {
	private static final long	serialVersionUID	= 1L;

	private final Class<T> targetClass;
    private final Class<?>[] parametersClasses;

    public SerializableConstructor(Class<T> targetClass, Class<?>... parametersClasses) {
        this.targetClass       = notNull(targetClass);
        this.parametersClasses = notNull(parametersClasses);
    }

    public Constructor<T> get() { return uncheckExceptionsAndGet(() -> targetClass.getConstructor(parametersClasses)); }
}

