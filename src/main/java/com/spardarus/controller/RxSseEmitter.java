package com.spardarus.controller;

import com.spardarus.service.temperature.dto.Temperature;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rx.Subscriber;

import java.io.IOException;

public class RxSseEmitter extends SseEmitter {

    private static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;

    private final Subscriber<Temperature> subscriber;

    public RxSseEmitter() {
        super(SSE_SESSION_TIMEOUT);

        this.subscriber = new Subscriber<Temperature>() {

            @Override
            public void onNext(Temperature temperature) {
                try {
                    RxSseEmitter.this.send(temperature);
                    System.out.println("Temperature on Next: " + temperature.getTemp());
                } catch (IOException e) {
                    System.out.println("onNext error: " + e.getMessage());
                    unsubscribe();
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onComplete");
            }
        };

        onCompletion(subscriber::unsubscribe);
        onTimeout(() -> {
            subscriber.unsubscribe();
            System.out.println("TimeOut");
        });
    }

    public Subscriber<Temperature> getSubscriber() {
        return subscriber;
    }
}
