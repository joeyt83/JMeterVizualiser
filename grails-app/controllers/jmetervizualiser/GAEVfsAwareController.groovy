package jmetervizualiser

import org.apache.commons.vfs.FileObject
import org.apache.commons.vfs.FileSystemManager
import com.newatlanta.commons.vfs.provider.gae.GaeVFS

class GAEVfsAwareController {

    FileSystemManager fsManager

    public FileObject getFile(String path) {
        fsManager = GaeVFS.getManager()
        return fsManager.resolveFile(path);
    }
}
