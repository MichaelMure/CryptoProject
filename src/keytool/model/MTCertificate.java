package keytool.model;

import java.security.cert.Certificate;

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
}
