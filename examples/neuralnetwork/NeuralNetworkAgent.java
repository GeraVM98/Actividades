import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkAgent extends Agent {
  myGui gui;
  NeuralNetwork neuralNetwork;

  protected void setup() {
    System.out.println("Agent " + getLocalName() + " started.");
    double [][] dataSetX = {
      { 
        0,0,1,0,0, 
        0,0,1,1,0, 
        1,1,1,1,1, 
        1,0,1,1,0, 
        1,0,1,0,0 
      },
      { 
        0,0,1,0,0, 
        0,0,0,1,0, 
        1,1,1,1,1, 
        1,0,0,1,0, 
        1,0,1,0,0 
      },
      { 
        0,0,1,1,0, 
        0,0,0,1,1, 
        1,1,1,1,1, 
        1,0,0,1,1, 
        1,0,1,1,0 
      },
      { 
        0,0,1,1,0, 
        0,0,0,1,0, 
        1,1,1,1,1, 
        1,0,0,1,1, 
        1,0,1,1,0 
      },
      
      { 
        0,0,1,0,0, 
        0,1,1,0,0, 
        1,1,1,1,1, 
        0,1,1,0,1, 
        0,0,1,0,1 
      },
      { 
        0,0,1,0,0, 
        0,1,0,0,0, 
        1,1,1,1,1, 
        0,1,0,0,1, 
        0,0,1,0,1 
      },
      { 
        0,1,1,0,0, 
        1,1,0,0,0, 
        1,1,1,1,1, 
        1,1,0,0,1, 
        0,1,1,0,1 
      },
      { 
        0,1,1,0,0, 
        1,1,0,0,0, 
        1,1,1,1,1, 
        0,1,0,0,1, 
        0,1,1,0,1 
      }
    };
    double [][] dataSetY= {
			{ 
        1, 1 
      },
      { 
        1, 1 
      },
      { 
        1, 1 
      },
      { 
        1, 1 
      },
      { 
        0, 0 
      },
      { 
        0, 0 
      },
      { 
        0, 0 
      },
      { 
        0, 0 
      }
	  };
    int i = 25;
    int nHidenNeurons = 8;
    int o = 2;
    int e = 500;
    neuralNetwork = new NeuralNetwork(i, nHidenNeurons, o, e);
    neuralNetwork.trainDataset(dataSetX, dataSetY);
    gui = new myGui(this);
    gui.showGui();
  }

  public void predict(String xData) {
    addBehaviour(new OneShotBehaviour() {
      @Override
      public void action() {
        double[] X = new double[25];
        String[] xInputs = xData.split(" ");
        for(int i=0; i<xInputs.length; i++) 
          X[i] = Double.parseDouble(xInputs[i]);
        List<Double> output = neuralNetwork.predict(X);
        if(output.get(0)==1d && output.get(1)==1d)
          System.out.println("Flecha derecha " + output.get(0) + " " + output.get(1));
        else if(output.get(0)==0d && output.get(1)==0d)
          System.out.println("Flecha izquierda" + output.get(0) + " " + output.get(1));
        else
          System.out.println("Error");
      }
    });
  }
}