package ch.makery.address.view
import ch.makery.address.model.Jewellery
import ch.makery.address.MainApp
import scalafx.event.ActionEvent 
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.Alert
import scala.util.{Failure, Success}

@sfxml
class MainController(){

    def addJewelleryPage(action : ActionEvent) = {
        val jewellery = new Jewellery("","")
        val okClicked = MainApp.showAddJewelleryPage(jewellery);
        if (okClicked) {
            jewellery.save() match {
                case Success(x) =>
                MainApp.jewelleryData += jewellery
                case Failure(e) =>
                val alert = new Alert(Alert.AlertType.Warning) {
                    initOwner(MainApp.stage)
                    title = "Failed to Save"
                    headerText = "Database Error"
                    contentText = "Database problem filed to save changes"
                }.showAndWait()
            }
        }
    }

    def searchJewelleryPage(action : ActionEvent) = {
        val jewellery = new Jewellery("","")
        MainApp.showSearchJewelleryPage(jewellery);
    }
}
