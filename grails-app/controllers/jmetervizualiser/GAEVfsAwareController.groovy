package jmetervizualiser

import org.apache.commons.vfs.FileObject
import org.apache.commons.vfs.FileSystemManager
import com.newatlanta.commons.vfs.provider.gae.GaeVFS

class GAEVfsAwareController {

    FileSystemManager fsManager

    public FileObject getWorkingDirectory() {
        String protocol = "gae://"
        String sessionId = session.getId()
        String path = "${protocol}/sessions/${sessionId}"
        fsManager = GaeVFS.getManager()
        FileObject workingDirectory = fsManager.resolveFile(path);
        if(!workingDirectory.exists()) {
            workingDirectory.createFolder()
        }
        return workingDirectory
    }

    public FileObject getRootDirectory() {
        String protocol = "gae://"
        String path = "${protocol}/"
        fsManager = GaeVFS.getManager()
        return fsManager.resolveFile(path);
    }
}
