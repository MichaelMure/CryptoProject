package keytool;

import keytool.controller.KeytoolController;
import keytool.model.KeytoolModel;

public class JKeytool {
	public static void main(String[] args) {
		KeytoolModel model = new KeytoolModel();
		KeytoolController controller = new KeytoolController(model);
		controller.displayViews();
	}
}
