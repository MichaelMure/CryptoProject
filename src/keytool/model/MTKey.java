package keytool.model;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.Key;
import java.security.cert.CertificateEncodingException;

import org.bouncycastle.openssl.PEMWriter;

/**
 * Key encapsulation :
 * the key can be Private, Secret or Public
 * @author Michaël Muré & Théophile Helleboid
 *
 */
public abstract class MTKey {
	protected Key key;
	
	public String toString() {
		return "clé "+key.getAlgorithm()+", "+key.getFormat()+", "+key.getClass().getName();
	}
	
	public abstract String getDetails();

	/**
	 * Export the PrivateKey to Base64 String
	 * if you wan the publicKey, go through the certificate
	 * @return the key encoded in base64
	 * @throws CertificateEncodingException
	 * @throws IOException
	 */
	public String toBase64() throws CertificateEncodingException, IOException {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
    
		PEMWriter pemWrt = new PEMWriter(new OutputStreamWriter(bOut));
    
		pemWrt.writeObject(this.key);
		pemWrt.close();
		bOut.close();
    
		return bOut.toString();
	}
	
	/**
	 * Export the current Key to a file
	 * @param path to the file
	 * @throws ModelException
	 */
	public void exportTo(String path) throws ModelException {
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(path));
			buf.write(this.toBase64());
			buf.close();
		} catch (CertificateEncodingException e) {
			throw new ModelException("Problème d'encodage de certificat : "+e.getMessage());
		} catch (IOException e) {
			throw new ModelException("Problème d'entrée/sortie : "+e.getMessage());
		}
		
	}

}
