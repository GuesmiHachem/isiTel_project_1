package entity;

import entity.Action;
import entity.Affectation;
import entity.Message;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-29T01:46:21")
@StaticMetamodel(Utilisateur.class)
public class Utilisateur_ { 

    public static volatile SingularAttribute<Utilisateur, String> password;
    public static volatile SingularAttribute<Utilisateur, String> role;
    public static volatile ListAttribute<Utilisateur, Message> messageList;
    public static volatile SingularAttribute<Utilisateur, Boolean> active;
    public static volatile ListAttribute<Utilisateur, Action> actionList;
    public static volatile SingularAttribute<Utilisateur, Integer> id;
    public static volatile ListAttribute<Utilisateur, Message> messageList1;
    public static volatile ListAttribute<Utilisateur, Affectation> affectationList;
    public static volatile SingularAttribute<Utilisateur, String> login;
    public static volatile SingularAttribute<Utilisateur, String> nom;
    public static volatile SingularAttribute<Utilisateur, String> prenom;

}