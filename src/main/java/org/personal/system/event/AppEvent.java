package org.personal.system.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@ToString
public class AppEvent extends ApplicationEvent {

    private String message;

    public AppEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

}
