package entity;

import entity.Utilisateur;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-29T01:46:21")
@StaticMetamodel(Message.class)
public class Message_ { 

    public static volatile SingularAttribute<Message, Date> date;
    public static volatile SingularAttribute<Message, Utilisateur> idDestination;
    public static volatile SingularAttribute<Message, Date> heure;
    public static volatile SingularAttribute<Message, Utilisateur> idSource;
    public static volatile SingularAttribute<Message, Integer> id;
    public static volatile SingularAttribute<Message, Boolean> messageLu;
    public static volatile SingularAttribute<Message, String> contenu;

}