package models.model;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * @version 1.0 with JPA
 */
@Entity
public class Provider extends Model{

    @Id @GeneratedValue
    Long id;

    @Constraints.Required
    String name;

	public Provider(String name){
        this.name = name;
	}

    public static Finder<Long,Provider> find = new Finder(Long.class, Provider.class);

    public List<Provider> all(){
        return find.all();
    }

}