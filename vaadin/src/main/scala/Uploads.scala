package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.Upload
import java.io.OutputStream

object Uploads {

  def succeededListener(f: (Upload.SucceededEvent) => _) = new Upload.SucceededListener {
    def uploadSucceeded(e: Upload.SucceededEvent) = f(e)
  }

  def finishedListener(f: (Upload.FinishedEvent) => _) = new Upload.FinishedListener {
    def uploadFinished(e: Upload.FinishedEvent) = f(e)
  }

  def progressListener(f: (Long, Long) => _) = new Upload.ProgressListener {
    def updateProgress(readBytes: Long, contentLength: Long) = f(readBytes, contentLength)
  }

  def receiver(f: (String, String) => OutputStream) = new Upload.Receiver {
    def receiveUpload(filename: String, mimeType: String) = f(filename, mimeType)
  }

  def startedListener(f: (Upload.StartedEvent) => _) = new Upload.StartedListener {
    def uploadStarted(e: Upload.StartedEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def uploadSucceededListener = succeededListener _
    implicit def uploadFinishedListener = finishedListener _
    implicit def uploadStartedListener = startedListener _
  }

}
