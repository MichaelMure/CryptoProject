package keytool;

/* 
@startuml
Package model {
	MTCertificate *-- Certificate
	MTKey *-- Key
	MTKeyStore *-- KeyStore
	Model *-- MTKeyStore
	MTKeyStore .. MTKey : use
	MTKeyStore .. MTCertificate : use
}
Package view {
	jFrame <|-- FileOpenWindow
	jFrame <|-- MainWindow
	View *--FileOpenWindow
	View *-- MainWindow
	MainWindow ..> Model
}
Package controller {
	Controller *-- Model
	Controller *-- View : listen event & change View
}
@enduml
*/

import keytool.controller.Controller;
import keytool.model.Model;
import keytool.view.View;
 
public class Keytool {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model);
 
        @SuppressWarnings("unused")
		Controller controller = new Controller(model, view);
    }
}