import javax.swing.*;
import javax.swing.plaf.multi.MultiRootPaneUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class conexion extends JFrame {


    public Connection conectar(){
    String url="jdbc:mysql://localhost:3306/banco";
    String usuarios="root";
    String contrasenia="123456";
    Connection conexion=null;
        try {
            conexion = DriverManager.getConnection(url,usuarios,contrasenia);
            //JOptionPane.showMessageDialog(null,"Conexion a la base de datos correcta");

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Error al conectar la base");
        }
        return conexion;
    }


}
