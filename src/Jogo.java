import java.io.Serializable;
import java.sql.Date;

public class Jogo implements Serializable {

	private Date data;
	private String estadio;
	private Selecao mandante;
	private Selecao visitante;

	public Jogo(Date data, String estadio, Selecao mandante, Selecao visitante) {
		this.data = data;
		this.estadio = estadio;
		this.mandante = mandante;
		this.visitante = visitante;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getEstadio() {
		return estadio;
	}

	public void setEstadio(String estadio) {
		this.estadio = estadio;
	}

	public Selecao getMandante() {
		return mandante;
	}

	public void setMandante(Selecao mandante) {
		this.mandante = mandante;
	}

	public Selecao getVisitante() {
		return visitante;
	}

	public void setVisitante(Selecao visitante) {
		this.visitante = visitante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((estadio == null) ? 0 : estadio.hashCode());
		result = prime * result + ((mandante == null) ? 0 : mandante.hashCode());
		result = prime * result + ((visitante == null) ? 0 : visitante.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jogo other = (Jogo) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (estadio == null) {
			if (other.estadio != null)
				return false;
		} else if (!estadio.equals(other.estadio))
			return false;
		if (mandante == null) {
			if (other.mandante != null)
				return false;
		} else if (!mandante.equals(other.mandante))
			return false;
		if (visitante == null) {
			if (other.visitante != null)
				return false;
		} else if (!visitante.equals(other.visitante))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Jogo [data=" + data + ", estadio=" + estadio + ", mandante=" + mandante + ", visitante=" + visitante
				+ "]";
	}

	public static void main(String[] args) {

		Selecao selecao1 = new Selecao(1L, "Brasil", 'A');
		Selecao selecao2 = new Selecao(2L, "Suica", 'A');

		@SuppressWarnings("deprecation")
		Jogo jogo = new Jogo(new Date(2018, 06, 17), "Poslov", selecao1, selecao2);

		System.out.println(selecao1.equals(selecao1));
		System.out.println(jogo);

	}

}
