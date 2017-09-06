/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "action")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Action.findAll", query = "SELECT a FROM Action a"),
    @NamedQuery(name = "Action.findById", query = "SELECT a FROM Action a WHERE a.id = :id"),
    @NamedQuery(name = "Action.findByDate", query = "SELECT a FROM Action a WHERE a.date = :date"),
    @NamedQuery(name = "Action.findByHeure", query = "SELECT a FROM Action a WHERE a.heure = :heure"),
    @NamedQuery(name = "Action.findByType", query = "SELECT a FROM Action a WHERE a.type = :type"),
    @NamedQuery(name = "Action.findByDuree", query = "SELECT a FROM Action a WHERE a.duree = :duree")})
public class Action implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "heure")
    @Temporal(TemporalType.TIME)
    private Date heure;
    @Basic(optional = false)
    @Column(name = "type")
    private int type;
    @Basic(optional = false)
    @Column(name = "duree")
    @Temporal(TemporalType.TIME)
    private Date duree;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Utilisateur idUser;
    @JoinColumn(name = "id_fichier", referencedColumnName = "id")
    @ManyToOne
    private Fichier idFichier;

    public Action() {
    }

    public Action(Integer id) {
        this.id = id;
    }

  
    public Action(Integer id, Date date, Date heure, int type, Date duree, Utilisateur idUser, Fichier idFichier) {
        this.id = id;
        this.date = date;
        this.heure = heure;
        this.type = type;
        this.duree = duree;
        this.idUser = idUser;
        this.idFichier = idFichier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDuree() {
        return duree;
    }

    public void setDuree(Date duree) {
        this.duree = duree;
    }

    public Utilisateur getIdUser() {
        return idUser;
    }

    public void setIdUser(Utilisateur idUser) {
        this.idUser = idUser;
    }

    public Fichier getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(Fichier idFichier) {
        this.idFichier = idFichier;
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
        if (!(object instanceof Action)) {
            return false;
        }
        Action other = (Action) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Action[ id=" + id + " ]";
    }
    
}
