package ch.makery.address.view
import ch.makery.address.model.Jewellery
import ch.makery.address.MainApp
import scalafx.scene.control.{TextField, Alert} 
import scalafx.event.ActionEvent 
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage 
import scalafx.Includes._ 
import scalafx.beans.property.ObjectProperty 
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.Alert
import scala.util.{Failure, Success}

@sfxml
class AddController(

    // table 
    private val jNameField : TextField,
    private val jTypeField : TextField,
    private val priceField : TextField,
    private val companyField : TextField,
    private val materialField : TextField,
    private val quantityField : TextField

){
    var dialogStage : Stage  = null 
    private var _jewellery : Jewellery = null 
    var okClicked = false 
    def jewellery = _jewellery 

    def jewellery_=(x : Jewellery): Unit = { 
      _jewellery = x 
      jNameField.text = _jewellery.name.value 
      jTypeField.text = _jewellery.jType.value 
      priceField.text = _jewellery.jPrice.value.toString;
      companyField.text = _jewellery.company.value 
      materialField.text = _jewellery.material.value 
      quantityField.text = _jewellery.quantity.value.toString;
    } 

    def addJewellery(action : ActionEvent) = {
      if (isInputValid()) { 
      _jewellery.name <== jNameField.text 
      _jewellery.jType <== jTypeField.text 
      _jewellery.jPrice = ObjectProperty[Double](priceField.text.value.toDouble)
      _jewellery.company <== companyField.text 
      _jewellery.material <== materialField.text 
      _jewellery.quantity.value = quantityField.getText().toInt
      okClicked = true; 
      dialogStage.close()
        } 
    }

    def handleCancel(action :ActionEvent) = { 
        dialogStage.close()
    } 

    def nullChecking (x : String) = x == null || x.length == 0 

    def isInputValid() : Boolean = { 
      var errorMessage = "" 
      if (nullChecking(jNameField.text.value)) 
        errorMessage += "No valid name!\n" 
      if (nullChecking(jTypeField.text.value)) 
        errorMessage += "No valid type!\n" 

      if (nullChecking(priceField.text.value)) 
        errorMessage += "No valid price!\n" 
      else { 
        try { 
          ObjectProperty[Double](priceField.text.value.toDouble)
        } catch { 
            case e : NumberFormatException => 
              errorMessage += "No valid price (must be a double)!\n" 
        } 
      } 

      if (nullChecking(companyField.text.value)) 
        errorMessage += "No valid company!\n" 
      if (nullChecking(materialField.text.value)) 
        errorMessage += "No valid material!\n" 

      if (nullChecking(quantityField.text.value)) 
        errorMessage += "No valid quantity!\n" 
      
      else { 
        try { 
          Integer.parseInt(quantityField.getText()); 
        } catch { 
            case e : NumberFormatException => 
              errorMessage += "No valid quantity (must be an integer)!\n" 
        } 
      } 

      if (errorMessage.length() == 0) { 
          return true; 
      } else { 
          // Show the error message. 
          val alert = new Alert(Alert.AlertType.Error){ 
            initOwner(dialogStage) 
            title = "Invalid Fields" 
            headerText = "Please correct invalid fields" 
            contentText = errorMessage 
          }.showAndWait() 
          return false; 
      }
     
   } 
}

