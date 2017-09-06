package entity;

import entity.Action;
import entity.Affectation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-29T01:46:21")
@StaticMetamodel(Fichier.class)
public class Fichier_ { 

    public static volatile ListAttribute<Fichier, Action> actionList;
    public static volatile SingularAttribute<Fichier, String> chemin;
    public static volatile SingularAttribute<Fichier, Integer> id;
    public static volatile ListAttribute<Fichier, Affectation> affectationList;
    public static volatile SingularAttribute<Fichier, String> nom;

}