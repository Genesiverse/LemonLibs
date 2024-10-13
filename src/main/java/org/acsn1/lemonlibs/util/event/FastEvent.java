package org.acsn1.lemonlibs.util.event;

import lombok.Getter;

@Getter
public abstract class FastEvent<T> {

    private final T t;
    public FastEvent(T t) {
        this.t = t;
    }

    public abstract void listen();

}
