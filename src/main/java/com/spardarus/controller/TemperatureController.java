package com.spardarus.controller;

import com.spardarus.service.temperature.TemperatureSensor;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class TemperatureController {

    private final TemperatureSensor temperatureSensor;

    @CrossOrigin()
    @GetMapping("/temperature-stream")
    public SseEmitter getTemperatureStream(HttpServletRequest httpServletRequest) {
        RxSseEmitter rxSseEmitter = new RxSseEmitter();
        System.out.println("Get temperature-stream, httpServletRequest: " + httpServletRequest);
        temperatureSensor
                .temperatureObservable()
                .subscribe(rxSseEmitter.getSubscriber());
        return rxSseEmitter;
    }
}
