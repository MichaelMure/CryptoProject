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

import java.security.Security;
import keytool.controller.Controller;
import keytool.model.MTCertificate;
import keytool.model.MTKey;
import keytool.model.MTPrivateKey;
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
	        model.addCertificate("test", new MTCertificate());
	        System.out.println(model.getCertificate("test").toBase64());
	        
	        model.addKey("cle-theo", new MTPrivateKey("CN=Hello-Theo"));
	        System.out.println(model.getKey("cle-theo").toBase64());
	        System.out.println(model.getCertificate("cle-theo").toBase64());
	        System.out.println(model.getCertificate("cle-theo").getPublicKey().toBase64());
	        
			View view = new View(model);
	        @SuppressWarnings("unused")
			Controller controller = new Controller(model, view);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}