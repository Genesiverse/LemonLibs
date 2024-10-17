package org.acsn1.lemonlibs.file;

import lombok.Getter;
import org.acsn1.lemonlibs.file.config.NewsFile;
import org.acsn1.lemonlibs.file.config.PopupFile;
import org.acsn1.lemonlibs.file.config.SidebarFile;
import org.acsn1.lemonlibs.file.config.SoundFile;

@Getter
public class FileLoader {

    private final SoundFile soundFile;
    private final SidebarFile sidebarFile;
    private final PopupFile popupFile;
    private final NewsFile newsFile;

    public FileLoader() {
        soundFile = new SoundFile();
        sidebarFile = new SidebarFile();
        popupFile = new PopupFile();
        newsFile = new NewsFile();
    }
}
