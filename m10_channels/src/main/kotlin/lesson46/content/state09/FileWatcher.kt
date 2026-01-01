package lesson46.content.state09

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.apache.commons.io.monitor.FileAlterationListener
import org.apache.commons.io.monitor.FileAlterationMonitor
import org.apache.commons.io.monitor.FileAlterationObserver
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolute
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

suspend fun main() {
    val path = Path(".")
    watch(path, 500.milliseconds, 30.seconds)
        .collect { println("File ${it.name} has changed, and now has size ${it.length}") }
}

fun watch(path: Path, interval: Duration, duration: Duration) = callbackFlow {
    println("Monitoring \"${path.absolute()}\" for $duration.")

    val listener = object : DefaultFileListener {
        override fun onFileChange(file: File?) {
            if (file != null) trySend(FileChange(file.name, file.length()))
        }
    }

    val observer = FileAlterationObserver(path.toFile()).apply { addListener(listener) }
    val monitor = FileAlterationMonitor(interval.inWholeMilliseconds, observer)

    launch {
        delay(duration)
        close()
    }

    monitor.start()
    awaitClose {
        monitor.stop()
    }
}

data class FileChange(val name: String, val length: Long)

interface DefaultFileListener : FileAlterationListener {
    override fun onDirectoryChange(directory: File?) {}
    override fun onDirectoryCreate(directory: File?) {}
    override fun onDirectoryDelete(directory: File?) {}
    override fun onFileChange(file: File?) {}
    override fun onFileCreate(file: File?) {}
    override fun onFileDelete(file: File?) {}
    override fun onStart(observer: FileAlterationObserver?) {}
    override fun onStop(observer: FileAlterationObserver?) {}
}
