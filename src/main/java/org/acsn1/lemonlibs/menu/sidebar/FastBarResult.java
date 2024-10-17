package org.acsn1.lemonlibs.menu.sidebar;

import lombok.Getter;

@Getter
public enum FastBarResult {

    SUCCESS(0), INVALID_TITLE(1), INVALID_SIZE(2), INVALID_PAGE(3), INVALID_WRAPPER(4);

    private final int id;
    FastBarResult(int id) {
        this.id = id;
    }

}
