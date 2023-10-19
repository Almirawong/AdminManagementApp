package ch.makery.address
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import scalafx.collections.ObservableBuffer
import ch.makery.address.model.Jewellery
import ch.makery.address.view.{AddController, SearchController, MenuController}
import scalafx.scene.image.Image
import ch.makery.address.util.Database
import scalafx.stage.{ Stage, Modality }

object MainApp extends JFXApp {
    //initialize database
    Database.setupDB()

    // // /**
    // // * The data as an observable list of Jewelleries.
    // // */
    val jewelleryData = new ObservableBuffer[Jewellery]()

    /**
    * Constructor
    */
    
    //assign all jewellery into jewelleryData array

    jewelleryData ++= Jewellery.getAllJewelleries

    // transform path of RootLayout.fxml to URI for resource location.
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    // initialize the loader object.
    val loader = new FXMLLoader(rootResource, NoDependencyResolver)
    // Load root layout from fxml file.
    loader.load();
    // retrieve the root component BorderPane from the FXML
    val roots = loader.getRoot[jfxs.layout.BorderPane]
    val control = loader.getController[MenuController#Controller] 

    // initialize stage
    stage = new PrimaryStage {
    title = "AdminApp"
    icons += new Image("file:src/main/resources/images/admin.png")
    scene = new Scene {
        root = roots
    }
}

    def showAboutPage() = {
        val resource = getClass.getResource("view/AboutPage.fxml")
        val loader = new FXMLLoader(resource, NoDependencyResolver)
        loader.load();
        val roots = loader.getRoot[jfxs.layout.AnchorPane]
        this.roots.setCenter(roots)
    }

    // actions for display main page window
    def showMainPage() = {
        val resource = getClass.getResource("view/MainPage.fxml")
        val loader = new FXMLLoader(resource, NoDependencyResolver)
        loader.load();
        val roots = loader.getRoot[jfxs.layout.AnchorPane]
        this.roots.setCenter(roots)
    }
    // call to display MainPage when app start
    showMainPage()

    def showAddJewelleryPage(jewellery: Jewellery): Boolean = { 
        val resource = getClass.getResourceAsStream("view/AddJewelleryPage.fxml") 
        val loader = new FXMLLoader(null, NoDependencyResolver) 
        loader.load(resource); 
        val roots2  = loader.getRoot[jfxs.Parent] 
        val control = loader.getController[AddController#Controller] 
        val dialog = new Stage() { 

            icons += new Image("file:src/main/resources/images/add.png")
            initModality(Modality.APPLICATION_MODAL) 
            initOwner(stage) 

            scene = new Scene { 
                root = roots2 
            } 
        } 

        control.dialogStage = dialog 
        control.jewellery = jewellery 
        dialog.showAndWait() 
        control.okClicked 
  } 

    def showSearchJewelleryPage(jewellery: Jewellery): Boolean = { 
        val resource = getClass.getResourceAsStream("view/SearchJewelleryPage.fxml") 
        val loader = new FXMLLoader(null, NoDependencyResolver) 
        loader.load(resource); 
        val roots2  = loader.getRoot[jfxs.Parent] 
        val control = loader.getController[SearchController#Controller] 

        val dialog = new Stage() { 
            icons += new Image("file:src/main/resources/images/search.png")
            initModality(Modality.APPLICATION_MODAL) 
            initOwner(stage) 

            scene = new Scene { 
                root = roots2 
            } 
        } 

        control.dialogStage = dialog 
        dialog.showAndWait() 
        control.editClicked 
  } 
}