package ui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import src.Client;

public class GUI extends JFrame {

	private Desktop desktop;
	private Client client;
	private Connection connection;
	private Files files;

	public GUI() {
		desktop = Desktop.getDesktop();
		connection = new Connection(this);
		files = new Files(this);
		files.setVisible(false);

		setSize(530, 530);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("TCP Client");
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		add(connection, BorderLayout.NORTH);
		add(files, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (client != null) {
					int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to end the connection?",
							"TCP Client", JOptionPane.YES_NO_OPTION);
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
	}

	public void hidePanels() {
		files.setVisible(false);
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

	public void download() {
		String fname = files.getSelectedForDownload();
		if (fname == null) {
			JOptionPane.showMessageDialog(this, "Please select a file to download", "Download", JOptionPane.INFORMATION_MESSAGE);
		} else {
			try {
				boolean alive = client.download(fname);
				if(!alive) {
					client = null;
					connection.changeStatus("TIMEOUT");
					connection.connectButton();
					hidePanels();
					JOptionPane.showMessageDialog(this, "You are no longer connected to the server. Restart your connection to continue", "Download", JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this, "Error downloading file", "Download", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void stopDownload() {
		boolean stopped = client.cancelDownload();
		if(!stopped) {
			JOptionPane.showMessageDialog(this, "No active download", "Download", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Download stopped. Restarting your connection..", "Download", JOptionPane.INFORMATION_MESSAGE);
			connect();
		}
	}
	
	public void open() {
		String fname = files.getSelectedToOpen();
		if (fname == null) {
			JOptionPane.showMessageDialog(this, "Please select a file to open", "Downloads", JOptionPane.INFORMATION_MESSAGE);
		} else {
			try {
				desktop.open(new File("./downloads/" + fname));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Couldn't open the selected file", "Downloads", JOptionPane.ERROR_MESSAGE);
			}
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
