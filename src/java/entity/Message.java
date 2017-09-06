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
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
    @NamedQuery(name = "Message.findByContenu", query = "SELECT m FROM Message m WHERE m.contenu = :contenu"),
    @NamedQuery(name = "Message.findByDate", query = "SELECT m FROM Message m WHERE m.date = :date"),
    @NamedQuery(name = "Message.findByHeure", query = "SELECT m FROM Message m WHERE m.heure = :heure"),
    @NamedQuery(name = "Message.findByMessageLu", query = "SELECT m FROM Message m WHERE m.messageLu = :messageLu")})
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "contenu")
    private String contenu;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "heure")
    @Temporal(TemporalType.TIME)
    private Date heure;
    @Basic(optional = false)
    @Column(name = "message_lu")
    private boolean messageLu;
    @JoinColumn(name = "id_source", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Utilisateur idSource;
    @JoinColumn(name = "id_destination", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Utilisateur idDestination;

    public Message() {
    }

    public Message(Integer id) {
        this.id = id;
    }

    public Message(Integer id, String contenu, Date date, Date heure, boolean messageLu) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
        this.heure = heure;
        this.messageLu = messageLu;
    }

    public Message(Integer id, String contenu, Date date, Date heure, boolean messageLu, Utilisateur idSource, Utilisateur idDestination) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
        this.heure = heure;
        this.messageLu = messageLu;
        this.idSource = idSource;
        this.idDestination = idDestination;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
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

    public boolean getMessageLu() {
        return messageLu;
    }

    public void setMessageLu(boolean messageLu) {
        this.messageLu = messageLu;
    }

    public Utilisateur getIdSource() {
        return idSource;
    }

    public void setIdSource(Utilisateur idSource) {
        this.idSource = idSource;
    }

    public Utilisateur getIdDestination() {
        return idDestination;
    }

    public void setIdDestination(Utilisateur idDestination) {
        this.idDestination = idDestination;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Message[ id=" + id + " ]";
    }
    
}
