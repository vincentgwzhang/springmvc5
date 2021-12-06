package org.personal.controller;

import org.personal.dto.EventMessageWrapper;
import org.personal.service.EventService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("event")
public record EventController(EventService eventService) {

    @PostMapping("send")
    public void emitEvent(@RequestBody EventMessageWrapper eventMessageWrapper) {
        eventService.emitEvent(eventMessageWrapper.getMessage());
    }

}
