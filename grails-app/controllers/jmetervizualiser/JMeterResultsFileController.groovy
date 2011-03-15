package jmetervizualiser

import org.apache.commons.vfs.FileObject

class JMeterResultsFileController extends GAEVfsAwareController {

    def index = {

    }

    def show = {
        List files = []

		FileObject anonymousDirectory = getFile("gae://users/anonymous/");
		anonymousDirectory.children.each { FileObject file ->
			files.add file.name.baseName
		}

		render(view: 'show', model: [files: files])
    }

    def delete = {
		FileObject anonymousDirectory = getFile("gae://users/anonymous/");
		anonymousDirectory.children.each { FileObject file ->
			file.delete()
		}

		redirect(action: 'show')
	}
}
