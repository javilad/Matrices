package matrices.Class;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Component;
import javax.swing.JSeparator;

public class CalculateMatrix {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CalculateMatrix window = new CalculateMatrix();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CalculateMatrix() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 613);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		JLabel lblCalcularMatrizInversa = new JLabel("Calcular Matriz Inversa");
		lblCalcularMatrizInversa.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(lblCalcularMatrizInversa);

		JPanel panelContent = new JPanel();
		final JPanel panelMatrix = new JPanel();
		final JPanel panelResult = new JPanel();
		final ArrayList<JTextField> listFields = new ArrayList<JTextField>();
		panelMatrix.setBorder(null);
		frame.getContentPane().add(panelContent, BorderLayout.CENTER);

		JLabel lblPorFavorIntroduzca = new JLabel("Por favor introduzca el tama\u00F1o de la matriz:");
		lblPorFavorIntroduzca.setFont(new Font("Tahoma", Font.PLAIN, 12));

		textField = new JTextField();
		textField.setColumns(10);

		JButton btnGenerar = new JButton("Crear matriz");
		btnGenerar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				double sizeInput = Double.parseDouble(textField.getText());

				if (panelMatrix.getComponentCount() > 0) {
					panelMatrix.removeAll();
					panelMatrix.repaint();
				}

				if (sizeInput > 0) {
					double size = Math.pow(sizeInput, 2);
					int coordY = 0;
					int acumCoordX = 0;

					for (int x = 0; x < size; x++) {
						
						if (x % sizeInput == 0) {
							coordY += 24;
							acumCoordX = 0;
						}

						int coordX = 42 * acumCoordX;
						JTextField input = new JTextField();
						input.setEditable(true);
						input.setVisible(true);
						input.setText(" ");
						input.setBounds(coordX, coordY, 40, 24);
						panelMatrix.add(input, BorderLayout.CENTER);
						listFields.add(input);
						acumCoordX++;
					}
				}
			}
		});

		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Component[] allTextfields = panelMatrix.getComponents();
				int sizeInput = Integer.parseInt(textField.getText());
				int indexRow = 0;
				int indexColumn = 0;
				Matrix.setMatrixObject(sizeInput, sizeInput);

				for (Component c : allTextfields) {
					if (c instanceof JTextField) {
						JTextField textField = (JTextField) c;

						if (textField != null && indexRow < sizeInput) {
							String value = textField.getText().trim();
							Double valueDouble = value == "" ? 0 : Double.parseDouble(value);

							if (Matrix.MatrixObject != null) {
								Matrix.MatrixObject[indexRow][indexColumn] = valueDouble;
							}

							indexColumn++;

							if (indexColumn % sizeInput == 0) {
								indexRow++;
								indexColumn = 0;
							}
						}
					}
				}

				try {
					double[][] matrixReverse = Matrix.getReverseMatrix();
					
					if (matrixReverse == null) {
						JOptionPane.showMessageDialog(null, "El determinante de la matriz es cero (0)");
					} else {

						if (panelResult.getComponentCount() > 0) {
							panelResult.removeAll();
							panelResult.repaint();
						}

						if (sizeInput > 0) {
							double size = Math.pow(sizeInput, 2);
							int coordY = 0;
							int acumCoordX = 0;
							int indexRowMatrix = 0;
							int indexColumnMatrix = 0;

							for (int x = 0; x < size; x++) {
								Double valueMatrix = (double) Math.round(matrixReverse[indexRowMatrix][indexColumnMatrix] * 100) / 100;

								indexColumnMatrix++;

								if (x % sizeInput == 0) {
									coordY += 24;
									acumCoordX = 0;
								}

								if (indexColumnMatrix % sizeInput == 0) {
									indexRowMatrix++;
									indexColumnMatrix = 0;
								}

								int coordX = 42 * acumCoordX;
								JTextField input = new JTextField();
								input.setEditable(false);
								input.setVisible(true);
								input.setText(valueMatrix.toString());
								input.setBounds(coordX, coordY, 40, 24);
								panelResult.add(input, BorderLayout.CENTER);
								listFields.add(input);
								acumCoordX++;

							}
						}
					}
				} catch (NullPointerException | IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Ocurrió un error calculando la matriz");
				}
			}
		});
		
		JLabel lblElResultadoDe = new JLabel("La matriz inversa es:");
		lblElResultadoDe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JSeparator separatorBottom = new JSeparator();
		
		JSeparator separatorTop = new JSeparator();
		
		JSeparator separatorTopResult = new JSeparator();
		
		JSeparator separator = new JSeparator();

		GroupLayout gl_panelContent = new GroupLayout(panelContent);
		gl_panelContent.setHorizontalGroup(
			gl_panelContent.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelContent.createSequentialGroup()
					.addContainerGap()
					.addComponent(separatorTopResult, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
				.addGroup(gl_panelContent.createSequentialGroup()
					.addGroup(gl_panelContent.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panelContent.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelResult, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panelContent.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelMatrix, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panelContent.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblPorFavorIntroduzca)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panelContent.createSequentialGroup()
							.addContainerGap()
							.addComponent(separatorBottom, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panelContent.createSequentialGroup()
							.addContainerGap()
							.addComponent(separatorTop, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)))
					.addGap(23))
				.addGroup(Alignment.LEADING, gl_panelContent.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblElResultadoDe)
					.addContainerGap(348, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_panelContent.createSequentialGroup()
					.addContainerGap()
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_panelContent.createSequentialGroup()
					.addGap(175)
					.addComponent(btnCalcular, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(192, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_panelContent.createSequentialGroup()
					.addGap(165)
					.addComponent(btnGenerar, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(165, Short.MAX_VALUE))
		);
		gl_panelContent.setVerticalGroup(
			gl_panelContent.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelContent.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelContent.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPorFavorIntroduzca)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separatorTop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(13)
					.addComponent(btnGenerar)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separatorBottom, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelMatrix, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separatorTopResult, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
					.addComponent(btnCalcular)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblElResultadoDe)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelResult, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
					.addGap(13))
		);
		
		panelContent.setLayout(gl_panelContent);
	}
}
