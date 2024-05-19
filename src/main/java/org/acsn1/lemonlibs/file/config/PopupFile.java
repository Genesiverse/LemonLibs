package org.acsn1.lemonlibs.file.config;

import lombok.Getter;
import org.acsn1.lemonlibs.LemonLibs;
import org.acsn1.lemonlibs.wrapper.FileWrapper;

@Getter
public class PopupFile extends FileWrapper {

    public PopupFile() {
        super(LemonLibs.getInstance(), "plugins/GenesiCore/LemonLibs/", "popup");
    }

}
