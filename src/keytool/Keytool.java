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

import java.security.Security;
import keytool.controller.Controller;
import keytool.model.Model;
import keytool.view.View;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Keytool {
    public static void main(String[] args) {
		// FIXME : l'ajouter au bon endroit
		Security.addProvider(new BouncyCastleProvider());
    	
        Model model;
		try {
			model = new Model();
			/* Pour test*/
	        model.openKeyStore("store.ks",  "keytool".toCharArray());
			
			View view = new View(model);
	        
	        
	        @SuppressWarnings("unused")
			Controller controller = new Controller(model, view);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}