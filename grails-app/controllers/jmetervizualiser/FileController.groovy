package jmetervizualiser

import org.apache.commons.vfs.FileObject
import grails.converters.JSON

class FileController extends GAEVfsAwareController {

    def index = {

    }

    def show = {
        List files = []

		FileObject resultsFileDirectory = getWorkingDirectory();
		resultsFileDirectory.children.each { FileObject file ->
			files.add file.name.baseName
		}

		render(view: 'show', model: [files: files])
    }

    def delete = {
		FileObject resultsFileDirectory = getWorkingDirectory();
		resultsFileDirectory.children.each { FileObject file ->
			file.delete()
		}

		redirect(action: 'show')
	}

    def create = {
        try {
            String requestedFilename = params.qqfile

            FileObject workingDirectory = getWorkingDirectory()
            FileObject newFile = workingDirectory.resolveFile(requestedFilename)

            if (!newFile.exists()) {
                def fileForUpload = request.inputStream
                newFile.createFile()
				newFile.getContent().getOutputStream().leftShift(fileForUpload.getText())
			} else {
				throw new RuntimeException("Already exists");
			}

			return render([success:true] as JSON)

		} catch (Exception e) {
			return render([success:false] as JSON)
		}
    }

    def showAll = {
        List files = []

		FileObject rootDirectory = getRootDirectory();
		rootDirectory.children.each { FileObject subDirectory ->
            files.add subDirectory.name.friendlyURI
            subDirectory.children.each { FileObject leafDirectory ->
                files.add leafDirectory.name.friendlyURI
                leafDirectory.children.each { FileObject file ->
                    files.add file.name.friendlyURI
                }
            }
        }

		render(view: 'show', model: [files: files])
    }

    def deleteAll = {
        FileObject rootDirectory = getRootDirectory();
		rootDirectory.children.each { FileObject subDirectory ->
			subDirectory.children.each { FileObject child ->
                println child.delete()
            }
        }

		redirect(action: 'showAll')
    }

}
