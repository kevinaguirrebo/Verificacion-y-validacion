
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class FrmPriemerExamen1 extends javax.swing.JFrame
{    
    int AñoActual=0;
    public FrmPriemerExamen1()
    {             
        initComponents();
        setLocationRelativeTo(null);
        MostrarTabla();
        Holders();
        FechaActual();
    }
    public void Holders()
    {
        PlaceHolder Holder;
        Holder = new PlaceHolder(TxtFechaNacimiento,"Seleccione todo para eliminar");
    }
  
     protected void Numeros(java.awt.event.KeyEvent evt)
    {       
        char c = evt.getKeyChar();            
        if(Character.isLetter(c))
        {            
            evt.consume();
        }       
    } 
     
     protected void Letras(java.awt.event.KeyEvent evt)
    {        
        char c = evt.getKeyChar();
        
        if(Character.isDigit(c))
        {
            evt.consume();
        }
    }
    
    
    public void Limpiar()
    {
        TxtDocumento.setFocusable(true);
        TxtNombres.setText("");
        TxtApellidos.setText("");
        TxtFechaNacimiento.setText("");
        TxtCorreo.setText("");   
        TxtTelefono.setText("");
        CbxEps.setSelectedIndex(0);      
    }
    
    public void Guardar()
    {
        String[] fn = TxtFechaNacimiento.getText().split("/");
        String fn1 = fn[2]+"-"+fn[1]+"-"+fn[0];
        ConexionPrimerExamen con = new ConexionPrimerExamen();
        Connection cnx = con.conectar();
        Statement st;
        try {
            String Documento = TxtDocumento.getText();
            String Nombres = TxtNombres.getText();
            String Apellidos = TxtApellidos.getText();           
            String Correo = TxtCorreo.getText();
            String Telefono = TxtTelefono.getText();
            int Eps = CbxEps.getSelectedIndex();
            

            st = cnx.createStatement();
            st.executeUpdate("INSERT INTO tblpacientes VALUES('" + Documento + "','" + Nombres + "','" + Apellidos + "','" + fn1 + "','" +Correo + "','"+Telefono+"','"+Eps+"');");
            System.out.println("listo");
            JOptionPane.showMessageDialog(null, "codigo guardado exitosamente");
            Limpiar();
        } catch (SQLException ex) {
            Logger.getLogger(primerexamen.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no pudo");
        }
    }
    
    public void buscar()
    {   
        MostrarTabla();
        ConexionPrimerExamen con = new ConexionPrimerExamen();
        Connection cnx =  con.conectar();
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery("Select * from tblpacientes where PKDocumento ='"+TxtDocumento.getText()+"'");
                                               
            while(rs.next())
            {
               TxtDocumento.setText(rs.getString(1));
               TxtNombres.setText(rs.getString(2));
               TxtApellidos.setText(rs.getString(3));
               TxtFechaNacimiento.setText(rs.getString(4));
               TxtCorreo.setText(rs.getString(5));
               TxtTelefono.setText(rs.getString(6));
               CbxEps.setSelectedIndex(rs.getInt(7));
               TxtDocumento.setText(rs.getString(8));              
               cnx.close();
            }            
        } 
        catch (Exception e)
        {
            System.out.println(e);
        }  
    }
    public void MostrarTabla()
    {
        LimpiarTabla();
        DefaultTableModel modelo;
        Statement st;
        ResultSet rs;
        ConexionPrimerExamen con = new ConexionPrimerExamen();
        Connection cnx = con.conectar();
        
        try {
            st = cnx.createStatement();
            rs = st.executeQuery(" SELECT pa.PKDocumento, pa.Nombre, pa.Apellido, pa.FechaNacimiento, pa.Correo, pa.Telefono, eps.Descripcion FROM tbleps eps, tblpacientes pa WHERE pa.FKCodigo_TblEps = eps.PKCodigo;");
            Object [] arreglo1 = new  Object[7];
                                    
            while(rs.next())
            {
                arreglo1[0] = rs.getString(1);
                arreglo1[1] = rs.getString(2);
                arreglo1[2] = rs.getString(3);
                arreglo1[3] = rs.getString(4);               
                arreglo1[4] = rs.getString(5);               
                arreglo1[5] = rs.getString(6);               
                arreglo1[6] = rs.getString(7);                                                        
                modelo =(DefaultTableModel)this.TablaExamen.getModel();
                modelo.addRow(arreglo1);
               TablaExamen.setModel(modelo);
            }          
        } 
        catch (Exception e)
        {
            System.out.println(e);
        }   
    }
    
    public void GuardarActualizar()          
    {   
        ConexionPrimerExamen con = new ConexionPrimerExamen();
        Connection cnx = con.conectar();
        try 
        {
            Statement st = cnx.createStatement();
            st.executeUpdate("UPDATE tblpacientes SET Nombre = '"+TxtNombres.getText()+"',Apellido = '"+TxtApellidos.getText()+"', FechaNacimiento = '"+TxtFechaNacimiento.getText()+"', Correo = '"+TxtCorreo.getText()+ "', Telefono = '"+TxtTelefono.getText()+"', FKCodigo_TblEps ="+CbxEps.getSelectedIndex()+"  WHERE PKDocumento = '"+TxtDocumento.getText()+"'");
                    
        } 
        catch (Exception e)
        {
            System.out.println(e);
        } 
        MostrarTabla();       
    }
    
    public void  Funcion_ValidarExistencia()
    {
      ConexionPrimerExamen conn = new ConexionPrimerExamen();
      Connection cnx = conn.conectar(); 
      try
      {
        Statement st = cnx.createStatement();
        ResultSet rs;
        st.executeQuery("SELECT COUNT(PKDocumento) FROM tblpacientes where PKDocumento="+ TxtDocumento.getText()+" ");
        rs = st.getResultSet();
        if(rs.next())
        {
            int dato = Integer.parseInt( rs.getString(1));
            if(dato >= 1)
            {
                JOptionPane.showMessageDialog(null,"Numero De Documento ya registrado","",JOptionPane.ERROR_MESSAGE);
                TxtDocumento.setForeground(Color.orange);
            }
            else
            {
               Guardar();
               TxtDocumento.setForeground(Color.BLACK);
            }
          }
      }
      catch(Exception e)
      {
          System.out.println("no conectado"+e.getMessage());
      }
  }
   public void LimpiarTabla(){
        try {
            DefaultTableModel modelo;
            modelo = (DefaultTableModel) this.TablaExamen.getModel();
            for(int x=0; x<TablaExamen.getColumnCount();x++)
            {
                modelo.removeRow(0);
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }     
    }
    
   public void Eliminar()
    {       
        ConexionPrimerExamen con = new ConexionPrimerExamen();
        Connection cnx = con.conectar();
        String Datoo = JOptionPane.showInputDialog(null,"Ingrese el usuario que desea eliminar");
        if(JOptionPane.showConfirmDialog(null,"¿Esta totalmente seguro de eliminar toda la informacion relacionada con el Usuario "+Datoo+"?","CUIDADO",JOptionPane.WARNING_MESSAGE)==0)
        {
            try {
            Statement st = cnx.createStatement();
            st.executeUpdate("DELETE FROM tblpacientes where PKDocumento='"+Datoo+"'");
            JOptionPane.showMessageDialog(null,"Usuario eliminado con exito");
            } catch (Exception e) {
                System.out.println(e);
            }
        }       
    }
    
    public void FechaActual()
    {
        String FechaUtilizar;
        Date FechaLocal = new Date();
        DateFormat f=new SimpleDateFormat("yyyy");
        FechaUtilizar =f.format(FechaLocal);   
        AñoActual=  Integer.parseInt(FechaUtilizar);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelPrincipalExamen = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TxtDocumento = new javax.swing.JTextField();
        TxtNombres = new javax.swing.JTextField();
        TxtApellidos = new javax.swing.JTextField();
        TxtFechaNacimiento = new javax.swing.JTextField();
        TxtCorreo = new javax.swing.JTextField();
        TxtTelefono = new javax.swing.JTextField();
        CbxEps = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaExamen = new javax.swing.JTable();
        BtnGuardar = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanelPrincipalExamen.setBackground(new java.awt.Color(153, 153, 153));
        PanelPrincipalExamen.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)), "Examen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Documento:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Nombres:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Apellidos:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Fecha de nacimiento:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Correo:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Telefono:");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Eps:");

        TxtDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtDocumentoKeyTyped(evt);
            }
        });

        TxtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtNombresKeyTyped(evt);
            }
        });

        TxtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtApellidosKeyTyped(evt);
            }
        });

        TxtFechaNacimiento.setFont(new java.awt.Font("Times New Roman", 0, 10)); // NOI18N
        TxtFechaNacimiento.setToolTipText("dd/MM/yyyy");
        TxtFechaNacimiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtFechaNacimientoMouseClicked(evt);
            }
        });
        TxtFechaNacimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtFechaNacimientoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtFechaNacimientoKeyTyped(evt);
            }
        });

        TxtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtTelefonoKeyTyped(evt);
            }
        });

        CbxEps.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "Coomeva", "Confama" }));

        TablaExamen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TablaExamen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Documento", "Nombres", "Apellidos", "Fecha de nacimiento", "Correo", "Telefono", "Eps"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TablaExamen);
        if (TablaExamen.getColumnModel().getColumnCount() > 0) {
            TablaExamen.getColumnModel().getColumn(0).setResizable(false);
        }

        BtnGuardar.setBackground(new java.awt.Color(0, 0, 0));
        BtnGuardar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BtnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        BtnGuardar.setText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnBuscar.setBackground(new java.awt.Color(0, 0, 0));
        BtnBuscar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BtnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        BtnActualizar.setBackground(new java.awt.Color(0, 0, 0));
        BtnActualizar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BtnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        BtnActualizar.setText("Actualizar");
        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });

        BtnEliminar.setBackground(new java.awt.Color(0, 0, 0));
        BtnEliminar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BtnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        BtnEliminar.setText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        BtnCancelar.setBackground(new java.awt.Color(0, 0, 0));
        BtnCancelar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        BtnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        BtnCancelar.setText("Cancelar");

        javax.swing.GroupLayout PanelPrincipalExamenLayout = new javax.swing.GroupLayout(PanelPrincipalExamen);
        PanelPrincipalExamen.setLayout(PanelPrincipalExamenLayout);
        PanelPrincipalExamenLayout.setHorizontalGroup(
            PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalExamenLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(TxtDocumento)
                        .addComponent(TxtNombres)
                        .addComponent(TxtApellidos)
                        .addComponent(TxtCorreo)
                        .addComponent(TxtTelefono)
                        .addComponent(CbxEps, 0, 163, Short.MAX_VALUE))
                    .addComponent(TxtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnBuscar)
                    .addComponent(BtnActualizar)
                    .addComponent(BtnEliminar)
                    .addComponent(BtnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPrincipalExamenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        PanelPrincipalExamenLayout.setVerticalGroup(
            PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrincipalExamenLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TxtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnActualizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(PanelPrincipalExamenLayout.createSequentialGroup()
                        .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnEliminar)
                            .addComponent(TxtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(BtnCancelar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(TxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPrincipalExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(CbxEps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipalExamen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelPrincipalExamen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        if (TxtDocumento.getText().equals("")||TxtNombres.getText().equals("")||CbxEps.getSelectedIndex()==0||
                TxtApellidos.getText().equals("")||TxtFechaNacimiento.getText().equals("")|| TxtCorreo.getText().equals("")|| TxtTelefono.getText().equals("")) 
        {
            JOptionPane.showMessageDialog(null, "Debe Completar todos los campos");
        } 
        else 
        {
            Funcion_ValidarExistencia();
            MostrarTabla();
            Limpiar();
        }
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        buscar();
        MostrarTabla();
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        Eliminar();
        MostrarTabla();
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        GuardarActualizar();
        Limpiar();
        MostrarTabla();
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void TxtNombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNombresKeyTyped
        Letras(evt);
    }//GEN-LAST:event_TxtNombresKeyTyped

    private void TxtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtApellidosKeyTyped
        Letras(evt);
    }//GEN-LAST:event_TxtApellidosKeyTyped

    private void TxtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtTelefonoKeyTyped
        Numeros(evt);
    }//GEN-LAST:event_TxtTelefonoKeyTyped

    private void TxtFechaNacimientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtFechaNacimientoMouseClicked
        TxtFechaNacimiento.setEditable(true);
    }//GEN-LAST:event_TxtFechaNacimientoMouseClicked

    private void TxtFechaNacimientoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtFechaNacimientoKeyReleased
        String fn = TxtFechaNacimiento.getText();
        if (fn.length() == 2)
        {
            TxtFechaNacimiento.setText(fn+"/");
            if(Integer.parseInt(fn) > 31)
            {
                TxtFechaNacimiento.setText("31/");
            }else
            {
                TxtFechaNacimiento.setText(fn+"/");
            }
        }
        if (fn.length() == 5) 
        {
            String[] fn1 = fn.split("/");
            if(Integer.parseInt(fn1[1]) > 12)
            {
                TxtFechaNacimiento.setText(fn1[0]+"/12/");
            }else
            {
                TxtFechaNacimiento.setText(fn+"/");
            }
        }
        if (fn.length() == 10)
        {   
            String[] fn1 = fn.split("/");
            if(Integer.parseInt(fn1[2]) > AñoActual)
            {
                TxtFechaNacimiento.setText(fn1[0]+"/"+fn1[1]+"/"+AñoActual);
            }else
            {
                TxtFechaNacimiento.setText(fn);
            }            
        }
    }//GEN-LAST:event_TxtFechaNacimientoKeyReleased

    private void TxtDocumentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtDocumentoKeyTyped
        Numeros(evt);
    }//GEN-LAST:event_TxtDocumentoKeyTyped

    private void TxtFechaNacimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtFechaNacimientoKeyTyped
        Numeros(evt);
    }//GEN-LAST:event_TxtFechaNacimientoKeyTyped

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPriemerExamen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPriemerExamen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPriemerExamen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPriemerExamen1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPriemerExamen1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JComboBox<String> CbxEps;
    private javax.swing.JPanel PanelPrincipalExamen;
    private javax.swing.JTable TablaExamen;
    private javax.swing.JTextField TxtApellidos;
    private javax.swing.JTextField TxtCorreo;
    private javax.swing.JTextField TxtDocumento;
    private javax.swing.JTextField TxtFechaNacimiento;
    private javax.swing.JTextField TxtNombres;
    private javax.swing.JTextField TxtTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
