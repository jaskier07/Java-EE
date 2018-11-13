package pl.gda.pg.eti.kask.javaee.jsf.api.converters;

import lombok.NoArgsConstructor;
import pl.gda.pg.eti.kask.javaee.jsf.business.services.BreweryService;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.function.BiFunction;
import java.util.function.Function;

@NoArgsConstructor
public abstract class AbstractEntityConverter<V> implements ParamConverterProvider {

    @Inject
    BreweryService bookService;

    private Class<V> entityClass;

    private BiFunction<BreweryService, Long, V> retrieveFunction;

    private Function<V, Long> idExtractor;

    AbstractEntityConverter(Class<V> entityClass, Function<V, Long> idExtractor, BiFunction<BreweryService, Long, V> retrieveFunction) {
        this.entityClass = entityClass;
        this.retrieveFunction = retrieveFunction;
        this.idExtractor = idExtractor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType != entityClass) {
            return null;
        }

        return (ParamConverter<T>) new ParamConverter<V>() {
            @Override
            public V fromString(String value) {
                V entity = retrieveFunction.apply(bookService, Long.valueOf(value));
                if (entity == null) {
                    throw new NotFoundException();
                }
                return entity;
            }

            @Override
            public String toString(V book) {
                return idExtractor.apply(book).toString();
            }
        };
    }
}
