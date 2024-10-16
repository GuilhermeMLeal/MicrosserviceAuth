package br.com.AuthenticationMicrosservice.domain.persistence;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.function.Function;

@Converter
public abstract class AbstractEnumConverter<E extends Enum<E> & PersistableEnum<T>, T>
        implements AttributeConverter<E, T> {

    private Function<T, E> fromCodeToEnum;

    protected AbstractEnumConverter(Function<T, E> fromCodeToEnum) {
        this.fromCodeToEnum = fromCodeToEnum;
    }

    @Override
    public T convertToDatabaseColumn(E persistableEnum) {
        return persistableEnum == null ? null : persistableEnum.getCode();
    }

    @Override
    public E convertToEntityAttribute(T code) {
        return code == null ? null : fromCodeToEnum.apply(code);
    }
}
