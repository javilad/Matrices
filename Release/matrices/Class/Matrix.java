/**
 * 
 */
package matrices.Class;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Javier Orlando Ladino
 *
 */
public class Matrix {
	/**
	 * Matrix of user
	 */
	public static double[][] MatrixObject;

	/**
	 * Class than obtains the data
	 */
	public static BufferedReader inputUser = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Method set of MatrixObject
	 * @param rows
	 * @param columns
	 */
	public static void setMatrixObject(int rows, int columns) {
		Matrix.MatrixObject = new double[rows][columns];
	}

	/**
	 * Method get of MatrixObject 
	 * @return
	 */
	public static double[][] getMatrixObject() {
		return Matrix.MatrixObject;
	}

	/**
	 * Class constructor
	 */
	public Matrix() {
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		int optionUser = 0;

		do {
			printMessage("");
			printMessage("================================================");
			printMessage("");
			printMessage("Seleccione alguna de las opciones disponibles:");
			printMessage("1.- Introducir la Matriz ");
			printMessage("2.- Calcular inversa de la matriz ");
			printMessage("0.- SALIR ");
			printMessage("");
			printMessage("================================================");
			printMessage("");

			optionUser = Integer.parseInt(inputUser.readLine());

			switch (optionUser) {
			case 1: {
				setDataMatrix();
			}
				break;
			case 2: {
				// Calculate reverse matrix
				double[][] matrixReverse = getReverseMatrix();
				
				// Print reverse matrix
				printMessage("La inversa de la matriz:");
				printMessage("[");
				printMatrix(Matrix.MatrixObject, 0, 0);
				printMessage("]");
				printMessage("es la siguiente:");
				printMessage("[");
				printMatrix(matrixReverse, 0, 0);
				printMessage("]");
			}
				break;
			}
		} while (optionUser != 0);
	}

	/**
	 * Prints messages of user
	 * @param message
	 */
	private static void printMessage(String message) {
		System.out.println(message);
	}
	
	/**
	 * Prints matrix recursively
	 * @param matrix
	 * @param numberRows
	 * @param numberColumns
	 */
	private static void printMatrix(double[][] matrix, int numberRows, int numberColumns)
	{
		// Array length
		int lengthArray = matrix.length;
		if (numberRows < lengthArray) {
			
			// Prints a item
			System.out.print(matrix[numberRows][numberColumns] + "    ");
			
			if (numberColumns < lengthArray - 1) {
				numberColumns++;
			} else {
				numberRows++;
				// Prints a "enter"
				printMessage("");
				numberColumns = 0;
			}

			// Call recursive
			printMatrix(matrix, numberRows, numberColumns);
		}
	}

	/**
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	private static void setDataMatrix() throws NumberFormatException, IOException {
		printMessage("Digite el tamaño de la matrix: ");
		int size = Integer.parseInt(inputUser.readLine());
		
		// Should be a square matrix
		if (size > 0 && size < 99) {
			// Constructs the variable "MatrixObject"
			ConstructMatrix(size, size);
		} else {
			printMessage("No es un tamaño válido");
		}
	}

	/**
	 * Fills the matrix with user data
	 * @param rows
	 * @param columns
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	private static void ConstructMatrix(int rows, int columns) throws NumberFormatException, IOException {
		setMatrixObject(rows, columns);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				printMessage("Digite el valor de la matriz en la posición:  [" + (i + 1) + " , " + (j + 1) + " ]");
				Matrix.MatrixObject[i][j] = Double.parseDouble(inputUser.readLine());
			}
		}
	}

	/**
	 * Gets the reverse matrix
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public static double[][] getReverseMatrix() throws NullPointerException, IOException {
		if (Matrix.MatrixObject != null) {
			if (getDeterminant(Matrix.MatrixObject) != 0) {
				// Determinant of the matrix
				double matrixDeterminant = 1 / getDeterminant(Matrix.MatrixObject);

				// Getting Adjoin matrix
				double[][] matrixAdjoin = getAdjointMatrix(Matrix.MatrixObject);

				// Getting product between matrix and determinant
				double[][] matrixProduct = multiplyMatrixByScalar(matrixDeterminant, matrixAdjoin,
						matrixAdjoin.length - 1, matrixAdjoin.length - 1);

				return matrixProduct;

			} else {
				printMessage("La matrix no tiene inversa por que su determinante es cero");
				return null;
			}
		} else {
			printMessage("Primero ingrese la matriz a calcular");
			return null;
		}
	}

	/**
	 * Gets the adjoin matrix
	 * @param matrix
	 * @return
	 * @throws IOException
	 * @throws NullPointerException
	 */
	private static double[][] getAdjointMatrix(double[][] matrix) throws IOException, NullPointerException {
		double[][] matrixResult = new double[matrix.length][matrix.length];
		// It is equal to the transpose of the matrix cofactors
		getTransposedMatrix(getCofactorMatrix(matrix), matrixResult, 0, 0);
		return matrixResult;
	}

