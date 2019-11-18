
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionPrimerExamen
{
    Connection cn;
    Statement st;
    
    public Connection conectar()
    {
       try{ 
        Class.forName("com.mysql.jdbc.Driver");
        cn = DriverManager.getConnection("jdbc:mysql://localhost/primerexamen","root","");
       }
       catch(ClassNotFoundException | SQLException e){
           System.out.println(e.getMessage());
       }
       return cn;
    }
    Statement opStatement()
    {
        throw new UnsupportedOperationException("no soportado");
    }
}
