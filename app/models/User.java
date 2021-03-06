package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import utils.AES;

/**
 * 
 * @category Models
 *
 */
@Entity
@Table(name="userDB")
public class User extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public interface All {}

    @Id
    @Required(groups = {All.class})
    @MinLength(value = 4, groups = {All.class})
    public String username;
    
    @Required(groups = {All.class})
    @Email(groups = {All.class})
    public String email;
    
    @Required(groups = {All.class})
    @MinLength(value = 6, groups = {All.class})
    public String password;

    //-------Personal info-------
    @Required(groups = {All.class})
    public String country;

    public String address;

    @Min(value = 18, groups = {All.class}) @Max(value = 120, groups = {All.class})
    public Integer age;
    
    @OneToMany
    public List<Document> documentos;

    
    public User() {
    	documentos = new ArrayList<Document>();
    }
    
    public User(String username, String email, String password,String country,String address,Integer age) {
        this.username = username;
        this.email = email;
        this.password = AES.encrypt(password);//We encrypt the password, before uploading it to the DB
        this.address = address;
        this.country = country;
        this.age = age;
        
        documentos = new ArrayList<Document>();
    }

    public static Model.Finder<String,User> find = new Finder<String, User>(String.class, User.class);

    public static List<User> all(){
        return find.all();
    }

    public static User create(User user)throws Exception{
        if (User.findByUsername(user.username) == null) {
            user.save();
            return user;
        }else
            throw new Exception("Element already exists");
    }

    public static User findByUsername(String username) {
        return find.byId(username);
    }

    public static void remove(String username) {
        find.ref(username).delete();
    }

    public static void removeAll(){
        for (User user: all()) {
            user.delete();
        }
    }

}