	/**
	 * Gets the cofactor matrix
	 * @param matrix
	 * @return
	 */
	private static double[][] getCofactorMatrix(double[][] matrix) {
		double[][] newMatrix = new double[matrix.length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {

				double[][] determinant = new double[matrix.length - 1][matrix.length - 1];
				double determinantValue;

				for (int k = 0; k < matrix.length; k++) {
					if (k != i) {
						for (int l = 0; l < matrix.length; l++) {
							if (l != j) {
								int a = k < i ? k : k - 1;
								int b = l < j ? l : l - 1;
								determinant[a][b] = matrix[k][l];
							}
						}
					}
				}

				determinantValue = getDeterminant(determinant);
				newMatrix[i][j] = determinantValue * Math.pow(-1, i + j + 2);
			}
		}
		return newMatrix;
	}

	/**
	 * Gets the tranposed matrix
	 * @param matrix
	 * @param newMatrix
	 * @param numberRows
	 * @param numberColumns
	 */
	private static void getTransposedMatrix(double[][] matrix, double[][] newMatrix, int numberRows, int numberColumns) {
		// Array length
		int lengthArray = matrix.length;

		if (numberRows < lengthArray && newMatrix != null) {

			// Transposes each element in the array
			newMatrix[numberColumns][numberRows] = matrix[numberRows][numberColumns];

			if (numberColumns < lengthArray - 1) {
				// Increments a column and keep the row
				numberColumns++;
			} else {
				// Increments a row and resets the column
				numberRows++;
				numberColumns = 0;
			}

			// Call recursive
			getTransposedMatrix(matrix, newMatrix, numberRows, numberColumns);
		}
	}

	/**
	 * Multiplies a scalar by a matrix
	 * @param scalar
	 * @param matrix
	 * @param numberRows
	 * @param numberColumns
	 * @return
	 */
	private static double[][] multiplyMatrixByScalar(double scalar, double[][] matrix, int numberRows,
			int numberColumns) {
		if (numberRows > -1 && numberRows > -1) {

			// Multiplies each item by scalar
			matrix[numberRows][numberColumns] *= scalar;

			if (numberColumns == 0) {
				// Less a row
				numberRows--;
				numberColumns = matrix.length - 1;
			} else {
				// Less a column
				numberColumns--;
			}

			// Call recursive
			multiplyMatrixByScalar(scalar, matrix, numberRows, numberColumns);
		}

		return matrix;
	}

	/**
	 * Gets determinant matrix
	 * @param matrix
	 * @return
	 */
	private static double getDeterminant(double[][] matrix) {
		double determinant;

		// Calculates determinant of matrix 2 x 2
		if (matrix.length == 2) {
			determinant = (matrix[0][0] * matrix[1][1]) - (matrix[1][0] * matrix[0][1]);
			return determinant;
		}

		// Calculates determinant of matrix n x n
		double sum = 0;

		for (int i = 0; i < matrix.length; i++) {
			double[][] newMatrix = new double[matrix.length - 1][matrix.length - 1];

			for (int j = 0; j < matrix.length; j++) {
				if (j != i) {
					for (int k = 1; k < matrix.length; k++) {
						int index = -1;
						if (j < i)
							index = j;
						else if (j > i)
							index = j - 1;
						newMatrix[index][k - 1] = matrix[j][k];
					}
				}
			}

			// Call recursive
			if (i % 2 == 0) {
				sum += matrix[i][0] * getDeterminant(newMatrix);
			} else {
				sum -= matrix[i][0] * getDeterminant(newMatrix);
			}
		}
		return sum;
	}
}
