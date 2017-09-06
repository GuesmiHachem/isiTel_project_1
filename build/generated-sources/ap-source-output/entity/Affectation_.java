package entity;

import entity.Fichier;
import entity.Utilisateur;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-29T01:46:21")
@StaticMetamodel(Affectation.class)
public class Affectation_ { 

    public static volatile SingularAttribute<Affectation, Utilisateur> idUser;
    public static volatile SingularAttribute<Affectation, Integer> id;
    public static volatile SingularAttribute<Affectation, Fichier> idFichier;

}