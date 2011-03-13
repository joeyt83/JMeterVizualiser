package jmetervizualiser

import org.apache.commons.vfs.FileObject
import com.newatlanta.commons.vfs.provider.gae.GaeVFS
import org.apache.commons.vfs.FileSystemManager

class JMeterResultsFileController {

    FileSystemManager fsManager = GaeVFS.getManager();

    def index = {

    }

    def show = {
        List files = []

		FileObject anonymousDirectory = fsManager.resolveFile("gae://users/anonymous/");
		anonymousDirectory.children.each { FileObject file ->
			files.add file.name.baseName
		}

		render(view: 'show', model: [files: files])
    }

    def delete = {
		FileObject anonymousDirectory = fsManager.resolveFile("gae://users/anonymous/");
		anonymousDirectory.children.each { FileObject file ->
			file.delete()
		}
		redirect(action: 'show')
	}
}
