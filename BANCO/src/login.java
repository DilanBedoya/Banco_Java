import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends JFrame{
    private JPanel panel_login;
    private JButton iniciarSesionButton;
    private JPasswordField campo_contrasenia;

    public JPasswordField getCampo_contrasenia() {
        return campo_contrasenia;
    }

    private JButton regresarButton;
    private JTextField campo_usuario;

    public JTextField getCampo_usuario() {
        return campo_usuario;
    }

    Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public login(){
        super("Login");

        setSize(510,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel_login);


        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                principal pagina_principal=new principal();
                pagina_principal.setVisible(true);
                dispose();
            }
        });
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                conexion db=new conexion();
                conexion=db.conectar();

                if(conexion!=null){
                    try{
                        String contrasena_ingresada=campo_contrasenia.getText();
                        Encriptar encriptado=new Encriptar();
                        String contrasena_encriptada=encriptado.MD5(contrasena_ingresada);
                        String consultausaurios="SELECT *FROM USUARIOS WHERE NOMBRE_USUARIO=? AND CONTRASENA=?";
                        PreparedStatement consult = conexion.prepareStatement(consultausaurios);
                        consult.setString(1,getCampo_usuario().getText());
                        consult.setString(2,getCampo_contrasenia().getText());
                        ResultSet resultado_consulta = consult.executeQuery();
                        if(resultado_consulta.next()){
                            JOptionPane.showMessageDialog(null,"Inicio de sesión correcto");
                            Cajero pagina_cajero=new Cajero(login.this);
                            pagina_cajero.setVisible(true);
                            dispose();
                        }else{

                            JOptionPane.showMessageDialog(null,"Usuario o clave incorrecto");
campo_contrasenia.setText("");
campo_usuario.setText("");
                        }
                    }catch (SQLException ex){
                        JOptionPane.showMessageDialog(null, "Se ha producido un error");
                    }

                }



            }
        });
    }

}
