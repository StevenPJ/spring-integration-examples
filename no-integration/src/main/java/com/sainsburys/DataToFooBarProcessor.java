package com.sainsburys;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DataToFooBarProcessor implements DataProcessor {

    private final Consumer<Foo> fooAdapter;
    private final Consumer<Bar> barAdapter;

    @Override
    public void process(Data data) {
        if (data == null) {return; }
        fooAdapter.accept(dateToFooTransformer.apply(data));
        barAdapter.accept(dateToBarTransformer.apply(data));
    }

    private Function<Data, Foo> dateToFooTransformer = data -> new Foo(data.getValue());
    private Function<Data, Bar> dateToBarTransformer = data -> new Bar(data.getValue());
}
