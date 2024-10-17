public class Mesa {
	 private int idMesa;
	    private boolean disponivel;

	    public Mesa(int idMesa) {
	        this.idMesa = idMesa;
	        this.disponivel = true;
	    }

	    public int getIdMesa() {
	        return idMesa;
	    }

	    public boolean isDisponivel() {
	        return disponivel;
	    }

	    public void setDisponivel(boolean disponivel) {
	        this.disponivel = disponivel;
	        }
}
