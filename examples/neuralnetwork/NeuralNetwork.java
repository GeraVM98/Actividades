import java.util.List;

public class NeuralNetwork{
	private Data layer1W;
	private Data hiddenB;
  private Data layer2W; 
  private Data outputB;	
	private double learningRate=0.3;
  private int epochs;
	
	public NeuralNetwork(int nInputs,int nNeurons,int nNeuronsOutput, int epochs){
		this.layer1W = new Data(nNeurons,nInputs);
		this.hiddenB = new Data(nNeurons,1);
		this.layer2W = new Data(nNeuronsOutput,nNeurons);
		this.outputB = new Data(nNeuronsOutput,1);
		this.epochs = epochs;
	}
	
	public List<Double> predict(double[] X){
		Data input = Data.fromArray(X);
		Data hidden = Data.multiply(layer1W, input);
		hidden.add(hiddenB);
		hidden.sigmoid();
		Data output = Data.multiply(layer2W,hidden);
		output.add(outputB);
		output.sigmoid();
		Data classification = Data.activate(output);
		return classification.toArray();
	}
	
	public void trainDataset(double[][]X,double[][]Y){
		for(int i=0;i<epochs;i++) {	
			int sampleN = (int)(Math.random() * X.length);
			this.train(X[sampleN], Y[sampleN]);
		}
	}
	
	public void train(double [] X,double [] Y){
		Data input = Data.fromArray(X);
		Data hidden = Data.multiply(layer1W, input);
		hidden.add(hiddenB);
		hidden.sigmoid();
		Data output = Data.multiply(layer2W,hidden);
		output.add(outputB);
		output.sigmoid();
		
		Data expectedY = Data.fromArray(Y);
		Data errorRate = Data.subtract(expectedY, output);
		Data localGradient = output.dsigmoid();

		localGradient.multiply(errorRate);
		localGradient.multiply(learningRate);
		Data hidden_T = Data.transpose(hidden);
		Data deltaLayer2 =  Data.multiply(localGradient, hidden_T);
		layer2W.add(deltaLayer2);
		outputB.add(localGradient);
		Data who_T = Data.transpose(layer2W);
		Data hidden_errors = Data.multiply(who_T, errorRate);

		Data h_gradient = hidden.dsigmoid();
		h_gradient.multiply(hidden_errors);
		h_gradient.multiply(learningRate);
		Data i_T = Data.transpose(input);
		Data deltaLayer1 = Data.multiply(h_gradient, i_T);
		layer1W.add(deltaLayer1);
		hiddenB.add(h_gradient);
	}
}