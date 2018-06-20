import java.util.HashMap;
import java.util.Map;

public class SelecaoFactory {

	private static Map<Long, Selecao> selecaoPool = new HashMap<>();
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
		return selecaoPool.get(id);
	}

	public void addSelecacao(Selecao s) {

		if (!selecaoPool.containsKey(s.getId()))
			selecaoPool.put(s.getId(), s);

	}

	public static void main(String[] args) {
		SelecaoFactory sf = SelecaoFactory.getInstance();
		Selecao selecao1 = new Selecao(1L, "Brasil", 'A');
		Selecao selecao2 = new Selecao(2L, "Suica", 'A');
		Selecao selecao3 = new Selecao(1L, "Franca", 'A');

		sf.addSelecacao(selecao1);
		sf.addSelecacao(selecao2);
		sf.addSelecacao(selecao3);

		Selecao s = sf.getSelecao(4L);
		System.out.println(s);
	}
}
