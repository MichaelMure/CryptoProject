package keytool.model;

import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class MTCertificate {
	private Certificate certificate;

	public MTCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
	
	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public Certificate getCertificate() {
		return certificate;
	}
	
	public PublicKey getPublicKey() {
        // Get public key
		PublicKey publicKey = this.certificate.getPublicKey();
		return publicKey;
	}
	
	public String getDetails() {
		X509Certificate cert = (X509Certificate) this.certificate;
		StringBuilder sb = new StringBuilder();
		sb.append("IssuerDN:"+cert.getIssuerDN().toString());
		sb.append("\nNotBefore:"+cert.getNotBefore().toString());
		sb.append("\nNotAfter:"+cert.getNotAfter().toString());
		
		return sb.toString();
	}
	
	public String toString() {
		return getDetails();
	}
}
