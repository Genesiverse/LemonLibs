package org.acsn1.lemonlibs.file.config;

import lombok.Getter;
import org.acsn1.lemonlibs.LemonLibs;
import org.acsn1.lemonlibs.wrapper.FileWrapper;

@Getter
public class SidebarFile extends FileWrapper {

    public SidebarFile() {
        super(LemonLibs.getInstance(), "plugins/GenesiCore/LemonLibs/", "sidebar");

    }

}
