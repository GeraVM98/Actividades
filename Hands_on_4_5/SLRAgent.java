package Hands_on_4_5;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import javax.swing.JOptionPane;

public class SLRAgent extends Agent {

	public static double[] calcRectaRegresionYsobreX(double[] lasX, double[] lasY) {
        	double[] retVal = new double[3];
       		double mediaX = calcMedia(lasX);
        	double mediaY = calcMedia(lasY);
        	double varianzaX = (calcMediaDeLosCuadrados(lasX) - Math.pow(mediaX, 2));
        	double varianzaY = (calcMediaDeLosCuadrados(lasY) - Math.pow(mediaY, 2));
        	double covarianza = calcMediaDeLosProductos(lasX, lasY) - (mediaX * mediaY);
        	double diviCovaX = covarianza / varianzaX;

        	retVal[0] = diviCovaX;                     
		// aqui devuelve la pendiente de la recta
        	retVal[1] = -(diviCovaX * mediaX) + mediaY;   
		// aqui devuelve el parametro independiente
        	if ((Math.sqrt(varianzaX) * Math.sqrt(varianzaY))==0){
            		retVal[2]=1;
        	} else {
            		retVal[2] = covarianza / (Math.sqrt(varianzaX) * Math.sqrt(varianzaY)); // esto es la correlacion r
        	}
        	return retVal;
    	}

    	public static double calcMedia(double[] valores) {
        	double retVal = 0;
        	for (int i = 0; i < valores.length; i++) {
            		retVal += valores[i];
        	}
        	return retVal / valores.length;
    	}

    	public static double calcMediaDeLosCuadrados(double[] valores) {
        	double retVal = 0;
        	for (int i = 0; i < valores.length; i++) {
            		retVal += Math.pow(valores[i], 2);
        	}
        	return retVal / valores.length;
    	}

    	public static double calcMediaDeLosProductos(double[] valores1, double[] valores2) {
        	double retVal = 0;
        	for (int i = 0; i < valores1.length; i++) {
            		retVal += valores1[i] * valores2[i];
        	}
        	return retVal / valores1.length;
    	}

  	protected void setup() {
    		addBehaviour(new MyOneShotBehaviour());
  	} 

  	private class MyOneShotBehaviour extends OneShotBehaviour {

    		public void action() {
			double[] X = new double[9];
			double[] Y = new double[9];
			double[] resultados = new double[3];
			double X_est;
			double Y_est;

			Y[0] = 651;Y[1] = 762;Y[2] = 856;Y[3] = 1063;Y[4] = 1190;Y[5] = 1298;Y[6] = 1421;Y[7] = 1440;Y[8] = 1518;
			X[0] = 23;X[1] = 26;X[2] = 30;X[3] = 34;X[4] = 43;X[5] = 48;X[6] = 52;X[7] = 57;X[8] = 58;

			resultados = calcRectaRegresionYsobreX(X,Y);
		
			X_est = Float.parseFloat(JOptionPane.showInputDialog("Ingresa un valor de X para estimar: "));

			Y_est= resultados[1] + (resultados[0]*X_est);

			System.out.println("Y =" + resultados[1] + "+" + resultados[0] + "X");
			System.out.println("Y =" + Y_est + "para X =" + X_est);

        		System.out.println("Metodo del Agente finalizado");
    		} 
    
    		public int onEnd() {
      			myAgent.doDelete();   
      			return super.onEnd();
    		} 
  	}    // END of inner class ...Behaviour
}
