package jp.co.cyberagent.aeromock.brew

import org.gradle.api.tasks.TaskAction

/**
 * Task to show available released versions.
 * @author stormcat24
 */
class VersionsTask extends BaseTask {

    @TaskAction
    def process() {

        def versionsFile = aeromockDir.resolve(".versions").toFile()
        project.download {
            src "${githubRootRaw}/versions.txt"
            dest versionsFile
            quiet true
            onlyIfNewer true
        }

        if (!versionsFile.exists()) {
            throw new AeromockBrewJobFailedException("Failed to download versions info.")
        }

        println("\nReleased version in repository, as follows.")
        versionsFile.eachLine {
            if (currentVersion != null && it == currentVersion) {
                println(" \u001b[32m$it\u001b[00m")
            } else {
                println(" $it")
            }
        }
    }

}
