package simrskhanza;

import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DlgUpdateSetting extends JDialog {
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;

    private final JTextField txtIP      = new JTextField(20);
    private final JTextField txtDir     = new JTextField(20);
    private final JButton btnBrowse = new JButton("Browse…");
    private final JButton btnSimpan = new JButton("Simpan");

    public DlgUpdateSetting(Frame owner) {
        super(owner, "Update Setting", true); 
        initUI();
        isGet();
    }

    private void initUI() {
        //---------------------------------
        // Panel form (IP & Directory)
        //---------------------------------
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = GridBagConstraints.WEST;

        // Label IP
        gc.gridx = 0; gc.gridy = 0;
        formPanel.add(new JLabel("Server IP:"), gc);

        // Text IP
        gc.gridx = 1; gc.gridy = 0; gc.gridwidth = 2; gc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(txtIP, gc);

        // Label Directory
        gc.gridx = 0; gc.gridy = 1; gc.gridwidth = 1; gc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Directory:"), gc);

        // Text Directory
        gc.gridx = 1; gc.gridy = 1; gc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(txtDir, gc);

        // Tombol Browse
        gc.gridx = 2; gc.gridy = 1; gc.fill = GridBagConstraints.NONE;
        formPanel.add(btnBrowse, gc);

        //---------------------------------
        // Tombol Simpan
        //---------------------------------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnSimpan);

        //---------------------------------
        // Root panel
        //---------------------------------
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        root.add(formPanel, BorderLayout.CENTER);
        root.add(bottomPanel, BorderLayout.PAGE_END);

        setContentPane(root);
        pack();                 // ukuran mengikuti komponen
        setLocationRelativeTo(getOwner());

        //---------------------------------
        // Event‑handler
        //---------------------------------
        btnBrowse.addActionListener(e -> chooseDirectory());
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
    }

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtIP.getText().trim().equals("")){
            Valid.textKosong(txtIP,"IP address");
        }else if(txtDir.getText().trim().equals("")){
            Valid.textKosong(txtDir,"Directory");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                    if(Sequel.mengedittf("update_setting","id=?","ip_address=?,directory=?",3,new String[]{
                            txtIP.getText(),txtDir.getText(),"1" 
                    })==true){
                        JOptionPane.showMessageDialog(null,"Data Tersimpan");
                        emptTeks();
                        isGet();
                    } 
            }else{
                JOptionPane.showMessageDialog(null,"Hanya admin yang dapat menyimpan data");
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    // untuk pilih folder
    private void chooseDirectory() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Pilih Folder");

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File dir = chooser.getSelectedFile();
            txtDir.setText(dir.getAbsolutePath());
        }
    }

     private void isGet() {
        try {
            ps=koneksi.prepareStatement(
                    "select update_setting.ip_address,update_setting.directory from update_setting where update_setting.id=?");
            try {
                ps.setString(1,"1");
                rs=ps.executeQuery();
                if(rs.next()){
                    txtIP.setText(rs.getString("ip_address"));
                    txtDir.setText(rs.getString("directory"));
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    private void emptTeks(){
        txtIP.setText("");
        txtDir.setText("");
    }

    // Getter (jika frmUtama butuh membaca nilainya kemudian)
    public String getIp()       { return txtIP.getText().trim(); }
    public String getDirectory(){ return txtDir.getText().trim(); }
}
