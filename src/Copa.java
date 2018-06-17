import java.util.Scanner;

public class Copa {

	private SelecaoFactory factory;

	public Copa() {
		factory = SelecaoFactory.getInstance();
	}

	public static void main(String[] args) {
		Copa copa = new Copa();

		copa.selecionarGrupo();

	}

	public char selecionarGrupo() {
		char gc;
		String g;
		do {
			Scanner s = new Scanner(System.in);
			System.out.println("Digite o grupo desejado: A|B|C|D|E|F|G|H");
			g = s.next();
			gc = g.charAt(0);
			System.out.println(g.charAt(0));
		} while (gc != 'A' && gc != 'B' && gc != 'C' && gc != 'D' && gc != 'E' && gc != 'F' && gc != 'G' && gc != 'H' );

		return gc;

	}

	public void cadastrarSelecoes() {

	}

	public void listarSelecoes() {

	}

	public void listarSelecoesGrupo(char grupo) {

	}

	public void listarSelecoesGrupo() {

	}

	public void cadastrarJogo() {

	}

	public void listarJogosGrupo() {

	}
}
