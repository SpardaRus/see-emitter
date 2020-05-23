package com.spardarus.service.temperature;

import com.spardarus.service.temperature.dto.Temperature;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class TemperatureSensor {

    private final Random rnd = new Random();
    private final Observable<Temperature> temperatureObservable =
            Observable
                    .range(0, Integer.MAX_VALUE)
                    .concatMap(ignore -> Observable
                            .just(1)
                            .delay(rnd.nextInt(5000), TimeUnit.MILLISECONDS)
                            .map(ignore2 -> {
                                System.out.println("Before start prob");
                                return this.prob();
                            }))
                    .publish()
                    .refCount();

    public Observable<Temperature> temperatureObservable() {
        return temperatureObservable;
    }

    private Temperature prob() {
        double actualTemp = 16 + rnd.nextGaussian() * 10;
        System.out.println("Prob is actualTemp: " + actualTemp);
        return new Temperature(actualTemp);
    }

}
