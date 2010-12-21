package keytool.mvc;
 
public class MVC {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model);
 
        @SuppressWarnings("unused")
		Controller controller = new Controller(model, view);
    }
}