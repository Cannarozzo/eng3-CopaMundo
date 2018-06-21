import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Copa {

	private SelecaoFactory factory;

	public Copa() {
		factory = SelecaoFactory.getInstance();
		try { // Restaura o estado da aplica��o caso seja interropida.
			List<Selecao> selecoes = new DAO<Selecao>("selecao.dat").findAll();
			selecoes.stream().forEach(selecao -> factory.addSelecacao(selecao));
			selecoes.stream().forEach(s -> System.out.println(s));
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
			System.out.println("1- Cadastrar sele��es");
			System.out.println("2- Listar todas sele��es");
			System.out.println("3- Listar sele��es por grupo");
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
				System.out.println("Op��o inv�lida");
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
			System.out.println("Cadastro da " + controle + " primeira sele��o");
			System.out.print("Digite o nome da " + controle + " sele��o: ");
			nome = s.next();

			boolean controleIDExiste = true;
			do {
				System.out.print("Digite o ID da selecao: ");
				id = s.nextLong();

				if (factory.getSelecao(id) != null) {
					System.out.println("ID j� cadastrado, digite um ID V�lido");
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
		List selecoes = null; // Criando um list gen�rico vazio
		try {
			selecoes = dao.findAll(); // invocando o m�todo findAll() do objeto dao que retorna uma lista de objetos
										// de forma gen�rica
			for (Object selecao : selecoes) { // fazendo um for para percorrer cada objeto existente no List selecoes
				System.out.println(selecao); // imprimindo na tela cada objeto sele��o contido na lista de sele��es
												// (m�todo toString imprime os atributos do objeto)
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// selecoes.stream().forEach( selecao -> System.out.println(selecao));

	}

	public void listarSelecoesGrupo(char grupo) {
		DAO<Selecao> dao = new DAO<>("selecao.dat"); // Instanciando um objeto do tipo DAO, por�m com tipo forte. � um
														// objeto DAO para o tipo Selecao
		try {
			List<Selecao> selecoes = dao.findAll(); // invocando o m�otodo findall() que retornar� um lista do tipo
													// sele��o. Devido a inst�ncia parametrizada do objeto DAO.
			for (Selecao selecao : selecoes) { // Percorrendo a lista de selecoes.
				if (selecao.getGrupo() == grupo) { // pergutando se o grupo da sele��o iterada � igual ao grupo que
													// usu�rio passou como par�metro
					System.out.println(selecao); // imprimindo a sele��o pertencendo ao grupo passado pelo usu�rio.
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
		String dataString, estadioString;
		Selecao selecaoMandante, selecaoVisitante;

		grupo = selecionarGrupo();
		Scanner s = new Scanner(System.in);

		String[] fixData;
		StringBuilder dataFixString;

		System.out.println("Digite uma data no seguinte formato: dia/mes/ano:");
		dataString = s.nextLine();
		// Ideal utilizar a api adequada � datas. Calendar
		// inverter a data.
		// precisa estar como Ano,mes,dia
		fixData = dataString.split("/");
		dataFixString = new StringBuilder();
		dataFixString.append(fixData[2]);
		dataFixString.append("/");
		dataFixString.append(fixData[1]);
		dataFixString.append("/");
		dataFixString.append(fixData[0]);

		Date dataJogo = new Date(dataFixString.toString());
		System.out.println("Digite o est�dio:");
		estadioString = s.next();
		listarSelecoesGrupo(grupo);

		// Garantir que o usu�rio digite um ID de uma sel�ao j� cadastrada.
		boolean controleIDExiste = true;
		do {
			System.out.print("Digite o ID da sele��o MANDANTE: ");
			Long idSelecaoMandante = s.nextLong();
			selecaoMandante = factory.getSelecao(idSelecaoMandante);
			if (factory.getSelecao(idSelecaoMandante) != null) {
				selecaoMandante = factory.getSelecao(idSelecaoMandante);
				controleIDExiste = false;
			} else {
				System.out.print("Digite um ID de uma sele��o existente");
				controleIDExiste = true;
			}
		} while (controleIDExiste);

		// Garantir que o usu�rio digite um ID v�lido e que seja diferente da sele��o
		// mandante;
		controleIDExiste = true;
		do {
			listarSelecoesGrupo(grupo);
			System.out.print("Digite o ID da sele��o Visiante: ");
			Long idSelecaoVisitante = s.nextLong();
			selecaoVisitante = factory.getSelecao(idSelecaoVisitante);
			if (selecaoVisitante != null && !idSelecaoVisitante.equals(selecaoMandante.getId())) {

				// selecaoVisitante = factory.getSelecao(idSelecao);
				controleIDExiste = false;
			} else {
				System.out.println("Digite um ID de uma sele��o existente e que seja diferente da sele��o mandante");
				controleIDExiste = true;
			}
		} while (controleIDExiste);
		
		//Cria objeto do tipo jogo completamente parametrizado pelos dados fornecidos pelo usu�rio
		Jogo jogo = new Jogo(dataJogo, estadioString, selecaoMandante, selecaoVisitante);
		//Instancia uma classe do tipo DAO tipada com a classe jogo para acesso/cria��o de um arquivo chamada jogo.dat
		DAO daoJogo = new DAO<Jogo>("jogo.dat");
		try {
			daoJogo.save(jogo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Jogo Salvo com sucesso");
	}

	public void listarJogosGrupo() {
		char grupo = selecionarGrupo();
		try {
			System.out.println("Todos os jogos do grupo: " + grupo);
			List<Jogo> jogos = new DAO<Jogo>("jogo.dat").findAll();
			jogos.stream()
				.filter( jogo -> jogo.getMandante().getGrupo() == grupo)
			    .forEach( jogo -> System.out.println(jogo));
			System.out.println();
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
