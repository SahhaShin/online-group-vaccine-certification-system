package chat;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SigninGUI extends JFrame{

	private JFrame frame;
	private JTextField newID;
	private JTextField newPW1;
	private JTextField newPW2;
	PrintWriter pw;
	BufferedReader br;
//	private static final String SERVER_IP = "127.0.0.1";
//	private static final int SERVER_PORT = 5000;
//	Socket socket = new Socket();
	
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the application.
	 */
	public SigninGUI(Socket socket) {
		
		try {
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		setTitle("SignUp");
		String getMemberInfo = null;
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBounds(100, 100, 400, 400);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//Labels
		JLabel lblSignInTitle = new JLabel("Sign up");
		lblSignInTitle.setBounds(168, 60, 52, 15);
		frame.getContentPane().add(lblSignInTitle);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setBounds(109, 137, 22, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setBounds(65, 178, 66, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		
		// ID ?????????
		newID = new JTextField();
		newID.setBounds(143, 134, 106, 21);
		frame.getContentPane().add(newID);
		newID.setColumns(10);
		//?????? 1??? ??????
		newPW1 = new JTextField();
		newPW1.setBounds(143, 175, 106, 21);
		frame.getContentPane().add(newPW1);
		newPW1.setColumns(10);
		//????????? ?????? ??????
		JButton btnIdCheck = new JButton("Check");
		btnIdCheck.setBounds(261, 133, 95, 23);
		frame.getContentPane().add(btnIdCheck);
		//?????? 2??? ??????
		JLabel lblNewLabel_2_1 = new JLabel("Password2");
		lblNewLabel_2_1.setBounds(65, 227, 80, 15);
		frame.getContentPane().add(lblNewLabel_2_1);
		//?????? 2??? ??????
		newPW2 = new JTextField();
		newPW2.setColumns(10);
		newPW2.setBounds(143, 224, 106, 21);
		frame.getContentPane().add(newPW2);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.setBounds(143, 298, 95, 23);
		frame.getContentPane().add(btnSignIn);
		
		
		frame.setVisible(true);
		
		//ID  ?????? ?????? ?????? ????????? ????????? ?????? ????????? ?????? ?????? ?????? ????????? ???????????????
		btnIdCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					
					//????????? ?????? 
					String newId = newID.getText();
					//String getMemberInfo = newId;
					
					try {
						//socket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
						//?????? ?????? client_list.txt ??????????????????
						// ????????????????????? ????????? ?????? ??????
						//PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
						pw.println(Protocol.ID_CHECK+newId);
						pw.flush();
						//server?????? ???????????? ?????????????????? 
						//
						//BufferedReader Id_OK = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
						//pw.println(getMemberInfo);
						
						// ????????? ?????? ????????? ?????? ??????  ????????? ?????????
						while(true) {
							//???????????? ?????? ????????? ???????????? ???????????? ???????????? (ID_CHECK_O ???????????? ??????)
							//: ???????????? ????????? ????????? Available??? ????????? ?????? ??????????????? ??????
							String request = br.readLine();
							String[] tokens = request.split(":");
							if("Available".equals(tokens[0])) {
								consoleLog(tokens[1]);
								//????????? ???????????????
								JOptionPane.showMessageDialog(null, tokens[1]);
								
								break;
							}
							else {
								JOptionPane.showMessageDialog(null, tokens[1]);
								
								break;
							}
						}
					}catch(IOException e1) {
						e1.printStackTrace();
						consoleLog("fail: Communication socket connection failed.");
					}
			}
			
		});
		
		
		// ???????????? ?????? ????????? ????????? ????????? ???????????? ???????????????
		btnSignIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				String newId = newID.getText();
				String newpw1 = newPW1.getText();
				String newpw2 = newPW2.getText();
				
				
				String newMemberInfo = Protocol.SIGN_CHECK+newId+":"+newpw1;
				
				// ???????????? ?????? ?????? ????????????
				if(!newpw2.equals(newpw1)) {
					consoleLog("Password unmatched");
					
				}
				
				else {
					try {
					
						//PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
						//BufferedReader newAccount = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
						pw.println(newMemberInfo);
						pw.flush();
					
						while(true) {
						
							String request = br.readLine();
							
							String[] tokens = request.split(":");
							
							if("added".equals(tokens[0])) {
								JOptionPane.showMessageDialog(null, "Membership registration is complete!");
								consoleLog(tokens[1]);
								frame.setVisible(false);
								break;
								
							}
							else if ("exist".equals(tokens[0])) {
								JOptionPane.showMessageDialog(null, "Please check the membership information again.");
								consoleLog(tokens[1]);
								break;
							}
							else {
								System.out.println(request);
							}
						}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				
			}
			
		});
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private static void consoleLog(String log) {
        System.out.println("[System] : : "+log);
    }
	
	public static void main(String[] args) {
		//LoginGUI window = new LoginGUI(socket);
		
	}
}