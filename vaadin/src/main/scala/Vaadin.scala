package fi.jawsy.jawscala.vaadin

/**
 * Master object that contains all implicits.
 *
 * Unless you want to cherry-pick individual things, import the whole thing with
 * <pre>
 *   import fi.jawsy.jascala.vaadin.Vaadin._
 * </pre>
 */
object Vaadin
  extends Buttons.Implicits
  with ComponentContainers.Implicits
  with Containers.Implicits
  with FieldEventsUtil.Implicits
  with Items.Implicits
  with LayoutEventsUtil.Implicits
  with LoginForms.Implicits
  with MouseEventsUtil.Implicits
  with PopupViews.Implicits
  with Properties.Implicits
  with SplitPanels.Implicits
  with Tables.Implicits
  with TabSheets.Implicits
  with Trees.Implicits
  with Uploads.Implicits
  with UriFragmentUtilities.Implicits
  with Windows.Implicits
  with Components.Implicits {

}
