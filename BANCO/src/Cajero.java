import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cajero extends JFrame{
    private JPanel panel_cajero;
    private JTextField usuariotf;
    private JTextField saldotf;
    private JButton retirarButton;
    private JButton salirButton;
    private JButton depositarButton;
    private JButton visualizarTransacciónButton;
    private JList list1;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a8Button;
    private JButton regresarButton;
    private JButton borrarButton;
    private JButton a7Button;
    private JButton a0Button;
    private JTextField valor_ingresado;
    private JButton a6Button;
    private JButton a9Button;
    private JButton cambiar_contra;


    Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public Cajero(login login){
        super("Cajero");

        setSize(530,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel_cajero);



        conexion db=new conexion();
        conexion= db.conectar();

        try{
            String consultasaldoactual = "SELECT SALDO FROM USUARIOS WHERE NOMBRE_USUARIO=?";
            PreparedStatement consulta = conexion.prepareStatement(consultasaldoactual);
            consulta.setString(1,login.getCampo_usuario().getText());
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()){
                saldotf.setText(String.valueOf(resultado.getInt("SALDO")));
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Error al encontrar el saldo");
        }

        try{
            String consultar_nombre = "SELECT NOMBRE FROM USUARIOS WHERE NOMBRE_USUARIO=?";
            PreparedStatement consulta = conexion.prepareStatement(consultar_nombre);
            consulta.setString(1,login.getCampo_usuario().getText());
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()){
                usuariotf.setText(resultado.getString("NOMBRE"));
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"Error al encontrar el Nombre");
        }



        try {


        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor_ingresado.setText(valor_ingresado.getText()+"1");
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor_ingresado.setText(valor_ingresado.getText()+"2");
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor_ingresado.setText(valor_ingresado.getText()+"3");
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor_ingresado.setText(valor_ingresado.getText()+"4");
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor_ingresado.setText(valor_ingresado.getText()+"5");
            }
        });
        a6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor_ingresado.setText(valor_ingresado.getText()+"6");
            }
        });
        a7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor_ingresado.setText(valor_ingresado.getText()+"7");
            }
        });
        a8Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor_ingresado.setText(valor_ingresado.getText()+"8");
            }
        });
        a9Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor_ingresado.setText(valor_ingresado.getText()+"9");
            }
        });
        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor_ingresado.setText(valor_ingresado.getText()+"0");
            }
        });
        }catch (Exception exp){
            JOptionPane.showMessageDialog(null,"¡Error, ingrese un valor válido!");
        }
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                valor_ingresado.setText("");
            }
        });


        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"¡Gracias por usar nuestro Sistema!");
                dispose();
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login pantalla_login=new login();
                pantalla_login.setVisible(true);
                dispose();
                dispose();
            }
        });
        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{

            int valor_1= Integer.parseInt(saldotf.getText());
            int valor_2=Integer.parseInt(valor_ingresado.getText());
            int valor_final=valor_1+valor_2;

try{
    String asignacion_saldo = "UPDATE usuarios SET SALDO=? WHERE NOMBRE_USUARIO=?";
    PreparedStatement consulta2 = conexion.prepareStatement(asignacion_saldo);
    consulta2.setInt(1,valor_final);
    consulta2.setString(2,login.getCampo_usuario().getText());
    int resultado = consulta2.executeUpdate();
    if(resultado>=0){
        JOptionPane.showMessageDialog(null,"Valor Depositado con exito");

        try{
            String consultar_id="SELECT ID FROM USUARIOS WHERE NOMBRE_USUARIO=?";
            PreparedStatement consulta3=conexion.prepareStatement(consultar_id);
            consulta3.setString(1,login.getCampo_usuario().getText());
            ResultSet resultado3=consulta3.executeQuery();
            if (resultado3.next()){
                int id_obtenido=resultado3.getInt("ID");
                String insertar_transaccion="INSERT INTO TRANSACCIONES (TIPO_TRANSACCION, MONTO, USUARIO_ID) VALUES (?, ?, ?)";
                PreparedStatement consulta4=conexion.prepareStatement(insertar_transaccion);
                consulta4.setString(1,"Depósito");
                consulta4.setInt(2, Integer.parseInt(valor_ingresado.getText()));
                consulta4.setInt(3,id_obtenido);
                consulta4.executeUpdate();
            }else{
                JOptionPane.showMessageDialog(null,"Error al encontrar el ID");
            }

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"No se ha podido registrar la transacción");
        }


        saldotf.setText(String.valueOf(valor_final));
        valor_ingresado.setText("");

    }else{
        JOptionPane.showMessageDialog(null,"No se ha podido depositar");
        valor_ingresado.setText("");

    }
}catch (Exception ex){
    JOptionPane.showMessageDialog(null,"Ha ocurrido un error en el sistema");
    valor_ingresado.setText("");
}}catch (Exception exc){
                JOptionPane.showMessageDialog(null,"¡Error, ingrese un valor válido!");
                valor_ingresado.setText("");
            }
            }
        });





        retirarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{


                int valor_1= Integer.parseInt(saldotf.getText());
                int valor_2=Integer.parseInt(valor_ingresado.getText());
                int valor_final=valor_1-valor_2;

                if (valor_final>=0){

                try{
                    String asignacion_saldo = "UPDATE usuarios SET SALDO=? WHERE NOMBRE_USUARIO=?";
                    PreparedStatement consulta2 = conexion.prepareStatement(asignacion_saldo);
                    consulta2.setInt(1,valor_final);
                    consulta2.setString(2,login.getCampo_usuario().getText());
                    int resultado = consulta2.executeUpdate();

                    if (resultado>=0){
                        JOptionPane.showMessageDialog(null,"Valor Retirado con exito");



                        try{
                            String consultar_id="SELECT ID FROM USUARIOS WHERE NOMBRE_USUARIO=?";
                            PreparedStatement consulta3=conexion.prepareStatement(consultar_id);
                            consulta3.setString(1,login.getCampo_usuario().getText());
                            ResultSet resultado3=consulta3.executeQuery();
                            if (resultado3.next()){
                                int id_obtenido=resultado3.getInt("ID");
                                String insertar_transaccion="INSERT INTO TRANSACCIONES (TIPO_TRANSACCION, MONTO, USUARIO_ID) VALUES (?, ?, ?)";
                                PreparedStatement consulta4=conexion.prepareStatement(insertar_transaccion);
                                consulta4.setString(1,"Retiro");
                                consulta4.setInt(2, Integer.parseInt(valor_ingresado.getText()));
                                consulta4.setInt(3,id_obtenido);
                                consulta4.executeUpdate();
                            }else{
                                JOptionPane.showMessageDialog(null,"Error al encontrar el ID");
                            }

                        }catch (Exception ex){
                            JOptionPane.showMessageDialog(null,"No se ha podido registrar la transacción");
                        }



                        saldotf.setText(String.valueOf(valor_final));
                        valor_ingresado.setText("");
                    }else {
                        JOptionPane.showMessageDialog(null,"Saldo Insuficiente para retirar");
                        valor_ingresado.setText("");
                    }

                }catch (Exception ex){

                    JOptionPane.showMessageDialog(null,"Ha ocurrido un error en el sistema");
                    valor_ingresado.setText("");

                }
                }else {
                    JOptionPane.showMessageDialog(null,"Saldo Insuficiente para retirar");
                    valor_ingresado.setText("");

                }
                }catch (Exception exc){
                    JOptionPane.showMessageDialog(null,"¡Error, ingrese un valor válido!");
                    valor_ingresado.setText("");
                }

            }
        });
        visualizarTransacciónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConsultaUsuario pagina_consulta=new ConsultaUsuario(Cajero.this, login);
                pagina_consulta.setVisible(true);
                dispose();
            }
        });
        cambiar_contra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cambiar_contrasena pagina_contrasena=new cambiar_contrasena(Cajero.this,login);
                pagina_contrasena.setVisible(true);

                /*
                try {
                String ver_contrasena_actual="SELECT CONTRASENA FROM USUARIOS WHERE NOMBRE_USUARIO=?";
                PreparedStatement consulta=conexion.prepareStatement(ver_contrasena_actual);
                ResultSet revisar=consulta.executeQuery();
                if(revisar.next()){

                }
                }catch (Exception ex){

                }*/


            }
        });
    }
}
