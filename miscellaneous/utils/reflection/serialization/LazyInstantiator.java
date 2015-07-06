package miscellaneous.utils.reflection.serialization;

import java.io.Serializable;

import miscellaneous.utils.check.Check;

public class LazyInstantiator<T> implements Serializable {
	private static final long	serialVersionUID	= 1L;

	private final SerializableFactory<T> factory;
    private transient T instance = null;

    public LazyInstantiator(SerializableFactory<T> factory) { this.factory = Check.notNull(factory); }

    public T instance() {
        if (instance == null) instance = factory.create();
        return instance;
    }
}

