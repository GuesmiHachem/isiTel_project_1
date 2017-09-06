package entity;

import entity.Fichier;
import entity.Utilisateur;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-29T01:46:21")
@StaticMetamodel(Action.class)
public class Action_ { 

    public static volatile SingularAttribute<Action, Date> date;
    public static volatile SingularAttribute<Action, Utilisateur> idUser;
    public static volatile SingularAttribute<Action, Date> heure;
    public static volatile SingularAttribute<Action, Date> duree;
    public static volatile SingularAttribute<Action, Integer> id;
    public static volatile SingularAttribute<Action, Integer> type;
    public static volatile SingularAttribute<Action, Fichier> idFichier;

}