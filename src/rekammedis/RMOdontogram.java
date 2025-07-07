/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import javax.swing.ButtonGroup;
import laporan.DlgCariPenyakit;
import kepegawaian.DlgCariDokter; 

/**
 *
 * @author perpustakaan
 */
public final class RMOdontogram extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPenyakit diagnosa=new DlgCariPenyakit(null,false);
    private String finger="",param="",param_edit="";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMOdontogram(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Permintaan","No.Rawat","No.RM","Nama Pasien","Tgl.Pemeriksaan","Kode Dokter","Nama Dokter",
            "Bagian","Diagnosa","Kode ICD","Hasil","Rahang","Catatan"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < tabMode.getColumnCount() ; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            column.setPreferredWidth(150);
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        // TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        // KdDokter.setDocument(new batasInput((byte)20).getKata(KdDokter));
        // TBPB.setDocument(new batasInput((byte)5).getKata(TBPB));
        // BB.setDocument(new batasInput((byte)5).getKata(BB));
        // TD.setDocument(new batasInput((byte)8).getKata(TD));
        // KdDiagnosa.setDocument(new batasInput((byte)5).getKata(KdDiagnosa));
        // RR.setDocument(new batasInput((byte)5).getKata(RR));
        // Suhu.setDocument(new batasInput((byte)5).getKata(Suhu));
        // SpO2.setDocument(new batasInput((byte)5).getKata(SpO2));
        // Diagnosa.setDocument(new batasInput((int)100).getKata(Diagnosa));
        // TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){        
                     KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                     NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }  
                KdDokter.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        diagnosa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(diagnosa.getTable().getSelectedRow()!= -1){        
                     Diagnosa.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),1).toString());
                     KdDiagnosa.setText(diagnosa.getTable().getValueAt(diagnosa.getTable().getSelectedRow(),0).toString());
                }  
                KdDiagnosa.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        ChkInput.setSelected(false);
        isForm();
        
        autoNomor();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSkriningNutrisi = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        btnDokter = new widget.Button();
        jLabel24 = new widget.Label();
        Diagnosa = new widget.TextBox();
        jLabel8 = new widget.Label();
        NoPermintaan = new widget.TextBox();
        KdDiagnosa = new widget.TextBox();
        jLabel32 = new widget.Label();
        HasilPemeriksaan = new widget.ComboBox();
        jLabel75 = new widget.Label();
        Panel18 = new usu.widget.glass.PanelGlass();
        jLabel20 = new widget.Label();
        jRadioButton1 = new javax.swing.JRadioButton();
        Panel19 = new usu.widget.glass.PanelGlass();
        Panel20 = new usu.widget.glass.PanelGlass();
        Panel21 = new usu.widget.glass.PanelGlass();
        Panel22 = new usu.widget.glass.PanelGlass();
        Panel23 = new usu.widget.glass.PanelGlass();
        Panel24 = new usu.widget.glass.PanelGlass();
        Panel25 = new usu.widget.glass.PanelGlass();
        Panel26 = new usu.widget.glass.PanelGlass();
        Panel27 = new usu.widget.glass.PanelGlass();
        Panel28 = new usu.widget.glass.PanelGlass();
        Panel29 = new usu.widget.glass.PanelGlass();
        Panel30 = new usu.widget.glass.PanelGlass();
        Panel31 = new usu.widget.glass.PanelGlass();
        Panel34 = new usu.widget.glass.PanelGlass();
        Panel35 = new usu.widget.glass.PanelGlass();
        Panel40 = new usu.widget.glass.PanelGlass();
        Panel36 = new usu.widget.glass.PanelGlass();
        Panel37 = new usu.widget.glass.PanelGlass();
        Panel41 = new usu.widget.glass.PanelGlass();
        Panel43 = new usu.widget.glass.PanelGlass();
        Panel32 = new usu.widget.glass.PanelGlass();
        Panel33 = new usu.widget.glass.PanelGlass();
        Panel38 = new usu.widget.glass.PanelGlass();
        Panel39 = new usu.widget.glass.PanelGlass();
        Panel42 = new usu.widget.glass.PanelGlass();
        Panel45 = new usu.widget.glass.PanelGlass();
        Panel44 = new usu.widget.glass.PanelGlass();
        Panel46 = new usu.widget.glass.PanelGlass();
        Panel47 = new usu.widget.glass.PanelGlass();
        Panel48 = new usu.widget.glass.PanelGlass();
        Panel49 = new usu.widget.glass.PanelGlass();
        Panel50 = new usu.widget.glass.PanelGlass();
        Panel51 = new usu.widget.glass.PanelGlass();
        Panel52 = new usu.widget.glass.PanelGlass();
        Panel53 = new usu.widget.glass.PanelGlass();
        Panel54 = new usu.widget.glass.PanelGlass();
        Panel55 = new usu.widget.glass.PanelGlass();
        Panel56 = new usu.widget.glass.PanelGlass();
        Panel57 = new usu.widget.glass.PanelGlass();
        Panel58 = new usu.widget.glass.PanelGlass();
        Panel59 = new usu.widget.glass.PanelGlass();
        Panel60 = new usu.widget.glass.PanelGlass();
        Panel61 = new usu.widget.glass.PanelGlass();
        Panel62 = new usu.widget.glass.PanelGlass();
        Panel63 = new usu.widget.glass.PanelGlass();
        Panel64 = new usu.widget.glass.PanelGlass();
        Panel65 = new usu.widget.glass.PanelGlass();
        Panel66 = new usu.widget.glass.PanelGlass();
        Panel67 = new usu.widget.glass.PanelGlass();
        Panel68 = new usu.widget.glass.PanelGlass();
        Panel69 = new usu.widget.glass.PanelGlass();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jRadioButton13 = new javax.swing.JRadioButton();
        jRadioButton14 = new javax.swing.JRadioButton();
        jRadioButton15 = new javax.swing.JRadioButton();
        jRadioButton16 = new javax.swing.JRadioButton();
        jRadioButton17 = new javax.swing.JRadioButton();
        jRadioButton18 = new javax.swing.JRadioButton();
        jRadioButton19 = new javax.swing.JRadioButton();
        jRadioButton20 = new javax.swing.JRadioButton();
        jRadioButton21 = new javax.swing.JRadioButton();
        jRadioButton22 = new javax.swing.JRadioButton();
        jRadioButton23 = new javax.swing.JRadioButton();
        jRadioButton24 = new javax.swing.JRadioButton();
        jRadioButton25 = new javax.swing.JRadioButton();
        jRadioButton26 = new javax.swing.JRadioButton();
        jRadioButton27 = new javax.swing.JRadioButton();
        jRadioButton28 = new javax.swing.JRadioButton();
        jRadioButton29 = new javax.swing.JRadioButton();
        jRadioButton30 = new javax.swing.JRadioButton();
        jRadioButton31 = new javax.swing.JRadioButton();
        jRadioButton32 = new javax.swing.JRadioButton();
        jRadioButton33 = new javax.swing.JRadioButton();
        jRadioButton34 = new javax.swing.JRadioButton();
        jRadioButton35 = new javax.swing.JRadioButton();
        jRadioButton36 = new javax.swing.JRadioButton();
        jRadioButton37 = new javax.swing.JRadioButton();
        jRadioButton38 = new javax.swing.JRadioButton();
        jRadioButton39 = new javax.swing.JRadioButton();
        jRadioButton40 = new javax.swing.JRadioButton();
        jRadioButton41 = new javax.swing.JRadioButton();
        jRadioButton42 = new javax.swing.JRadioButton();
        jRadioButton43 = new javax.swing.JRadioButton();
        jRadioButton44 = new javax.swing.JRadioButton();
        jRadioButton45 = new javax.swing.JRadioButton();
        jRadioButton46 = new javax.swing.JRadioButton();
        jRadioButton47 = new javax.swing.JRadioButton();
        jRadioButton48 = new javax.swing.JRadioButton();
        jRadioButton49 = new javax.swing.JRadioButton();
        jRadioButton50 = new javax.swing.JRadioButton();
        jRadioButton51 = new javax.swing.JRadioButton();
        jRadioButton52 = new javax.swing.JRadioButton();
        btnDiagnosa = new widget.Button();
        Rahang = new widget.ComboBox();
        jLabel76 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        CatatanPemeriksaan = new widget.TextArea();
        jLabel77 = new widget.Label();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSkriningNutrisi.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningNutrisi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningNutrisi.setForeground(new java.awt.Color(50, 50, 50));
        MnSkriningNutrisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningNutrisi.setText("Formulir Skrining Nutrisi Pasien Dewasa");
        MnSkriningNutrisi.setName("MnSkriningNutrisi"); // NOI18N
        MnSkriningNutrisi.setPreferredSize(new java.awt.Dimension(260, 26));
        MnSkriningNutrisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningNutrisiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSkriningNutrisi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Skrining Nutrisi Pasien Dewasa ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-07-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-07-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 400));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(900, 545));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-07-2025" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalItemStateChanged(evt);
            }
        });
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(460, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(390, 40, 60, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(80, 40, 94, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(177, 40, 187, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('2');
        btnDokter.setToolTipText("ALt+2");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(360, 40, 28, 23);

        jLabel24.setText("Diagnosa Gigi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(610, 170, 90, 23);

        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(710, 170, 419, 23);

        jLabel8.setText("No. Permintaan :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(550, 40, 100, 23);

        NoPermintaan.setHighlighter(null);
        NoPermintaan.setEditable(false);
        NoPermintaan.setName("NoPermintaan"); // NOI18N
        FormInput.add(NoPermintaan);
        NoPermintaan.setBounds(660, 40, 150, 23);

        KdDiagnosa.setFocusTraversalPolicyProvider(true);
        KdDiagnosa.setEditable(false);
        KdDiagnosa.setName("KdDiagnosa"); // NOI18N
        KdDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HRKeyPressed(evt);
            }
        });
        FormInput.add(KdDiagnosa);
        KdDiagnosa.setBounds(1133, 170, 97, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Kode ICD :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(1140, 150, 90, 23);

        HasilPemeriksaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Erupsi","Hilang","Karies","Sisa Akar","Tumpatan","Goyang","Calculus","Abces","Impaksi","Protesa","Hiperemia","Persistensi","Malposisi"  }));
        HasilPemeriksaan.setName("HasilPemeriksaan"); // NOI18N
        HasilPemeriksaan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG1ItemStateChanged(evt);
            }
        });
        HasilPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG1KeyPressed(evt);
            }
        });
        FormInput.add(HasilPemeriksaan);
        HasilPemeriksaan.setBounds(710, 200, 130, 23);

        jLabel75.setText("Catatan Pemeriksaan :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(590, 230, 110, 23);

        Panel18.setBackground(new java.awt.Color(255, 255, 255));
        Panel18.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel18.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel18.setOpaque(true);
        Panel18.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel18.setRound(false);
        Panel18.setWarna(new java.awt.Color(110, 110, 110));
        Panel18.setLayout(null);
        FormInput.add(Panel18);
        Panel18.setBounds(70, 100, 40, 40);

        jLabel20.setText("Dokter Gigi :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 40, 75, 23);

        jRadioButton1.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton1.setText("48");
        jRadioButton1.setName("jRadioButton1"); // NOI18N
        jRadioButton1.setOpaque(false);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton1);
        jRadioButton1.setBounds(30, 340, 40, 21);

        Panel19.setBackground(new java.awt.Color(255, 255, 255));
        Panel19.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel19.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel19.setOpaque(true);
        Panel19.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel19.setRound(false);
        Panel19.setWarna(new java.awt.Color(110, 110, 110));
        Panel19.setLayout(null);
        FormInput.add(Panel19);
        Panel19.setBounds(110, 100, 40, 40);

        Panel20.setBackground(new java.awt.Color(255, 255, 255));
        Panel20.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel20.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel20.setOpaque(true);
        Panel20.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel20.setRound(false);
        Panel20.setWarna(new java.awt.Color(110, 110, 110));
        Panel20.setLayout(null);
        FormInput.add(Panel20);
        Panel20.setBounds(150, 100, 40, 40);

        Panel21.setBackground(new java.awt.Color(255, 255, 255));
        Panel21.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel21.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel21.setOpaque(true);
        Panel21.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel21.setRound(false);
        Panel21.setWarna(new java.awt.Color(110, 110, 110));
        Panel21.setLayout(null);
        FormInput.add(Panel21);
        Panel21.setBounds(190, 100, 40, 40);

        Panel22.setBackground(new java.awt.Color(255, 255, 255));
        Panel22.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel22.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel22.setOpaque(true);
        Panel22.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel22.setRound(false);
        Panel22.setWarna(new java.awt.Color(110, 110, 110));
        Panel22.setLayout(null);
        FormInput.add(Panel22);
        Panel22.setBounds(470, 100, 40, 40);

        Panel23.setBackground(new java.awt.Color(255, 255, 255));
        Panel23.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel23.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel23.setOpaque(true);
        Panel23.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel23.setRound(false);
        Panel23.setWarna(new java.awt.Color(110, 110, 110));
        Panel23.setLayout(null);
        FormInput.add(Panel23);
        Panel23.setBounds(510, 100, 40, 40);

        Panel24.setBackground(new java.awt.Color(255, 255, 255));
        Panel24.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel24.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel24.setOpaque(true);
        Panel24.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel24.setRound(false);
        Panel24.setWarna(new java.awt.Color(110, 110, 110));
        Panel24.setLayout(null);
        FormInput.add(Panel24);
        Panel24.setBounds(550, 100, 40, 40);

        Panel25.setBackground(new java.awt.Color(255, 255, 255));
        Panel25.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel25.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel25.setOpaque(true);
        Panel25.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel25.setRound(false);
        Panel25.setWarna(new java.awt.Color(110, 110, 110));
        Panel25.setLayout(null);
        FormInput.add(Panel25);
        Panel25.setBounds(590, 100, 40, 40);

        Panel26.setBackground(new java.awt.Color(255, 255, 255));
        Panel26.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel26.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel26.setOpaque(true);
        Panel26.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel26.setRound(false);
        Panel26.setWarna(new java.awt.Color(110, 110, 110));
        Panel26.setLayout(null);
        FormInput.add(Panel26);
        Panel26.setBounds(630, 100, 40, 40);

        Panel27.setBackground(new java.awt.Color(255, 255, 255));
        Panel27.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel27.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel27.setOpaque(true);
        Panel27.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel27.setRound(false);
        Panel27.setWarna(new java.awt.Color(110, 110, 110));
        Panel27.setLayout(null);
        FormInput.add(Panel27);
        Panel27.setBounds(150, 150, 40, 40);

        Panel28.setBackground(new java.awt.Color(255, 255, 255));
        Panel28.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel28.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel28.setOpaque(true);
        Panel28.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel28.setRound(false);
        Panel28.setWarna(new java.awt.Color(110, 110, 110));
        Panel28.setLayout(null);
        FormInput.add(Panel28);
        Panel28.setBounds(510, 150, 40, 40);

        Panel29.setBackground(new java.awt.Color(255, 255, 255));
        Panel29.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel29.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel29.setOpaque(true);
        Panel29.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel29.setRound(false);
        Panel29.setWarna(new java.awt.Color(110, 110, 110));
        Panel29.setLayout(null);
        FormInput.add(Panel29);
        Panel29.setBounds(30, 100, 40, 40);

        Panel30.setBackground(new java.awt.Color(255, 255, 255));
        Panel30.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel30.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel30.setOpaque(true);
        Panel30.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel30.setRound(false);
        Panel30.setWarna(new java.awt.Color(110, 110, 110));
        Panel30.setLayout(null);
        FormInput.add(Panel30);
        Panel30.setBounds(230, 150, 40, 40);

        Panel31.setBackground(new java.awt.Color(255, 255, 255));
        Panel31.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel31.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel31.setOpaque(true);
        Panel31.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel31.setRound(false);
        Panel31.setWarna(new java.awt.Color(110, 110, 110));
        Panel31.setLayout(null);
        FormInput.add(Panel31);
        Panel31.setBounds(470, 150, 40, 40);

        Panel34.setBackground(new java.awt.Color(255, 255, 255));
        Panel34.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel34.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel34.setOpaque(true);
        Panel34.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel34.setRound(false);
        Panel34.setWarna(new java.awt.Color(110, 110, 110));
        Panel34.setLayout(null);
        FormInput.add(Panel34);
        Panel34.setBounds(150, 300, 40, 40);

        Panel35.setBackground(new java.awt.Color(255, 255, 255));
        Panel35.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel35.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel35.setOpaque(true);
        Panel35.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel35.setRound(false);
        Panel35.setWarna(new java.awt.Color(110, 110, 110));
        Panel35.setLayout(null);
        FormInput.add(Panel35);
        Panel35.setBounds(190, 300, 40, 40);

        Panel40.setBackground(new java.awt.Color(255, 255, 255));
        Panel40.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel40.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel40.setOpaque(true);
        Panel40.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel40.setRound(false);
        Panel40.setWarna(new java.awt.Color(110, 110, 110));
        Panel40.setLayout(null);
        FormInput.add(Panel40);
        Panel40.setBounds(630, 300, 40, 40);

        Panel36.setBackground(new java.awt.Color(255, 255, 255));
        Panel36.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel36.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel36.setOpaque(true);
        Panel36.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel36.setRound(false);
        Panel36.setWarna(new java.awt.Color(110, 110, 110));
        Panel36.setLayout(null);
        FormInput.add(Panel36);
        Panel36.setBounds(470, 300, 40, 40);

        Panel37.setBackground(new java.awt.Color(255, 255, 255));
        Panel37.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel37.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel37.setOpaque(true);
        Panel37.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel37.setRound(false);
        Panel37.setWarna(new java.awt.Color(110, 110, 110));
        Panel37.setLayout(null);
        FormInput.add(Panel37);
        Panel37.setBounds(510, 300, 40, 40);

        Panel41.setBackground(new java.awt.Color(255, 255, 255));
        Panel41.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel41.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel41.setOpaque(true);
        Panel41.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel41.setRound(false);
        Panel41.setWarna(new java.awt.Color(110, 110, 110));
        Panel41.setLayout(null);
        FormInput.add(Panel41);
        Panel41.setBounds(150, 250, 40, 40);

        Panel43.setBackground(new java.awt.Color(255, 255, 255));
        Panel43.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel43.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel43.setOpaque(true);
        Panel43.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel43.setRound(false);
        Panel43.setWarna(new java.awt.Color(110, 110, 110));
        Panel43.setLayout(null);
        FormInput.add(Panel43);
        Panel43.setBounds(30, 300, 40, 40);

        Panel32.setBackground(new java.awt.Color(255, 255, 255));
        Panel32.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel32.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel32.setOpaque(true);
        Panel32.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel32.setRound(false);
        Panel32.setWarna(new java.awt.Color(110, 110, 110));
        Panel32.setLayout(null);
        FormInput.add(Panel32);
        Panel32.setBounds(70, 300, 40, 40);

        Panel33.setBackground(new java.awt.Color(255, 255, 255));
        Panel33.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel33.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel33.setOpaque(true);
        Panel33.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel33.setRound(false);
        Panel33.setWarna(new java.awt.Color(110, 110, 110));
        Panel33.setLayout(null);
        FormInput.add(Panel33);
        Panel33.setBounds(110, 300, 40, 40);

        Panel38.setBackground(new java.awt.Color(255, 255, 255));
        Panel38.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel38.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel38.setOpaque(true);
        Panel38.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel38.setRound(false);
        Panel38.setWarna(new java.awt.Color(110, 110, 110));
        Panel38.setLayout(null);
        FormInput.add(Panel38);
        Panel38.setBounds(550, 300, 40, 40);

        Panel39.setBackground(new java.awt.Color(255, 255, 255));
        Panel39.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel39.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel39.setOpaque(true);
        Panel39.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel39.setRound(false);
        Panel39.setWarna(new java.awt.Color(110, 110, 110));
        Panel39.setLayout(null);
        FormInput.add(Panel39);
        Panel39.setBounds(590, 300, 40, 40);

        Panel42.setBackground(new java.awt.Color(255, 255, 255));
        Panel42.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel42.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel42.setOpaque(true);
        Panel42.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel42.setRound(false);
        Panel42.setWarna(new java.awt.Color(110, 110, 110));
        Panel42.setLayout(null);
        FormInput.add(Panel42);
        Panel42.setBounds(510, 250, 40, 40);

        Panel45.setBackground(new java.awt.Color(255, 255, 255));
        Panel45.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel45.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel45.setOpaque(true);
        Panel45.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel45.setRound(false);
        Panel45.setWarna(new java.awt.Color(110, 110, 110));
        Panel45.setLayout(null);
        FormInput.add(Panel45);
        Panel45.setBounds(470, 250, 40, 40);

        Panel44.setBackground(new java.awt.Color(255, 255, 255));
        Panel44.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel44.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel44.setOpaque(true);
        Panel44.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel44.setRound(false);
        Panel44.setWarna(new java.awt.Color(110, 110, 110));
        Panel44.setLayout(null);
        FormInput.add(Panel44);
        Panel44.setBounds(190, 250, 40, 40);

        Panel46.setBackground(new java.awt.Color(255, 255, 255));
        Panel46.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon1.png"))); // NOI18N
        Panel46.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel46.setOpaque(true);
        Panel46.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel46.setRound(false);
        Panel46.setWarna(new java.awt.Color(110, 110, 110));
        Panel46.setLayout(null);
        FormInput.add(Panel46);
        Panel46.setBounds(190, 150, 40, 40);

        Panel47.setBackground(new java.awt.Color(255, 255, 255));
        Panel47.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel47.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel47.setOpaque(true);
        Panel47.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel47.setRound(false);
        Panel47.setWarna(new java.awt.Color(110, 110, 110));
        Panel47.setLayout(null);
        FormInput.add(Panel47);
        Panel47.setBounds(270, 150, 40, 40);

        Panel48.setBackground(new java.awt.Color(255, 255, 255));
        Panel48.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel48.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel48.setOpaque(true);
        Panel48.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel48.setRound(false);
        Panel48.setWarna(new java.awt.Color(110, 110, 110));
        Panel48.setLayout(null);
        FormInput.add(Panel48);
        Panel48.setBounds(310, 150, 40, 40);

        Panel49.setBackground(new java.awt.Color(255, 255, 255));
        Panel49.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel49.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel49.setOpaque(true);
        Panel49.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel49.setRound(false);
        Panel49.setWarna(new java.awt.Color(110, 110, 110));
        Panel49.setLayout(null);
        FormInput.add(Panel49);
        Panel49.setBounds(350, 150, 40, 40);

        Panel50.setBackground(new java.awt.Color(255, 255, 255));
        Panel50.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel50.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel50.setOpaque(true);
        Panel50.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel50.setRound(false);
        Panel50.setWarna(new java.awt.Color(110, 110, 110));
        Panel50.setLayout(null);
        FormInput.add(Panel50);
        Panel50.setBounds(390, 150, 40, 40);

        Panel51.setBackground(new java.awt.Color(255, 255, 255));
        Panel51.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel51.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel51.setOpaque(true);
        Panel51.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel51.setRound(false);
        Panel51.setWarna(new java.awt.Color(110, 110, 110));
        Panel51.setLayout(null);
        FormInput.add(Panel51);
        Panel51.setBounds(430, 150, 40, 40);

        Panel52.setBackground(new java.awt.Color(255, 255, 255));
        Panel52.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel52.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel52.setOpaque(true);
        Panel52.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel52.setRound(false);
        Panel52.setWarna(new java.awt.Color(110, 110, 110));
        Panel52.setLayout(null);
        FormInput.add(Panel52);
        Panel52.setBounds(430, 100, 40, 40);

        Panel53.setBackground(new java.awt.Color(255, 255, 255));
        Panel53.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel53.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel53.setOpaque(true);
        Panel53.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel53.setRound(false);
        Panel53.setWarna(new java.awt.Color(110, 110, 110));
        Panel53.setLayout(null);
        FormInput.add(Panel53);
        Panel53.setBounds(390, 100, 40, 40);

        Panel54.setBackground(new java.awt.Color(255, 255, 255));
        Panel54.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel54.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel54.setOpaque(true);
        Panel54.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel54.setRound(false);
        Panel54.setWarna(new java.awt.Color(110, 110, 110));
        Panel54.setLayout(null);
        FormInput.add(Panel54);
        Panel54.setBounds(350, 100, 40, 40);

        Panel55.setBackground(new java.awt.Color(255, 255, 255));
        Panel55.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel55.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel55.setOpaque(true);
        Panel55.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel55.setRound(false);
        Panel55.setWarna(new java.awt.Color(110, 110, 110));
        Panel55.setLayout(null);
        FormInput.add(Panel55);
        Panel55.setBounds(310, 100, 40, 40);

        Panel56.setBackground(new java.awt.Color(255, 255, 255));
        Panel56.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel56.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel56.setOpaque(true);
        Panel56.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel56.setRound(false);
        Panel56.setWarna(new java.awt.Color(110, 110, 110));
        Panel56.setLayout(null);
        FormInput.add(Panel56);
        Panel56.setBounds(270, 100, 40, 40);

        Panel57.setBackground(new java.awt.Color(255, 255, 255));
        Panel57.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel57.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel57.setOpaque(true);
        Panel57.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel57.setRound(false);
        Panel57.setWarna(new java.awt.Color(110, 110, 110));
        Panel57.setLayout(null);
        FormInput.add(Panel57);
        Panel57.setBounds(230, 100, 40, 40);

        Panel58.setBackground(new java.awt.Color(255, 255, 255));
        Panel58.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel58.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel58.setOpaque(true);
        Panel58.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel58.setRound(false);
        Panel58.setWarna(new java.awt.Color(110, 110, 110));
        Panel58.setLayout(null);
        FormInput.add(Panel58);
        Panel58.setBounds(230, 300, 40, 40);

        Panel59.setBackground(new java.awt.Color(255, 255, 255));
        Panel59.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel59.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel59.setOpaque(true);
        Panel59.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel59.setRound(false);
        Panel59.setWarna(new java.awt.Color(110, 110, 110));
        Panel59.setLayout(null);
        FormInput.add(Panel59);
        Panel59.setBounds(270, 300, 40, 40);

        Panel60.setBackground(new java.awt.Color(255, 255, 255));
        Panel60.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel60.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel60.setOpaque(true);
        Panel60.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel60.setRound(false);
        Panel60.setWarna(new java.awt.Color(110, 110, 110));
        Panel60.setLayout(null);
        FormInput.add(Panel60);
        Panel60.setBounds(310, 300, 40, 40);

        Panel61.setBackground(new java.awt.Color(255, 255, 255));
        Panel61.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel61.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel61.setOpaque(true);
        Panel61.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel61.setRound(false);
        Panel61.setWarna(new java.awt.Color(110, 110, 110));
        Panel61.setLayout(null);
        FormInput.add(Panel61);
        Panel61.setBounds(350, 300, 40, 40);

        Panel62.setBackground(new java.awt.Color(255, 255, 255));
        Panel62.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel62.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel62.setOpaque(true);
        Panel62.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel62.setRound(false);
        Panel62.setWarna(new java.awt.Color(110, 110, 110));
        Panel62.setLayout(null);
        FormInput.add(Panel62);
        Panel62.setBounds(390, 300, 40, 40);

        Panel63.setBackground(new java.awt.Color(255, 255, 255));
        Panel63.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel63.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel63.setOpaque(true);
        Panel63.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel63.setRound(false);
        Panel63.setWarna(new java.awt.Color(110, 110, 110));
        Panel63.setLayout(null);
        FormInput.add(Panel63);
        Panel63.setBounds(430, 300, 40, 40);

        Panel64.setBackground(new java.awt.Color(255, 255, 255));
        Panel64.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel64.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel64.setOpaque(true);
        Panel64.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel64.setRound(false);
        Panel64.setWarna(new java.awt.Color(110, 110, 110));
        Panel64.setLayout(null);
        FormInput.add(Panel64);
        Panel64.setBounds(430, 250, 40, 40);

        Panel65.setBackground(new java.awt.Color(255, 255, 255));
        Panel65.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel65.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel65.setOpaque(true);
        Panel65.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel65.setRound(false);
        Panel65.setWarna(new java.awt.Color(110, 110, 110));
        Panel65.setLayout(null);
        FormInput.add(Panel65);
        Panel65.setBounds(390, 250, 40, 40);

        Panel66.setBackground(new java.awt.Color(255, 255, 255));
        Panel66.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel66.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel66.setOpaque(true);
        Panel66.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel66.setRound(false);
        Panel66.setWarna(new java.awt.Color(110, 110, 110));
        Panel66.setLayout(null);
        FormInput.add(Panel66);
        Panel66.setBounds(350, 250, 40, 40);

        Panel67.setBackground(new java.awt.Color(255, 255, 255));
        Panel67.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel67.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel67.setOpaque(true);
        Panel67.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel67.setRound(false);
        Panel67.setWarna(new java.awt.Color(110, 110, 110));
        Panel67.setLayout(null);
        FormInput.add(Panel67);
        Panel67.setBounds(310, 250, 40, 40);

        Panel68.setBackground(new java.awt.Color(255, 255, 255));
        Panel68.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel68.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel68.setOpaque(true);
        Panel68.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel68.setRound(false);
        Panel68.setWarna(new java.awt.Color(110, 110, 110));
        Panel68.setLayout(null);
        FormInput.add(Panel68);
        Panel68.setBounds(270, 250, 40, 40);

        Panel69.setBackground(new java.awt.Color(255, 255, 255));
        Panel69.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/odon2.png"))); // NOI18N
        Panel69.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        Panel69.setOpaque(true);
        Panel69.setPreferredSize(new java.awt.Dimension(200, 200));
        Panel69.setRound(false);
        Panel69.setWarna(new java.awt.Color(110, 110, 110));
        Panel69.setLayout(null);
        FormInput.add(Panel69);
        Panel69.setBounds(230, 250, 40, 40);

        jRadioButton2.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton2.setText("18");
        jRadioButton2.setOpaque(false);
        jRadioButton2.setName("jRadioButton2"); // NOI18N
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton2);
        jRadioButton2.setBounds(30, 80, 40, 21);

        jRadioButton3.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton3.setText("17");
        jRadioButton3.setOpaque(false);
        jRadioButton3.setName("jRadioButton3"); // NOI18N
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton3);
        jRadioButton3.setBounds(70, 80, 40, 21);

        jRadioButton4.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton4.setText("16");
        jRadioButton4.setOpaque(false);
        jRadioButton4.setName("jRadioButton4"); // NOI18N
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton4);
        jRadioButton4.setBounds(110, 80, 40, 21);

        jRadioButton5.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton5.setText("15");
        jRadioButton5.setOpaque(false);
        jRadioButton5.setName("jRadioButton5"); // NOI18N
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton5);
        jRadioButton5.setBounds(150, 80, 40, 21);

        jRadioButton6.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton6.setText("14");
        jRadioButton6.setOpaque(false);
        jRadioButton6.setName("jRadioButton6"); // NOI18N
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton6);
        jRadioButton6.setBounds(190, 80, 40, 21);

        jRadioButton7.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton7.setText("13");
        jRadioButton7.setOpaque(false);
        jRadioButton7.setName("jRadioButton7"); // NOI18N
        jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton7ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton7);
        jRadioButton7.setBounds(230, 80, 40, 21);

        jRadioButton8.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton8.setText("12");
        jRadioButton8.setOpaque(false);
        jRadioButton8.setName("jRadioButton8"); // NOI18N
        jRadioButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton8ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton8);
        jRadioButton8.setBounds(270, 80, 40, 21);

        jRadioButton9.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton9.setText("11");
        jRadioButton9.setOpaque(false);
        jRadioButton9.setName("jRadioButton9"); // NOI18N
        jRadioButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton9ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton9);
        jRadioButton9.setBounds(310, 80, 40, 21);

        jRadioButton10.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton10.setText("21");
        jRadioButton10.setOpaque(false);
        jRadioButton10.setName("jRadioButton10"); // NOI18N
        jRadioButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton10ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton10);
        jRadioButton10.setBounds(350, 80, 40, 21);

        jRadioButton11.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton11.setText("22");
        jRadioButton11.setOpaque(false);
        jRadioButton11.setName("jRadioButton11"); // NOI18N
        jRadioButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton11ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton11);
        jRadioButton11.setBounds(390, 80, 40, 21);

        jRadioButton12.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton12.setText("23");
        jRadioButton12.setOpaque(false);
        jRadioButton12.setName("jRadioButton12"); // NOI18N
        jRadioButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton12ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton12);
        jRadioButton12.setBounds(430, 80, 40, 21);

        jRadioButton13.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton13.setText("24");
        jRadioButton13.setOpaque(false);
        jRadioButton13.setName("jRadioButton13"); // NOI18N
        jRadioButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton13ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton13);
        jRadioButton13.setBounds(470, 80, 40, 21);

        jRadioButton14.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton14.setText("25");
        jRadioButton14.setOpaque(false);
        jRadioButton14.setName("jRadioButton14"); // NOI18N
        jRadioButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton14ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton14);
        jRadioButton14.setBounds(510, 80, 40, 21);

        jRadioButton15.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton15.setText("26");
        jRadioButton15.setOpaque(false);
        jRadioButton15.setName("jRadioButton15"); // NOI18N
        jRadioButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton15ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton15);
        jRadioButton15.setBounds(550, 80, 40, 21);

        jRadioButton16.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton16.setText("27");
        jRadioButton16.setOpaque(false);
        jRadioButton16.setName("jRadioButton16"); // NOI18N
        jRadioButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton16ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton16);
        jRadioButton16.setBounds(590, 80, 40, 21);

        jRadioButton17.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton17.setText("28");
        jRadioButton17.setOpaque(false);
        jRadioButton17.setName("jRadioButton17"); // NOI18N
        jRadioButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton17ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton17);
        jRadioButton17.setBounds(630, 80, 40, 21);

        jRadioButton18.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton18.setText("55");
        jRadioButton18.setOpaque(false);
        jRadioButton18.setName("jRadioButton18"); // NOI18N
        jRadioButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton18ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton18);
        jRadioButton18.setBounds(150, 190, 40, 21);

        jRadioButton19.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton19.setText("54");
        jRadioButton19.setOpaque(false);
        jRadioButton19.setName("jRadioButton19"); // NOI18N
        jRadioButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton19ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton19);
        jRadioButton19.setBounds(190, 190, 40, 21);

        jRadioButton20.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton20.setText("53");
        jRadioButton20.setOpaque(false);
        jRadioButton20.setName("jRadioButton20"); // NOI18N
        jRadioButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton20ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton20);
        jRadioButton20.setBounds(230, 190, 40, 21);

        jRadioButton21.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton21.setText("52");
        jRadioButton21.setOpaque(false);
        jRadioButton21.setName("jRadioButton21"); // NOI18N
        jRadioButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton21ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton21);
        jRadioButton21.setBounds(270, 190, 40, 21);

        jRadioButton22.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton22.setText("51");
        jRadioButton22.setOpaque(false);
        jRadioButton22.setName("jRadioButton22"); // NOI18N
        jRadioButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton22ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton22);
        jRadioButton22.setBounds(310, 190, 40, 21);

        jRadioButton23.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton23.setText("61");
        jRadioButton23.setOpaque(false);
        jRadioButton23.setName("jRadioButton23"); // NOI18N
        jRadioButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton23ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton23);
        jRadioButton23.setBounds(350, 190, 40, 21);

        jRadioButton24.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton24.setText("62");
        jRadioButton24.setOpaque(false);
        jRadioButton24.setName("jRadioButton24"); // NOI18N
        jRadioButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton24ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton24);
        jRadioButton24.setBounds(390, 190, 40, 21);

        jRadioButton25.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton25.setText("63");
        jRadioButton25.setOpaque(false);
        jRadioButton25.setName("jRadioButton25"); // NOI18N
        jRadioButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton25ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton25);
        jRadioButton25.setBounds(430, 190, 40, 21);

        jRadioButton26.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton26.setText("64");
        jRadioButton26.setOpaque(false);
        jRadioButton26.setName("jRadioButton26"); // NOI18N
        jRadioButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton26ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton26);
        jRadioButton26.setBounds(470, 190, 40, 21);

        jRadioButton27.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton27.setText("65");
        jRadioButton27.setOpaque(false);
        jRadioButton27.setName("jRadioButton27"); // NOI18N
        jRadioButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton27ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton27);
        jRadioButton27.setBounds(510, 190, 40, 21);

        jRadioButton28.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton28.setText("85");
        jRadioButton28.setOpaque(false);
        jRadioButton28.setName("jRadioButton28"); // NOI18N
        jRadioButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton28ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton28);
        jRadioButton28.setBounds(150, 230, 40, 21);

        jRadioButton29.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton29.setText("84");
        jRadioButton29.setOpaque(false);
        jRadioButton29.setName("jRadioButton29"); // NOI18N
        jRadioButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton29ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton29);
        jRadioButton29.setBounds(190, 230, 40, 21);

        jRadioButton30.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton30.setText("83");
        jRadioButton30.setOpaque(false);
        jRadioButton30.setName("jRadioButton30"); // NOI18N
        jRadioButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton30ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton30);
        jRadioButton30.setBounds(230, 230, 40, 21);

        jRadioButton31.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton31.setText("82");
        jRadioButton31.setOpaque(false);
        jRadioButton31.setName("jRadioButton31"); // NOI18N
        jRadioButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton31ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton31);
        jRadioButton31.setBounds(270, 230, 40, 21);

        jRadioButton32.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton32.setText("81");
        jRadioButton32.setOpaque(false);
        jRadioButton32.setName("jRadioButton32"); // NOI18N
        jRadioButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton32ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton32);
        jRadioButton32.setBounds(310, 230, 40, 21);

        jRadioButton33.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton33.setText("71");
        jRadioButton33.setOpaque(false);
        jRadioButton33.setName("jRadioButton33"); // NOI18N
        jRadioButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton33ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton33);
        jRadioButton33.setBounds(350, 230, 40, 21);

        jRadioButton34.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton34.setText("72");
        jRadioButton34.setOpaque(false);
        jRadioButton34.setName("jRadioButton34"); // NOI18N
        jRadioButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton34ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton34);
        jRadioButton34.setBounds(390, 230, 40, 21);

        jRadioButton35.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton35.setText("73");
        jRadioButton35.setOpaque(false);
        jRadioButton35.setName("jRadioButton35"); // NOI18N
        jRadioButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton35ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton35);
        jRadioButton35.setBounds(430, 230, 40, 21);

        jRadioButton36.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton36.setText("74");
        jRadioButton36.setOpaque(false);
        jRadioButton36.setName("jRadioButton36"); // NOI18N
        jRadioButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton36ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton36);
        jRadioButton36.setBounds(470, 230, 40, 21);

        jRadioButton37.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton37.setText("75");
        jRadioButton37.setOpaque(false);
        jRadioButton37.setName("jRadioButton37"); // NOI18N
        jRadioButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton37ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton37);
        jRadioButton37.setBounds(510, 230, 40, 21);

        jRadioButton38.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton38.setText("38");
        jRadioButton38.setOpaque(false);
        jRadioButton38.setName("jRadioButton38"); // NOI18N
        jRadioButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton38ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton38);
        jRadioButton38.setBounds(630, 340, 40, 21);

        jRadioButton39.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton39.setText("37");
        jRadioButton39.setOpaque(false);
        jRadioButton39.setName("jRadioButton39"); // NOI18N
        jRadioButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton39ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton39);
        jRadioButton39.setBounds(590, 340, 40, 21);

        jRadioButton40.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton40.setText("36");
        jRadioButton40.setOpaque(false);
        jRadioButton40.setName("jRadioButton40"); // NOI18N
        jRadioButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton40ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton40);
        jRadioButton40.setBounds(550, 340, 40, 21);

        jRadioButton41.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton41.setText("35");
        jRadioButton41.setOpaque(false);
        jRadioButton41.setName("jRadioButton41"); // NOI18N
        jRadioButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton41ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton41);
        jRadioButton41.setBounds(510, 340, 40, 21);

        jRadioButton42.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton42.setText("34");
        jRadioButton42.setOpaque(false);
        jRadioButton42.setName("jRadioButton42"); // NOI18N
        jRadioButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton42ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton42);
        jRadioButton42.setBounds(470, 340, 40, 21);

        jRadioButton43.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton43.setText("33");
        jRadioButton43.setOpaque(false);
        jRadioButton43.setName("jRadioButton43"); // NOI18N
        jRadioButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton43ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton43);
        jRadioButton43.setBounds(430, 340, 40, 21);

        jRadioButton44.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton44.setText("32");
        jRadioButton44.setOpaque(false);
        jRadioButton44.setName("jRadioButton44"); // NOI18N
        jRadioButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton44ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton44);
        jRadioButton44.setBounds(390, 340, 40, 21);

        jRadioButton45.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton45.setText("31");
        jRadioButton45.setOpaque(false);
        jRadioButton45.setName("jRadioButton45"); // NOI18N
        jRadioButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton45ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton45);
        jRadioButton45.setBounds(350, 340, 40, 21);

        jRadioButton46.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton46.setText("41");
        jRadioButton46.setOpaque(false);
        jRadioButton46.setName("jRadioButton46"); // NOI18N
        jRadioButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton46ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton46);
        jRadioButton46.setBounds(310, 340, 40, 21);

        jRadioButton47.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton47.setText("42");
        jRadioButton47.setOpaque(false);
        jRadioButton47.setName("jRadioButton47"); // NOI18N
        jRadioButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton47ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton47);
        jRadioButton47.setBounds(270, 340, 40, 21);

        jRadioButton48.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton48.setText("43");
        jRadioButton48.setOpaque(false);
        jRadioButton48.setName("jRadioButton48"); // NOI18N
        jRadioButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton48ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton48);
        jRadioButton48.setBounds(230, 340, 40, 21);

        jRadioButton49.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton49.setText("44");
        jRadioButton49.setOpaque(false);
        jRadioButton49.setName("jRadioButton49"); // NOI18N
        jRadioButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton49ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton49);
        jRadioButton49.setBounds(190, 340, 40, 21);

        jRadioButton50.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton50.setText("45");
        jRadioButton50.setOpaque(false);
        jRadioButton50.setName("jRadioButton50"); // NOI18N
        jRadioButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton50ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton50);
        jRadioButton50.setBounds(150, 340, 40, 21);

        jRadioButton51.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton51.setText("46");
        jRadioButton51.setOpaque(false);
        jRadioButton51.setName("jRadioButton51"); // NOI18N
        jRadioButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton51ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton51);
        jRadioButton51.setBounds(110, 340, 40, 21);

        jRadioButton52.setForeground(new java.awt.Color(0, 0, 0));
        jRadioButton52.setText("47");
        jRadioButton52.setOpaque(false);
        jRadioButton52.setName("jRadioButton52"); // NOI18N
        jRadioButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton52ActionPerformed(evt);
            }
        });
        FormInput.add(jRadioButton52);
        jRadioButton52.setBounds(70, 340, 40, 21);

        ButtonGroup group = new ButtonGroup();
        group.add(jRadioButton1);
        group.add(jRadioButton2);
        group.add(jRadioButton3);
        group.add(jRadioButton4);
        group.add(jRadioButton5);
        group.add(jRadioButton6);
        group.add(jRadioButton7);
        group.add(jRadioButton8);
        group.add(jRadioButton9);
        group.add(jRadioButton10);
        group.add(jRadioButton11);
        group.add(jRadioButton12);
        group.add(jRadioButton13);
        group.add(jRadioButton14);
        group.add(jRadioButton15);
        group.add(jRadioButton16);
        group.add(jRadioButton17);
        group.add(jRadioButton18);
        group.add(jRadioButton19);
        group.add(jRadioButton20);
        group.add(jRadioButton21);
        group.add(jRadioButton22);
        group.add(jRadioButton23);
        group.add(jRadioButton24);
        group.add(jRadioButton25);
        group.add(jRadioButton26);
        group.add(jRadioButton27);
        group.add(jRadioButton28);
        group.add(jRadioButton29);
        group.add(jRadioButton30);
        group.add(jRadioButton31);
        group.add(jRadioButton32);
        group.add(jRadioButton33);
        group.add(jRadioButton34);
        group.add(jRadioButton35);
        group.add(jRadioButton36);
        group.add(jRadioButton37);
        group.add(jRadioButton38);
        group.add(jRadioButton39);
        group.add(jRadioButton40);
        group.add(jRadioButton41);
        group.add(jRadioButton42);
        group.add(jRadioButton43);
        group.add(jRadioButton44);
        group.add(jRadioButton45);
        group.add(jRadioButton46);
        group.add(jRadioButton47);
        group.add(jRadioButton48);
        group.add(jRadioButton49);
        group.add(jRadioButton50);
        group.add(jRadioButton51);
        group.add(jRadioButton52);

        btnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa.setMnemonic('2');
        btnDiagnosa.setToolTipText("ALt+2");
        btnDiagnosa.setName("btnDiagnosa"); // NOI18N
        btnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosaActionPerformed(evt);
            }
        });
        btnDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(btnDiagnosa);
        btnDiagnosa.setBounds(1230, 170, 28, 23);

        Rahang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-","Rahang Atas","Rahang Bawah" }));
        Rahang.setName("Rahang"); // NOI18N
        Rahang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SG3ItemStateChanged(evt);
            }
        });
        Rahang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SG3KeyPressed(evt);
            }
        });
        FormInput.add(Rahang);
        Rahang.setBounds(930, 200, 130, 23);

        jLabel76.setText("Rahang :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(850, 200, 70, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        CatatanPemeriksaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        CatatanPemeriksaan.setColumns(20);
        CatatanPemeriksaan.setRows(5);
        CatatanPemeriksaan.setName("CatatanPemeriksaan"); // NOI18N
        CatatanPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(CatatanPemeriksaan);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(710, 230, 540, 73);

        jLabel77.setText("Hasil Pemeriksaan :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(590, 200, 110, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>                        

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {                                 
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}                                

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {                                   
        Valid.pindah(evt,TCari,BtnSimpan);
}                                  

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {                                          
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(NoPermintaan.getText().trim().equals("")){
            Valid.textKosong(NoPermintaan,"Nomor Permintaan");
        }else{

            param="";

            if (jRadioButton9.isSelected()==true){
                param="1.1";
            } 
            if (jRadioButton8.isSelected()==true){
                param="1.2";
            } 
            if (jRadioButton7.isSelected()==true){
                param="1.3";
            } 
            if (jRadioButton6.isSelected()==true){
                param="1.4";
            } 
            if (jRadioButton5.isSelected()==true){
                param="1.5";
            } 
            if (jRadioButton4.isSelected()==true){
                param="1.6";
            } 
            if (jRadioButton3.isSelected()==true){
                param="1.7";
            } 
            if (jRadioButton2.isSelected()==true){
                param="1.8";
            } 
            if (jRadioButton10.isSelected()==true){
                param="2.1";
            } 
            if (jRadioButton11.isSelected()==true){
                param="2.2";
            } 
            if (jRadioButton12.isSelected()==true){
                param="2.3";
            } 
            if (jRadioButton13.isSelected()==true){
                param="2.4";
            } 
            if (jRadioButton14.isSelected()==true){
                param="2.5";
            } 
            if (jRadioButton15.isSelected()==true){
                param="2.6";
            } 
            if (jRadioButton16.isSelected()==true){
                param="2.7";
            } 
            if (jRadioButton17.isSelected()==true){
                param="2.8";
            } 
            if (jRadioButton45.isSelected()==true){
                param="3.1";
            } 
            if (jRadioButton44.isSelected()==true){
                param="3.2";
            } 
            if (jRadioButton43.isSelected()==true){
                param="3.3";
            } 
            if (jRadioButton42.isSelected()==true){
                param="3.4";
            } 
            if (jRadioButton41.isSelected()==true){
                param="3.5";
            } 
            if (jRadioButton40.isSelected()==true){
                param="3.6";
            } 
            if (jRadioButton39.isSelected()==true){
                param="3.7";
            } 
            if (jRadioButton38.isSelected()==true){
                param="3.8";
            } 
            if (jRadioButton46.isSelected()==true){
                param="4.1";
            } 
            if (jRadioButton47.isSelected()==true){
                param="4.2";
            } 
            if (jRadioButton48.isSelected()==true){
                param="4.3";
            } 
            if (jRadioButton49.isSelected()==true){
                param="4.4";
            } 
            if (jRadioButton50.isSelected()==true){
                param="4.5";
            } 
            if (jRadioButton51.isSelected()==true){
                param="4.6";
            } 
            if (jRadioButton52.isSelected()==true){
                param="4.7";
            } 
            if (jRadioButton1.isSelected()==true){
                param="4.8";
            } 
            if (jRadioButton22.isSelected()==true){
                param="5.1";
            } 
            if (jRadioButton21.isSelected()==true){
                param="5.2";
            } 
            if (jRadioButton20.isSelected()==true){
                param="5.3";
            } 
            if (jRadioButton19.isSelected()==true){
                param="5.4";
            } 
            if (jRadioButton18.isSelected()==true){
                param="5.5";
            } 
            if (jRadioButton23.isSelected()==true){
                param="6.1";
            } 
            if (jRadioButton24.isSelected()==true){
                param="6.2";
            } 
            if (jRadioButton25.isSelected()==true){
                param="6.3";
            } 
            if (jRadioButton26.isSelected()==true){
                param="6.4";
            } 
            if (jRadioButton27.isSelected()==true){
                param="6.5";
            } 
            if (jRadioButton33.isSelected()==true){
                param="7.1";
            } 
            if (jRadioButton34.isSelected()==true){
                param="7.2";
            } 
            if (jRadioButton35.isSelected()==true){
                param="7.3";
            } 
            if (jRadioButton36.isSelected()==true){
                param="7.4";
            }
            if (jRadioButton37.isSelected()==true){
                param="7.5";
            } 
            if (jRadioButton32.isSelected()==true){
                param="8.1";
            } 
            if (jRadioButton31.isSelected()==true){
                param="8.2";
            } 
            if (jRadioButton30.isSelected()==true){
                param="8.3";
            } 
            if (jRadioButton29.isSelected()==true){
                param="8.4";
            } 
            if (jRadioButton28.isSelected()==true){
                param="8.5";
            } 

            System.out.println(param);

            if(Sequel.menyimpantf("pemeriksaan_gigi","?,?,?,?,?,?,?,?,?,?","Data",10,new String[]{
                TNoRw.getText(),KdDokter.getText(),param,Diagnosa.getText(),KdDiagnosa.getText(),HasilPemeriksaan.getSelectedItem().toString(),CatatanPemeriksaan.getText(),
                Valid.SetTgl(Tanggal.getSelectedItem()+""),NoPermintaan.getText(),Rahang.getSelectedItem().toString()
            })==true){
                tampil();
                emptTeks();
            }  
        }
}                                         

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {                                     
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,cmbSkor3,BtnBatal);
        }
}                                    

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {                                         
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}                                        

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {                                    
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}                                   

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }  
}                                        

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {                                    
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}                                   

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {                                        
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Dokter");
        }else if(NoPermintaan.getText().trim().equals("")){
            Valid.textKosong(NoPermintaan,"Nomor Permintaan");
        }else{

            param_edit="";

            if (jRadioButton9.isSelected()==true){
                param_edit="1.1";
            } 
            if (jRadioButton8.isSelected()==true){
                param_edit="1.2";
            } 
            if (jRadioButton7.isSelected()==true){
                param_edit="1.3";
            } 
            if (jRadioButton6.isSelected()==true){
                param_edit="1.4";
            } 
            if (jRadioButton5.isSelected()==true){
                param_edit="1.5";
            } 
            if (jRadioButton4.isSelected()==true){
                param_edit="1.6";
            } 
            if (jRadioButton3.isSelected()==true){
                param_edit="1.7";
            } 
            if (jRadioButton2.isSelected()==true){
                param_edit="1.8";
            } 
            if (jRadioButton10.isSelected()==true){
                param_edit="2.1";
            } 
            if (jRadioButton11.isSelected()==true){
                param_edit="2.2";
            } 
            if (jRadioButton12.isSelected()==true){
                param_edit="2.3";
            } 
            if (jRadioButton13.isSelected()==true){
                param_edit="2.4";
            } 
            if (jRadioButton14.isSelected()==true){
                param_edit="2.5";
            } 
            if (jRadioButton15.isSelected()==true){
                param_edit="2.6";
            } 
            if (jRadioButton16.isSelected()==true){
                param_edit="2.7";
            } 
            if (jRadioButton17.isSelected()==true){
                param_edit="2.8";
            } 
            if (jRadioButton45.isSelected()==true){
                param_edit="3.1";
            } 
            if (jRadioButton44.isSelected()==true){
                param_edit="3.2";
            } 
            if (jRadioButton43.isSelected()==true){
                param_edit="3.3";
            } 
            if (jRadioButton42.isSelected()==true){
                param_edit="3.4";
            } 
            if (jRadioButton41.isSelected()==true){
                param_edit="3.5";
            } 
            if (jRadioButton40.isSelected()==true){
                param_edit="3.6";
            } 
            if (jRadioButton39.isSelected()==true){
                param_edit="3.7";
            } 
            if (jRadioButton38.isSelected()==true){
                param_edit="3.8";
            } 
            if (jRadioButton46.isSelected()==true){
                param_edit="4.1";
            } 
            if (jRadioButton47.isSelected()==true){
                param_edit="4.2";
            } 
            if (jRadioButton48.isSelected()==true){
                param_edit="4.3";
            } 
            if (jRadioButton49.isSelected()==true){
                param_edit="4.4";
            } 
            if (jRadioButton50.isSelected()==true){
                param_edit="4.5";
            } 
            if (jRadioButton51.isSelected()==true){
                param_edit="4.6";
            } 
            if (jRadioButton52.isSelected()==true){
                param_edit="4.7";
            } 
            if (jRadioButton1.isSelected()==true){
                param_edit="4.8";
            } 
            if (jRadioButton22.isSelected()==true){
                param_edit="5.1";
            } 
            if (jRadioButton21.isSelected()==true){
                param_edit="5.2";
            } 
            if (jRadioButton20.isSelected()==true){
                param_edit="5.3";
            } 
            if (jRadioButton19.isSelected()==true){
                param_edit="5.4";
            } 
            if (jRadioButton18.isSelected()==true){
                param_edit="5.5";
            } 
            if (jRadioButton23.isSelected()==true){
                param_edit="6.1";
            } 
            if (jRadioButton24.isSelected()==true){
                param_edit="6.2";
            } 
            if (jRadioButton25.isSelected()==true){
                param_edit="6.3";
            } 
            if (jRadioButton26.isSelected()==true){
                param_edit="6.4";
            } 
            if (jRadioButton27.isSelected()==true){
                param_edit="6.5";
            } 
            if (jRadioButton33.isSelected()==true){
                param_edit="7.1";
            } 
            if (jRadioButton34.isSelected()==true){
                param_edit="7.2";
            } 
            if (jRadioButton35.isSelected()==true){
                param_edit="7.3";
            } 
            if (jRadioButton36.isSelected()==true){
                param_edit="7.4";
            }
            if (jRadioButton37.isSelected()==true){
                param_edit="7.5";
            } 
            if (jRadioButton32.isSelected()==true){
                param_edit="8.1";
            } 
            if (jRadioButton31.isSelected()==true){
                param_edit="8.2";
            } 
            if (jRadioButton30.isSelected()==true){
                param_edit="8.3";
            } 
            if (jRadioButton29.isSelected()==true){
                param_edit="8.4";
            } 
            if (jRadioButton28.isSelected()==true){
                param_edit="8.5";
            } 

            System.out.println("param edit: " + param_edit);

            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    Sequel.mengedit("pemeriksaan_gigi","noorder=?","no_rawat=?,kd_dokter=?,bagian=?,diagnosa=?,kd_diagnosa=?,hasil=?,catatan=?,tanggal=?,noorder=?,rahang=?",11,new String[]{
                            TNoRw.getText(),KdDokter.getText(),param_edit,Diagnosa.getText(),KdDiagnosa.getText(),HasilPemeriksaan.getSelectedItem().toString(),CatatanPemeriksaan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),
                            NoPermintaan.getText(),Rahang.getSelectedItem().toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                    });
                    if(tabMode.getRowCount()!=0){tampil();}
                    emptTeks();
                }else{
                    if(KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())){
                        Sequel.mengedit("pemeriksaan_gigi","noorder=?","no_rawat=?,kd_dokter=?,bagian=?,diagnosa=?,kd_diagnosa=?,hasil=?,catatan=?,tanggal=?,noorder=?,rahang=?",11,new String[]{
                                TNoRw.getText(),KdDokter.getText(),param_edit,Diagnosa.getText(),KdDiagnosa.getText(),HasilPemeriksaan.getSelectedItem().toString(),CatatanPemeriksaan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),
                                NoPermintaan.getText(),Rahang.getSelectedItem().toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                        });
                        if(tabMode.getRowCount()!=0){tampil();}
                        emptTeks();
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh dokter yang bersangkutan..!!");
                    }
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
            } 
        }
}                                       

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {                                   
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}        

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalItemStateChanged                          

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        dokter.dispose();
        dispose();
}                                         

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {                                     
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}                                    

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        // if(tabMode.getRowCount()==0){
        //     JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
        //     BtnBatal.requestFocus();
        // }else if(tabMode.getRowCount()!=0){
        //     Map<String, Object> param = new HashMap<>(); 
        //     param.put("namars",akses.getnamars());
        //     param.put("alamatrs",akses.getalamatrs());
        //     param.put("kotars",akses.getkabupatenrs());
        //     param.put("propinsirs",akses.getpropinsirs());
        //     param.put("kontakrs",akses.getkontakrs());
        //     param.put("emailrs",akses.getemailrs());   
        //     param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
        //     if(TCari.getText().trim().equals("")){
        //         Valid.MyReportqry("rptDataSkriningNutrisiDewasa.jasper","report","::[ Data Skrining Gizi ]::",
        //             "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,skrining_nutrisi_dewasa.tanggal,"+
        //             "skrining_nutrisi_dewasa.td,skrining_nutrisi_dewasa.hr,skrining_nutrisi_dewasa.rr,skrining_nutrisi_dewasa.suhu,"+
        //             "skrining_nutrisi_dewasa.bb,skrining_nutrisi_dewasa.tbpb,skrining_nutrisi_dewasa.spo2,skrining_nutrisi_dewasa.alergi,"+
        //             "skrining_nutrisi_dewasa.sg1,skrining_nutrisi_dewasa.nilai1,skrining_nutrisi_dewasa.sg2,skrining_nutrisi_dewasa.nilai2,"+
        //             "skrining_nutrisi_dewasa.total_hasil,skrining_nutrisi_dewasa.nip,petugas.nama,pasien.jk from skrining_nutrisi_dewasa "+
        //             "inner join reg_periksa on skrining_nutrisi_dewasa.no_rawat=reg_periksa.no_rawat "+
        //             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
        //             "inner join petugas on skrining_nutrisi_dewasa.nip=petugas.nip where "+
        //             "skrining_nutrisi_dewasa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' "+
        //             "order by skrining_nutrisi_dewasa.tanggal",param);
        //     }else{
        //         Valid.MyReportqry("rptDataSkriningNutrisiDewasa.jasper","report","::[ Data Skrining Gizi ]::",
        //             "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,skrining_nutrisi_dewasa.tanggal,"+
        //             "skrining_nutrisi_dewasa.td,skrining_nutrisi_dewasa.hr,skrining_nutrisi_dewasa.rr,skrining_nutrisi_dewasa.suhu,"+
        //             "skrining_nutrisi_dewasa.bb,skrining_nutrisi_dewasa.tbpb,skrining_nutrisi_dewasa.spo2,skrining_nutrisi_dewasa.alergi,"+
        //             "skrining_nutrisi_dewasa.sg1,skrining_nutrisi_dewasa.nilai1,skrining_nutrisi_dewasa.sg2,skrining_nutrisi_dewasa.nilai2,"+
        //             "skrining_nutrisi_dewasa.total_hasil,skrining_nutrisi_dewasa.nip,petugas.nama,pasien.jk from skrining_nutrisi_dewasa "+
        //             "inner join reg_periksa on skrining_nutrisi_dewasa.no_rawat=reg_periksa.no_rawat "+
        //             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
        //             "inner join petugas on skrining_nutrisi_dewasa.nip=petugas.nip where "+
        //             "skrining_nutrisi_dewasa.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59' "+
        //             "and (reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
        //             "pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or skrining_nutrisi_dewasa.alergi like '%"+TCari.getText().trim()+"%' or "+
        //             "skrining_nutrisi_dewasa.nip like '%"+TCari.getText().trim()+"%' or petugas.nama like '%"+TCari.getText().trim()+"%') "+
        //             "order by skrining_nutrisi_dewasa.tanggal ",param);
        //     }  
        // }
        // this.setCursor(Cursor.getDefaultCursor());
}                                        

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {                                    
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}                                   

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {                                 
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}                                

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {                                        
        tampil();
}                                       

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {                                   
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}                                  

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {                                       
        TCari.setText("");
        tampil();
}                                      

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}                                 

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {                                   
        // Valid.pindah(evt,TCari,Jam);
}                                  

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {                                 
        // Valid.pindah(evt, TNm, BtnSimpan);
}                                

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {                                    
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}                                   

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {                                  
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}                                 

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {                                         
        isForm();
    }                                        

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {                                     
        // if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        //     NmDokter.setText(petugas.tampil3(KdDokter.getText()));
        // }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        //     Detik.requestFocus();
        // }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        //     Diagnosa.requestFocus();
        // }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        //     btnDokterActionPerformed(null);
        // }
    }                                    

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {                                           
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }                                          

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {                                  
        // Valid.pindah(evt,SpO2,HasilPemeriksaan);
    }                                 

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {                                      
        // Valid.pindah(evt,Detik,BB);
    }                                     

    private void MnSkriningNutrisiActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // if(tbObat.getSelectedRow()>-1){
        //     Map<String, Object> param = new HashMap<>();
        //     param.put("namars",akses.getnamars());
        //     param.put("alamatrs",akses.getalamatrs());
        //     param.put("kotars",akses.getkabupatenrs());
        //     param.put("propinsirs",akses.getpropinsirs());
        //     param.put("kontakrs",akses.getkontakrs());
        //     param.put("emailrs",akses.getemailrs());   
        //     param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
        //     finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        //     param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),6).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),5).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
        //     Valid.MyReportqry("rptFormulirSkriningNutrisiDewasa.jasper","report","::[ Formulir Skrining Nutrisi Pasien Dewasa ]::",
        //             "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,skrining_nutrisi_dewasa.tanggal,"+
        //             "skrining_nutrisi_dewasa.td,skrining_nutrisi_dewasa.hr,skrining_nutrisi_dewasa.rr,skrining_nutrisi_dewasa.suhu,"+
        //             "skrining_nutrisi_dewasa.bb,skrining_nutrisi_dewasa.tbpb,skrining_nutrisi_dewasa.spo2,skrining_nutrisi_dewasa.alergi,"+
        //             "skrining_nutrisi_dewasa.sg1,skrining_nutrisi_dewasa.nilai1,skrining_nutrisi_dewasa.sg2,skrining_nutrisi_dewasa.nilai2,"+
        //             "skrining_nutrisi_dewasa.total_hasil,skrining_nutrisi_dewasa.nip,petugas.nama,pasien.jk from skrining_nutrisi_dewasa "+
        //             "inner join reg_periksa on skrining_nutrisi_dewasa.no_rawat=reg_periksa.no_rawat "+
        //             "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
        //             "inner join petugas on skrining_nutrisi_dewasa.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        // }
    }                                                 

    private void SG1ItemStateChanged(java.awt.event.ItemEvent evt) {                                     
        // Nilai1.setSelectedIndex(HasilPemeriksaan.getSelectedIndex());
        // TotalHasil.setText(""+(Integer.parseInt(Nilai1.getSelectedItem().toString())+Integer.parseInt(Nilai2.getSelectedItem().toString())));
    }                                    

    private void SG1KeyPressed(java.awt.event.KeyEvent evt) {                               
        // Valid.pindah(evt,Diagnosa,Nilai1);
    }                              

    private void HRKeyPressed(java.awt.event.KeyEvent evt) {                              
        // Valid.pindah(evt,TD,RR);
    }                             

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void jRadioButton7ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void jRadioButton8ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void jRadioButton9ActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void jRadioButton10ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton11ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton12ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton13ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton14ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton15ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton16ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton17ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton18ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton19ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton20ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton21ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton22ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton23ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton24ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton25ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton26ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton27ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton28ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton29ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton30ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton31ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton32ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton33ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton34ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton35ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton36ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton37ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton38ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton39ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton40ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton41ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton42ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton43ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton44ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton45ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton46ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton47ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton48ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton49ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton50ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jRadioButton51ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              
    
    private void jRadioButton52ActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {                                            
        diagnosa.emptTeks();
        diagnosa.isCek();
        diagnosa.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        diagnosa.setLocationRelativeTo(internalFrame1);
        diagnosa.setVisible(true);
    }                                           

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void SG3ItemStateChanged(java.awt.event.ItemEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

    private void SG3KeyPressed(java.awt.event.KeyEvent evt) {                               
        // TODO add your handling code here:
    }                              

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {                                        
        // Valid.pindah2(evt,Hubungan,RPS);
    }                                       

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMOdontogram dialog = new RMOdontogram(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    private widget.TextBox Diagnosa;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdDiagnosa;
    private widget.TextBox KdDokter;
    private widget.TextArea CatatanPemeriksaan;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnSkriningNutrisi;
    private widget.TextBox NmDokter;
    private widget.TextBox NoPermintaan;
    private usu.widget.glass.PanelGlass Panel18;
    private usu.widget.glass.PanelGlass Panel19;
    private usu.widget.glass.PanelGlass Panel20;
    private usu.widget.glass.PanelGlass Panel21;
    private usu.widget.glass.PanelGlass Panel22;
    private usu.widget.glass.PanelGlass Panel23;
    private usu.widget.glass.PanelGlass Panel24;
    private usu.widget.glass.PanelGlass Panel25;
    private usu.widget.glass.PanelGlass Panel26;
    private usu.widget.glass.PanelGlass Panel27;
    private usu.widget.glass.PanelGlass Panel28;
    private usu.widget.glass.PanelGlass Panel29;
    private usu.widget.glass.PanelGlass Panel30;
    private usu.widget.glass.PanelGlass Panel31;
    private usu.widget.glass.PanelGlass Panel32;
    private usu.widget.glass.PanelGlass Panel33;
    private usu.widget.glass.PanelGlass Panel34;
    private usu.widget.glass.PanelGlass Panel35;
    private usu.widget.glass.PanelGlass Panel36;
    private usu.widget.glass.PanelGlass Panel37;
    private usu.widget.glass.PanelGlass Panel38;
    private usu.widget.glass.PanelGlass Panel39;
    private usu.widget.glass.PanelGlass Panel40;
    private usu.widget.glass.PanelGlass Panel41;
    private usu.widget.glass.PanelGlass Panel42;
    private usu.widget.glass.PanelGlass Panel43;
    private usu.widget.glass.PanelGlass Panel44;
    private usu.widget.glass.PanelGlass Panel45;
    private usu.widget.glass.PanelGlass Panel46;
    private usu.widget.glass.PanelGlass Panel47;
    private usu.widget.glass.PanelGlass Panel48;
    private usu.widget.glass.PanelGlass Panel49;
    private usu.widget.glass.PanelGlass Panel50;
    private usu.widget.glass.PanelGlass Panel51;
    private usu.widget.glass.PanelGlass Panel52;
    private usu.widget.glass.PanelGlass Panel53;
    private usu.widget.glass.PanelGlass Panel54;
    private usu.widget.glass.PanelGlass Panel55;
    private usu.widget.glass.PanelGlass Panel56;
    private usu.widget.glass.PanelGlass Panel57;
    private usu.widget.glass.PanelGlass Panel58;
    private usu.widget.glass.PanelGlass Panel59;
    private usu.widget.glass.PanelGlass Panel60;
    private usu.widget.glass.PanelGlass Panel61;
    private usu.widget.glass.PanelGlass Panel62;
    private usu.widget.glass.PanelGlass Panel63;
    private usu.widget.glass.PanelGlass Panel64;
    private usu.widget.glass.PanelGlass Panel65;
    private usu.widget.glass.PanelGlass Panel66;
    private usu.widget.glass.PanelGlass Panel67;
    private usu.widget.glass.PanelGlass Panel68;
    private usu.widget.glass.PanelGlass Panel69;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox HasilPemeriksaan;
    private widget.ComboBox Rahang;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.Button btnDokter;
    private widget.Button btnDiagnosa;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel24;
    private widget.Label jLabel32;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton13;
    private javax.swing.JRadioButton jRadioButton14;
    private javax.swing.JRadioButton jRadioButton15;
    private javax.swing.JRadioButton jRadioButton16;
    private javax.swing.JRadioButton jRadioButton17;
    private javax.swing.JRadioButton jRadioButton18;
    private javax.swing.JRadioButton jRadioButton19;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton20;
    private javax.swing.JRadioButton jRadioButton21;
    private javax.swing.JRadioButton jRadioButton22;
    private javax.swing.JRadioButton jRadioButton23;
    private javax.swing.JRadioButton jRadioButton24;
    private javax.swing.JRadioButton jRadioButton25;
    private javax.swing.JRadioButton jRadioButton26;
    private javax.swing.JRadioButton jRadioButton27;
    private javax.swing.JRadioButton jRadioButton28;
    private javax.swing.JRadioButton jRadioButton29;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton30;
    private javax.swing.JRadioButton jRadioButton31;
    private javax.swing.JRadioButton jRadioButton32;
    private javax.swing.JRadioButton jRadioButton33;
    private javax.swing.JRadioButton jRadioButton34;
    private javax.swing.JRadioButton jRadioButton35;
    private javax.swing.JRadioButton jRadioButton36;
    private javax.swing.JRadioButton jRadioButton37;
    private javax.swing.JRadioButton jRadioButton38;
    private javax.swing.JRadioButton jRadioButton39;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton40;
    private javax.swing.JRadioButton jRadioButton41;
    private javax.swing.JRadioButton jRadioButton42;
    private javax.swing.JRadioButton jRadioButton43;
    private javax.swing.JRadioButton jRadioButton44;
    private javax.swing.JRadioButton jRadioButton45;
    private javax.swing.JRadioButton jRadioButton46;
    private javax.swing.JRadioButton jRadioButton47;
    private javax.swing.JRadioButton jRadioButton48;
    private javax.swing.JRadioButton jRadioButton49;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton50;
    private javax.swing.JRadioButton jRadioButton51;
    private javax.swing.JRadioButton jRadioButton52;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbObat;
    // End of variables declaration                   
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select pemeriksaan_gigi.noorder,pemeriksaan_gigi.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pemeriksaan_gigi.tanggal,pemeriksaan_gigi.kd_dokter,"+
                    "dokter.nm_dokter,pemeriksaan_gigi.bagian,pemeriksaan_gigi.diagnosa,pemeriksaan_gigi.kd_diagnosa,"+
                    "pemeriksaan_gigi.hasil,pemeriksaan_gigi.rahang,pemeriksaan_gigi.catatan from pemeriksaan_gigi "+
                    "inner join reg_periksa on pemeriksaan_gigi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on pemeriksaan_gigi.kd_dokter=dokter.kd_dokter where "+
                    "pemeriksaan_gigi.tanggal between ? and ? order by pemeriksaan_gigi.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select pemeriksaan_gigi.noorder,pemeriksaan_gigi.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pemeriksaan_gigi.tanggal,pemeriksaan_gigi.kd_dokter,"+
                    "dokter.nm_dokter,pemeriksaan_gigi.bagian,pemeriksaan_gigi.diagnosa,pemeriksaan_gigi.kd_diagnosa,"+
                    "pemeriksaan_gigi.hasil,pemeriksaan_gigi.rahang,pemeriksaan_gigi.catatan from pemeriksaan_gigi "+
                    "inner join reg_periksa on pemeriksaan_gigi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter on pemeriksaan_gigi.kd_dokter=dokter.kd_dokter where "+
                    "pemeriksaan_gigi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or pemeriksaan_gigi.hasil like ? or pemeriksaan_gigi.catatan like ? or dokter.kd_dokter like ?) "+
                    "order by pemeriksaan_gigi.tanggal ");
            }
            
            try {
                if(TCari.getText().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("noorder"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tanggal"),rs.getString("kd_dokter"),
                        rs.getString("nm_dokter"),rs.getString("bagian"),rs.getString("diagnosa"),rs.getString("kd_diagnosa"),rs.getString("hasil"),rs.getString("rahang"),rs.getString("catatan")
                    });
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }
    
    public void emptTeks() {
        NoPermintaan.setText("");
        Diagnosa.setText("");
        KdDiagnosa.setText("");
        HasilPemeriksaan.setSelectedIndex(0);
        Rahang.setSelectedIndex(0);
        CatatanPemeriksaan.setText("");
        Tanggal.setDate(new Date());
        autoNomor();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoPermintaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KdDiagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            HasilPemeriksaan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Rahang.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            CatatanPemeriksaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            
            String bagian = tbObat.getValueAt(tbObat.getSelectedRow(),7).toString();
            System.out.println(bagian);

            if (bagian.equals("1.1")){
                jRadioButton9.setSelected(true);
            }
            if (bagian.equals("1.2")){
                jRadioButton8.setSelected(true);
            }
            if (bagian.equals("1.3")){
                jRadioButton7.setSelected(true);
            }
            if (bagian.equals("1.4")){
                jRadioButton6.setSelected(true);
            }
            if (bagian.equals("1.5")){
                jRadioButton5.setSelected(true);
            }
            if (bagian.equals("1.6")){
                jRadioButton4.setSelected(true);
            }
            if (bagian.equals("1.7")){
                jRadioButton3.setSelected(true);
            }
            if (bagian.equals("1.8")){
                jRadioButton2.setSelected(true);
            }
            if (bagian.equals("2.1")){
                jRadioButton10.setSelected(true);
            }
            if (bagian.equals("2.2")){
                jRadioButton11.setSelected(true);
            }
            if (bagian.equals("2.3")){
                jRadioButton12.setSelected(true);
            }
            if (bagian.equals("2.4")){
                jRadioButton13.setSelected(true);
            }
            if (bagian.equals("2.5")){
                jRadioButton14.setSelected(true);
            }
            if (bagian.equals("2.6")){
                jRadioButton15.setSelected(true);
            }
            if (bagian.equals("2.7")){
                jRadioButton16.setSelected(true);
            }
            if (bagian.equals("2.8")){
                jRadioButton17.setSelected(true);
            }
            if (bagian.equals("3.1")){
                jRadioButton45.setSelected(true);
            }
            if (bagian.equals("3.2")){
                jRadioButton44.setSelected(true);
            }
            if (bagian.equals("3.3")){
                jRadioButton43.setSelected(true);
            }
            if (bagian.equals("3.4")){
                jRadioButton42.setSelected(true);
            }
            if (bagian.equals("3.5")){
                jRadioButton41.setSelected(true);
            }
            if (bagian.equals("3.6")){
                jRadioButton40.setSelected(true);
            }
            if (bagian.equals("3.7")){
                jRadioButton39.setSelected(true);
            }
            if (bagian.equals("3.8")){
                jRadioButton38.setSelected(true);
            }
            if (bagian.equals("4.1")){
                jRadioButton46.setSelected(true);
            }
            if (bagian.equals("4.2")){
                jRadioButton47.setSelected(true);
            }
            if (bagian.equals("4.3")){
                jRadioButton48.setSelected(true);
            }
            if (bagian.equals("4.4")){
                jRadioButton49.setSelected(true);
            }
            if (bagian.equals("4.5")){
                jRadioButton50.setSelected(true);
            }
            if (bagian.equals("4.6")){
                jRadioButton51.setSelected(true);
            }
            if (bagian.equals("4.7")){
                jRadioButton52.setSelected(true);
            }
            if (bagian.equals("4.8")){
                jRadioButton1.setSelected(true);
            }
            if (bagian.equals("5.1")){
                jRadioButton22.setSelected(true);
            }
            if (bagian.equals("5.2")){
                jRadioButton21.setSelected(true);
            }
            if (bagian.equals("5.3")){
                jRadioButton20.setSelected(true);
            }
            if (bagian.equals("5.4")){
                jRadioButton19.setSelected(true);
            }
            if (bagian.equals("5.5")){
                jRadioButton18.setSelected(true);
            }
            if (bagian.equals("6.1")){
                jRadioButton23.setSelected(true);
            }
            if (bagian.equals("6.2")){
                jRadioButton24.setSelected(true);
            }
            if (bagian.equals("6.3")){
                jRadioButton25.setSelected(true);
            }
            if (bagian.equals("6.4")){
                jRadioButton26.setSelected(true);
            }
            if (bagian.equals("6.5")){
                jRadioButton27.setSelected(true);
            }
            if (bagian.equals("7.1")){
                jRadioButton33.setSelected(true);
            }
            if (bagian.equals("7.2")){
                jRadioButton34.setSelected(true);
            }
            if (bagian.equals("7.3")){
                jRadioButton35.setSelected(true);
            }
            if (bagian.equals("7.4")){
                jRadioButton36.setSelected(true);
            }
            if (bagian.equals("7.5")){
                jRadioButton37.setSelected(true);
            }
            if (bagian.equals("8.1")){
                jRadioButton32.setSelected(true);
            }
            if (bagian.equals("8.2")){
                jRadioButton31.setSelected(true);
            }
            if (bagian.equals("8.3")){
                jRadioButton30.setSelected(true);
            }
            if (bagian.equals("8.4")){
                jRadioButton29.setSelected(true);
            }
            if (bagian.equals("8.5")){
                jRadioButton28.setSelected(true);
            }
        }
    }
    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,400));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getskrining_nutrisi_dewasa());
        BtnHapus.setEnabled(akses.getskrining_nutrisi_dewasa());
        BtnEdit.setEnabled(akses.getskrining_nutrisi_dewasa());
        BtnPrint.setEnabled(akses.getskrining_nutrisi_dewasa()); 
        if(akses.getjml2()>=1){
            KdDokter.setEditable(false);
            btnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if(NmDokter.getText().equals("")){
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from pemeriksaan_gigi where noorder=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tampil();
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(pemeriksaan_gigi.noorder,5),signed)),0) from pemeriksaan_gigi where pemeriksaan_gigi.tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ","OD"+Valid.SetTgl(Tanggal.getSelectedItem()+"").replaceAll("-",""),5,NoPermintaan);           
    }
    
}
