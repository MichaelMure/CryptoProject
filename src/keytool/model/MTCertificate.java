package keytool.model;

import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

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
		if(cert != null) {
				
			sb.append("IssuerDN:"+cert.getIssuerDN().toString());
			sb.append("\nNotBefore:"+cert.getNotBefore().toString());
			sb.append("\nNotAfter:"+cert.getNotAfter().toString());
	
		    // récupération de la description X500 de l'émetteur
			X500Principal issuer = cert.getIssuerX500Principal();
			// édition de la description selon la RFC2253 (mode par défaut)
			sb.append("émetteur\n").append(issuer.getName("RFC2253")).append("\n________________\n\n");
			// récupération de la description X500 du sujet
			X500Principal subject = cert.getSubjectX500Principal();
			// édition de la description selon la RFC1779
			sb.append("sujet\n").append(subject.getName("RFC1779")).append("\n________________\n\n");

		} else {
			sb.append("Pas de certificat X509");
		}
		return sb.toString();
	}
	
	public String toString() {
		return getDetails();
	}
}
