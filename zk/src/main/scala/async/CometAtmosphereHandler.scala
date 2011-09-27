package fi.jawsy.jawscala
package zk
package async

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.atmosphere.cpr.AtmosphereHandler
import org.atmosphere.cpr.AtmosphereResource
import org.atmosphere.cpr.AtmosphereResourceEvent
import org.zkoss.zk.ui.http.SessionResolverImpl
import org.zkoss.zk.ui.sys.SessionCtrl
import org.zkoss.zk.ui.sys.WebAppCtrl
import org.zkoss.zk.ui.http.WebManager
import org.zkoss.zk.ui.Session
import org.zkoss.zk.ui.Desktop
import org.zkoss.zk.ui.sys.DesktopCtrl

class CometAtmosphereHandler extends AtmosphereHandler[HttpServletRequest, HttpServletResponse] {

  def destroy() {}

  private def lookupSession(request: HttpServletRequest): Either[String, Session] =
    Option(WebManager.getSession(request.getServletContext, request, false)).toRight("Could not find session")

  private def lookupDesktopId(request: HttpServletRequest): Either[String, String] =
    Option(request.getParameter("dtid")).filter(!_.isEmpty).toRight("Could not find desktop id")

  private def lookupDesktop(session: Session, dtid: String): Either[String, Desktop] =
    session.getWebApp match {
      case webAppCtrl: WebAppCtrl =>
        Option(webAppCtrl.getDesktopCache(session).getDesktopIfAny(dtid)).toRight("Could not find desktop")
      case _ => Left("Web app does not implement WebAppCtrl")
    }

  private def lookupServerPush(desktop: Desktop): Either[String, AtmosphereServerPush] =
    desktop match {
      case desktopCtrl: DesktopCtrl =>
        desktopCtrl.getServerPush match {
          case null => Left("Server push is not enabled")
          case serverPush: AtmosphereServerPush => Right(serverPush)
          case _ => Left("Unsupported server push implementation")
        }
      case _ => Left("Desktop does not implement DesktopCtrl")
    }

  def onRequest(resource: AtmosphereResource[HttpServletRequest, HttpServletResponse]) {
    val response = resource.getResponse
    val request = resource.getRequest
    response.setContentType("text/plain")

    (for {
      session <- lookupSession(request).right
      dtid <- lookupDesktopId(request).right
      desktop <- lookupDesktop(session, dtid).right
      serverPush <- lookupServerPush(desktop).right
    } yield {
      serverPush.updateResource(resource)
    }).fold(
      { error =>
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
        response.getWriter.write(error)
      },
      { success =>
      })
  }

  def onStateChange(event: AtmosphereResourceEvent[HttpServletRequest, HttpServletResponse]) {
  }

}