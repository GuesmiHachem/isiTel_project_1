/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "fichier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fichier.findAll", query = "SELECT f FROM Fichier f"),
    @NamedQuery(name = "Fichier.findById", query = "SELECT f FROM Fichier f WHERE f.id = :id"),
    @NamedQuery(name = "Fichier.findByNom", query = "SELECT f FROM Fichier f WHERE f.nom = :nom"),
    @NamedQuery(name = "Fichier.findByChemin", query = "SELECT f FROM Fichier f WHERE f.chemin = :chemin")})
public class Fichier implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @Column(name = "chemin")
    private String chemin;
    @OneToMany(mappedBy = "idFichier")
    private List<Action> actionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFichier")
    private List<Affectation> affectationList;

    public Fichier() {
    }

    public Fichier(Integer id) {
        this.id = id;
    }

    public Fichier(Integer id, String nom, String chemin) {
        this.id = id;
        this.nom = nom;
        this.chemin = chemin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    @XmlTransient
    public List<Action> getActionList() {
        return actionList;
    }

    public void setActionList(List<Action> actionList) {
        this.actionList = actionList;
    }

    @XmlTransient
    public List<Affectation> getAffectationList() {
        return affectationList;
    }

    public void setAffectationList(List<Affectation> affectationList) {
        this.affectationList = affectationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fichier)) {
            return false;
        }
        Fichier other = (Fichier) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Fichier[ id=" + id + " ]";
    }
    
}
