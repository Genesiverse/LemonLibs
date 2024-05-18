package org.acsn1.lemonlibs.file;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileLoader {

    @Getter private final SoundFile soundFile;
    @Getter private final SidebarFile sidebarFile;
    @Getter private final PopupFile popupFile;
    @Getter private final iNewsFile iNewsFile;

    public FileLoader() {
        soundFile = new SoundFile();
        sidebarFile = new SidebarFile();
        popupFile = new PopupFile();
        iNewsFile = new iNewsFile();
    }
}
