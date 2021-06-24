package Hands_on_6;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import javax.swing.JOptionPane;
import Jama.Matrix;
import Jama.QRDecomposition;

public class MLRAgent extends Agent {

	private Matrix beta;  // regression coefficients
    	private double sse;         // sum of squared
    	private double sst;         // sum of squared

    	public void MLR(double[][] x, double[] y) {
        	if (x.length != y.length) {
            		throw new IllegalArgumentException("matrix dimensions don't agree");
        	}

        	// number of observations
        	int n = y.length;

        	Matrix matrixX = new Matrix(x);

        	// create matrix from vector
        	Matrix matrixY = new Matrix(y, n);

        	// find least squares solution
        	QRDecomposition qr = new QRDecomposition(matrixX);
        	beta = qr.solve(matrixY);


        	// mean of y[] values
        	double sum = 0.0;
        	for (int i = 0; i < n; i++)
            		sum += y[i];
        	double mean = sum / n;

        	// total variation to be accounted for
        	for (int i = 0; i < n; i++) {
            		double dev = y[i] - mean;
            		sst += dev*dev;
        	}

        	// variation not accounted for
       		Matrix residuals = matrixX.times(beta).minus(matrixY);
        	sse = residuals.norm2() * residuals.norm2();

    	}

       	public double beta(int j) {
        	return beta.get(j, 0);
    	}

  	protected void setup() {
    		addBehaviour(new MyOneShotBehaviour());
  	} 

  	private class MyOneShotBehaviour extends OneShotBehaviour {

    		public void action() {
			double[][] x = { {  1,  41.9,  29.1 },
                         {  1,  43.4,  29.3 },
                         {  1,  43.9,  29.5 },
                         {  1,  44.5,  29.7 },
                         {  1,  47.3,  29.9 },
                         {  1,  47.5,  30.3 },
			 {  1,  47.9,  30.5 },
                         {  1,  50.2,  30.7 },
                         {  1,  52.8,  30.8 },
                         {  1,  53.2,  30.9 },
                         {  1,  56.7,  31.5 },
			 {  1,  57.0,  31.7 },
			 {  1,  63.5,  31.9 },
                         {  1,  65.3,  32.0 },
                         {  1,  71.1,  32.1 },
                         {  1,  77.0,  32.5 },
                         {  1,  77.8,  32.9 } };
        		double[] y = { 251.3, 251.3, 248.3, 267.5, 273.0, 276.5, 270.3, 274.9, 285.0, 290.0, 297.0, 302.5, 304.5, 309.3, 321.7, 330.7, 349.0 };
        		double X1;
			double X2;
			double Y;

			MLRAgent regression = new MLRAgent();
			regression.MLR(x, y);
		
			X1 = Float.parseFloat(JOptionPane.showInputDialog("Ingresa un valor de X1 para estimar: "));
			X2 = Float.parseFloat(JOptionPane.showInputDialog("Ingresa un valor de X2 para estimar: "));

			Y= regression.beta(0) + (regression.beta(1)*X1) + (regression.beta(2)*X2);

			System.out.printf("Y = %.2f + %.2f X1 + %.2f X2 \n",
                      			regression.beta(0), regression.beta(1), regression.beta(2));
			System.out.printf("Y = %.2f para X1 = %.2f y X2 = %.2f \n", Y, X1, X2);

        		System.out.println("Metodo del Agente finalizado");
    		} 
    
    		public int onEnd() {
      			myAgent.doDelete();   
      			return super.onEnd();
    		} 
  	}    // END of inner class ...Behaviour
}
