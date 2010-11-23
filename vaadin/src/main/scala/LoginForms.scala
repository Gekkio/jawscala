package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.LoginForm

object LoginForms {

  def loginListener(f: (LoginForm#LoginEvent) => _) = new LoginForm.LoginListener {
    def onLogin(e: LoginForm#LoginEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def loginFormLoginListener = loginListener _
  }

}
