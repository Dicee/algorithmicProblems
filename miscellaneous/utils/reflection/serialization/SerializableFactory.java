package miscellaneous.utils.reflection.serialization;

import static miscellaneous.utils.check.Check.notNull;
import static miscellaneous.utils.exceptions.ExceptionUtils.uncheckExceptionsAndGet;

import java.io.Serializable;

public abstract class SerializableFactory<T> implements Serializable {
	private static final long	serialVersionUID	= 1L;

	public abstract T create();

    public static <T extends Serializable> SerializableFactory<T> of(T instance) {
        return new SimpleWrapperFactory<>(instance);
    }

    public static <T> SerializableFactory<T> fromDefaultConstructor(Class<T> clazz) {
        return new FromConstructorFactory<>(new SerializableConstructor<>(clazz));
    }

    // this method disambiguates the other two *fromConstructor*
    public static <T> SerializableFactory<T> fromConstructor(SerializableConstructor<T> constructor) {
        return new FromConstructorFactory<>(constructor, new Object[0]);
    }

    public static <T> SerializableFactory<T> fromConstructor(SerializableConstructor<T> constructor, Serializable... params) {
        return new FromConstructorFactory<>(constructor, params);
    }

    @SafeVarargs
	public static <T> SerializableFactory<T> fromConstructor(SerializableConstructor<T> constructor, SerializableFactory<T>... paramFactories) {
        return new CompoundFactory<>(constructor, paramFactories);
    }

    private static class SimpleWrapperFactory<T extends Serializable> extends SerializableFactory<T> {
		private static final long	serialVersionUID	= 1L;
		private final T instance;

        private SimpleWrapperFactory(T instance) { this.instance = notNull(instance); }

        @Override
        public T create() { return instance; }
    }

    private static class FromConstructorFactory<T> extends SerializableFactory<T> {
		private static final long	serialVersionUID	= 1L;
		private final SerializableConstructor<T> constructor;
        private final Serializable[] params;

        private FromConstructorFactory(SerializableConstructor<T> constructor, Serializable... params) {
            this.constructor = notNull(constructor);
            this.params      = notNull(params);
        }

        @Override
        public T create() { return uncheckExceptionsAndGet(() -> constructor.get().newInstance(params)); }
    }

    private static class CompoundFactory<T> extends SerializableFactory<T> {
		private static final long	serialVersionUID	= 1L;
		private final SerializableConstructor<T> constructor;
        private final SerializableFactory<?>[] factories;

        public CompoundFactory(SerializableConstructor<T> constructor, SerializableFactory<?>... factories) {
            this.constructor = notNull(constructor);
            this.factories   = notNull(factories);
        }

        @Override
        public T create() {
            Object[] params = new Object[factories.length];
            for (int i=0 ; i<params.length ; i++) params[i] = factories[i].create();
            return uncheckExceptionsAndGet(() -> constructor.get().newInstance(params));
        }
    }
}
