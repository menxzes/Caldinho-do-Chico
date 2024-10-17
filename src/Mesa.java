public class Mesa {
	private int idMesa;
	private boolean disponivel;

	public Mesa(int idMesa, boolean disponivel) {
		this.idMesa = idMesa;
		this.disponivel = disponivel;
	}

	public int getIdMesa() {
		return idMesa;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel() {
		this.disponivel = true;
	}
}
