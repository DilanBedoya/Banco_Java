import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class principal extends JFrame{
    private JPanel panel_principal;
    private JButton UsuarioButton;

    private JPanel panel_principall;


    public principal() {
        super("Principal");

        setSize(500,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel_principall);

        UsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //conexion base
                conexion db=new conexion();
                db.conectar();

                //Pasar a la pantalla de login
                login pantalla_login=new login();
                pantalla_login.setVisible(true);
                dispose();

            }
        });

    }
}
