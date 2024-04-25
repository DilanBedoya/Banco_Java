import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class cambiar_contrasena extends JFrame{
    private JLabel contra_actual;
    private JTextField nueva_contrasena;
    private JPanel panel_contrasena;
    private JButton confirmarButton;
    private JButton cancelarButton;

    Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public cambiar_contrasena(Cajero cajero, login login){
        super("Cambiar Clave");
        setSize(250,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel_contrasena);
        setUndecorated(true);

        conexion db=new conexion();
        conexion= db.conectar();
        try{
            String consultar_nombre = "SELECT CONTRASENA FROM USUARIOS WHERE NOMBRE_USUARIO=?";
            PreparedStatement consulta = conexion.prepareStatement(consultar_nombre);
            consulta.setString(1,login.getCampo_usuario().getText());
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()){
                contra_actual.setText(resultado.getString("CONTRASENA"));
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Error al encontrar la contraseña");
        }





        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Objects.equals(nueva_contrasena.getText(), "")){
                try{
                    String consultar_nombre = "UPDATE USUARIOS SET CONTRASENA=? WHERE NOMBRE_USUARIO=?";
                    PreparedStatement consulta2 = conexion.prepareStatement(consultar_nombre);
                    consulta2.setString(1,nueva_contrasena.getText());
                    consulta2.setString(2,login.getCampo_usuario().getText());
                    consulta2.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Contraseña actualizada con éxito");
                    contra_actual.setText(nueva_contrasena.getText());

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"Error al actualizar la contraseña");

                }
                }else {
                    JOptionPane.showMessageDialog(null,"Por favor, ingrese una nueva contraseña");
                }
            }
        });
    }


}
