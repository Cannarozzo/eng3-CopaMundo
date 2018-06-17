import java.util.HashMap;
import java.util.Map;

public class SelecaoFactory {

	private static Map<Integer, Selecao> selecaoPool = new HashMap<>();
	private static SelecaoFactory instance;

	private SelecaoFactory() {
	}

	public static SelecaoFactory getInstance() {
		if (instance == null) {
			return new SelecaoFactory();
		}
		return instance;
	}
	
	
	public Selecao getSelecao(Long id) {
		return null;
		
	}
	
	public void addSelecacao(Selecao s) {
		
	}
	
	public static void main(String[] args) {
		
	}
}
