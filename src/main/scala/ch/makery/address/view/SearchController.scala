package ch.makery.address.view
import ch.makery.address.model.Jewellery
import ch.makery.address.MainApp
import scalafx.scene.control.{TableView, TableColumn, Label, Alert}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.{StringProperty, DoubleProperty, IntegerProperty}
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scala.util.{Failure, Success}
import scalafx.stage.Stage 
    
@sfxml
class SearchController(

    // table on the left
    private val jewelleryTable : TableView[Jewellery],
    private val jNameColumn : TableColumn[Jewellery, String],
    private val jTypeColumn : TableColumn[Jewellery, String],
    private val jPriceColumn : TableColumn[Jewellery, Double],
    private val jCompanyColumn : TableColumn[Jewellery, String],
    private val jMaterialColumn : TableColumn[Jewellery, String],
    private val jQuantityColumn : TableColumn[Jewellery, Int]

    ) {

    var dialogStage : Stage  = null 
    private var _jewellery : Jewellery = null 
    var editClicked = false 
    def jewellery = _jewellery 

    // initialize Table View display contents model
    jewelleryTable.items = MainApp.jewelleryData
    // initialize columns's cell values
    jNameColumn.cellValueFactory = {_.value.name}
    jTypeColumn.cellValueFactory = {_.value.jType}
    jPriceColumn.cellValueFactory = {_.value.jPrice}
    jCompanyColumn.cellValueFactory = {_.value.company}
    jMaterialColumn.cellValueFactory = {_.value.material}
    jQuantityColumn.cellValueFactory = {_.value.quantity}

    def handleDeleteJewellery(action : ActionEvent) = {
        val selectedIndex = jewelleryTable.selectionModel().selectedIndex.value
        val selectedJewellery = jewelleryTable.selectionModel().selectedItem.value
        if (selectedIndex >= 0) {
            selectedJewellery.delete() match {
            case Success(x) =>
                jewelleryTable.items().remove(selectedIndex);
                val alert = new Alert(AlertType.Information){
                    initOwner(MainApp.stage)
                    title       = "Delete Confirmation"
                    headerText  = "Value deleted"
                }.showAndWait()
            case Failure(e) =>
                val alert = new Alert(Alert.AlertType.Warning) {
                    initOwner(MainApp.stage)
                    title = "Failed to Save"
                    headerText = "Database Error"
                    contentText = "Database problem filed to save changes"
                }.showAndWait()
            }
        } else {
            // Nothing selected.
            val alert = new Alert(AlertType.Warning){
                initOwner(MainApp.stage)
                title       = "No Selection"
                headerText  = "No Jewellery Selected"
                contentText = "Please select a jewellery in the table."
            }.showAndWait()
        }
    }


    def handleEditJewellery(action : ActionEvent) = {
        val selectedJewellery = jewelleryTable.selectionModel().selectedItem.value
        if (selectedJewellery != null) {

            val editClicked = MainApp.showAddJewelleryPage(selectedJewellery);
            if (editClicked) {
            selectedJewellery.save() match {
                case Success(x) =>
                    MainApp.showSearchJewelleryPage(selectedJewellery);
                case Failure(e) =>
                    val alert = new Alert(Alert.AlertType.Warning) {
                        initOwner(MainApp.stage)
                        title = "Failed to Save"
                        headerText = "Database Error"
                        contentText = "Database problem filed to save changes"
                    }.showAndWait()
            }
            }


        } else {
            // Nothing selected.
            val alert = new Alert(Alert.AlertType.Warning){
                initOwner(MainApp.stage)
                title       = "No Selection"
                headerText  = "No Jewellery Selected"
                contentText = "Please select a jewellery in the table."
            }.showAndWait()
        }
        dialogStage.close()
  }

}