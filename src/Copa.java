import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javafx.beans.binding.StringExpression;

public class Copa {

	private SelecaoFactory factory;

	public Copa() {
		factory = SelecaoFactory.getInstance();
		try { // Restaura o estado da aplicação caso seja interropida.
			List<Selecao> selecoes = new DAO<Selecao>("selecao.dat").findAll();
			selecoes.stream().forEach(selecao -> factory.addSelecacao(selecao));
			selecoes.stream().forEach( s -> System.out.println(s));
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void main(String[] args) {
		Copa copa = new Copa();
		Scanner s = new Scanner(System.in);
		int menu = 0;

		while (menu != 10) {
			System.out.println("Sistema copa do mundo. Digite 10 para sair");
			System.out.println("1- Cadastrar seleções");
			System.out.println("2- Listar todas seleções");
			System.out.println("3- Listar seleções por grupo");
			System.out.println("4- Cadastrar jogo");
			System.out.println("5- Listar jogos cadastrados por grupo");

			menu = s.nextInt();

			switch (menu) {
			case 1:
				copa.cadastrarSelecoes();
				break;
			case 2:
				copa.listarSelecoes();
				break;
			case 3:
				copa.listarSelecoesGrupo();
				break;
			case 4:
				copa.cadastrarJogo();
				break;
			case 5:
				copa.listarJogosGrupo();
				break;
			case 10:
				System.out.println("Programa encerrado");
				break;
			default:
				System.out.println("Opção inválida");
				break;
			}

		}

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

	public void listarSelecoes() {
		DAO dao = new DAO("selecao.dat"); // instanciando um objeto do tipo DAO para acesso dos objetos no arquivo
											// selecao.dat
		List selecoes = null; // Criando um list genérico vazio
		try {
			selecoes = dao.findAll(); // invocando o método findAll() do objeto dao que retorna uma lista de objetos
										// de forma genérica
			for (Object selecao : selecoes) { // fazendo um for para percorrer cada objeto existente no List selecoes
				System.out.println(selecao); // imprimindo na tela cada objeto seleção contido na lista de seleções
												// (método toString imprime os atributos do objeto)
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// selecoes.stream().forEach( selecao -> System.out.println(selecao));

	}

	public void listarSelecoesGrupo(char grupo) {
		DAO<Selecao> dao = new DAO<>("selecao.dat"); // Instanciando um objeto do tipo DAO, porém com tipo forte. É um
														// objeto DAO para o tipo Selecao
		try {
			List<Selecao> selecoes = dao.findAll(); // invocando o méotodo findall() que retornará um lista do tipo
													// seleção. Devido a instância parametrizada do objeto DAO.
			for (Selecao selecao : selecoes) { // Percorrendo a lista de selecoes.
				if (selecao.getGrupo() == grupo) { // pergutando se o grupo da seleção iterada é igual ao grupo que
													// usuário passou como parâmetro
					System.out.println(selecao); // imprimindo a seleção pertencendo ao grupo passado pelo usuário.
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void listarSelecoesGrupo() {
		char grupoChar = selecionarGrupo();
		listarSelecoesGrupo(grupoChar);

	}

	public void cadastrarJogo() {
		char grupo;
		String dataString,estadioString;
		Selecao selecaoMandante, selecaoVisitante;
	

		grupo = selecionarGrupo();
		Scanner s = new Scanner(System.in);
		System.out.println("Digite uma data no seguinte formato: dia/mes/ano:");
		dataString = s.nextLine();
		// Ideal utilizar a api adequada à datas. Calendar
		// inverter a data.
		// precisa estar como Ano,mes,dia
		Date dataJogo = new Date(dataString);
		System.out.println("Digite o estádio:");
		estadioString = s.next();
		listarSelecoesGrupo(grupo);
		
		//Garantir que o usuário digite um ID de uma selçao já cadastrada.
		boolean controleIDExiste = true;
		do {
			System.out.print("Digite o ID da seleção MANDANTE: ");
			Long idSelecaoMandante = s.nextLong();
			selecaoMandante = factory.getSelecao(idSelecaoMandante);
			if (factory.getSelecao(idSelecaoMandante) != null) {
				selecaoMandante = factory.getSelecao(idSelecaoMandante);
				controleIDExiste = false;
			} else {
				System.out.print("Digite um ID de uma seleção existente");
				controleIDExiste = true;
			}
		} while (controleIDExiste);
		
		//Garantir que o usuário digite um ID válido e que seja diferente da seleção mandante;
		controleIDExiste = true;
		do {
			listarSelecoesGrupo(grupo);
			System.out.print("Digite o ID da seleção Visiante: ");
			Long idSelecaoVisitante = s.nextLong();
			selecaoVisitante = factory.getSelecao(idSelecaoVisitante);
			System.out.println(idSelecaoVisitante);
			System.out.println(selecaoMandante.getId());
			if (selecaoVisitante != null && !idSelecaoVisitante.equals(selecaoMandante.getId()) ) {
				
				//selecaoVisitante = factory.getSelecao(idSelecao);
				controleIDExiste = false;
			} else {
				System.out.println("Digite um ID de uma seleção existente e que seja diferente da seleção mandante");
				controleIDExiste = true;
			}
		} while (controleIDExiste);
		
		Jogo jogo = new Jogo(null , estadioString, selecaoMandante, selecaoVisitante);
		System.out.println(jogo);
	}

	public void listarJogosGrupo() {

	}
}
