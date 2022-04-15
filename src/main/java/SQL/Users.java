package SQL;

public class Users extends SQL{

    public Users() {
        super();
    }

    public String[] user_data(String name){
        return select(String.format("select login, pas from users where name = \"%s\"", name)).toArray(new String[2]);
    }

    public void insert_name(String name){
        insert(String.format("insert users(name) value (\"%s\")", name));
    }

    public void reg(String name, String login, String password){
        insert(String.format("update users set login = \"%s\", pas = \"%s\" where name = \"%s\"", login, password, name));
    }

    public void CreateTable(){
        insert("CREATE TABLE users(id int auto_increment primary key, name varchar(30) unique, login varchar(30), password varchar(30));");
    }
}


