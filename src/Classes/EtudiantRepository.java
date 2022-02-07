package Classes;
import Interfaces.IJournal;
import Interfaces.InterfaceEtudiant;
import Interfaces.InterfaceEtudiantRepository;
import Interfaces.InterfaceEtudiantService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EtudiantRepository implements InterfaceEtudiantRepository {
	private Connection Connect;
	private IJournal AffichageListes;
	private Statement stmt;
	public EtudiantRepository(IJournal AffichageListes) throws SQLException {
		try {
			this.Connect = DBConnection.getConn();
			this.stmt = this.Connect.createStatement(); // TO DO ..
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.AffichageListes = AffichageListes;
	}
	@Override
	public void add(InterfaceEtudiant E) throws SQLException{
		String sql = "INSERT into etudiant values (" + E.getMatricule() + ",'" + E.getNom() + "','" + E.getPrenom() + "','" + E.getEmail() + "'," +E.getPwd() +"'," +E.getNbLivreMensuel_Autorise() + "," +E.getNbLivreEmprunte() + "," +E.getId_universite()+")";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()){
			AffichageDate.setClassName("EtudiantRepository");
			AffichageListes.outPut_Msg("log : ajout dans la BD réussi de l'étudiant  du Matricule" + E.getMatricule());
			}else if (!rs.next()){
			AffichageListes.outPut_Msg("log : Echec de l'ajout dans la BD de l'étudiant  du Matricule" + E.getMatricule());
			}
		this.Connect.close();
	}

	@Override
	public boolean Exists(String email) throws SQLException{
		Statement stmt = this.Connect.createStatement(); // TO DO ..
		String sql = "select * from etudiant where email='"+ email+"'";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()){
			System.out.println("logBD--- :email existe dans la BD  " + email);
			this.Connect.close();
			return true;
			}
		System.out.println("logBD--- : email n'existe pas " + email);	
		this.Connect.close();
		return false;
	}
	@Override
	public boolean Exists(int mat) throws SQLException{
		Statement stmt = this.Connect.createStatement(); // TO DO ..
		String sql = "select * from etudiant where matricule="+ mat;
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()){
			System.out.println("logBD--- :etudiant avec ce matricule existe d�ja dans la BD  " + mat);
			this.Connect.close();
			return true;
			}
		System.out.println("logBD----: etudiant avec ce matricule n'existe pas " + mat);	
		this.Connect.close();
		return false;
	}

	public static class ViewInscription extends InterfaceEtudiantService.AbstractViewInscription {
		private JFrame frame;
		private JPanel Container;
		private JTextField MatriculeText;
		private JTextField LastNameText;
		private JTextField FirstNameText;
		private JTextField EmailText;
		private JTextField PasswordText;
		private JTextField UniversiteText;
		private JButton ButtonSubmite;
		private JButton ButtonCancel;

		public String getMatriculeText(){
			return this.MatriculeText.getText();
		}
		public String getLastNameText(){
			return this.LastNameText.getText();
		}
		public String getFirstNameText(){
			return this.FirstNameText.getText();
		}
		public String getEmailTex(){
			return this.EmailText.getText();
		}
		public String getPasswordText(){
			return this.PasswordText.getText();
		}
		public String getUniversiteText(){
			return this.UniversiteText.getText();
		}

		@Override
		public void Inscription(){
		   (this.frame = new JFrame()).setBounds(300,300,450,300);
		   this.frame.getContentPane().setLayout(null);
		   this.frame.setDefaultCloseOperation(3);
		   (this.Container = new JPanel()).setBorder(new EmptyBorder(7,7,7,7));
		   this.frame.setContentPane(this.Container);
		   this.Container.setLayout(null);


		   JLabel Matricule = new JLabel("Matricule");
		   Matricule.setBounds(30, 30, 200, 17);
		   this.Container.add(Matricule);
		   (this.MatriculeText = new JTextField()).setBounds(184, 27, 200, 20);
		   this.MatriculeText.setColumns(10);
		   this.Container.add(MatriculeText);


		   JLabel LastName = new JLabel("Last name");
		   LastName.setBounds(30, 60, 200, 14);
		   this.Container.add(LastName);
		   (this.LastNameText = new JTextField()).setBounds(184, 54, 200, 20);
		   this.LastNameText.setColumns(10);
		   this.Container.add(LastNameText);


		   JLabel FirstName = new JLabel("First Name");
		   FirstName.setBounds(30, 91, 200, 14);
		   this.Container.add(FirstName);
		   (this.FirstNameText = new JTextField()).setBounds(184, 85, 200, 20);
		   this.FirstNameText.setColumns(10);
		   this.Container.add(this.FirstNameText);


			JLabel Email = new JLabel("Email");
			Email.setBounds(30, 126, 200, 14);
			this.Container.add(Email);
			(this.EmailText = new JTextField()).setColumns(10);
			this.EmailText.setBounds(184, 120, 200, 20);
			this.Container.add(this.EmailText);


			JLabel Password = new JLabel("Password");
			Password.setBounds(30, 165, 200, 14);
			this.Container.add(Password);
			(this.PasswordText = new JPasswordField()).setColumns(10);
			this.PasswordText.setBounds(184, 159, 200, 20);
			this.Container.add(this.PasswordText);

			JLabel Id_Universite = new JLabel("ID of universite");
			Id_Universite.setBounds(30, 200, 200, 14);
			this.Container.add(Id_Universite);
			(this.UniversiteText = new JTextField()).setColumns(10);
			this.UniversiteText.setBounds(184, 194, 200, 20);
			this.Container.add(this.UniversiteText);

			(this.ButtonSubmite = new JButton("Submit")).setBounds(295, 227, 89, 23);
			this.Container.add(this.ButtonSubmite);
			(this.ButtonCancel = new JButton("Cancel")).setBounds(184, 227, 89, 23);
			this.Container.add(this.ButtonCancel);
		}
		@Override
		public void InitialiserInscription(){
			this.MatriculeText.setText("");
			this.LastNameText.setText("");
			this.FirstNameText.setText("");
			this.EmailText.setText("");
			this.PasswordText.setText("");
			this.UniversiteText.setText("");
		}

		public ViewInscription(InterfaceEtudiantRepository ER){
			// this.InitialiserInscription();
			this.Inscription();
			this.frame.setVisible(true);
			this.ButtonSubmite.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					InterfaceEtudiantService.ControleurInscription C = new InterfaceEtudiantService.ControleurInscription(ViewInscription.this);
					Boolean Control = C.getStatus();
					if(Control){
						System.out.println("Succes ! ");
						InterfaceEtudiant E = new Etudiant(Integer.parseInt(MatriculeText.getText()), LastNameText.getText(), FirstNameText.getText(),EmailText.getText(), PasswordText.getText(), Integer.parseInt(UniversiteText.getText()));
						try {
							ER.add(E);
						} catch (SQLException ex) {
							ex.printStackTrace();
						}
					}
				}

			});

		}
	}
}