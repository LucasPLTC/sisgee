package br.cefetrj.sisgee.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 
 * @author padu
 * @since 1.0
 * 
 */
@Entity
public class Convenio {

	@Id
	@GeneratedValue
	private Integer idConvenio;

	@Column(length = 10, nullable = false)
	private String numeroConvenio;

	// TODO verificar relacionamentos e seus atributos (empresa e termoEstagio)

	@ManyToOne(fetch = FetchType.EAGER)
	private Empresa empresa;

	@OneToMany(mappedBy = "convenio")
	private List <TermoEstagio> termoEstagios;

	public Convenio() {}

	public Integer getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(Integer idConvenio) {
		this.idConvenio = idConvenio;
	}

	public String getNumeroConvenio() {
		return numeroConvenio;
	}

	public void setNumeroConvenio(String numeroConvenio) {
		this.numeroConvenio = numeroConvenio;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<TermoEstagio> getTermoEstagio() {
		return termoEstagios;
	}

	public void setTermoEstagio(List<TermoEstagio> termoEstagios) {
		this.termoEstagios = termoEstagios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idConvenio == null) ? 0 : idConvenio.hashCode());
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
		Convenio other = (Convenio) obj;
		if (idConvenio == null) {
			if (other.idConvenio != null)
				return false;
		} else if (!idConvenio.equals(other.idConvenio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return numeroConvenio;
	}

}
