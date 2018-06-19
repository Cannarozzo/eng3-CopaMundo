import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Copa {

	private SelecaoFactory factory;

	public Copa() {
		factory = SelecaoFactory.getInstance();
	}

	public static void main(String[] args) {
		Copa copa = new Copa();

		//copa.cadastrarSelecoes();
		//copa.listarSelecoes();
		copa.listarSelecoesGrupo('B');

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
		} while (gc != 'A' && gc != 'B' && gc != 'C' && gc != 'D' && gc != 'E' && gc != 'F' && gc != 'G' && gc != 'H');

		return gc;

	}

	public void cadastrarSelecoes() {
		DAO dao = new DAO<>("selecao.dat");
		char grupo = selecionarGrupo();
		Scanner s = new Scanner(System.in);
		int controle = 1;
		while (controle <= 4) {
			String nome;
			Long id;
			System.out.println();
			System.out.println("Cadastro da " + controle + " primeira seleção");
			System.out.print("Digite o nome da " + controle + " seleção: ");
			nome = s.next();

			boolean controleIDExiste = true;
			do {
				System.out.print("Digite o ID da selecao: ");
				id = s.nextLong();

				if (factory.getSelecao(id) != null) {
					System.out.println("ID já cadastrado, digite um ID Válido");
				} else {
					controleIDExiste = false;
				}

			} while (controleIDExiste);

			Selecao selecao = new Selecao(id, nome, grupo);
			factory.addSelecacao(selecao);
			try {
				dao.save(selecao);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			controle++;
		}
		System.out.println();

	}

	public void listarSelecoes(){
		DAO dao = new DAO("selecao.dat"); // instanciando um objeto do tipo DAO para acesso dos objetos no arquivo selecao.dat 
		List selecoes = null; // Criando um list genérico vazio
		try {
			selecoes = dao.findAll(); // invocando o método findAll() do objeto dao que retorna uma lista de objetos de forma genérica
			for(Object selecao : selecoes) { // fazendo um for para percorrer cada objeto existente no List selecoes
				System.out.println(selecao); // imprimindo na tela cada objeto seleção contido na lista de seleções (método toString imprime os atributos do objeto)
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//selecoes.stream().forEach( selecao -> System.out.println(selecao));

	}

	public void listarSelecoesGrupo(char grupo) {
		DAO<Selecao> dao = new DAO<>("selecao.dat"); // Instanciando um objeto do tipo DAO, porém com tipo forte. É um objeto DAO para o tipo Selecao
		try {
			List<Selecao> selecoes = dao.findAll(); // invocando o méotodo findall() que retornará um lista do tipo seleção. Devido a instância parametrizada do objeto DAO.
			for(Selecao selecao : selecoes) { // Percorrendo a lista de selecoes. 
				if(selecao.getGrupo() == grupo) { // pergutando se o grupo da seleção iterada é igual ao grupo que usuário passou como parâmetro
					System.out.println(selecao);  // imprimindo a seleção pertencendo ao grupo passado pelo usuário.
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void listarSelecoesGrupo() {

	}

	public void cadastrarJogo() {

	}

	public void listarJogosGrupo() {

	}
}
