package ru.z3r0ing.telegrambots.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Stereotype annotation for any bot handlers.
 * You can use it instead of {@link Component} to create bean of any handler type.
 *
 * @author Z3R0ing
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface BotHandler {

    @AliasFor(annotation = Component.class)
    String value() default "";

}
