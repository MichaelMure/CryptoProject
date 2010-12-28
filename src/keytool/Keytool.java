package keytool;

/* 
@startuml
Package model {
	MTCertificate *-- Certificate
	MTKey *-- Key
	Model *-- KeyStore
	Model .. MTKey : use
	Model .. MTCertificate : use
}
Package view {
	jFrame <|-- FileOpenWindow
	jFrame <|-- MainWindow
	View *--FileOpenWindow
	View *-- MainWindow
}
Package controller {
	Controller *-- Model
	Controller *-- View : listen event & change View
}
@enduml
*/

import java.io.FileInputStream;
import java.security.Security;
import keytool.controller.Controller;
import keytool.model.MTCertificate;
import keytool.model.MTPrivateKey;
import keytool.model.Model;
import keytool.view.View;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
@SuppressWarnings("unused")
public class Keytool {
    public static void main(String[] args) {
		// FIXME : l'ajouter au bon endroit
		Security.addProvider(new BouncyCastleProvider());
    	
        Model model;
		try {
			model = new Model();
	        
			View view = new View(model);
	        
			Controller controller = new Controller(model, view);
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
}