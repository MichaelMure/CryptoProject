package keytool;

public class JKeytool {
	public static void main(String[] args) {
		KeytoolModel model = new KeytoolModel();
		KeytoolController controller = new KeytoolController(model);
		controller.displayViews();
	}
}
