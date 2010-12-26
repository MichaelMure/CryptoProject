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

	        /* Test d'import certificats */
	        MTCertificate verisign = new MTCertificate(new FileInputStream("verisign-cert.pem"));
	        verisign.addToKeyStore(model, "verisign");
	        MTCertificate geotrust = new MTCertificate(new FileInputStream("geotrust-cert.der"));
	        geotrust.addToKeyStore(model, "geotrust");
	        
			View view = new View(model);
	        @SuppressWarnings("unused")
			Controller controller = new Controller(model, view);
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
}