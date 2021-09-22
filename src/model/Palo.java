package model;

public class Palo {

	private Integer numero;
	private Integer linha;
	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;
	private Integer centroidX;
	private Integer centroidY;
	
	public boolean isMaior(Palo palo) {
		if (palo.getCentroidX() > this.getCentroidX()) {
			int diferenca = this.getCentroidY() - palo.getCentroidY();
			if (diferenca <= 15 && diferenca >= -15)
				return true;
		}
		return false;
	}
	
	public boolean isMenor(Palo palo) {
		if (palo.getCentroidX() < this.getCentroidX()) {
			int diferenca = this.getCentroidY() - palo.getCentroidY();
			if (diferenca <= 15 && diferenca >= -15)
				return true;
		}
		return false;
	}
	

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getLinha() {
		return linha;
	}

	public void setLinha(Integer linha) {
		this.linha = linha;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getCentroidX() {
		return centroidX;
	}

	public void setCentroidX(Integer centroidX) {
		this.centroidX = centroidX;
	}

	public Integer getCentroidY() {
		return centroidY;
	}

	public void setCentroidY(Integer centroidY) {
		this.centroidY = centroidY;
	}

	@Override
	public String toString() {
		return "Palo [numero=" + numero + ", linha=" + linha + ", x=" + x + ", y=" + y + ", width=" + width
				+ ", height=" + height + ", centroidX=" + centroidX + ", centroidY=" + centroidY + "]";
	}


}
