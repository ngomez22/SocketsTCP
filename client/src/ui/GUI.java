package ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

import javax.swing.*;

import src.Client;

public class GUI extends JFrame {

	private Client client;
	private Connection connection;
	private Files files;
	private Controls controls;

	public GUI() {
		connection = new Connection(this);
		files = new Files(this);
		files.setVisible(false);
		controls = new Controls(this);
		controls.setVisible(false);

		setSize(530, 530);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("TCP Client");
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		add(connection, BorderLayout.NORTH);
		add(files, BorderLayout.CENTER);
		add(controls, BorderLayout.SOUTH);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (client != null) {
					int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to end the connection?", "TCP Client", JOptionPane.YES_NO_OPTION);
					if (confirmed == JOptionPane.YES_OPTION) {
						client.endConnection();
						dispose();
					}
				} else {
					dispose();
				}
			}
		});
	}

	public void showPanels() {
		files.setVisible(true);
		controls.setVisible(true);
	}

	public void hidePanels() {
		files.setVisible(false);
		controls.setVisible(false);
	}

	public void connect() {
		client = new Client();
		String status = client.startConnection();
		System.out.println(status);
		connection.changeStatus(status);
		if (status.startsWith("SUCCESS")) {
			getDownloads();
			getDownloadables();
			showPanels();
			connection.disconnectButton();
		}
	}

	public void download(String fname) {
		try {
			client.downloadFile(fname);
			files.updateDownloads(client.getDownloads());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error downloading file", "Download", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void disconnect() {
		String status = client.endConnection();
		client = null;
		System.out.println(status);
		connection.changeStatus(status);
		connection.connectButton();
		hidePanels();
	}

	public void getDownloadables() {
		String[] downloadables = client.getFiles();
		files.updateDownloadables(downloadables);
	}

	public void getDownloads() {
		String[] downloads = client.getDownloads();
		files.updateDownloads(downloads);
	}

	public static void main(String[] args) {
		GUI i = new GUI();
		i.setVisible(true);
	}
}
