package fi.jawsy.jawscala
package vaadin

import com.vaadin.ui.UriFragmentUtility

object UriFragmentUtilities {

  def fragmentChangedListener(f: (UriFragmentUtility#FragmentChangedEvent) => _) = new UriFragmentUtility.FragmentChangedListener {
    def fragmentChanged(e: UriFragmentUtility#FragmentChangedEvent) = f(e)
  }

  object Implicits extends Implicits

  trait Implicits {
    implicit def uriFragmentUtilityFragmentChangedListener = fragmentChangedListener _
  }

}
