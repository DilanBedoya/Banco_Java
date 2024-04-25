import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaUsuario  extends JFrame{
    private JPanel panel_historial;
    private JTextArea tipo;
    private JTextArea monto;
    private JTextArea fecha;
    private JTextArea usuario_id;
    private JButton regresarButton;
    private JButton descargarButton;

    Connection conexion;

    public Connection getConexion() {
        return conexion;
    }


    private void generarPDF() {
        // Crear un objeto PageFormat
        PageFormat pageFormat = new PageFormat();
        // Establecer la orientación de la página
        pageFormat.setOrientation(PageFormat.LANDSCAPE);

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                // Títulos de los campos
                g2d.drawString("Historial de transacciones", 100, 100);
                g2d.drawString("Fechas y horas:", 25, 120);
                g2d.drawString("IDs de transacción:", 175, 120); // Alinear con la columna de fechas
                g2d.drawString("Tipos de transacción:", 300, 120); // Alinear con la columna de IDs de transacción
                g2d.drawString("Montos de transacción:", 500, 120); // Alinear con la columna de tipos de transacción

                // Contenido de los campos
                String[] fechas = fecha.getText().split("\n");
                String[] idsTransaccion = usuario_id.getText().split("\n");
                String[] tiposTransaccion = tipo.getText().split("\n");
                String[] montosTransaccion = monto.getText().split("\n");

                int yPos = 140; // Posición inicial Y para las fechas
                for (int i = 0; i < fechas.length; i++) {
                    g2d.drawString(fechas[i], 25, yPos);
                    yPos += 20; // Incrementar la posición Y para el siguiente campo
                }

                yPos = 140; // Restablecer la posición Y para los otros campos
                for (int i = 0; i < idsTransaccion.length; i++) {
                    g2d.drawString(idsTransaccion[i], 175, yPos);
                    yPos += 20; // Incrementar la posición Y para el siguiente campo
                }

                yPos = 140; // Restablecer la posición Y para los otros campos
                for (int i = 0; i < tiposTransaccion.length; i++) {
                    g2d.drawString(tiposTransaccion[i], 300, yPos);
                    yPos += 20; // Incrementar la posición Y para el siguiente campo
                }

                yPos = 140; // Restablecer la posición Y para los otros campos
                for (int i = 0; i < montosTransaccion.length; i++) {
                    g2d.drawString(montosTransaccion[i], 500, yPos);
                    yPos += 20; // Incrementar la posición Y para el siguiente campo
                }

                return Printable.PAGE_EXISTS;
            }
        }, pageFormat);

        try {
            job.print();
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(null, "Error al imprimir: " + e.getMessage());
        }
    }


    public ConsultaUsuario(Cajero cajero, login login){
        super("Consulta");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel_historial);

        conexion db=new conexion();
        conexion=db.conectar();





        try {
            String consultar_id = "SELECT ID FROM USUARIOS WHERE NOMBRE_USUARIO=?";
            PreparedStatement consulta3 = conexion.prepareStatement(consultar_id);
            consulta3.setString(1, login.getCampo_usuario().getText());
            ResultSet resultado3 = consulta3.executeQuery();

            if (resultado3.next()) {
                int id_obtenido = resultado3.getInt("ID");

                String consultar_transacciones = "SELECT TIPO_TRANSACCION, MONTO, FECHA_HORA FROM TRANSACCIONES WHERE USUARIO_ID=?";
                PreparedStatement consulta4 = conexion.prepareStatement(consultar_transacciones);
                consulta4.setInt(1, id_obtenido);
                ResultSet resultadoTransacciones = consulta4.executeQuery();

                while (resultadoTransacciones.next()) {
                    tipo.append(resultadoTransacciones.getString("TIPO_TRANSACCION")+"\n");
                    monto.append(resultadoTransacciones.getInt("MONTO") + "\n");
                    fecha.append(resultadoTransacciones.getString("FECHA_HORA") + "\n");
                    usuario_id.append(id_obtenido +"\n");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error al encontrar el ID");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta: " + ex.getMessage());
        }



        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conexion db=new conexion();
                conexion=db.conectar();

                try{
                    String consultausaurios="SELECT *FROM USUARIOS WHERE NOMBRE_USUARIO=?";
                    PreparedStatement consult = conexion.prepareStatement(consultausaurios);
                    consult.setString(1,login.getCampo_usuario().getText());
                    ResultSet resultado_consulta = consult.executeQuery();
                    if(resultado_consulta.next()){
                        Cajero pagina_cajero=new Cajero(login);
                        pagina_cajero.setVisible(true);
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "La consulta ha fallado");
                    }
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error al Regresar");
                }



                dispose();



            }
        });
        descargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarPDF();
            }
        });
    }
}
