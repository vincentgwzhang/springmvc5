package org.personal.controller;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("metrics")
public class MetricStatisticsController {

    @Autowired
    private Meter meter1;

    @Autowired
    private Meter meter2;

    @Autowired
    private Histogram histogram;

    @Autowired
    private Counter counter;

    @Autowired
    private Timer timer;

    @GetMapping("funA")
    public ResponseEntity<Void> callFunctionA() {
        meter1.mark();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("funB")
    public ResponseEntity<Void> callFunctionB() {
        meter2.mark();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("fun2")
    public ResponseEntity<Void> callFunction2() {
        histogram.update(new Random().nextInt(100));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("fun3")
    public ResponseEntity<Void> callFunction3() {
        counter.inc();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("fun4")
    public ResponseEntity<Void> callFunction4() {
        final Timer.Context context = timer.time();
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
        }
        catch (Exception e) {}
        finally {
            context.stop();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
