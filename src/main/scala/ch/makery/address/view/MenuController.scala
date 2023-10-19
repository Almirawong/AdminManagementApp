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
class MenuController(){

    def moreInformation(action : ActionEvent) = {
        MainApp.showAboutPage()
    }

    def showMainPage(action : ActionEvent) = {
        MainApp.showMainPage()
    }
}
