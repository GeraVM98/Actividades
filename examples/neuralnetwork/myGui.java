import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class myGui extends JFrame {
  private JTextField xDataField;

  myGui(NeuralNetworkAgent neuralNetworkAgent) {
    super(neuralNetworkAgent.getLocalName());

    JPanel p = new JPanel();
    p.setLayout(new GridLayout(3, 2));

    p.add(new JLabel("Ingrese los datos :"));
    xDataField = new JTextField("", 50);
    p.add(xDataField);
    getContentPane().add(p, BorderLayout.CENTER);

    JButton addButton = new JButton("Aceptar");
    addButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        try {
          String xData = xDataField.getText().trim();
          neuralNetworkAgent.predict(xData);
          xDataField.setText("");
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    });
    p = new JPanel();
    p.add(addButton);
    getContentPane().add(p, BorderLayout.SOUTH);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        neuralNetworkAgent.doDelete();
      }
    });

    setResizable(false);
  }

  public void showGui() {
    pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int centerX = (int) screenSize.getWidth() / 2;
    int centerY = (int) screenSize.getHeight() / 2;
    setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
    super.setVisible(true);
  }
}